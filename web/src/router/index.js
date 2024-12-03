import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '首页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  }
]
export const asyncRoutes = [

  {
    path: '/magazine',
    component: Layout,
    redirect: '/magazine/list',
    name: 'magazine',
    meta: { title: '外刊管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '外刊信息',
        component: () => import('@/views/magazine/index'),
        meta: { title: '外刊信息' }
      },
      {
        path: 'magazineClassified',
        name: '外刊分类信息',
        component: () => import('@/views/magazineClassified/index'),
        meta: { title: '外刊分类信息' }
      }
    ]
  },
  {
    path: '/lexicon',
    component: Layout,
    name: '词库管理',
    alwaysShow: true,
    meta: { title: '词库管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'lexicon',
        name: '词库信息',
        component: () => import('@/views/lexicon/index.vue'),
        meta: { title: '词库信息' }
      },
      {
        path: 'lexiconInfo',
        name: '词库单词信息',
        component: () => import('@/views/lexiconInfo/index.vue'),
        meta: { title: '词库单词信息' }
      }]
  },
  {
    path: '/feedback',
    component: Layout,
    name: '反馈管理',
    alwaysShow: true,
    meta: { title: '反馈管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'feedback',
        name: '反馈记录',
        component: () => import('@/views/feedback/index.vue'),
        meta: { title: '反馈记录' }
      }]
  },
  // 系统管理
  {
    path: '/system',
    redirect: '/system/user', // 默认访问地址
    component: Layout,
    meta: { title: '系统管理', icon: 'dr' },
    children: [
      {
        path: 'user',
        name: '用户信息',
        component: () => import('@/views/system/user/index'),
        meta: { title: '用户信息' }
      },
      {
        path: 'updatePassword',
        name: '密码修改',
        component: () => import('@/views/system/updatePassword'),
        meta: { title: '密码修改' }
      },
      {
        path: 'info',
        name: '个人中心',
        component: () => import('@/views/system/info'),
        meta: { title: '个人中心' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
