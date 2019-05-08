import { integral as fieldName } from '../../../../const/fieldName'

export default [
  { title: '客户名称', dataIndex: fieldName.clientName },
  { title: '总积分', dataIndex: fieldName.totalIntegral, width: 200 },
  { title: '已用积分', dataIndex: fieldName.usedIntegral, width: 200 },
  { title: '剩余积分', dataIndex: fieldName.residualIntegral, width: 200 },
  { title: '联系方式', dataIndex: fieldName.telephone, width: 200 },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 200 },
]
