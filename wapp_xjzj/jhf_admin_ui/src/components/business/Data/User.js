import React from 'react';
import { Row, Select, Divider, Button, Spin } from "antd";
import { Chart, Tooltip, Axis, Bar } from 'viser-react';
import { width180 } from '../../../const/style'
import { axiosPost } from "../../../utils/request";
import { connect } from "react-redux";
import { yearData, monthData, years } from "./utils";

const { Option } = Select
const yearApi = '/jhf/api/adm/stats/getUserStatsByYear.do' // year
const monthApi = '/jhf/api/adm/stats/getUserStatsByMonth.do' // month

@connect(state => state.loading, null)
class User extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      scale: [{ dataKey: '注册人数', tickInterval: 5 }],
      sourceData: [],
      position: `month*${'注册人数'}`,
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      dataType: 'year', // 'month'
    }
    this.onSelect = this.onSelect.bind(this)
    this.onBarClick = this.onBarClick.bind(this)
    this.returnYearData = this.returnYearData.bind(this)
  }

  componentDidMount() {
    axiosPost(yearApi, { year: this.state.year.toString() }, res => {
      this.setState({
        sourceData: yearData(res.data),
      })
    })
  }

  onSelect(value) {
    this.setState({
      year: value,
      dataType: 'year',
      position: `month*${'注册人数'}`,
    })
    axiosPost(yearApi, { year: value.toString() }, res => {
      this.setState({
        sourceData: yearData(res.data)
      })
    })
  }

  onBarClick(e) {
    const { year, dataType } = this.state
    if (dataType === 'year') {
      const { month } = e.data._origin
      let m = month.substring(-1, 1)
      if (m.length === 1) {
        m = '0' + m
      }
      axiosPost(monthApi, { month: m }, res => {
        this.setState({
          dataType: 'month',
          month,
          sourceData: monthData(year, month, res.data),
          position: `day*${'注册人数'}`,
        })
      })
    }
  }

  returnYearData() {
    axiosPost(yearApi, { year: this.state.year.toString() }, res => {
      this.setState({
        dataType: 'year',
        sourceData: yearData(res.data),
        position: `month*${'注册人数'}`,
      })
    })
  }

  render() {
    const { year, month, scale, sourceData, position, dataType } = this.state
    const { loading } = this.props

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
            : null
        }
        <Divider dashed />
        <h2>{year}年{dataType === 'month' ? month : null} 用户注册数据统计</h2>
        <Row gutter={16} style={{ textAlign: 'center', paddingTop: loading ? 30 : 0 }}>
          {
            loading
              ? <Spin />
              : sourceData.length === 0
                ? <span>暂无数据</span>
                : <Chart forceFit height={480} data={sourceData} scale={scale} >
                  <Tooltip />
                  <Axis />
                  <Bar position={position} onClick={this.onBarClick} />
                </Chart>

          }
        </Row>
      </Row>
    )
  }
}

export default User