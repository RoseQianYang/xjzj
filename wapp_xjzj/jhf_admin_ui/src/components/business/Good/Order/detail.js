// 订单详情
import React, { PureComponent } from 'react';
import { Spin, Row, Col, Divider } from 'antd';
import { order as api } from "../../../../const/api";
import { axiosPost } from "../../../../utils/request";
import { connect } from "react-redux";

@connect(state => state.loading, null)
export default class extends PureComponent {
  state = { orderDetail: [] };

  componentWillMount() {
    const { id } = this.props.record;
    const callback = data => this.setState({ orderDetail: data.data });
    axiosPost(api.detail, { orderId: id }, callback);
  }

  render() {
    const { loading, record } = this.props;
    const { orderDetail } = this.state;

    return (
      <Row
        gutter={16}
        style={{
          textAlign: loading ? 'center' : 'left',
          paddingTop: loading ? 30 : 0,
        }}>
        {
          loading
            ? <Spin />
            : <div>
              <OrderDetail detail={record} />
              <GoodList goodList={orderDetail.length ? orderDetail : null} />
            </div>
        }
      </Row>
    );
  }
}

const fontBold = { fontWeight: 'bolder' };
const fontSize = { fontSize: 15 };

const OrderDetail = ({ detail }) => {
  let orderStatus
  switch (detail.orderStatus) {
    case "1":
      orderStatus = '未支付'
      break;
    case "2":
      orderStatus = '支付取消'
      break;
    case "3":
      orderStatus = '已支付，等待发货'
      break;
    case "4":
      orderStatus = '已发货，等待收款'
      break;
    case "5":
      orderStatus = '已收货，交易完成'
      break;
    default:
      orderStatus = '数据错误'
      break;
  }

  return [
    <h2 key='orderDetail'>订单详情</h2>,
    <div key='orderName' style={fontSize}>
      <span style={fontBold}>订单编号：</span>{detail.orderNo}
    </div>,
    <div key='logisticsName' style={fontSize}>
      <span style={fontBold}>物流公司：</span>{detail.logisticsName}
    </div>,
    <div key='logisticsNo' style={fontSize}>
      <span style={fontBold}>物流单号：</span>{detail.logisticsNo}
    </div>,
    <div key='orderStatus' style={fontSize}>
      <span style={fontBold}>订单状态：</span>{orderStatus}
    </div>,
    <div key='paymentTime' style={fontSize}>
      <span style={fontBold}>订单完成时间：</span>{detail.paymentTime}
    </div>,
    <div key='userName' style={fontSize}>
      <span style={fontBold}>用户名：</span>{detail.userName}
    </div>,
    <div key='remark' style={fontSize}>
      <span style={fontBold}>买家留言：</span>{detail.remark}
    </div>,
    <div key='updateTime' style={fontSize}>
      <span style={fontBold}>修改时间：</span>{detail.updateTime}
    </div>,
    <div key='createTime' style={fontSize}>
      <span style={fontBold}>创建时间：</span>{detail.createTime}
    </div>,
    <Divider dashed key='divider' />,
  ]
};

const GoodList = ({ goodList }) => [
  <h2 key='title'>商品列表</h2>,
  <Row key='list'>
    {
      goodList
        ? goodList.map(v => [
          <Col key='productBrandTitle' style={fontSize} span={12}>
            <span style={fontBold}>商品品牌：</span>{v.productBrandTitle}
          </Col>,
          <Col key='productCateTitle' style={fontSize} span={12}>
            <span style={fontBold}>商品分类：</span>{v.productCateTitle}
          </Col>,
          <Col key='productColor' style={fontSize} span={12}>
            <span style={fontBold}>商品颜色：</span>{v.productColor}
          </Col>,
          <Col key='productCount' style={fontSize} span={12}>
            <span style={fontBold}>购买数量：</span>{v.productCount}
          </Col>,
          <Col key='productPrice' style={fontSize} span={12}>
            <span style={fontBold}>商品价格：</span>{v.productPrice / 100}
          </Col>,
          <Col key='productSize' style={fontSize} span={12}>
            <span style={fontBold}>商品尺码：</span>{v.productSize}
          </Col>,
          <Col key='productTitle' style={fontSize} span={12}>
            <span style={fontBold}>商品名称：</span>{v.productTitle}
          </Col>,
          <Col span={24}>
            <Divider dashed key='divider' />
          </Col>,
        ])
        : null
    }
  </Row>,
];

