import React from 'react'
import { Row } from 'antd'
import loginLogo from '../../../assets/loginLogo.png'

const style = {
  row: { width: 540, margin: '100px auto 50px', textAlign: 'center' },
  wrapper: { height: 100, lineHeight: '100px' },
  img: { height: 100, marginRight: 16 },
  // title: {
  //   fontSize: 32,
  //   color: 'rgba(0, 0, 0, 0.85)',
  //   fontFamily: '"Myriad Pro", "Helvetica Neue", Arial, Helvetica, sans-serif',
  //   fontWeight: 600,
  // },
}

export default () => (
  <Row style={style.row}>
    <div style={style.wrapper}>
      <img src={loginLogo} alt="logo" style={style.img} />
      {/* <span style={style.title}>用户登录</span> */}
    </div>
  </Row>
)