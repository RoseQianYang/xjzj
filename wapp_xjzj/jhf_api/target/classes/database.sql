/****************************************************************/
/*                         季候风库表                                                                                  */ 
/****************************************************************/
/*==============================================================*/
/*                       创建数据库和使用 jhf_test                    */                                                      
/*==============================================================*/
CREATE SCHEMA `jhf_test` DEFAULT CHARACTER SET utf8 ;
USE `jhf_test`;
/*==============================================================*/
/* Table: t_product       产品表                                                                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_product`;

CREATE TABLE `t_product` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cate_id`  INT(11) DEFAULT NULL COMMENT '产品分类',
  `brand_id` INT(11) DEFAULT NULL COMMENT '品牌id',
  `price`  INT(11)  DEFAULT NULL COMMENT '售价',
  `image_id` VARCHAR(300)  DEFAULT NULL COMMENT '商品素材图片id',
  `title`   VARCHAR(300)  DEFAULT NULL COMMENT '商品名称',
  `has_color`  ENUM('Y','N')  DEFAULT NULL COMMENT '是否有颜色  Y有颜色  默认为N ',
  `has_size`   ENUM('Y','N')  DEFAULT NULL COMMENT '是否有尺寸 Y有尺寸  默认为N',
  `cover`   VARCHAR(1000)  DEFAULT NULL COMMENT '产品封面',
  `putaway` ENUM('Y','N') DEFAULT null COMMENT '是否商品 上架   Y上架   N下架',
  `description`  VARCHAR(300)  DEFAULT NULL COMMENT '描述简介',
  `content` MEDIUMTEXT DEFAULT NULL COMMENT '商品图文详情',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='产品表';

/*==============================================================*/
/* Table: t_product_attribute       产品属性表                                                                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_product_attribute`;

CREATE TABLE `t_product_attribute` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id`  INT(11) DEFAULT NULL COMMENT '产品id',
  `stock`  INT(11)  DEFAULT NULL COMMENT '产品库存',
  `product_color` VARCHAR(300)  DEFAULT NULL COMMENT '商品颜色',
  `product_size`  VARCHAR(300)  DEFAULT NULL COMMENT '商品尺码',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='产品属性表';

/*==============================================================*/
/* Table: t_product_brand   产品品牌表                                                                                */
/*==============================================================*/

DROP TABLE IF EXISTS `t_product_brand`;

CREATE TABLE `t_product_brand` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(400) DEFAULT NULL COMMENT '产品品牌名称',
  `cover`   VARCHAR(1000)  DEFAULT NULL COMMENT '产品品牌logo图',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='产品品牌表';

/*==============================================================*/
/* Table: t_prod_cate       产品功能分类表                      */
/*==============================================================*/
DROP TABLE IF EXISTS `t_prod_cate`;

CREATE TABLE `t_prod_cate` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(400) DEFAULT NULL COMMENT '产品分类名称',
  `cover`   VARCHAR(1000)  DEFAULT NULL COMMENT '产品分类图片',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='产品功能分类表';


/*==============================================================*/
/* Table: t_img_cate        图片分类                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_img_cate`;

CREATE TABLE `t_img_cate` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(400) DEFAULT NULL COMMENT '图片分类名称',
  `parent_id`   INT(11) NOT NULL DEFAULT 0 COMMENT '图片父分类名称',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='图片分类表';

/*==============================================================*/
/* Table: t_img              产品素材图片表                                                                     */
/*==============================================================*/
DROP TABLE IF EXISTS `t_img`;

CREATE TABLE `t_img` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `img_cate_id`   INT(11) NOT NULL DEFAULT 0 COMMENT '图片分类id，上级用户图片分类id，默认为0 表示1级代理商',
  `title` VARCHAR(400) DEFAULT NULL COMMENT '产品素材图片分类名称',
  `img_src` VARCHAR(1000) DEFAULT NULL COMMENT '产品素材图片路径',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT=' 产品素材图片表';


/*==============================================================*/
/* Table: t_event          促销活动表                           */
/*==============================================================*/
DROP TABLE IF EXISTS `t_event`;

