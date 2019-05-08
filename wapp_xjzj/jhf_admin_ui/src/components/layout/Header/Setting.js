import React, { PureComponent } from 'react'
import { Dropdown, Icon, Menu } from 'antd'
import { connect } from 'react-redux'
import { logout } from '../../../redux/reducers/login'

const { Item } = Menu

@connect(state => state.login, { logout })
export default class Setting extends PureComponent {
  constructor() {
    super()
    this.handleClick = this.handleClick.bind(this)
  }

  handleClick(e) {
    switch (e.key) {
      case 'logout':
        this.props.logout()
        break
      default:
        break
    }
  }

  render() {
    return this.props.user ? (
      <Dropdown overlay={
        <Menu onClick={this.handleClick}>
          <Item key="logout">退出登录</Item>
        </Menu>
      }>
        <a>{this.props.user.accountName}&nbsp;&nbsp;<Icon type='setting' /></a>
      </Dropdown>
    ) : null
  }
}