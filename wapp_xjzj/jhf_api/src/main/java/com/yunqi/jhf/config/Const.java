package com.yunqi.jhf.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



/**
 * 数据字典定义
 * 
 * @author Administrator
 */
public class Const {

	public final static int IS_TRUE = 1;
	public final static int IS_FALSE = 0;
	public final static String TRUEORFALSE = "trueorfalse"; //排序状态说明 
	public final static String LATITUDE = "latitude"; 
	public final static String LONGITUDE = "longitude"; 

	public final static String UPLOAD_BASE_DIR = "/home/wapp/upload";

	/*public final static String WECHAT_APP_ID = "wx4fb49455265a775f"; //季候风微信公众号AppId
	public final static String WECHAT_APP_SECRET = "e75880e56ec6d234ecced549a5848bfd"; //季候风微信公众号AppSecret
	public final static String WECHAT_SESSION_USER_KEY = "WECHAT_SESSION_USER_KEY"; //微信用户数据进行加密签名的密钥
	public final static String WECHAT_PAYMENT_SIGN_KEY = "jhf2018jhfjhf2018jhfjhf2018jhfOK"; // 微信支付签名的密钥
	public final static String WECHAT_MCH_ID = "1295078201"; //微信支付商户号
*/	
	
	public final static String WECHAT_APP_ID = "wx75049fdeb3e18fcf"; //小金之家微信公众号AppId
	public final static String WECHAT_APP_SECRET = "c011b9811fd6ad1e7956308f558af895"; //小金之家微信公众号AppSecret
	public final static String WECHAT_SESSION_USER_KEY = "WECHAT_SESSION_USER_KEY"; //微信用户数据进行加密签名的密钥
	public final static String WECHAT_PAYMENT_SIGN_KEY = "23CedarGroveKeiravilleNSW2500aus"; // 微信支付签名的密钥
	public final static String WECHAT_MCH_ID = "1508165161"; //微信支付商户号
	

	
	// 前台显示数据数
	public final static int TEST_FONT_PAGE_SIZE = 10;
	public final static int FONT_PAGE_SIZE = 10;
	public final static int ADM_PAGE_SIZE = 30;
    public final static String FONT_PAGE_CURRENTPAGE = "page"; //前台分页页码数
	public final static String ADM_PAGE_CURRENTSIZE = "size"; //前台分页参数条数

	public final static String SESSION_ADMIN_CAPTCHA_KEY = "session.admin.captcha.key";
	public final static String SESSION_PORTAL_CAPTCHA_KEY = "session.portal.captcha.key";

	// 积分类型  Y可提（增长）财富  N已提财富
	public final static String INTEGRAL_TYPE_INCREASE = "Y";
	public final static String INTEGRAL_TYPE_DECREASE = "N";
	// 兑换申请积分数
	public final static int Integral_Conversion_NUM = 1000;

	/**
	 * 1）用户创建订单，处于初始状态（1：待支付状态） 
     * 2）用户在未支付之前可以取消订单 （2：改为支付已取消） 
     * 3）用户支付成功，订单为已支付（3：已支付，等待发货状态）
     * 4）管理员线下联系物流，通过后台管理填写物流公司以及订单编号，并确认发货。（4：已发货，待收货）
     * 5）用户线下收货，确认后可以点击收货按钮，该订单状态改为 （5：已收货）
     * 6）如果用户不点击收货， 在订单发货时间15天以后，该订单自动改为  （6：超时，已收货）
	 */
	public final static  int DAYS = 15;  //订单发货时间15天以后改为超时，已收货
	public final static String ORDER_STATUS_WAITING_PAY = "1"; // 未支付
	public final static String ORDER_STATUS_CANCELED = "2"; // 支付取消
	public final static String ORDER_STATUS_ALREADY_PAYED = "3"; // 已支付，等待发货状态
	public final static String ORDER_STATUS_ALREADY_SIPPING = "4"; // 已发货，待收货
	public final static String ORDER_STATUS_ALREADY_SIGNED = "5"; // 客户确认已收货
	public final static String ORDER_STATUS_EXCEED_SIGN = "6"; // 超时，已收货
	public final static Map<String, String> orderStatusMap = new HashMap<String, String>();
	static {
		orderStatusMap.put(ORDER_STATUS_WAITING_PAY, "未支付");
		orderStatusMap.put(ORDER_STATUS_CANCELED, "支付取消");
		orderStatusMap.put(ORDER_STATUS_ALREADY_PAYED, "已支付，等待发货状态");
		orderStatusMap.put(ORDER_STATUS_ALREADY_SIPPING, "已发货，待收货");
		orderStatusMap.put(ORDER_STATUS_ALREADY_SIGNED, "客户确认已收货");
		orderStatusMap.put(ORDER_STATUS_EXCEED_SIGN, "超时，已收货");

	}

