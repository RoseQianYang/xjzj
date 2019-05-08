// 图片列表
import React, { PureComponent } from 'react';
import { Row, Col, Card, Divider, Spin, Icon, Pagination, TreeSelect, Popconfirm } from 'antd';
import { connect } from "react-redux";
import search from "./search";
import SearchComponent from '../../../public/SearchComponent';
import btnGroup from './btnGroup';
import BtnGroupComponent from '../../../public/BtnGroupComponent';
import ModalComponent from '../../../public/ModalComponent';
import ImageUpload from './Upload';
import { axiosPost } from "../../../../utils/request";
import { originOrThumb } from "../../../../utils/func";
import {
  imageList as api,
  imageCate as imageCateApi
} from "../../../../const/api";
import { imageList as fieldName } from "../../../../const/fieldName";
import { initState } from "../../../../const/initState";
import { width180 } from "../../../../const/style";
import axios from 'axios'

const { TreeNode } = TreeSelect;
const { Meta } = Card;

@connect(state => state.loading, null)
export default class ImageList extends PureComponent {
  constructor() {
    super();
    this.state = { ...initState, imageCate: [], searchBar: [] };
    this.searchClear = this.searchClear.bind(this);
    this.searchSubmit = this.searchSubmit.bind(this);
    this.searchWithImgCate = this.searchWithImgCate.bind(this);
    this.handleTableChange = this.handleTableChange.bind(this);
  }

  // 页面初始化时获取数据
  componentDidMount() {
    const { pagination } = this.state;

    axiosPost(api.get, { page: pagination.current }, data => {
      if (data.hasOwnProperty('data')) {
        this.setState({
          dataSource: data.data.list,
          pagination: { ...pagination, total: data.data.totalSize },
        });
      }

      axiosPost(imageCateApi.get, {}, imageCate => {
        if (imageCate.hasOwnProperty('data')) {
          this.setState({ imageCate: imageCate.data });
        }
        this.searchWithImgCate(search);
      });
    }, false);
  }

  renderTreeNodes = treeData => treeData.map(item => {
    if (item.childCate && item.childCate.length) {
      return (
        <TreeNode
          title={item.title}
          key={item.id}
          value={item.id.toString()}
          dataRef={item}>
          {this.renderTreeNodes(item.childCate)}
        </TreeNode>
      );
    } else {
      return (
        <TreeNode
          title={item.title}
          value={item.id.toString()}
          key={item.id} />
      );
    }
  });

  searchWithImgCate(search) {
    const { imageCate } = this.state;

    search.push({
      name: fieldName.imgCateId,
      item: <TreeSelect
        allowClear
        placeholder="图片分类"
        style={width180}>
        {this.renderTreeNodes(imageCate)}
      </TreeSelect>,
    });
    this.setState({ searchBar: [...search] });
  }

  add = values => {
    axiosPost(api.add, values, () => {
      const { pagination } = this.state;
      // const total = pagination.total + 1;
      // const current = Math.ceil((total + 1) / 20);
      this.setState({
        modalTitle: '',
        modalVisible: false,
      });
      axiosPost(api.get, { page: 1 }, data => {
        this.setState({
          dataSource: data.data.list,
          pagination: { ...pagination, total: data.data.totalSize, current: 1 },
        });
      }, false);
    }, true);
  };

  edit = values => {
    axiosPost(api.update, values, data => {
      const { record, dataSource } = this.state;
      for (let i in dataSource) {
        if (dataSource[i].id === record.id) {
          dataSource[i] = { ...data.data };
          break;
        }
      }
      this.setState({
        modalTitle: '',
        modalVisible: false,
        dataSource: [...dataSource],
      });
    }, true);
  };

  // 表格分页、排序、筛选变化
  handleTableChange(page, filters, sorter) {
    const { pagination, search } = this.state;
    const callback = data => {
      this.setState({
        dataSource: data.data.list,
        pagination: {
          ...pagination,
          total: data.data.totalSize,
          current: page,
        },
      });
    };
    axiosPost(api.get, { ...search, page }, callback, false);
  }

