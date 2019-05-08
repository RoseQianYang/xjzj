import React from 'react'
import { Row, Col, Icon } from 'antd'
import { withRouter } from 'react-router-dom'
import { originOrThumb } from "../../../utils/func";
import PropTypes from 'prop-types'
import "./index.css";

const iconStyle = { lineHeight: '40px', fontSize: '24px', color: 'aliceblue' }
const iconContainerStyle = {
  position: 'absolute',
  width: 40,
  height: 40,
  borderRadius: 20,
  background: 'rgba(0,0,0,.3)',
  textAlign: 'center',
  marginTop: -20,
  top: '50%',
}

function ImgDisplay({ imgList, remove, location }) {
  const { pathname } = location
  const largeArr = [
    '/app/good/goodManage',
    '/app/product/actionList',
    '/app/housecar/houseCar',
    '/app/shop/shop',
    '/app/service/service',
    '/app/line/lineManage',
    '/app/campsite/campsite',
  ]
  const span = largeArr.indexOf(pathname) !== -1 ? 3 : 8

  return imgList.length > 0 ? (
    <Row id="imgDisplay" gutter={16} >
      {
        imgList.map((v, i) => {
          return <Col span={span} key={i}>
            <span className="imgRemove">
              <span style={{ ...iconContainerStyle, left: '25%', marginLeft: -10 }}>
                <Icon
                  onClick={() => window.open(originOrThumb(v, 'orgin'))}
                  type="eye-o"
                  style={iconStyle} />
              </span>
              <span style={{ ...iconContainerStyle, right: '25%', marginRight: -10 }}>
                <Icon
                  onClick={() => remove(i)}
                  type="delete"
                  style={iconStyle} />
              </span>
              <img src={originOrThumb(v, 'thumb')} alt={v} width='100%' height="100px" />
            </span>
          </Col>
        })
      }
    </Row>
  ) : null
}

ImgDisplay.propTypes = {
  imgList: PropTypes.array.isRequired,
  remove: PropTypes.func.isRequired
}

export default withRouter(ImgDisplay)