import React from 'react'
import { Input } from 'antd'
import { service as fieldName } from '../../../const/fieldName'
import { width180 } from '../../../const/style'

export default [
  { name: fieldName.serviceName, item: <Input placeholder="网点名称" style={width180} /> },
  { name: fieldName.serviceAddress, item: <Input placeholder="网点地址" style={width180} /> },
]