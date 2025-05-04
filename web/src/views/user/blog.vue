<template>
  <div class="blog-home-container">
    <!-- Search & New Blog -->
    <el-card class="search-box">
      <el-row :gutter="40">
        <el-col :span="20">
          <el-input
            v-model="searchQuery"
            placeholder="Search blog posts..."
            clearable
            @keyup.enter.native="handleSearch"
          >
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button 
            type="primary" 
            icon="el-icon-document-add"
            @click="toNew"
            class="new-btn"
          >
            New Post
          </el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- Blog Cards -->
    <div class="card-list" v-loading="loading">
      <el-card 
        v-for="blog in filteredBlogs" 
        :key="blog.id"
        class="blog-card"
        shadow="hover"
      >
        <div @click="gotoPage(blog.id)" class="card-content">
            <h3>{{ blog.name }}</h3>
          <div class="preview-text">
            {{ blog.content.substring(0, 150) }}...
          </div>

          <div class="meta-info">
            <el-avatar :size="32" class="author-avatar">{{ blog.createdName[0] }}</el-avatar>
            <div class="author-details">
              <span class="author-name">By {{ blog.createdName }}</span>
              <span class="post-date">{{ formatDate(blog.createdTime) }}</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- Pagination -->
    <el-pagination
      background
      :current-page="currentPage"
      :page-size="pageSize"
      :total="totalBlogs"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      class="blog-pagination"
    />
  </div>
</template>
  <script>
  import blogApis from '@/apis/blog';
  import moment from 'moment';
  
  export default {
    data() {
      return {
        searchQuery: '',
        blogs: [],
        filteredBlogs: [],
        loading: false,
        currentPage: 1,
        pageSize: 2,
        totalBlogs: 0
      };
    },
  
    mounted() {
      this.fetchBlogs();
    },
  
    methods: {
      gotoPage(id) {
        this.$router.push({ path: "/blog-detail", query: {id:id} })   
      },
      toNew() {
     this.$router.push("/blog-w")   
      },
      async fetchBlogs() {
        try {
          this.loading = true;
          const { data } = await blogApis.list({name:this.searchQuery
          },this.currentPage,this.pageSize);
          
          this.blogs = data.records;
          this.totalBlogs = data.total;
          this.filterBlogs();
        } catch (error) {
          console.error('Error fetching blogs:', error);
        } finally {
          this.loading = false;
        }
      },
  
      filterBlogs() {
        this.filteredBlogs = this.blogs.filter(blog =>
          blog.name.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
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
  <style lang="less" scoped>
  .blog-home-container {
    max-width: 95%;
    margin: 0 auto;
    padding: 20px;
  
    .search-box {
      margin-bottom: 30px;
      border: none;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  
      .new-btn {
        width: 100%;
        padding: 12px;
      }
    }
  
    .card-list {
      .blog-card {
        margin-bottom: 20px;
        transition: transform 0.2s;
  
        &:hover {
          transform: translateY(-3px);
        }
  
        .card-content {
          padding: 20px;
  
          .title-link {
            text-decoration: none;
            
            h3 {
              color: #2c3e50;
              margin: 0 0 12px;
              font-size: 20px;
  
              &:hover {
                color: #007bff;
              }
            }
          }
  
          .preview-text {
            color: #666;
            font-size: 15px;
            line-height: 1.6;
            margin-bottom: 15px;
          }
  
          .meta-info {
            display: flex;
            align-items: center;
            border-top: 1px solid #eee;
            padding-top: 15px;
  
            .author-avatar {
              background-color: #007bff;
              margin-right: 12px;
            }
  
            .author-details {
              display: flex;
              flex-direction: column;
  
              .author-name {
                font-weight: 500;
                font-size: 14px;
              }
  
              .post-date {
                color: #8a8a8a;
                font-size: 13px;
              }
            }
          }
        }
      }
    }
  
    .blog-pagination {
      margin-top: 30px;
      /deep/ .el-pager li {
        border-radius: 4px;
        margin: 0 4px;
      }
    }
  }
  </style>