import React from 'react';
import { goodManage as api } from '../../../../const/api';
import { Spin, Row, Col } from 'antd';
import axios from 'axios';
import { connect } from 'react-redux';

@connect(state => state.loading, null)
export default class Detail extends React.PureComponent {
  constructor() {
    super();
    this.state = { detail: {} };
  }

  componentWillMount() {
    const { id } = this.props;
    axios.get(api.detail, { params: { productId: id } }).then(data => {
      console.log('商品详情：', data);
      this.setState({ detail: data.data.data });
    });
  }

  render() {
    const { loading } = this.props;
    const { detail } = this.state;
    const fontBold = { fontWeight: 'bolder' };
    const fontSize = { fontSize: 15, margin: '4px 0' };

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
            :
            <Row>
              <Col span={24}>
                <img src={detail.cover} alt="封面图片" />
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品名称：</span>{detail.title}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品品牌：</span>{detail.title}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品分类：</span>{detail.cateName}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品颜色：</span>{detail.productColor}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品尺码：</span>{detail.productSize}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品库存：</span>{detail.stock}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品价格：</span>{detail.price}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>商品描述：</span>{detail.description}
              </Col>
              <Col span={24} style={fontSize}>
                <span style={fontBold}>图文详情：</span>
                <span dangerouslySetInnerHTML={{ __html: detail.content }} />
              </Col>
            </Row>
        }
      </Row>
    );
  }
}
