import React, { PureComponent } from 'react'
import { Form, Input, Row, Col, Button, TreeSelect, Transfer } from 'antd'
import { textAlignCenter, textAreaSize } from '../../../../const/style'
import { role as fieldName } from '../../../../const/fieldName'
import { role as api } from "../../../../const/api"
import { formItemFull } from '../../../../const/style'
import { axiosPost } from "../../../../utils/request"
import { fullMenu } from "../../../../const/menu"

const { Item } = Form
const { TextArea } = Input
const { TreeNode, SHOW_ALL } = TreeSelect

function renderTreeNode() {
  return fullMenu.map(v => (
    <TreeNode value={v.code} title={v.name} key={v.code}>
      {v.subMenu.map(k => (
        <TreeNode value={k.code} title={k.name} key={k.code} />
      ))}
    </TreeNode>
  ))
}

@Form.create()
export default class RoleForm extends PureComponent {
  constructor(props) {
    super(props)
    this.state = {
      treeSelectValue: [],
      permission: [],
      permissionId: [],
    }
    this.getTreeSelectValue = this.getTreeSelectValue.bind(this)
  }

  componentDidMount() {
    const { record, form } = this.props

    if (record.hasOwnProperty('menus') && record.menus) {
      const userMenus = JSON.parse(record.menus)
      const treeSelectValue = []

      // 将角色的菜单权限数据格式改为数组，以便树形选择器渲染
      userMenus.forEach(i => {
        treeSelectValue.push(i.code)
        i.subMenu.forEach(j => treeSelectValue.push(j.code))
      })

      this.setState({ treeSelectValue: JSON.parse(record.menus) })
      form.setFieldsValue({ menus: treeSelectValue })
    }

    axiosPost(api.getPermission, {}, data => { // 获取全部权限列表
      if (record.hasOwnProperty('permissions') && record.permissions) {
        const userPermission = JSON.parse(record.permissions)
        const permissionId = []

        userPermission.forEach(v => permissionId.push(v.id))

        this.setState({ permission: data.data, permissionId })
        form.setFieldsValue({ permissions: permissionId })
      } else {
        this.setState({ permission: data.data })
      }
    }, false)
  }

  // 获取菜单权限并包装成树形数据结构
  getTreeSelectValue(value, label, extra) {
    const treeSelectValue = []
    let lastParentIndex = -1
    let nowIndex = -1

    extra.allCheckedNodes.forEach(v => {
      const indexArr = v.pos.split('-')
      const parentIndex = indexArr[1]
      const childIndex = indexArr[2]

      if (lastParentIndex !== parentIndex) {
        lastParentIndex = parentIndex
        nowIndex++
        treeSelectValue[nowIndex] = { code: '' }
      }

      treeSelectValue[nowIndex].code = fullMenu[parentIndex].code

      if (childIndex) {
        if (!treeSelectValue[nowIndex].subMenu) {
          treeSelectValue[nowIndex].subMenu = []
        }
        treeSelectValue[nowIndex].subMenu.push({ code: fullMenu[parentIndex].subMenu[childIndex].code })
      } else {
        if (!treeSelectValue[nowIndex].subMenu) {
          treeSelectValue[nowIndex].subMenu = []
        }
        fullMenu[parentIndex].subMenu.forEach(v => {
          treeSelectValue[nowIndex].subMenu.push({ code: v.code })
        })
      }
    })

    this.setState({ treeSelectValue })
  }

  // 穿梭框的筛选条件
  filterOption = (inputValue, option) => option.name.indexOf(inputValue) > -1

  // 获取穿梭框数据
  handleTransferChange = (targetKeys) => this.setState({ permissionId: targetKeys })

  handleSubmit = (e) => {
    e.preventDefault()
    const { add, edit, record, form } = this.props
    form.validateFields((err, values) => {
      if (!err) {
        const permission = []
        this.state.permissionId.forEach(v => {
          for (let i in this.state.permission) {
            if (this.state.permission[i].id === v) {
              permission.push(this.state.permission[i])
              break
            }
          }
        })

        values = {
          roleName: values.roleName,
          menus: JSON.stringify(this.state.treeSelectValue),
          permissions: JSON.stringify(permission),
          remark: values.remark,
        }

        if (record.id) {
          edit({ ...values, id: record.id })
        } else {
          add(values)
        }
      }
    })
  }

  render() {
    const { loading, form, record } = this.props
    const { getFieldDecorator } = form

    return (
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <Col span={24}>
            <Item label="角色名称" {...formItemFull}>
              {getFieldDecorator(fieldName.roleName, {
                rules: [{ required: true, message: '角色名称不能为空' }],
                initialValue: record && record.hasOwnProperty(fieldName.roleName)
                  ? record[fieldName.roleName]
                  : undefined,
              })(
                <Input placeholder="请输入角色名称" />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item label="菜单权限" {...formItemFull}>
              {getFieldDecorator(fieldName.menus, {
                rules: [{ required: true, message: '菜单权限不能为空！' }],
              })(
                <TreeSelect
                  allowClear
                  onChange={this.getTreeSelectValue}
                  treeCheckable={true}
                  showCheckedStrategy={SHOW_ALL}
                  searchPlaceholder='请选择菜单权限'
                  treeNodeFilterProp='title'
                  dropdownStyle={{ height: '50vh' }}>
                  {renderTreeNode()}
                </TreeSelect>,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item label="操作权限" {...formItemFull}>
              {getFieldDecorator(fieldName.permissions, {
                rules: [{ required: true, message: '操作权限不能为空！' }],
              })(
                <Transfer
                  showSearch
                  rowKey={record => record.id}
                  filterOption={this.filterOption}
                  dataSource={this.state.permission}
                  targetKeys={this.state.permissionId}
                  onChange={this.handleTransferChange}
                  render={item => item.name} />,
              )}
            </Item>
          </Col>
          <Col span={24}>
            <Item label="备注" {...formItemFull}>
              {getFieldDecorator(fieldName.remark, {
                initialValue: record && record.hasOwnProperty(fieldName.remark)
                  ? record[fieldName.remark]
                  : undefined,
              })(
                <TextArea autosize={textAreaSize} placeholder="备注" />,
              )}
            </Item>
          </Col>
        </Row>
        <Item style={textAlignCenter}>
          <Button type='primary' htmlType="submit" loading={loading}>提交</Button>
        </Item>
      </Form>
    )
  }
}