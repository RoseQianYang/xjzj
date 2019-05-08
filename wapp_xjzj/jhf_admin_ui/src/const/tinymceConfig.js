import React from 'react';
import { axiosPost } from '../utils/request';
import {
  imageCate as imgCateApi,
  imageList as imgListApi,
} from './api';
import { imageList as fieldName } from './fieldName';
import {
  Modal, Card, Input, TreeSelect, Divider,
  Row, Col, Spin, Pagination, Button,
  Form, Upload, Icon, Tooltip
} from 'antd';
import { formItemFull, formItemHalf, textAlignCenter, width180 } from './style';
import Search from '../components/public/SearchComponent';
import { originOrThumb } from "../utils/func";

export const tinymceConfig = {
  plugins: [
    "advlist autolink lists link image charmap print preview anchor",
    "searchreplace visualblocks code fullscreen media",
    "insertdatetime table contextmenu paste",
  ],
  image_advtab: true,
  toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image media | 插入图片",
  autosave_ask_before_unload: false,
  convert_urls: false,
  height: 400,
  setup: function (editor) {
    editor.addButton('插入图片', {
      text: '插入图片',
      icon: false,
      onclick: function () {
        const modal = Modal.info({
          title: '选择图片',
          width: 880,
          okText: '关闭',
          content: <ImgSelector selectImg={img => {
            editor.insertContent(`&nbsp;<img src="${originOrThumb(img.imgSrc, 'orgin')}"/>&nbsp;`);
            modal.destroy();
          }} />,
        });
      },
    });
  },
};

const { Meta } = Card;
const { TreeNode } = TreeSelect;
const { Item } = Form;

