// 图片分类
import React, { PureComponent } from 'react';
import { Row, Card, Tree, Divider, Spin, Alert } from 'antd';
import { imageCate as api } from "../../../../const/api";
import btnGroup from './btnGroup';
import BtnGroupComponent from '../../../public/BtnGroupComponent';
import Form from './Form';
import ModalComponent from '../../../public/ModalComponent';
import { axiosPost } from "../../../../utils/request";
import { connect } from "react-redux";

const { TreeNode } = Tree;

const getParentKey = (id, tree) => {
  let parentKey;
  for (let i = 0; i < tree.length; i++) {
    const node = tree[i];
    if (node.childCate) {
      if (node.childCate.some(item => item.id.toString() === id)) {
        parentKey = node.id;
      } else if (getParentKey(id, node.childCate)) {
        parentKey = getParentKey(id, node.childCate);
      }
    }
  }
  return parentKey;
};

const insertChild = (parentId, childData, tree) => {
  if (parentId) {
    for (let i = 0; i < tree.length; i++) {
      if (tree[i].id === parentId) {
        tree[i].childCate = tree[i].childCate || [];
        tree[i].childCate.push(childData);
        break;
      } else if (tree[i].childCate && tree[i].childCate.length) {
        tree[i].childCate = insertChild(parentId, childData, tree[i].childCate);
      }
    }
  } else {
    tree.push(childData);
  }
  return tree;
};

@connect(state => state.loading, null)
export default class ImageCate extends PureComponent {
  constructor() {
    super();
    this.state = {
      treeData: [],
      selectedCate: {},
      selectedCateId: undefined,
      parentId: undefined,
      title: '',
      modalTitle: '',
      modalVisible: false,
      expandedKeys: [],
    };
  }

  // 页面初始化时获取数据
  componentDidMount() {
    axiosPost(api.get, {}, data => {
      this.setState({
        treeData: data.data || [],
      });
    }, false);
  }

  // 渲染树组件
  renderTreeNodes = data => {
    let disabled;
    const { selectedCateId } = this.state;
    const selectedColor = { color: '#1890ff' };
    return data.map(item => {
      disabled = selectedCateId ? item.id.toString() === selectedCateId.toString() : false;
      if (item.childCate && item.childCate.length) {
        return (
          <TreeNode
            disabled={disabled}
            title={(<span style={disabled ? selectedColor : null}>{item.title}</span>)}
            key={item.id}
            value={item.id.toString()}
            dataRef={item}>
            {this.renderTreeNodes(item.childCate)}
          </TreeNode>
        );
      } else {
        return (
          <TreeNode
            disabled={disabled}
            title={(<span style={disabled ? selectedColor : null}>{item.title}</span>)}
            value={item.id.toString()}
            key={item.id} />
        );
      }
    });
  };

  // 选择一个分类
  onSelect = (selectedKeys, info) => {
    const { expandedKeys } = this.state;
    if (expandedKeys.indexOf(selectedKeys[0]) === -1) {
      expandedKeys.push(selectedKeys[0]);
    }
    this.setState({
      parentId: getParentKey(info.selectedNodes[0].key, this.state.treeData),
      selectedCate: info.node.props,
      selectedCateId: selectedKeys[0],
      title: info.node.props.title.props.children,
      expandedKeys: [...expandedKeys],
    });
  };

  // 展开或收起分类
  onExpand = (expanded, { expanded: bool, node }) => {
    const { expandedKeys } = this.state;
    const { value } = node.props;
    if (bool) {
      if (expandedKeys.indexOf(value) === -1) {
        expandedKeys.push(value);
        this.setState({ expandedKeys: [...expandedKeys] });
      }
    } else {
      const index = expandedKeys.indexOf(value);
      expandedKeys.splice(index, 1);
      this.setState({ expandedKeys: [...expandedKeys] });
    }
  };

  // 点击按钮的回调
  showModal = title => this.setState({ modalVisible: true, modalTitle: title });

  // 根据条件渲染不同的对话框
  renderModal = () => {
    const { modalTitle, title, selectedCate, treeData, parentId } = this.state;
    switch (modalTitle) {
      case '添加':
        return (
          <Form
            treeData={this.renderTreeNodes(treeData)}
            add={this.add} />
        );
      case '编辑':
        return (
          <Form
            parentId={parentId}
            treeData={this.renderTreeNodes(treeData)}
            record={selectedCate}
            title={title}
            edit={this.edit} />
        );
      default:
        return null;
    }
  };

  // 添加
  add = values => {
    const { treeData } = this.state;
    const callback = data => {
      this.setState({
        treeData: [...insertChild(values.parentId, data.data, treeData)],
        modalVisible: false,
      });
    };
    axiosPost(api.add, values, callback, true);
  };

  // 修改
  edit = values => {
    const { treeData, parentId } = this.state;
    const callback = data => {

      const insert = (tree, parentId, node) => {
        if (parentId) {
          for (let j in tree) {
            if (tree[j].id === parentId) {
              tree[j].childCate.push(node);
              break;
            } else if (tree[j].childCate && tree[j].childCate.length) {
              insert(tree[j].childCate, parentId, node);
            }
          }
        } else {
          tree.push(node);
        }
      };

      const findNodeByIdFromTree = (tree, newData) => {
        for (let i in tree) {
          if (tree[i].id === newData.id) {
            tree[i] = { ...tree[i], ...newData };
            if ((newData.parentId && parentId !== newData.parentId) || !newData.parentId) {
              insert(treeData, newData.parentId, tree[i]);
              tree.splice(i, 1);
            }
            return tree[i];
          } else if (tree[i].childCate && tree[i].childCate.length) {
            findNodeByIdFromTree(tree[i].childCate, newData);
          }
        }
      };

      findNodeByIdFromTree(treeData, data.data);

      this.setState({
        parentId: data.data.parentId,
        selectedCateId: data.data.id,
        title: data.data.title,
        treeData,
        modalVisible: false,
      });

    };

    axiosPost(api.update, values, callback, true);
  };

  render() {
    const { treeData, selectedCateId, modalTitle, expandedKeys, modalVisible } = this.state;
    const { loading } = this.props;

    return (
      <Card>
        <BtnGroupComponent
          showModal={this.showModal}
          btnGroup={btnGroup}
          record={selectedCateId} />
        <Divider dashed />
        <Alert
          message="修改分类前先点击分类名称选中分类"
          type="info"
          closeText="关闭" />
        <Row
          gutter={16}
          style={{
            textAlign: loading ? 'center' : 'left',
            paddingTop: loading ? 30 : 0,
          }}>
          {
            loading
              ? <Spin />
              : (
                <Tree
                  expandedKeys={expandedKeys}
                  onExpand={this.onExpand}
                  onSelect={this.onSelect}>
                  {this.renderTreeNodes(treeData)}
                </Tree>
              )
          }
        </Row>
        <ModalComponent
          width={560}
          title={modalTitle}
          visible={modalVisible}
          onCancel={() => this.setState({ modalVisible: false, modalTitle: '' })}>
          {this.renderModal()}
        </ModalComponent>
      </Card>
    );
  }
}
