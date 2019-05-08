import React from 'react'
import { Input } from 'antd'
import { order as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.goodName, item: <Input placeholder="商品名称" style={width180} /> },
  { name: fieldName.orderStatus, item: <Input placeholder="订单状态" style={width180} /> },
]
