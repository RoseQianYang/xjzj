import React from "react"
import Setting from './Setting'
import { Layout, Menu, Row, Col } from "antd"
import headerLogo from '../../../assets/headerLogo.png'

const { Header } = Layout
const { Item } = Menu

const style = {
  header: {
    background: '#373d41',
    height: 60,
    lineHeight: 60,
  },
  logo: {
    width: 160,
    height: 60,
    lineHeight: '60px',
    float: 'left',
    color: '#dddddd',
  },
  menu: {
    background: '#373d41',
    lineHeight: '60px',
    borderBottom: 0,
  },
  menuItem: {
    color: '#fff',
    height: '100%',
  },
}

export default class extends React.Component {
  render() {
    return (
      <Header style={style.header}>
        <Row>
          <Col span={22}>
            <div style={style.logo}>
              <img src={headerLogo} alt="图片加载失败" />
            </div>
          </Col>
          <Col span={2}>
            <Menu theme="dark" mode="horizontal" style={style.menu}>
              <Item key="setting" style={style.menuItem}>
                <Setting />
              </Item>
            </Menu>
          </Col>
        </Row>
      </Header>
    )
  }
} 