<template>
  <div class="activity-management">
    <!-- Search Criteria -->
    <el-row :gutter="20" class="filter-bar">
      <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
        <el-input 
          v-model="searchParams.name"
          placeholder="Activity name"
          clearable 
          size="small"
          @keyup.enter.native="loadData"/>
      </el-col>
      <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
        <el-input
          v-model="searchParams.address"
          placeholder="Location"
          clearable
          size="small"/>
      </el-col>
      <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
        <el-select 
          v-model="searchParams.status" 
          placeholder="Status"
          clearable
          size="small">
          <el-option label="Pending" value="pending"/>
          <el-option label="Approved" value="approve"/>
          <el-option label="Rejected" value="reject"/>
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
      <el-table-column prop="bqn" label="Tags" width="200" />
      <el-table-column prop="num" label="Capacity" width="120" align="center"/>
      <el-table-column prop="ys" label="Budget" width="150">
        <template slot-scope="{row}">
          ¥{{ row.ys?.toLocaleString() }}
        </template>
      </el-table-column>
      <el-table-column prop="stime" label="Date" width="220">
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
      <el-table-column fixed="right" label="Actions" width="300" align="center">
        <template slot-scope="scope">
          <el-button 
            type="primary" 
            size="mini"
            @click="openEditDialog(scope.row)">
            Edit
          </el-button>
          <el-button 
            type="danger" 
            size="mini"
            @click="confirmDelete(scope.row)">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog 
      title="Edit Activity"
      :visible.sync="showEditDialog"
      width="1200px">
      <el-form 
        ref="editForm"
        :model="currentItem"
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
          <img v-if="currentItem.imageUrl" :src="currentItem.imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
        <el-form-item
          label="Activity Name"
          prop="name"
          :rules="[{ required: true, message: 'Name is required' }]">
          <el-input v-model="currentItem.name"/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">         
        <el-form-item 
          label="Activity Tag"
          prop="bq">
          <el-select
            v-model="currentItem.bq" placeholder="please select..."
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
                v-model="currentItem.num"
                :min="1"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item
          label="Location"
          prop="address"
          :rules="[{ required: true, message: 'Location is required' }]">
          <el-input v-model="currentItem.address"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item
              label="Date & Time"
              prop="stime"
              :rules="[{ required: true, message: 'Date is required' }]">
              <el-date-picker
                v-model="currentItem.stime"
                type="datetime"
                value-format="timestamp"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="Total Budget ($)"
              prop="ys"
              :rules="[{ required: true, message: 'Budget is required' }]">
              <el-input-number 
                v-model="currentItem.ys"
                :min="100"
                :step="100"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="Description">
          <el-input
            v-model="currentItem.js"
            type="textarea"
            :rows="4"/>
        </el-form-item>
      </el-form>
      
      <div slot="footer">
        <el-button @click="showCreate = false">Cancel</el-button>
        <el-button 
          type="primary"
          @click="submitEdit">
          Save
        </el-button>
      </div>
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
import typeApi from '@/apis/type'
import { formatDate } from '@/utils/date'

export default {
  data() {
    return {
      isLoading: false,
      searchParams: {
        name: '',
        address: '',
        status: '',
        bq: ''
      },
      user:null,
      tagOptions: [
      ],
      showEditDialog: false,
      currentItem: {},
      editData: {
        id: '',
        name: '',
        address: '',
        bq: [],
        num: 0,
        ys: 0,
        stime: 0
      },
      formRules: {
        name: [{ required: true, message: 'Name is required', trigger: 'blur' }],
        address: [{ required: true, message: 'Address is required', trigger: 'blur' }],
        num: [{ type: 'number', required: true, message: 'Capacity is required' }],
        stime: [{ required: true, message: 'Date is required', trigger: 'change' }]
      },
      activityList: [],
      pagination: {
        page: 1,
        pageSize: 10,
        total: 0
      }
    }
  },

  methods: {
    getTypes() {
      typeApi.list({}, 1, -1).then(res => {
        this.tagOptions = res.data.records
      })   
    },
    async loadData() {
      try {
        this.isLoading = true
        this.searchParams.createdId = this.user.id
        const res = await activityApi.list(
          this.searchParams,
          this.pagination.page,
          this.pagination.pageSize
        )
        this.activityList = res.data.records
        this.pagination.total = res.data.total
      } finally {
        this.isLoading = false
      }
    },

    openEditDialog(item) {
      this.currentItem = JSON.parse(JSON.stringify(item))
      this.currentItem.ys = this.currentItem.ys / 100
      this.showEditDialog = true
    },

    async submitEdit() {
      try {
        await this.$refs.editForm.validate()
        
        const payload = {
          ...this.currentItem,
          modifiedId: this.user.id
        }

        await activityApi.update(payload)
        this.$message.success('Activity updated')
        this.showEditDialog = false
        this.loadData()
      } catch (error) {
        console.error('Update failed:', error)
      }
    },

    confirmDelete(item) {
      this.$confirm('Permanently delete this activity?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(async () => {
        await activityApi.deleteById(item.id)
        this.$message.success('Activity deleted')
        this.loadData()
      }).catch(() => {})
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
    
    handleUploadSuccess(res) {
      this.currentItem.imageUrl = res.data.url
      this.$forceUpdate()
    },
    
    beforeUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 15
      if (!isLt2M) {
        this.$message.error('Image size must be less than 15MB!')
      }
      return isLt2M
    },
    handlePageChange(page) {
      this.pagination.page = page
      this.loadData()
    }
  },

  mounted() {
    this.user = JSON.parse(this.$store.getStore('user'))
    this.loadData()
    this.getTypes()
  }
}
</script>

<style lang="less" scoped>
.activity-management {
  padding: 20px;
  background: #f5f7fa;
  min-height: calc(100vh - 60px);
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
  justify-content: center;
}

.dialog-actions {
  margin-top: 24px;
  text-align: right;
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
</style>