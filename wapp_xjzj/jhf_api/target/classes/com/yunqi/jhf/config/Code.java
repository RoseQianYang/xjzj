package com.yunqi.jhf.config;

/**
 * 所有通信协议返回代码定义
 * 
 * @author Administrator
 */
public interface Code {

	// 0-1000 属于通用错误
	int CODE_SUCCESS = 0;
	int CODE_SERVICE_ERROR = 1;
	int HAVE_NEW_VERSION = 3;
	int HAVE_MUST_UPDATE_NEW_VERSION = 4;

	// 500 以内客户端错误
	int CODE_NOT_LOGIN = 2;
	int PARAMETER_INVALID = 100;	// 参数异常
	
	// 500以上服务端错误
	int INTERNAL_ERROR = 500;
	int DB_EXCEPTON = 501;		// 数据库异常

}
