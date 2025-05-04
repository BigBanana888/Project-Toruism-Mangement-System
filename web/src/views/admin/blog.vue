<template>
  <div class="goodsindex">
    <!-- 搜索栏 -->
    <el-row :gutter="20" class="filter-bar">
      <el-col :xs="16" :sm="12" :md="10" :lg="8" :xl="6">
        <el-input 
          v-model="searchKey"
          placeholder="Search blogs..."
          clearable 
          size="small"
          @keyup.enter.native="loadData">
        </el-input>
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

    <!-- 数据表格 -->
    <el-table
      v-loading="isLoading"
      :data="blogList"
      border
      size="small"
      style="width: 100%"
      :header-cell-style="{ 'text-align': 'center', background: '#f5f7fa' }"
      :cell-style="{ 'text-align': 'center' }">
      <el-table-column 
          prop="id" 
          label="ID" 
          width="220"
          show-overflow-tooltip/>
        <el-table-column 
          prop="name" 
          label="Notice Title" 
          width="180"
          show-overflow-tooltip/>
        <el-table-column 
          prop="content" 
          label="Content"
          show-overflow-tooltip/>
        <el-table-column 
          prop="createdTime" 
          label="Created Time" 
          width="220">
          <template slot-scope="scope">
            {{ scope.row.createdTime | dateFormat }}
          </template>
        </el-table-column>
        <el-table-column 
          prop="createdName" 
          label="Creator" 
          width="120"/>
        <el-table-column 
          prop="modifiedTime" 
          label="Updated Time" 
          width="220">
          <template slot-scope="scope">
            {{ scope.row.modifiedTime | dateFormat }}
          </template>
        </el-table-column>
        <el-table-column 
          prop="modifiedName" 
          label="Updater" 
          width="120"/>
      <el-table-column 
        fixed="right" 
        label="Actions" 
        width="360" 
        align="center">
        <template slot-scope="scope">
          <el-button 
            @click="showDetail(scope.row)" 
            type="primary" 
            size="mini">
            Detail
          </el-button>
          <el-button 
            @click="deleteItem(scope.row)" 
            type="danger" 
            size="mini">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 详情弹窗 -->
    <el-dialog 
      title="Content Details" 
      :visible.sync="detailVisible" 
      width="60%"
      top="5vh">
      <div class="blog-detail">
        <el-row :gutter="32">
          <el-col :span="16">
            <div class="content-section">
              <h3 class="detail-title">Title:{{currentBlog.name}}</h3>
              <v-md-editor 
      :value="currentBlog.content" 
      mode="preview"
      class="preview-editor"
    />
            </div>
          </el-col>
          <el-col :span="8">
            <div class="meta-section">
              <el-descriptions :column="1" border>
                <el-descriptions-item label="Creator">
                  {{ currentBlog.createdName }}
                </el-descriptions-item>
                <el-descriptions-item label="Created Time">
                  {{ currentBlog.createdTime  }}
                </el-descriptions-item>
                <el-descriptions-item label="Updater">
                  {{ currentBlog.modifiedName }}
                </el-descriptions-item>
                <el-descriptions-item label="Updated Time">
                  {{ currentBlog.modifiedTime  }}
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-dialog>

    <!-- 分页 -->
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
<style lang="less" scoped>

  
.goodsindex{
    min-height: 100%;
    padding: 15px;
    box-sizing: border-box;
    background-color:white;
    margin: 20px;
}
.blog-detail {
  padding: 20px;
  max-height: 70vh;
  overflow-y: auto;

  .detail-title {
    color: #303133;
    margin-bottom: 20px;
  }

  .markdown-content {
    padding: 15px;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    min-height: 300px;
  }

  .meta-section {
    padding: 15px;
    background: #f8f9fa;
    border-radius: 4px;
  }
}

::v-deep .el-descriptions__label {
  width: 100px;
  font-weight: 500;
}
</style>
<script>
import blogApi from '@/apis/blog'

export default {
  mounted(){
this.loadData()
  },
  data() {
    return {
      isLoading: true,
      searchKey: '',
      detailVisible: false,
      currentBlog: {},
      blogList: [],
      pagination: {
        page: 1,
        pageSize: 10,
        total: 0
      }
    }
  },

  methods: {
    showDetail(blog) {
      this.currentBlog = { ...blog }
      this.detailVisible = true
    },

    async deleteItem(item) {
      try {
        await this.$confirm('This will permanently delete the blog. Continue?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        })
        
        await blogApi.deleteById(item.id)
        this.$message.success('Blog deleted')
        this.loadData()
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('Delete failed')
        }
      }
    },
      async loadData() {
        try {
          this.isLoading = true
          const res = await blogApi.list({
            name: this.searchKey
          }, this.pagination.page, this.pagination.pageSize)
  
          this.blogList = res.data.records
          this.pagination.total = res.data.total
        } finally {
          this.isLoading = false
        }
      },
  
      handlePageChange(page) {
        this.pagination.page = page
        this.loadData()
      }
    }
  }
  </script>
  
  <style scoped>
  .blog-management {
    padding: 20px;
    background: #f5f7fa;
    min-height: calc(100vh - 60px);
  }
  
  .filter-bar {
    margin-bottom: 20px;
    display: flex;
    gap: 15px;
  }
  
  .el-table {
    margin: 20px 0;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  .pagination-bar {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
  
  .el-form-item {
    margin-bottom: 22px;
  }
  </style>