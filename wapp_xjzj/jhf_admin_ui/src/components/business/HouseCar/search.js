import React from 'react'
import { Input } from 'antd'
import { houseCar as fieldName } from '../../../const/fieldName'
import { width180 } from '../../../const/style'

export default [
  { name: fieldName.houseCarName, item: <Input placeholder="房车名称" style={width180} /> },
]