CREATE TABLE `t_event` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `event_cate_id`   INT(11) NOT NULL COMMENT '活动分类id',
  `title` VARCHAR(400) DEFAULT NULL COMMENT ' 促销活动名称',
  `cover` VARCHAR(1000) DEFAULT NULL COMMENT '活动封面',
  `content` MEDIUMTEXT DEFAULT NULL COMMENT '活动模板展示内容 16MB',
  `is_show` ENUM('Y','N') DEFAULT null COMMENT '是否首页显示，枚举型， Y 显示活动时间和名称 N 将过期的活动设为不显示', 
  `start_time`  TIMESTAMP NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time`    TIMESTAMP NULL DEFAULT NULL COMMENT '活动结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='促销活动表';

/*==============================================================*/
/* Table: t_event_cate   活动分类表                             */
/*==============================================================*/
DROP TABLE IF EXISTS `t_event_cate`;

CREATE TABLE `t_event_cate` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(400) DEFAULT NULL COMMENT ' 活动分类名称',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='活动分类表';

/*==============================================================*/
/* Table: t_event_product    活动产品表                         */
/*==============================================================*/
DROP TABLE IF EXISTS `t_event_product`;

CREATE TABLE `t_event_product` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` INT(11)  DEFAULT NULL COMMENT '产品ID',
  `event_id`  INT(11)   DEFAULT NULL COMMENT '活动ID',
  `event_price` INT(11) DEFAULT NULL COMMENT '活动产品价格',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='活动产品表';

/********************************/
/*  用户相关表                  */
/********************************/

/*==============================================================*/
/* Table: t_user    用户表                                                                                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `openid`   VARCHAR(256) DEFAULT NULL COMMENT '微信公众号 openid',
  `parent_id`INT(11) NOT NULL DEFAULT 0 COMMENT '上级用户id，0 表示1级代理商',
  `user_name` VARCHAR(20)  DEFAULT NULL COMMENT '用户名称',
  `avatar` VARCHAR(256) DEFAULT NULL COMMENT '用户头像',
  `mobile` VARCHAR(40)  DEFAULT NULL COMMENT '用户电话作为登录账户',
  `user_integral_sum` INT(11)  DEFAULT NULL COMMENT '用户总积分数',
  `is_enabled` VARCHAR(1) DEFAULT NULL COMMENT '是否可用 Y 可用 N 被挂起',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*==============================================================*/
/* Table: t_user_address    用户地址表                          */
/*==============================================================*/
DROP TABLE IF EXISTS `t_user_address`;

CREATE TABLE `t_user_address` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11)  DEFAULT NULL COMMENT '用户id',
  `address` VARCHAR(100) DEFAULT NULL COMMENT '地址',
  `address_detail` VARCHAR(100) DEFAULT NULL COMMENT '详细地址',
  `is_default` ENUM('Y','N') DEFAULT 'Y' COMMENT '默认地址',
  `consignee` VARCHAR(40) DEFAULT NULL COMMENT '收货人',
  `mobile`  VARCHAR(100) DEFAULT NULL COMMENT '联系电话',
  `postcode` VARCHAR(50)  DEFAULT NULL COMMENT '邮编',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户地址表';

/*==============================================================*/
/* Table: t_shopping_cart     购物车表                          */
/*==============================================================*/
DROP TABLE IF EXISTS `t_shopping_cart`;

CREATE TABLE `t_shopping_cart` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11)  DEFAULT NULL COMMENT '用户id',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='购物车表';

/*==============================================================*/
/* Table: t_shopping_cart_detail          购物车详情表          */
/*==============================================================*/
DROP TABLE IF EXISTS `t_shopping_cart_detail`;

CREATE TABLE `t_shopping_cart_detail` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` INT(11)  DEFAULT NULL COMMENT '商品ID',
  `shopping_cart_id` INT(11)  DEFAULT NULL COMMENT '购物车ID',
  `product_num` INT(11)  DEFAULT NULL COMMENT '商品数量',
  `product_price` INT(11)  DEFAULT NULL COMMENT '商品加入时的价格，是否活动价',
  `product_color` VARCHAR(100) DEFAULT NULL COMMENT '商品颜色',
  `product_size` VARCHAR(100) DEFAULT NULL COMMENT '商品尺码',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='购物车详情表';

/*==============================================================*/
/* Table: t_orders           订单表                             */
/*==============================================================*/
DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11)  DEFAULT NULL COMMENT '用户id',
  `user_address_id` INT(11)  DEFAULT NULL COMMENT '用户地址id',
  `order_total_price` INT(11)  DEFAULT NULL COMMENT '订单总价',
  `order_name` VARCHAR(400) DEFAULT NULL COMMENT '订单名称',
  `order_no` VARCHAR(400) DEFAULT NULL COMMENT '订单编号',
  `order_status`  VARCHAR(10) DEFAULT NULL  COMMENT '订单状态',
  `logistics_id`  VARCHAR(400) DEFAULT NULL COMMENT '物流公司',
  `logistics_no`  VARCHAR(400) DEFAULT NULL COMMENT '物流编号',
  `logistics_name` VARCHAR(400) DEFAULT NULL COMMENT '物流公司名称',
  `address` VARCHAR(100) DEFAULT NULL COMMENT '地址与详细地址拼接存放',
  `consignee` VARCHAR(40) DEFAULT NULL COMMENT '收货人',
  `mobile`  VARCHAR(100) DEFAULT NULL COMMENT '联系电话',
  `remark` VARCHAR(400) DEFAULT NULL COMMENT '买家留言',
  `payment_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '完成订单的时间',
  `cancel_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '订单取消时间',
  `payde_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '订单支付时间',
  `sipping_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '订单发货时间',
  `signed_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '订单用户签收时间',
  `exceed_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '订单超时签收时间', 
  `exceed_signed_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '超时签收时间', 
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='订单表   ';

