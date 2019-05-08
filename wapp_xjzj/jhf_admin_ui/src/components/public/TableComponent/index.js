import React from 'react'
import { Row, Table } from 'antd'

export default ({ table, record, rowSelect, onChange }) => (
  <Row>
    <Table
      size='middle'
      rowKey='id'
      scroll={{ y: 440 }}
      rowSelection={{
        type: 'radio',
        fixed: true,
        selectedRowKeys: [record.id],
        onSelect: record => rowSelect(record),
      }}
      onChange={(pagination, filters, sorter) => onChange(pagination, filters, sorter)}
      onRow={(record) => {
        return {
          onClick: () => rowSelect(record)
        }
      }}
      {...table}
    />
  </Row>
)