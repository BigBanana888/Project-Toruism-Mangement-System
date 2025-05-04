<template>
    <div class="blog-list-container">
      <!-- Search Section -->
      <el-card class="search-box">
    <el-row :gutter="20" class="filter-bar" >
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
        <el-input 
          v-model="searchQuery.name"
          placeholder="Search blogs by title..."
          clearable
          @clear="handleSearch"
          @keyup.enter.native="handleSearch"
        >
        </el-input>
      </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
      <el-button 
        type="primary" 
        icon="el-icon-plus"
        @click="handleSearch">
        Search
      </el-button>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
      <el-button 
        type="primary" 
        icon="el-icon-plus"
        @click="toNew">
        New Blog
      </el-button>
        </el-col>
          </el-row>
      </el-card>
  
      <!-- Blog List -->
      <el-card class="blog-list">
        <el-table 
          :data="blogs" 
          style="width: 100%"
          v-loading="loading"
        >
          <el-table-column label="Title" width="300">
            <template slot-scope="{row}">
                {{ row.name }}
              <div class="meta-info">
                <span class="author">By {{ row.createdName }}</span>
                <el-divider direction="vertical"/>
                <span class="time">{{ formatDate(row.createdTime) }}</span>
              </div>
            </template>
          </el-table-column>
  
          <el-table-column label="Preview">
            <template slot-scope="{row}">
              <div
              >{{ row.content.substring(0, 150) + '...' }}</div>
            </template>
          </el-table-column>
  
          <el-table-column label="Last Updated" width="180">
            <template slot-scope="{row}">
              <div class="update-info">
                <span class="time">{{ formatDate(row.modifiedTime) }}</span>
                <div class="editor">Edited by: {{ row.modifiedName }}</div>
              </div>
            </template>
         
          </el-table-column>
          <el-table-column label="Operation" width="180" align="center">
          <template slot-scope="{row}">
            <el-button
              type="primary"
              size="mini"
              @click="handleEdit(row.id)"
            >
              Update
            </el-button>
            
            <el-popconfirm
              title="Are you sure to delete this blog?"
              confirm-button-text="Confirm"
              cancel-button-text="Cancel"
              @confirm="handleDelete(row.id)"
            >
              <el-button
                slot="reference"
                type="danger"
                size="mini"
                style="margin-left: 8px"
              >
                Delete
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
        </el-table>
  
        <!-- Pagination -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalBlogs"
        />
      </el-card>
    </div>
  </template>
  
  <script>
  import blogApis from '@/apis/blog';
  import moment from 'moment';
  
  export default {
    data() {
      return {
        searchQuery: {createdId:"",name:""},
        blogs: [],
        loading: false,
        currentPage: 1,
        pageSize: 10,
        totalBlogs: 0,
        user:null
      };
    },
  
    mounted() {
      this.user = JSON.parse(this.$store.getStore('user'))
      this.fetchBlogs();
    },
  
    methods: {
      toNew() {
     this.$router.push("/blog-w")   
      },
      async fetchBlogs() {
        try {
          this.loading = true;
          this.searchQuery.createdId = this.user.id
          const { data } = await blogApis.list(this.searchQuery
          ,this.currentPage,this.pageSize);
          console.log(this.blogs)
          this.blogs = data.records;
          this.totalBlogs = data.total;
        } catch (error) {
          console.error('Error fetching blogs:', error);
        } finally {
          this.loading = false;
        }
      },
    // 新增的方法
    handleEdit(blogId) {
      this.$router.push({
        path: '/blog-u',
        query: { id: blogId }
      });
    },

    async handleDelete(blogId) {
      try {
        this.loading = true;
        await blogApis.deleteById(blogId);
        
        this.$message.success('Delete Success');
        // 重新获取数据（保持当前分页）
        this.fetchBlogs();
      } catch (error) {
        console.error('Delete Fail:', error);
        this.$message.error('Delete Fail:');
      } finally {
        this.loading = false;
      }
    },

      handleSearch() {
        this.currentPage = 1;
        this.fetchBlogs();
      },
  
      handleSizeChange(size) {
        this.pageSize = size;
        this.fetchBlogs();
      },
  
      handleCurrentChange(page) {
        this.currentPage = page;
        this.fetchBlogs();
      },
  
      formatDate(date) {
        return moment(date).format('MMM D, YYYY HH:mm');
      }
    }
  };
  </script>
  
  <style scoped>
  .blog-list-container {
    max-width: 95%;
    margin: 0 auto;
    padding: 20px;
  }
  
  .search-box {
    margin-bottom: 20px;
  }
  
  .blog-title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    text-decoration: none;
  }
  
  .meta-info {
    margin-top: 8px;
    font-size: 12px;
    color: #909399;
  }
  
  .content-preview {
    max-height: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .update-info {
    font-size: 12px;
    color: #909399;
  }
  
  .editor {
    margin-top: 4px;
    color: #606266;
  }
  </style>