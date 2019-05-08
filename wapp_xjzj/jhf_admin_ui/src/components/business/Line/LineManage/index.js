// 线路管理
import React, { PureComponent } from 'react'
import { Row, Col, Card, Divider } from 'antd';
import search from './search'
import SearchComponent from '../../../public/SearchComponent'
import btnGroup from './btnGroup'
import BtnGroupComponent from '../../../public/BtnGroupComponent'
import columns from './columns'
import TableComponent from '../../../public/TableComponent'
import Form from './Form'
import ModalComponent from '../../../public/ModalComponent'
import { initState } from '../../../../const/initState'
import { lineManage as api } from '../../../../const/api'
import { axiosPost } from '../../../../utils/request'
import { connect } from 'react-redux'
import axios from 'axios'
import Detail from './Detail'

@connect(state => state.loading, null)
export default class LineManage extends PureComponent {
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
          pagination: { ...pagination, total: data.data.totalSize },
        })
      }
    }, false)
  }

  // 选择某行数据
  rowSelect = record => this.setState({ record })

  // 提交搜索
  searchSubmit = values => {
    const { pagination, search } = this.state
    axiosPost(api.get, { ...values, page: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: { ...pagination, total: data.data.totalSize, current: 1 },
      search: { ...search, ...values },
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

  // 点击按钮的回调
  showModal = title => this.setState({ modalVisible: true, modalTitle: title })

  // 添加
  add = values => {
    const { dataSource } = this.state
    const callback = data => {
      dataSource.push(data.data)
      this.setState({
        dataSource: [...dataSource],
        record: data.data,
        modalVisible: false,
      })
    }
    axiosPost(api.add, values, callback, true)
  }

  // 修改
  edit = values => {
    const { dataSource } = this.state
    const callback = data => {
      for (let i in dataSource) {
        if (dataSource[i].id === values.id) {
          dataSource[i] = { ...dataSource[i], ...data.data }
          this.setState({
            dataSource: [...dataSource],
            record: dataSource[i],
            modalVisible: false,
          })
          break
        }
      }
    }
    axiosPost(api.update, values, callback, true)
  }

  // 删除行
  deleteRow = record => {
    const callback = res => {
      if (res.status === 200) {
        const { dataSource } = this.state
        for (let i in dataSource) {
          if (dataSource[i].id === record.id) {
            dataSource.splice(i, 1)
            this.setState({ dataSource: [...dataSource], record: {} })
            break
          }
        }
      }
    }

    axios.get(api.remove, { params: { mobileRouteId: record.id } }).then(callback)
  }

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, record } = this.state
    switch (modalTitle) {
      case '添加':
        return <Form add={this.add} />
      case '编辑':
        return <Form record={record} edit={this.edit} />
      case '详情':
        return <Detail id={record.id} />;
      default:
        return null
    }
  }

  render() {
    const { loading } = this.props
    const { dataSource, pagination, modalVisible, modalTitle, record } = this.state
    const table = { columns, dataSource, pagination, loading }
    return (
      <Card>
        <Row>
          <Col span={12} style={{ marginTop: -4 }}>
            <SearchComponent
              search={search}
              loading={loading}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear} />
          </Col>
          <Col span={8} style={{ textAlign: 'left' }}>
            <BtnGroupComponent
              deleteRow={this.deleteRow}
              showModal={this.showModal}
              btnGroup={btnGroup}
              record={record} />
          </Col>
        </Row>
        <Divider dashed />
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange}
          rowSelect={this.rowSelect} />
        <ModalComponent
          width={1360}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    )
  }
}