	// 营地分类( 1 自由营地 2 合作营地 )
	public final static int FREE_CAMPSITE = 1;
	public final static int COOPERATION_CAMPSITE = 2;

	// 房车品鉴中 定义是否为二手
    public final static String ISSENCOND_YES = "Y"; //定义房车品鉴 二手置换
	public final static String ISSALES_YES = "Y"; //定义房车品鉴  当季促销
	private static final Integer Y = null;
	private static final Integer N = null;
	public final static Integer VEHICLE_IS_SENCOND = Y;
	public final static Integer VEHICLE_NO_SENCOND = N;
	public final static Map<Integer, String> VehicleIsSencondMap = new TreeMap<Integer, String>();

	static {
		VehicleIsSencondMap.put(VEHICLE_IS_SENCOND, "二手");
		VehicleIsSencondMap.put(VEHICLE_NO_SENCOND, "非二手");
	}

	// 房车品鉴中 定义是否为促销
	public final static Integer VEHICLE_IS_SALES = Y;
	public final static Integer VEHICLE_NO_SALES = N;
	public final static Map<Integer, String> VehicleIsSalesMap = new TreeMap<Integer, String>();

	static {
		VehicleIsSalesMap.put(VEHICLE_IS_SALES, "促销");
		VehicleIsSalesMap.put(VEHICLE_NO_SALES, "非促销");
	}

	// 房车品鉴中 定义：自行式 1 拖挂式 2 皮卡 3
	public final static int VEHICLE_FUNCTION_TYPE_SELFPROPELLED = 1;
	public final static int VEHICLE_FUNCTION_TYPE_PULL = 2;
	public final static int VEHICLE_FUNCTION_TYPE_PCUT = 3;
	public final static Map<Integer, String> vehicleFunctionTypeMap = new HashMap<Integer, String>();

	static {
		vehicleFunctionTypeMap.put(VEHICLE_FUNCTION_TYPE_SELFPROPELLED, "自行式");
		vehicleFunctionTypeMap.put(VEHICLE_FUNCTION_TYPE_PULL, "拖挂式");
		vehicleFunctionTypeMap.put(VEHICLE_FUNCTION_TYPE_PCUT, "皮卡");
	}

	///////////////////////////// 上面为当前项目定义的数据字典,下面为参考/////////////////////////////////////

	public final static float current_client_version = 0.8f;

	public final static Map<String, String> operatorMap = new HashMap<String, String>();

	// public final static int IS_TRUE = 1;
	// public final static int IS_FALSE = 0;

	public final static int ARTICLE_CATEGORY_AIO = -1;
	public final static Map<Integer, String> articleAIOMap = new TreeMap<Integer, String>();

	static {
		articleAIOMap.put(ARTICLE_CATEGORY_AIO, "一体机介绍");
	}

	public final static int PUBLISH_STATUS_SUBMITED = 0;
	public final static int PUBLISH_STATUS_UNAPPROVED = 1;
	public final static int PUBLISH_STATUS_APPROVED = 2;
	public final static Map<Integer, String> publishStatusMap = new HashMap<Integer, String>();

	static {
		publishStatusMap.put(PUBLISH_STATUS_SUBMITED, "待审核");
		publishStatusMap.put(PUBLISH_STATUS_APPROVED, "审核通过");
		publishStatusMap.put(PUBLISH_STATUS_UNAPPROVED, "审核未通过");
	}

	public final static int TOPIC_STATUS_UNREPLIED = 0;
	public final static int TOPIC_STATUS_REPLIED = 1;
	public final static Map<Integer, String> topicStatusMap = new HashMap<Integer, String>();

	static {
		topicStatusMap.put(TOPIC_STATUS_UNREPLIED, "未回复");
		topicStatusMap.put(TOPIC_STATUS_REPLIED, "已回复");
	}

