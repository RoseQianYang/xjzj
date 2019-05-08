package com.yunqi.jhf.service.background;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.common.util.ExportExcelUtil;
import com.yunqi.jhf.dao.StatisticsDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.vo.IntegralStatsBean;
import com.yunqi.jhf.vo.OrderDetailBean;
import com.yunqi.jhf.vo.SalesVolumeStatsBean;
import com.yunqi.jhf.vo.UserStatisticsBean;

@Service
public class StatisticsService {
	
	protected static Logger log = Logger.getLogger(StatisticsService.class);
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	public final static String[] ORDERDETAIL_TITLE = {"用户名","产品名称","产品分类","产品品牌","购买单价","购买数量","购买时间"};
	
	public JsonResult getUserStatsByYear(ModelMap jsonInfo) {
		log.info("进入用户年统计信息获取接口");
		JsonResult result = new JsonResult();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String time = formatter.format(new Date());
		if(jsonInfo.get("year") != null) {
			time = (String) jsonInfo.get("year");
		}
		Integer bigTime = Integer.parseInt(time) + 1;
		jsonInfo.put("latTime", time);
		jsonInfo.put("bigTime", bigTime.toString());
		List<UserStatisticsBean> list = statisticsDao.getUserStatsByYear(jsonInfo);
		if(list != null) {
			log.info("用户年统计信息获取接口执行成功");
			result.success("获取成功");
			result.setData(list);
		}else {
			log.info("用户年统计信息获取接口执行失败");
			result.success("获取失败");
		}
		return result;
	}
	
	public JsonResult getUserStatsByMonth(ModelMap jsonInfo) {
		log.info("进入用户月统计信息获取接口");
		JsonResult result = new JsonResult();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String[] time = formatter.format(new Date()).split("-");
		if(jsonInfo.get("year") != null) {
			time[0] = (String) jsonInfo.get("year");
		}
		if(jsonInfo.get("month") != null) {
			time[1] = (String) jsonInfo.get("month");
		}
		Integer bigTime = Integer.parseInt(time[1]) + 1;
		jsonInfo.put("latTime", time[0] + "-" + time[1]);
		jsonInfo.put("bigTime", time[0] + "-" + (bigTime < 10 ? "0" + bigTime : bigTime));
		List<UserStatisticsBean> list = statisticsDao.getUserStatsByMonth(jsonInfo);
		if(list != null) {
			log.info("用户月统计信息获取接口执行成功");
			result.success("获取成功");
			result.setData(list);
		}else {
			log.info("用户月统计信息获取接口执行失败");
			result.success("获取失败");
		}
		return result;
	}
	
