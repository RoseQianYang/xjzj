import React from 'react'
import { Input } from 'antd'
import { lineManage as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.lineName, item: <Input placeholder="线路名称" style={width180} /> },
  { name: fieldName.address, item: <Input placeholder="线路地址" style={width180} /> },
]