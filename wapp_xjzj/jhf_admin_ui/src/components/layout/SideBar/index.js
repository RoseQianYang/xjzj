import React from 'react'
import { Layout } from 'antd'
import SideMenu from './SideMenu'
import './SideBar.css'

const { Sider } = Layout

export default () => (
  <Sider width={200} id='jhf-sidebar'>
    <SideMenu />
  </Sider>
)