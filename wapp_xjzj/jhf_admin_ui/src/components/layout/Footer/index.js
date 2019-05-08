import React from 'react'
import { Layout, Icon, Row } from 'antd'
import './Footer.css'

const { Footer } = Layout

export default () => (
  <Footer className="jg-footer">
    <Row className="jg-footer-links">
      <span>帮助</span>
      <span>隐私</span>
      <span>条款</span>
    </Row>
    <Row className="jg-footer-copyright">
      Copyright <Icon type="copyright" /> 2018 南昌云启信息技术有限公司版权所有
    </Row>
  </Footer>
)
