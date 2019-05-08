import { combineReducers } from 'redux'
import { login } from './reducers/login'
import { loading } from './reducers/loading'
import { tabs } from "./reducers/tabs"

export default combineReducers({ login, loading, tabs })