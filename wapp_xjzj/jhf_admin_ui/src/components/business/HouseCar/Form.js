import React from 'react'
import { Form, Input, Row, Col, Button, Select, InputNumber, Alert } from 'antd'
import { formItemHalfLarge, formItemFullLarge, textAlignCenter, textAreaSize, width100p } from '../../../const/style'
import { houseCar as fieldName } from '../../../const/fieldName'
import ImageSelector from '../../public/ImgSelector'
import ImageDisplay from '../../public/ImageDisplay'
import TinyMCE from 'react-tinymce';
import { tinymceConfig } from '../../../const/tinymceConfig';

const { Item } = Form
const { TextArea } = Input
const { Option } = Select

@Form.create()
export default class extends React.PureComponent {
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

    if (record && record.hasOwnProperty('imageId')) {
      const imgList = record.imageId.split(',')

      this.setState({ imgList })
    }

  }

  handleSubmit = e => {
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
    const { form, loading, brandList, record } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="房车名称">
              {getFieldDecorator(fieldName.houseCarName, {
                rules: [{ required: true, message: '请输入房车名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.houseCarName)
                  ? record[fieldName.houseCarName]
                  : undefined,
              })(
                <Input placeholder="请输入房车名称" />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="房车品牌">
              {getFieldDecorator(fieldName.vehicleBrandId, {
                rules: [{ required: true, message: '请输入房车品牌！' }],
                initialValue: record && record.hasOwnProperty(fieldName.vehicleBrandId)
                  ? record[fieldName.vehicleBrandId]
                  : undefined,
              })(
                <Select placeholder="请输入房车品牌">
                  {
                    brandList.map(v => <Option value={v.id} key={v.id}>{v.brandName}</Option>)
                  }
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="房车介绍">
              {getFieldDecorator(fieldName.houseCarIntro, {
                rules: [{ required: true, message: "请输入房车介绍！" }],
                initialValue: record && record.hasOwnProperty(fieldName.houseCarIntro)
                  ? record[fieldName.houseCarIntro]
                  : undefined,
              })(
                <TextArea placeholder="房车介绍" autosize={textAreaSize} />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="房车封面">
              <ImageSelector selectImg={img => {
                form.setFieldsValue({ [fieldName.cover]: img.imgSrc });
                this.setState({ cover: img.imgSrc })
              }} />
              <Alert banner type="info" message="建议房车封面图片尺寸为750*420px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={cover && cover.length ? [cover] : []}
                remove={i => {
                  this.setState({ cover: undefined })
                  form.setFieldsValue({ [fieldName.cover]: undefined });
                }}
              />
              {getFieldDecorator(fieldName.cover, {
                rules: [{ required: true, message: '请选择房车封面图片！' }],
                initialValue: record && record.hasOwnProperty(fieldName.cover)
                  ? record[fieldName.cover]
                  : undefined,
              })(
                <Input type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="官方指导价">
              {getFieldDecorator(fieldName.price, {
                rules: [{ required: true, message: '请输入官方指导价！' }],
                initialValue: record && record.hasOwnProperty(fieldName.price)
                  ? record[fieldName.price]
                  : undefined,
              })(
                <InputNumber placeholder="请输入官方指导价" style={width100p} />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="燃油类型">
              {getFieldDecorator(fieldName.spec, {
                rules: [{ required: true, message: '请输入燃油类型！' }],
                initialValue: record && record.hasOwnProperty(fieldName.spec)
                  ? record[fieldName.spec]
                  : undefined,
              })(
                <Input placeholder="请输入燃油类型" />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="档位">
              {getFieldDecorator(fieldName.gears, {
                rules: [{ required: true, message: '请选择档位！' }],
                initialValue: record && record.hasOwnProperty(fieldName.gears)
                  ? record[fieldName.gears]
                  : undefined,
              })(
                <Select placeholder="请选择档位">
                  <Option value='自动挡'>自动挡</Option>
                  <Option value='手动挡'>手动挡</Option>
                  <Option value='手自一体'>手自一体</Option>
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="是否二手">
              {getFieldDecorator(fieldName.isSencond, {
                rules: [{ required: true, message: '请选择是否二手！' }],
                initialValue: record && record.hasOwnProperty(fieldName.isSencond)
                  ? record[fieldName.isSencond]
                  : undefined,
              })(
                <Select placeholder="请选择是否二手">
                  <Option value='Y'>是</Option>
                  <Option value='N'>否</Option>
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="是否促销">
              {getFieldDecorator(fieldName.isSales, {
                rules: [{ required: true, message: '请选择是否促销！' }],
                initialValue: record && record.hasOwnProperty(fieldName.isSales)
                  ? record[fieldName.isSales]
                  : undefined,
              })(
                <Select placeholder="请选择是否促销">
                  <Option value='Y'>是</Option>
                  <Option value='N'>否</Option>
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="房车类型">
              {getFieldDecorator(fieldName.functionType, {
                rules: [{ required: true, message: '请选择房车类型！' }],
                initialValue: record && record.hasOwnProperty(fieldName.functionType)
                  ? record[fieldName.functionType]
                  : undefined,
              })(
                <Select placeholder="请选择房车类型">
                  <Option value={1}>自行式</Option>
                  <Option value={2}>拖挂式</Option>
                  <Option value={3}>皮卡</Option>
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="驾照级别">
              {getFieldDecorator(fieldName.license, {
                rules: [{ required: true, message: '请选择驾照级别！' }],
                initialValue: record && record.hasOwnProperty(fieldName.license)
                  ? record[fieldName.license]
                  : undefined,
              })(
                <Select placeholder="请选择驾照级别">
                  <Option value='A1'>A1</Option>
                  <Option value='A2'>A2</Option>
                  <Option value='A3'>A3</Option>
                  <Option value='B1'>B1</Option>
                  <Option value='B2'>B2</Option>
                  <Option value='C1'>C1</Option>
                  <Option value='C2'>C2</Option>
                  <Option value='C3'>C3</Option>
                  <Option value='C4'>C4</Option>
                  <Option value='D'>D</Option>
                  <Option value='E'>E</Option>
                  <Option value='F'>F</Option>
                  <Option value='M'>M</Option>
                  <Option value='N'>N</Option>
                  <Option value='P'>P</Option>
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="床位">
              {getFieldDecorator(fieldName.bedNum, {
                rules: [{ required: true, message: '请输入床位！' }],
                initialValue: record && record.hasOwnProperty(fieldName.bedNum)
                  ? record[fieldName.bedNum]
                  : undefined,
              })(
                <InputNumber placeholder="请输入床位" style={width100p} />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="房车图片">
              <ImageSelector
                selectImg={img => {
                  const imageId = form.getFieldValue('imageId') || '';
                  const hasComma = imageId.length ? ',' : '';
                  form.setFieldsValue({ 'imageId': imageId + hasComma + img.imgSrc });
                  imgList.push(img.imgSrc)
                  this.setState({ imgList })
                }}
              />
              <Alert banner type="info" message="建议房车图片尺寸为750*420px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={imgList}
                remove={i => {
                  imgList.splice(i, 1)
                  const imgStr = imgList.join(',')
                  this.setState({ imgList })
                  form.setFieldsValue({ 'imageId': imgStr || undefined })
                }}
              />
              {getFieldDecorator('imageId', {
                rules: [{ required: true, message: '请选择房车图片！' }],
                initialValue: record && record.hasOwnProperty('imageId')
                  ? record['imageId']
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
