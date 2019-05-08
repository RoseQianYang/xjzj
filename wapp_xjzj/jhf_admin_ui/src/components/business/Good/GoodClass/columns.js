import React from "react";
import { originOrThumb } from "../../../../utils/func";
import { goodClass as fieldName } from '../../../../const/fieldName'

export default [
  {
    title: '分类图片', dataIndex: fieldName.cover, render: cover => (
      <img src={originOrThumb(cover, 'thumb')} alt="图片加载失败" height="64px" />
    )
  },
  { title: '分类名称', dataIndex: fieldName.title, width: 200 },
  { title: '修改时间', dataIndex: fieldName.updateTime, width: 200 },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 200 },
]