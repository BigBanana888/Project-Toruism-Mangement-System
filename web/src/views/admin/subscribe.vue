<template>
    <div class="goodsindex">
      <!-- Application List -->
      <el-row style="padding: 10px;" :gutter="20" class="filter-bar">
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input 
            v-model="searchParams.aname"
            placeholder="Search by name..."
            clearable 
            size="small"
            @keyup.enter.native="loadApplications"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-button 
            type="primary" 
            size="small"
            @click="loadApplications">
            Search
          </el-button>
        </el-col>
      </el-row>
        <el-table :data="applications" v-loading="loading">
          <el-table-column prop="aname" label="Travel Name" width="500"/>
          <el-table-column label="Application Content">
            <template slot-scope="{row}">
              <div class="remark">{{ row.remark || 'No remarks' }}</div>
            </template>
          </el-table-column>
          <el-table-column label="Applied Time" width="300">
            <template slot-scope="{row}">
              <div class="time-info">
                <div>{{ formatTime(row.createdTime) }}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="Status" width="220">
            <template slot-scope="{row}">
              <el-tag :type="statusType[row.status]">
                {{ row.status}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Actions" width="300">
            <template slot-scope="{row}">
              <el-button 
                :disabled="row.status !== 'Already subscribe'"
                type="primary"
                size="mini"
                @click="showReviewDialog(row)">
                Review
              </el-button>
              <el-button
                :disabled="row.status !== 'Already subscribe'"
                type="danger"
                size="mini"
                @click="cancelApplication(row.id)">
                Cancel
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
  class="pagination-bar"
/>
      <!-- Review Dialog -->
      <el-dialog
        title="Event Review"
        :visible.sync="reviewVisible"
        width="1200px">
        <el-form :model="reviewForm" label-width="200px">
          <el-form-item label="Rating" required>
            <el-rate
              v-model="reviewForm.rate"
              :max="5"
              show-score
              style="margin-top: 10px"/>
          </el-form-item>
          
          <el-form-item label="Review Content">
            <el-input
              v-model="reviewForm.content"
              type="textarea"
              :rows="4"
              placeholder="Please share your experience"/>
          </el-form-item>
  
          <el-form-item label="Upload Image">
            <el-upload
              action="http://localhost:9001/file/upload"
              :show-file-list="false"
              :on-success="handleUploadSuccess">
              <el-button size="small" type="primary">
                Upload Image
              </el-button>
              <div v-if="reviewForm.imageUrl" class="preview">
                <img :src="reviewForm.imageUrl" class="preview-img">
                <i class="el-icon-delete" @click.stop="reviewForm.imageUrl = ''"/>
              </div>
            </el-upload>
          </el-form-item>
        </el-form>
  
        <div slot="footer">
          <el-button @click="reviewVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submitReview">
            Submit
          </el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import apis from '@/apis/subscribe'
  import evaapis from '@/apis/eva'
  import moment from 'moment'
  
  export default {
    data() {
          return {
            searchParams: { createdId: "", aname: "" },
            user:null,
            loading: false,

            pagination: { // 统一分页参数结构
      page: 1,
      pageSize: 10,
      total: 0
    },
            applications: [],
            reviewVisible: false,
            currentApp: null,
            reviewForm: {
            aid: '',
            rate: 0,
            content: '',
            imageUrl: ''
                },
                item1: {},
            statusMap: {
            '0': 'Already subscribe',
            '1': 'Finish'
            },
            statusType: {
            '0': 'success',
            '1': 'info'
                },
            }
    },
  
    mounted() {
      this.user = JSON.parse(this.$store.getStore('user'))
      this.loadApplications()
    },
  
    methods: {
      // Load applications with pagination
      async loadApplications() {
        try {
            this.loading = true
            this.searchParams.createdId = this.user.id
            const { data } = await apis.list(this.searchParams,
                this.pagination.page, this.pagination.pageSize)
          this.applications = data.records
          this.pagination.total = data.total
        } finally {
          this.loading = false
        }
      },
  
      // Cancel application
      async cancelApplication(row) {
  try {
    // 获取对应申请记录
    const application = row

    // 计算时间差（天）
    const eventTime = moment(application.stime)  // 确保字段名正确，原代码中使用的是startedTime
    const daysDiff = eventTime.diff(moment(), 'days')

    // 判断是否允许取消
    if (daysDiff <= 3) {
      this.$message.warning('Cannot cancel within 3 days of event start')
      return
    }

    // 确认对话框
    await this.$confirm('Are you sure to cancel this application?', 'Confirm', { 
      type: 'warning',
      confirmButtonText: 'Confirm',
      cancelButtonText: "Cancel"
    })
    
    // 执行取消操作
    await apis.deleteById(id)
    this.$message.success("Cancellation successful")
    this.loadApplications()
  } catch (error) {
    // 捕获用户取消对话框的情况
    if (error !== 'cancel') {
      console.error(error)
      this.$message.error("Cancellation failed")
    }
  }
},
  
      // Show review dialog
      showReviewDialog(row) {
        this.currentApp = JSON.parse(JSON.stringify(row))
        this.reviewForm = {
          aid: row.aid,
          rate: 0,
          content: '',
          imageUrl: ''
        }
        this.reviewVisible = true
      },
  
      // Submit review
      async submitReview() {
        try {
            // Submit review
            this.reviewForm.cid = this.currentApp.uid
            await evaapis.save(this.reviewForm)
            this.currentApp.status = "finish"
          await apis.update(this.currentApp)
          this.$message.success("Review submitted successfully!")
          this.reviewVisible = false
          this.loadApplications()
        } catch (error) {
          this.$message.error("Failed to submit review")
          console.error(error)
        }
      },
  
      // Handle image upload
      handleUploadSuccess(res) {
        if (res.code === 200) {
          this.reviewForm.imageUrl = res.data.url
        }
      },
  
      // Check if review needed
      isNeedReview(row) {
        return row.status === '0' && 
          moment(row.startedTime).isBefore(moment()) &&
          !row.reviewed
      },
  
      // Format timestamps
      formatTime(time) {
        return moment(time).format('YYYY-MM-DD HH:mm')
      },
  
      // Pagination handlers
      handlePageChange(page) { // 统一方法命名
  this.pagination.page = page
  this.loadApplications()
}
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
  
  .remark {
    color: #666;
    line-height: 1.6;
  }
  
  .time-info {
    font-size: 12px;
    color: #999;
  }
  
  .preview {
    position: relative;
    margin-top: 10px;
    width: 100px;
  }
  
  .preview-img {
    width: 100%;
    height: 100px;
    object-fit: cover;
    border-radius: 4px;
  }
  
  .el-icon-delete {
    position: absolute;
    right: -10px;
    top: -10px;
    cursor: pointer;
    color: #f56c6c;
    background: white;
    border-radius: 50%;
  }
  .pagination-bar {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
  </style>