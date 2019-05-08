import React from 'react'
import { Input } from 'antd'
import { imageList as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.imgName, item: <Input placeholder="图片名称" style={width180} /> },
]
