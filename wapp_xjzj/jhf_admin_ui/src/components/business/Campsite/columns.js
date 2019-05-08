import { campsite as fieldName } from '../../../const/fieldName'

export default [
  { title: '营地名称', dataIndex: fieldName.campsiteName, width: 180 },
  { title: '营地地址', dataIndex: fieldName.address },
  { title: '营地分类', dataIndex: fieldName.categoryId, width: 180, render: categoryId => categoryId === 1 ? '自由营地' : '合作营地' },
  { title: '联系电话', dataIndex: fieldName.telephone, width: 180 },
  { title: '修改时间', dataIndex: fieldName.updateTime, width: 180 },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 180 },
]
