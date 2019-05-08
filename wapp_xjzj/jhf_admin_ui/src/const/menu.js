import React from 'react';
import asyncComponent from "../components/public/AsyncComponent";
// import { getStorage } from "../utils/storage"

// 商城管理
const GoodManage = asyncComponent(() => import("../components/business/Good/GoodManage"));
// import GoodManage from "../components/business/Good/GoodManage";
const BrandManage = asyncComponent(() => import("../components/business/Good/BrandManage"));
// import BrandManage from "../components/business/Good/BrandManage";
const GoodClass = asyncComponent(() => import("../components/business/Good/GoodClass"));
// import GoodClass from "../components/business/Good/GoodClass";
const Order = asyncComponent(() => import("../components/business/Good/Order"));
// import Order from "../components/business/Good/Order";
// import Logistics from "../components/business/Good/Logistics"
// 活动管理
const ActionClass = asyncComponent(() => import("../components/business/Action/ActionClass"));
// import ActionClass from "../components/business/Action/ActionClass";
const ActionList = asyncComponent(() => import("../components/business/Action/ActionList"));
// import ActionList from "../components/business/Action/ActionList";
// 营地管理
const Campsite = asyncComponent(() => import("../components/business/Campsite"));
// import Campsite from "../components/business/Campsite";
// 线路管理
const LineManage = asyncComponent(() => import("../components/business/Line/LineManage"));
// import LineManage from "../components/business/Line/LineManage";
// 车友分享
const Tourism = asyncComponent(() => import("../components/business/Tourism"));
// import Tourism from "../components/business/Tourism";
// 售后网点
const Service = asyncComponent(() => import("../components/business/Service"));
// import Service from "../components/business/Service";
// Banner管理
// import Banner from "../components/business/Banner";
// 线下门店
const Shop = asyncComponent(() => import("../components/business/Shop"));
// import Shop from "../components/business/Shop";
// 财务管理
// import Consumption from "../components/business/Money/Consumption";
// import Integral from "../components/business/Money/Integral";
const Withdraw = asyncComponent(() => import("../components/business/Money/Withdraw"));
// import Withdraw from "../components/business/Money/Withdraw";
const SaleDetail = asyncComponent(() => import("../components/business/Money/SaleDetail"));
// import SaleDetail from "../components/business/Money/SaleDetail";
// 房车品鉴
const HouseCar = asyncComponent(() => import("../components/business/HouseCar"));
// import HouseCar from "../components/business/HouseCar";
const HouseCarLease = asyncComponent(() => import("../components/business/HouseCar/HouseCarLease"));
// import HouseCarLease from '../components/business/HouseCar/HouseCarLease';
const HouseCarBrand = asyncComponent(() => import("../components/business/HouseCar/HouseCarBrand"));
// import HouseCarBrand from "../components/business/HouseCar/HouseCarBrand";
// 图片管理
const ImageList = asyncComponent(() => import("../components/business/Image/ImageList"));
// import ImageList from "../components/business/Image/ImageList";
const ImageCate = asyncComponent(() => import("../components/business/Image/ImageCate"));
// import ImageCate from "../components/business/Image/ImageCate";
// 系统设置
const Role = asyncComponent(() => import("../components/business/System/Role"));
// import Role from "../components/business/System/Role";
const SysLog = asyncComponent(() => import("../components/business/System/SysLog"));
// import SysLog from "../components/business/System/SysLog";
const Admin = asyncComponent(() => import("../components/business/System/Admin"));
// import Admin from "../components/business/System/Admin";
const Data = asyncComponent(() => import("../components/business/Data"));
// import Data from "../components/business/Data";
const Config = asyncComponent(() => import("../components/business/System/Config"));
// import Config from '../components/business/System/Config'

// 一级菜单的code值必须紧跟在二级菜单的link之后

