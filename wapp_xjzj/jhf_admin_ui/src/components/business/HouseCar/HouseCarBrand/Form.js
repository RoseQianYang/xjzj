import React, { PureComponent } from 'react'
import { Form, Input, Row, Col, Button, Alert } from 'antd'
import { formItemFull, textAlignCenter } from '../../../../const/style'
import { houseCarBrand as fieldName } from '../../../../const/fieldName'
import ImgSelector from '../../../public/ImgSelector'
import ImageDisplay from '../../../public/ImageDisplay'

const { Item } = Form

@Form.create()
export default class extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      cover: '',
    };
  }

  componentDidMount() {
    const { record } = this.props

    if (record && record.hasOwnProperty(fieldName.cover)) {
      this.setState({ cover: record[fieldName.cover] })
    }

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

  selectImg = src => this.props.form.setFieldsValue({ [fieldName.cover]: src })

  render() {
    const { cover } = this.state
    const { loading, form, record } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={24}>
            <Item {...formItemFull} label="品牌名称">
              {getFieldDecorator(fieldName.brandName, {
                rules: [{ required: true, message: '请输入品牌名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.brandName)
                  ? record[fieldName.brandName]
                  : undefined,
              })(
                <Input placeholder="请输入品牌名称" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFull} label="品牌Logo">
              <ImgSelector selectImg={img => {
                form.setFieldsValue({ [fieldName.cover]: img.imgSrc });
                this.setState({ cover: img.imgSrc })
              }} />
              <Alert banner type="info" message="建议Logo图片尺寸为230*170px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={cover && cover.length ? [cover] : []}
                remove={i => {
                  this.setState({ cover: undefined })
                  form.setFieldsValue({ [fieldName.cover]: undefined });
                }}
              />
              {getFieldDecorator(fieldName.cover, {
                rules: [{ required: true, message: '请选择品牌Logo图片！' }],
                initialValue: record && record.hasOwnProperty(fieldName.cover)
                  ? record[fieldName.cover]
                  : undefined,
              })(
                <Input type="hidden" />,
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