class ImgSelector extends React.PureComponent {
  constructor() {
    super();
    this.state = {
      loading: false,
      uploading: false,
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

  componentDidMount() {
    const { pagination } = this.state;
    this.setState({ loading: true });
    axiosPost(imgListApi.get, { page: pagination.current }, list => {
      axiosPost(imgCateApi.get, {}, cate => {
        this.setState({
          imgCate: cate.data,
          imgList: list.data.list,
          pagination: {
            ...pagination,
            total: list.data.totalSize,
          },
          loading: false,
        });
      });
    });
  }

  // 翻页
  handlePageChange = (page, filters, sorter) => {
    const { pagination, search } = this.state;
    this.setState({ loading: true });
    axiosPost(imgListApi.get, { ...search, page }, data => {
      this.setState({
        dataSource: data.data.list,
        pagination: {
          ...pagination,
          total: data.data.totalSize,
          current: page,
        },
        loading: false,
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
    this.setState({ loading: true });
    axiosPost(
      imgListApi.get,
      { ...values, imgCateId: parseInt(values.imgCateId, 10), page: 1 },
      data => this.setState({
        imgList: data.data.list,
        pagination: { ...pagination, total: data.data.totalSize, current: 1 },
        search: { ...search, ...values },
        loading: false,
      }),
      false,
    );
  };

  // 清空搜索条件
  searchClear = () => this.setState({ search: {} });

  // 选中图片
  selectImg = img => this.props.selectImg(img);

  // 上传图片
  add = () => {
    const { pagination } = this.state;
    const total = pagination.total + 1;
    const current = Math.ceil((total + 1) / 20);
    this.setState({ loading: true });
    axiosPost(imgListApi.get, { page: current }, data => {
      this.setState({
        imgList: data.data.list,
        pagination: { ...pagination, total, current },
        loading: false,
        uploading: false,
      });
    });
  };

  render() {
    const { pagination, imgList, imgCate, loading, uploading } = this.state;

    return (
      <div>
        <Search
          loading={loading}
          search={this.renderSearch()}
          searchClear={this.searchClear}
          searchSubmit={this.searchSubmit} />
        <Divider dashed />
        {
          uploading
            ? <Button type='primary' onClick={() => this.setState({ uploading: false })}>返回选择</Button>
            : <Button type='primary' onClick={() => this.setState({ uploading: true })}>上传图片</Button>
        }
        <Divider dashed />
        <Row gutter={16} style={{ textAlign: 'center', paddingTop: loading ? 30 : 0 }}>
          {
            loading
              ? <Spin />
              : uploading
                ? <ImgUpload imgCateTreeNodes={this.treeRender(imgCate)} add={this.add} />
                : <ImgList imgList={imgList} selectImg={this.selectImg} />
          }
        </Row>
        <Divider dashed />
        <Pagination size='small' {...pagination} onChange={this.handlePageChange} />
      </div>
    );
  }
}

function ImgList({ imgList, selectImg }) {
  return <Row gutter={16}>
    {
      imgList.map((v, i) => (
        <Col span={3} key={v.id} style={{ marginTop: i < 8 ? 0 : 20 }}>
          <Card cover={
            <a
              onClick={() => selectImg(v)}
              style={{ display: 'inline-block', width: '100%', height: '100%' }}>
              <img height={100} width='100%' alt="图片加载失败" src={originOrThumb(v.imgSrc, 'thumb')} />
            </a>
          }>
            <Meta description={
              <Tooltip title={v.title}>
                <p style={{ overflow: 'hidden', whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{v.title}</p>
              </Tooltip>
            } />
          </Card>
        </Col>
      ))
    }
  </Row>;
}

@Form.create()
class ImgUpload extends React.PureComponent {
  constructor(props) {
    super(props);
    this.state = { fileList: [] };
  }

  componentDidMount() {
    if (this.props.hasOwnProperty('record')) {
      const { fileList } = this.state;
      const { record, form } = this.props;
      fileList.push({
        id: record.id,
        uid: record.id,
        name: record.id,
        status: 'done',
        url: record.imgSrc,
      });
      form.setFieldsValue({ file: fileList[0] });
      this.setState({ fileList: [...fileList] });
    }
  }

  handleUpload = ({ file, fileList }) => {
    this.setState({ fileList: [file] });
  };

  handleRemove = file => {
    this.setState({ fileList: [] });
    this.props.form.setFieldsValue({ file: [] });
  };

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        if (this.props.hasOwnProperty('record')) {
          const { record } = this.props;
          const data = {
            ...values,
            id: record.id,
            imgCateId: parseInt(values[fieldName.imgCateId], 10),
            imgSrc: values.file.hasOwnProperty('file') ? values.file.file.response.data : null,
          };
          delete data.file;
          if (!data.imgSrc) delete data.imgSrc;
          this.props.edit(data);
        } else {
          const data = {
            ...values,
            [fieldName.imgCateId]: parseInt(values[fieldName.imgCateId], 10),
            [fieldName.imgUrl]: values.file.file.response.data,
          };
          delete data.file;
          this.props.add(data);
        }
      }
    });
  };

  render() {
    const { fileList } = this.state;
    const { imgCateTreeNodes } = this.props;
    const { getFieldDecorator } = this.props.form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row style={{ textAlign: 'left' }}>
          <Col span={12}>
            <Item {...formItemHalf} label="图片名称">
              {getFieldDecorator(fieldName.imgName, {
                rules: [{ required: true, message: '请输入图片名称！' }],
              })(
                <Input placeholder='图片名称' />,
              )}
            </Item>
          </Col>
          <Col span={12}>
            <Item {...formItemHalf} label="图片分类">
              {getFieldDecorator(fieldName.imgCateId, {
                rules: [{ required: true, message: '请选择图片分类！' }],
              })(
                <TreeSelect allowClear placeholder='图片分类'>
                  {imgCateTreeNodes}
                </TreeSelect>,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFull} label="上传图片">
              {getFieldDecorator('file', {
                rules: [{ required: true, message: '请上传图片！' }],
              })(
                <Upload
                  defaultFileList={fileList}
                  fileList={fileList}
                  action={imgListApi.upload}
                  listType='picture'
                  onChange={this.handleUpload}
                  onRemove={this.handleRemove}>
                  <Button type='primary'>
                    <Icon type="upload" /> 上传图片
                  </Button>
                </Upload>,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item style={textAlignCenter}>
              <Button type='primary' htmlType="submit">提交</Button>
            </Item>
          </Col>
        </Row>
      </Form>
    );
  }
}
