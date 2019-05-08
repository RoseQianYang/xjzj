const baseurl = '/jhf/api/adm';

// 登录登出
export const login = {
  login: `/jhf/api/adminLogin.do`, // 登录
  verify: `/jhf/api/getAdminCaptcha.do`, // 验证码
};

// 文件上传下载
export const file = {
  upload: `/upload.do`,
  download: `/download.do`,
};

// 角色管理
export const role = {
  add: `${baseurl}/user/addRoleInfo.do`,
  get: `${baseurl}/user/getRolePageList.do`,
  all: `${baseurl}/user/getRoleList.do`, // get
  update: `${baseurl}/user/updateRole.do`,
  remove: `${baseurl}/user/delRoleInfo.do`,
  getPermission: `${baseurl}/user/getPermissionsList.do`,
  getMenus: `${baseurl}/user/getMenusList.do`,
};

// 商品分类
export const goodClass = {
  add: `${baseurl}/productCate/addProductCate.do`,
  get: `${baseurl}/productCate/getProductCateList.do`,
  all: `${baseurl}/productCate/getProductCateInfoList.do`, // get提交，不传参
  update: `${baseurl}/productCate/updateProductCate.do`,
};

// 商品管理
export const goodManage = {
  add: `${baseurl}/product/addProductInfo.do`,
  get: `${baseurl}/product/getProductList.do`,
  update: `${baseurl}/product/updateProduct.do`,
  detail: `${baseurl}/product/getProductById.do`,
};

// 品牌管理
export const brandManage = {
  add: `${baseurl}/productBrand/addProductBrand.do`,
  get: `${baseurl}/productBrand/getProductBrandList.do`,
  all: `${baseurl}/productBrand/getProductBrandInfoList.do`, // get提交，不传参
  update: `${baseurl}/productBrand/updateProductBrand.do`,
};

// 订单管理
export const order = {
  get: `${baseurl}/order/getOrderList.do`,
  update: `${baseurl}/order/updateOrder.do`,
  detail: `${baseurl}/orderDetail//getOrderDetailList.do`,
};

// 物流管理
export const logistics = {
  add: `${baseurl}/logistics/add.do`,
  get: `${baseurl}/logistics/get.do`,
  update: `${baseurl}/logistics/update.do`,
  remove: `${baseurl}/logistics/del.do`,
};

// 营地管理
export const campsite = {
  add: `${baseurl}/campsite/addCampsite.do`,
  get: `${baseurl}/campsite/getCampsitePageList.do`,
  update: `${baseurl}/campsite/updateCampsite.do`,
  remove: `${baseurl}/campsite/delCampsite.do`,
  detail: `${baseurl}/campsite/getCampsiteById.do`,
};

// 线路管理
export const lineManage = {
  add: `${baseurl}/route/addMobileRoute.do`,
  get: `${baseurl}/route/getMobileRoutePageList.do`,
  update: `${baseurl}/route/updateMobileRoute.do`,
  remove: `${baseurl}/route/delMobileRoute.do`,
  detail: `${baseurl}/route/getMobileRouteById.do`,
};

// banner管理
export const banner = {
  add: `${baseurl}/banner/add.do`,
  get: `${baseurl}/banner/get.do`,
  update: `${baseurl}/banner/update.do`,
  remove: `${baseurl}/banner/del.do`,
};

// 售后网点
export const service = {
  add: `${baseurl}/outlets/addAfterOutlets.do`,
  get: `${baseurl}/outlets/getAfterOutletsPageList.do`,
  update: `${baseurl}/outlets/updateAfterOutlets.do`,
  remove: `${baseurl}/outlets/delAfterOutlets.do`,
  detail: `${baseurl}/outlets/getAfterOutletsById.do`,
};

// 线下网点
export const shop = {
  add: `${baseurl}/store/addPhysicalStore.do`,
  remove: `${baseurl}/store/delPhysicalStore.do`,
  get: `${baseurl}/store/getPhysicalStorePageList.do`,
  update: `${baseurl}/store/updatePhysicalStore.do`,
  detail: `${baseurl}/store/getPhysicalStoreById.do`,
};

