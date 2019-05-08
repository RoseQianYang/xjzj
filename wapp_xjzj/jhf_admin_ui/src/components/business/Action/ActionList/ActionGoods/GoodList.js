import React from 'react';
import { List } from 'antd';
import GoodCard from './GoodCard';
import PropTypes from 'prop-types';

function GoodList({ pagination, dataSource, listTitle, loading, onSelect }) {
  return (
    <List
      bordered
      loading={loading}
      itemLayout="vertical"
      pagination={pagination}
      header={<h3>{listTitle}</h3>}
      dataSource={dataSource}
      renderItem={item => <GoodCard item={item} onSelect={onSelect} />}
    />
  );
}

GoodList.propTypes = {
  pagination: PropTypes.object.isRequired,
  dataSource: PropTypes.array.isRequired,
  listTitle: PropTypes.string.isRequired,
  loading: PropTypes.bool.isRequired,
  onSelect: PropTypes.func.isRequired,
};

export default GoodList;
