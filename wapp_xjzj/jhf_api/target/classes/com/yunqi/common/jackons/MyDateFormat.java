package com.yunqi.common.jackons;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 自定义日期转换规则  格式化json数据的时候用到
 * @author leter
 */
public class MyDateFormat extends DateFormat{

	private static final long serialVersionUID = 1L;
	
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat(DATETIME_FORMAT);
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT);
	
	public MyDateFormat(){}
	
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,FieldPosition fieldPosition) {
        String value = sdf1.format(date);
        toAppendTo.append(value);
        return toAppendTo;
	}

	@Override
	public Date parse(String source, ParsePosition pos)  {
		int len = source.length();
        pos.setIndex(len);
        try {
        	 if(len > 10){
      			return sdf1.parse(source);
        	 }else{
     			return sdf2.parse(source);
        	 }
		} catch (ParseException e) {
	        return null;
		}
	}
	
	 @Override
	 public Object clone() {
	      return this;    
	 }
	 
//	 public static void main(String[] args) throws IOException{
//		 ObjectMapper mapper = new CustomObjectMapper();
//		 try {
//			String json = mapper.writeValueAsString(new Date());
//			AppendAccount aa = mapper.readValue("{\"startDate\":\"2017-09-11\"}", AppendAccount.class);
//			System.out.println(json);
//			System.out.println(aa);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	 }

}
