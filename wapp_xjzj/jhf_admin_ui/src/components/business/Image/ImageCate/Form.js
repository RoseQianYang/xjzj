import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, Button, TreeSelect } from 'antd';
import { formItemFull, textAlignCenter } from '../../../../const/style';
import { connect } from 'react-redux';

const { Item } = Form;

@Form.create()
@connect(state => state.loading, null)
export default class extends PureComponent {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        if (this.props.hasOwnProperty('title')) {
          this.props.edit({
            ...values,
            id: parseInt(this.props.record.value, 10),
            parentId: parseInt(values.parentId || 0, 10),
          });
        } else {
          this.props.add({ ...values, parentId: parseInt(values.parentId || 0, 10) });
        }
      }
    });
  };

  render() {
    const { form, treeData, loading, title, parentId } = this.props;
    const { getFieldDecorator } = form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={24}>
            <Item {...formItemFull} label="分类名称">
              {getFieldDecorator('title', {
                rules: [{ required: true, message: '请输入分类名称！' }],
                initialValue: title
                  ? title
                  : undefined,
              })(
                <Input placeholder="请输入分类名称" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFull} label="上级分类">
              {getFieldDecorator('parentId', {
                initialValue: parentId
                  ? parentId.toString()
                  : undefined,
              })(
                <TreeSelect
                  allowClear
                  placeholder="请选择上级分类">
                  {treeData}
                </TreeSelect>,
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
