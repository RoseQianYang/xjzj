import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, Button } from 'antd';
import { formItemFull, textAlignCenter } from '../../../../const/style';
import { actionClass as fieldName } from '../../../../const/fieldName';

const { Item } = Form;

@Form.create()
export default class extends PureComponent {
  handleSubmit = (e) => {
    e.preventDefault();
    const { form } = this.props;
    form.validateFields((err, values) => {
      if (!err) {
        if (this.props.hasOwnProperty('record')) {
          this.props.edit({ ...values, id: this.props.record.id });
        } else {
          this.props.add(values);
        }
      }
    });
  };

  render() {
    const { loading, form, record } = this.props;
    const { getFieldDecorator } = form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={24}>
            <Item {...formItemFull} label="分类名称">
              {getFieldDecorator(fieldName.title, {
                rules: [{ required: true, message: '请输入分类名称！' }],
                initialValue: record && record.hasOwnProperty(fieldName.title)
                  ? record[fieldName.title]
                  : undefined,
              })(
                <Input placeholder="请输入分类名称" />,
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
