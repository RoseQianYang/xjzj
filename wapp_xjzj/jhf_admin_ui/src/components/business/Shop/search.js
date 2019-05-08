import React from 'react'
import { Input } from 'antd'
import { shop as fieldName } from '../../../const/fieldName'
import { width180 } from '../../../const/style'

export default [
  { name: fieldName.shopName, item: <Input placeholder="门店名称" style={width180} /> },
  { name: fieldName.shopAddress, item: <Input placeholder="门店地址" style={width180} /> },
]