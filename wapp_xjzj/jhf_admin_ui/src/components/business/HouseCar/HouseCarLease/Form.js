import React, { PureComponent } from 'react';
import { Form, Input, Row, Col, InputNumber, Button } from 'antd';
import { houseCarLease as fieldName } from '../../../../const/fieldName';
import { connect } from 'react-redux';
import { formItemFull, width100p, textAlignCenter } from '../../../../const/style';
import HouseCarSelect from './HouseCarSelect';

const { Item } = Form;

@Form.create()
@connect(state => state.loading, null)
export default class extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      selectedHouseCarName: props.hasOwnProperty('record')
        ? props.record.vehicleName
        : '暂未选择',
    };
  }

  componentDidMount() {
    const { record, form } = this.props;
    if (record) {
      form.setFieldsValue({ [fieldName.vehicleId]: record.vehicleId });
    }
  }

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        values[fieldName.rentPrice] = values[fieldName.rentPrice] * 100
        if (this.props.hasOwnProperty('record')) {
          this.props.edit({ ...values, id: this.props.record.id });
        } else {
          this.props.add(values);
        }
      }
    });
  };

  selectHouseCar = ({ id, name }) => {
    this.props.form.setFieldsValue({ [fieldName.vehicleId]: id });
    this.setState({ selectedHouseCarName: name });
  };

  render() {
    const { selectedHouseCarName } = this.state;
    const { form, loading, record } = this.props;
    const { getFieldDecorator } = form;

    return (
      <Row gutter={24}>
        <Col span={12}>
          <Form onSubmit={this.handleSubmit}>
            <Row>
              <Col span={24}>
                <Item {...formItemFull} label="房车名称">
                  <span>{selectedHouseCarName}</span>
                  {getFieldDecorator(fieldName.vehicleId, {
                    rules: [{ required: true, message: '请选择房车！' }],
                    initialValue: record && record.hasOwnProperty(fieldName.vehicleId)
                      ? record[fieldName.vehicleId]
                      : undefined,
                  })(
                    <Input type='hidden' />,
                  )}
                </Item>
              </Col>
              <Col span={24}>
                <Item {...formItemFull} label="租赁价格">
                  {getFieldDecorator(fieldName.rentPrice, {
                    rules: [{ required: true, message: '请输入租赁价格！' }],
                    initialValue: record && record.hasOwnProperty(fieldName.rentPrice)
                      ? record[fieldName.rentPrice] / 100
                      : undefined,
                  })(
                    <InputNumber style={width100p} placeholder='请输入租赁价格' />,
                  )}
                </Item>
              </Col>
              <Col span={24}>
                <Item {...formItemFull} label="联系地址">
                  {getFieldDecorator(fieldName.address, {
                    rules: [{ required: true, message: '请输入联系地址！' }],
                    initialValue: record && record.hasOwnProperty(fieldName.address)
                      ? record[fieldName.address]
                      : undefined,
                  })(
                    <Input placeholder='请输入联系地址' />,
                  )}
                </Item>
              </Col>
              <Col span={24}>
                <Item {...formItemFull} label="联系电话">
                  {getFieldDecorator(fieldName.phone, {
                    rules: [{ required: true, message: '请输入联系电话！' }],
                    initialValue: record && record.hasOwnProperty(fieldName.phone)
                      ? record[fieldName.phone]
                      : undefined,
                  })(
                    <Input placeholder='请输入联系电话' />,
                  )}
                </Item>
              </Col>
              <Col span={24}>
                <Item style={textAlignCenter}>
                  <Button type='primary' htmlType="submit" loading={loading}>提交</Button>
                </Item>
              </Col>
            </Row>
          </Form>
        </Col>
        <Col span={12} style={{ borderLeft: '1px dashed #e8e8e8' }}>
          <HouseCarSelect selectHouseCar={this.selectHouseCar} />
        </Col>
      </Row>
    );
  }
}

