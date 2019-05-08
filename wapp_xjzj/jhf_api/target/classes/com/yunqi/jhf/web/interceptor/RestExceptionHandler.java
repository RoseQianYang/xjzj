package com.yunqi.jhf.web.interceptor;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;

/**
 * @author ZhangQ 统一异常处理器
 */
@RestControllerAdvice
public class RestExceptionHandler {

	protected Logger logger = Logger.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(NumberFormatException.class)
	public JsonResult numberFormat(NumberFormatException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------NumberFormatException   " + errorMsg);
		return new ErrorResult("数据转换失败");
	}

	@ExceptionHandler(NullPointerException.class)
	public JsonResult nullPointer(NullPointerException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------NullPointerException   " + errorMsg);
		return new ErrorResult("对象为空");
	}

	@ExceptionHandler(SQLException.class)
	public JsonResult sqlGrammer(SQLException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------SQLException   " + errorMsg);
		return new ErrorResult("数据库错误，无法操作");
	}

	@ExceptionHandler(ParseException.class)
	public JsonResult parse(ParseException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------ParseException   " + errorMsg);
		return new ErrorResult("解析发生错误");
	}

	@ExceptionHandler(RuntimeException.class)
	public JsonResult runTime(RuntimeException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------RuntimeException   " + errorMsg);
		return new ErrorResult("运行时发生错误");
	}

	@ExceptionHandler(ClassCastException.class)
	public JsonResult classCast(ClassCastException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------ClassCastException   " + errorMsg);
		return new ErrorResult("类型转换错误");
	}

	@ExceptionHandler(IOException.class)
	public JsonResult ioException(IOException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------IOException   " + errorMsg);
		return new ErrorResult("输入/输出错误");
	}

	@ExceptionHandler(NoSuchMethodException.class)
	public JsonResult noMethod(NoSuchMethodException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------NoSuchMethodException   " + errorMsg);
		return new ErrorResult("找不到该方法");
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, HttpMessageNotWritableException.class })
	public JsonResult messageConverter(HttpMessageConversionException e) {
		String errorMsg = e.getMessage();
		logger.info("Exception------------HttpMessageConversionException   " + errorMsg);
		return new ErrorResult("信息传输错误");
	}
}
