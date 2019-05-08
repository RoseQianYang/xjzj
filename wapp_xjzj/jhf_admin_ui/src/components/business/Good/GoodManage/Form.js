import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, Button, InputNumber, Select, Alert, Checkbox } from 'antd';
import { formItemFullLarge, textAlignCenter } from '../../../../const/style';
import { goodManage as fieldName } from '../../../../const/fieldName';
import { formItemHalfLarge, width100p, textAreaSize } from '../../../../const/style';
import { connect } from 'react-redux';
import ImageSelector from '../../../public/ImgSelector';
import ImageDisplay from '../../../public/ImageDisplay'
import TinyMCE from 'react-tinymce';
import { tinymceConfig } from '../../../../const/tinymceConfig';

const { Item } = Form;
const { TextArea } = Input;
const { Option } = Select;

@Form.create()
@connect(state => state.loading, null)
export default class GoodManageForm extends PureComponent {
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
      for (let i in imgList) {
        imgList[i] = imgList[i]
      }
      this.setState({ imgList })
    }

  }

  handleSubmit = (e) => {
    e.preventDefault();
    const { form } = this.props;
    form.validateFields((err, values) => {
      if (!err) {
        values.hasColor = values.hasColor ? "Y" : "N"
        values.hasSize = values.hasSize ? "Y" : "N"
        if (this.props.hasOwnProperty('record')) {
          this.props.edit({ ...values, id: this.props.record.id, price: values.price * 100 });
        } else {
          this.props.add({ ...values, price: values.price * 100 });
        }
      }
    });
  };

  render() {
    const { content, cover, imgList, hasColor, hasSize } = this.state;
    const { loading, form, brand, cate, record } = this.props;
    const { getFieldDecorator } = form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="商品名称">
              {getFieldDecorator(fieldName.title, {
                rules: [{ required: true, message: '请输入商品名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.title)
                  ? record[fieldName.title]
                  : undefined,
              })(
                <Input placeholder="请输入商品名称" />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="商品分类">
              {getFieldDecorator(fieldName.cateId, {
                rules: [{ required: true, message: '请选择商品分类！' }],
                initialValue: record && record.hasOwnProperty(fieldName.cateId)
                  ? record[fieldName.cateId]
                  : undefined,
              })(
                <Select placeholder="请选择商品分类">
                  {
                    cate.map(v => <Option value={v.id} key={v.id}>{v.title}</Option>)
                  }
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="商品品牌">
              {getFieldDecorator(fieldName.brandId, {
                rules: [{ required: true, message: '请选择商品品牌！' }],
                initialValue: record && record.hasOwnProperty(fieldName.brandId)
                  ? record[fieldName.brandId]
                  : undefined,
              })(
                <Select placeholder="请选择商品品牌">
                  {
                    brand.map(v => <Option value={v.id} key={v.id}>{v.title}</Option>)
                  }
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="商品价格">
              {getFieldDecorator(fieldName.price, {
                rules: [{ required: true, message: '请输入商品价格！' }],
                initialValue: record && record.hasOwnProperty(fieldName.price)
                  ? record[fieldName.price] / 100
                  : undefined,
              })(
                <InputNumber placeholder="请输入商品价格" style={width100p} />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="是否有颜色">
              {getFieldDecorator('hasColor', {
                initialValue: record && record.hasOwnProperty('hasColor')
                  ? record.hasColor === "Y"
                  : undefined,
              })(
                <Checkbox defaultChecked={
                  record && record.hasOwnProperty('hasColor')
                    ? record.hasColor === "Y"
                    : undefined
                } />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="是否有尺码">
              {getFieldDecorator('hasSize', {
                initialValue: record && record.hasOwnProperty('hasSize')
                  ? record.hasSize === "Y"
                  : undefined,
              })(
                <Checkbox defaultChecked={
                  record && record.hasOwnProperty('hasSize')
                    ? record.hasSize === "Y"
                    : undefined
                } />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="商品描述">
              {getFieldDecorator(fieldName.description, {
                rules: [{ required: true, message: '请输入商品描述！' }],
                initialValue: record && record.hasOwnProperty(fieldName.description)
                  ? record[fieldName.description]
                  : undefined,
              })(
                <TextArea placeholder="请输入商品描述" autosize={textAreaSize} />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="商品封面">
              <ImageSelector selectImg={img => {
                form.setFieldsValue({ [fieldName.cover]: img.imgSrc });
                this.setState({ cover: img.imgSrc })
              }} />
              <Alert banner type="info" message="建议封面图片尺寸为750*320px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={cover && cover.length ? [cover] : []}
                remove={i => {
                  this.setState({ cover: undefined })
                  form.setFieldsValue({ [fieldName.cover]: undefined });
                }} />
              {getFieldDecorator(fieldName.cover, {
                rules: [{ required: true, message: '请选择商品封面图片！' }],
                initialValue: record && record.hasOwnProperty(fieldName.cover)
                  ? record[fieldName.cover]
                  : undefined,
              })(
                <Input type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="商品素材图片">
              <ImageSelector selectImg={img => {
                const imageId = form.getFieldValue(fieldName.imageId) || '';
                const hasComma = imageId.length ? ',' : '';
                form.setFieldsValue({ [fieldName.imageId]: imageId + hasComma + img.imgSrc });
                imgList.push(img.imgSrc)
                this.setState({ imgList })
              }} />
              <Alert banner type="info" message="建议商品图片尺寸为750*750px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={imgList}
                remove={i => {
                  imgList.splice(i, 1)
                  const imgStr = imgList.join(',')
                  this.setState({ imgList })
                  form.setFieldsValue({ [fieldName.imageId]: imgStr || undefined })
                }} />
              {getFieldDecorator(fieldName.imageId, {
                rules: [{ required: true, message: '请选择商品素材图片！' }],
                initialValue: record && record.hasOwnProperty(fieldName.imageId)
                  ? record[fieldName.imageId]
                  : undefined,
              })(
                <Input type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="商品图文详情">
              <TinyMCE
                content={content}
                config={tinymceConfig}
                onChange={e => form.setFieldsValue({ [fieldName.content]: e.target.getContent() })}
              />
              {getFieldDecorator(fieldName.content, {
                rules: [{ required: true, message: '请输入商品图文详情！' }],
                initialValue: record && record.hasOwnProperty(fieldName.content)
                  ? record[fieldName.content]
                  : undefined,
              })(
                <Input placeholder="请输入商品图文详情" type='hidden' />,
              )}
            </Item>
          </Col>
        </Row>
        <Item style={textAlignCenter}>
          <Button type='primary' htmlType="submit" loading={loading}>提交</Button>
        </Item>
      </Form>
    );
  }
}
