<template>
    <div class="goodsindex">
      <!-- Search Criteria -->
      <el-row :gutter="20" class="filter-bar">
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input 
            v-model="searchParams.name"
            placeholder="Search by name..."
            clearable 
            size="small"
            @keyup.enter.native="loadData"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input
            v-model="searchParams.address"
            placeholder="Search by address..."
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
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-select
            v-model="searchParams.bq"
            placeholder="Filter by tag"
            clearable
            size="small">
            <el-option 
              v-for="tag in tagOptions"
              :key="tag.value"
              :label="tag.label"
              :value="tag.value"/>
          </el-select>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
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
        :data="activityList"
        border
        size="small"
        style="width: 100%"
        :header-cell-style="{ 'text-align': 'center', background: '#f5f7fa' }">
        <el-table-column prop="name" label="Name" width="200"/>
        <el-table-column prop="address" label="Address" width="200"/>
        <el-table-column prop="bqn" label="Tag" width="200" />
        <el-table-column prop="num" label="Capacity" width="120" align="center"/>
        <el-table-column prop="ys" label="Budget" width="150">
          <template slot-scope="{row}">
            ¥{{ row.ys?.toLocaleString() }}
          </template>
        </el-table-column>
        <el-table-column prop="stime" label="Event Date" width="220">
          <template slot-scope="{row}">
            {{ formatDate(row.stime) }}
          </template>
        </el-table-column>
        <el-table-column prop="ss" label="Status" width="150">
          <template slot-scope="{row}">
            <el-tag :type="statusTagType(row.ss)" size="small">
              {{ row.ss }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="js" label="Description" />
        <el-table-column fixed="right" label="Actions" width="180" align="center">
          <template slot-scope="scope">
            <el-button 
              :disabled="scope.row.ss !== 'pending'"
              type="primary" 
              size="mini"
              @click="openReviewDialog(scope.row)">
              Review
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- Review Dialog -->
      <el-dialog 
        :title="`Review Activity - ${currentItem.name}`" 
        :visible.sync="showReviewDialog" 
        width="600px">
        <el-form ref="reviewForm" :model="reviewData">
          <el-form-item label="Review Result" prop="result">
            <el-radio-group v-model="reviewData.result">
              <el-radio label="approve">Approve</el-radio>
              <el-radio label="reject">Reject</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item 
            label="Review Comments"
            prop="comments"
            :rules="[{ required: reviewData.result === 'reject', message: 'Comments required for rejection' }]">
            <el-input
              v-model="reviewData.comments"
              type="textarea"
              :rows="4"
              placeholder="Please enter review comments..."
              resize="none"/>
          </el-form-item>
          <el-form-item class="dialog-actions">
            <el-button @click="showReviewDialog = false">Cancel</el-button>
            <el-button 
              type="primary" 
              @click="submitReview">
              Submit
            </el-button>
          </el-form-item>
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
  import activityApi from '@/apis/act'
  import { formatDate } from '@/utils/date'
  export default {
    data() {
      return {
        isLoading: false,
        searchParams: {
          name: '',
          address: '',
          status: '',
          tag: ''
        },
        tagOptions: [
          { value: 'sports', label: 'Sports' },
          { value: 'meeting', label: 'Meeting' },
          // Add more tag options
        ],
        showReviewDialog: false,
        currentItem: {},
        reviewData: {
          result: 'approve',
          comments: ''
        },
        activityList: [],
        pagination: {
          page: 1,
          pageSize: 10,
          total: 0
          },
        user:null
      }
    },
  
    methods: {
      async loadData() {
        try {
          this.isLoading = true
          const params = {
            ...this.searchParams,
          }
          
          const res = await activityApi.list(params,
            this.pagination.page,
            this.pagination.pageSize)
          this.activityList = res.data.records
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
          await activityApi.update(this.currentItem)
          this.$message.success('Review submitted')
          this.showReviewDialog = false
          this.loadData()
        } catch (error) {
          console.error('Review submission failed:', error)
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
  .filter-bar {
    margin-bottom: 20px;
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
  }
  
  .el-select {
    width: 100%;
  }
  
.pagination-bar {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end; 
}
  </style>