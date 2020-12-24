import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/icon',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Icon.vue'),
                    meta: { title: '自定义图标' }
                },
                {
                    path: '/table',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/BaseTable.vue'),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/Tabs.vue'),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/brand',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/Tablebrand.vue'),
                    meta: { title: '品牌' }
                },
                {
                    path: '/catePory',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/TableCatepory.vue'),
                    meta: { title: '商品' }
                },
                {
                    path: '/attrSetting',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/TableAttrType.vue'),
                    meta: { title: '属性设置' }
                },
                {
                    path: '/paramSetting',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/TableParamSetting.vue'),
                    meta: { title: '参数设置' }
                },
                {
                    path: '/attrTypr',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/TableAttributeCategory.vue'),
                    meta: { title: '商品分类' }
                },
                {
                    path: '/catePoryGroup',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/TableCatePoryGroup.vue'),
                    meta: { title: '商品列表' }
                },
                {
                    path: '/addPory',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/component/add.vue'),
                    meta: { title: '商品添加' }
                },
                {
                    path: '/updatePory',
                    component: () => import(/* webpackChunkName: "brand" */ '../components/page/newfile/component/update.vue'),
                    meta: { title: '商品修改' }
                },
                {
                    path: '/form',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/BaseForm.vue'),
                    meta: { title: '基本表单' }
                },
                {
                    path: '/umsAdmin',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/TableUmsAdmin.vue'),
                    meta: { title: '用户列表' }
                },
                {
                    path: '/umsPermission',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/TableUmsPermission.vue'),
                    meta: { title: '角色列表' }
                },
                {
                    path: '/resourceCate',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/TableResourceCate.vue'),
                    meta: { title: '资源分类' }
                },
                {
                    path: '/umsMenu',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/TableUmsMenu.vue'),
                    meta: { title: '角色列表' }
                },
                {
                    path: '/umsResource',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/TableUmsReource.vue'),
                    meta: { title: '资源列表' }
                },
                {
                    path: '/ztreeMenu',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/ztreeMenu.vue'),
                    meta: { title: '角色赋菜单' }
                },  {
                    path: '/cheResource',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/checkResouece.vue'),
                    meta: { title: '角色赋资源' }
                },
                {
                    path: '/umsRole',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/usm/TableUmsRole.vue'),
                    meta: { title: '权限列表' }
                },
                {
                    // 富文本编辑器组件
                    path: '/editor',
                    component: () => import(/* webpackChunkName: "editor" */ '../components/page/VueEditor.vue'),
                    meta: { title: '富文本编辑器' }
                },
                {
                    // markdown组件
                    path: '/markdown',
                    component: () => import(/* webpackChunkName: "markdown" */ '../components/page/Markdown.vue'),
                    meta: { title: 'markdown编辑器' }
                },

                {
                    // 图片上传组件
                    path: '/upload',
                    component: () => import(/* webpackChunkName: "upload" */ '../components/page/Upload.vue'),
                    meta: { title: '文件上传' }
                },
                {
                    // vue-schart组件
                    path: '/charts',
                    component: () => import(/* webpackChunkName: "chart" */ '../components/page/BaseCharts.vue'),
                    meta: { title: 'schart图表' }
                },
                {
                    // 拖拽列表组件
                    path: '/drag',
                    component: () => import(/* webpackChunkName: "drag" */ '../components/page/DragList.vue'),
                    meta: { title: '拖拽列表' }
                },
                {
                    // 拖拽Dialog组件
                    path: '/dialog',
                    component: () => import(/* webpackChunkName: "dragdialog" */ '../components/page/DragDialog.vue'),
                    meta: { title: '拖拽弹框' }
                },
                {
                    // 国际化组件
                    path: '/i18n',
                    component: () => import(/* webpackChunkName: "i18n" */ '../components/page/I18n.vue'),
                    meta: { title: '国际化' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: () => import(/* webpackChunkName: "permission" */ '../components/page/Permission.vue'),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                },
                {
                    path: '/donate',
                    component: () => import(/* webpackChunkName: "donate" */ '../components/page/Donate.vue'),
                    meta: { title: '支持作者' }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        },

    ]
});
