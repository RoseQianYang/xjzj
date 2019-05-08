import React from 'react'
import { Input } from 'antd'
import { banner as fieldName } from '../../../const/fieldName'
import { width180 } from '../../../const/style'

export default [
  { name: fieldName.title, item: <Input placeholder="Banner名称" style={width180} /> },
]