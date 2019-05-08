import React, { PureComponent } from 'react';
import { Row, Table, Input, Popconfirm, Button, Divider, InputNumber } from "antd";
import { goodManage as fieldName } from "../../../../const/fieldName";
import { axiosPost } from "../../../../utils/request";
import { connect } from "react-redux";
import axios from 'axios'

const api = {
  get: '/jhf/api/adm/product/getProductStockList.do',
  add: '/jhf/api/adm/product/addProductStock.do',
  update: '/jhf/api/adm/product/updateProductStock.do',
  remove: '/jhf/api/adm/product//delProductStock.do',
}

function EditableCell({ addable, editable, type, value, loading, onChange, disabled }) { // 可编辑单元格
  return (
    <div>
      {
        addable || editable
          ? type === 'input'
            ?
            <Input
              disabled={loading || disabled}
              style={{ margin: '-5px 0' }}
              value={value}
              onChange={e => onChange(e.target.value)} />
            :
            <InputNumber
              disabled={loading || disabled}
              style={{ margin: '-5px 0' }}
              value={value}
              onChange={value => onChange(value)} />
          : value
      }
    </div>
  )
}

@connect(state => state.loading, null)
class Stock extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      dataSource: [],
      total: 0,
      current: 1,
    }
    this.cacheData = []
    this.handleAdd = this.handleAdd.bind(this)
    this.handleDel = this.handleDel.bind(this)
    this.handleEdit = this.handleEdit.bind(this)
    this.handleCancel = this.handleCancel.bind(this)
    this.handleChange = this.handleChange.bind(this)
    this.handleTableChange = this.handleTableChange.bind(this)
  }

  componentDidMount() {
    axiosPost(api.get, { productId: this.props.id, page: this.state.current }, res => {
      this.setState({
        dataSource: res.data.list,
        total: res.data.totalSize,
      })
      this.cacheData = res.data.list.map(item => ({ ...item }));
    })
  }

  columns = [{
    title: '库存',
    dataIndex: fieldName.stock,
    width: '25%',
    render: (text, record) =>
      <EditableCell
        addable={record.addable}
        editable={record.editable}
        type='inputNumber'
        value={text}
        loading={this.props.loading}
        onChange={value => this.handleChange(value, record.id, fieldName.stock)} />,
  }, {
    title: '颜色',
    dataIndex: fieldName.productColor,
    width: '25%',
    render: (text, record) =>
      <EditableCell
        addable={record.addable}
        editable={record.editable}
        type='input'
        value={text}
        disabled={this.props.hasColor === "N"}
        loading={this.props.loading}
        onChange={value => this.handleChange(value, record.id, fieldName.productColor)} />,
  }, {
    title: '尺码',
    dataIndex: fieldName.productSize,
    width: '25%',
    render: (text, record) =>
      <EditableCell
        addable={record.addable}
        editable={record.editable}
        type='input'
        value={text}
        disabled={this.props.hasSize === "N"}
        loading={this.props.loading}
        onChange={value => this.handleChange(value, record.id, fieldName.productSize)} />,
  }, {
    title: '操作',
    dataIndex: 'operator',
    width: '25%',
    render: (text, record) => {
      const { addable, editable } = record
      if (addable && !editable) { // 添加操作
        return (
          <span>
            <a onClick={() => this.submitAdd(record.id)}>保存</a>
            &nbsp;&nbsp;
            <Popconfirm
              title="是否确认放弃添加？"
              placement="topRight"
              okText="是"
              cancelText="否"
              onConfirm={() => this.handleDel(record.id)}>
              <a>取消</a>
            </Popconfirm>
          </span>
        )
      } else if (!addable && editable) { // 修改操作
        return (
          <span>
            <a onClick={() => this.submitEdit(record.id)}>保存</a>
            &nbsp;&nbsp;
            <Popconfirm
              title="是否确认放弃修改？"
              placement="topRight"
              okText="是"
              cancelText="否"
              onConfirm={() => this.handleCancel(record.id)}>
              <a>取消</a>
            </Popconfirm>
          </span>
        )
      } else {
        return (
          <span>
            <a onClick={() => this.handleEdit(record.id)}>修改</a>
            &nbsp;&nbsp;
            <Popconfirm
              key={record.id}
              title="确认删除？"
              okText='确认'
              cancelText='取消'
              onConfirm={() => this.handleRemove(record.id)}>
              <a>删除</a>
            </Popconfirm>
          </span>
        )
      }
    },
  }]

  handleRemove(id) {
    const { total, dataSource } = this.state;
    axios.get(api.remove, {
      params: { id }
    }).then(res => {
      if (res.status === 200 && res.data.code === 0) {
        this.setState({
          total: total - 1,
          dataSource: dataSource.filter(v => v.id !== id)
        })
      }
    })
  }

  handleChange(value, id, fieldName) { // input内容改变
    const dataSource = [...this.state.dataSource];
    const target = dataSource.filter(item => id === item.id)[0];
    if (target) {
      target[fieldName] = value;
      this.setState({ dataSource });
    }
  }

  handleAdd() { // 添加行
    const { total, current, dataSource } = this.state;
    const page = Math.ceil((total + 1) / 20)
    if (page !== current) {
      this.handleTableChange({ current: page })
    }
    const newData = {
      id: new Date().getTime(),
      [fieldName.stock]: null,
      [fieldName.productColor]: null,
      [fieldName.productSize]: null
    }
    newData.addable = true
    newData.editable = false
    this.setState({
      dataSource: [...dataSource, newData],
      total: total + 1
    })
  }

  handleDel(id) { // 删除行，即取消正在添加的行
    const { total, dataSource } = this.state;
    this.setState({
      total: total - 1,
      dataSource: dataSource.filter(v => v.id !== id)
    })
  }

  submitAdd(id) { // 提交添加
    const dataSource = [...this.state.dataSource];
    const target = dataSource.filter(v => v.id === id)[0]
    if (target) {
      axiosPost(api.add, {
        productId: this.props.id,
        productColor: target.productColor,
        productSize: target.productSize,
        stock: target.stock,
      }, res => {
        target.addable = false
        target.id = res.data.id
        this.setState({ dataSource })
        this.cacheData = dataSource.map(item => ({ ...item }));
      })
    }
  }

  handleEdit(id) { // 修改行
    const dataSource = [...this.state.dataSource];
    const target = dataSource.filter(v => v.id === id)[0];
    if (target) {
      target.editable = true
      this.setState({ dataSource })
    }
  }

  handleCancel(id) { // 取消修改
    const dataSource = [...this.state.dataSource];
    let target = dataSource.filter(v => v.id === id)[0]
    if (target) {
      Object.assign(target, this.cacheData.filter(item => id === item.id)[0]);
      target.editable = false
      this.setState({ dataSource })
    }
  }

  submitEdit(id) { // 提交修改
    const dataSource = [...this.state.dataSource];
    const target = dataSource.filter(v => v.id === id)[0]
    if (target) {
      axiosPost(api.update, {
        // productColor: target.productColor,
        // productId: this.props.id,
        // productSize: target.productSize,
        id,
        stock: target.stock,
      }, res => {
        target.editable = false
        this.setState({ dataSource })
      })
    }
  }

  handleTableChange(pagination, filters, sorter) { // 表格翻页
    this.setState({ current: pagination.current })
  }

  render() {
    const { total, current, dataSource } = this.state
    const { loading, title } = this.props

    return (
      <Row>
        <h3>商品名称：{title}</h3>
        <Button type="primary" onClick={this.handleAdd} loading={loading}>添加</Button>
        <Divider dashed />
        <Table
          rowKey='id'
          loading={loading}
          dataSource={dataSource}
          columns={this.columns}
          onChange={this.handleTableChange}
          pagination={{
            size: 'small',
            total,
            current,
            pageSize: 20,
            showTotal: (total, range) => `第 ${range[0]} - ${range[1]} 条，共 ${total} 条`,
          }}
        />
      </Row>
    );
  }
}

export default Stock;