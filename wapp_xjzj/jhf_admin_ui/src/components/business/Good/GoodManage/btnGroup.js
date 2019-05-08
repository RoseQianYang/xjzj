export default [
  { title: '添加' },
  { title: '编辑', disabled: record => !record.id },
  { title: '库存管理', disabled: record => !record.id },
  // { title: '商品详情', disabled: record => !record.id },
  // { title: '删除', disabled: record => !record.id },
]
