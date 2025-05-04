<template>
    <div class="goodsindex">
      <!-- Search Bar -->
      <el-row :gutter="20" class="filter-bar">
        <el-col :span="8">
          <el-input
            v-model="searchParams.bname"
            placeholder="Search by blog title"
            clearable
            size="small"
            @keyup.enter.native="loadComments"
            @clear="loadComments">
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary"
            size="small" @click="loadComments">Search</el-button>
        </el-col>
      </el-row>
  
      <!-- Comment Table -->
      <el-table
        v-loading="loading"
        :data="comments"
        border
        style="width: 100%"
        :header-cell-style="{ background: '#f5f7fa' }">
        <el-table-column prop="bname" label="Post" width="220" />
        <el-table-column prop="createdName" label="Creator" width="150" />
        <el-table-column label="Content" show-overflow-tooltip>
          <template slot-scope="{ row }">
            <div class="content-preview">{{ row.content }}</div>
          </template>
        </el-table-column>
        <el-table-column label="Created Time" width="220">
          <template slot-scope="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Actions" width="300">
          <template slot-scope="{ row }">
            <el-button
              type="primary"
              size="mini"
              @click="showDetail(row)">
              Detail
            </el-button>
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(row.id)">
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- Pagination -->
      <el-pagination
        background
        layout="total, prev, pager, next"
        :current-page="pagination.current"
        :page-size="pagination.size"
        :total="pagination.total"
        @current-change="handlePageChange"
        class="pagination-bar"/>
  
      <!-- Detail Dialog -->
      <el-dialog :title="currentComment.bname" :visible.sync="detailVisible" width="50%">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="Post">{{ currentComment.bname }}</el-descriptions-item>
          <el-descriptions-item label="Creator">{{ currentComment.createdName }}</el-descriptions-item>
          <el-descriptions-item label="Content">
            <div class="comment-content">{{ currentComment.content }}</div>
          </el-descriptions-item>
          <el-descriptions-item label="Images" v-if="currentComment.imageUrl">
            <div class="image-container">
              <el-image
                v-for="(img, index) in imageList"
                :key="index"
                :src="img"
                :preview-src-list="imageList"
                fit="cover"
                class="comment-image">
              </el-image>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="Created Time">
            {{ formatDate(currentComment.createdTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="Last Updated">
            {{ formatDate(currentComment.modifiedTime) }}
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import commentApi from '@/apis/comments'
  
  export default {
    data() {
      return {
        loading: false,
        detailVisible: false,
        currentComment: {},
        searchParams: {
          bname: ''
        },
        comments: [],
        pagination: {
          current: 1,
          size: 10,
          total: 0
        }
      }
    },
  
    computed: {
      imageList() {
        return this.currentComment.imageUrl 
          ? this.currentComment.imageUrl.split(',') 
          : []
      }
    },
  
    mounted() {
      this.loadComments()
    },
  
    methods: {
      async loadComments() {
        try {
          this.loading = true
          const res = await commentApi.list(
            { ...this.searchParams },
            this.pagination.current,
            this.pagination.size
          )
          this.comments = res.data.records
          this.pagination.total = res.data.total
        } catch (error) {
          this.$message.error('Failed to load comments')
        } finally {
          this.loading = false
        }
      },
  
      showDetail(comment) {
        this.currentComment = { ...comment }
        this.detailVisible = true
      },
  
      handleDelete(id) {
        this.$confirm('This will permanently delete the comment. Continue?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(async () => {
          await commentApi.deleteById(id)
          this.$message.success('Comment deleted')
          this.loadComments()
        }).catch(() => {
          this.$message.info('Deletion canceled')
        })
      },
  
      handlePageChange(page) {
        this.pagination.current = page
        this.loadComments()
      },
  
      formatDate(date) {
        return date ? new Date(date).toLocaleString() : '--'
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
  .filter-bar {
    margin-bottom: 20px;
  }
  .pagination-bar {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end; /* 替换原来的text-align: center */
}
  .content-preview {
    max-height: 60px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .comment-content {
    white-space: pre-wrap;
    line-height: 1.6;
  }
  
  .image-container {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
  }
  
  .comment-image {
    width: 120px;
    height: 120px;
    border-radius: 4px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  </style>