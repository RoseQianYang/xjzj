// 车友分享
import React, { PureComponent } from 'react'
import { Row, Table, /* Popconfirm, */ Card, Divider } from 'antd'
import search from './search'
// import columns from './columns'
import SearchComponent from '../../public/SearchComponent'
import ModalComponent from '../../public/ModalComponent'
import Detail from './detail'
import { initState } from '../../../const/initState'
import { tourism as api } from '../../../const/api'
import { axiosPost } from '../../../utils/request'
import { connect } from 'react-redux'

@connect(state => state.loading, null)
export default class Tourism extends PureComponent {
  constructor() {
    super()
    this.state = { ...initState, columns: [] }
    this.changeIsShow = this.changeIsShow.bind(this)
    this.changeIsEnabled = this.changeIsEnabled.bind(this)
    this.handleTableChange = this.handleTableChange.bind(this)
    // this.columnsWithOperator = this.columnsWithOperator.bind(this)
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state
    // this.columnsWithOperator()
    axiosPost(api.get, { page: pagination.current }, data => {
      if (data.hasOwnProperty('data')) {
        this.setState({
          dataSource: data.data.list,
          pagination: { ...pagination, total: data.data.total },
        })
      }
    }, false)
  }

  // 提交搜索
  searchSubmit = values => {
    const { pagination, search } = this.state
    axiosPost(api.get, { ...values, page: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: { ...pagination, total: data.data.total, current: 1 },
      search: { ...search, ...values },
    }), false)
  }

  // 清空搜索条件
  searchClear = () => this.setState({ search: {} })

  // 表格分页、排序、筛选变化
  handleTableChange(page, filters, sorter) {
    const { pagination, search } = this.state
    const data = { ...search, page: page.current }
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

  // 修改首页是否显示
  changeIsShow(id, isShow) {
    const { dataSource } = this.state
    const callback = data => {
      for (let i in dataSource) {
        if (dataSource[i].id === id) {
          dataSource[i] = { ...dataSource[i], ...data.data }
          this.setState({ dataSource: [...dataSource] })
          break
        }
      }
    }
    axiosPost(api.audit, { id, isShow: isShow === "Y" ? "N" : "Y" }, callback, true)
  }

  // 修改分享挂起状态
  changeIsEnabled(id, isEnabled) {
    const { dataSource } = this.state
    const callback = data => {
      for (let i in dataSource) {
        if (dataSource[i].id === id) {
          dataSource[i] = { ...dataSource[i], ...data.data }
          this.setState({ dataSource: [...dataSource] })
          break
        }
      }
    }
    axiosPost(api.enabled, { id, isEnabled: isEnabled === "Y" ? "N" : "Y" }, callback, true)
  }

  // 操作按钮
  // columnsWithOperator() {
  //   columns.push({
  //     title: '操作',
  //     dataIndex: 'operator',
  //     width: 180,
  //     render: (text, record, index) => (
  //       <span>
  //         <a onClick={() => this.changeIsShow(record.id, record.isShow)}>
  //           {record.isShow === "Y" ? '取消显示' : '首页显示'}
  //         </a>
  //         &nbsp;&nbsp;
  //         <a onClick={() => this.changeIsEnabled(record.id, record.isEnabled)}>
  //           {record.isEnabled === "Y" ? '取消挂起' : '挂起分享'}
  //         </a>
  //       </span>
  //     ),
  //   })
  //   this.setState({ columns })
  // }

  columns = [
    { title: '用户名', dataIndex: 'suserName', width: 120 },
    {
      title: '分享分类', dataIndex: 'categoryId', width: 120, render: categoryId => {
        switch (categoryId) {
          case 1:
            return <span>结伴出游</span>
          case 2:
            return <span>营地分享</span>
          default:
            return <span>数据错误</span>;
        }
      }
    },
    { title: '发布地址', dataIndex: 'publishAddress' },
    { title: '分享时间', dataIndex: 'publishTime', width: 160 },
    {
      title: '是否首页显示', dataIndex: 'isShow', width: 120, render: isShow => {
        switch (isShow) {
          case 'Y':
            return <span>是</span>;
          case 'N':
            return <span>否</span>;
          default:
            return <span>数据错误</span>;
        }
      }
    },
    {
      title: '是否挂起', dataIndex: 'isEnabled', width: 120, render: isEnabled => {
        switch (isEnabled) {
          case 'Y':
            return <span>是</span>;
          case 'N':
            return <span>否</span>;
          default:
            return <span>数据错误</span>;
        }
      }
    },
    {
      title: '操作',
      dataIndex: 'operator',
      width: 180,
      render: (text, record, index) => (
        <span>
          <a onClick={() => this.changeIsShow(record.id, record.isShow)}>
            {record.isShow === "Y" ? '取消显示' : '首页显示'}
          </a>
          &nbsp;&nbsp;
              <a onClick={() => this.changeIsEnabled(record.id, record.isEnabled)}>
            {record.isEnabled === "Y" ? '取消挂起' : '挂起分享'}
          </a>
        </span>
      ),
    }
  ]

  render() {
    const { loading } = this.props
    const { dataSource, pagination, modalVisible, modalTitle } = this.state
    const columns = this.columns
    const table = { columns, dataSource, pagination, loading }
    return (
      <Card>
        <SearchComponent
          search={search}
          loading={loading}
          searchSubmit={this.searchSubmit}
          searchClear={this.searchClear} />
        <Divider dashed />
        <Row>
          <Table
            size='middle'
            rowKey='id'
            scroll={{ y: 360 }}
            onChange={
              (pagination, filters, sorter) => this.handleTableChange(pagination, filters, sorter)
            }
            {...table}
          />
        </Row>
        <ModalComponent
          width={480}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          <Detail />
        </ModalComponent>
      </Card>
    )
  }
}