/*==============================================================*/
/* Table: t_order_detail  订单详情表                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_order_detail`;

CREATE TABLE `t_order_detail` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11)  DEFAULT NULL COMMENT '用户id',
  `order_id` INT(11)  DEFAULT NULL COMMENT '订单id',
  `product_id` INT(11)  DEFAULT NULL COMMENT '产品ID',
  `product_cate_id` INT(11)  DEFAULT NULL COMMENT '商品分类id',
  `product_brand_id` INT(11)  DEFAULT NULL COMMENT '商品品牌id',
  `product_count` INT(11)  DEFAULT NULL COMMENT '产品数量',
  `product_price` INT(11)  DEFAULT NULL COMMENT '本次购买的价格',
  `order_status`  VARCHAR(10) DEFAULT NULL  COMMENT '订单状态',
  `product_color` VARCHAR(400) DEFAULT NULL COMMENT '产品颜色',
  `product_size` VARCHAR(400) DEFAULT NULL COMMENT '产品尺码',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='订单详情表';

/****   最新新增的表 ***/

/*==============================================================*/
/* Table: t_after_outlets       售后网点表                    */
/*==============================================================*/
DROP TABLE IF EXISTS `t_after_outlets`;

CREATE TABLE `t_after_outlets` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title`  VARCHAR(400)  DEFAULT NULL COMMENT '网点名称',
  `address`  VARCHAR(400)   DEFAULT NULL COMMENT '网点地址',
  `latitude`  DECIMAL(10,7)    DEFAULT NULL COMMENT '地理位置纬度',
  `longitude` DECIMAL(10,7)   DEFAULT NULL COMMENT '地址位置经度',
  `distance` float  DEFAULT NULL COMMENT '距离',
  `cover` VARCHAR(1000)  DEFAULT NULL COMMENT '网点封面',
  `img_id` VARCHAR(400)  DEFAULT NULL COMMENT '网点图片',
  `phone` VARCHAR(400)  DEFAULT NULL COMMENT '联系电话',
  `content`MEDIUMTEXT DEFAULT NULL COMMENT '图文详情',
  `is_delete` ENUM('Y','N') DEFAULT 'Y' COMMENT '有效状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='售后网点表';


/*==============================================================*/
/* Table: t_physical_store        实体店表                      */
/*==============================================================*/
DROP TABLE IF EXISTS `t_physical_store`;

CREATE TABLE `t_physical_store` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title`  VARCHAR(400)  DEFAULT NULL COMMENT '实体店名称',
  `address`  VARCHAR(400)   DEFAULT NULL COMMENT '实体店地址',
  `latitude`  DECIMAL(10,7)    DEFAULT NULL COMMENT '地理位置纬度',
  `longitude` DECIMAL(10,7)   DEFAULT NULL COMMENT '地址位置经度',
  `distance` float  DEFAULT NULL COMMENT '距离',
  `cover` VARCHAR(1000)  DEFAULT NULL COMMENT '实体店封面',
  `img_id` VARCHAR(400)  DEFAULT NULL COMMENT '实体店图片',
  `phone` VARCHAR(400)  DEFAULT NULL COMMENT '联系电话',
  `content`MEDIUMTEXT DEFAULT NULL COMMENT '图文详情',
  `is_delete` ENUM('Y','N') DEFAULT 'Y' COMMENT '有效状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='实体店表';


/*==============================================================*/
/* Table: "t_campsite"             营地表                       */
/*==============================================================*/
DROP TABLE IF EXISTS `t_campsite`;

