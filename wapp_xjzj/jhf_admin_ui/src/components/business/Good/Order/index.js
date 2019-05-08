// 订单管理
import React, { PureComponent } from 'react';
import { Row, Col, Card, Divider, Alert } from 'antd';
import search from './search';
import SearchComponent from '../../../public/SearchComponent/index';
import btnGroup from './btnGroup';
import BtnGroupComponent from '../../../public/BtnGroupComponent/index';
import columns from './columns';
import TableComponent from '../../../public/TableComponent/index';
import Form from './Form';
import ModalComponent from '../../../public/ModalComponent/index';
import Detail from './detail';
import { initState } from '../../../../const/initState';
import { order as api } from '../../../../const/api';
import { axiosPost } from '../../../../utils/request';
import { connect } from 'react-redux';

@connect(state => state.loading, null)
export default class Order extends PureComponent {
  constructor() {
    super();
    this.state = { ...initState };
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state;
    axiosPost(api.get, { page: pagination.current }, data => {
      if (data.hasOwnProperty('data')) {
        this.setState({
          dataSource: data.data.list,
          pagination: { ...pagination, total: data.data.totalSize, pageSize: data.data.pageSize },
        });
      }
    }, false);
  }

  // 选择某行数据
  rowSelect = record => this.setState({ record });

  // 提交搜索
  searchSubmit = values => {
    const { pagination, search } = this.state;
    axiosPost(api.get, { ...values, page: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: { ...pagination, total: data.data.totalSize, current: 1 },
      search: { ...search, ...values },
    }), false);
  };

  // 清空搜索条件
  searchClear = () => this.setState({ search: {} });

  // 表格分页、排序、筛选变化
  handleTableChange = (page, filters, sorter) => {
    const { pagination, search } = this.state;
    const data = { ...search, page: page.current };
    const callback = data => this.setState({
      dataSource: data.data.list,
      pagination: {
        ...pagination,
        total: data.data.totalSize,
        current: page.current,
      },
    });
    axiosPost(api.get, data, callback, false);
  };

  // 点击按钮的回调
  showModal = title => this.setState({ modalVisible: true, modalTitle: title });

  // 修改
  edit = values => {
    const { dataSource } = this.state;
    const callback = data => {
      for (let i in dataSource) {
        if (dataSource[i].id === values.id) {
          dataSource[i] = { ...dataSource[i], ...data.data };
          this.setState({
            dataSource: [...dataSource],
            record: dataSource[i],
            modalVisible: false,
          });
          break;
        }
      }
    };
    axiosPost(api.update, values, callback, true);
  };

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, record } = this.state;
    switch (modalTitle) {
      case '物流发货':
        return <Form record={record} edit={this.edit} />;
      case '查看详情':
        return <Detail record={record} />;
      default:
        return null;
    }
  };

  render() {
    const { loading } = this.props;
    const { dataSource, pagination, modalVisible, modalTitle, record } = this.state;
    const table = { columns, dataSource, pagination, loading };
    return (
      <Card>
        <Alert banner message="注意：只有已付款并等待发货的订单可以修改物流状态" type="warning" style={{ marginBottom: 10 }} />
        <Row>
          <Col span={16} style={{ marginTop: -4 }}>
            <SearchComponent
              search={search}
              loading={loading}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear} />
          </Col>
          <Col span={6} style={{ textAlign: 'left' }}>
            <BtnGroupComponent
              showModal={this.showModal}
              btnGroup={btnGroup}
              record={record} />
          </Col>
        </Row>
        <Divider dashed />
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange}
          rowSelect={this.rowSelect} />
        <ModalComponent
          width={640}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    );
  }
}


