// import menu from '../../const/menu'
import { fullMenu as menu } from '../../const/menu'

const ADD = 'ADD',
  REMOVE = 'REMOVE',
  TOGGLE = 'TOGGLE'
const initState = { panes: [], activeKey: '' }

export function tabs(state = initState, action) {
  switch (action.type) {
    case ADD:
      return { ...state, ...state.panes.push(action.newTab), activeKey: action.newTab.name }
    case REMOVE:
      return { ...state, ...state.panes.splice(action.targetIndex, 1) }
    case TOGGLE:
      return { ...state, activeKey: action.activeKey }
    default:
      return state
  }
}

// 添加tab
function addTabAction(newTab) {
  return { type: ADD, newTab }
}

export function addTab(newTab) {
  return (dispatch, getStore) => {
    const { panes } = getStore().tabs

    // 如果当前没有打开的tabs，则直接添加
    if (panes.length === 0) {
      dispatch(addTabAction(newTab))
      return false
    } else {
      // 判断newTab是否已经存在
      for (let i = 0; i < panes.length; i++) {
        // newTab已打开，则跳转到该tab，跳出循环
        if (panes[i].name === newTab.name) {
          dispatch(changeTabAction(newTab.name))
          break
        }
        // 遍历到最后一项且最后一项不是newTab，则添加newTab，跳出循环
        if (i === panes.length - 1 && panes[i].name !== newTab.name) {
          dispatch(addTabAction(newTab))
          break
        }
      }
    }
  }
}

// 删除tab
export function removeTab(targetKey) {
  return (dispatch, getStore) => {
    const { panes, activeKey } = getStore().tabs
    panes.forEach((pane, i) => {
      if (pane.name === targetKey) { // 找到要删除tab的索引
        dispatch({ type: REMOVE, targetIndex: i })
        if (targetKey === activeKey) { // 如果当前删除的tab就是激活tab
          if (i === 0) {
            if (panes.length === 0) return false
            dispatch(changeTabAction(panes[i].name))
          } else {
            dispatch(changeTabAction(panes[i - 1].name))
          }
        }
      }
    })
  }
}

// 切换tab
function changeTabAction(activeKey) {
  return { type: TOGGLE, activeKey }
}

export function changeTab(activeKey) {
  return dispatch => dispatch(changeTabAction(activeKey))
}

// tab：添加Tab时遍历menu，根据link匹配subMenu，将其作为新Tab
export function getNewTab(link) {
  let newTab
  for (let i in menu) {
    for (let j in menu[i].subMenu) {
      if (link === menu[i].subMenu[j].link) {
        newTab = menu[i].subMenu[j]
        break
      }
    }
  }
  return newTab
}
