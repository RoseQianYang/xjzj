import React from 'react'
import { DatePicker } from 'antd'

const { RangePicker } = DatePicker

export default [
  {
    name: 'range',
    item: <RangePicker
      style={{ width: 240 }}
      format="YYYY-MM-DD"
      placeholder={['开始时间', '结束时间']}
      allowClear
    />
  },
]