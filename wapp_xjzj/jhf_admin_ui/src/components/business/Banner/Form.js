import React, { PureComponent } from 'react'
import { Form, Input, Row, Col, Button } from 'antd'
import { textAlignCenter } from '../../../const/style'
import { banner as fieldName } from '../../../const/fieldName'
import { formItemHalf, formItemFull, textAreaSize } from '../../../const/style'

const { Item } = Form
const { TextArea } = Input

@Form.create({
  mapPropsToFields(props) {
    const { record } = props
    if (record) {
      const { createFormField } = Form
      return {
        [fieldName.title]: createFormField({ value: record[fieldName.title] }),
        [fieldName.banner]: createFormField({ value: record[fieldName.banner] }),
        [fieldName.description]: createFormField({ value: record[fieldName.description] }),
      }
    } else {
      return false
    }
  },
})
export default class extends PureComponent {
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

  render() {
    const { form, loading } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={12}>
            <Item {...formItemHalf} label="Banner名称">
              {getFieldDecorator(fieldName.title, {
                rules: [{ required: true, message: '请输入Banner名称！' }],
              })(
                <Input placeholder="请输入Banner名称" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFull} label="Banner描述">
              {getFieldDecorator(fieldName.description, {
                rules: [{ required: true, message: '请输入Banner描述！' }],
              })(
                <TextArea autosize={textAreaSize} placeholder="请输入Banner描述" />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="Banner图片">
              {getFieldDecorator(fieldName.banner, {
                rules: [{ required: true, message: '请上传Banner图片！' }],
              })(
                <Input placeholder='请上传Banner图片' />,
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
