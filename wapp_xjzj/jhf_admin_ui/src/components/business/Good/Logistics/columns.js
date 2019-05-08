import { logistics as fieldName } from '../../../../const/fieldName'

export default [
  { title: '物流编号', dataIndex: fieldName.logisticsCode },
  { title: '物流名称', dataIndex: fieldName.logisticsName },
  { title: '省内首重价格', dataIndex: fieldName.firstWeightPriceInside },
  { title: '省外首重价格', dataIndex: fieldName.firstWeightPriceOutside },
  { title: '省内续重价格', dataIndex: fieldName.continuousWeightPrice },
]