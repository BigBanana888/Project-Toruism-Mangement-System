<template>
    <div class="message-manage">
      <!-- Filter Section -->
      <el-row :gutter="20" class="filter-bar">
        <el-col :xs="14" :sm="12" :md="10" :lg="8" :xl="6">
          <el-input
            v-model="searchKey.createdName"
            placeholder="Search send username..."
            clearable
            size="small"
            @keyup.enter.native="loadData">
          </el-input>
        </el-col>
        <el-col :xs="4" :sm="3" :md="2" :lg="2" :xl="2">
          <el-button 
            type="primary" 
            size="small"
            @click="loadData">
            Search
          </el-button>
        </el-col>
      </el-row>
  
      <!-- Data Table -->
      <el-table
        v-loading="isLoading"
        :data="messageList"
        border
        size="small"
        style="width: 100%"
        :header-cell-style="{ 'text-align': 'center', background: '#f5f7fa' }"
        :cell-style="{ 'text-align': 'center' }">
        
        <el-table-column 
          prop="content" 
          label="Content"
          show-overflow-tooltip>
        </el-table-column>
  
        <el-table-column 
          label="Sender" 
          width="180">
          <template slot-scope="scope">
            <div class="user-cell">
              <el-avatar :size="32" :src="scope.row.ch"></el-avatar>
              <span class="user-name">{{ scope.row.createdName }}</span>
            </div>
          </template>
        </el-table-column>
  
        <el-table-column 
          label="Receiver" 
          width="180">
          <template slot-scope="scope">
            <div class="user-cell">
              <el-avatar :size="32" :src="scope.row.ah"></el-avatar>
              <span class="user-name">{{ scope.row.aname }}</span>
            </div>
          </template>
        </el-table-column>
  
        <el-table-column 
          prop="createdTime" 
          label="Created Time" 
          width="180">
          <template slot-scope="scope">
            {{ formatDate(scope.row.createdTime) }}
          </template>
        </el-table-column>
  
        <el-table-column 
          label="Status" 
          width="120">
          <template slot-scope="scope">
            <el-tag 
              :type="scope.row.ss === '1' ? 'success' : 'warning'"
              effect="plain">
              {{ scope.row.ss === '1' ? 'Replied' : 'Pending' }}
            </el-tag>
          </template>
        </el-table-column>
  
        <el-table-column 
          fixed="right" 
          label="Actions" 
          width="200" 
          align="center">
          <template slot-scope="scope">
            <el-button 
              @click="deleteItem(scope.row)" 
              type="danger" 
              size="mini">
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- Pagination -->
      <el-pagination
        background
        layout="total, prev, pager, next"
        :current-page="pagination.page"
        :page-size="pagination.pageSize"
        :total="pagination.total"
        @current-change="handlePageChange"
        class="pagination-bar"/>
    </div>
  </template>
  
  <script>
  import messageApi from '@/apis/chat'
  import { formatDate } from '@/utils/date'
  
  export default {
    data() {
      return {
        isLoading: true,
        searchKey: {},
        messageList: [],
        pagination: {
          page: 1,
          pageSize: 10,
          total: 0
        },
        user:{}
      }
    },
  
    mounted() {
    this.loadData()
    },
  
    methods: {
      async loadData() {
        try {
          this.isLoading = true
          const res = await messageApi.list(
           this.searchKey
          , this.pagination.page, this.pagination.pageSize)
  
          this.messageList = res.data.records
          this.pagination.total = res.data.total
        } finally {
          this.isLoading = false
        }
      },
  
      async markAsReplied(item) {
        try {
          await messageApi.updateStatus(item.id)
          this.$message.success('Marked as replied')
          this.loadData()
        } catch (error) {
          this.$message.error('Operation failed')
        }
      },
  
      deleteItem(item) {
        this.$confirm('This will permanently delete the message. Continue?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(async () => {
          await messageApi.deleteById(item.id)
          this.$message.success('Message deleted')
          this.loadData()
        }).catch(() => {
          this.$message.info('Deletion canceled')
        })
      },
  
      handlePageChange(page) {
        this.pagination.page = page
        this.loadData()
      },
  
      formatDate(time) {
        return formatDate(time, 'YYYY-MM-DD HH:mm')
      }
    }
  }
  </script>
  
  <style scoped>
  .message-manage {
    min-height: 100%;
    padding: 15px;
    box-sizing: border-box;
    background-color: white;
    margin: 20px;
  }
  
  .filter-bar {
    margin-bottom: 20px;
  }
  
  .user-cell {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
  }
  
  .user-name {
    font-size: 14px;
  }
  
  .pagination-bar {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
  
  .el-table {
    margin: 20px 0;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  </style>