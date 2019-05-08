// 管理员管理
import React, { PureComponent } from 'react'
import { Row, Col, Card, Divider } from 'antd';
import search from './search'
import SearchComponent from '../../../public/SearchComponent'
import btnGroup from './btnGroup'
import BtnGroupComponent from '../../../public/BtnGroupComponent'
// import columns from './columns'
import TableComponent from '../../../public/TableComponent'
import Form from './Form'
import ModalComponent from '../../../public/ModalComponent'
import { initState } from '../../../../const/initState'
import { admin as api } from '../../../../const/api'
import { axiosPost } from '../../../../utils/request'
import { connect } from 'react-redux'
import UpdatePassword from "./UpdatePassword";
import { admin as fieldName } from '../../../../const/fieldName'

@connect(state => state.loading, null)
export default class Admin extends PureComponent {
  constructor() {
    super()
    this.state = { ...initState, columns: [] }
    // this.columnsWithOperator = this.columnsWithOperator.bind(this)
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state
    // this.columnsWithOperator()
    axiosPost(api.get, { page: pagination.current }, data => {
      if (data.hasOwnProperty('data') && data.data) {
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

  updatePassword = values => {
    axiosPost(api.updatePassword, values, () => {
      this.setState({
        modalVisible: false,
      })
    }, true)
  }

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, record } = this.state
    switch (modalTitle) {
      case '添加':
        return <Form add={this.add} />
      case '编辑':
        return <Form record={record} edit={this.edit} />
      case '修改密码':
        return <UpdatePassword id={record.id} updatePassword={this.updatePassword} />
      default:
        return null
    }
  }

  // columnsWithOperator() {
  //   columns.push({
  //     title: '操作', dataIndex: 'operator', width: 180,
  //     render: (text, record, index) => (
  //       <span>
  //         <a onClick={
  //           () => axiosPost(
  //             '/jhf/api/adm/user/OperatorisEnabled.do',
  //             {
  //               id: record.id,
  //               isEnabled: record.isEnabled === "Y" ? "N" : "Y"
  //             },
  //             () => {
  //               const { pagination } = this.state
  //               axiosPost(api.get, { page: pagination.current }, data => {
  //                 if (data.hasOwnProperty('data') && data.data) {
  //                   this.setState({
  //                     dataSource: data.data.list,
  //                     pagination: { ...pagination, total: data.data.totalSize },
  //                   })
  //                 }
  //               }, false)
  //             })
  //         }>
  //           {record.isEnabled === 'Y' ? '冻结' : '启用'}
  //         </a>
  //       </span>
  //     ),
  //   })

  //   this.setState({ columns })
  // }

  columns = [
    { title: '登录账号', dataIndex: fieldName.accountName },
    { title: '真实姓名', dataIndex: fieldName.realName, width: 180 },
    { title: '手机号码', dataIndex: fieldName.mobile, width: 200 },
    { title: '角色名称', dataIndex: fieldName.roleName, width: 180 },
    { title: '创建时间', dataIndex: fieldName.createTime, width: 200 },
    {
      title: '操作', dataIndex: 'operator', width: 180,
      render: (text, record, index) => (
        <span>
          <a onClick={
            () => axiosPost(
              '/jhf/api/adm/user/OperatorisEnabled.do',
              {
                id: record.id,
                isEnabled: record.isEnabled === "Y" ? "N" : "Y"
              },
              () => {
                const { pagination } = this.state
                axiosPost(api.get, { page: pagination.current }, data => {
                  if (data.hasOwnProperty('data') && data.data) {
                    this.setState({
                      dataSource: data.data.list,
                      pagination: { ...pagination, total: data.data.totalSize },
                    })
                  }
                }, false)
              })
          }>
            {record.isEnabled === 'Y' ? '冻结' : '启用'}
          </a>
        </span>
      ),
    }
  ]


  render() {
    const { loading } = this.props
    const { dataSource, pagination, modalVisible, modalTitle, record } = this.state
    const columns = this.columns
    const table = { columns, dataSource, pagination, loading }
    return (
      <Card>
        <Row>
          <Col span={12} style={{ marginTop: -4 }}>
            <SearchComponent
              search={search}
              loading={loading}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear}
            />
          </Col>
          <Col span={8}>
            <BtnGroupComponent
              deleteRow={this.deleteRow}
              showModal={this.showModal}
              btnGroup={btnGroup}
              record={record}
            />
          </Col>
        </Row>
        <Divider dashed />
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange}
          rowSelect={this.rowSelect} />
        <ModalComponent
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    )
  }
}


