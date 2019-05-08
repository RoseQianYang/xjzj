import React from 'react'
import { goodManage as fieldName } from '../../../../const/fieldName'
import { Tag } from "antd";

export default [
  { title: '商品名称', dataIndex: fieldName.title },
  { title: '商品分类', dataIndex: fieldName.cateName, width: 120 },
  { title: '商品品牌', dataIndex: fieldName.brandName, width: 120 },
  { title: '商品价格', dataIndex: fieldName.price, width: 120, render: price => price / 100 },
  {
    title: '状态', dataIndex: 'putaway', width: 150, render: putaway => {
      switch (putaway) {
        case 'Y':
          return <Tag color="green">上架中</Tag>
        case 'N':
          return <Tag color="red">下架中</Tag>
        default:
          return <Tag color="gray">数据错误</Tag>
      }
    }
  },
  { title: '创建时间', dataIndex: fieldName.createTime, width: 150 },
]
