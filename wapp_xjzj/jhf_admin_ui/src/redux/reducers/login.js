// 登录
import { login as api } from '../../const/api'
import { axiosPost } from '../../utils/request'
import { setStorage, getStorage, removeStorage } from '../../utils/storage'
import { fullMenu } from '../../const/menu'
import { filterMenu } from '../../utils/func'

const user = getStorage('jhf_app') || undefined

const initState = {
  user,
  redirect: user ? null : '/login',
}

const LOGIN = 'LOGIN', LOGOUT = 'LOGOUT'

export function login(state = initState, action) {
  switch (action.type) {
    case LOGIN:
      return { ...state, user: action.user, redirect: action.redirect }
    case LOGOUT:
      return { ...state, user: {}, redirect: '/login' }
    default:
      return state
  }
}

// 登录
export function doLogin(user) {
  return dispatch => {
    const callback = data => {
      removeStorage('jhf_app')
      setStorage('jhf_app', data.data)

      const userAuthCode = JSON.parse(data.data.menus)
      const userMenu = filterMenu(fullMenu, userAuthCode)
      const redirect = userMenu
        .filter(v => v.auth === true)[0].subMenu
        .filter(v => v.auth === true)[0].link

      return dispatch({ type: LOGIN, user: data.data, redirect })
    }

    axiosPost(api.login, user, callback, true)
  }
}

// 登出
export function logout() {
  return dispatch => {
    // const callback = () => {
    removeStorage('jhf_app')
    return dispatch({ type: LOGOUT })
    // }

    // axiosPost(api.logout, {}, callback, false)
  }
}