import React from 'react'

export default [
  { title: '用户名', dataIndex: 'suserName', width: 120 },
  {
    title: '分享分类', dataIndex: 'categoryId', width: 120, render: categoryId => {
      switch (categoryId) {
        case 1:
          return <span>结伴出游</span>
        case 2:
          return <span>营地分享</span>
        default:
          return <span>数据错误</span>;
      }
    }
  },
  { title: '发布地址', dataIndex: 'publishAddress' },
  { title: '分享时间', dataIndex: 'publishTime', width: 160 },
  {
    title: '是否首页显示', dataIndex: 'isShow', width: 120, render: isShow => {
      switch (isShow) {
        case 'Y':
          return <span>是</span>;
        case 'N':
          return <span>否</span>;
        default:
          return <span>数据错误</span>;
      }
    }
  },
  {
    title: '是否挂起', dataIndex: 'isEnabled', width: 120, render: isEnabled => {
      switch (isEnabled) {
        case 'Y':
          return <span>是</span>;
        case 'N':
          return <span>否</span>;
        default:
          return <span>数据错误</span>;
      }
    }
  },
]