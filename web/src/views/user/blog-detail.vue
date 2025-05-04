<template>
  <div class="blog-detail-container" v-loading="loading">
    <!-- Back Navigation -->
    <div class="nav-back">
      <el-button 
        type="text" 
        icon="el-icon-arrow-left" 
        @click="$router.go(-1)"
      >
        Back to List
      </el-button>
    </div>

    <!-- Main Content -->
    <el-card class="blog-card">
      <h1 class="blog-title">{{ blog.name }}</h1>
      <!-- Blog Content -->
      <v-md-editor 
        v-model="blog.content" 
        mode="preview"
        class="markdown-content"
      />

      <!-- Author Info -->
      <div class="author-section">
        <el-avatar class="author-avatar">{{ authorInitial }}</el-avatar>
        <div class="author-details">
          <h3 class="author-name">{{ blog.createdName }}</h3>
          <div class="timestamps">
            <span class="created-time">
              Published: {{ formatDate(blog.createdTime) }}
            </span>
            <span v-if="blog.modifiedTime" class="updated-time">
              | Updated: {{ formatDate(blog.modifiedTime) }}
            </span>
          </div>
        </div>
      </div>

      <!-- Comment Input -->
      <div class="comment-input">
        <el-input
          v-model="newComment"
          type="textarea"
          :rows="3"
          placeholder="Write your comment..."
        />
        <el-button 
          type="primary" 
          @click="submitComment"
          class="submit-btn"
        >
          Post Comment
        </el-button>
      </div>

      <!-- Comments List -->
      <div class="comments-section">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <el-avatar class="comment-avatar">{{ comment.createdName?.[0] }}</el-avatar>
          <div class="comment-content">
            <div class="comment-header">
              <span class="comment-author">{{ comment.createdName }}</span>
              <span class="comment-time">{{ formatDate(comment.createdTime) }}</span>
            </div>
            <p class="comment-text">{{ comment.content }}</p>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import moment from 'moment'
import blogApis from '@/apis/blog'
import comApis from '@/apis/comments'

export default {
  data() {
    return {
      loading: false,
      blog: {  
      id: '',
      name: '',
      content: '',
      createdTime: null,
      modifiedTime: null,
      createdName: '',
      userInfo: {}  
    },
      newComment: '',
      comments: [],
      user:null,
    }
  },

  computed: {
    authorInitial() {
      return this.blog.createdName?.[0]?.toUpperCase() || 'A'
    }
  },

  created() {
    this.user = JSON.parse(this.$store.getStore('user'))
    console.log(this.$route.query.id)
  },
  mounted() {
    this.blog.id = this.$route.query.id
    this.loadBlog()
},
  methods: {
    async loadBlog() {
      try {
        this.loading = true
        const { data } = await blogApis.oneById(this.blog.id)
        if (data) this.blog = data
        this.loadComments()
      } catch (error) {
        this.$message.error('Failed to load blog post')
      } finally {
        this.loading = false
      }
    },

    async loadComments() {
      try {
        console.log(this.blog.id)
        const { data } = await comApis.list({bid:this.blog.id},1,-1)
        this.comments = data.records || []
      } catch (error) {
        this.$message.error('Failed to load comments')
      }
    },

    async submitComment() {
      if (!this.newComment.trim()) return

      try {
        const commentData = {
          content: this.newComment,
          bid: this.blog.id,
          createdId: this.user.id
        }

        await comApis.save(commentData)
        this.newComment = ''
        this.loadComments()
        this.$message.success('Comment posted successfully')
      } catch (error) {
        this.$message.error('Failed to post comment')
      }
    },

    formatDate(date) {
      return moment(date).format('MMM D, YYYY HH:mm')
    }
  }
}
</script>

<style lang="less" scoped>
.blog-detail-container {
  max-width: 95%;
  margin: 0 auto;
  padding: 20px;

  .nav-back {
    margin-bottom: 20px;
    text-align: left;
  }

  .blog-card {
    padding: 30px;
    box-shadow: 0 2px 12px rgba(0,0,0,0.08);
    .author-section {
      margin: 40px 0;
      display: flex;
      align-items: center;
      border-top: 1px solid #eee;
      padding-top: 30px;

      .author-avatar {
        width: 60px;
        height: 60px;
        font-size: 24px;
        margin-right: 20px;
      }

      .author-details {
        text-align: left;

        .author-name {
          font-size: 18px;
          margin-bottom: 5px;
        }

        .timestamps {
          color: #7f8c8d;
          font-size: 14px;
        }
      }
    }

    .comment-input {
      margin: 30px 0;

      text-align: right;
      .submit-btn {
        margin: 0 auto;
        margin-top: 15px;
        width: 200px;
      }
    }

    .comments-section {
      .comment-item {
        display: flex;
        margin: 25px 0;
        padding: 15px;
        background: #f8f9fa;
        border-radius: 8px;

        .comment-avatar {
          width: 40px;
          height: 40px;
          margin-right: 15px;
        }

        .comment-content {
          text-align: left;
          flex: 1;

          .comment-header {
            margin-bottom: 8px;

            .comment-author {
              font-weight: 500;
              margin-right: 10px;
            }

            .comment-time {
              color: #7f8c8d;
              font-size: 13px;
            }
          }

          .comment-text {
            margin: 0;
            color: #34495e;
            line-height: 1.6;
          }
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .blog-detail-container {
    padding: 20px 15px;

    .blog-card {
      padding: 20px;

      .author-section {
        padding-top: 20px;
        
        .author-avatar {
  width: 64px !important;
  height: 64px !important;
  border-radius: 50%;
  transition: all 0.3s ease;
  background: linear-gradient(45deg, #4facfe, #00f2fe); // 添加渐变背景
  font-weight: 600;
  font-size: 28px;

  &:hover {
    transform: scale(1.05);
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  }
}
      }
    }
  }
}
</style>