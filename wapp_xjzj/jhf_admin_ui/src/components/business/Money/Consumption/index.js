// 消费统计
import React, { PureComponent } from 'react'
import { Card, Divider } from 'antd'
import search from './search'
import SearchComponent from '../../../public/SearchComponent'
import btnGroup from './btnGroup'
import BtnGroupComponent from '../../../public/BtnGroupComponent'
import columns from './columns'
import TableComponent from '../../../public/TableComponent'
import { initState } from '../../../../const/initState'
import { consumption as api } from '../../../../const/api'
import { axiosPost } from '../../../../utils/request'
import { connect } from 'react-redux'

@connect(state => state.loading, null)
export default class Consumption extends PureComponent {
  constructor() {
    super()
    this.state = { ...initState }
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state
    axiosPost(api.get, { page: pagination.current }, data => {
      if (data.hasOwnProperty('data')) {
        this.setState({
          dataSource: data.data.list,
          pagination: { ...pagination, total: data.data.total },
        })
      }
    }, false)
  }

  // 选择某行数据
  rowSelect = record => this.setState({ record })

  // 提交搜索
  searchSubmit = values => {
    const { pagination, search } = this.state
    axiosPost(api.get, { ...values, currentPage: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: { ...pagination, total: data.data.total, current: 1 },
      search: { ...search, ...values },
    }), false)
  }

  // 清空搜索条件
  searchClear = () => this.setState({ search: {} })

  // 表格分页、排序、筛选变化
  handleTableChange = (page, filters, sorter) => {
    const { pagination, search } = this.state
    const data = { ...search, currentPage: page.current }
    const callback = data => this.setState({
      dataSource: data.data.list,
      pagination: {
        ...pagination,
        total: data.data.total,
        current: page.current,
      },
    })
    axiosPost(api.get, data, callback, false)
  }

  // 点击按钮的回调
  showModal = title => this.setState({ modalVisible: true, modalTitle: title })

  // 删除行
  deleteRow = record => {
    const callback = () => {
      const { dataSource } = this.state
      for (let i in dataSource) {
        if (dataSource[i].id === record.id) {
          dataSource.splice(i, 1)
          this.setState({ dataSource: [...dataSource], record: {} })
          break
        }
      }
    }
    axiosPost(api.remove, { id: record.id }, callback, true)
  }

  render() {
    const { loading } = this.props
    const { dataSource, pagination, record } = this.state
    const table = { columns, dataSource, pagination, loading }
    return (
      <Card>
        <SearchComponent
          loading={loading}
          search={search}
          searchSubmit={this.searchSubmit}
          searchClear={this.searchClear} />
        <Divider dashed />
        <BtnGroupComponent
          deleteRow={this.deleteRow}
          showModal={this.showModal}
          btnGroup={btnGroup}
          record={record} />
        <Divider dashed />
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange}
          rowSelect={this.rowSelect} />
      </Card>
    )
  }
}


