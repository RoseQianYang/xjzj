// 系统日志
import React, { PureComponent } from 'react'
import { Card, Divider } from 'antd'
import search from './search'
import SearchComponent from '../../../public/SearchComponent'
import columns from './columns'
import TableComponent from '../../../public/TableComponent'
import { initState } from '../../../../const/initState'
import { sysLog as api } from '../../../../const/api'
import { axiosPost } from '../../../../utils/request'
import { connect } from 'react-redux'
import moment from "moment";

@connect(state => state.loading, null)
export default class SysLog extends PureComponent {
  constructor() {
    super()
    this.state = { ...initState }
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state
    axiosPost(api.get, { page: pagination.current }, data => {
      this.setState({
        dataSource: data.data.list,
        pagination: { ...pagination, total: data.data.totalSize },
      })
    }, false)
  }

  // 提交搜索
  searchSubmit = values => {
    const { pagination, search } = this.state
    let data = {}

    if (values.hasOwnProperty('range') && !!values.range) {
      data.startTime = moment(values.range[0]).format('YYYY-MM-DD')
      data.endTime = moment(values.range[1]).format('YYYY-MM-DD')
    }

    axiosPost(api.get, { ...data, page: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: {
        ...pagination,
        total: data.data.totalSize,
        current: 1
      },
      search: { ...search, ...data },
    }), false)
  }

  // 清空搜索条件
  searchClear = () => this.setState({ search: {} })

  // 表格分页、排序、筛选变化
  handleTableChange = (page, filters, sorter) => {
    const { pagination, search } = this.state
    const data = { ...search, page: page.current }
    const callback = data => this.setState({
      dataSource: data.data.list,
      pagination: {
        ...pagination,
        total: data.data.totalSize,
        current: page.current,
      },
    })
    axiosPost(api.get, data, callback, false)
  }

  render() {
    const { loading } = this.props
    const { dataSource, pagination, record } = this.state
    const table = { columns, dataSource, pagination, loading, rowSelection: null, onRow: null }
    return (
      <Card>
        <SearchComponent
          loading={loading}
          search={search}
          searchSubmit={this.searchSubmit}
          searchClear={this.searchClear} />
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


