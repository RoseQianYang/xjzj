import { houseCarLease as fieldName } from '../../../../const/fieldName';

export default [
  { title: '房车名称', dataIndex: fieldName.vehicleName, width: 200 },
  { title: '租赁价格', dataIndex: fieldName.rentPrice, width: 120, render: price => price / 100 },
  { title: '租赁地址', dataIndex: fieldName.address },
  { title: '联系电话', dataIndex: fieldName.phone, width: 160 },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 160 },
  { title: '修改时间', dataIndex: fieldName.updateTime, width: 160 },
];
