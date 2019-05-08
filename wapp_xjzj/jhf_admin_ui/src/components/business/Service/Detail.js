import React from 'react'
import { service as api } from '../../../const/api'
import axios from 'axios'

export default class Detail extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      outletDetail: {}
    }
  }

  componentDidMount() {
    axios.get(api.detail, {
      params: {
        outletId: this.props.id
      }
    }).then(res => {
      console.log('售后网店详情', res)
      this.setState({ outletDetail: res.data })
    })
  }

  render() {
    return (
      <h2>售后网点详情</h2>
    )
  }
}
