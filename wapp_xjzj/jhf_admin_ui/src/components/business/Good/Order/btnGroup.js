export default [
  { title: '物流发货', disabled: record => !(record.id && record.orderStatus === '3') },
  { title: '查看详情', disabled: record => !record.id },
];
