import React from 'react';
import { Card, Tabs } from "antd";
import Sale from "./Sale";
import Integration from "./Integration";
import User from "./User";

const { TabPane } = Tabs
function tabsChange(key) {
  window.dispatchEvent(new Event('resize'));
}

function Data() {
  return (
    <Card defaultactivekey="user">
      <Tabs tabPosition="left" onChange={tabsChange}>
        <TabPane tab="用户统计" key="user">
          <User />
        </TabPane>
        <TabPane tab="积分统计" key="integration">
          <Integration />
        </TabPane>
        <TabPane tab="销售统计" key="sale">
          <Sale />
        </TabPane>
      </Tabs>
    </Card>
  );
}

export default Data;