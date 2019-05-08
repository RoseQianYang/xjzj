import React from 'react';
import { Row, AutoComplete, Divider, Input, Icon, Alert, Button } from 'antd';
import { Map, Marker } from 'react-bmap';
import axios from 'axios'

const { Option } = AutoComplete;
const bmapUrl = 'https://bird.ioliu.cn/v1?url=http://api.map.baidu.com/place/v2/suggestion';
const ak = 'ib5QHglR4lnOvgqoZRf9fD92ZnLWxgHO';
const region = '全国';
const output = 'json';
const city_limit = false;

export default class MapSelector extends React.PureComponent {
  constructor() {
    super();
    this.state = {
      dataSource: [],
      loading: false,
      center: '南昌',
      zoom: 12,
      selectedData: {}
    };
  }

  componentWillMount() {
    if (this.props.hasOwnProperty('address') && this.props.address) {
      const dataSource = [...this.state.dataSource]
      const addressArr = this.props.address.split(' ')
      dataSource.push({
        uid: this.props.uid,
        name: addressArr[2],
        city: addressArr[0],
        district: addressArr[1],
        location: {
          lat: this.props.latitude,
          lng: this.props.longitude
        }
      })
      this.setState({
        dataSource,
        center: { lat: this.props.latitude, lng: this.props.longitude },
        selectedData: dataSource[0],
        zoom: 16
      })
    }
  }

  search = value => { // 搜索地址
    this.setState({ loading: true })
    axios.get(bmapUrl, {
      withCredentials: false,
      params: { query: value, region, city_limit, output, ak },
    }).then(res => {
      this.setState({ loading: false })
      if (res.status === 200) {
        const dataSource = []
        res.data.result.forEach(v => {
          if (v.hasOwnProperty('location')) {
            dataSource.push(v)
          }
        })
        this.setState({ dataSource, zoom: 16, center: dataSource[0].location });
      }
    });
  }

  select = position => { // 选择地址
    const location = JSON.parse(position).location
    this.setState({
      center: location,
      selectedData: JSON.parse(position)
    })
  }

  onConfirm = () => this.props.select(this.state.selectedData)

  render() {
    const { loading, dataSource, center, zoom } = this.state;
    const { address } = this.props

    return (
      <Row>
        <Divider dashed />
        <Alert
          banner
          type="info"
          style={{ marginBottom: 10 }}
          message="输入地址后按回车即可获取地址搜索结果"
        />
        <AutoComplete
          defaultValue={address}
          defaultActiveFirstOption={false}
          optionLabelProp="children"
          autoFocus
          dataSource={
            dataSource.map((v, i) => (
              <Option key={v.uid} value={JSON.stringify(v)}>
                {`${i + 1}. ${v.city ? v.city : ''} ${v.district ? v.district : ''} ${v.name ? v.name : ''}`}
              </Option>)
            )
          }
          style={{ width: 400 }}
          onSelect={v => this.select(v)}
          placeholder='请输入地址'
        >
          <Input
            onPressEnter={e => this.search(e.target.value)}
            suffix={<Icon type={loading ? "loading" : "search"} />} />
        </AutoComplete>
        &nbsp;&nbsp;
        <Button type="primary" onClick={this.onConfirm} >确认</Button>
        <Divider dashed />
        <Map center={center} zoom={zoom} enableScrollWheelZoom style={{ height: 640 }}>
          {
            dataSource.map((v, i) => (
              <Marker
                key={v.uid}
                icon={`red${i + 1}`}
                position={{ lng: v.location.lng, lat: v.location.lat }}
                events={{ click: () => this.select(JSON.stringify(v)) }} />
            ))
          }
        </Map>
      </Row >
    );
  }
}
