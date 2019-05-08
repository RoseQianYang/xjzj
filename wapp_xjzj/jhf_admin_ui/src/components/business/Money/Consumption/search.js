import React from 'react'
import { Input } from 'antd'
import { consumption as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.clientName, item: <Input placeholder="客户名称" style={width180} /> },
]