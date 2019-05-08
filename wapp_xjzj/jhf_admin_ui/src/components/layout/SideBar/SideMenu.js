import React, { PureComponent } from 'react'
import { Menu, Icon } from 'antd'
import { Link, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import { addTab, getNewTab } from '../../../redux/reducers/tabs'
import { fullMenu } from '../../../const/menu'
import { filterMenu } from '../../../utils/func'

const { SubMenu, Item } = Menu

@withRouter
@connect(state => state.login, { addTab })
export default class SideMenu extends PureComponent {
  constructor(props) {
    super(props)
    this.state = {
      openKeys: [],
      selectedKeys: [],
      rootSubmenuKeys: [],
      userMenu: props.user ? filterMenu(fullMenu, JSON.parse(props.user.menus)) : []
    }
    this.onOpenChange = this.onOpenChange.bind(this)
    this.onMenuClick = this.onMenuClick.bind(this)
  }

  componentDidMount() {
    const rootSubmenuKeys = []
    this.state.userMenu.forEach(v => rootSubmenuKeys.push(v.code))
    this.setState({ rootSubmenuKeys })
  }

  componentWillMount() {
    const { pathname } = this.props.location
    const { userMenu } = this.state

    if (pathname === '/' || pathname === '/login') return false

    let newTab = getNewTab(pathname)

    if (!newTab && userMenu.length) newTab = userMenu[0].subMenu[0]

    const openKeys = newTab.link.split('/')[2]

    this.setState({ openKeys: [openKeys], selectedKeys: [newTab.link] })
    this.props.addTab(newTab)
  }

  componentWillReceiveProps(nextProps) {
    const { pathname } = nextProps.location
    const pathArr = pathname.split('/')
    this.setState({
      selectedKeys: [pathname],
      openKeys: [`${pathArr[2]}`],
    })
  }

  onOpenChange(openKeys) {
    const latestOpenKey = openKeys.find(key => this.state.openKeys.indexOf(key) === -1)
    if (this.state.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
      this.setState({ openKeys })
    } else {
      this.setState({ openKeys: latestOpenKey ? [latestOpenKey] : [] })
    }
  }

  onMenuClick(target) {
    this.props.addTab(getNewTab(target.key))
  }

  render() {
    return (
      <Menu
        className='jhf-sidebar-menu'
        mode="inline"
        selectedKeys={this.state.selectedKeys}
        openKeys={this.state.openKeys}
        onOpenChange={this.onOpenChange}
        onClick={this.onMenuClick}
      >
        {
          this.state.userMenu.filter(v => v.auth).map(i =>
            <SubMenu key={i.code} title={
              <span><Icon type={i.icon} />{i.name}</span>}>
              {
                i.subMenu.filter(v => v.auth).map(j =>
                  <Item key={j.link}>
                    <Link to={j.link}>{j.name}</Link>
                  </Item>
                )
              }
            </SubMenu>
          )
        }
      </Menu>
    )
  }
}