CREATE TABLE `t_campsite` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` INT(11)  DEFAULT NULL COMMENT '营地分类（自由营地：1，合作营地：2）',
  `title`  VARCHAR(400)  DEFAULT NULL COMMENT '营地名称',
  `address`  VARCHAR(400)   DEFAULT NULL COMMENT '营地地址',
  `latitude`  DECIMAL(10,7)    DEFAULT NULL COMMENT '地理位置纬度',
  `longitude` DECIMAL(10,7)   DEFAULT NULL COMMENT '地址位置经度',
  `distance` float  DEFAULT NULL COMMENT '距离',
  `cover` VARCHAR(1000)  DEFAULT NULL COMMENT '营地封面',
  `img_id` VARCHAR(400)  DEFAULT NULL COMMENT '实体店图片',
  `phone` VARCHAR(400)  DEFAULT NULL COMMENT '联系电话',
  `content`MEDIUMTEXT DEFAULT NULL COMMENT '图文详情',
  `introduction` VARCHAR(400)  DEFAULT NULL COMMENT '营地简介',
  `campsite_function`  VARCHAR(400) DEFAULT NULL COMMENT '营地基础功能，以逗号区分',
  `is_delete` ENUM('Y','N') DEFAULT 'Y' COMMENT '有效状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='营地表';

/*==============================================================*/
/* Table: t_vehicle_rent                 房车租赁表             */
/*==============================================================*/
DROP TABLE IF EXISTS `t_vehicle_rent`;

CREATE TABLE `t_vehicle_rent` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `vehicle_id` INT(11) DEFAULT NULL COMMENT '房车id',
  `rent_price` INT(11) DEFAULT NULL COMMENT '预定价格,一天多钱',
  `address` VARCHAR(400) DEFAULT NULL COMMENT '联系地址',
  `phone` VARCHAR(400) DEFAULT NULL COMMENT '联系电话',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='房车租赁表';


/*==============================================================*/
/* Table: t_mobile_route                 露营路线表             */
/*==============================================================*/
DROP TABLE IF EXISTS `t_mobile_route`;

CREATE TABLE `t_mobile_route` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title`  VARCHAR(400)  DEFAULT NULL COMMENT '路线名称',
  `address`  VARCHAR(400)   DEFAULT NULL COMMENT '路线地址',
  `cover` VARCHAR(1000)  DEFAULT NULL COMMENT '路线封面',
  `img_id` VARCHAR(400)  DEFAULT NULL COMMENT '路线图片',
  `phone` VARCHAR(400)  DEFAULT NULL COMMENT '联系电话',
  `content`MEDIUMTEXT DEFAULT NULL COMMENT '图文详情',
  `introduction` VARCHAR(400)  DEFAULT NULL COMMENT '露营路线简介',
  `is_delete` ENUM('Y','N') DEFAULT 'Y' COMMENT '有效状态',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='露营路线表';

/*==============================================================*/
/* Table: t_share          分享表                               */
/*==============================================================*/
DROP TABLE IF EXISTS `t_share`;

CREATE TABLE `t_share` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '分享的用户ID',
  `category_id` INT(11) DEFAULT NULL COMMENT '分享分类（结伴出游：1，营地分享：2）',
  `content_text`   VARCHAR(1000)  DEFAULT NULL COMMENT '内容文字',
  `is_show` ENUM('Y','N') DEFAULT null COMMENT '是否首页显示，枚举型， Y 微信端首页显示  N微信端首页不显示',
  `is_enabled` ENUM('Y','N') DEFAULT null COMMENT 'Y 对经常违规用户挂起  N 不挂起',
  `content_img_id`  VARCHAR(400)   DEFAULT NULL COMMENT '内容图片',
  `publish_address` VARCHAR(1000)  DEFAULT NULL COMMENT '发布地址',
  `publish_time` TIMESTAMP NULL DEFAULT NULL COMMENT '发布时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='分享表';


/*==============================================================*/
/* Table: t_share_comment            分享的评价表                                                   */
/*==============================================================*/
DROP TABLE IF EXISTS `t_share_comment`;

CREATE TABLE `t_share_comment` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '评价的用户ID',
  `share_id` INT(11) DEFAULT NULL COMMENT '分享的用户ID',
  `parent_comment` INT(11) DEFAULT NULL COMMENT '评论的上一级评论,相当于本条是回复评论',
  `user_name`  VARCHAR(400)   DEFAULT NULL COMMENT '评价的用户名字',
  `comment_content`   VARCHAR(1000)  DEFAULT NULL COMMENT '评价内容',
  `comment_time` TIMESTAMP NULL DEFAULT NULL COMMENT '评价时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='分享的评价表';

