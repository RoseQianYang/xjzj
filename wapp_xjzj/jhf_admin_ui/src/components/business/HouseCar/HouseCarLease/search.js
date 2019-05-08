import React from 'react';
import { Input } from 'antd';
import { houseCarLease as fieldName } from '../../../../const/fieldName';
import { width180 } from '../../../../const/style';

export default [
  { name: fieldName.vehicleName, item: <Input placeholder="房车名称" style={width180} /> },
];
