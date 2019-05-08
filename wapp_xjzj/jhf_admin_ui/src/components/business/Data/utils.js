export function yearData(data) {
  const dataSource = []

  for (let i = 1; i < 13; i++) {
    const target = data.filter(v => parseInt(v.grpName, 10) === i)[0]
    dataSource.push({
      month: `${i}月`,
      '注册人数': target ? target.sumun : 0,
    })
  }

  return dataSource
}

export function saleYearData(data) {
  const dataSource = []

  for (let i = 1; i < 13; i++) {
    const target = data.filter(v => parseInt(v.grpName, 10) === i)[0]
    dataSource.push({
      month: `${i}月`,
      '销量': target ? target.productCount : 0,
      '销售额': target ? target.productPrice : 0,
    })
  }

  return dataSource
}

export function monthData(year, month, data) {
  const dataSource = []
  let dayNum

  switch (month) {
    case '1月':
    case '3月':
    case '5月':
    case '7月':
    case '8月':
    case '10月':
    case '12月':
      dayNum = 31
      break;
    case '2月':
      year % 4 === 0 ? dayNum = 29 : dayNum = 28
      break;
    default:
      dayNum = 30
      break;
  }

  for (let i = 1; i < dayNum + 1; i++) {
    const target = data.filter(v => parseInt(v.grpName, 10) === i)[0]
    dataSource.push({
      day: `${i}日`,
      '注册人数': target ? target.sumun : 0,
    })
  }

  return dataSource
}

export function saleMonthData(year, month, data) {
  const dataSource = []
  let dayNum

  switch (month) {
    case '1月':
    case '3月':
    case '5月':
    case '7月':
    case '8月':
    case '10月':
    case '12月':
      dayNum = 31
      break;
    case '2月':
      year % 4 === 0 ? dayNum = 29 : dayNum = 28
      break;
    default:
      dayNum = 30
      break;
  }

  for (let i = 1; i < dayNum + 1; i++) {
    const target = data.filter(v => parseInt(v.grpName, 10) === i)[0]
    dataSource.push({
      day: `${i}日`,
      '销量': target ? target.productCount : 0,
      '销售额': target ? target.productPrice : 0,
    })
  }

  return dataSource
}

export function years(len) { // 生成近 len 年的数据
  let thisYear = new Date().getFullYear();
  const yearsArr = []
  for (let i = len; i > 0; i--) {
    yearsArr.push(thisYear)
    thisYear--
  }
  return yearsArr
}