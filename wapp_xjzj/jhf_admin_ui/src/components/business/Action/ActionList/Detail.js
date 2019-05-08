import React from 'react';
import axios from 'axios';
import { actionList as api } from '../../../../const/api';

export default class ActionDetail extends React.PureComponent {
  componentDidMount() {
    axios.get(api.detail, {
      params: { eventId: this.props.id },
    }).then(res => console.log('活动详情：', res));
  }

  render() {
    return (
      <h2>活动详情</h2>
    );
  }
}
