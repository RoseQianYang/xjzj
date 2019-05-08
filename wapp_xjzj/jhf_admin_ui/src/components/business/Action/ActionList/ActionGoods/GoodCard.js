import React from 'react';
import { Row, List, Button, InputNumber } from 'antd';
import { originOrThumb } from "../../../../../utils/func";

const { Item } = List;
const { Meta } = Item;

export default class GoodCard extends React.PureComponent {
  constructor() {
    super();
    this.state = {
      isSetPrice: false,
      eventPrice: undefined,
    };
  }

  render() {
    const { item, onSelect } = this.props;
    const { eventPrice, isSetPrice } = this.state;

    return (
      <Item extra={<img width={128} height={128} alt="logo" src={originOrThumb(item.cover, 'thumb')} />}>
        <Meta
          title={item.title}
          description={
            <Row>
              <Row>原价：￥{item.productPrice / 100}</Row>
              <br />
              <Row>
                {
                  isSetPrice
                    ? (
                      <span>
                        <InputNumber
                          min={0}
                          max={item.productPrice / 100}
                          defaultValue={item.productPrice / 100}
                          style={{ width: 180 }}
                          onChange={value => this.setState({ eventPrice: value * 100 })}
                          placeholder='请输入活动价格' />
                        &nbsp;&nbsp;
                        <Button
                          type='primary'
                          disabled={!eventPrice || eventPrice > item.productPrice || eventPrice < 0}
                          onClick={() => {
                            onSelect({ ...item, eventPrice });
                            this.setState({ isSetPrice: false });
                          }}>
                          确认
                        </Button>
                        &nbsp;&nbsp;
                        <Button onClick={() => this.setState({ isSetPrice: false })}>取消</Button>
                      </span>
                    )
                    : (
                      <Button
                        disabled={item.isActionGood}
                        type='primary'
                        onClick={() => this.setState({ isSetPrice: true })}>
                        添加至活动
                      </Button>
                    )
                }
              </Row>
            </Row>
          }
        />
      </Item>
    );
  }
}
