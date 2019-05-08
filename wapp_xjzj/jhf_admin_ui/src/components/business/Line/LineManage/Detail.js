import React from 'react'
import { lineManage as api } from '../../../../const/api'
import axios from 'axios'

export default class Detail extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      lineDetail: {}
    }
  }

  componentDidMount() {
    axios.get(api.detail, {
      params: {
        mobileRouteId: this.props.id
      }
    }).then(res => {
      console.log('露营线路详情', res)
      this.setState({ lineDetail: res.data })
    })
  }

  render() {
    return (
      <h2>露营线路详情</h2>
    )
  }
}