/*==============================================================*/
/* Table: t_integral            积分表                                                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_integral`;

CREATE TABLE `t_integral` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `integral_num` INT(11)   DEFAULT NULL COMMENT '积分数',
  `introduction` VARCHAR(400)  DEFAULT NULL COMMENT '积分说明',
  `integral_type` ENUM('Y','N') DEFAULT null COMMENT '积分类型  Y增长财富  N已提财富',
  `integral_source` VARCHAR(400)  DEFAULT NULL COMMENT '积分来源   存订单编号',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='积分表';

/*==============================================================*/
/* Table: t_integral_conversion            积分兑换申请表                               */
/*==============================================================*/
DROP TABLE IF EXISTS `t_integral_conversion`;

CREATE TABLE `t_integral_conversion` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `conversion_num`  INT(11)   DEFAULT NULL COMMENT '兑换申请积分数',
  `user_mobile`  VARCHAR(100)   DEFAULT NULL COMMENT '申请用户的联系方式',
  `reality_conversion_num`  INT(11)   DEFAULT NULL COMMENT '实际兑换积分数',
  `introduction` VARCHAR(400)  DEFAULT NULL COMMENT '兑换申请积分说明',
  `conversion_status` ENUM('Y','N') DEFAULT null COMMENT '兑换申请审核状态，枚举值，Y 已审核 N 待审核',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '兑换申请时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='积分兑换申请表';

/*==============================================================*/
/* Table: t_friend            三级分销好友表                                                                  */
/*==============================================================*/
DROP TABLE IF EXISTS `t_friend`;

CREATE TABLE `t_friend` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户ID',
  `friend_id`  INT(11) DEFAULT NULL COMMENT '好友id',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='三级分销好友表';
/* 后台管理相关 */



/*==============================================================*/
/* Table: t_vehicle   房车表                                    */
/*==============================================================*/
DROP TABLE IF EXISTS `t_vehicle`;

CREATE TABLE `t_vehicle` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `vehicle_brand_id` INT(11) DEFAULT NULL COMMENT '房车品牌id',
  `vehicle_name`    VARCHAR(300)  DEFAULT NULL COMMENT '房车名称',
  `cover` VARCHAR(1000) DEFAULT NULL COMMENT '房车封面图片',
  `image_id` VARCHAR(300)  DEFAULT NULL COMMENT '房车素材图片id',
  `introduction` VARCHAR(1000) DEFAULT NULL COMMENT '房车简介',
  `content` MEDIUMTEXT COMMENT '商品图文详情',
  `price`  INT DEFAULT NULL COMMENT '预定价格',
  `spec` VARCHAR(1000) DEFAULT NULL COMMENT '房车规格型号',
  `gears` VARCHAR(500) DEFAULT NULL COMMENT '档位',
  `is_sencond` VARCHAR(20) DEFAULT NULL COMMENT '是否是2手，Y N ',
  `is_sales`  VARCHAR(20)  DEFAULT NULL COMMENT '是否是促销，Y N',
  `function_type` INT DEFAULT NULL COMMENT '自行式1  拖挂式2   皮卡3',
  `license` VARCHAR(500) DEFAULT NULL COMMENT '驾照级别',
  `bed_num` INT DEFAULT NULL COMMENT '床位',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='房车表';

/*==============================================================*/
/* Table: t_vehicle_brand  房车品牌表                           */
/*==============================================================*/
DROP TABLE IF EXISTS `t_vehicle_brand`;

CREATE TABLE `t_vehicle_brand` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `brand_name`    VARCHAR(300)  DEFAULT NULL COMMENT '房车名称',
  `cover` VARCHAR(1000) DEFAULT NULL COMMENT '房车品牌logo图',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='房车品牌表';


/*==============================================================*/
/* Table: t_sys_log                 系统日志表         */
/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_log`;

CREATE TABLE `t_sys_log` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `log_type` VARCHAR(400) DEFAULT NULL COMMENT '日志类型',
  `log_action` VARCHAR(400) DEFAULT NULL COMMENT '日志操作类型',
  `log_content` VARCHAR(400) DEFAULT NULL COMMENT '日志内容',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统日志表 ';

/*==============================================================*/
/* Table: t_sys_config                 系统配置表         */
/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_config`;

