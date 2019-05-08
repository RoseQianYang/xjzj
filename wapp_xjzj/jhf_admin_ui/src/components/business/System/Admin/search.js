import React from 'react'
import { Input } from 'antd'
import { admin as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.realName, item: <Input placeholder="真实姓名" style={width180} /> },
  { name: fieldName.mobile, item: <Input placeholder="电话号码" style={width180} /> },
]
