// 系统设置
import React, { PureComponent } from 'react'
import { Card, Table, Form, Button, Input } from "antd";
import { connect } from 'react-redux'
import { textAlignCenter, formItemFull } from "../../../../const/style";
import Modal from "../../../public/ModalComponent";
import axios from 'axios'

const { Item } = Form

@connect(state => state.loading, null)
export default class Config extends PureComponent {
  constructor() {
    super()
    this.state = {
      dataSource: [],
      modalVisible: false,
      record: {},
    }
  }

  // 页面初始化时获取数据
  componentDidMount() {
    this.getList()
  }

  getList = () => {
    axios.get('/jhf/api/adm/sysconfig/list.do').then(res => {
      if (res.status === 200 && res.data.code === 0) {
        this.setState({ dataSource: res.data.data })
      }
    })
  }

  submit = values => {
    axios.post('/jhf/api/adm/sysconfig/save.do', values).then(res => {
      if (res.status === 200 && res.data.code === 0) {
        this.getList()
        this.cancel()
      }
    })
  }

  cancel = () => {
    this.setState({ modalVisible: false, record: {} })
  }

  render() {
    const { dataSource, modalVisible, record } = this.state
    const { loading } = this.props
    const columns = [
      { title: '参数名称', dataIndex: 'configKey' },
      { title: '返现比例', dataIndex: 'configValue' },
      {
        title: '操作', dataIndex: 'operate', render: (text, record, index) => {
          return (
            <a onClick={() => this.setState({ modalVisible: true, record })}>修改</a>
          )
        }
      },
    ]

    return (
      <Card>
        <Table
          rowKey="configKey"
          loading={loading}
          dataSource={dataSource}
          columns={columns} />
        <Modal
          width={560}
          title={'修改'}
          visible={modalVisible}
          onCancel={this.cancel}>
          <UpdateForm record={record} submit={this.submit} />
        </Modal>
      </Card>
    )
  }
}

@connect(state => state.loading, null)
@Form.create()
class UpdateForm extends React.Component {
  handleSubmit = (e) => {
    e.preventDefault()
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.props.submit(values)
      }
    })
  }

  render() {
    const { loading, form, record } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Item {...formItemFull} label="参数名称">
          <span>{record.configKey}</span>
          {getFieldDecorator('configKey', {
            rules: [{ required: true, message: '请输入参数名称！' }],
            initialValue: record && record.hasOwnProperty('configKey')
              ? record.configKey
              : undefined,
          })(
            <Input type="hidden" placeholder="请输入参数名称" />,
          )}
        </Item>
        <Item {...formItemFull} label="返现比例">
          {getFieldDecorator('configValue', {
            rules: [{ required: true, message: '请输入返现比例！' }],
            initialValue: record && record.hasOwnProperty('configValue')
              ? record.configValue
              : undefined,
          })(
            <Input placeholder="请输入返现比例" />,
          )}
        </Item>
        <Item style={textAlignCenter}>
          <Button type='primary' htmlType="submit" loading={loading}>提交</Button>
        </Item>
      </Form>
    )
  }
}
