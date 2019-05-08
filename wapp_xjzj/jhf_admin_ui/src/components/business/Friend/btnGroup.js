export default [
  { title: '审核', disabled: record => !record.id },
  { title: '删除', disabled: record => !record.id },
]