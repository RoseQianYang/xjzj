import React, { PureComponent } from 'react';
import GoodList from './GoodList';
import ActionGoodsList from './ActionGoodsList';
import { Row, Col, Alert } from 'antd';
import { actionGood as api } from '../../../../../const/api';
import { axiosPost } from '../../../../../utils/request';
import { connect } from 'react-redux';
import axios from 'axios'

const pagination = {
  pageSize: 20,
  current: 1,
  total: 0,
  showTotal: (total, range) => `第 ${range[0]} - ${range[1]} 条，共 ${total} 条`,
};

@connect(state => state.loading, null)
export default class ActionGoods extends PureComponent {
  constructor() {
    super();
    this.state = {
      goodsList: { // 商品列表
        dataSource: [],
        pagination: {
          ...pagination,
          onChange: page => this.getDataList(this.state.actionGoodsList.pagination.current, page),
        },
      },
      actionGoodsList: { // 活动商品
        dataSource: [],
        pagination: {
          ...pagination,
          onChange: page => this.getDataList(page, this.state.goodsList.pagination.current),
        },
      },
    };
  }

  componentDidMount() {
    this.getDataList()
  }

  getDataList = (
    page = this.state.actionGoodsList.pagination.current,
    prdPage = this.state.goodsList.pagination.current
  ) => {
    axiosPost(api.get, {
      page,
      prdPage,
      eventId: this.props.record.id,
    }, res => {
      const { list, not } = res.data
      this.setState({
        goodsList: {
          dataSource: not.list,
          pagination: {
            ...pagination,
            total: not.totalSize
          }
        },
        actionGoodsList: {
          dataSource: list.list,
          pagination: {
            ...pagination,
            total: list.totalSize
          }
        }
      })
    })
  }

  onGoodListSelect = item => {
    axiosPost(api.add, {
      productId: item.id,
      eventPrice: item.eventPrice,
      eventId: this.props.record.id,
    }, res => {
      this.getDataList()
    });
  };

  onRemove = item => {
    axios.get(api.remove, {
      params: { eventProductId: item.id },
    }).then(res => {
      this.getDataList()
    });
  };

  onEdit = values => {
    console.log(values)
    axiosPost(api.update, { ...values, eventId: this.props.record.id }, res => {
      this.getDataList()
    }, true)
  }

  render() {
    const { goodsList, actionGoodsList } = this.state;
    const { record, loading } = this.props;

    return (
      <div>
        <h2>{record.title}</h2>
        <Alert banner message="注意：商品的活动价格必须低于商品的原价" style={{ marginBottom: 10 }} />
        <Row gutter={16}>
          <Col span={12}>
            <ActionGoodsList
              loading={loading}
              dataSource={actionGoodsList.dataSource}
              pagination={actionGoodsList.pagination}
              listTitle='活动商品'
              onRemove={this.onRemove}
              onEdit={this.onEdit}
            />
          </Col>
          <Col span={12} style={{ borderLeft: '1px dashed #e8e8e8' }}>
            <GoodList
              loading={loading}
              dataSource={goodsList.dataSource}
              pagination={goodsList.pagination}
              listTitle='商品列表'
              onSelect={this.onGoodListSelect}
            />
          </Col>
        </Row>
      </div>
    );
  }
}
