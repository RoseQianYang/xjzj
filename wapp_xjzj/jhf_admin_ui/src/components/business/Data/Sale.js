import React from 'react';
import { List, Row, Col, DatePicker, Divider, Select, Button } from "antd";
import moment from 'moment'
import { connect } from "react-redux";
import { axiosPost } from "../../../utils/request";
import axios from "axios";
import SaleChart from "./SaleChart";
import {
  brandManage as brandApi,
  goodClass as cateApi
} from "../../../const/api";

const { RangePicker } = DatePicker
const { Option } = Select
const { Item } = List
const { Meta } = Item

const api = {
  get: '/jhf/api/adm/stats/getSalesVolumeStatsList.do',
}

@connect(state => state.loading, null)
class Sale extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      showLoadingMore: true,
      page: 1,
      dataSource: [],
      range: {},
      total: 0,
      brandList: [],
      productBrandId: undefined,
      cateList: [],
      productCateId: undefined,
      price: false,
      showChart: false,
      chartId: ''
    }
    this.onTimePick = this.onTimePick.bind(this)
    this.onCateSelect = this.onCateSelect.bind(this)
    this.onLoadMore = this.onLoadMore.bind(this)
    this.sortByPrice = this.sortByPrice.bind(this)
    this.onCateSelect = this.onCateSelect.bind(this)
    this.onBrandSelect = this.onBrandSelect.bind(this)
  }

  componentWillMount() {
    axiosPost(api.get, { page: 1 }, res => {
      axios.get(cateApi.all).then(cateRes => {
        axios.get(brandApi.all).then(brandRes => {
          const showLoadingMore = Math.ceil(res.data.totalSize / 20) > 1 ? true : false
          this.setState({
            page: 1,
            dataSource: res.data.list,
            brandList: brandRes.data.data ? brandRes.data.data : [],
            cateList: cateRes.data.data ? cateRes.data.data : [],
            total: res.data.totalSize,
            showLoadingMore,
          })
        })
      })
    })
  }

  onTimePick(time) { // 选择时间
    let { productBrandId, productCateId, price } = this.state
    const range = {}
    let values = { page: 1, productBrandId, productCateId, }

    if (price) values.price = true

    if (time[0] && time[1]) {
      range.startTime = moment(time[0]).format("YYYY-MM-DD")
      range.endTime = moment(time[1]).format("YYYY-MM-DD")
    }

    values = { ...values, ...range }

    axiosPost(api.get, values, res => {
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > 1 ? true : false
      this.setState({
        dataSource: res.data.list,
        page: 1,
        range: { ...range },
        showLoadingMore,
      })
    })
  }

  onCateSelect(cateId) { // 选择分类
    let { range, productBrandId, price } = this.state
    const values = {
      page: 1,
      productBrandId,
      productCateId: cateId,
      ...range
    }

    if (price) values.price = true

    axiosPost(api.get, values, res => {
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > 1 ? true : false
      this.setState({
        dataSource: res.data.list,
        page: 1,
        loadingMore: false,
        showLoadingMore,
        productCateId: cateId,
      })
    })
  }

  onBrandSelect(brandId) { // 选择品牌
    let { range, productCateId, price } = this.state
    const values = {
      page: 1,
      productBrandId: brandId,
      productCateId,
      ...range
    }

    if (price) values.price = true

    axiosPost(api.get, values, res => {
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > 1 ? true : false
      this.setState({
        dataSource: res.data.list,
        page: 1,
        loadingMore: false,
        showLoadingMore,
        productBrandId: brandId,
      })
    })
  }

  showCharts(id) { // 显示图表
    this.setState({ chartId: id, showChart: true })
  }

  onLoadMore() { // 加载更多
    let { page, range, dataSource, productBrandId, productCateId, price } = this.state
    page++

    const values = { page, productBrandId, productCateId, ...range }

    if (price) values.price = true

    axiosPost(api.get, values, res => {
      dataSource = dataSource.concat(res.data.list)
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > page ? true : false
      this.setState({
        dataSource,
        page,
        loadingMore: false,
        showLoadingMore,
      })
    })
  }

  sortByPrice(bool) { // 是否按销售额排序
    const { productBrandId, productCateId, range } = this.state
    const values = {
      page: 1,
      productBrandId,
      productCateId,
      ...range
    }

    if (bool) values.price = true

    axiosPost(api.get, values, res => {
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > 1 ? true : false
      this.setState({
        dataSource: res.data.list,
        total: res.data.totalSize,
        showLoadingMore,
        page: 1,
        price: bool
      })
    })
  }

  render() {
    const {
      dataSource, showLoadingMore,
      brandList, cateList,
      productBrandId, productCateId,
      price, showChart, chartId
    } = this.state
    const { loading } = this.props
    const loadMore = showLoadingMore && !loading ? (
      <div style={{ textAlign: 'center', marginTop: 12, height: 32, lineHeight: '32px' }}>
        <Button onClick={this.onLoadMore}>加载更多</Button>
      </div>
    ) : null;

    return showChart
      ? <SaleChart
        id={chartId}
        return={() => this.setState({ chartId: '', showChart: false })} />
      : (
        <Row>
          <RangePicker
            style={{ width: 320 }}
            format="YYYY-MM-DD"
            onChange={this.onTimePick}
            placeholder={['开始时间', '结束时间']}
            allowClear
          />
          &nbsp;&nbsp;&nbsp;
        <Select
            allowClear
            defaultValue={productCateId}
            placeholder='商品分类'
            style={{ width: 180 }}
            onChange={this.onCateSelect} >
            {
              cateList.map(v => <Option value={v.id} key={v.id}>{v.title}</Option>)
            }
          </Select>
          &nbsp;&nbsp;&nbsp;
        <Select
            allowClear
            defaultValue={productBrandId}
            placeholder='商品品牌'
            style={{ width: 180 }}
            onChange={this.onBrandSelect} >
            {
              brandList.map(v => <Option value={v.id} key={v.id}>{v.title}</Option>)
            }
          </Select>
          <Divider dashed />
          <List
            header={
              <div style={{ textAlign: 'right' }}>
                <a
                  style={{ fontWeight: price ? 'bold' : 'normal' }}
                  onClick={() => this.sortByPrice(true)}>按销售额排序</a>
                &nbsp;&nbsp;&nbsp;
              <a
                  style={{ fontWeight: !price ? 'bold' : 'normal' }}
                  onClick={() => this.sortByPrice(false)}>按销量排序</a>
              </div>
            }
            loading={loading}
            loadMore={loadMore}
            itemLayout="horizontal"
            dataSource={dataSource}
            renderItem={item => (
              <Item>
                <Meta
                  title={<a onClick={() => this.showCharts(item.productId)} >{item.productName}</a>}
                  description={
                    <Row gutter={16}>
                      <Col span={3}>分类：{item.productCateName}</Col>
                      <Col span={3}>品牌：{item.productBrandName}</Col>
                      <Col span={3}>颜色：{item.productColor || '无'}</Col>
                      <Col span={3}>尺码：{item.productSize || '无'}</Col>
                      <Col span={3}>销量：{item.productCount}</Col>
                      <Col span={3}>销售额：{item.productPrice / 200}</Col>
                      {/* <Col span={3}>订单时间：{item.createTime}</Col> */}
                    </Row>
                  }
                />
              </Item>
            )}
          />
        </Row>
      )
  }
}

export default Sale