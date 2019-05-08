// import { sysLog as fieldName } from '../../../../const/fieldName'

export default [
  // { title: '请求的用户', dataIndex: fieldName.userName, width: 120 },
  // { title: '日期和时间', dataIndex: fieldName.dateTime, width: 180 },
  // { title: 'error信息', dataIndex: fieldName.errorInfo, width: 120 },
  // { title: '请求的参数', dataIndex: fieldName.params, width: 120 },
  // { title: '请求的ip地址', dataIndex: fieldName.ip, width: 200 },
  // { title: '请求的url', dataIndex: fieldName.url },
  {
    title: '操作平台', dataIndex: 'logType', width: 240, render: logType => {
      switch (logType) {
        case '1':
          return '管理后台'
        case '2':
          return '微信平台'
        default:
          return '数据错误';
      }
    }
  },
  { title: '日志时间', dataIndex: 'createTime', width: 240 },
  { title: '操作内容', dataIndex: 'logContent' },
]