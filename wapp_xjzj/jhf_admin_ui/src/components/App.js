import React from 'react'
import { Layout } from 'antd'
import Header from './layout/Header'
import SideBar from './layout/SideBar'
import Content from './layout/Content'
import './App.css'

export default () => (
  <Layout id="app">
    <Header />
    <Layout>
      <SideBar />
      <Content />
    </Layout>
  </Layout>
)