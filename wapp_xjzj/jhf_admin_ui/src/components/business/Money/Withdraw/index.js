// 提现申请
import React, { PureComponent } from 'react'
import { Card, Tag, Popconfirm, Button } from 'antd'
import TableComponent from '../../../public/TableComponent'
import { initState } from '../../../../const/initState'
import { withdraw as api } from '../../../../const/api'
import { withdraw as fieldName } from '../../../../const/fieldName'
import { axiosPost } from '../../../../utils/request'
import { connect } from 'react-redux'

@connect(state => state.loading, null)
export default class Withdraw extends PureComponent {
  constructor() {
    super()
    this.state = { ...initState }
  }

  // 页面初始化时获取数据
  componentDidMount() {
    this.getData()
  }

  getData = () => {
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

  columns = [
    { title: '用户名称', dataIndex: fieldName.userName },
    { title: '联系电话', dataIndex: 'mobile', width: 180 },
    { title: '兑换申请积分数', dataIndex: fieldName.conversionNum, width: 180 },
    {
      title: '兑换申请审核状态',
      dataIndex: fieldName.conversionStatus,
      width: 180,
      render: conversionStatus => conversionStatus === 'Y'
        ? <Tag color="green">已审核</Tag>
        : <Tag color="red">待审核</Tag>
    },
    { title: '申请时间', dataIndex: fieldName.createTime, width: 180 },
    { title: '审核时间', dataIndex: fieldName.updateTime, width: 180 },
    {
      title: '操作', dataIndex: 'operator', width: 180, render: (text, record, index) => (
        <Popconfirm
          title='确认审核通过？'
          okText='确认'
          cancelText='取消'
          onConfirm={() => this.audit(record)}>
          <Button
            type="primary"
            ghost
            disabled={record.conversionStatus === "Y" ? true : false}>
            {record.conversionStatus === 'Y' ? '已审核' : '审核'}
          </Button>
        </Popconfirm>
      )
    }
  ]

  audit = values => {
    axiosPost(api.audit, {
      id: values.id,
      userId: values.userId,
      userName: values.userName,
      conversionNum: values.conversionNum,
    }, res => {
      this.getData()
    })
  }

  render() {
    const { loading } = this.props
    const { dataSource, pagination, record } = this.state
    const table = { columns: this.columns, dataSource, pagination, loading, rowSelection: null, onRow: null }
    return (
      <Card>
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange}
        />
      </Card>
    )
  }
}


