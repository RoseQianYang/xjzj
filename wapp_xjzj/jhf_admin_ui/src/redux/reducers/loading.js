const initState = { loading: false }
const LOADING = 'LOADING'

export function loading(state = initState, action) {
  switch (action.type) {
    case LOADING:
      return { ...state, loading: action.loading }
    default:
      return state
  }
}

export function changeLoading(boolean) {
  return dispatch => dispatch({ type: LOADING, loading: boolean })
}
