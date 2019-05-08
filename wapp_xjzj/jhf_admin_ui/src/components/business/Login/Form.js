import React, { Component } from 'react'
import { Icon, Form, Input, Button, Card, Row, Col, Tooltip } from 'antd'
import { doLogin } from '../../../redux/reducers/login'
import { connect } from 'react-redux'
import { login as api } from '../../../const/api'
import { login as fieldName } from '../../../const/fieldName'

const { Item } = Form
const style = {
  card: { width: 420, margin: '0 auto', cursor: 'default' },
  body: { padding: '32px 24px 24px' },
  button: { width: '100%', height: 40 },
  verify: { display: 'inline-block', width: 120, height: 44 },
  img: { verticalAlign: 'top', width: '100%', height: '100%' },
}

@connect(state => state.loading, { doLogin })
@Form.create()
export default class LoginForm extends Component {
  constructor() {
    super()
    this.state = { url: '' }
  }

  componentDidMount() {
    this.setState({ url: this.captchaImg.src })
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const { form, doLogin } = this.props
    form.validateFields((err, values) => {
      if (!err) {
        doLogin(values)
      }
    })
  }

  // 重新获取验证码
  getCaptcha = () => {
    const timestamp = (new Date()).valueOf()
    this.captchaImg.src = this.state.url + '?nocache=' + timestamp
  }

  render() {
    const { loading, form } = this.props
    const { getFieldDecorator } = form

    return (
      <Card title="用户登录" style={style.card} bodyStyle={style.body} hoverable>
        <Form onSubmit={this.handleSubmit}>
          <Item hasFeedback>
            {getFieldDecorator(fieldName.userName, {
              rules: [{ required: true, message: '请输入用户名！' }],
            })(
              <Input
                disabled={loading}
                prefix={<Icon type="user" />}
                placeholder="请输入用户名（admin）"
                size="large" />,
            )}
          </Item>
          <Item hasFeedback>
            {getFieldDecorator(fieldName.password, {
              rules: [{ required: true, message: '请输入密码！' }],
            })(
              <Input
                disabled={loading}
                type="password"
                prefix={<Icon type="lock" />}
                placeholder="请输入密码（111111）"
                size="large" />,
            )}
          </Item>
          <Row gutter={16}>
            <Col span={12}>
              <Item hasFeedback>
                {getFieldDecorator(fieldName.verify, {
                  rules: [{ required: true, message: '请输入验证码！' }],
                })(
                  <Input
                    disabled={loading}
                    prefix={<Icon type="exception" />}
                    placeholder="请输入验证码"
                    size="large" />,
                )}
              </Item>
            </Col>
            <Col span={12}>
              <Tooltip placement="top" title="点击重新获取验证码">
                <a style={style.verify} onClick={this.getCaptcha}>
                  <img
                    style={style.img}
                    ref={img => this.captchaImg = img}
                    src={api.verify}
                    alt="点击获取验证码" />
                </a>
              </Tooltip>
            </Col>
          </Row>
          <Item>
            <Button
              loading={loading}
              type="primary"
              htmlType="submit"
              size="large"
              style={style.button}>登录</Button>
          </Item>
        </Form>
      </Card>
    )
  }
}