	public final static int USER_TYPE_USER = 1;
	public final static int USER_TYPE_OPERATOR = 2;
	public final static Map<Integer, String> userTypeMap = new TreeMap<Integer, String>();

	static {
		userTypeMap.put(USER_TYPE_USER, "用户");
		userTypeMap.put(USER_TYPE_OPERATOR, "操作员");
	}

	public final static int JOB_STATUS_SEARCH = 1;
	public final static int JOB_STATUS_UNSEARCH = 2;

	public final static Map<Integer, String> jobStatusMap = new TreeMap<Integer, String>();

	static {
		jobStatusMap.put(JOB_STATUS_SEARCH, "求职中");
		jobStatusMap.put(JOB_STATUS_UNSEARCH, "取消求职");
	}

	public final static int POSITION_TYPE_JOBWORK = 1;
	public final static int POSITION_TYPE_HOMEWORK = 2;
	public final static int POSITION_TYPE_PARTTIME = 3;
	public final static Map<Integer, String> positionTypeMap = new TreeMap<Integer, String>();

	static {
		positionTypeMap.put(POSITION_TYPE_JOBWORK, "散工");
		positionTypeMap.put(POSITION_TYPE_HOMEWORK, "家政服务");
		positionTypeMap.put(POSITION_TYPE_PARTTIME, "大学生兼职");
	}

	public final static Map<Integer, Integer> rankMap = new TreeMap<Integer, Integer>();

	static {
		rankMap.put(10, 1);
		rankMap.put(20, 2);
		rankMap.put(30, 3);
		rankMap.put(40, 4);
		rankMap.put(50, 5);
	}

	public final static Map<Integer, String> rankUPMap = new TreeMap<Integer, String>();

	static {
		rankUPMap.put(0, "无评论");
		rankUPMap.put(10, "一星");
		rankUPMap.put(20, "二星");
		rankUPMap.put(30, "三星");
		rankUPMap.put(40, "四星");
		rankUPMap.put(50, "五星");
	}

	public final static String SUPER_ADMIN_ID = "root";
	public final static int ROLE_SUPER_ADMIN = 0;
	public final static int ROLE_NORMAL_ADMIN = 1;
	public final static int ROLE_AUDIT_ADMIN = 2;
	public final static int ROLE_OPERATE_ADMIN = 3;
	public final static int ROLE_OPERATE_AIO = 4;
	public final static int ROLE_PAYMENT_ADMIN = 5;
	public final static int ROLE_WECHAT_ADMIN = 6;

	/*
	 * public final static Map<Integer, String> operatorRoleMap = new
	 * TreeMap<Integer, String>();
	 * 
	 * static { operatorRoleMap.put(ROLE_SUPER_ADMIN, "超级管理员");
	 * operatorRoleMap.put(ROLE_NORMAL_ADMIN, "内容维护员(编辑)");
	 * operatorRoleMap.put(ROLE_AUDIT_ADMIN, "部门管理员(审核)");
	 * operatorRoleMap.put(ROLE_OPERATE_ADMIN, "运营管理员");
	 * operatorRoleMap.put(ROLE_OPERATE_AIO, "一体机管理员");
	 * operatorRoleMap.put(ROLE_PAYMENT_ADMIN, "缴费管理员");
	 * operatorRoleMap.put(ROLE_WECHAT_ADMIN, "微信管理员"); }
	 */

	public final static int SUSPEND_STATUS_TRUE = 1;
	public final static int SUSPEND_STATUS_FALSE = 0;
	public final static Map<Integer, String> suspendStatusMap = new TreeMap<Integer, String>();

	static {
		suspendStatusMap.put(SUSPEND_STATUS_TRUE, "挂起");
		suspendStatusMap.put(SUSPEND_STATUS_FALSE, "启用");
	}

	public final static int GENDER_MAN = 1;
	public final static int GENDER_WOMAN = 0;
	public final static int GENDER_UNKNOWN = -1;
	public final static Map<Integer, String> genderMap = new TreeMap<Integer, String>();

	static {
		genderMap.put(GENDER_MAN, "男");
		genderMap.put(GENDER_WOMAN, "女");
		genderMap.put(GENDER_UNKNOWN, "未知");
	}

