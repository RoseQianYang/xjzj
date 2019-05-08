import React from 'react'
import { Input } from 'antd'
import { houseCarBrand as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.brandName, item: <Input placeholder="品牌名称" style={width180} /> },
]
