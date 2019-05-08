import React from 'react'
import { order as fieldName } from '../../../../const/fieldName';

export default [
  { title: '用户名称', dataIndex: fieldName.userName, width: 140 },
  { title: '订单编号', dataIndex: 'orderNo', width: 180 },
  {
    title: '订单状态',
    dataIndex: fieldName.orderStatus,
    width: 140,
    render: orderStatus => {
      switch (orderStatus) {
        case "1":
          return <span>待支付</span>
        case "2":
          return <span>支付取消</span>
        case "3":
          return <span>已支付，等待发货</span>
        case "4":
          return <span>已发货，等待收款</span>
        case "5":
          return <span>已收货，交易完成</span>
        case "6":
          return <span>超时，自动收货</span>
        default:
          return <span>数据错误</span>
      }
    }
  },
  { title: '物流公司', dataIndex: fieldName.logisticsName },
  { title: '物流编号', dataIndex: fieldName.logisticsNo, width: 160 },
  { title: '订单完成时间', dataIndex: fieldName.paymentTime, width: 180 },
];
