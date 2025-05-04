<template>
  <el-row class="admin-layout">
    <!-- 侧边栏容器 -->
    <el-col :span="4" class="sidebar-col">
      <el-menu
        class="system-menu"
        :default-active="activateMenu"
        :collapse="isOpen"
        router
        background-color="#f8f9fa"
        active-text-color="#1a6f57"
        text-color="#2c3e50">
        <template>
          <div style="width: 100%;text-align: center;padding: 20px 0;
          font-size: 24px;">Campus Travel Hub</div>
        </template>
        <!-- 菜单项保持原有结构 -->
        <el-menu-item index="/admin-home">
          <i class="el-icon-s-home menu-icon"></i>
          <span>Home</span>
        </el-menu-item>
        
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-place menu-icon"></i>
            <span>Travel Management</span>
          </template>
          <el-menu-item index="/admin-type">
            <i class="el-icon-s-ticket submenu-icon"></i>
            <span>Travel Tag Management</span>
          </el-menu-item>
            <el-menu-item index="/admin-act">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>Travel Info Management</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-place"></i>
              <span>Subscribe Management</span>
            </template>
            <el-menu-item index="/admin-sub">
              <template slot="title">
                <div>
                  <i class="el-icon-mobile-phone"></i>
                  <span>Travel Subscribe Management</span>
                </div>
              </template>
            </el-menu-item>
            
            <el-menu-item index="/admin-eva">
              <template slot="title">
                <i class="el-icon-document-checked"></i>
                <span>Travel Evaluate Management</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>User Management</span>
            </template>
            <el-menu-item index="/admin-user">
              <template slot="title">
                <i class="el-icon-tickets"></i>
                <span>User Info Management</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/admin-message">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>User Message Management</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-chat-line-round"></i>
              <span>Blog Management</span>
            </template>
            <el-menu-item index="/admin-blog">
              <template slot="title">
                <i class="el-icon-document-checked"></i>
                <span>Blog Info Management</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/admin-com">
              <template slot="title">
                <i class="el-icon-document-checked"></i>
                <span>Blog Comments Management</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-bell"></i>
              <span>Notice Management</span>
            </template>
            <el-menu-item index="/admin-notice">
              <template slot="title">
                <i class="el-icon-document"></i>
                <span>Notice Info Management</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-menu-item index="/admin-center">
              <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span>Personal Center</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-col>
    <!-- 内容区域 -->
    <el-col :span="20" class="content-col">
      <!-- <body-top @toggle-collapse="isOpen = !isOpen" /> -->
      <div class="main-content">
        <router-view />
      </div>
    </el-col>
  </el-row>
</template>
  
<script>
import BodyTop from '@/components/BodyTop.vue';

export default {
  components: { BodyTop },
  data() {
    return {
      isOpen: false,
      activateMenu: "/admin-home"
    }
  }
}
</script>

<style scoped>
/* 统一字体规范 */
.system-menu {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", Arial, sans-serif;
}

/* 侧边栏样式优化 */
.sidebar-col {
  min-height: 100vh;
  box-shadow: 2px 0 6px rgba(0, 0, 0, 0.1);
}

.system-menu {
  height: 100vh;
  transition: all 0.3s;
}

.menu-icon {
  font-size: 20px;
  margin-right: 8px;
  color: #1a6f57;
}

.submenu-icon {
  font-size: 16px;
  vertical-align: middle;
}

.el-menu-item {
  height: 56px;
  line-height: 56px;
  transition: all 0.2s;
}

.el-menu-item:hover {
  background-color: #e8f5e9 !important;
}

.el-menu-item.is-active {
  background-color: #e8f5e9 !important;
  font-weight: 500;
}

/* 内容区域样式 */
.content-col {
  background: #f5f7fa;
  min-height: 100vh;
}

.main-content {
  padding: 20px;
  margin: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 响应式处理 */
@media screen and (max-width: 768px) {
  .sidebar-col {
    display: none;
  }
  
  .content-col {
    width: 100%;
  }
}
</style>