	public final static Map<Integer, String> agePeriodMap = new TreeMap<Integer, String>();

	static {
		agePeriodMap.put(1, "16-20岁");
		agePeriodMap.put(2, "21-25岁");
		agePeriodMap.put(3, "26-30岁");
		agePeriodMap.put(4, "31-35岁");
		agePeriodMap.put(5, "36-40岁");
		agePeriodMap.put(6, "40岁以上");
	}

	public final static int VERIFY_STATUS_SUBMITED = 1;
	public final static int VERIFY_STATUS_SENDSMS = 2;
	public final static int VERIFY_STATUS_APPROVED = 3;
	public final static Map<Integer, String> verifyStatusMap = new TreeMap<Integer, String>();

	static {
		verifyStatusMap.put(0, "未绑定");
		verifyStatusMap.put(1, "未绑定");
		verifyStatusMap.put(2, "已发送验证码");
		verifyStatusMap.put(3, "已绑定");
	}

	public final static int USER_TYPE_SYSTEM = 1;
	public final static int USER_TYPE_OTHERS = 2;

	// 热门关键字分类
	public final static int KEYWORD_TYPE_ARTICLE = 1;
	public final static int KEYWORD_TYPE_KNOWLEDGE = 2;
	public final static int KEYWORD_TYPE_GUID = 3;
	public final static int KEYWORD_TYPE_ORG = 4;
	public final static int KEYWORD_TYPE_POST = 5;
	public final static int KEYWORD_TYPE_JOBWORK = 6;
	public final static int KEYWORD_TYPE_HOMEWORK = 7;
	public final static int KEYWORD_TYPE_PARTTIME = 8;

	// 搜索历史类型
	public final static int SEARCH_TYPE_JOBWORK = 1;
	public final static int SEARCH_TYPE_HOMEWORK = 2;
	public final static int SEARCH_TYPE_PARTTIME = 3;
	public final static int SEARCH_TYPE_POST = 4;
	public final static Map<Integer, String> searchTypeMap = new TreeMap<Integer, String>();

	static {
		searchTypeMap.put(SEARCH_TYPE_JOBWORK, "散工搜索记录");
		searchTypeMap.put(SEARCH_TYPE_HOMEWORK, "家政搜索记录");
		searchTypeMap.put(SEARCH_TYPE_PARTTIME, "兼职搜索记录");
		searchTypeMap.put(SEARCH_TYPE_POST, "职位搜索记录");
	}

	// 统计类型
	public final static String stat_type_news_create = "news_create";
	public final static String stat_type_news_audit = "news_audit";
	public final static String stat_type_12333_create = "12333_create";
	public final static String stat_type_12333_reply = "12333_reply";
	public final static String stat_type_12333_audit = "12333_audit";
	public final static String stat_type_guide_create = "guide_create";
	public final static String stat_type_guide_audit = "guide_audit";
	public final static String stat_type_org_create = "org_create";
	public final static String stat_type_org_audit = "org_audit";
	public final static Map<String, String> statTypeMap = new LinkedHashMap<String, String>();

	static {
		statTypeMap.put(stat_type_news_create, "新闻添加");
		statTypeMap.put(stat_type_news_audit, "新闻审核");
		statTypeMap.put(stat_type_12333_create, "12333添加");
		statTypeMap.put(stat_type_12333_reply, "12333回复");
		statTypeMap.put(stat_type_12333_audit, "12333审核");
		statTypeMap.put(stat_type_guide_create, "指南添加");
		statTypeMap.put(stat_type_guide_audit, "指南审核");
		statTypeMap.put(stat_type_org_create, "机构添加");
		statTypeMap.put(stat_type_org_audit, "机构审核");
	}

	// 统计分组
	public final static int STAT_GROUP_TYPE_OP = 0;
	public final static int STAT_GROUP_TYPE_DEPT = 1;
	public final static int STAT_GROUP_TYPE_ALL = 2;
	public final static Map<Integer, String> statGroupTypeMap = new TreeMap<Integer, String>();

	static {
		statGroupTypeMap.put(STAT_GROUP_TYPE_OP, "按个人分组");
		statGroupTypeMap.put(STAT_GROUP_TYPE_DEPT, "按部门分组");
		statGroupTypeMap.put(STAT_GROUP_TYPE_ALL, "统计全部");
	}

