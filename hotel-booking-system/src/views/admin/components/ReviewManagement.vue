<template>
  <div class="review-management">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input v-model="queryParams.hotelId" placeholder="酒店ID" clearable style="width: 120px" />
      <el-input v-model="queryParams.userId" placeholder="用户ID" clearable style="width: 120px" />
      <el-select v-model="queryParams.status" placeholder="状态" clearable style="width: 120px">
        <el-option label="待审核" :value="0" />
        <el-option label="已通过" :value="1" />
        <el-option label="已拒绝" :value="2" />
      </el-select>
      <el-button type="primary" @click="fetchReviews">搜索</el-button>
      <el-button @click="resetQuery">重置</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="reviewList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="hotelId" label="酒店ID" width="80" />
      <el-table-column prop="userId" label="用户ID" width="80" />
      <el-table-column prop="rating" label="评分" width="140">
        <template #default="{ row }">
          <el-rate v-model="row.rating" disabled size="small" />
        </template>
      </el-table-column>
      <el-table-column prop="content" label="评价内容" min-width="250" show-overflow-tooltip />
      <el-table-column prop="reviewDate" label="评价时间" width="160" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" type="success" :disabled="row.status === 1" @click="handleApprove(row)">
            通过
          </el-button>
          <el-button size="small" type="warning" :disabled="row.status === 2" @click="handleReject(row)">
            拒绝
          </el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
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
        @size-change="fetchReviews"
        @current-change="fetchReviews"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAdminReviews, updateReviewStatus, deleteReview } from '@/api/admin'

const loading = ref(false)
const reviewList = ref([])
const total = ref(0)

const queryParams = reactive({
  page: 1,
  pageSize: 10,
  hotelId: '',
  userId: '',
  status: null
})

const getStatusText = (status) => {
  const map = { 0: '待审核', 1: '已通过', 2: '已拒绝' }
  return map[status] || '未知'
}

const getStatusType = (status) => {
  const map = { 0: 'warning', 1: 'success', 2: 'danger' }
  return map[status] || 'info'
}

const fetchReviews = async () => {
  loading.value = true
  try {
    const res = await getAdminReviews(queryParams)
    reviewList.value = res.records || []
    total.value = res.total || 0
  } catch (error) {
    ElMessage.error('获取评价列表失败')
  } finally {
    loading.value = false
  }
}

const resetQuery = () => {
  queryParams.hotelId = ''
  queryParams.userId = ''
  queryParams.status = null
  queryParams.page = 1
  fetchReviews()
}

const handleApprove = async (row) => {
  try {
    await updateReviewStatus(row.id, 1)
    ElMessage.success('审核通过')
    fetchReviews()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleReject = async (row) => {
  try {
    await updateReviewStatus(row.id, 2)
    ElMessage.success('已拒绝')
    fetchReviews()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该评价吗？此操作不可恢复', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await deleteReview(row.id)
    ElMessage.success('删除成功')
    fetchReviews()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

onMounted(() => {
  fetchReviews()
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
</style>
