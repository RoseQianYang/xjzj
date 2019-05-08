import React from 'react'
import { Input } from 'antd'
import { friend as fieldName } from '../../../const/fieldName'
import { width180 } from '../../../const/style'

export default [
  { name: fieldName.userName, item: <Input placeholder="用户名" style={width180} /> },
]