export const fullMenu = [
  {
    code: 'good',
    name: '商城管理',
    icon: 'shopping-cart',
    auth: false,
    subMenu: [
      {
        name: '商品管理',
        code: 'goodManage',
        link: '/app/good/goodManage',
        auth: false,
        component: <GoodManage />
      },
      {
        name: '品牌管理',
        code: 'brandManage',
        link: '/app/good/brandManage',
        auth: false,
        component: <BrandManage />
      },
      {
        name: '商品分类',
        code: 'goodClass',
        link: '/app/good/goodClass',
        auth: false,
        component: <GoodClass />
      },
      {
        name: '订单管理',
        code: 'order',
        link: '/app/good/order',
        auth: false,
        component: <Order />
      },
      // { name: '物流管理', code: 'logistics', link: '/app/good/logistics', component: <Logistics /> },
    ],
  },
  {
    code: 'product',
    name: '活动管理',
    icon: 'paper-clip',
    auth: false,
    subMenu: [
      {
        name: '活动列表',
        code: 'actionList',
        link: '/app/product/actionList',
        auth: false,
        component: <ActionList />
      },
      {
        name: '活动分类',
        code: 'actionClass',
        link: '/app/product/actionClass',
        auth: false,
        component: <ActionClass />
      },
      // { name: '产品活动列表', code: 'actionProductList', link: '/app/product/ActionProductList', component: <ActionProductList />},
    ],
  },
  {
    code: 'campsite',
    name: '营地管理',
    icon: 'tags',
    auth: false,
    subMenu: [
      {
        name: '营地管理',
        code: 'campsiteList',
        link: '/app/campsite/campsite',
        auth: false,
        component: <Campsite />
      },
    ],
  },
  {
    code: 'line',
    name: '线路管理',
    icon: 'swap-right',
    auth: false,
    subMenu: [
      {
        name: '线路管理',
        code: 'lineList',
        link: '/app/line/lineManage',
        auth: false,
        component: <LineManage />
      },
    ],
  },
  {
    code: 'tourism',
    name: '驴友共享',
    icon: 'smile',
    auth: false,
    subMenu: [
      {
        name: '旅游分享',
        code: 'tourismList',
        link: '/app/tourism/tourism',
        auth: false,
        component: <Tourism />
      },
    ],
  },
  {
    code: 'service',
    name: '售后网点管理',
    icon: 'windows-o',
    auth: false,
    subMenu: [
      {
        name: '售后网点',
        code: 'serviceList',
        link: '/app/service/service',
        auth: false,
        component: <Service />
      },
    ],
  },
  {
    code: 'shop',
    name: '线下门店管理',
    icon: 'home',
    auth: false,
    subMenu: [
      {
        name: '门店列表',
        code: 'shopList',
        link: '/app/shop/shop',
        auth: false,
        component: <Shop />
      },
    ],
  },
  // {
  //   code: 'banner',
  //   name: 'Banner管理',
  //   icon: 'book',
  //   subMenu: [
  //     { name: 'Banner管理', code: 'banner', link: '/app/banner/banner', component: <Banner /> },
  //   ],
  // },
  {
    code: 'money',
    name: '财务管理',
    icon: 'shopping-cart',
    auth: false,
    subMenu: [
      //     { name: '消费统计', code: 'consumption', link: '/app/money/consumption', component: <Consumption /> },
      //     { name: '积分明细', code: 'integral', link: '/app/money/integral', component: <Integral /> },
      {
        name: '返点申请',
        code: 'withdrawals',
        link: '/app/money/withdrawals',
        auth: false,
        component: <Withdraw />
      },
      {
        name: '销售明细',
        code: 'saleDetail',
        link: '/app/money/saleDetail',
        auth: false,
        component: <SaleDetail />
      },
    ],
  },
  {
    code: 'housecar',
    name: '房车品鉴',
    icon: 'car',
    auth: false,
    subMenu: [
      {
        name: '房车列表',
        code: 'houseCarList',
        link: '/app/housecar/houseCar',
        auth: false,
        component: <HouseCar />
      },
      {
        name: '房车租赁',
        code: 'houseCarLease',
        link: '/app/housecar/houseCarLease',
        auth: false,
        component: <HouseCarLease />
      },
      {
        name: '房车品牌管理',
        code: 'houseCarBrand',
        link: '/app/housecar/houseCarBrand',
        auth: false,
        component: <HouseCarBrand />
      },
    ],
  },
  // {
  //     code: 'user',
  //     name: '用户管理',
  //     icon: 'shopping-cart',
  //     subMenu: [
  // { name: '用户列表', code: 'userList', link: '/app/user/UserList', component: <h2>用户列表</h2> },
  // ],
  // },
  {
    code: 'image',
    name: '图片管理',
    icon: 'picture',
    auth: false,
    subMenu: [
      {
        name: '图片列表',
        code: 'imgList',
        link: '/app/image/imgList',
        auth: false,
        component: <ImageList />
      },
      {
        name: '图片分类',
        code: 'imgCate',
        link: '/app/image/imgCate',
        auth: false,
        component: <ImageCate />
      },
    ],
  },
  {
    code: 'sys',
    name: '系统管理',
    icon: 'setting',
    auth: false,
    subMenu: [
      {
        name: '管理员',
        code: 'admin',
        link: '/app/sys/admin',
        auth: false,
        component: <Admin />
      },
      {
        name: '系统日志',
        code: 'sysLog',
        link: '/app/sys/sysLog',
        auth: false,
        component: <SysLog />
      },
      {
        name: '角色管理',
        code: 'role',
        link: '/app/sys/role',
        auth: false,
        component: <Role />
      },
      {
        name: '数据统计',
        code: 'data',
        link: '/app/sys/data',
        auth: false,
        component: <Data />
      },
      {
        name: '系统设置',
        code: 'config',
        link: '/app/sys/config',
        auth: false,
        component: <Config />
      },
      // { name: '权限管理', code: 'authorization', link: '/app/sys/Authorization', component: <h2>权限管理</h2> },
    ],
  },
];

// const user = getStorage('jhf_app') || {}
// const fMenu = fullMenu
// let menu = []
//
// if (user.hasOwnProperty('menus')) {
//   const { menus } = user
//   const userParentCode = []
//   const userChildrenCode = []
//
//   menus.forEach((i, index) => {
//     userParentCode.push(i.code)
//     userChildrenCode[index] = []
//     if (i.hasOwnProperty('subMenu')) {
//       i.subMenu.forEach(j => {
//         userChildrenCode[index].push(j.code)
//       })
//     }
//   })
//
//   menu = fMenu.filter(v => {
//     return userParentCode.indexOf(v.code) !== -1
//   })
//
//   menu.forEach((v, i) => {
//     v.subMenu = v.subMenu.filter(k => {
//       return userChildrenCode[i].indexOf(k.code) !== -1
//     })
//   })
// }
//
// export default menu

