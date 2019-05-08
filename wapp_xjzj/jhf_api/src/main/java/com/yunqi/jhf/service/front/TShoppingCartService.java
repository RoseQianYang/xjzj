package com.yunqi.jhf.service.front;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.ShoppingCartDetailDao;
import com.yunqi.jhf.dao.domain.TProductAttribute;
import com.yunqi.jhf.dao.domain.TShoppingCart;
import com.yunqi.jhf.dao.domain.TShoppingCartDetail;
import com.yunqi.jhf.dao.persistence.TShoppingCartDao;
import com.yunqi.jhf.dao.persistence.TShoppingCartDetailDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.ShoppingCartDetailBean;
import com.yunqi.jhf.web.portal.WechatSessionUser;

@Service
public class TShoppingCartService {
	protected Logger logger = Logger.getLogger(TShoppingCartService.class);

	@Resource
	private TShoppingCartDao tShoppingCartDao;
	@Resource
	private TShoppingCartDetailDao tShoppingCartdetailDao;
	@Resource
	private ShoppingCartDetailDao ShoppingcartDetailDao;
	@Autowired
	private TProductService tProductService;
	
	/**
	 * 通过购物车Id获取购物车所有产品
	 * 
	 * @param currentPage
	 *            当前页T userId 当前操作用户ID
	 * @return 分页产品
	 */
	public PageList<ShoppingCartDetailBean> findShopcartProduct(Integer currentPage, int userId) {
		ModelMap map = new ModelMap();
		PageList<ShoppingCartDetailBean> pageList = new PageList<ShoppingCartDetailBean>();
		try {
			if (userId != -1) {
				int shopcartId = findShopcartId(userId);
				map.put("shopcartId", shopcartId);
				List<ShoppingCartDetailBean> detailList = ShoppingcartDetailDao.getshopcartDetailList(map);
				if (detailList .size()!=0) {
					pageList.setList(detailList);
					pageList.setCurrentPage(currentPage);
					pageList.setPageSize(Const.FONT_PAGE_SIZE);
					int totalSize = detailList.size();
					pageList.setTotalSize(totalSize);
				} else {
					logger.info("获取购物车详情信息失败");
				}
			} else {
				logger.info("获取用户信息失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}

	/**
	 * 异步 获取 所有购物车Id获取购物车所有产品 分页列表
	 * 
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getcartDetails(HttpServletRequest req, ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<ShoppingCartDetailBean> pageList = new PageList<ShoppingCartDetailBean>();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			}
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", pageList.getFromPos());
			jsonInfo.put("size", Const.TEST_FONT_PAGE_SIZE);
			if (userId > 0) {
				int shopcartId = findShopcartId(userId);
				jsonInfo.put("shopcartId", shopcartId);
				List<ShoppingCartDetailBean> detailList = ShoppingcartDetailDao.getshopcartDetailpageList(jsonInfo);
				if (detailList.size()!=0) {
					pageList.setList(detailList);
					pageList.setTotalSize(ShoppingcartDetailDao.getshopcartDetailCount(jsonInfo));
					result.setData(pageList);
					logger.info("获取购物车详情接口执行成功");
					result.success("获取成功");
				} else {
					logger.info("获取购物车产品无第二页产品");
					return result.error("获取购物车产品无第二页产品");
				}
			} else {
				logger.info("获取用户信息失败");
				return result.error("获取用户信息失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return result.error("获取失败");
		}
		return result;
	}

	/**
	 * 通过用户Id获取购物车ID
	 * 
	 * @param userId
	 *            当前操作用户ID
	 * @return 购物车ID
	 */
	public int findShopcartId(int userId) {
		// 通过用户ID查找购物车ID
		TShoppingCart shoppingCart = new TShoppingCart();
		SqlSelect sqlSelect = new SqlSelect().where(TShoppingCart.SQL_userId);
		shoppingCart.setUserId(userId);
		int shopcartId = 0;
		try {
			TShoppingCart shopcart = tShoppingCartDao.load(sqlSelect, shoppingCart);
			if (shopcart != null) {
				shopcartId = shopcart.getId();
			} else {
				logger.info("未获取购物车ID，根据用户ID新创建一个购物车ID");
				tShoppingCartDao.insert(shoppingCart);
				int userIdnew = shoppingCart.getUserId();
				findShopcartId(userIdnew);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return shopcartId;
	}

	/**
	 * 通过购物车详情Id获取购物车详情里面的数据
	 * 
	 * @param userId
	 *            当前操作用户ID
	 * @return 购物车详情
	 */
	public TShoppingCartDetail findShopcartDetailById(int detailId) {
		// 通过用户ID查找购物车ID
		TShoppingCartDetail shoppingCartDetail = new TShoppingCartDetail();
		SqlSelect sqlSelect = new SqlSelect().where(TShoppingCartDetail.SQL_id);
		shoppingCartDetail.setId(detailId);
		try {
			shoppingCartDetail = tShoppingCartdetailDao.load(sqlSelect, shoppingCartDetail);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return shoppingCartDetail;
	}

	/**
	 * 加入购物车
	 * 
	 * @param userId
	 *            productId productNum productColor productSize
	 * @return result
	 */
	public JsonResult insertShopCart(int userId, int productId, int productNum, String productColor, String productSize,
			int productPrice) {
		JsonResult result = new JsonResult();
		TShoppingCartDetail sc = new TShoppingCartDetail();
		try {
			if (userId != -1) {
				TShoppingCartDetail shopcartdetail = new TShoppingCartDetail();
				if (StringUtils.isEmpty(productSize) && StringUtils.isEmpty(productColor)) {

				
					int shopcartId = findShopcartId(userId);
					shopcartdetail.setShoppingCartId(shopcartId);
					shopcartdetail.setProductId(productId);
					shopcartdetail.setProductPrice(productPrice);

					SqlSelect sqlSelect = new SqlSelect().where(TShoppingCartDetail.SQL_shoppingCartId)
							.and(TShoppingCartDetail.SQL_productId).and(TShoppingCartDetail.SQL_productPrice);

					sc = tShoppingCartdetailDao.load(sqlSelect, shopcartdetail);
				} else if (StringUtils.isEmpty(productSize) && StringUtils.isNotEmpty(productColor)) {
					
					int shopcartId = findShopcartId(userId);
					shopcartdetail.setShoppingCartId(shopcartId);
					shopcartdetail.setProductId(productId);
					shopcartdetail.setProductColor(productColor);
					shopcartdetail.setProductPrice(productPrice);

					SqlSelect sqlSelect = new SqlSelect().where(TShoppingCartDetail.SQL_shoppingCartId)
							.and(TShoppingCartDetail.SQL_productId).and(TShoppingCartDetail.SQL_productColor)
							.and(TShoppingCartDetail.SQL_productPrice);

					sc = tShoppingCartdetailDao.load(sqlSelect, shopcartdetail);
				} else if (StringUtils.isNotEmpty(productSize) && StringUtils.isEmpty(productColor)) {
					
					int shopcartId = findShopcartId(userId);
					shopcartdetail.setShoppingCartId(shopcartId);
					shopcartdetail.setProductId(productId);
					shopcartdetail.setProductColor(productSize);
					shopcartdetail.setProductPrice(productPrice);

					SqlSelect sqlSelect = new SqlSelect().where(TShoppingCartDetail.SQL_shoppingCartId)
							.and(TShoppingCartDetail.SQL_productId).and(TShoppingCartDetail.SQL_productSize)
							.and(TShoppingCartDetail.SQL_productPrice);
					sc = tShoppingCartdetailDao.load(sqlSelect, shopcartdetail);
				}else{
				
					int shopcartId = findShopcartId(userId);
					shopcartdetail.setShoppingCartId(shopcartId);
					shopcartdetail.setProductId(productId);
					shopcartdetail.setProductColor(productColor);
					shopcartdetail.setProductSize(productSize);
					shopcartdetail.setProductPrice(productPrice);

					SqlSelect sqlSelect = new SqlSelect().where(TShoppingCartDetail.SQL_shoppingCartId)
							.and(TShoppingCartDetail.SQL_productId).and(TShoppingCartDetail.SQL_productColor)
							.and(TShoppingCartDetail.SQL_productSize).and(TShoppingCartDetail.SQL_productPrice);

					sc = tShoppingCartdetailDao.load(sqlSelect, shopcartdetail);
				}

				
				if (sc != null) {
					logger.info("购物车已存在商品，直接修改商品的数量");
					int id = sc.getId();
					int num = sc.getProductNum();
					int pronum = num + productNum;
					
					TProductAttribute prodAtt = tProductService.getProductStock(sc.getProductId(),sc.getProductColor(), sc.getProductSize());
					int stock=prodAtt.getStock();
					
					if(pronum<=stock){
						shopcartdetail.setProductNum(pronum);
					}else{
						shopcartdetail.setProductNum(stock);
					}	
					
					shopcartdetail.setId(id);
					int res = tShoppingCartdetailDao.updateAllById(shopcartdetail);
					if (res > 0) {
						logger.info("添加成功");
						result.success("添加购物车成功");
					} else {
						result.error("添加失败");
					}
				} else {
					logger.info("购物车未存在商品，直接添加商品");
					shopcartdetail.setProductNum(productNum);
					int res = tShoppingCartdetailDao.insert(shopcartdetail);
					if (res > 0) {
						logger.info("添加成功");
						result.success("添加购物车成功");
					} else {
						result.error("添加失败");
					}
				}
			}
		} catch (Exception e) {
			result.error("商品添加购物车失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除购物车产品
	 * 
	 * @param shoppingCartId
	 *            购物车产品id
	 * @return JsonResult
	 */
	public JsonResult delete(int shoppingCartId) {
		JsonResult result = new JsonResult();
		TShoppingCartDetail tShoppingCartDetail = tShoppingCartdetailDao.loadById(shoppingCartId);
		if (tShoppingCartDetail == null) {
			logger.info("购物车产品不存在或已删除");
			result.error("删除失败");
		}
		int res = tShoppingCartdetailDao.deleteById(shoppingCartId);
		if (res > 0) {
			logger.info("后台删除购物车产品执行成功");
			result.success("删除成功");
		} else {
			logger.info("后台删除购物车产品执行失败");
			result.error("删除失败");
		}
		return result;
	}

	/**
	 * 修改购物车产品数量
	 * 
	 * @param shoppingCartId
	 *            购物车产品id
	 * @return JsonResult
	 */
	public JsonResult update(int shoppingCartDetailId, int jiaJian) {
		JsonResult result = new JsonResult();
		int res = 0;
		if (shoppingCartDetailId != -1) {
			TShoppingCartDetail tShoppingCartDetail = tShoppingCartdetailDao.loadById(shoppingCartDetailId);
			TProductAttribute tpa = new TProductAttribute();

			if (tShoppingCartDetail != null) {
				int proId = tShoppingCartDetail.getProductId();
				String proColor = tShoppingCartDetail.getProductColor();
				String proSize = tShoppingCartDetail.getProductSize();
				tpa = tProductService.getProductStock(proId, proColor, proSize);
				int stock = tpa.getStock();
				if (jiaJian == 1) {
					int id = tShoppingCartDetail.getId();
					int num = tShoppingCartDetail.getProductNum() + 1;
					if (num > stock) {
						res = -1;
					} else {
						tShoppingCartDetail.setId(id);
						tShoppingCartDetail.setProductNum(num);
						res = tShoppingCartdetailDao.updateAllById(tShoppingCartDetail);
					}

				} else {
					int id = tShoppingCartDetail.getId();
					int num = tShoppingCartDetail.getProductNum() - 1;
					tShoppingCartDetail.setId(id);
					tShoppingCartDetail.setProductNum(num);
					res = tShoppingCartdetailDao.updateAllById(tShoppingCartDetail);
				}
				if (res > 0) {
					result.success("修改成功");
				} else {
					result.error("超过库存不能添加");
				}
			}

		}
		return result;
	}

}