CREATE TABLE `t_sys_config` (
  `config_key` VARCHAR(400) NOT NULL  COMMENT '配置项key，唯一',
  `config_value` VARCHAR(400) DEFAULT NULL COMMENT '配置内容',
  PRIMARY KEY (`config_key`)
) ENGINE=INNODB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='系统配置表 ';


/*==============================================================*/
/* Table: t_menus  菜单表                                       */
/*==============================================================*/
DROP TABLE IF EXISTS `t_menus`;

CREATE TABLE `t_menus` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` VARCHAR(100) DEFAULT NULL COMMENT '菜单编号',
  `name` VARCHAR(200) DEFAULT NULL COMMENT '菜单名称',
  `fid` INT(11) DEFAULT NULL COMMENT '上级id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `t_menus` */

INSERT  INTO `t_menus`(`id`,`code`,`name`,`fid`) VALUES (1,'banner',NULL,0),(2,'money',NULL,0),(3,'user',NULL,0),(4,'sys',NULL,0),(5,'bannerAdd',NULL,1),(6,'consumptionCount',NULL,2),(7,'integral',NULL,2),(8,'withdrawals',NULL,2),(9,'userList',NULL,3),(10,'admin',NULL,4),(11,'role',NULL,4),(12,'sysLog',NULL,4);

/*==============================================================*/
/* Table: t_operator  后台用户表                                */
/*==============================================================*/
DROP TABLE IF EXISTS `t_operator`;

CREATE TABLE `t_operator` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_name` VARCHAR(100) DEFAULT NULL COMMENT '用户登录账号',
  `real_name` VARCHAR(100) DEFAULT NULL COMMENT '用户真实姓名',
  `position` VARCHAR(100) DEFAULT NULL COMMENT '职位',
  `department` VARCHAR(100) DEFAULT NULL COMMENT '部门',
  `mobile` VARCHAR(100) DEFAULT NULL COMMENT '手机号',
  `password` VARCHAR(100) DEFAULT NULL COMMENT '密码',
  `role_id` INT(11) DEFAULT NULL COMMENT '角色',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  `remark` TEXT COMMENT '备注',
  `is_enabled` ENUM('Y','N') DEFAULT null COMMENT 'Y 对后台用户挂起  N 不挂起',
  `is_delete` ENUM('Y','N') DEFAULT 'Y' COMMENT '启用状态',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `t_operator` */

INSERT  INTO `t_operator`(`id`,`account_name`,`real_name`,`position`,`department`,`mobile`,`password`,`role_id`,`create_time`,`update_time`,`remark`,`is_delete`) VALUES (1,'admin','admin','111','111','1111','111111',1,'2018-02-03 16:35:35',NULL,NULL,'Y');

/*==============================================================*/
/* Table: t_permissions  权限表                                */
/*==============================================================*/
DROP TABLE IF EXISTS `t_permissions`;

CREATE TABLE `t_permissions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` VARCHAR(100) DEFAULT NULL COMMENT '权限名称',
  `url` VARCHAR(200) DEFAULT NULL COMMENT '接口地址',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `t_permissions` */

INSERT  INTO `t_permissions`(`id`,`name`,`url`) VALUES (1,'获取用户列表','/user/getUserList'),(2,'修改用户信息','/user/updateUserInfo'),(3,'获取权限列表','/user/getRoleList');

/*==============================================================*/
/* Table: t_role  角色表                                        */
/*==============================================================*/

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` VARCHAR(50) DEFAULT NULL COMMENT '角色名称',
  `permissions` VARCHAR(8000) DEFAULT NULL COMMENT '访问权限',
  `ranges` VARCHAR(20) DEFAULT NULL COMMENT '访问范围',
  `menus` VARCHAR(4000) DEFAULT NULL COMMENT '菜单权限',
  `remark` VARCHAR(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `t_role` */

INSERT  INTO `t_role`(`id`,`role_name`,`permissions`,`ranges`,`menus`,`remark`) VALUES (1,'admin',NULL,NULL,'[{code:\'banner\',subMenu:[{code:\'bannerAdd\'},],},{code:\'money\',subMenu:[{code:\'consumptionCount\'},{code:\'integral\'},{code:\'withdrawals\'},],},{code:\'user\',subMenu:[{code:\'userList\'},],},{code:\'sys\',subMenu:[{code:\'admin\'},{code:\'role\'},{code:\'sysLog\'},],}]',NULL);
