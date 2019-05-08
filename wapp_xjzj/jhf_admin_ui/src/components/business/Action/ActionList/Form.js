import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, Button, Select, DatePicker, Alert } from 'antd';
import { formItemHalfLarge, textAlignCenter, width100p, formItemFullLarge } from '../../../../const/style';
import { actionList as fieldName } from '../../../../const/fieldName';
import ImgSelector from '../../../public/ImgSelector';
import ImageDisplay from '../../../public/ImageDisplay'
import moment from 'moment';
import { connect } from 'react-redux';
import TinyMCE from 'react-tinymce';
import { tinymceConfig } from '../../../../const/tinymceConfig';

const { Item } = Form;
const { Option } = Select;
const { RangePicker } = DatePicker;

@Form.create()
@connect(state => state.loading, null)
export default class extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      content: props.hasOwnProperty('record') && props.record.content ? props.record.content : '',
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
    e.preventDefault();
    const { form } = this.props;
    form.validateFields((err, values) => {
      if (!err) {
        delete values.timeRange;
        if (this.props.hasOwnProperty('record')) {
          this.props.edit({ ...values, id: this.props.record.id });
        } else {
          this.props.add(values);
        }
      }
    });
  };

  onTimePick = time => {
    this.props.form.setFieldsValue({
      [fieldName.startTime]: moment(time[0]).format("YYYY-MM-DD HH:mm:ss"),
      [fieldName.endTime]: moment(time[1]).format("YYYY-MM-DD HH:mm:ss"),
    });
  };

  render() {
    const { content, cover } = this.state
    const { loading, form, actionClass, record } = this.props;
    const { getFieldDecorator } = form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="活动名称">
              {getFieldDecorator(fieldName.title, {
                rules: [{ required: true, message: '请输入活动名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.title)
                  ? record[fieldName.title]
                  : undefined,
              })(
                <Input placeholder="请输入活动名称" />,
              )}
            </Item>
          </Col>
          <Col span={8}>
            <Item {...formItemHalfLarge} label="活动分类">
              {getFieldDecorator(fieldName.actionClassId, {
                rules: [{ required: true, message: '请输入活动分类！' }],
                initialValue: record && record.hasOwnProperty(fieldName.actionClassId)
                  ? record[fieldName.actionClassId]
                  : undefined,
              })(
                <Select placeholder="请输入活动分类">
                  {
                    actionClass.map(v => <Option key={v.id} value={v.id}>{v.title}</Option>)
                  }
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item
              labelCol={{ span: 4 }}
              wrapperCol={{ span: 20 }}
              hasFeedback
              label='活动时间'>
              {getFieldDecorator('timeRange', {
                rules: [{ required: true, message: '请选择活动时间！' }],
                initialValue: record
                  && record.hasOwnProperty(fieldName.startTime)
                  && record.hasOwnProperty(fieldName.endTime)
                  ? [moment(record[fieldName.startTime]), moment(record[fieldName.endTime])]
                  : undefined,
              })(
                <RangePicker
                  style={width100p}
                  format="YYYY-MM-DD HH:mm:ss"
                  onChange={this.onTimePick}
                  placeholder={['开始时间', '结束时间']}
                  allowClear
                  showTime />,
              )}
            </Item>
          </Col>
          {getFieldDecorator(fieldName.startTime, {
            initialValue: record && record.hasOwnProperty(fieldName.startTime)
              ? record[fieldName.startTime]
              : undefined,
          })(
            <Input type='hidden' />,
          )}
          {getFieldDecorator(fieldName.endTime, {
            initialValue: record && record.hasOwnProperty(fieldName.endTime)
              ? record[fieldName.endTime]
              : undefined,
          })(
            <Input type='hidden' />,
          )}
          <Col span={24}>
            <Item {...formItemFullLarge} label="活动封面">
              <ImgSelector
                selectImg={img => {
                  form.setFieldsValue({ [fieldName.cover]: img.imgSrc })
                  this.setState({ cover: img.imgSrc })
                }}
              />
              <Alert banner type="info" message="建议封面图片尺寸为750*320px" style={{ margin: '4px 0 8px' }} />
              <ImageDisplay
                imgList={cover && cover.length ? [cover] : []}
                remove={i => {
                  this.setState({ cover: undefined })
                  form.setFieldsValue({ [fieldName.cover]: undefined });
                }}
              />
              {getFieldDecorator(fieldName.cover, {
                rules: [{ required: true, message: '请上传活动封面！' }],
                initialValue: record && record.hasOwnProperty(fieldName.cover)
                  ? record[fieldName.cover]
                  : undefined,
              })(
                <Input placeholder="请上传活动封面" type="hidden" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFullLarge} label="活动内容">
              <TinyMCE
                content={content}
                config={tinymceConfig}
                onChange={e => form.setFieldsValue({ [fieldName.content]: e.target.getContent() })}
              />
              {getFieldDecorator(fieldName.content, {
                rules: [{ required: true, message: '请输入活动内容！' }],
                initialValue: record && record.hasOwnProperty(fieldName.content)
                  ? record[fieldName.content]
                  : undefined,
              })(
                <Input placeholder="请输入活动内容" type='hidden' />,
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
