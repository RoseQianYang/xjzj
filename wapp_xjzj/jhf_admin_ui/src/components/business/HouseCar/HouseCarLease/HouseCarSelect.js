import React, { PureComponent } from 'react';
import { List, Button } from 'antd';
import { axiosPost } from '../../../../utils/request';
import { houseCar as api } from '../../../../const/api';
import { connect } from 'react-redux';
import { originOrThumb } from '../../../../utils/func'

@connect(state => state.loading, null)
export default class HouseCarSelect extends PureComponent {
  constructor() {
    super();
    this.state = {
      houseCar: [],
    };
  }

  componentDidMount() {
    axiosPost(api.all, {}, data => this.setState({ houseCar: data.data }));
  }

  render() {
    const { houseCar } = this.state;
    const { loading, selectHouseCar } = this.props;
    const { Item } = List;
    const { Meta } = Item;

    return (
      <List
        bordered
        loading={loading}
        itemLayout="vertical"
        header={<h3>房车列表</h3>}
        dataSource={houseCar}
        renderItem={item => (
          <Item extra={
            <img
              width={96}
              height={96}
              alt="logo"
              src={originOrThumb(item.cover, 'thumb')}
            />
          }>
            <Meta
              title={item.title}
              description={
                <span>
                  <span>房车名称：{item.vehicleName}</span>
                  <br />
                  <span>房车品牌：{item.brandName}</span>
                  <br />
                  <span style={{ marginTop: 8, display: 'inline-block' }}>
                    <Button
                      type='primary'
                      onClick={() => selectHouseCar({ id: item.id, name: item.vehicleName })}>
                      选择
                    </Button>
                  </span>
                </span>
              }
            />
          </Item>
        )}
      />
    );
  }
}
