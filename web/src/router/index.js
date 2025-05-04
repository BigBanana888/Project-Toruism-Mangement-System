import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'index',
    meta:{
      title:"home"
    },
    redirect:'/login',
    component: Home,
    children:[
      {
        path: '/admin-index',
        name: 'index',
        meta:{
          title:"admin-home"
        },
        redirect:'/admin-home',
        component: () => import('../views/admin/index.vue'),
        children:[     
          {
            path: '/admin-home',
            name: 'home',
            meta:{
              title:"Home"
            },
            component: () => import('../views/admin/home.vue'),
          },
          {
            path: '/admin-type',
            name: 'type',
            meta:{
              title:"type management"
            },
            component: () => import('../views/admin/type.vue'),
          },
          {
            path: '/admin-act',
            name: 'act',
            meta:{
              title:"activate management"
            },
            component: () => import('../views/admin/activate.vue'),
          },
          {
            path: '/admin-center',
            name: 'center',
            meta:{
              title:"Personal Center"
            },
            component: () => import('../views/admin/center.vue'),
          },
          {
            path: '/admin-notice',
            name: 'notice',
            meta:{
              title:"notice management"
            },
            component: () => import('../views/admin/notice.vue'),
          },
          {
            path: '/admin-sub',
            name: 'subscribe',
            meta:{
              title:"subscribe management"
            },
            component: () => import('../views/admin/subscribe.vue'),
          },
          {
            path: '/admin-eva',
            name: 'eva',
            meta:{
              title:"evaluate management"
            },
            component: () => import('../views/admin/evaluate.vue'),
          },
          {
            path: '/admin-com',
            name: 'comments',
            meta:{
              title:"comments management"
            },
            component: () => import('../views/admin/comments.vue'),
          },
          {
            path: '/admin-message',
            name: 'admin-message',
            meta:{
              title:"message management"
            },
            component: () => import('../views/admin/message.vue'),
          },
          {
            path: '/admin-blog',
            name: 'admin-blog',
            meta:{
              title:"blog management"
            },
            component: () => import('../views/admin/blog.vue'),
          },
          {
            path: '/admin-user',
            name: 'user',
            meta:{
              title:"user management"
            },
            component: () => import('../views/admin/user.vue'),
          },
          {
            path: '/admin-center',
            name: 'admin-center',
            meta:{
              title:"personal center"
            },
            component: () => import('../views/user/center.vue'),
          }]
      },
    ],
  },
  {
    path: '/user-home',
    name: 'user-home',
    meta:{
      title:"Home"
    },
    component: () => import('../views/user/index.vue'),
    children:[     
          {
            path: '/user-index',
            name: 'user-index',
            meta:{
              title:"Home"
            },
            component: () => import('../views/user/home.vue'),
          },
          {
            path: '/user-act',
            name: 'user-act',
            meta:{
              title:"activate view"
            },
            component: () => import('../views/user/activate.vue'),
          },
          {
            path: '/act-detail',
            name: 'act-detail',
            meta:{
              title:"activate detail"
            },
            component: () => import('../views/user/act-detail.vue'),
          },
          {
            path: '/my-blog',
            name: 'my-blog',
            meta:{
              title:"blog view"
            },
            component: () => import('../views/user/myblog.vue'),
          },
          {
            path: '/my-com',
            name: 'my-com',
            meta:{
              title:"comments view"
            },
            component: () => import('../views/user/mycom.vue'),
          },
          {
            path: '/user-blog',
            name: 'user-blog',
            meta:{
              title:"blog view"
            },
            component: () => import('../views/user/blog.vue'),
          },
          {
            path: '/blog-u',
            name: 'blog-u',
            meta:{
              title:"blog update"
            },
            component: () => import('../views/user/blog-u.vue'),
          },
          {
            path: '/blog-w',
            name: 'blog-w',
            meta:{
              title:"blog write"
            },
            component: () => import('../views/user/blog-w.vue'),
          },
          {
            path: '/blog-detail',
            name: 'blog-detail',
            meta:{
              title:"blog detail"
            },
            component: () => import('../views/user/blog-detail.vue'),
          },
          {
            path: '/myactivate',
            name: 'user-myactivate',
            meta:{
              title:"my activate"
            },
            component: () => import('../views/user/myactivate.vue'),
          },
          {
            path: '/user-subscribe',
            name: 'user-subscribe',
            meta:{
              title:"my subscribe"
            },
            component: () => import('../views/user/subscribe.vue'),
          },
          {
            path: '/mymessage',
            name: 'mymessage',
            meta:{
              title:"my message"
            },
            component: () => import('../views/user/mymessage.vue'),
          },
          {
            path: '/user-chat',
            name: 'user-chat',
            meta:{
              title:"chat view"
            },
            component: () => import('../views/user/chat.vue'),
          },
          {
            path: '/user-notice',
            name: 'user-notice',
            meta:{
              title:"notice view"
            },
            component: () => import('../views/user/notice.vue'),
          },
          {
            path: '/user-center',
            name: 'user-center',
            meta:{
              title:"personal center"
            },
            component: () => import('../views/user/center.vue'),
          }]
  },
  {
    path: '/login',
    name: 'login',
    meta:{
      title:"user login"
    },
    component: () => import('../views/login.vue'),
  },  {
    path: '/login-e',
    name: 'login-e',
    meta:{
      title:"user login"
    },
    component: () => import('../views/login-e.vue'),
  },
  {
    path: '/register',
    name: 'register',
    meta:{
      title:"user register"
    },
    component: () => import('../views/register.vue'),
  },
]

const router = new VueRouter({
  routes
})
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => err);
}
router.beforeEach((to, from, next) => {
  console.log(to.name)
  let user = JSON.parse(window.localStorage.getItem("user"))
    if(to.name===null&&user!==undefined&&user!==null){
      if(user.roleId === 0){
        next('/admin-index')
      }else if(user.roleId === 2){
        next('/user-index')
      }
    }
  // else if(to.name===null&&undefined===user){
  //   next('/login')
  // }
  //to.name是登录路由的名字
    if (to.name!='login'&&to.name!='register'&&to.name!='login-e') {
    //token思路就是登录成功之后保存起来，然后下面拿来用
    //判断token是否已经保存了
      if (user) {
      //成功跳转
        document.title = to.meta.title
        next()
      } else {
        Vue.prototype.$message({
          type: "error",
          message: "请先登录"
       });
       document.title = to.meta.title
        next('/login')
      }
    } else {
      next()
    }
  })
  
  
export default router
