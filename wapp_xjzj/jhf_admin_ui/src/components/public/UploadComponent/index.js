import React, { PureComponent } from 'react'
import { Upload, Button, Icon } from 'antd'
import { file as action } from "../../../const/api"
import PropTypes from 'prop-types'

// 将fileList中的id取出并拼接成字符串
function fileListToIdString(fileArr) {
  const fileIdArr = []
  fileArr.map(v => fileIdArr.push(v.id))
  return fileIdArr.join(',')
}

export default class UploadComponent extends PureComponent {
  constructor() {
    super()
    this.state = { fileList: [] }
  }

  // 组件初始化时从父组件获取并重新包装fileList
  componentDidMount() {
    let { fileList } = this.props
    if (fileList) {
      fileList.map((file, i) => fileList[i] = {
        id: file.id,
        uid: file.id,
        name: file.fileName,
        status: 'done',
        url: 'xxx',
      })
    } else {
      fileList = []
    }

    this.setState({ fileList })
  }

  onChange = (info) => {
    let { fileList } = info

    fileList = fileList.map(file => {
      if (file.response) {
        file.url = 'xxx'
        file.id = file.response.data.id
      }
      return file
    })

    fileList = fileList.filter(file => {
      if (file.response) return file.response.code === 0
      return true
    })

    this.setState({ fileList })
    this.props.getUploadFile(fileListToIdString(fileList))
  }

  onRemove = file => { // 删除附件
    const { fileList } = this.state
    for (let i in fileList) {
      if (fileList[i] === file.id) {
        fileList.splice(i, 1)
        this.setState({ fileList })
        break
      }
    }
  }

  onPreview = file => window.open(`${this.props.action.download}?id=${file.id}`)

  render() {
    const { fileList } = this.state

    return (
      <Upload
        action={action.upload}
        fileList={fileList}
        onChange={this.onChange}
        onRemove={this.onRemove}
        onPreview={this.onPreview}>
        <Button type="primary">
          <Icon type="upload" /> 点击上传附件
        </Button>
      </Upload>
    )
  }
}

UploadComponent.propTypes = {
  fileList: PropTypes.array, // 从父组件获取的默认文件列表
  getUploadFile: PropTypes.func, // 上传完成后的回调函数，将fileList中的id字符串返回给父组件
}