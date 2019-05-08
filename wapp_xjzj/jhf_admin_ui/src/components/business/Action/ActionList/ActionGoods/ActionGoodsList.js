import React from 'react';
import { List } from 'antd';
import PropTypes from 'prop-types';
import ActionGoodCard from './ActionGoodCard'

function ActionGoodsList({ pagination, dataSource, listTitle, loading, onRemove, onEdit }) {
  return (
    <List
      bordered
      loading={loading}
      itemLayout="vertical"
      pagination={pagination}
      header={<h3>{listTitle}</h3>}
      dataSource={dataSource}
      renderItem={item => <ActionGoodCard item={item} onRemove={onRemove} onEdit={onEdit} />}
    />
  );
}

ActionGoodsList.propTypes = {
  pagination: PropTypes.object.isRequired,
  dataSource: PropTypes.array.isRequired,
  listTitle: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  onRemove: PropTypes.func.isRequired,
  onEdit: PropTypes.func.isRequired,
};

export default ActionGoodsList;
