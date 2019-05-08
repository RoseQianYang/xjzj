import React, { PureComponent } from 'react'
import axios from 'axios'
import { notification } from 'antd'
import { changeLoading } from '../../../redux/reducers/loading'
import { logout } from '../../../redux/reducers/login'
import { connect } from 'react-redux'
import { Redirect, withRouter } from 'react-router-dom'

axios.defaults.withCredentials = true

@withRouter
@connect(state => state.login, { changeLoading, logout })
export default class Interceptor extends PureComponent {
  interceptorRequest = () => {
    axios.interceptors.request.use(
      req => {
        req.timeout = req.timeout || 30000
        this.props.changeLoading(true)
        return req
      },
      error => {
        return Promise.reject(error)
      },
    )
  }

  interceptorResponse = () => {
    axios.interceptors.response.use(
      res => {
        this.props.changeLoading(false)
        if (res.status === 200) {
          if (res.data.code === -1) {
            if (this.props.redirect === '/login') return res
            notification['error']({ message: '请登录', description: res.data.info })
            this.props.logout()
          }
        }
        return res
      },
      error => {
        this.props.changeLoading(false)
        return Promise.reject(error)
      },
    )
  }

  render() {
    let { redirect, location } = this.props
    if (location.pathname === '/') redirect = '/login'
    this.interceptorRequest()
    this.interceptorResponse()
    return redirect ? <Redirect to={redirect} /> : null
  }
}
