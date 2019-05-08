// 销售明细
import React from 'react';
import moment from "moment"
import search from './search'
import columns from './columns'
import SearchComponent from '../../../public/SearchComponent'
import TableComponent from '../../../public/TableComponent'
import { Row, Col, Card, Divider } from "antd"
import { connect } from 'react-redux'
import { axiosPost } from '../../../../utils/request'
import { initState } from '../../../../const/initState'

const api = {
  get: '/jhf/api/adm/stats/getOrderDetailList.do',
  output: '/jhf/api/adm/stats/exportExcel.do',
}

@connect(state => state.loading, null)
class SaleDetail extends React.Component {
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
    let searchData = {}

    if (values.hasOwnProperty('range') && !!values.range) {
      searchData.startTime = moment(values.range[0]).format('YYYY-MM-DD')
      searchData.endTime = moment(values.range[1]).format('YYYY-MM-DD')
    }

    axiosPost(api.get, { ...searchData, page: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: {
        ...pagination,
        total: data.data.totalSize,
        current: 1
      },
      search: { ...search, ...searchData },
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
        <Row>
          <Col span={9} style={{ marginTop: -6 }}>
            <SearchComponent
              loading={loading}
              search={search}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear} />
          </Col>
          <Col span={4} style={{ marginTop: 2 }}>
            <a
              // href={`${api.output}?jsonInfo=${JSON.stringify(this.state.search)}`}
              href={`${api.output}`}
              target="_blank">
              导出明细
            </a>
          </Col>
        </Row>
        <Divider dashed />
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange} />
      </Card>
    );
  }
}

export default SaleDetail;