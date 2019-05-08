import axios from 'axios'
import { notification } from 'antd'

export function axiosPost(url, data, callback, hasNotification = false) {
  return axios.post(url, data).then(res => {
    if (res.status === 200) {
      if (res.data.code === 0) {
        if (hasNotification) {
          notification['success']({ message: res.data.info })
        }
        callback && callback(res.data)
        return res.data
      } else if (res.data.code === -1) {
        return null
      } else {
        notification['error']({ message: res.data.info })
      }
    }
  })
}