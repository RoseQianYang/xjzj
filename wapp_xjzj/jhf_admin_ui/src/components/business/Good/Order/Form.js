import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, Button, Select, Alert } from 'antd';
import { textAlignCenter } from '../../../../const/style';
import { order as fieldName } from "../../../../const/fieldName";
import { formItemHalf } from '../../../../const/style';
import { connect } from 'react-redux'

const { Item } = Form;
const { Option } = Select;

@Form.create()
@connect(state => state.loading, null)
export default class extends PureComponent {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const { record } = this.props
        this.props.edit({
          ...values,
          id: record.id,
          userId: record.userId,
          // userName: record.userName,
          orderTotalPrice: record.orderTotalPrice,
        });
      }
    });
  };

  render() {
    const { loading, form, record } = this.props;
    const { getFieldDecorator } = form;
    const disabled = record.orderStatus === "5"

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          {
            record.orderStatus === "5"
              ? <Alert banner message="订单已完成！" type="success" style={{ marginBottom: 10 }} />
              : <Alert banner message="警告：一旦订单交易成功将无法修改，请谨慎操作！" style={{ marginBottom: 10 }} />
          }
          <Col span={12}>
            <Item {...formItemHalf} label="订单名称">
              <span>{record[fieldName.orderName]}</span>
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="订单状态">
              {getFieldDecorator(fieldName.orderStatus, {
                rules: [{ required: true, message: '请输入订单状态！' }],
                // initialValue: record && record.hasOwnProperty(fieldName.orderStatus)
                // ? record[fieldName.orderStatus]
                // : undefined,
                initialValue: '4'
              })(
                <Select placeholder="请输入订单状态" disabled={disabled}>
                  {/* <Option value='1'>待支付</Option> */}
                  {/* <Option value='2'>支付取消</Option> */}
                  {/* <Option value='3'>已支付，等待发货</Option> */}
                  <Option value='4'>已发货，等待收款</Option>
                  {/* <Option value='5'>已收货</Option> */}
                  {/* <Option value='6'>超时，自动收货</Option> */}
                </Select>,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="物流公司">
              {getFieldDecorator(fieldName.logisticsName, {
                rules: [{ required: true, message: '请输入物流公司！' }],
                initialValue: record && record.hasOwnProperty(fieldName.logisticsName)
                  ? record[fieldName.logisticsName]
                  : undefined,
              })(
                <Input placeholder="请输入物流公司" disabled={disabled} />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="物流编号">
              {getFieldDecorator(fieldName.logisticsNo, {
                rules: [{ required: true, message: '请输入物流编号！' }],
                initialValue: record && record.hasOwnProperty(fieldName.logisticsNo)
                  ? record[fieldName.logisticsNo]
                  : undefined,
              })(
                <Input placeholder="请输入物流编号" disabled={disabled} />,
              )}
            </Item>
          </Col>
        </Row>
        <Item style={textAlignCenter}>
          <Button type='primary' htmlType="submit" loading={loading} disabled={disabled}>提交</Button>
        </Item>
      </Form>
    );
  }
}
