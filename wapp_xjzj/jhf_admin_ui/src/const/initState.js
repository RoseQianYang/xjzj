export const initState = {
  pagination: {
    current: 1,
    defaultCurrent: 1,
    pageSize: 20,
    defaultPageSize: 20,
    total: 0,
    showTotal: (total, range) => `第 ${range[0]} - ${range[1]} 条，共 ${total} 条`,
  },
  dataSource: [],
  modalTitle: '',
  modalVisible: false,
  record: {},
  index: null,
  search: {},
};