	// 人才网和就业网
	public final static int POST_SOURCE_JIUYE = 1;
	public final static int POST_SOURCE_RENCAI = 2;
	public final static int POST_SOURCE_QIUZHI = 3;
	public final static Map<Integer, String> postSourceMap = new TreeMap<Integer, String>();

	static {
		postSourceMap.put(POST_SOURCE_JIUYE, "就业网");
		postSourceMap.put(POST_SOURCE_RENCAI, "人才网");
		postSourceMap.put(POST_SOURCE_QIUZHI, "求职网");
	}

	// 平台账号类型
	public final static String PLATFORM_ALIPAY = "alipay";
	public final static String PLATFORM_WEIXIN = "weixin";

	// 平台绑定状态
	public final static int PLATFORM_BNIND_STATUS_UNBIND = 0; // 未绑定
	public final static int PLATFORM_BNIND_STATUS_BINDED = 1; // 已绑定
	public final static int PLATFORM_BNIND_STATUS_LOSE = 2; // 绑定已失效

	// 个人账户养老金计发月数
	public final static Map<Integer, Integer> planMonthsMap = new HashMap<Integer, Integer>();

	static {
		planMonthsMap.put(40, 233);
		planMonthsMap.put(41, 230);
		planMonthsMap.put(42, 226);
		planMonthsMap.put(43, 223);
		planMonthsMap.put(44, 220);
		planMonthsMap.put(45, 216);
		planMonthsMap.put(46, 212);
		planMonthsMap.put(47, 208);
		planMonthsMap.put(48, 204);
		planMonthsMap.put(49, 199);
		planMonthsMap.put(50, 195);
		planMonthsMap.put(51, 190);
		planMonthsMap.put(52, 185);
		planMonthsMap.put(53, 180);
		planMonthsMap.put(54, 175);
		planMonthsMap.put(55, 170);
		planMonthsMap.put(56, 164);
		planMonthsMap.put(57, 158);
		planMonthsMap.put(58, 152);
		planMonthsMap.put(59, 145);
		planMonthsMap.put(60, 139);
		planMonthsMap.put(61, 132);
		planMonthsMap.put(62, 125);
		planMonthsMap.put(63, 117);
		planMonthsMap.put(64, 109);
		planMonthsMap.put(65, 101);
		planMonthsMap.put(66, 93);
		planMonthsMap.put(67, 84);
		planMonthsMap.put(68, 75);
		planMonthsMap.put(69, 65);
		planMonthsMap.put(70, 56);
	}

	// 养老计算配置信息
	public final static String CONFIG_KEY_YANGLAO_PARAM = "yanglao.param";
	public final static String param_my_salary = "my_salary";
	public final static String param_ave_salary = "ave_salary";
	public final static String param_pay_year = "pay_year";
	public final static String param_my_rate = "my_rate";
	public final static String param_ave_rate = "ave_rate";
	public final static String param_retire_age = "retire_age";

	// 门诊类别
	public final static String MENZHEN_TYPE_NORMAL = "11";
	public final static String MENZHEN_TYPE_TARGETED = "12";
	public final static String MENZHEN_TYPE_CHRONIC = "13";
	public final static String MENZHEN_TYPE_SPRCIAL = "14";
	public final static Map<String, String> menzhenTypeMap = new LinkedHashMap<String, String>();

	static {
		menzhenTypeMap.put(MENZHEN_TYPE_NORMAL, "普通门诊");
		menzhenTypeMap.put(MENZHEN_TYPE_TARGETED, "定点药店购药");
		menzhenTypeMap.put(MENZHEN_TYPE_CHRONIC, "门诊慢性病");
		menzhenTypeMap.put(MENZHEN_TYPE_SPRCIAL, "单列特殊门诊");
	}

	public static final class sysLog {
		public final static String log_type_news = "news";
		public final static String log_type_news_category = "news_category";
		public final static String log_type_image = "image";
		public final static String log_type_image_category = "image_category";
		public final static String log_type_topic = "topic";
		public final static String log_type_topic_category = "topic_category";
		public final static String log_type_topic_reply = "topic_reply";
		public final static String log_type_org = "org";
		public final static String log_type_org_category = "org_category";
		public final static String log_type_guide = "guide";
		public final static String log_type_guide_category = "guide_category";
		public final static String log_type_user = "user";
		public final static String log_type_oper = "oper";
		public final static String log_type_post = "post";
		public final static String log_type_rank_temp = "rank_temp";
		public final static String log_type_rank = "rank";
		public final static String log_type_settings = "settings";
		public final static String log_type_dept = "dept";
		public final static String log_type_wechat = "wechat";
		public final static String log_type_vote = "vote";
		public final static String log_type_role = "role";

