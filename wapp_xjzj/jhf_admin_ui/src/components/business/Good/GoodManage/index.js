// 商品管理
import React, { PureComponent } from 'react';
import { Row, Col, Card, Divider, Tag } from 'antd';
import search from './search';
import SearchComponent from '../../../public/SearchComponent/index';
import btnGroup from './btnGroup';
import BtnGroupComponent from '../../../public/BtnGroupComponent/index';
import TableComponent from '../../../public/TableComponent/index';
import Form from './Form';
import ModalComponent from '../../../public/ModalComponent/index';
import { initState } from '../../../../const/initState';
import {
  goodManage as api,
  goodClass as cateApi,
  brandManage as brandApi,
} from '../../../../const/api';
import { goodManage as fieldName } from '../../../../const/fieldName'
import { axiosPost } from '../../../../utils/request';
import { connect } from 'react-redux';
import axios from 'axios';
// import Detail from './detail';
import Stock from "./Stock";

@connect(state => state.loading, null)
export default class GoodManage extends PureComponent {
  constructor() {
    super();
    this.state = { ...initState, cate: [], brand: [], /* columns: [] */ };
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state;

    // this.columnsWithOperator()

    axiosPost(api.get, { page: pagination.current }, data => {
      if (data.hasOwnProperty('data')) {
        axios.get(cateApi.all).then(cate => {
          axios.get(brandApi.all).then(brand => {
            this.setState({
              cate: cate.data.data,
              brand: brand.data.data,
              dataSource: data.data.list,
              pagination: { ...pagination, total: data.data.totalSize },
            });
          });
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
    const { dataSource } = this.state;
    const callback = data => {
      dataSource.push(data.data);
      this.setState({
        dataSource: [...dataSource],
        record: data.data,
        // modalVisible: false,
        modalTitle: '库存管理'
      });
    };
    axiosPost(api.add, values, callback, true);
  };

  componentWillUnmount() {
    clearInterval(this.timerID);
  }

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
  // deleteRow = record => {
  //   const callback = () => {
  //     const { dataSource } = this.state;
  //     for (let i in dataSource) {
  //       if (dataSource[i].id === record.id) {
  //         dataSource.splice(i, 1);
  //         this.setState({ dataSource: [...dataSource], record: {} });
  //         break;
  //       }
  //     }
  //   };
  //   axiosPost(api.remove, { id: record.id }, callback, true);
  // };

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, record, brand, cate } = this.state;
    switch (modalTitle) {
      case '添加':
        return <Form add={this.add} brand={brand} cate={cate} />;
      case '编辑':
        return <Form record={record} edit={this.edit} brand={brand} cate={cate} />;
      // case '商品详情':
      //   return <Detail id={record.id} />;
      case '库存管理':
        return <Stock id={record.id} title={record.title} hasColor={record.hasColor} hasSize={record.hasSize} />
      default:
        return null;
    }
  };

  columns = [
    { title: '商品名称', dataIndex: fieldName.title },
    { title: '商品分类', dataIndex: fieldName.cateName, width: 120 },
    { title: '商品品牌', dataIndex: fieldName.brandName, width: 120 },
    { title: '商品价格', dataIndex: fieldName.price, width: 120, render: price => price / 100 },
    {
      title: '状态', dataIndex: 'putaway', width: 150, render: putaway => {
        switch (putaway) {
          case 'Y':
            return <Tag color="green">上架中</Tag>
          case 'N':
            return <Tag color="red">下架中</Tag>
          default:
            return <Tag color="gray">数据错误</Tag>
        }
      }
    },
    { title: '创建时间', dataIndex: fieldName.createTime, width: 150 },
    {
      title: '操作', dataIndex: 'operator', width: 180,
      render: (text, record, index) => (
        <span>
          <a onClick={
            () => axiosPost(
              '/jhf/api/adm/product/putawayProduct.do',
              {
                id: record.id,
                putaway: record.putaway === "Y" ? "N" : "Y"
              },
              () => {
                const { pagination } = this.state
                axiosPost(api.get, { page: pagination.current }, data => {
                  if (data.hasOwnProperty('data') && data.data) {
                    this.setState({
                      dataSource: data.data.list,
                      pagination: { ...pagination, total: data.data.totalSize },
                    })
                  }
                }, false)
              })
          }>
            {record.putaway === 'Y' ? '下架' : '上架'}
          </a>
        </span>
      ),
    }
  ]

  render() {
    const { loading } = this.props;
    const { dataSource, pagination, modalVisible, modalTitle, record, /* columns */ } = this.state;
    const columns = this.columns
    const table = { columns, dataSource, pagination, loading };
    return (
      <Card>
        <Row>
          <Col span={8} style={{ marginTop: -4 }}>
            <SearchComponent
              loading={loading}
              search={search}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear} />
          </Col>
          <Col span={8}>
            <BtnGroupComponent
              // deleteRow={this.deleteRow}
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
          width={modalTitle === '库存管理' ? 800 : 1360}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    );
  }
}


