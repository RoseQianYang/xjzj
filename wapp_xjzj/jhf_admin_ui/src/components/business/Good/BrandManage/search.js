import React from 'react'
import { Input } from 'antd'
import { brandManage as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.title, item: <Input placeholder="品牌名称" style={width180} /> },
]