export default [
  { title: '添加' },
  { title: '编辑', disabled: record => !record.id },
  { title: '删除', disabled: record => !record.id },
]