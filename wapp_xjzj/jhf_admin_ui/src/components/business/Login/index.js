import React from 'react'
import { Layout } from 'antd'
import Logo from './Logo'
import LoginForm from './Form'
import Footer from '../../layout/Footer/index'

const { Content } = Layout
const bgImg = require('../../../assets/bg.svg')
const style = {
  layout: {
    backgroundImage: `url(${bgImg})`,
    backgroundColor: '#f0f2f5',
    minHeight: '100%',
    display: 'flex',
    flexDirection: 'column',
  },
  content: { flex: 1 },
}

export default () => (
  <Layout style={style.layout}>
    <Content style={style.content}>
      <Logo />
      <LoginForm />
    </Content>
    <Footer />
  </Layout>
)