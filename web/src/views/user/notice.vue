<template>
    <div class="notice-container">

      <el-row style="padding: 10px;" :gutter="20" class="filter-bar">
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input 
            v-model="filter.name"
            placeholder="Search by name..."
            clearable 
            size="small"
            @keyup.enter.native="loadNotices"/>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-button 
            type="primary" 
            size="small"
            @click="loadNotices">
            Search
          </el-button>
        </el-col>
      </el-row>
  
      <!-- 卡片布局优化 -->
      <el-row :gutter="24" class="notice-row" type="flex">
        <el-col 
          v-for="item in notices" 
          :key="item.id"
          :xs="24" :sm="12" :md="8" :lg="6"
          class="notice-col"
        >
          <el-card 
            class="notice-card"
            shadow="hover" 
            @click.native="showDetail(item)"
          >
            <div class="card-content">
              <img v-if="item.imageUrl"
                :src="item.imageUrl"
                fit="cover"
                class="card-image"
              >
                <div v-else slot="error" class="image-error">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </img>
              
              <div class="card-body">
                <h3 class="card-title">{{ item.name }}</h3>
                <div class="card-text">
                  {{ item.content | truncate(80) }}
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
        
      </el-row>
  
      <!-- 详情弹窗 -->
      <el-dialog 
        :title="selectedNotice.name" 
        :visible.sync="detailVisible" 
        width="60%"
        :close-on-click-modal="false"  
      >
        <div class="dialog-content">
          <img  v-if="selectedNotice.imageUrl"
            :src="selectedNotice.imageUrl" 
            class="dialog-image"
          >
            <div v-else slot="error" class="image-error-lg">
              <i class="el-icon-picture-outline"></i>
            </div>
          </img>
          <div class="dialog-text">
            <h3>{{ selectedNotice.name }}</h3>
            <div class="scroll-content">{{ selectedNotice.content }}</div>
          </div>
        </div>
        <template #footer>
          <el-button type="primary" @click="detailVisible = false">Close</el-button>
        </template>
      </el-dialog>
  
      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          background
          :current-page.sync="pagination.page"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          layout="total, prev, pager, next"
          @current-change="handlePageChange"
        />
      </div>
    </div>
  </template>
<style lang="less" scoped>
.notice-container {
  max-width: 95%;
  text-align: center;
  margin: 0 auto;
  padding: 24px;

  .filter-bar {
    margin-bottom: 24px;
    
    .el-col {
      display: flex;
      gap: 12px;
    }
  }

  .notice-row {
    // 移除负边距
    margin: 0;
text-align: left;
    .notice-col {
      // 保持原有响应式设置
      padding: 12px;
      height: 100%; // 新增高度继承
      
      // 修复卡片高度不一致问题
      .notice-card {
        width: 100%;
        height: 100%; // 改为百分比高度
        display: flex;
        flex-direction: column;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        
        // 优化卡片内容布局
        .card-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          overflow: hidden; // 防止内容溢出

          .card-image {
            height: 200px;
            flex-shrink: 0;
            background: #f5f7fa;
            
            // 图片比例容器
            &::before {
              content: "";
              display: block;
              padding-top: 56.25%; // 16:9 比例
            }
            
            .el-image {
              position: absolute;
              top: 0;
              left: 0;
              width: 100%;
              height: 100%;
            }
          }

          .card-body {
            flex: 1;
            padding: 16px;
            overflow: hidden;
            
            .card-title {
              font-size: 18px;
              margin-bottom: 12px;
              @include text-ellipsis(1);
            }

            .card-text {
              color: #606266;
              line-height: 1.6;
              @include text-ellipsis(3);
              margin-bottom: 8px;
            }
          }
        }
      }

      &:hover {
        transform: translateY(-3px);
        box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
      }
    }
  }
// 优化弹窗布局
.dialog-content {
  display: flex;
  flex-direction: column;
  gap: 24px;

  .dialog-image {
    max-width: 80%;
    height: 300px;
    margin: 0 auto;
    border-radius: 8px;
    overflow: hidden;
    background: #f5f7fa;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      display: block;
    }

    .image-error-lg {
      font-size: 40px;
      color: #dcdfe6;
    }
  }

  .dialog-text {
    text-align: left;
    padding: 0 20px;

    h3 {
      font-size: 24px;
      margin: 0 0 16px;
      text-align: center;
    }

    .scroll-content {
      max-height: 400px;
      overflow-y: auto;
      line-height: 1.8;
      padding: 0 10px;
      
      &::-webkit-scrollbar {
        width: 6px;
      }
      
      &::-webkit-scrollbar-thumb {
        background: #ddd;
        border-radius: 3px;
      }
    }
  }

  @media (max-width: 992px) {
    .dialog-image {
      max-width: 100%;
      height: 250px;
    }
  }

  @media (max-width: 768px) {
    .dialog-image {
      height: 200px;
    }
    
    .dialog-text h3 {
      font-size: 20px;
    }
  }
}

  // 响应式适配
  @media (max-width: 992px) {
    .notice-card {
      .card-image {
        height: 180px !important;
      }
    }

    .dialog-content {
      grid-template-columns: 1fr;
      
      .dialog-image {
        height: 200px;
      }
    }
  }

  @media (max-width: 768px) {
    padding: 16px;

    .notice-col {
      padding: 8px !important;
    }

    .card-image {
      height: 150px !important;
    }
  }

  .pagination-container {
    margin-top: 24px;
    display: flex;
    justify-content: center;
  }
}
</style>
  
<script>
import noticeApis from '@/apis/notice'

export default {
data() {
    return {
    filter: {
        name: '',
        type: '',
        status:0,
        userId: null
    },
    notices: [],
      selectedNotice: {},
    
    detailVisible: false,
    pagination: {
        page: 1,
        pageSize: 10,
        total: 0
    },
    user:null
    }
},
mounted() {
    this.loadNotices()
},
methods: {
    async loadNotices() {
    try {
        const { data } = await noticeApis.list(
        this.filter,
        this.pagination.page,
        this.pagination.pageSize
        )
        
        this.notices = data.records
        this.pagination.total = data.total
    } catch (error) {
        this.$message.error('Failed to load notices')
    }
    },
    handleFilterChange() {
    this.pagination.page = 1
    this.loadNotices()
    },
    handlePageChange(page) {
    this.pagination.page = page
    this.loadNotices()
    },
    showDetail(notice) {
        this.selectedNotice = { ...notice }
        this.detailVisible = true
    },
}
}
</script>
