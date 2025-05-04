<template>
  <div class="activity-container">
    
    <el-row :gutter="20" class="filter-bar">
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input 
            v-model="searchParams.name"
            placeholder="Search by name..."
            clearable 
            size="small"
            @keyup.enter.native="loadActivities"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input
            v-model="searchParams.address"
            placeholder="Search by address..."
            clearable
            size="small"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input
            v-model="lys"
            placeholder="Search by budget..."
            clearable
            size="small"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input
            v-model="yys"
            placeholder="Search by budget..."
            clearable
            size="small"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-select
            v-model="searchParams.bq"
            placeholder="Filter by tag"
            clearable
            size="small">
            <el-option 
              label="ALL"
              value=""/>
            <el-option 
              v-for="tag in tagOptions"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id"/>
          </el-select>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-button 
            type="primary" 
            size="small"
            @click="loadActivities">
            Search
          </el-button>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
      <el-button 
        type="primary" 
        icon="el-icon-plus"
            size="small"
        @click="showCreateDialog">
        New Activity
      </el-button>
        </el-col>
      </el-row>
  

    <!-- 活动卡片列表 -->
    <el-row :gutter="20" class="activity-list">
      <el-col 
        v-for="activity in activities"
        :key="activity.id"
        :xs="24" :sm="12" :md="8" :lg="6">
        <el-card class="activity-card">
          <div class="cover-image">
    <el-image 
      :src="activity.imageUrl"
      fit="cover"
      :preview-src-list="[activity.imageUrl]"
      :z-index="9999">
      <div slot="error" class="image-error">
        <i class="el-icon-picture-outline"></i>
        <span>Image not found</span>
      </div>
    </el-image>
  </div>
          <div slot="header" class="card-header">
            <span class="activity-name">{{ activity.name }}</span>
          </div>
          
          <div class="card-body">
            <div class="info-item">
              <i class="el-icon-location"></i>
              {{ activity.address }}
            </div>
            <div class="info-item">
              <i class="el-icon-time"></i>
              {{ formatDate(activity.stime) }}
            </div>
            <div class="progress-wrapper">
              <el-progress 
                :percentage="calculateProgress(activity.ys, activity.yys)"
                :color="getProgressColor(activity.ss)"
                :show-text="false"/>
              <div class="budget-info">
                <span>Budget: ${{ activity.ys / 100 }}</span>
                <span>Used: ${{ activity.yys }}</span>
              </div>
            </div>
          </div>
          
          <div class="card-footer">
            <el-button 
              type="text" 
              @click="viewDetail(activity.id)">
              Details <i class="el-icon-arrow-right"></i>
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 创建活动弹窗 -->
    <el-dialog 
      title="Create New Activity"
      :visible.sync="showCreate"
      width="1200px">
      <el-form 
        ref="createForm"
        :model="newActivity"
        label-width="120px"
        label-position="top">
              <!-- 新增图片上传 -->
      <el-form-item label="Cover Image" prop="imageUrl">
        <el-upload
          class="avatar-uploader"
          action="http://localhost:9001/file/upload"
          :show-file-list="false"
          :on-success="handleUploadSuccess"
          :before-upload="beforeUpload">
          <img v-if="newActivity.imageUrl" :src="newActivity.imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
        <el-form-item
          label="Activity Name"
          prop="name"
          :rules="[{ required: true, message: 'Name is required' }]">
          <el-input v-model="newActivity.name"/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">         
        <el-form-item 
          label="Activity Tag"
          prop="bq">
          <el-select  placeholder="please select..."
            v-model="newActivity.bq"
            size="small">
            <el-option 
              v-for="tag in tagOptions"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id"/>
          </el-select>
        </el-form-item>
          </el-col>
          <el-col :span="12">
            
        <el-form-item
              label="Number of People"
              prop="number">
              <el-input-number 
                v-model="newActivity.num"
                :min="1"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item
          label="Location"
          prop="address"
          :rules="[{ required: true, message: 'Location is required' }]">
          <el-input v-model="newActivity.address"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item
              label="Date & Time"
              prop="stime"
              :rules="[{ required: true, message: 'Date is required' }]">
              <el-date-picker
                v-model="newActivity.stime"
                type="datetime"
                value-format="timestamp"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="Total Budget ($)"
              prop="ys">
              <el-input-number 
                v-model="newActivity.ys"
                :min="100"
                :step="100"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="Description">
          <el-input
            v-model="newActivity.js"
            type="textarea"
            :rows="4"/>
        </el-form-item>
      </el-form>
      
      <div slot="footer">
        <el-button @click="showCreate = false">Cancel</el-button>
        <el-button 
          type="primary"
          @click="createActivity">
          Create
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import actApi from '@/apis/act'
import typeApi from '@/apis/type'

