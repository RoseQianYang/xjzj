import React from 'react'
import { campsite as api } from '../../../const/api'
import axios from 'axios'

export default class Detail extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      campsiteDetail: {}
    }
  }

  componentDidMount() {
    axios.get(api.detail, {
      params: {
        campsiteId: this.props.id
      }
    }).then(res => {
      console.log('营地详情', res)
      this.setState({ campsiteDetail: res.data })
    })
  }

  render() {
    return (
      <h2>营地详情</h2>
    )
  }
}
