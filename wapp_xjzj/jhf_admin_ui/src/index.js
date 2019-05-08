import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { HashRouter, Route, Switch } from 'react-router-dom';
import { Layout } from 'antd';
import Login from './components/business/Login';
import App from './components/App';
import Interceptor from './components/public/Interceptor';
import store from './redux/store';
import registerServiceWorker from './registerServiceWorker';
import './assets/font-awesome-4.7.0/css/font-awesome.min.css';

ReactDOM.render(
  <Provider store={store}>
    <HashRouter>
      <Layout>
        <Interceptor />
        <Switch>
          <Route path='/app' component={App} />
          <Route component={Login} />
        </Switch>
      </Layout>
    </HashRouter>
  </Provider>,
  document.getElementById('root'),
);

registerServiceWorker();