		public final static String log_action_create = "create";
		public final static String log_action_update = "update";
		public final static String log_action_delete = "delete";
		public final static String log_action_audit = "audit";
		public final static String log_action_login = "login";
		public final static String log_action_publish = "publish";

		public final static int level_min = 1;
		public final static int level_normal = 2;
		public final static int level_important = 3;

	}

	public final static String area_nanchang = "jx_nc";
	public final static String area_jiujiang = "jx_jj";
	public final static Map<String, String> areaMap = new LinkedHashMap<String, String>();

	static {
		areaMap.put(area_nanchang, "南昌");
		areaMap.put(area_jiujiang, "九江");
	}

	// 缴费订单状态
	public final static int PAYMENT_STATUS_NOT_PAIED = 1;
	public final static int PAYMENT_STATUS_PAIED_WATING_CONFIRM = 2;
	public final static int PAYMENT_STATUS_PAIED_CONFIRM_SUCCESS = 3;
	public final static int PAYMENT_STATUS_PAIED_CONFIRM_FAILURE = 4;
	public final static int PAYMENT_STATUS_REFUND_ACCEPTED = 5;
	public final static int PAYMENT_STATUS_REFUND_SUCCESS = 6;
	public final static int PAYMENT_STATUS_REFUND_FAILURE = 7;
	public final static Map<Integer, String> paymentSourceMap = new TreeMap<Integer, String>();

	static {
		paymentSourceMap.put(PAYMENT_STATUS_NOT_PAIED, "待支付");
		paymentSourceMap.put(PAYMENT_STATUS_PAIED_WATING_CONFIRM, "支付成功，等待上账");
		paymentSourceMap.put(PAYMENT_STATUS_PAIED_CONFIRM_SUCCESS, "支付成功，并已上账");
		paymentSourceMap.put(PAYMENT_STATUS_PAIED_CONFIRM_FAILURE, "支付成功，上账失败 ");
		paymentSourceMap.put(PAYMENT_STATUS_REFUND_ACCEPTED, "退款已受理");
		paymentSourceMap.put(PAYMENT_STATUS_REFUND_SUCCESS, "退款成功");
		paymentSourceMap.put(PAYMENT_STATUS_REFUND_FAILURE, "退款失败");
	}

	public static final int WECHAT_MASS_STATUS_INIT = 0;
	public static final int WECHAT_MASSS_STATUS_SEND = 10;

	public final static Map<Integer, String> wechatMassStatusMap = new TreeMap<Integer, String>();

	static {
		wechatMassStatusMap.put(WECHAT_MASS_STATUS_INIT, "未发布");
		wechatMassStatusMap.put(WECHAT_MASSS_STATUS_SEND, "已发布");
	}

