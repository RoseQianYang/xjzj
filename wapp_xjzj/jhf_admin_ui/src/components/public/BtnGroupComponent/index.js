import React from 'react'
import { Row, Popconfirm, Button } from 'antd'

const marginRight10 = { marginRight: 10 }

export default ({ btnGroup, record, showModal, deleteRow }) => (
  <Row>
    {btnGroup.map(v => {
      const disabled = v.disabled ? v.disabled(record) : false
      return v.title === '删除' ?
        <Popconfirm
          key={v.title}
          title="确认删除？"
          okText='确认'
          cancelText='取消'
          onConfirm={() => deleteRow(record)}>
          <Button type='primary' disabled={disabled} style={marginRight10}>{v.title}</Button>
        </Popconfirm> :
        <Button
          type='primary'
          key={v.title}
          disabled={disabled}
          style={marginRight10}
          onClick={() => showModal(v.title)}>{v.title}</Button>
    })}
  </Row>
)
