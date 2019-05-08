import React from 'react'
import { Input } from 'antd'
import { role as fieldName } from '../../../../const/fieldName'
import { width180 } from '../../../../const/style'

export default [
  { name: fieldName.roleName, item: <Input placeholder="角色名称" style={width180} /> },
  { name: fieldName.permissions, item: <Input placeholder="操作权限" style={width180} /> },
  { name: fieldName.menus, item: <Input placeholder="菜单权限" style={width180} /> },
]