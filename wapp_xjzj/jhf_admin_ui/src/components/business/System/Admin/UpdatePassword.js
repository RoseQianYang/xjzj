import React from "react";
import { Form, Button, Row, Col, Input } from "antd";
import { connect } from "react-redux";
import { formItemHalf, textAlignCenter } from '../../../../const/style'

const { Item } = Form

@Form.create()
@connect(state => state.loading, null)
export default class UpdatePassword extends React.Component {
  constructor() {
    super()
    this.state = { confirmDirty: false }
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const { form, id } = this.props
    form.validateFields((err, values) => {
      if (!err) {
        delete values.confirmPassword
        this.props.updatePassword({ id, ...values })
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
    } else {
      callback()
    }
  }

  checkPassword = (rule, value, callback) => {
    if (value && value !== this.props.form.getFieldValue('newPassword')) {
      callback('两次输入的密码不一致')
    } else {
      callback()
    }
  }

  render() {
    const { form, loading } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={12}>
            <Item {...formItemHalf} label="旧密码">
              {getFieldDecorator('oldPassword', {
                rules: [
                  { required: true, message: '旧密码不能为空！' },
                  { validator: this.checkConfirm },
                ],
              })(
                <Input placeholder="请输入旧密码" type='password' />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="新密码">
              {getFieldDecorator('newPassword', {
                rules: [
                  { required: true, message: '新密码不能为空！' },
                  { validator: this.checkConfirm },
                ],
              })(
                <Input placeholder="请输入新密码" type='password' />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="确认密码">
              {getFieldDecorator('confirmPassword', {
                rules: [
                  { required: true, message: '请再次输入密码！' },
                  { validator: this.checkPassword },
                ],
              })(
                <Input placeholder="请再次输入密码" type='password' onBlur={this.handleConfirmBlur} />,
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