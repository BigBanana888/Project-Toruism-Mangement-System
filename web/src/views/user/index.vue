<template>
  <div class="tour-header-container">
      <div class="header-nav">
          <div class="nav-brand">
            Campus Travel Hub
          </div>
          <div class="nav-menu">
              <div class="menu-item" @click="gotoPage('/user-index')">Home</div>
              <div class="menu-item" @click="gotoPage('/user-act')">Travels</div>
              <div class="menu-item" @click="gotoPage('/user-blog')">Blogs</div>
              <div class="menu-item" @click="gotoPage('/user-subscribe')">Subscribes</div>
              <div class="menu-item" @click="gotoPage('/mymessage')">Messages</div>
              <div class="menu-item" @click="gotoPage('/user-notice')">Notices</div>
              <div class="menu-item" @click="gotoPage('/user-center')">Profile</div>
          </div>
      </div>
      <div class="main-content">
        <RouterView/>
      </div>
  </div>
</template>

<style scoped lang="less">
.tour-header-container {
user-select: none;
background: rgba(245, 250, 248, 0.95);
}

.header-nav {
height: 70px;
width: calc(100% - 40px);
background: rgba(255, 255, 255, 0.95);
backdrop-filter: blur(8px);
display: flex;
align-items: center;
justify-content: space-between;
padding: 0 20px;
box-shadow: 0 2px 12px rgba(26, 111, 87, 0.1);
position: fixed;
top: 0;
z-index: 1000;

.nav-brand {
  font-size: 24px;
  color: #1a6f57;
  font-weight: 600;
  letter-spacing: 0.5px;
  background: linear-gradient(135deg, #1a6f57, #4a8c7c);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.nav-menu {
  display: flex;
  // gap: 40px;
  align-items: center;

  .menu-item {
    color: #2a6653;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    padding: 8px 12px;
    border-radius: 6px;

    &:hover {
      color: #1a6f57;
      background: rgba(26, 111, 87, 0.08);
      transform: translateY(-2px);
    }

    &:active {
      transform: translateY(0);
    }
  }
}
}

.main-content {
margin-top: 70px;
padding: 30px 40px;
height: calc(100vh - 200px);
background-color: #f8faf9;
}

@media (max-width: 768px) {
.header-nav {
  padding: 0 20px;
  
  .nav-menu {
    gap: 20px;
    
    .menu-item {
      font-size: 14px;
      padding: 6px 10px;
    }
  }
}

.main-content {
  padding: 20px;
}
}
</style>
<script>
import BodyTop from '@/components/BodyTop.vue'
import { RouterView } from 'vue-router';
  export default{
    components: {
      BodyTop, RouterView
  },
  data(){
    return {
      isOpen:false,
      user:{},
      activateMenu:"/admin-home",
    }
  },
  mounted(){
    this.user = JSON.parse(this.$store.getStore('user'))
  },
  methods: {
      gotoPage(path){
          this.$router.push(path)
      }
  }
}
</script>
