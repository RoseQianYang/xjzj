import React from 'react'
import { Layout } from 'antd'
import TabsBar from './TabsBar'

const { Content } = Layout
const style = {
  layout: { padding: 0 },
  content: { background: '#f2f6fa', padding: 0, margin: 0, minHeight: 280 },
}

export default () => (
  <Layout style={style.layout}>
    <Content style={style.content}>
      <TabsBar />
    </Content>
  </Layout>
)
