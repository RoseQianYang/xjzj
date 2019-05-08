export default [
  { title: '用户名', dataIndex: 'userName', width: 180 },
  { title: '商品名称', dataIndex: 'productTitle' },
  { title: '分类', dataIndex: 'productCateTitle', width: 160 },
  { title: '价格', dataIndex: 'productPrice', width: 160, render: price => price / 100 },
  { title: '数量', dataIndex: 'productCount', width: 160 },
  { title: '颜色', dataIndex: 'productColor', width: 160 },
  { title: '尺码', dataIndex: 'productSize', width: 160 },
]