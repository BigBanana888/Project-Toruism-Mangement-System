<template>
    <div class="goodsindex">
      <!-- Search Filters -->
      <el-row :gutter="20" class="filter-bar">
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input 
            v-model="searchParams.username"
            placeholder="Search by account"
            clearable 
            size="small"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input
            v-model="searchParams.phone"
            placeholder="Search by phone"
            clearable
            size="small"/>
        </el-col>
        
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-select 
            v-model="searchParams.ss" 
            placeholder="Filter by status"
            clearable
            size="small">
            <el-option label="pending" value="pending"/>
            <el-option label="approve" value="approve"/>
            <el-option label="reject" value="reject"/>
          </el-select>
        </el-col>
        <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
          <el-button 
            type="primary" 
            size="small"
            @click="loadData">
            Search
          </el-button>
        </el-col>
      </el-row>
  
      <!-- User List -->
      <el-table
        v-loading="isLoading"
        :data="userList"
        border
        size="small"
        style="width: 100%"
        :header-cell-style="{ 'text-align': 'center', background: '#f5f7fa' }">
        
        <el-table-column prop="id" label="User ID" width="220" align="center"></el-table-column>
        <el-table-column prop="nickname" label="Name" width="180" show-overflow-tooltip></el-table-column>
        
        <el-table-column prop="roleId" label="Role" width="180" align="center">
          <template slot-scope="{row}">
            <el-tag v-if="row.roleId==='0'" effect="dark">Super Admin</el-tag>
            <!-- <el-tag v-if="row.roleId==='1'" type="danger" effect="dark">Venue Manager</el-tag> -->
            <el-tag v-if="row.roleId==='2'" type="info" effect="dark">Regular User</el-tag>
          </template>
        </el-table-column>
  
        <el-table-column prop="username" label="Account" width="220" show-overflow-tooltip></el-table-column>
        <el-table-column prop="phone" label="Phone" width="220" show-overflow-tooltip></el-table-column>
        <el-table-column prop="email" label="Email" width="220" show-overflow-tooltip></el-table-column>
        <el-table-column prop="imageUrl" label="Avatar" width="120" align="center">
          <template slot-scope="{row}">
            <img :src="row.imageUrl" class="user-avatar" alt="avatar"/>
          </template>
        </el-table-column>
  
        <el-table-column prop="birth" label="Birthday" width="220" :formatter="formatDate"></el-table-column>
        <el-table-column prop="createdTime" label="Created" width="220" :formatter="formatDate"></el-table-column>
        
        <el-table-column prop="ss" label="Status" width="150" align="center">
          <template slot-scope="{row}">
            <el-tag :type="statusTagType(row.ss)" size="small">
              {{ row.ss }}
            </el-tag>
          </template>
        </el-table-column>
  
        <el-table-column fixed="right" label="Actions" width="300" align="center">
          <template slot-scope="scope">
            <el-button 
              :disabled="scope.row.ss !== 'pending'"
              @click="openReviewDialog(scope.row)" 
              type="primary" 
              size="mini">
              Review
            </el-button>
            <el-button 
              @click="toDelete(scope.row)" 
              type="danger" 
              size="mini" 
              >
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- Review Dialog -->
      <el-dialog 
        :title="`User Review - ${currentItem.username}`" 
        :visible.sync="showReviewDialog" 
        width="600px">
        <el-form ref="reviewForm" :model="reviewData" label-width="120px">
          <el-form-item label="Credentials">
            <img 
              :src="currentItem.sf" 
              class="credential-image"
              alt="verification materials"/>
          </el-form-item>
          <el-form-item 
            label="Review Result" 
            prop="result"
            :rules="[{ required: true, message: 'Please select review result' }]">
            <el-radio-group v-model="reviewData.result">
              <el-radio label="approve">Approve</el-radio>
              <el-radio label="reject">Reject</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item 
            label="Comments"
            prop="comments"
            :rules="[{ 
              required: reviewData.result === 'reject',
              message: 'Comments are required when rejecting' 
            }]">
            <el-input
              v-model="reviewData.comments"
              type="textarea"
              :rows="3"
              placeholder="Enter review comments..."
              resize="none"/>
          </el-form-item>
          <div class="dialog-actions">
            <el-button @click="showReviewDialog = false">Cancel</el-button>
            <el-button 
              type="primary" 
              @click="submitReview">
              Submit
            </el-button>
          </div>
        </el-form>
      </el-dialog>
  
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
  import userApi from '@/apis/user'
  import { formatDate } from '@/utils/date'
  
  export default {
    data() {
      return {
        user:null,
        isLoading: false,
        searchParams: {
          username: '',
          phone: ''
        },
        showReviewDialog: false,
        currentItem: {},
        reviewData: {
          result: 'approve',
          comments: ''
        },
        userList: [],
        pagination: {
          page: 1,
          pageSize: 10,
          total: 0
        }
      }
    },
  
    methods: {
      async loadData() {
        try {
          this.isLoading = true
          const params = {
            ...this.searchParams
          }
          
          const res = await userApi.list(params,
            this.pagination.page,
            this.pagination.pageSize)
          this.userList = res.data.records
          this.pagination.total = res.data.total
        } finally {
          this.isLoading = false
        }
      },
  
      openReviewDialog(item) {
        this.currentItem = { ...item }
        this.reviewData = {
          result: 'approve',
          comments: ''
        }
        this.showReviewDialog = true
      },
  
      async submitReview() {
        try {
          await this.$refs.reviewForm.validate()
            this.currentItem.ss = this.reviewData.result
            this.currentItem.rep = this.reviewData.comments
            this.currentItem.modifiedId = this.user.id
          await userApi.update(this.currentItem)
          this.$message.success('Review submitted successfully')
          this.showReviewDialog = false
          this.loadData()
        } catch (error) {
          console.error('Review submission failed:', error)
          this.$message.error('Submission failed, please check the form')
        }
      },
      statusTagType(status) {
        switch(status) {
          case 'pending': return 'warning'
          case 'approve': return 'success'
          case 'reject': return 'danger'
          default: return 'info'
        }
      },
  
      formatDate,
  
      handlePageChange(page) {
        this.pagination.page = page
        this.loadData()
      },
  
      toDelete(item) {
        this.$confirm("This will permanently delete the user. Continue?", "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }).then(() => {
          this.deleteUser(item.id)
        })
      },
  
      async deleteUser(id) {
        try {
          await userApi.deleteById(id)
          this.$message.success('User deleted')
          this.loadData()
        } catch (error) {
          console.error('Delete failed:', error)
        }
      }
    },
  
    mounted() {
        this.user = JSON.parse(this.$store.getStore('user'))
      this.loadData()
    }
  }
  </script>
  
  <style scoped>
  
  .goodsindex{
      min-height: 100%;
      padding: 15px;
      box-sizing: border-box;
      background-color:white;
      margin: 20px;
  }
  
  .user-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    object-fit: cover;
  }
  
  .credential-image {
    width: 200px;
    height: 200px;
    border: 1px solid #eee;
    border-radius: 4px;
  }
  
  .dialog-actions {
    margin-top: 20px;
    text-align: right;
  }
  
  .pagination-bar {
    margin-top: 20px;
    display: flex;
    justify-content: end;
  }
  
  .filter-bar {
    margin-bottom: 20px;
  }
  </style>