import React from 'react'
import { Input, Select } from 'antd'
import { campsite as fieldName } from '../../../const/fieldName'
import { width180 } from '../../../const/style'

const { Option } = Select

export default [
  { name: fieldName.campsiteName, item: <Input placeholder="营地名称" style={width180} /> },
  { name: fieldName.campsiteAddress, item: <Input placeholder="营地地址" style={width180} /> },
  {
    name: fieldName.categoryId, item: <Select placeholder="营地分类" style={width180} >
      <Option value={1}>自由营地</Option>
      <Option value={2}>合作营地</Option>
    </Select>
  },
]