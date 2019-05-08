package com.yunqi.common.util;

import java.util.*;

/**
 * @author  ZhangQ
 */
public class MyData {

    //默认每页记录数
    public final static  Integer DEFAULT_PAGE_SIZE = 10;
    //默认当前页数
    public final static  Integer DEFAULT_CURRENT_PAGE = 1;

    /**用户启用状态 0-停用 1-启用
     *  合作客户数据 customer_activate_read
     *  过期客户数据 customer_deactivate_read
     *  可查看所有客户数据 all
     *  可查看个人的数据  self（如果是客户表以服务人和负责人id做判断）
     */
    public final static  Integer USER_ENABLED = 1;
    public final static  Integer USER_DISABLED = 0;
    public final static String CUSTOMER_ACTIVATE_READ = "customer_activate_read";
    public final static String CUSTOMER_DEACTIVATE_READ = "customer_deactivate_read";
    public final static String READ_ALL = "all";
    public final static String READ_SELF = "self";

    //用户角色规定
    public final static Integer ADMIN = 1;
    public final static Integer MANAGER = 2;
    public final static Integer SALES_DIRECTOR = 3;
    public final static Integer SALES = 4;
    public final static Integer SERVICER_DIRECTOR = 5;
    public final static Integer SERVICER = 6;
    public final static Integer ACCOUNTANT = 7;
    //用户角色map
    public final static Map<Integer,String> roleMap = new LinkedHashMap<>();
    static {

        roleMap.put(MyData.ADMIN,"管理员");
        roleMap.put(MyData.MANAGER,"总经办");
        roleMap.put(MyData.SALES_DIRECTOR,"销售总监");
        roleMap.put(MyData.SALES,"销售");
        roleMap.put(MyData.SERVICER_DIRECTOR,"客服总监");
        roleMap.put(MyData.SERVICER,"客服");
        roleMap.put(MyData.ACCOUNTANT,"财务人员");

    }



    //department 总经办，销售部，客服部，综合部
    public final static List<String> departmentList = new ArrayList<>();

    static {
        departmentList.add("总经办");
        departmentList.add("销售部");
        departmentList.add("客服部");
        departmentList.add("综合部");
    }



    /**
     * 合同类型  1-首签 2-续费
     *          1-企业版  2-专业版
     */
    public final static Integer CONTRACT_FIRST_SIGN = 1;
    public final static Integer CONTRACT_RENEWALS = 2;
    public final static Integer PRODUCT_ENTERPRISE_EDITION = 1;
    public final static Integer PRODUCT_PROFESSIONAL_EDITION = 2;

    /**
     * 客户 customer
     *
     */
    //客户状态 0-停用  1-启用
    public final static Integer CUSTOMER_ENABLED = 1;
    public final static Integer CUSTOMER_DISABLED = 0;


    /**
     *
     * 附件信息
     */

    public final static Long FILE_MAXSIZE = 1024*1024* 50L;
}
