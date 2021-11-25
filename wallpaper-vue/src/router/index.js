import Vue from 'vue'
import Router from 'vue-router'
import Index from '../views/index'
import HelloWorld from '../components/HelloWorld'
import Upload from '../views/upload'
import Main from '../components/main'


Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/hello',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/upload',
      name: 'Upload',
      component: Upload
    },
    {
      path: '/main/:query',
      name: 'Main',
      component: Main
    }

  ]
})
