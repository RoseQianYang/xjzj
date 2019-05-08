// 图片上传
import React, { PureComponent } from 'react';
import { Row, Col, Form, Upload, Alert, Button, Icon, TreeSelect, Input, message } from 'antd';
import { imageList as api } from "../../../../const/api";
import { imageList as fieldName } from "../../../../const/fieldName";
import { connect } from "react-redux";
import { formItemFull, textAlignCenter } from "../../../../const/style";
import { originOrThumb } from "../../../../utils/func";

const { Item } = Form;

@Form.create()
@connect(state => state.loading, null)
export default class ImageUpload extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      fileList: [],
      uploadImgUrl: [],
      uploadImgName: []
    };
  }

  componentDidMount() {
    if (this.props.hasOwnProperty('record')) {
      const { fileList } = this.state;
      const { record, form } = this.props;

      fileList.push({
        id: record.id,
        uid: record.id,
        name: record.title,
        status: 'done',
        url: originOrThumb(record.imgSrc, 'thumb'),
      });

      form.setFieldsValue({
        [fieldName.imgUrl]: [record.imgSrc].join(','),
        [fieldName.imgName]: [record.title].join(',')
      })

      this.setState({ fileList: [...fileList] });
    }
  }

  beforeUpload = file => { // 上传前作类型和文件大小校验
    console.log(file.type)
    const type = file.type === 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg'
    const size = file.size < 1024 * 1024 * 5
    if (type && size) {
      return true
    } else {
      message.warning(`文件 ${file.name} 的大小或类型不正确`)
      return false
    }
  }

  handleUpload = ({ file, fileList }) => { // 上传图片
    if (file.status === 'done' && file.response.code === 0) {
      const { uploadImgUrl, uploadImgName } = this.state
      this.setState({
        uploadImgUrl: [...uploadImgUrl, file.response.data],
        uploadImgName: [...uploadImgName, file.name],
      }, () => {
        this.props.form.setFieldsValue({
          [fieldName.imgUrl]: this.state.uploadImgUrl.join(','),
          [fieldName.imgName]: this.state.uploadImgName.join(',')
        })
      });
    } else if (file.status === 'done' && file.response.code !== 0) {
      message.warning(`文件 ${file.name} ${file.response.info}`)
    }
  };

  handleRemove = file => {
    let { uploadImgUrl, uploadImgName } = this.state
    uploadImgUrl = uploadImgUrl.filter(v => v !== file.response.data)
    uploadImgName = uploadImgName.filter(v => v !== file.name)

    this.setState({
      uploadImgUrl: [...uploadImgUrl],
      uploadImgName: [...uploadImgName],
    }, () => {
      this.props.form.setFieldsValue({
        [fieldName.imgUrl]: this.state.uploadImgUrl.join(','),
        [fieldName.imgName]: this.state.uploadImgName.join(',')
      })
    });
  };

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        if (this.props.hasOwnProperty('record')) {
          const { record } = this.props;
          const data = {
            // ...values,
            id: record.id,
            imgCateId: parseInt(values[fieldName.imgCateId], 10),
          };
          this.props.edit(data);
        } else {
          this.props.add(values);
        }
      }
    });
  };

  render() {
    const { fileList } = this.state;
    const { form, loading, imgCateTreeNodes, record } = this.props;
    const { getFieldDecorator } = form;

    return (
      <Form onSubmit={this.handleSubmit}>
        <Alert
          banner
          type="info"
          style={{ marginBottom: 10 }}
          message="图片大小不能超过5M，上传图片的格式必须为png/jpg，支持批量上传" />
        <Row>
          {getFieldDecorator(fieldName.imgName, {})(<Input type="hidden" />)}
          <Col span={24}>
            <Item {...formItemFull} label="图片分类">
              {getFieldDecorator(fieldName.imgCateId, {
                rules: [{ required: true, message: '请选择图片分类！' }],
                initialValue: record && record.hasOwnProperty(fieldName.imgCateId)
                  ? record[fieldName.imgCateId].toString()
                  : undefined,
              })(
                <TreeSelect allowClear placeholder='图片分类'>
                  {imgCateTreeNodes}
                </TreeSelect>,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item {...formItemFull} label="上传图片">
              <Upload
                multiple
                accept="image/png, image/jpg"
                defaultFileList={fileList}
                action={api.upload}
                listType='picture'
                onChange={this.handleUpload}
                onRemove={!record ? this.handleRemove : () => false}
                beforeUpload={this.beforeUpload}
              >
                <Button type='primary' disabled={!!record}>
                  <Icon type="upload" /> 上传图片
                </Button>
              </Upload>
              {getFieldDecorator(fieldName.imgUrl, {
                rules: [{ required: true, message: '请上传图片！' }],
              })(
                <Input type="hidden" />
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item style={textAlignCenter}>
              <Button type='primary' htmlType="submit" loading={loading}>提交</Button>
            </Item>
          </Col>
        </Row>
      </Form>
    );
  }
}
