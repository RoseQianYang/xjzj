import React, { PureComponent } from 'react'
import { Form, Input, Row, Col, Button, Modal, Alert } from 'antd'
import { lineManage as fieldName } from '../../../../const/fieldName'
import { formItemHalfLarge, formItemFullLarge, textAlignCenter, textAreaSize } from '../../../../const/style'
import { connect } from 'react-redux'
import MapSelector from '../../../public/MapSelector/MapSelector'
import ImageSelector from '../../../public/ImgSelector'
import ImageDisplay from '../../../public/ImageDisplay'
import TinyMCE from 'react-tinymce'
import { tinymceConfig } from '../../../../const/tinymceConfig'

const { Item } = Form

@Form.create()
@connect(state => state.loading, null)
export default class extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      content: props.hasOwnProperty('record') && props.record.content ? props.record.content : '',
      cover: '',
      imgList: [],
    };
  }

  componentDidMount() {
    const { record } = this.props

    if (record && record.hasOwnProperty(fieldName.cover)) {
      this.setState({ cover: record[fieldName.cover] })
    }

    if (record && record.hasOwnProperty(fieldName.imageId)) {
      const imgList = record[fieldName.imageId].split(',')

      this.setState({ imgList })
    }

  }

  handleSubmit = (e) => {
    e.preventDefault()
    this.props.form.validateFields((err, values) => {
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
    const { content, cover, imgList } = this.state;
    const { form, loading, record } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="线路名称">
              {getFieldDecorator(fieldName.lineName, {
                rules: [{ required: true, message: '请输入线路名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.lineName)
                  ? record[fieldName.lineName]
                  : undefined,
              })(
                <Input placeholder="请输入线路名称" />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="联系方式">
              {getFieldDecorator(fieldName.phone, {
                rules: [{ required: true, message: '请输入联系方式！' }],
                initialValue: record && record.hasOwnProperty(fieldName.phone)
                  ? record[fieldName.phone]
                  : undefined,
              })(
                <Input placeholder="请输入联系方式" />,
              )}
            </Item>
          </Col>
          <Col span={24} >
            <Item {...formItemFullLarge} label="线路简介">
              {getFieldDecorator(fieldName.introduction, {
                rules: [{ required: true, message: '请输入线路简介！' }],
                initialValue: record && record.hasOwnProperty(fieldName.introduction)
                  ? record[fieldName.introduction]
                  : undefined,
              })(
                <Input.TextArea autosize={textAreaSize} placeholder="请输入线路简介" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="线路地址">
              <Button
                type='primary'
                onClick={() => {
                  const modal = Modal.info({
                    title: '选择地址',
                    content: <MapSelector
                      uid={record ? record.id : null}
                      address={form.getFieldValue(fieldName.address)}
                      latitude={form.getFieldValue('latitude')}
                      longitude={form.getFieldValue('longitude')}
                      select={item => {
                        form.setFieldsValue({
                          [fieldName.address]: `${item.city} ${item.district} ${item.name}`,
                          // [fieldName.latitude]: item.location.lat,
                          // [fieldName.longitude]: item.location.lng,
                        })
                        modal.destroy()
                      }} />,
                    okText: '确认',
                    onOk: () => modal.destroy(),
                    maskClosable: true,
                    width: 960,
                  });
                }}>选择地址</Button>
              &nbsp;&nbsp;
              <span>{form.getFieldValue(fieldName.address)}</span>
              {getFieldDecorator(fieldName.address, {
                rules: [{ required: true, message: '请输入线路地址！' }],
                initialValue: record && record.hasOwnProperty(fieldName.address)
                  ? record[fieldName.address]
                  : undefined,
              })(
                <Input type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="线路封面">
              <ImageSelector selectImg={img => {
                form.setFieldsValue({ [fieldName.cover]: img.imgSrc });
                this.setState({ cover: img.imgSrc })
              }} />
              <Alert banner type="info" message="建议线路图片尺寸为750*420px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={cover && cover.length ? [cover] : []}
                remove={i => {
                  this.setState({ cover: undefined })
                  form.setFieldsValue({ [fieldName.cover]: undefined });
                }}
              />
              {getFieldDecorator(fieldName.cover, {
                rules: [{ required: true, message: '请选择线路封面图片！' }],
                initialValue: record && record.hasOwnProperty(fieldName.cover)
                  ? record[fieldName.cover]
                  : undefined,
              })(
                <Input type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="线路图片">
              <ImageSelector selectImg={img => {
                const imageId = form.getFieldValue(fieldName.imageId) || '';
                const hasComma = imageId.length ? ',' : '';
                form.setFieldsValue({ [fieldName.imageId]: imageId + hasComma + img.imgSrc });
                imgList.push(img.imgSrc)
                this.setState({ imgList })
              }} />
              <Alert banner type="info" message="建议线路图片尺寸为750*420px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={imgList}
                remove={i => {
                  imgList.splice(i, 1)
                  const imgStr = imgList.join(',')
                  this.setState({ imgList })
                  form.setFieldsValue({ 'imageId': imgStr || undefined })
                }}
              />
              {getFieldDecorator(fieldName.imageId, {
                rules: [{ required: true, message: '请选择线路图片！' }],
                initialValue: record && record.hasOwnProperty(fieldName.imageId)
                  ? record[fieldName.imageId]
                  : undefined,
              })(
                <Input type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="图文详情">
              <TinyMCE
                content={content}
                config={tinymceConfig}
                onChange={e => form.setFieldsValue({ [fieldName.content]: e.target.getContent() })}
              />
              {getFieldDecorator(fieldName.content, {
                rules: [{ required: true, message: '请输入图文详情！' }],
                initialValue: record && record.hasOwnProperty(fieldName.content)
                  ? record[fieldName.content]
                  : undefined,
              })(
                <Input type='hidden' />,
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
