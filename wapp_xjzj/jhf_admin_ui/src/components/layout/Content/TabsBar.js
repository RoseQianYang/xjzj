import React, { Component } from "react"
import { Tabs } from "antd"
import { connect } from "react-redux"
import { removeTab, changeTab } from "../../../redux/reducers/tabs"
import { Link } from "react-router-dom"
import "./TabsBar.css"

const { TabPane } = Tabs
const tabsBarStyle = { background: "#ddd", padding: "10px 10px 0" }

@connect(state => state.tabs, { removeTab, changeTab })
export default class TabsBar extends Component {
  constructor() {
    super()
    this.remove = this.remove.bind(this)
    this.onEdit = this.onEdit.bind(this)
  }

  onEdit(targetKey, action) {
    this[action](targetKey)
  }

  remove(targetKey) {
    this.props.removeTab(targetKey)
  }

  render() {
    return (
      <Tabs
        className="jhf-tabs-bar"
        onChange={this.props.changeTab}
        activeKey={this.props.activeKey}
        type="editable-card"
        onEdit={this.onEdit}
        hideAdd
        animated={false}
        tabBarStyle={tabsBarStyle}>
        {
          this.props.panes.map(pane => {
            return <TabPane
              tab={<Link to={pane.link}>{pane.name}</Link>}
              key={pane.name}>
              {pane.auth ? pane.component : <h1>403：您没有访问该页面的权限，请与管理员联系</h1>}
            </TabPane>
          })
        }
      </Tabs>
    )
  }
}
