import { getStorage } from './storage'

export function originOrThumb(url, type = "thumb") { // 将图片Url改成略缩图(thumb)或原图(orgin)或方图(sq)
  const index = url.lastIndexOf('.')
  const imgType = url.slice(index)
  let newUrl, baseUrl = ''

  if (url.indexOf('http://') !== 0 && url.indexOf('https://') !== 0) {
    baseUrl = 'http://wapp.ncyunqi.com/upload/'
  }

  switch (type) {
    case 'thumb':
      newUrl = baseUrl + url.slice(0, index) + '_s' + imgType
      break;
    case 'orgin':
      newUrl = baseUrl + url.slice(0, index) + '_o' + imgType
      break;
    case 'sq':
      newUrl = baseUrl + url.slice(0, index) + '_sq' + imgType
      break;
    default:
      break;
  }

  return newUrl
}

export function filterMenu(fullMenu, authCode) { // 根据用户权限遍历菜单
  const userMenu = [...fullMenu]
  if (!authCode) {
    authCode = getStorage('jhf_app')
  }

  authCode.forEach((item, i) => {
    let targetIndex
    const targetMenu = userMenu.filter((v, k) => {
      if (v.code === item.code) targetIndex = k
      return v.code === item.code
    })[0]

    if (targetMenu) {
      targetMenu.auth = true

      authCode[i].subMenu.forEach((value, j) => {
        let targetSubMenuIndex
        const targetSubMenu = userMenu[targetIndex].subMenu.filter((v, x) => {
          if (v.code === authCode[i].subMenu[j].code) targetSubMenuIndex = x
          return v.code === authCode[i].subMenu[j].code
        })

        if (targetSubMenu) {
          userMenu[targetIndex].subMenu[targetSubMenuIndex].auth = true
        }
      })
    }
  })

  return userMenu
}