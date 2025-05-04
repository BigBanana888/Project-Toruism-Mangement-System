<template>
    <div class="activity-container">
      <!-- Activity Header -->
      <div class="header">
        <h1>{{ activity.name }}</h1>
      </div>
      <el-divider />
  
      <!-- Activity Details -->
      <el-row :gutter="30">
        <el-col :span="16">
          <el-card class="image-card">
            <img :src="activity.imageUrl" class="main-image" />
          </el-card>
        </el-col>
        
        <el-col :span="8">
          <el-card class="info-card">
            <div class="info-item">
              <i class="el-icon-location" /> {{ activity.address }}
            </div>
            
            <div class="info-item">
              <i class="el-icon-time" /> {{ formattedDate }}
            </div>
            
            <div class="budget-section">
              <div class="budget-labels">
                <span>Budget: ¥{{ activity.ys /100}}</span>
              </div>
            </div>
  
            <el-divider />
  
            <div class="action-buttons">
              <el-button :disabled="user.id===activity.createdId" type="primary" @click="toSub">Subscirbe Now</el-button>
            
              <el-button 
              type="primary" 
              @click="tochat">
              Chat Now
            </el-button></div>
          </el-card>
        </el-col>
      </el-row>
  
      <el-dialog 
        title="Subscibe Form" 
        :visible.sync="showDialog" 
        width="800px">
        <el-form 
          ref="typeForm"
          :model="currentItem" 
          label-width="200px"
          label-position="left">
          <el-form-item 
            label="Apply Infomation" 
            prop="remark">
            <el-input 
              v-model="remark"
              type="textarea"
              :rows="4"
              resize="none"/>
          </el-form-item>
          <el-form-item class="dialog-actions">
            <el-button @click="showDialog = false">Cancel</el-button>
            <el-button 
              type="primary" 
              @click="handleBooking">
              Save
            </el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- Comments Section -->
      <div class="comments-section">
        <h2>Reviews ({{ comments.length }})</h2>
       
  
        <!-- Comments List -->
        <div class="comment-list">
          <el-card 
            v-for="comment in comments" 
            :key="comment.id"
            class="comment-item"
          >
            <div class="comment-header">
              <el-avatar :src="comment.imageUrl" />
              <div class="user-info">
                <span class="username">{{ comment.createdName }}</span>
                <el-rate 
                  :value="comment.rate" 
                  disabled
                  show-score
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                />
              </div>
              <span class="comment-time">{{ formatTime(comment.createdTime) }}</span>
            </div>
            
            <div class="comment-content">
              {{ comment.content }}
            </div>
            <img style="width: 200px;height: 200px;" :src="comment.imageUrl" />
          </el-card>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import actApi from '@/apis/act'
  import evaApi from '@/apis/eva'
  import subApi from '@/apis/subscribe'
  import moment from 'moment'
  
  export default {
    data() {
          return {
            user: null,
            showDialog:false,
            remark: "",
            currentItem: {},
        activity: {
          id: '',
          name: '',
          address: '',
          stime: null,
          ys: 0,
          yys: 0,
          ss: '0',
          imageUrl: ''
        },
        comments: [],
        newComment: {
          content: '',
          rate: 3,
          imageUrl: ''
        },
        statusMap: {
          '0': 'Pending',
          '1': 'Approved',
          '2': 'Rejected'
              },
        user:null,
      }
    },
  
    computed: {
      formattedDate() {
        return moment(this.activity.stime).format('MMM Do YYYY, h:mm a')
      },
  
      statusText() {
        return this.statusMap[this.activity.ss]
      },
  
      statusType() {
        const types = { '0': 'warning', '1': 'success', '2': 'danger' }
        return types[this.activity.ss]
      }
    },
  
    async mounted() {
        this.user = JSON.parse(this.$store.getStore('user'))
      await this.loadData()
    },
  
    methods: {
      toSub() {
    this.showDialog = true    
      },
      async loadData() {
        try {
          const { data } = await actApi.oneById(this.$route.query.id)
          this.activity = data
          console.log(this.activity)
            const commentsRes = await evaApi.list({aid:this.activity.id},1,-1)
          this.comments = commentsRes.data.records
        } catch (error) {
          this.$message.error('Failed to load data')
        }
      },
  
      handleUploadSuccess(res) {
        this.newComment.imageUrl = res.data.url
      },
  
      formatTime(time) {
        return moment(time).fromNow()
      },
      tochat() {
        this.$router.push({path:"/user-chat",query:{id:this.activity.createdId}})
      },
      handleBooking() {
        let sub = {
          aid: this.activity.id,
          createdId: this.user.id,
          remark: this.remark,
          uid:this.activity.createdId
        }
        subApi.save(sub).then(res => {
          if (res.code === 200) {
            this.$message.success("Subscribe Success!")
            this.showDialog = false
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
  </script>
  
<style scoped>
.activity-container {
  max-width: 95%;
  margin: 20px auto;
  padding: 0 20px;
}

.header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 30px;
}

.main-image {
  width: 100%;
  height: 500px;
  object-fit: cover;
  border-radius: 4px;
}

.info-card {
  height: 100%;
  
  .info-item {
    margin: 15px 0;
    font-size: 16px;
    
    i {
      margin-right: 10px;
      color: #666;
    }
  }
}

.budget-labels {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  color: #666;
}

.comments-section {
  margin-top: 40px;
  
  h2 {
    color: #333;
    border-bottom: 2px solid #eee;
    padding-bottom: 10px;
  }
}

.comment-form {
  margin: 20px 0;
  
  .form-actions {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-top: 15px;
  }
}

.comment-item {
  margin: 15px 0;
  
  .comment-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    
    .user-info {
      flex-grow: 1;
      margin-left: 15px;
      
      .username {
        font-weight: bold;
        margin-right: 10px;
      }
    }
    
    .comment-time {
      color: #999;
      font-size: 0.9em;
    }
  }
  
  .comment-content {
    line-height: 1.6;
    color: #444;
  }
}
</style>