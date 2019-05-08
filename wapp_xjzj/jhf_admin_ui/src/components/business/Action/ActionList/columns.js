import { actionList as fieldName } from '../../../../const/fieldName';

export default [
  { title: '活动名称', dataIndex: fieldName.title },
  { title: '活动分类', dataIndex: fieldName.eventCateName, width: 180 },
  { title: '开始时间', dataIndex: fieldName.startTime, width: 180 },
  { title: '结束时间', dataIndex: fieldName.endTime, width: 180 },
  { title: '修改时间', dataIndex: fieldName.updateTime, width: 180 },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 180 },
];
