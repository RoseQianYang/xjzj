import React from 'react';
import { Button, Modal } from 'antd';
import Upload from '../../business/Image/ImageList/Upload';
import { axiosPost } from "../../../utils/request";
import { imageList } from "../../../const/api";
import PropTypes from 'prop-types';

export default class UploadModal extends React.PureComponent {
  state = { visible: false };

  add = values => {
    this.setState({ visible: false });
    axiosPost(imageList.add, values, data => this.props.add(data), true);
  };

  render() {
    const { visible } = this.state;
    const { treeNodes } = this.props;
    const modalProps = {
      visible,
      footer: null,
      title: '上传图片',
      destroyOnClose: true,
      onCancel: () => this.setState({ visible: false }),
    };

    return (
      <div>
        <Button type='primary' onClick={() => this.setState({ visible: true })}>上传图片</Button>
        <Modal {...modalProps}>
          <Upload imgCateTreeNodes={treeNodes} add={this.add} />
        </Modal>
      </div>
    );
  }
}

UploadModal.propTypes = {
  add: PropTypes.func.isRequired,
  treeNodes: PropTypes.node.isRequired,
};