	// 和我信接口通信私钥
	public final static String ANDWOXIN_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKhw5hIhcKpaWqVaa5WD1qv5SdwL\r\n"
			+ "60gnD/6Q+IFGejF3ayz0BMcNaALZBvzgHzL4TQkHab+MglzFeCa/dslaCnLeQZDDCDSNULBKzu3K\r\n"
			+ "ceUnewPRiGjdiFtFQ1Vqnk+8A6VQIe77M/J1yir+p1dj3encZqIPIhzs7+fOS2Si+AyVAgMBAAEC\r\n"
			+ "gYBnld53AeeWa7BZBc0pioVHByxbzVyo55TXeXHJlnzo1MatQ3d4rg7ryWmZs36cSsSdK9jlelRl\r\n"
			+ "Fu837QDw/JV1Y2ZF8HUDb8utQzRcGeDIzlHeu7Z5mK/55VcvoqFmacBRyHEpvr3Y1ECMQoV9LApm\r\n"
			+ "lU6ASRymhZc5ioPXdZGsVQJBAOot4DGTpU3fjx2zFSeF6S16WQp6FZGGP8zUXZLGke0Wko8gSQDq\r\n"
			+ "AICN4/+MiLfcgOLLSwcjX3fArrPFZzWzmm8CQQC4IuZ8FN59IXUhO6+wzsljtnUrmT7Xwj3jqLt5\r\n"
			+ "2orW/qIAzhuj/qcTMETeQR4SzP8njKgxFFhcEHqmfbhPMls7AkAGRHgNm5/+947DArn8Ns9I0k9z\r\n"
			+ "wlS1clxexQhEF2BscgA2KG0LwzeEOUfy3PYDxbkFWN+HL2EsuVFzqkLa4xWtAkEAtJt+yKvLgp6D\r\n"
			+ "fYJGlDxvaf83s/jOm9/oxljk8eM6lSASlImUsOkAxSBuHDVwou4xQoCDJAiZlkuPkaf0VLHGqwJA\r\n"
			+ "GFnAbTN/DA1300qqAUFJH9zH9E25+lihL3btjlUaaguJDcA1+5QyJKB9DC5iLfhNGfyFX4nw1+NT\r\n" + "GzJoEeJrzQ==\r\n";

	public final static String ANDWOXIN_ACCESS_KEY = "ZHRS20161111100528";
	public final static String ANDWOXIN_RESULTCODE_SUCCESS = "0";

	// 东软社保缴费接口相关常量
	public final static String NEUSOFT_SOCIAL_STAUTSCODE = "0001";

	// 权限控制
	public final static Map<String, Set<String>> authMap = new HashMap<String, Set<String>>();
	static {
		Set<String> temp = new HashSet<String>();
		for (int i = 1; i < 15; i++) {
			temp.add("tuwg" + i);
		}
		authMap.put("paymentAction", temp);
	}

	// 险种状态
	public static String NORMAL_STATUS = "0001"; // 正常状态
	public static String NOXY_STATUS = "0016"; // 无协议信息
	public static String NORY_STATUS = "0015"; // 无人员信息
	public static String QF_STATUS = "0019"; // 存在欠费
	public static String QT_STATUS = "0009"; // 其他错误
	public static String MD_STATUS = "0021"; // 免单人员，不需要银行缴费
	public static String NOBANK_STATUS = "0023"; // 该客户不能再本银行缴费

	// 支付发起入口
	public static String PAYMENT_FROM_ANDROID = "app_android";
	public static String PAYMENT_FROM_IOS = "app_ios";
	public static String PAYMENT_FROM_WE = "h5_weixin";
	public static String PAYMENT_FROM_ANDWOXIN = "h5_andwoxin";
	// 支付渠道
	public static String PAYMENT_CHANNEL_ANDWOXIN = "andwoxin_pay";
	public static String PAYMENT_CHANNEL_WE = "we_pay";

	public static String SUCCESS_INFO = "success";

	// 现在支付常量
	public static String APP_ID = "149762285172190"; // 商户应用唯一标示
	public static String APP_KEY = "pKGaEVyDKmMMARsRkcz8TsV6mpJXqiv1";
	public static String FUN_CODE_PAY = "WP001"; // 功能号
	public static String VERSION = "1.0.0";
	public static String DEVICE_TYPE = "0600"; // 设备类型:0600公众号
	public static String PAY_CHANNEL_TYPE = "13"; // 用户所选渠道类型:13微信，12支付宝
	public static String OUTPUT_TYPE = "1"; // 输出格式：0直接调用支付，1返回支付凭证
	public static String MHT_ORDER_TYPE = "01"; // 商户交易类型:01普通消费
	public static String MHT_CURRENCY_TYPE = "156"; // 币种:156人民币
	public static String MHT_ORDER_TIMEOUT = "3600"; // 订单超时时间
	public static String MHT_CHARSET = "UTF-8"; // 字符编码
	public static String MHT_SUBAPPID = "wxeaaac97ac1fa2f71"; // 商户APPid：output=1且paychannelType=13时必传
	public static String MHT_SIGNTYPE = "MD5"; // 签名方法
	public static String NOTIFY_URL = "http://wechat.lihuansoft.com:9002/ihrss/wechat/notify.do"; // 回调地址
	public static String IPAY_URL = "https://pay.ipaynow.cn/"; // 支付地址
}