  // 提交搜索
  searchSubmit(values) {
    const { pagination, search } = this.state;
    if (values.hasOwnProperty('imgCateId')) {
      values.imgCateId = parseInt(values.imgCateId, 10);
    }
    axiosPost(api.get, { ...values, page: 1 }, data => this.setState({
      dataSource: data.data.list,
      pagination: { ...pagination, total: data.data.totalSize, current: 1 },
      search: { ...search, ...values },
    }), false);
  }

  // 清空搜索条件
  searchClear() {
    this.setState({ search: {} });
  }

  // 点击按钮的回调
  showModal = title => this.setState({ modalVisible: true, modalTitle: title });

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, imageCate, record } = this.state;
    const { loading } = this.props;
    const imgCateTreeNodes = this.renderTreeNodes(imageCate);
    switch (modalTitle) {
      case '上传':
        return <ImageUpload
          loading={loading}
          imgCateTreeNodes={imgCateTreeNodes}
          add={this.add} />;
      case '修改':
        return <ImageUpload
          loading={loading}
          imgCateTreeNodes={imgCateTreeNodes}
          edit={this.edit}
          record={record} />;
      default:
        return null;
    }
  };

  render() {
    const { loading } = this.props;
    const { dataSource, pagination, modalTitle, modalVisible, searchBar, search } = this.state;
    return (
      <Card>
        <Row>
          <Col span={12} style={{ marginTop: -4 }}>
            <SearchComponent
              search={searchBar}
              loading={loading}
              searchSubmit={this.searchSubmit}
              searchClear={this.searchClear}
            />
          </Col>
          <Col span={8}>
            <BtnGroupComponent
              showModal={this.showModal}
              btnGroup={btnGroup}
            />
          </Col>
        </Row>
        <Divider dashed />
        <Row gutter={16} style={{ textAlign: 'center', paddingTop: loading ? 30 : 0 }}>
          {
            loading
              ? <Spin />
              : dataSource.map((v, i) => (
                <Col span={3} key={v.id} style={{ marginTop: i < 8 ? 0 : 20 }}>
                  <Card
                    bodyStyle={{ padding: '12px' }}
                    cover={
                      <img
                        height={120}
                        alt="图片加载失败"
                        src={originOrThumb(v.imgSrc, 'thumb')}
                      />
                    }
                    actions={[
                      <Icon
                        type="search"
                        onClick={() => window.open(originOrThumb(v.imgSrc, 'orgin'))}
                        style={{ fontSize: 18 }} />,
                      <Icon
                        type="edit"
                        onClick={() => {
                          this.setState({ record: v });
                          this.showModal('修改');
                        }}
                        style={{ fontSize: 16 }} />,
                      <Popconfirm
                        title="确认删除？"
                        okText="确认"
                        cancelText="取消"
                        onConfirm={() => {
                          axios.get(api.remove, { params: { imageId: v.id } }).then(res => {
                            if (res.status === 200 && res.data.code === 0) {
                              axiosPost(api.get, { page: pagination.current, ...search }, data => {
                                this.setState({
                                  dataSource: data.data.list,
                                  pagination: { ...pagination, total: data.data.totalSize },
                                });
                              })
                            }
                          })
                        }}
                      >
                        <Icon type="delete" style={{ fontSize: 16 }} />
                      </Popconfirm>,
                    ]}
                  >
                    <Meta title={<span style={{ fontWeight: 400, fontSize: 14 }}>{v.title}</span>} />
                  </Card>
                </Col>
              ))
          }
        </Row>
        <Divider dashed />
        <Pagination
          size='small'
          {...pagination}
          onChange={this.handleTableChange} />
        <ModalComponent
          width={560}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    );
  }
}
