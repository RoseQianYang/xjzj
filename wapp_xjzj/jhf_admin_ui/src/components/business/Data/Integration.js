import React from 'react';
import { List, Row, DatePicker, Divider, Button } from "antd";
import { axiosPost } from "../../../utils/request";
import moment from "moment";
import { connect } from "react-redux";

const { RangePicker } = DatePicker
const { Item } = List
const { Meta } = Item
const api = {
  get: '/jhf/api/adm/stats/getIntegralStats.do'
}

@connect(state => state.loading, null)
class Integration extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      showLoadingMore: true,
      page: 1,
      dataSource: [],
      range: [],
      total: 0
    }
    this.onTimePick = this.onTimePick.bind(this)
    this.onLoadMore = this.onLoadMore.bind(this)
  }

  componentDidMount() {
    axiosPost(api.get, { page: this.state.page }, res => {
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > this.state.page ? true : false
      this.setState({
        dataSource: res.data.list,
        total: res.data.totalSize,
        showLoadingMore,
      })
    })
  }

  onTimePick(time) {
    const range = {}
    if (time[0] && time[1]) {
      range.startTime = moment(time[0]).format("YYYY-MM-DD")
      range.endTime = moment(time[1]).format("YYYY-MM-DD")
    }
    axiosPost(api.get, {
      page: 1,
      ...range
    }, res => {
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > 1 ? true : false
      this.setState({
        dataSource: res.data.list,
        page: 1,
        range,
        showLoadingMore
      })
    })
  }

  onLoadMore() {
    let { page, range, dataSource } = this.state
    page++
    axiosPost(api.get, { page, ...range }, res => {
      dataSource = dataSource.concat(res.data.list)
      const showLoadingMore = Math.ceil(res.data.totalSize / 20) > page ? true : false
      this.setState({
        dataSource,
        page,
        loadingMore: false,
        showLoadingMore
      })
    })
  }

  render() {
    const { dataSource, showLoadingMore } = this.state
    const { loading } = this.props
    const loadMore = showLoadingMore && !loading ? (
      <div style={{ textAlign: 'center', marginTop: 12, height: 32, lineHeight: '32px' }}>
        <Button onClick={this.onLoadMore}>加载更多</Button>
      </div>
    ) : null;

    return (
      <Row>
        <RangePicker
          style={{ width: 320 }}
          format="YYYY-MM-DD"
          onChange={this.onTimePick}
          placeholder={['开始时间', '结束时间']}
          allowClear
        />
        <Divider dashed />
        <List
          loading={loading}
          loadMore={loadMore}
          itemLayout="horizontal"
          dataSource={dataSource}
          renderItem={item => (
            <Item>
              <Meta
                title={<a>{item.userName}</a>}
                description={item.introduction}
              />
            </Item>
          )}
        />
      </Row>
    )
  }
}

export default Integration