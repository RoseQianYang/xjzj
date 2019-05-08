import React from 'react'
import { shop as api } from '../../../const/api'
import axios from 'axios'

export default class Detail extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      shopDetail: {}
    }
  }

  componentDidMount() {
    axios.get(api.detail, {
      params: {
        storeId: this.props.id
      }
    }).then(res => {
      console.log('门店详情', res)
      this.setState({ shopDetail: res.data })
    })
  }

  render() {
    return (
      <h2>门店详情</h2>
    )
  }
}
