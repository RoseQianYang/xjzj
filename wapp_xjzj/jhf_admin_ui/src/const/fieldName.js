// 登录
export const login = {
  userName: 'accountName', // 登录名
  password: 'password', // 登录密码
  verify: 'verificationCode', // 验证码
};

// 角色管理
export const role = {
  roleName: 'roleName', // 角色名称
  permissions: 'permissions', // 操作权限
  menus: 'menus', // 菜单权限
  remark: 'remark', // 备注
};

// 商品分类管理
export const goodClass = {
  id: 'id', // 唯一标识
  title: 'title', // 分类名称
  cover: 'cover', // 分类图片
  updateTime: 'updateTime', // 修改时间
  createTime: 'createTime', // 创建时间
};

// 商品管理
export const goodManage = {
  id: 'id', // 唯一标识
  cateName: 'cateName', // 商品分类
  cateId: 'cateId', // 商品分类
  brandName: 'brandName', // 商品品牌
  brandId: 'brandId', // 商品品牌
  imageId: 'imageId', // 素材图片id
  imageList: 'imageList', // 素材图片
  title: 'title', // 商品名称
  stock: 'stock', // 商品库存
  price: 'price', // 商品价格
  cover: 'cover', // 商品封面
  description: 'description', // 商品描述
  content: 'content', // 商品图文详情
  productColor: 'productColor', // 商品颜色
  productSize: 'productSize', // 商品尺码
  updateTime: 'updateTime', // 修改时间
  createTime: 'createTime', // 创建时间
};

// 品牌管理
export const brandManage = {
  id: 'id', // 唯一标识
  title: 'title', // 品牌名称
  cover: 'cover', // 品牌logo
  updateTime: 'updateTime', // 修改时间
  createTime: 'createTime', // 创建时间
};

