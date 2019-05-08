import React, { PureComponent } from 'react'
import { Row, Col, Card, Avatar, Divider } from 'antd'
import './detail.css'

const { Meta, Grid } = Card
const testData = [1, 2, 3, 4, 5, 6, 7, 8, 9]
const marginTop10 = { marginTop: 10 }

export default class extends PureComponent {
  render() {
    return [
      <Row id="tourismDetail" key={1}>
        <Col span={24} style={marginTop10}>
          <Meta
            title="用户名"
            avatar={
              <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
            }
            description="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa dolore, dolorem et, ex hic, id inventore iste laboriosam natus nobis odio quas quis quod quos ratione repellendus temporibus ut vero!"
          />
        </Col>
      </Row>,
      <Row key={2}>
        <Col span={22} offset={2} style={{ marginTop: 10 }}>
          {
            testData.map(v => (
              <Grid className="imgGrid" key={v}>
                <img
                  width='100%'
                  height='100%'
                  alt='图片加载失败'
                  src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png" />
              </Grid>
            ))
          }
        </Col>
      </Row>,
      <Row style={marginTop10} key={3}>
        <Col span={16} offset={2}>
          <a>
            <i className='fa fa-map-marker fa-lg' />&nbsp;&nbsp;地理位置
          </a>
        </Col>
        <Col span={3}>
          <a>
            <i className='fa fa-thumbs-o-up fa-lg' />&nbsp;&nbsp;6&nbsp;&nbsp;
          </a>
        </Col>
        <Col span={3}>
          <a>
            <i className='fa fa-commenting-o fa-lg' />&nbsp;&nbsp;2
          </a>
        </Col>
      </Row>,
      <Row key={4}>
        <Col span={22} offset={2} style={marginTop10}>
          <Card title="用户评论" bodyStyle={{ paddingLeft: 10, paddingRight: 10 }}>
            <Meta
              title="用户名"
              avatar={
                <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
              }
              description="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa dolore, dolorem et, ex hic, id inventore iste laboriosam natus nobis odio quas quis quod quos ratione repellendus temporibus ut vero!"
            />
            <Divider />
            <Meta
              title="用户名"
              avatar={
                <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
              }
              description="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa dolore, dolorem et, ex hic, id inventore iste laboriosam natus nobis odio quas quis quod quos ratione repellendus temporibus ut vero!"
            />
          </Card>
        </Col>
      </Row>,
    ]
  }
}