// 消费详单
export const consumption = {
  get: `${baseurl}/consumption/get.do`,
  remove: `${baseurl}/consumption/del.do`,
};

// 积分明细
export const integral = {
  get: `${baseurl}/integral/get.do`,
  remove: `${baseurl}/integral/del.do`,
};

// 提现申请
export const withdraw = {
  get: `${baseurl}/integralConversion/getIntegralConversionList.do`,
  audit: `${baseurl}/integralConversion/updateIntegralConversionStatus.do`,
};

// 房车品鉴
export const houseCar = {
  add: `${baseurl}/vehicle/create.do`,
  get: `${baseurl}/vehicle/getVehicleList.do`,
  update: `${baseurl}/vehicle/updateVehicle.do`,
  remove: `${baseurl}/vehicle/delete.do`,
  all: `${baseurl}/vehicle/vehicleList.do`,
};

// 房车品牌
export const houseCarBrand = {
  add: `${baseurl}/vehicleBrand/create.do`,
  get: `${baseurl}/vehicleBrand/getVehicleBrandList.do`,
  update: `${baseurl}/vehicleBrand/updateVehicleBrand.do`,
  remove: `${baseurl}/vehicleBrand/delete.do`,
  all: `${baseurl}/vehicleBrand/vehicleBrandList.do`,
};

// 驴友分享
export const tourism = {
  get: `${baseurl}/share/getShareList.do`,
  audit: `${baseurl}/share/updateShareIsShow.do`,
  enabled: `${baseurl}/share/updateShareIsEnabled.do`,
};

// 系统日志
export const sysLog = {
  get: `${baseurl}/syslog/getSysLogList.do`,
};

// 管理员管理
export const admin = {
  add: `${baseurl}/user/addOperator.do`,
  get: `${baseurl}/user/getUserList.do`,
  update: `${baseurl}/user/updateOperator.do`,
  remove: `${baseurl}/user/delOperator.do`,
  updatePassword: `${baseurl}/user/updatePassword.do`
};

// 图片管理
export const imageList = {
  get: `${baseurl}/image/getImageList.do`, // 获取图片列表
  all: `${baseurl}/image/getImageInfoList.do`, // get提交，不传参
  add: `${baseurl}/image/addImageInfo.do`, // 添加，包括图片路径、名称、分类
  update: `${baseurl}/image/updateImage.do`, // 修改
  remove: `${baseurl}/image/delImageInfo.do`, // 删除
  // upload: `${baseurl}/image/uploadImage.do`, //  上传，返回图片路径，之后用add接口添加
  upload: `${baseurl}/image/doUpload.do`, //  上传，返回图片路径，之后用add接口添加
};

// 图片分类
export const imageCate = {
  get: `${baseurl}/imagecate/getImageCateList.do`,
  add: `${baseurl}/imagecate/addImageCate.do`,
  update: `${baseurl}/imagecate/updateImgCate.do`,
};

// 活动分类
export const actionClass = {
  add: `${baseurl}/eventCate/addEventCateInfo.do`,
  get: `${baseurl}/eventCate/getEventCatePageList.do`,
  update: `${baseurl}/eventCate/updateEventCate.do`,
  all: `${baseurl}/eventCate/getEventCateList.do`, // get
};

// 活动列表
export const actionList = {
  add: `${baseurl}/event/addEventInfo.do`,
  get: `${baseurl}/event/getEventList.do`,
  update: `${baseurl}/event/updateEvent.do`,
  detail: `${baseurl}/event/getEventById.do`, // get
};

// 房车租赁
export const houseCarLease = {
  add: `${baseurl}/vehicleRent/create.do`,
  update: `${baseurl}/vehicleRent/update.do`,
  get: `${baseurl}/vehicleRent/getVehicleRentList.do`,
  remove: `${baseurl}/vehicleRent/delete.do`,
  delete: `${baseurl}/vehicleRent/deleteVehicleRent.do`,
};

// 活动商品
export const actionGood = {
  add: `${baseurl}/eventproduct/addEventProduct.do`,
  remove: `${baseurl}/eventproduct/delEventProduct.do`,
  get: `${baseurl}/eventproduct/getEventProductList.do`,
  update: `${baseurl}/eventproduct/updateEventProduct.do`,
};
