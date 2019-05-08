import React from 'react';
import { Row, List, Button, Popconfirm, InputNumber } from 'antd';
import { originOrThumb } from "../../../../../utils/func";

const { Item } = List;
const { Meta } = Item;

export default class ActionGoodCard extends React.PureComponent {
  constructor() {
    super()
    this.state = {
      isEditing: false,
      eventPrice: undefined
    }
  }
  render() {
    const { item, onEdit, onRemove } = this.props
    const { isEditing, eventPrice } = this.state

    return (
      <Item extra={<img width={128} height={128} alt="logo" src={originOrThumb(item.cover, 'thumb')} />}>
        <Meta
          title={item.title}
          description={
            <Row>
              <Row>活动价格：￥{item.eventPrice / 100}</Row>
              <Row>商品原价：￥{item.productPrice / 100}</Row>
              <br />
              {
                isEditing
                  ? (
                    <span>
                      <InputNumber
                        min={0}
                        max={item.productPrice / 100}
                        defaultValue={item.eventPrice / 100}
                        style={{ width: 180 }}
                        onChange={value => this.setState({ eventPrice: value * 100 })}
                        placeholder='请输入活动价格' />
                      &nbsp;&nbsp;
                      <Button
                        type='primary'
                        disabled={!eventPrice || eventPrice > item.productPrice || eventPrice < 0}
                        onClick={() => {
                          onEdit({ id: item.id, eventPrice });
                          this.setState({ isEditing: false });
                        }}>
                        确认
                      </Button>
                      &nbsp;&nbsp;
                      <Button onClick={() => this.setState({ isEditing: false })}>取消</Button>
                    </span>
                  )
                  : (
                    <Row>
                      <Button
                        type='primary'
                        onClick={() => this.setState({ isEditing: true })}>
                        修改活动价格
                      </Button>
                      &nbsp;&nbsp;
                    <Popconfirm
                        title="确认从活动中移除该商品？"
                        okText='确认'
                        cancelText='取消'
                        onConfirm={() => onRemove(item)}
                      >
                        <Button>取消活动商品</Button>
                      </Popconfirm>
                    </Row>
                  )
              }
            </Row>
          }
        />
      </Item>
    );
  }
}