// 订单管理
export const order = {
  id: 'id',
  userId: 'userId', // 用户ID
  userName: 'userName', // 用户名称
  userAddressId: 'userAddressId', // 用户地址ID
  orderName: 'orderName', // 订单名称
  orderStatus: 'orderStatus', // 订单状态
  logisticsId: 'logisticsId', // 物流公司id
  logisticsName: 'logisticsName', // 物流公司名称
  logisticsNo: 'logisticsNo', // 物流编号
  remark: 'remark', // 买家留言
  paymentTime: 'paymentTime', // 完成订单时间
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// 订单详情
export const orderDetail = {
  id: 'id',
  orderId: 'orderId', // 订单id
  userId: 'userId', // 用户id
  productId: 'productId', // 商品id
  productCateId: 'productCateId', // 商品分类id
  productBrandId: 'productBrandId', // 商品品牌id
  productCount: 'productCount', // 商品数量
  productPrice: 'productPrice', // 购买价格
  productColor: 'productColor', // 商品颜色
  productSize: 'productSize', // 商品尺码
};

// 物流管理
export const logistics = {
  logisticsName: 'logisticsName', // 物流名称
  logisticsCode: 'logisticsCode', // 物流编号
  firstWeightPriceInside: 'firstWeightPriceInside', // 省内首重价格
  firstWeightPriceOutside: 'firstWeightPriceOutside', // 省外首重价格
  continuousWeightPrice: 'continuousWeightPrice', // 省内续重价格
};

// 营地管理
export const campsite = {
  id: 'id',
  campsiteName: 'title', // 营地名称
  telephone: 'phone', // 联系电话
  address: 'address', // 营地地址
  campsiteAddress: 'address', // 营地地址
  categoryId: 'categoryId', // 营地分类（自由营地：1，合作营地：2）
  campsiteFunction: 'campsiteFunction', // 营地功能
  latitude: 'latitude', // 纬度
  longitude: 'longitude', // 经度
  cover: 'cover', // 封面图片
  imageId: 'imageId', // 封面图片id
  content: 'content', // 图文详情
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// 露营路线管理
export const lineManage = {
  id: 'id',
  lineName: 'title', // 露营路线名称
  address: 'address', // 露营路线地址
  cover: 'cover', // 封面图片
  imageId: 'imageId', // 封面图片id
  phone: 'phone', // 联系电话
  introduction: 'introduction', // 露营路线简介
  content: 'content', // 图文详情
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// banner管理
export const banner = {
  id: 'id',
  title: 'title', // banner名称
  description: 'description', // banner描述
  banner: 'banner', // banner图片
  createTime: 'createTime', // 发布时间
};

// 结伴同行
export const friend = {
  id: 'id',
  auditStatus: 'auditStatus', // 审核状态
  userName: 'userName', // 用户名
  detail: 'detail', // 结伴详情
  friend: 'friend', // 意向同行人
  createTime: 'createTime', // 分享时间
};

// 售后网点
export const service = {
  id: 'id',
  serviceName: 'title', // 网点名称
  serviceAddress: 'address', // 网点地址
  latitude: 'latitude', // 纬度
  longitude: 'longitude', // 经度
  cover: 'cover', // 封面图片
  imageId: 'imageId', // 封面图片id
  telephone: 'phone', // 联系方式
  content: 'content', // 图文详情
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
}

// 线下门店
export const shop = {
  id: 'id',
  shopName: 'title', // 门店名称
  shopAddress: 'address', // 门店地址
  latitude: 'latitude', // 纬度
  longitude: 'longitude', // 经度
  cover: 'cover', // 封面图片
  imageId: 'imageId', // 封面图片id
  telephone: 'phone', // 联系方式
  content: 'content', // 图文详情
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// 车友分享
export const tourism = {
  id: 'id',
  cate: 'cate', // 分类
  userName: 'userName', // 用户名
  detail: 'detail', // 分享详情
  createTime: 'createTime', // 分享时间
  status: 'status', // 审核状态
};

// 消费详单
export const consumption = {
  id: 'id',
  clientName: 'clientName', // 客户名称
  monetary: 'monetary', // 消费金额
  consumerDetail: 'consumerDetail', // 消费明细
  telephone: 'telephone', // 联系方式
  createTime: 'createTime', // 创建时间
};

// 积分明细
export const integral = {
  id: 'id',
  clientName: 'clientName', // 客户名称
  totalIntegral: 'totalIntegral', // 总积分
  usedIntegral: 'usedIntegral', // 剩余积分
  residualIntegral: 'residualIntegral', // 已用积分
  telephone: 'telephone', // 联系方式
  createTime: 'createTime', // 创建时间
};

// 返点申请
export const withdraw = {
  id: 'id',
  userName: 'userName', // 用户名称
  conversionNum: 'conversionNum', // 兑换申请积分数
  introduction: 'introduction', // 兑换申请积分说明
  conversionStatus: 'conversionStatus', // 兑换申请审核状态 Y 已审核 N 待审核" 
  createTime: 'createTime', // 申请时间
  updateTime: 'updateTime', // 审核时间
};

// 房车品鉴
export const houseCar = {
  id: 'id', // 唯一标识
  vehicleBrandId: 'vehicleBrandId', // 房车品牌id
  vehicleBrand: 'brandName', // 房车品牌名称
  houseCarName: 'vehicleName', // 房车名称
  cover: 'cover', // 房车图片
  houseCarIntro: 'introduction', // 房车简介
  price: 'price', // 预订价格
  spec: 'spec', // 规格型号
  gears: 'gears', // 档位
  isSencond: 'isSencond', // 是否二手 Y N
  isSales: 'isSales', // 是否促销 Y N
  functionType: 'functionType', // 房车类型 自行式-1 拖挂式-2 皮卡-3
  license: 'license', // 驾照级别
  bedNum: 'bedNum', // 床位
  content: 'content', // 图文详情
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// 系统日志
export const sysLog = {
  userName: 'userName', // 请求的用户
  dateTime: 'dateTime', // 日期和时间
  errorInfo: 'errorInfo', // error信息
  params: 'params', // 请求的参数
  ip: 'ip', // 请求的ip地址
  url: 'url', // 请求的url
};

// 管理员管理
export const admin = {
  id: 'id',
  accountName: 'accountName', // 登录名
  realName: 'realName', // 真实姓名
  mobile: 'mobile', // 手机号码
  password: 'password', // 密码
  roleId: 'roleId', // 角色
  roleName: 'roleName', // 角色名称
  remark: 'remark', // 备注
  isDelete: 'isDelete', // 是否有效
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// 图片管理
export const imageList = {
  id: 'id',
  imgName: 'title', // 图片名称
  imgCateId: 'imgCateId', // 图片分类ID
  imgUrl: 'imgSrc', // 图片地址
};

// 图片分类
export const imageCate = {
  id: 'id',
  title: 'title',
  childCate: 'childCate',
};

// 房车品牌
export const houseCarBrand = {
  id: 'id',
  brandName: 'brandName',
  cover: 'cover',
  createTime: 'createTime', // 创建时间
  updateTime: 'updateTime', // 修改时间
};

// 活动分类
export const actionClass = {
  id: 'id',
  title: 'title', // 分类名称
  createTime: 'createTime',
  updateTime: 'updateTime',
};

// 活动列表
export const actionList = {
  id: 'id',
  title: 'title', // 活动名称
  cover: 'cover', // 活动封面
  actionClassId: 'eventCateId', // 活动分类id
  eventCateName: 'eventCateName', // 活动分类id
  content: 'content', // 活动内容
  startTime: 'startTime', // 活动开始时间
  endTime: 'endTime', // 活动结束时间
  createTime: 'createTime',
  updateTime: 'updateTime',
};

// 房车租赁
export const houseCarLease = {
  id: 'id',
  vehicleId: 'vehicleId', // 房车id
  vehicleName: 'vehicleName', // 房车名称
  rentPrice: 'rentPrice', // 租赁价格
  address: 'address', // 联系地址
  phone: 'phone', // 联系电话
  createTime: 'createTime',
  updateTime: 'updateTime',
};

// 活动商品
export const actionGood = {
  id: 'id',
  productId: 'productId', // 商品id
  productName: 'productName', // 商品名称
  eventId: 'eventId', // 活动id
  eventName: 'eventName', // 活动名称
  eventPrice: 'eventPrice', // 活动价格
  createTime: 'createTime',
  updateTime: 'updateTime',
};
