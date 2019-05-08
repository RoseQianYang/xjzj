import React from 'react';
import { Row, Select, Divider, Button, Spin } from "antd";
import { Chart, Tooltip, Axis, Legend, Line, Point } from 'viser-react';
import { width180 } from '../../../const/style'
import { axiosPost } from "../../../utils/request";
import { connect } from "react-redux";
import { saleYearData, saleMonthData, years } from "./utils";

const DataSet = require('@antv/data-set');
const { Option } = Select
const api = {
  month: '/jhf/api/adm/stats/getSalesVolumeStatsByMonth.do',
  year: '/jhf/api/adm/stats/getSalesVolumeStatsByYear.do',
}

@connect(state => state.loading, null)
class SaleChart extends React.PureComponent {
  constructor(props) {
    super(props)
    this.state = {
      scale: [{ dataKey: 'month', min: 0, max: 1 }],
      sourceData: [],
      position: "month*sale",
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      dataType: 'year', // 'month'
      productId: props.id
    }
    this.onSelect = this.onSelect.bind(this)
    this.onBarClick = this.onBarClick.bind(this)
    this.returnYearData = this.returnYearData.bind(this)
  }

  componentDidMount() {
    const { productId } = this.state
    axiosPost(api.year, { year: this.state.year.toString(), productId }, res => {
      this.setState({
        sourceData: saleYearData(res.data)
      })
    })
  }

  onSelect(value) { // 选择年份
    this.setState({
      year: value,
      dataType: 'year',
      position: "month*sale",
    })
    const { productId } = this.state
    axiosPost(api.year, { year: value.toString(), productId }, res => {
      this.setState({
        sourceData: saleYearData(res.data)
      })
    })
  }

  onBarClick(e) { // 选中节点
    const { year, dataType, productId } = this.state
    if (dataType === 'year') {
      const { month } = e.data._origin
      let m = month.substring(-1, 1)
      if (m.length === 1) {
        m = '0' + m
      }
      axiosPost(api.month, { month: m, productId }, res => {
        this.setState({
          dataType: 'month',
          month,
          sourceData: saleMonthData(year, month, res.data),
          position: "day*sale",
        })
      })
    }
  }

  returnYearData() {
    const { productId } = this.state
    axiosPost(api.year, { year: this.state.year.toString(), productId }, res => {
      this.setState({
        dataType: 'year',
        sourceData: saleYearData(res.data),
        position: "month*sale",
      })
    })
  }

  render() {
    const { year, month, scale, sourceData, position, dataType } = this.state
    const { loading } = this.props

    const dv = new DataSet.View().source(sourceData);

    dv.transform({
      type: 'fold',
      fields: ['销量', '销售额'],
      key: 'type',
      value: 'sale',
    });

    const data = dv.rows;

    return (
      <Row>
        <Select placeholder='选择年份' style={width180} onSelect={this.onSelect}>
          {
            years(10).map(v => <Option key={v} value={v}>{v}年</Option>)
          }
        </Select>
        {
          dataType === 'month'
            ? <span>
              &nbsp;&nbsp;
              <Button type="primary" onClick={this.returnYearData}>返回年数据统计图</Button>
            </span>
            : <span>
              &nbsp;&nbsp;
            <Button type="primary" onClick={this.props.return}>返回商品数据列表</Button>
            </span>
        }
        <Divider dashed />
        <h2>{year}年{dataType === 'month' ? month : null} 商品销售数据统计</h2>
        <Row gutter={16} style={{ textAlign: 'center', paddingTop: loading ? 30 : 0 }}>
          {
            loading
              ? <Spin />
              : sourceData.length === 0
                ? <span>暂无数据</span>
                : <Chart forceFit height={400} data={data} scale={scale}>
                  <Tooltip />
                  <Axis />
                  <Legend />
                  <Line position={position} color="type" />
                  <Point
                    position={position}
                    color="type"
                    size={4}
                    style={{ stroke: '#fff', lineWidth: 1 }}
                    shape="circle"
                    onClick={this.onBarClick}
                  />
                </Chart>
          }
        </Row>
      </Row>
    )
  }
}

export default SaleChart