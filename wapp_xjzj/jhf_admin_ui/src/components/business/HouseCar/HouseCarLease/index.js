// 房车租赁
import React, { PureComponent } from 'react';
import { Row, Col, Card, Divider } from 'antd';
import search from './search';
import SearchComponent from '../../../public/SearchComponent';
import btnGroup from './btnGroup';
import BtnGroupComponent from '../../../public/BtnGroupComponent';
import columns from './columns';
import TableComponent from '../../../public/TableComponent';
import Form from './Form';
import ModalComponent from '../../../public/ModalComponent';
import { initState } from '../../../../const/initState';
import {
  houseCarLease as api,
} from '../../../../const/api';
import { axiosPost } from '../../../../utils/request';
import { connect } from 'react-redux';

@connect(state => state.loading, null)
export default class HouseCarLease extends PureComponent {
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
          pagination: {
            ...pagination,
            total: data.data.totalSize
          },
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

  // 添加
  add = values => {
    const { pagination } = this.state;
    const { total } = pagination.total + 1;
    const page = Math.ceil(total / 20);

    axiosPost(api.add, values, () => {
      axiosPost(api.get, { page }, data => {
        this.setState({
          dataSource: data.data.list,
          pagination: {
            ...pagination,
            total: data.data.totalSize,
            current: page,
          },
          modalVisible: false,
        });
      });
    }, true);
  };

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

  // 删除行
  deleteRow = record => {

    axiosPost(api.remove, { id: record.id }, () => {
      const { dataSource } = this.state;
      for (let i in dataSource) {
        if (dataSource[i].id === record.id) {
          dataSource.splice(i, 1);
          this.setState({ dataSource: [...dataSource], record: {} });
          break;
        }
      }
    }, true);
  };

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, record } = this.state;
    switch (modalTitle) {
      case '添加':
        return <Form add={this.add} />;
      case '编辑':
        return <Form record={record} edit={this.edit} />;
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
        <Row>
          <Col span={8} style={{ marginTop: -4 }}>
            <SearchComponent
              search={search}
              loading={loading}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear}
            />
          </Col>
          <Col span={8}>
            <BtnGroupComponent
              deleteRow={this.deleteRow}
              showModal={this.showModal}
              btnGroup={btnGroup}
              record={record}
            />
          </Col>
        </Row>
        <Divider dashed />
        <TableComponent
          table={table}
          record={record}
          onChange={this.handleTableChange}
          rowSelect={this.rowSelect} />
        <ModalComponent
          width={1000}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    );
  }
}


