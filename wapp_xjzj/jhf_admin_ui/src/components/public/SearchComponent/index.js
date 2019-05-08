import React, { PureComponent } from 'react'
import { Row, Form, Button } from 'antd'
import PropTypes from 'prop-types'

const { Item } = Form

@Form.create()
export default class SearchComponent extends PureComponent {
  handleSubmit = (e) => {
    e.preventDefault()
    e.stopPropagation()
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.props.searchSubmit(values)
      }
    })
  }

  clearForm = () => {
    this.props.form.resetFields()
    this.props.searchClear()
  }

  render() {
    const { search, form, loading } = this.props
    const { getFieldDecorator } = form

    return (
      <Row>
        <Form onSubmit={this.handleSubmit} layout="inline">
          {search.map(v =>
            <Item key={v.name}>{getFieldDecorator(v.name, {})(v.item)}</Item>,
          )}
          <Item>
            <Button type='primary' htmlType='submit' loading={loading}>搜索</Button>
          </Item>
          <Item>
            <Button onClick={this.clearForm}>清空</Button>
          </Item>
        </Form>
      </Row>
    )
  }
}

SearchComponent.propTypes = {
  loading: PropTypes.bool.isRequired,
}
