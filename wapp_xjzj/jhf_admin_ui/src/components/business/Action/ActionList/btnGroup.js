export default [
  { title: '添加' },
  { title: '编辑', disabled: record => !record.id },
  // { title: '查看详情', disabled: record => !record.id },
  { title: '活动商品管理', disabled: record => !record.id },
  // { title: '删除', disabled: record => !record.id },
];
