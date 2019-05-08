import { houseCar as fieldName } from '../../../const/fieldName';

export default [
  { title: '房车名称', dataIndex: fieldName.houseCarName },
  { title: '房车品牌', dataIndex: fieldName.vehicleBrand, width: 160 },
  { title: '官方指导价', dataIndex: fieldName.price, width: 140 },
  { title: '燃油类型', dataIndex: fieldName.spec, width: 140 },
  { title: '档位', dataIndex: fieldName.gears, width: 140 },
  {
    title: '是否二手',
    dataIndex: fieldName.isSencond,
    width: 100,
    render: isSencond => isSencond === 'Y' ? '是' : '否',
  },
  {
    title: '是否促销',
    dataIndex: fieldName.isSales,
    width: 100,
    render: isSales => isSales === 'Y' ? '是' : '否',
  },
  {
    title: '房车类型',
    dataIndex: fieldName.functionType,
    width: 140,
    render: functionType => {
      switch (functionType) {
        case 1:
          return '自行式';
        case 2:
          return '拖挂式';
        case 3:
          return '皮卡';
        default:
          return null;
      }
    },
  },
];
