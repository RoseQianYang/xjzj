package com.yunqi.jhf.web.portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.persistence.TProductDao;
import com.yunqi.jhf.dao.persistence.TSGroupDao;
import com.yunqi.jhf.dao.persistence.TSGroupJoinDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.service.front.TProductService;

/**
 * 
 * @author Seek
 *
 */
@CrossOrigin
@RestController
public class IndexAction {

	private static final Logger log = Logger.getLogger(IndexAction.class);
	
	@Autowired
	private TProductDao tProductDao;
	
	@Autowired
	private TSGroupJoinDao tsGroupJoinDao;
	
	@Autowired
	private TSGroupDao tsGroupDao;
	
	@Autowired
	private TProductService tProductService;
	/**
	 * 跳转到wechat首页
	 * 
	 * @param req
	 * @param sess
	 * @param res
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest req, HttpSession sess, HttpServletResponse res) throws Exception {
		res.sendRedirect("wechatIndex.do");
		return null;
	}*/

	/*@RequestMapping(value = "/wechatIndex")
	public ModelAndView wechatIndex(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/index");
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		PageList<TProduct> proList = new PageList<TProduct>();
		PageList<TProduct> tProList = new PageList<TProduct>();
		proList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
		List<TSGroup> groupList = tsGroupDao.list(null, new TSGroup());
		if (groupList.size() > 0) {
			for (TSGroup tsGroup : groupList) {
				int productId = tsGroup.getProductId();
				tProList = tProductDao.pageList(proList, true, 
						new SqlSelect().where(TProduct.SQL_putaway).and(TProduct.SQL_id),
						new TProduct().setPutaway("Y").setId(productId));
			}
		}
		mav.addObject("tProList", tProList);
		mav.addObject("userId",userId);
		return mav;
	}
	*/
	/**
	 * 优化  异步获取  产品列表
	 * @param JsonInfo
	 * @return json
	 */
	@RequestMapping(value = "/getProductPageLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getProductPageLists(@RequestBody ModelMap JsonInfo) {
		return tProductService.getProductPageLists(JsonInfo);
	}
}