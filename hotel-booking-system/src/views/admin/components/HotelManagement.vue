<template>
  <div class="hotel-management">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input v-model="queryParams.name" placeholder="酒店名称" clearable style="width: 200px" />
      <el-input v-model="queryParams.city" placeholder="城市" clearable style="width: 150px" />
      <el-select v-model="queryParams.status" placeholder="状态" clearable style="width: 120px">
        <el-option label="正常" :value="1" />
        <el-option label="停用" :value="0" />
      </el-select>
      <el-button type="primary" @click="fetchHotels">搜索</el-button>
      <el-button @click="resetQuery">重置</el-button>
      <el-button type="success" @click="handleAdd">新增酒店</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="hotelList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column label="图片" width="100">
        <template #default="{ row }">
          <el-image v-if="row.image" :src="row.image" style="width: 60px; height: 40px" fit="cover" />
          <span v-else class="text-gray-400">无图片</span>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="酒店名称" min-width="150" />
      <el-table-column prop="city" label="城市" width="100" />
      <el-table-column prop="address" label="地址" min-width="180" show-overflow-tooltip />
      <el-table-column prop="starRating" label="星级" width="120">
        <template #default="{ row }">
          <el-rate v-model="row.starRating" disabled size="small" />
        </template>
      </el-table-column>
      <el-table-column prop="minPrice" label="起始价格" width="100">
        <template #default="{ row }">¥{{ row.minPrice || 0 }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" :type="row.status === 1 ? 'warning' : 'success'" 
            @click="handleToggleStatus(row)">
            {{ row.status === 1 ? '停用' : '启用' }}
          </el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="queryParams.page"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next"
        @size-change="fetchHotels"
        @current-change="fetchHotels"
      />
    </div>

    <!-- 编辑/新增对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑酒店' : '新增酒店'" width="600px">
      <el-form :model="hotelForm" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="酒店名称" prop="name">
          <el-input v-model="hotelForm.name" placeholder="请输入酒店名称" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="hotelForm.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="hotelForm.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="星级" prop="starRating">
          <el-rate v-model="hotelForm.starRating" />
        </el-form-item>
        <el-form-item label="起始价格" prop="minPrice">
          <el-input-number v-model="hotelForm.minPrice" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="酒店图片">
          <el-upload
            class="avatar-uploader"
            action="/api/file/upload/image"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload"
          >
            <img v-if="hotelForm.image" :src="hotelForm.image" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="hotelForm.description" type="textarea" :rows="3" placeholder="请输入酒店描述" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="hotelForm.status" :active-value="1" :inactive-value="0" 
            active-text="正常" inactive-text="停用" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getAdminHotels, updateHotelStatus, createHotel, updateHotel, deleteHotel } from '@/api/admin'

const loading = ref(false)
const hotelList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)
const editingId = ref(null)

// 上传请求头
const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${localStorage.getItem('token')}`
}))

const queryParams = reactive({
  page: 1,
  pageSize: 10,
  name: '',
  city: '',
  status: null
})

const hotelForm = reactive({
  name: '',
  city: '',
  address: '',
  starRating: 3,
  minPrice: 0,
  image: '',
  description: '',
  status: 1
})

const rules = {
  name: [{ required: true, message: '请输入酒店名称', trigger: 'blur' }],
  city: [{ required: true, message: '请输入城市', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
}

const fetchHotels = async () => {
  loading.value = true
  try {
    const res = await getAdminHotels(queryParams)
    hotelList.value = res.records || []
    total.value = res.total || 0
  } catch (error) {
    ElMessage.error('获取酒店列表失败')
  } finally {
    loading.value = false
  }
}

const resetQuery = () => {
  queryParams.name = ''
  queryParams.city = ''
  queryParams.status = null
  queryParams.page = 1
  fetchHotels()
}

const resetForm = () => {
  hotelForm.name = ''
  hotelForm.city = ''
  hotelForm.address = ''
  hotelForm.starRating = 3
  hotelForm.minPrice = 0
  hotelForm.image = ''
  hotelForm.description = ''
  hotelForm.status = 1
}

const handleAdd = () => {
  isEdit.value = false
  editingId.value = null
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  editingId.value = row.id
  Object.assign(hotelForm, {
    name: row.name || '',
    city: row.city || '',
    address: row.address || '',
    starRating: row.starRating || 3,
    minPrice: row.minPrice || 0,
    image: row.image || '',
    description: row.description || '',
    status: row.status ?? 1
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    submitLoading.value = true
    try {
      if (isEdit.value) {
        await updateHotel(editingId.value, hotelForm)
        ElMessage.success('更新成功')
      } else {
        await createHotel(hotelForm)
        ElMessage.success('创建成功')
      }
      dialogVisible.value = false
      fetchHotels()
    } catch (error) {
      ElMessage.error(isEdit.value ? '更新失败' : '创建失败')
    } finally {
      submitLoading.value = false
    }
  })
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该酒店吗？此操作不可恢复！', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteHotel(row.id)
    ElMessage.success('删除成功')
    fetchHotels()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleToggleStatus = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  const action = newStatus === 1 ? '启用' : '停用'
  
  try {
    await ElMessageBox.confirm(`确定要${action}该酒店吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await updateHotelStatus(row.id, newStatus)
    ElMessage.success(`${action}成功`)
    fetchHotels()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(`${action}失败`)
    }
  }
}

const handleUploadSuccess = (response) => {
  if (response.code === 200) {
    hotelForm.image = response.data
    ElMessage.success('图片上传成功')
  } else {
    ElMessage.error(response.message || '上传失败')
  }
}

const beforeUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt5M = file.size / 1024 / 1024 < 5
  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (!isLt5M) {
    ElMessage.error('图片大小不能超过5MB!')
    return false
  }
  return true
}

onMounted(() => {
  fetchHotels()
})
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 120px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-uploader:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}

.avatar {
  width: 120px;
  height: 80px;
  object-fit: cover;
}
</style>
