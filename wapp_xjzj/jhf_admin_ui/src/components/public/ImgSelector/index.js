import React from 'react';
import { Modal, Button, Card, Input, TreeSelect, Divider, Row, Col, Spin, Pagination, Tooltip } from 'antd';
import {
  imageList as imgListApi,
  imageCate as imgCateApi,
} from "../../../const/api";
import Search from '../../public/SearchComponent';
import { axiosPost } from "../../../utils/request";
import { originOrThumb } from "../../../utils/func";
import { connect } from "react-redux";
import { imageList as fieldName } from "../../../const/fieldName";
import { width180 } from "../../../const/style";
import PropTypes from 'prop-types';
import UploadModal from './UploadModal';

const { Meta } = Card;
const { TreeNode } = TreeSelect;

@connect(state => state.loading, null)
export default class ImgSelector extends React.PureComponent {
  constructor() {
    super();
    this.state = {
      visible: false,
      imgList: [],
      imgCate: [],
      search: {},
      pagination: {
        current: 1,
        defaultCurrent: 1,
        pageSize: 20,
        defaultPageSize: 20,
        total: 0,
        showTotal: (total, range) => `第 ${range[0]} - ${range[1]} 条，共 ${total} 条`,
      },
    };
  }

  // 翻页
  handlePageChange = (page, filters, sorter) => {
    const { pagination, search } = this.state;
    axiosPost(imgListApi.get, { ...search, page }, data => {
      this.setState({
        imgList: data.data.list,
        pagination: {
          ...pagination,
          total: data.data.totalSize,
          current: page,
        },
      });
    });
  };

  treeRender = treeData => treeData.map(item => {
    if (item.childCate && item.childCate.length) {
      return (
        <TreeNode title={item.title} key={item.id} value={item.id.toString()} dataRef={item}>
          {this.treeRender(item.childCate)}
        </TreeNode>
      );
    } else {
      return <TreeNode title={item.title} value={item.id.toString()} key={item.id} />;
    }
  });

  // 定义搜索框
  renderSearch = () => [
    { name: fieldName.imgName, item: <Input placeholder="图片名称" style={width180} /> },
    {
      name: fieldName.imgCateId, item: (
        <TreeSelect placeholder="图片分类" style={width180}>
          {this.treeRender(this.state.imgCate)}
        </TreeSelect>
      ),
    },
  ];

  // 提交搜索
  searchSubmit = values => {
    const { pagination, search } = this.state;
    if (values.imgCateId) {
      values.imgCateId = parseInt(values.imgCateId, 10)
    }
    axiosPost(
      imgListApi.get,
      { ...values, page: 1 },
      data => this.setState({
        imgList: data.data.list,
        pagination: {
          ...pagination,
          total: data.data.totalSize,
          current: 1
        },
        search: { ...search, ...values },
      }),
      false,
    );
  };

  // 清空搜索条件
  searchClear = () => this.setState({ search: {} });

  // 选中图片
  selectImg = img => {
    this.props.selectImg(img);
    this.setState({ visible: false });
  };

  // 上传图片
  add = () => {
    const { pagination } = this.state;
    const total = pagination.total + 1;
    const current = Math.ceil((total + 1) / 20);
    axiosPost(imgListApi.get, { page: current }, data => {
      this.setState({
        imgList: data.data.list,
        pagination: { ...pagination, total, current },
      });
    });
  };

  // 显示图片列表对话框
  showSelectModal = () => {
    this.setState({ visible: true });
    const { pagination } = this.state;
    axiosPost(imgListApi.get, { page: pagination.current }, list => {
      axiosPost(imgCateApi.get, {}, cate => {
        this.setState({
          imgCate: cate.data,
          imgList: list.data.list,
          pagination: {
            ...pagination,
            total: list.data.totalSize,
          },
        });
      });
    });
  };

  render() {
    const { visible, pagination, imgList, imgCate } = this.state;
    const { loading } = this.props;
    const modalProps = {
      visible,
      width: 880,
      footer: null,
      title: '选择图片',
      onCancel: () => this.setState({ visible: false }),
    };

    return (
      <div>
        <Button
          type='primary'
          onClick={this.showSelectModal}>选择图片</Button>
        <Modal {...modalProps}>
          <Row>
            <Col span={17} style={{ marginTop: -4 }}>
              <Search
                loading={loading}
                search={this.renderSearch()}
                searchClear={this.searchClear}
                searchSubmit={this.searchSubmit} />
            </Col>
            <Col span={7}>
              <UploadModal treeNodes={this.treeRender(imgCate)} add={this.add} />
            </Col>
          </Row>
          <Divider dashed />
          <Row gutter={16} style={{ textAlign: 'center', paddingTop: loading ? 30 : 0 }}>
            {
              loading
                ? <Spin />
                : imgList.map((v, i) => (
                  <Col span={3} key={v.id} style={{ marginTop: i < 8 ? 0 : 20 }}>
                    <Card cover={
                      <a
                        onClick={() => this.selectImg(v)}
                        style={{ display: 'inline-block', width: '100%', height: '100%' }}>
                        <img
                          height="100px"
                          width='100%'
                          alt="图片加载失败"
                          src={originOrThumb(v.imgSrc, 'sq')} />
                      </a>
                    }>
                      <Meta description={
                        <Tooltip title={v.title}>
                          <p style={{
                            overflow: 'hidden',
                            whiteSpace: 'nowrap',
                            textOverflow: 'ellipsis'
                          }}>{v.title}</p>
                        </Tooltip>
                      } />
                    </Card>
                  </Col>
                ))
            }
          </Row>
          <Divider dashed />
          <Pagination size='small' {...pagination} onChange={this.handlePageChange} />
        </Modal>
      </div>
    );
  }
}

ImgSelector.propTypes = {
  selectImg: PropTypes.func.isRequired,
};
