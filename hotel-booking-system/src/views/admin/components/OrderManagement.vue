<template>
  <div class="order-management">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input v-model="queryParams.orderNo" placeholder="订单号" clearable style="width: 200px" />
      <el-input v-model="queryParams.userId" placeholder="用户ID" clearable style="width: 120px" />
      <el-select v-model="queryParams.status" placeholder="订单状态" clearable style="width: 140px">
        <el-option label="待支付" value="PENDING" />
        <el-option label="已支付" value="PAID" />
        <el-option label="已确认" value="CONFIRMED" />
        <el-option label="已完成" value="COMPLETED" />
        <el-option label="已取消" value="CANCELLED" />
      </el-select>
      <el-button type="primary" @click="fetchOrders">搜索</el-button>
      <el-button @click="resetQuery">重置</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="orderList" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="orderNo" label="订单号" width="180" />
      <el-table-column prop="userId" label="用户ID" width="80" />
      <el-table-column prop="hotelId" label="酒店ID" width="80" />
      <el-table-column prop="roomId" label="房间ID" width="80" />
      <el-table-column prop="checkInDate" label="入住日期" width="110" />
      <el-table-column prop="checkOutDate" label="退房日期" width="110" />
      <el-table-column prop="totalPrice" label="总价" width="100">
        <template #default="{ row }">¥{{ row.totalPrice }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-dropdown @command="(cmd) => handleStatusChange(row, cmd)">
            <el-button size="small" type="primary">
              修改状态 <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="CONFIRMED" :disabled="row.status !== 'PAID'">确认订单</el-dropdown-item>
                <el-dropdown-item command="COMPLETED" :disabled="row.status !== 'CONFIRMED'">完成订单</el-dropdown-item>
                <el-dropdown-item command="CANCELLED" :disabled="['COMPLETED', 'CANCELLED'].includes(row.status)">取消订单</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
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
        @size-change="fetchOrders"
        @current-change="fetchOrders"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowDown } from '@element-plus/icons-vue'
import { getAdminOrders, updateOrderStatus } from '@/api/admin'

const loading = ref(false)
const orderList = ref([])
const total = ref(0)

const queryParams = reactive({
  page: 1,
  pageSize: 10,
  orderNo: '',
  userId: '',
  status: ''
})

const statusMap = {
  PENDING: { text: '待支付', type: 'warning' },
  PAID: { text: '已支付', type: 'primary' },
  CONFIRMED: { text: '已确认', type: 'success' },
  COMPLETED: { text: '已完成', type: 'info' },
  CANCELLED: { text: '已取消', type: 'danger' }
}

const getStatusText = (status) => statusMap[status]?.text || status
const getStatusType = (status) => statusMap[status]?.type || 'info'

const fetchOrders = async () => {
  loading.value = true
  try {
    const res = await getAdminOrders(queryParams)
    orderList.value = res.records || []
    total.value = res.total || 0
  } catch (error) {
    ElMessage.error('获取订单列表失败')
  } finally {
    loading.value = false
  }
}

const resetQuery = () => {
  queryParams.orderNo = ''
  queryParams.userId = ''
  queryParams.status = ''
  queryParams.page = 1
  fetchOrders()
}

const handleStatusChange = async (row, newStatus) => {
  try {
    await ElMessageBox.confirm(`确定要将订单状态修改为"${getStatusText(newStatus)}"吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await updateOrderStatus(row.id, newStatus)
    ElMessage.success('状态修改成功')
    fetchOrders()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('状态修改失败')
    }
  }
}

onMounted(() => {
  fetchOrders()
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
