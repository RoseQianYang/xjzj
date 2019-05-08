import React from 'react';
import { Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import PropTypes from 'prop-types';
import './index.css';

const ModalComponent = ({ title, visible, children, width, onCancel }) => {
  document.body.style.overflow = visible ? 'hidden' : 'auto';
  const modalWidth = width || 720;

  return [
    <QueueAnim key='modal-component-mask-anim' type='alpha'>
      {visible ? [
        <div className="modal-component-mask" onClick={onCancel} key='modal-component-mask' />,
      ] : null}
    </QueueAnim>,
    <QueueAnim key='modal-component-root-anim'>
      {visible ? [
        <div className="modal-component-root" style={{ width: modalWidth }} key="modal-component-root">
          <header className="modal-component-header">
            <div className="modal-component-title">
              {title}
              <span className="modal-compoennt-close" onClick={onCancel}>
                <Icon type='close' className="modal-component-icon" />
              </span>
            </div>
          </header>
          <div className="modal-component-body">
            {visible ? children : null}
          </div>
        </div>] : null}
    </QueueAnim>,
  ];
};

ModalComponent.propTypes = {
  title: PropTypes.string.isRequired, // 对话框标题，必需
  width: PropTypes.number, // 对话框宽度，默认为800px
  visible: PropTypes.bool.isRequired, // 对话框是否隐藏，必需
  onCancel: PropTypes.func.isRequired, // 对话框关闭函数，必需
};

export default ModalComponent;
