import React, { PureComponent } from 'react'
import { Form, Input, Row, Col, Button, Select } from 'antd'
import { textAlignCenter } from '../../../../const/style'
import { admin as fieldName } from '../../../../const/fieldName'
import { formItemHalf, formItemFull, textAreaSize } from '../../../../const/style'
import { connect } from 'react-redux'
import axios from "axios";
import { role as api } from "../../../../const/api";

const { Item } = Form
const { TextArea } = Input
const { Option } = Select

@Form.create()
@connect(state => state.loading, null)
export default class extends PureComponent {
  constructor() {
    super()
    this.state = { confirmDirty: false, roleList: [] }
  }

  componentDidMount() {
    axios.get(api.all).then(res => {
      this.setState({ roleList: res.data.data })
    })
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const { form } = this.props
    form.validateFields((err, values) => {
      if (!err) {
        if (this.props.hasOwnProperty('record')) {
          this.props.edit({ ...values, id: this.props.record.id })
        } else {
          this.props.add(values)
        }
      }
    })
  }

  handleConfirmBlur = (e) => {
    const value = e.target.value
    this.setState({ confirmDirty: this.state.confirmDirty || !!value })
  }

  checkConfirm = (rule, value, callback) => {
    if (value && this.state.confirmDirty) {
      this.props.form.validateFields(['confirm'], { force: true })
    }
    callback()
  }

  checkPassword = (rule, value, callback) => {
    if (value && value !== this.props.form.getFieldValue(fieldName.password)) {
      callback('两次输入的密码不一致')
    } else {
      callback()
    }
  }

  render() {
    const { roleList } = this.state
    const { form, loading, record } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={12}>
            <Item {...formItemHalf} label="登录账号">
              {getFieldDecorator(fieldName.accountName, {
                rules: [{ required: true, message: '请输入登录账号' }],
                initialValue: record && record.hasOwnProperty(fieldName.accountName)
                  ? record[fieldName.accountName]
                  : undefined,
              })(
                <Input placeholder="请输入登录账号" disabled={!!record} />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="真实姓名">
              {getFieldDecorator(fieldName.realName, {
                rules: [{ required: true, message: '请输入真实姓名！' }],
                initialValue: record && record.hasOwnProperty(fieldName.realName)
                  ? record[fieldName.realName]
                  : undefined,
              })(
                <Input placeholder="请输入真实姓名" />,
              )}
            </Item>
          </Col>
          {
            !record
              ? [
                <Col span={12} key='password'>
                  <Item {...formItemHalf} label="密码">
                    {getFieldDecorator(fieldName.password, {
                      rules: [
                        { required: true, message: '密码不能为空！' },
                        { validator: this.checkConfirm },
                      ],
                    })(
                      <Input placeholder="请输入密码" type='password' />,
                    )}
                  </Item>
                </Col>,
                <Col span={12} key='confirm'>
                  <Item {...formItemHalf} label="确认密码">
                    {getFieldDecorator('confirmPassword', {
                      rules: [
                        { required: true, message: '请再次输入密码！' },
                        { validator: this.checkPassword },
                      ],
                    })(
                      <Input
                        placeholder="请再次输入密码"
                        type='password'
                        onBlur={this.handleConfirmBlur} />,
                    )}
                  </Item>
                </Col>,
              ]
              : null
          }
          <Col span={12}>
            <Item {...formItemHalf} label="电话号码">
              {getFieldDecorator(fieldName.mobile, {
                rules: [{ required: true, message: '请输入电话号码！' }],
                initialValue: record && record.hasOwnProperty(fieldName.mobile)
                  ? record[fieldName.mobile]
                  : undefined,
              })(
                <Input placeholder="请输入电话号码" />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="角色名称">
              {getFieldDecorator(fieldName.roleId, {
                rules: [{ required: true, message: '请输入角色名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.roleId)
                  ? record[fieldName.roleId]
                  : undefined,
              })(
                <Select placeholder="请输入角色名称" >
                  {
                    roleList.map(v => <Option value={v.id} key={v.id}>{v.roleName}</Option>)
                  }
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFull} label="备注">
              {getFieldDecorator(fieldName.remark, {
                rules: [{ required: true, message: '请输入备注！' }],
                initialValue: record && record.hasOwnProperty(fieldName.remark)
                  ? record[fieldName.remark]
                  : undefined,
              })(
                <TextArea placeholder="请输入备注" autosize={textAreaSize} />,
              )}
            </Item>
          </Col>
        </Row>
        <Item style={textAlignCenter}>
          <Button type='primary' htmlType="submit" loading={loading}>提交</Button>
        </Item>
      </Form>
    )
  }
}
