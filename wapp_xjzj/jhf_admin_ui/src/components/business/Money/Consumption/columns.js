import { consumption as fieldName } from '../../../../const/fieldName'

export default [
  { title: '客户名称', dataIndex: fieldName.clientName, width: 160 },
  { title: '消费金额', dataIndex: fieldName.monetary, width: 180 },
  { title: '消费明细', dataIndex: fieldName.consumerDetail },
  { title: '联系方式', dataIndex: fieldName.telephone, width: 200 },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 200 },
]
