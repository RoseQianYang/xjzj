package com.yunqi.common.util;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author  ZhangQ
 */
public class DaoUtil {

    public static String formatData(Timestamp timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return   dateFormat.format(timestamp);
    }

    /**
     * 判断查询条件是否可用
     * @param condition 查询条件
     * @return 是否可用，可用则true
     */
    public static boolean conditionUsable(Object condition){
        if(condition instanceof Date){
            return condition != null;
        }
        if(condition instanceof String){
            if("".equals(condition)) return false;
            String conditions =  ((String) condition).trim();
            Integer length = conditions.replaceAll(" ","").length();
            return length>0;
        }
        if (condition instanceof Integer){
            return true;
        }
        return condition != null;
    }

    /**
     * 查询条件统一trim
     * @param condition  查询条件
     * @return 查询条件
     */
    public static String trimCondition(String condition){
        return condition.replaceAll(" ","");
    }

    /**
     * nvl函数实现
     * @param a a
     * @return 可用的String
     */
    public static String nvl(String a){
        return a == null ? "0" : a;
    }

    /**
     *
     * 抽象出三张小表在 self 权限下条件补充
     */
   /* public static void Condition(Integer userId,String dataAccess,StringBuilder sql,Map<String,Object> condition){
        if(MyData.READ_SELF.equals(dataAccess) && userId != null){
            //去掉右边两个小括号
            sql.delete(sql.length()-2,sql.length());
            sql.append(" and servicer_id=:servicer_id or sale_id=:sale_id))");
            condition.put("servicer_id",userId);
            condition.put("sale_id",userId);
        }
    }*/
}
