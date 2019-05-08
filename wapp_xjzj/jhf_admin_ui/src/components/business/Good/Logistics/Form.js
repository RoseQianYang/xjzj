import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, Button } from 'antd';
import { textAlignCenter } from '../../../../const/style';
import { logistics as fieldName } from '../../../../const/fieldName';
import { formItemHalf } from '../../../../const/style';

const { Item } = Form;

@Form.create({
  mapPropsToFields(props) {
    const { record } = props;
    const { createFormField } = Form;
    return {
      [fieldName.logisticsCode]: createFormField({ value: record[fieldName.logisticsCode] }),
    };
  },
})
export default class LogisticsForm extends PureComponent {
  handleSubmit = (e) => {
    e.preventDefault();
    const { add, edit, record, form } = this.props;
    form.validateFields((err, values) => {
      if (!err) {
        if (record.id) {
          edit({ ...values, id: record.id });
        } else {
          add(values);
        }
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={12}>
            <Item {...formItemHalf} label="物流名称">
              {getFieldDecorator(fieldName.logisticsName, {
                rules: [{ required: true, message: '请输入物流名称！' }],
              })(
                <Input placeholder="请输入物流名称" />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="物流编号">
              {getFieldDecorator(fieldName.logisticsCode, {
                rules: [{ required: true, message: '请输入物流编号！' }],
              })(
                <Input placeholder="请输入物流编号" />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="省内首重价格">
              {getFieldDecorator(fieldName.firstWeightPriceInside, {
                rules: [{ required: true, message: '请输入省内首重价格！' }],
              })(
                <Input placeholder="请输入省内首重价格" />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="省外首重价格">
              {getFieldDecorator(fieldName.firstWeightPriceOutside, {
                rules: [{ required: true, message: '请输入省外首重价格！' }],
              })(
                <Input placeholder="请输入省外首重价格" />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="省内续重价格">
              {getFieldDecorator(fieldName.continuousWeightPrice, {
                rules: [{ required: true, message: '请输入省内续重价格！' }],
              })(
                <Input placeholder="请输入省内续重价格" />,
              )}
            </Item>
          </Col>
        </Row>
        <Item style={textAlignCenter}>
          <Button type='primary' htmlType="submit">提交</Button>
        </Item>
      </Form>
    );
  }
}