export default {
  data() {
    return {
        searchParams: {
          name: '',
          address: '',
          status: '',
          bq: '',
        lys: null,
        yys: null,
          
        },
      user: null,
      searchKey: '',
      showCreate: false,
      activities: [],
      newActivity: {
        name: '',
        imageUrl:"",
        address: '',
        stime: null,
        ys: 1000,
        js: ''
      },
      tagOptions: [],
      lys: 0,
      yys:0
    }
  },
  mounted() {
    this.user = JSON.parse(this.$store.getStore('user'))
    this.loadActivities()
    this.getTypes()
  },
  methods: {
    getTypes() {
      typeApi.list({}, 1, -1).then(res => {
        this.tagOptions = res.data.records
      })   
    },
    handleUploadSuccess(res) {
      this.newActivity.imageUrl = res.data.url
      this.$forceUpdate()
    },
    
    beforeUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 15
      if (!isLt2M) {
        this.$message.error('Image size must be less than 15MB!')
      }
      return isLt2M
    },
    async loadActivities() {
      try {
        if (this.lys !== 0)
          this.searchParams.lys = this.lys * 100
        if (this.yys !== 0)
          this.searchParams.yys = this.yys * 100
        this.searchParams.ss = "approve"
        const res = await actApi.list(this.searchParams, 1, 10)
        this.activities = res.data.records
      } catch (error) {
        this.$message.error('Failed to load activities')
      }
    },
    
    getStatusType(status) {
      const types = { '0': 'warning', '1': 'success', '2': 'danger' }
      return types[status] || 'info'
    },

    calculateProgress(total, used) {
      if (!total || total === 0) return 0
      return Math.round((used / total) * 100)
    },

    getProgressColor(status) {
      return this.getStatusType(status)
    },

    formatDate(timestamp) {
      if (!timestamp) return 'N/A'
      const options = { 
        year: 'numeric', 
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      }
      return new Date(timestamp).toLocaleString('en-US', options)
    },

    viewDetail(id) {
      this.$router.push({
        path: "/act-detail",
        query:{id:id}
      })
    },

    showCreateDialog() {
      this.showCreate = true
      this.$nextTick(() => {
        this.$refs.createForm.resetFields()
      })
    },

    async createActivity() {
      try {
        await this.$refs.createForm.validate()
        await actApi.save({
          ...this.newActivity,
          createdId: this.user.id
        })
        
        this.$message.success('Activity created!')
        this.showCreate = false
        this.loadActivities()
      } catch (error) {
        console.error('Create error:', error)
      }
    }
  }
}
</script>

<style lang="less" scoped>
.activity-container {
  max-width: 95%;
  margin: 0 auto;
  padding: 20px;
}
/* 封面图样式 */
.cover-image {
  height: 200px;
  overflow: hidden;
  border-radius: 4px 4px 0 0;
  background-color: #f5f7fa;
  .el-image {
    width: 100%;
    height: 100%;
    transition: transform 0.3s;
    &:hover {
      transform: scale(1.05);
    }
  }
}

/* 错误状态 */
.image-error {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #909399;
  i {
    font-size: 40px;
    margin-bottom: 10px;
  }
}
.filter-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 30px;
}
/* 新增上传组件样式 */
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 200px;
  height: 200px;
}

.avatar-uploader:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 200px;
  line-height: 200px;
  text-align: center;
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.search-input {
  width: 300px;
}

.activity-list {
  min-height: 500px;
}

.activity-card {
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.activity-card:hover {
  transform: translateY(-5px);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;
}

.activity-name {
  font-weight: 600;
  color: #1a6f57;
}

.card-body {
  padding: 10px 0;
}
::v-deep .v-md-editor {
  .upload-image-wrapper {
    position: relative;
    &:hover::after {
      content: "点击上传图片";
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      background: rgba(0,0,0,0.7);
      color: white;
      padding: 5px 10px;
      border-radius: 4px;
    }
  }
}
.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #666;
  margin-bottom: 12px;
}

.progress-wrapper {
  margin-top: 15px;
}

.budget-info {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #888;
  margin-top: 5px;
}

.card-footer {
  border-top: 1px solid #eee;
  padding-top: 15px;
  text-align: right;
}
</style>