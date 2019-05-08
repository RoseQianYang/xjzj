import React from 'react'
import { Input, Select } from 'antd'
import { width180 } from '../../../const/style'

const { Option } = Select

export default [
  { name: "suserName", item: <Input placeholder="用户名" style={width180} /> },
  {
    name: "cateId",
    item: <Select placeholder="分类" style={width180}>
      <Option value={1}>结伴出游</Option>
      <Option value={2}>营地分享</Option>
    </Select>,
  },
]