	public JsonResult getIntegralStats(ModelMap jsonInfo) {
		log.info("进入用户积分统计流水获取接口");
		JsonResult result = new JsonResult();
		PageList<IntegralStatsBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
			jsonInfo.put(ConstantTool.PAGE_CURRENTSIZE, pagelist.getPageSize());
		}
		List<IntegralStatsBean> list = statisticsDao.getIntegralStats(jsonInfo);
		if(list != null) {
			log.info("用户积分统计流水获取执行成功");
			pagelist.setList(list);
			pagelist.setTotalSize(statisticsDao.getIntegralStatsCount(jsonInfo));
			result.success("获取成功");
			result.setData(pagelist);
		}else {
			log.info("用户积分统计流水获取执行失败");
			result.success("获取失败");
		}
		return result;
	}
	
	public JsonResult getSalesVolumeStatsList(ModelMap jsonInfo) {
		log.info("进入获取单品销量统计列表接口");
		JsonResult result = new JsonResult();
		PageList<SalesVolumeStatsBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
			jsonInfo.put(ConstantTool.PAGE_CURRENTSIZE, pagelist.getPageSize());
		}
		List<SalesVolumeStatsBean> list = statisticsDao.getSalesVolumeStatsList(jsonInfo);
		if(list != null) {
			log.info("获取单品销量统计列表接口执行成功");
			pagelist.setList(list);
			pagelist.setTotalSize(statisticsDao.getSalesVolumeStatsCount(jsonInfo));
			result.success("获取成功");
			result.setData(pagelist);
		}else {
			log.info("获取单品销量统计列表接口执行失败");
			result.success("获取失败");
		}
		return result;
	}
	
	public JsonResult getSalesVolumeStatsByYear(ModelMap jsonInfo){
		log.info("进入获取单品年销量统计接口");
		JsonResult result = new JsonResult();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String time = formatter.format(new Date());
		if(jsonInfo.get("year") != null) {
			time = (String) jsonInfo.get("year");
		}
		Integer bigTime = Integer.parseInt(time) + 1;
		jsonInfo.put("latTime", time);
		jsonInfo.put("bigTime", bigTime.toString());
		List<SalesVolumeStatsBean> list = statisticsDao.getSalesVolumeStatsByYear(jsonInfo);
		if(list != null) {
			log.info("获取单品年销量统计接口执行成功");
			result.success("获取成功");
			result.setData(list);
		}else {
			log.info("获取单品年销量统计接口执行失败");
			result.success("获取失败");
		}
		return result;
	}
	
	public JsonResult getSalesVolumeStatsByMonth(ModelMap jsonInfo){
		log.info("进入获取单品月销量统计接口");
		JsonResult result = new JsonResult();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String[] time = formatter.format(new Date()).split("-");
		if(jsonInfo.get("year") != null) {
			time[0] = (String) jsonInfo.get("year");
		}
		if(jsonInfo.get("month") != null) {
			time[1] = (String) jsonInfo.get("month");
		}
		Integer bigTime = Integer.parseInt(time[1]) + 1;
		jsonInfo.put("latTime", time[0] + "-" + time[1]);
		jsonInfo.put("bigTime", time[0] + "-" + (bigTime < 10 ? "0" + bigTime : bigTime));
		List<SalesVolumeStatsBean> list = statisticsDao.getSalesVolumeStatsByMonth(jsonInfo);
		if(list != null) {
			log.info("获取单品月销量统计接口执行成功");
			result.success("获取成功");
			result.setData(list);
		}else {
			log.info("获取单品月销量统计接口执行失败");
			result.success("获取失败");
		}
		return result;
	}
	
	public JsonResult getOrderDetailList(ModelMap jsonInfo){
		log.info("进入获取销售明细列表接口");
		JsonResult result = new JsonResult();
		PageList<OrderDetailBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
			jsonInfo.put(ConstantTool.PAGE_CURRENTSIZE, pagelist.getPageSize());
		}
		List<OrderDetailBean> list = statisticsDao.getOrderDetailList(jsonInfo);
		if(list != null) {
			log.info("获取销售明细列表接口执行成功");
			pagelist.setList(list);
			pagelist.setTotalSize(statisticsDao.getOrderDetailCount(jsonInfo));
			result.success("获取成功");
			result.setData(pagelist);
		}else {
			log.info("获取销售明细列表接口执行失败");
			result.success("获取失败");
		}
		
		return result;
	}
	
	
	public void exportExcel(HttpServletResponse response,ModelMap jsonInfo) {
		log.info("进入导出销售明细excel接口方法");
		List<OrderDetailBean> list = statisticsDao.getOrderDetailList(jsonInfo);
		String[] [] values = new String[list.size()][ORDERDETAIL_TITLE.length];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		for(int i = 0;i < list.size(); i++) {
			values[i][0] = list.get(i).getUserName(); //用户名
			values[i][1] = list.get(i).getProductTitle(); //产品名称
			values[i][2] = list.get(i).getProductCateTitle(); //产品分类
			values[i][3] = list.get(i).getProductBrandTitle(); //产品品牌
			values[i][4] = list.get(i).getProductPrice() + ""; //购买单价
			values[i][5] = list.get(i).getProductCount() + ""; //购买数量
			values[i][6] = sdf.format(list.get(i).getCreateTime()); //购买时间
		}
		HSSFWorkbook wb = ExportExcelUtil.getHSSFWorkbook("销售明细列表", ORDERDETAIL_TITLE, values);
		try {
			this.setResponseHeader(response, UUID.randomUUID().toString() + ".xls");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
			log.info("导出销售明细excel接口方法执行成功");
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	//发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
            	log.error(e);
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            log.error(ex);
        }
    }

}
