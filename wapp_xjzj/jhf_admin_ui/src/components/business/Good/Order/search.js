import React from 'react';
import { Input, Select } from 'antd';
import { order as fieldName } from '../../../../const/fieldName';
import { width180 } from '../../../../const/style';

const { Option } = Select

export default [
  { name: fieldName.userName, item: <Input placeholder="用户名称" style={width180} /> },
  { name: 'orderNo', item: <Input placeholder="订单编号" style={width180} /> },
  {
    name: fieldName.orderStatus, item: <Select placeholder="订单状态" style={width180} >
      <Option value='1'>待支付</Option>
      <Option value='2'>支付取消</Option>
      <Option value='3'>已支付，等待发货</Option>
      <Option value='4'>已发货，等待收货</Option>
      <Option value='5'>已收货，交易完成</Option>
      <Option value='6'>超时，自动收货</Option>
    </Select>
  },
];
