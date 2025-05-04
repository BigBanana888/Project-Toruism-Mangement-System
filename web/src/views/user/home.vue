<template>
  <div class="tour-home-container">
      <!-- 轮播图模块 -->
      <el-carousel 
          class="tour-carousel" 
          height="40vh" 
          :interval="4000"
          indicator-position="outside"
          arrow="always">
          <el-carousel-item v-for="item in notices" :key="item.id">
              <div class="carousel-item">
                  <el-image 
                      :src="item.imageUrl" 
                      fit="cover"
                      class="carousel-image">
                      <div class="carousel-caption">
                          <h3>{{ item.title }}</h3>
                          <p>{{ item.description }}</p>
                      </div>
                  </el-image>
              </div>
          </el-carousel-item>
      </el-carousel>

    <!-- 主内容区 -->
    <div class="main-content">
      <el-row :gutter="30">
        <!-- 活动列表（调整为更大比例） -->
        <el-col :md="14" :sm="24">
          <div class="card-container">
            <h2 class="section-title">
              <i class="el-icon-picture"></i>
              Latest Activity
            </h2>
            <template v-if="acts.length === 0">
              <el-empty description="No Activity" />
            </template>
            <div class="event-grid">
              <el-card 
                v-for="item in acts" 
                :key="item.id"
                class="event-card">
                <!-- 新增图片展示区域 -->
                <div class="event-image-wrapper">
                  <el-image 
                    :src="item.imageUrl" 
                    fit="cover"
                    class="event-image">
                    <div class="image-overlay">
                      <h3>{{ item.name }}</h3>
                    </div>
                  </el-image>
                </div>
                <div class="event-meta">
                  <div class="meta-item">
                    <i class="el-icon-location"></i>
                    {{ item.address }}
                  </div>
                  <div class="meta-item">
                    <i class="el-icon-time"></i>
                    {{ formatDate(item.stime) }}
                  </div>
                </div>
              </el-card>
            </div>
            <div class="more-action" @click="gotoPage('/user-act', null)">
              View All Events
              <i class="el-icon-arrow-right"></i>
            </div>
          </div>
        </el-col>

        <!-- 预约信息（调整为较小比例） -->
        <el-col :md="10" :sm="24">
          <div class="card-container">
            <h2 class="section-title">
              <i class="el-icon-tickets"></i>
              My Subscriptions
            </h2>
            <template v-if="subcribes.length === 0">
              <el-empty description="No subscriptions yet" />
            </template>
            <div class="subscription-list">
              <div 
                v-for="item in subcribes" 
                :key="item.id"
                class="subscription-item">
                <div class="subs-info">
                  <h4 class="subs-title">{{ item.aname }}</h4>
                  <div class="subs-meta">
                    <div class="meta-line">
                      <i class="el-icon-location-outline"></i>
                      <span>{{ item.address }}</span>
                    </div>
                    <div class="meta-line">
                      <i class="el-icon-alarm-clock"></i>
                      <span>{{ formatDate(item.stime) }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="more-action" @click="gotoPage('/user-subscribe', null)">
              View All Subscriptions
              <i class="el-icon-arrow-right"></i>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped lang="less">
/* 轮播图容器调整 */
.tour-carousel {
  // 新增以下样式
  .el-carousel__item {
    padding: 0 !important; /* 清除默认内边距 */
  }

  .carousel-item {
    width: 100%;
    height: 100%;
    position: relative; /* 为图片定位提供基准 */

    .carousel-image {
      width: 100%;
      height: 100%;
      
      /* 修复element-ui图片容器默认inline-block问题 */
      ::v-deep .el-image__inner {
        width: 100%;
        height: 100%;
        object-fit: cover; /* 保持原有cover设置 */
      }
    }
  }

  /* 标题定位调整 */
  .carousel-caption {
    position: absolute;
    bottom: 20%;
    left: 5%;
    color: white;
    text-shadow: 1px 1px 3px rgba(0,0,0,0.5);
    z-index: 2;
  }
}
/* 新增活动卡片样式 */
.event-grid {
  display: grid;
  gap: 20px;
}

.event-card {
  border-radius: 12px;
  overflow: hidden;
  transition: transform 0.3s ease;
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 20px rgba(26, 111, 87, 0.15);
  }
}

.event-image-wrapper {
  position: relative;
  height: 200px;
  
  .event-image {
    height: 100%;
    width: 100%;
    
    .image-overlay {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      padding: 15px;
      background: linear-gradient(transparent, rgba(0,0,0,0.7));
      
      h3 {
        color: white;
        margin: 0;
        font-size: 18px;
        line-height: 1.3;
      }
    }
  }
}

.event-meta {
  padding: 15px;
  background: #fff;
  
  .meta-item {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #4a8c7c;
    font-size: 14px;
    
    & + .meta-item {
      margin-top: 10px;
    }
    
    .el-icon {
      font-size: 16px;
    }
  }
}

/* 优化订阅列表样式 */
.subscription-list {
  max-height: 600px;
  overflow-y: auto;
}

.subscription-item {
  padding: 12px;
  border-radius: 8px;
  background: rgba(245, 250, 248, 0.5);
  transition: background 0.3s;
  
  & + .subscription-item {
    margin-top: 12px;
  }
  
  &:hover {
    background: rgba(26, 111, 87, 0.05);
  }
}

.subs-info {
  .subs-title {
    color: #1a6f57;
    margin: 0 0 8px;
    font-size: 15px;
    font-weight: 500;
  }
  
  .subs-meta {
    color: #7a9c8e;
    font-size: 13px;
    
    .meta-line {
      display: flex;
      align-items: center;
      gap: 8px;
      
      & + .meta-line {
        margin-top: 6px;
      }
      
      .el-icon {
        width: 16px;
        text-align: center;
      }
    }
  }
}

/* 响应式调整 */
@media (max-width: 768px) {
  .event-image-wrapper {
    height: 160px;
  }
  
  .subscription-list {
    max-height: 400px;
  }
}
</style>
<script>
import BodyTop from '@/components/BodyTop.vue'
import actApi from '@/apis/act'
import noticeApi from '@/apis/notice'
import subcribeApi from '@/apis/subscribe'
import { formatDate } from '@/utils/date'
  
export default{
    components: {
      BodyTop, 
  },
  data(){
    return {
      isOpen:false,
      activateMenu:"/admin-home",
      acts:[],
      notices:[],
      subcribes:[],
      user:{}
    }
  },
  mounted(){
    this.user = JSON.parse(this.$store.getStore("user"))
    this.getActs()
    this.getNotices()
    this.getSubcribes()
  },
  methods: {
    formatDate,
      gotoPage(path,params){
        if(params===null){
          this.$router.push(path)
        }else{
          this.$router.push({path:path,params:{id:params.id}})
        }
      },
      getSubcribes(){
        subcribeApi.list({status:'Already subscribe',userId:this.user.id},1,3).then(res=>{
          this.subcribes = res.data.records
          console.log(this.subcribes)
        })
      },
      getActs(){
        actApi.list({ss:"approve"},1,3).then(res=>{
          this.acts = res.data.records
        })
      },
      getNotices(){
        noticeApi.list({status:0},1,3).then(res=>{
          this.notices = res.data.records
        })
      },
  }
}
</script>