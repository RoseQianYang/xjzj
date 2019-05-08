package com.yunqi.jhf.service.front;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.OrderDetailDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.vo.OrderDetailBean;
import com.yunqi.jhf.web.portal.WechatSessionUser;

@Service
public class TOrderDetailService {

	private static final Logger log = Logger.getLogger(TOrderDetailService.class);

	@Resource
	private OrderDetailDao orderDetailDao;
	
	/**
	 * 通过orderId获取订单详情列表
	 * @param orderId
	 * @return list
	 */
	public List<OrderDetailBean> getOrderDetailByOidList(int orderId) {
		ModelMap jsonInfo = new ModelMap();
		JsonResult result = new JsonResult();
		List<OrderDetailBean> orderDetailList = null;
		try {
			if (orderId != -1) {
				jsonInfo.put("orderId", orderId);
			}else {
				log.error("获取订单id失败");
			}
			orderDetailList = orderDetailDao.getOrderDetailByOidList(jsonInfo);
		} catch (Exception e) {
			result.error("获取订单详情列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return orderDetailList;
    }  
	
	/**
	 * 通过userId获取订单详情列表
	 * @param userId
	 * @return list
	 */
	public List<OrderDetailBean> getOrderDetailByUidList(int userId) {
		ModelMap jsonInfo = new ModelMap();
		JsonResult result = new JsonResult();
		List<OrderDetailBean> orderDetailList = null;
		try {
			if (userId != -1) {
				jsonInfo.put("userId", userId);
			}else {
				log.error("获取用户id失败");
			}
			orderDetailList = orderDetailDao.getOrderDetailByUidList(jsonInfo);
		} catch (Exception e) {
			result.error("获取订单详情列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return orderDetailList;
    } 
	
	/**
	 * 异步 通过userId获取订单详情列表
	 * @param req
	 * @param jsonInfo
	 * @return
	 */
	public JsonResult getorderLists(HttpServletRequest req,ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<OrderDetailBean> OrderDetailList = new PageList<OrderDetailBean>();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				OrderDetailList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			OrderDetailList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", OrderDetailList.getFromPos());
			jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);	
			if (userId != -1) {
				jsonInfo.put("userId", userId);
				 List<OrderDetailBean> orderDetailByUidList = orderDetailDao.getOrderDetailByUidLists(jsonInfo);
				if (orderDetailByUidList != null) {
					OrderDetailList.setList(orderDetailByUidList);
					OrderDetailList.setTotalSize(orderDetailDao.getOrderDetailByUidListsCount(jsonInfo));
				    result.setData(OrderDetailList);
					log.info("异步 获取订单详情列表成功");
					result.success("异步 获取 订单详情列表成功");
				}
			}else {
				log.error("获取用户id失败");
				return result.error("获取用户id失败");
			}
		} catch (Exception e) {
			result.error("获取订单详情列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
}
