package com.yunqi.jhf.service.background;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.ProductDao;
import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.domain.TProductAttribute;
import com.yunqi.jhf.dao.persistence.TProductAttributeDao;
import com.yunqi.jhf.dao.persistence.TProductDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlDelete;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.ProductBean;

@Service
public class ProductInfoService {

	protected static Logger log = Logger.getLogger(ProductInfoService.class);

	@Autowired
	private ProductDao prdDao;

	@Autowired
	private TProductDao tProductDao;

	@Autowired
	private TProductAttributeDao tProductAttributeDao;

	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取商品信息列表接口");
		JsonResult result = new JsonResult();
		PageList<ProductBean> pagelist = new PageList<>();
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		} else {
			log.info("输入页码数为空");
			return result.error("输入页码数不可为空");
		}
		List<ProductBean> productList = prdDao.getProductList(jsonInfo);
		if (productList != null) {
			pagelist.setList(productList);
			pagelist.setTotalSize(prdDao.getProductCount(jsonInfo));
			result.setData(pagelist);
			log.info("获取商品信息列表接口执行成功");
			result.success("获取成功");
		} else {
			log.info("获取商品信息列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}

	public JsonResult create(TProduct product) {
		log.info("进入新增商品信息接口");
		JsonResult result = new JsonResult();
		if (product.getCateId() == 0) {
			log.info("商品分类为空");
			return result.error("未选择商品分类");
		}
		if (product.getBrandId() == 0) {
			log.info("商品品牌为空");
			return result.error("未选择商品品牌");
		}
		if (product.getImageId() == null) {
			log.info("素材图片为空");
			return result.error("未选择商品素材图片");
		}
		if (product.getTitle() == null || "".equals(product.getTitle())) {
			log.info("商品名称为空");
			return result.error("商品名称不可为空");
		}
		if (product.getPrice() == 0) {
			log.info("商品售价为空");
			return result.error("商品售价不可为0");
		}
		if (product.getCover() == null || "".equals(product.getCover())) {
			log.info("商品封面为空");
			return result.error("商品封面不可为空");
		}
		if (product.getDescription() == null || "".equals(product.getDescription())) {
			log.info("商品描述简介为空");
			return result.error("商品描述简介不可为空");
		}
		if (product.getContent() == null || "".equals(product.getContent())) {
			log.info("商品图文详情为空");
			return result.error("商品图文详情不可为空");
		}
		product.setPutaway("Y");
		int productId = tProductDao.insert(product);
		if (productId > 0) {
			log.info("新增商品信息接口执行成功");
			result.success("新增成功");
			result.setData(prdDao.getProductById(productId));
		} else {
			log.info("新增商品信息接口执行失败");
			result.error("新增失败");
		}
		return result;
	}

	public JsonResult update(TProduct product) {
		log.info("进入修改商品信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if (product.getId() == 0) {
			log.info("传入商品信息Id为空");
			return result.error("传入商品信息Id不可为空");
		} else {
			sql.where(TProduct.SQL_id);
		}
		if (product.getCateId() == 0) {
			log.info("商品分类为空");
			return result.error("未选择商品分类");
		} else {
			sql.addColumn(TProduct.SQL_cateId);
		}
		if (product.getBrandId() == 0) {
			log.info("商品品牌为空");
			return result.error("未选择商品品牌");
		} else {
			sql.addColumn(TProduct.SQL_brandId);
		}
		if (product.getImageId() == null) {
			log.info("素材图片为空");
			return result.error("未选择商品素材图片");
		} else {
			sql.addColumn(TProduct.SQL_imageId);
		}
		if (product.getTitle() == null || "".equals(product.getTitle())) {
			log.info("商品名称为空");
			return result.error("商品名称不可为空");
		} else {
			sql.addColumn(TProduct.SQL_title);
		}
		if (product.getPrice() == 0) {
			log.info("商品售价为空");
			return result.error("商品售价不可为0");
		} else {
			sql.addColumn(TProduct.SQL_price);
		}
		if (product.getCover() == null || "".equals(product.getCover())) {
			log.info("商品封面为空");
			return result.error("商品封面不可为空");
		} else {
			sql.addColumn(TProduct.SQL_cover);
		}
		if (product.getDescription() == null || "".equals(product.getDescription())) {
			log.info("商品描述简介为空");
			return result.error("商品描述简介不可为空");
		} else {
			sql.addColumn(TProduct.SQL_description);
		}
		if (product.getContent() == null || "".equals(product.getContent())) {
			log.info("商品图文详情为空");
			return result.error("商品图文详情不可为空");
		} else {
			sql.addColumn(TProduct.SQL_content);
		}
		SqlSelect stocksql = new SqlSelect().where(TProductAttribute.SQL_productId);
		ModelMap map = new ModelMap();
		map.put(TProductAttribute.PROP_productId, product.getId());
		List<TProductAttribute> attributes = tProductAttributeDao.listByMap(stocksql, map);
		if (ConstantTool.ISDELETE_YES.equals(product.getHasSize())) {
			for (TProductAttribute a : attributes) {
				if (a.getProductSize() == null || "".equals(a.getProductSize())) {
					log.info("该商品库存中存有未定义尺寸的库存");
					return result.error("该商品库存中存有未定义尺寸的库存，请先删除该库存之后在进行修改");
				}
			}
		} else {
			for (TProductAttribute a : attributes) {
				if (a.getProductSize() != null && !"".equals(a.getProductSize())) {
					log.info("该商品库已存有定义尺寸的库存");
					return result.error("该商品库已存有定义尺寸的库存，请先删除该库存之后在进行修改");
				}
			}
		}
		sql.addColumn(TProduct.SQL_hasSize);
		if (ConstantTool.ISDELETE_YES.equals(product.getHasColor())) {
			for (TProductAttribute a : attributes) {
				if (a.getProductColor() == null || "".equals(a.getProductColor())) {
					log.info("该商品库存中存有未定义颜色的库存");
					return result.error("该商品库存中存有未定义颜色的库存，请先删除该库存之后在进行修改");
				}
			}
		} else {
			for (TProductAttribute a : attributes) {
				if (a.getProductColor() != null && !"".equals(a.getProductColor())) {
					log.info("该商品库已存有定义颜色的库存");
					return result.error("该商品库已存有定义颜色的库存，请先删除该库存之后在进行修改");
				}
			}
		}
		sql.addColumn(TProduct.SQL_hasColor);
		int res = tProductDao.update(sql, product);
		if (res > 0) {
			log.info("修改商品信息接口执行成功");
			result.success("修改成功");
			result.setData(prdDao.getProductById(product.getId()));
		} else {
			log.info("修改商品信息接口执行失败");
			result.error("修改失败");
		}
		return result;
	}

	public JsonResult putawayProduct(ModelMap jsonInfo) {
		log.info("进入商品上架下架接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate();
		if (jsonInfo.get(TProduct.PROP_id) != null) {
			sql.where(TProduct.SQL_id);
		} else {
			log.info("商品ID为空");
			return result.error("商品ID不可为空");
		}
		if (jsonInfo.get(TProduct.PROP_putaway) != null) {
			sql.addColumn(TProduct.SQL_putaway);
		} else {
			log.info("商品上架状态为空");
			return result.error("商品上架状态为空");
		}
		int res = tProductDao.updateByMap(sql, jsonInfo);
		if (res > 0) {
			log.info("商品上架下架接口执行成功");
			result.setData(tProductDao.loadById((int) jsonInfo.get(TProduct.PROP_id)));
			if (ConstantTool.ISDELETE_YES.equals(jsonInfo.get(TProduct.PROP_putaway))) {
				result.success("商品上架成功");
			} else {
				result.success("商品下架成功");
			}
		} else {
			log.info("商品上架下架接口执行失败");
			result.error("执行失败");
		}
		return result;
	}

	public JsonResult getProductStockList(ModelMap jsonInfo) {
		log.info("进入获取商品库存列表接口");
		JsonResult result = new JsonResult();
		PageList<TProductAttribute> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect().where(TProductAttribute.SQL_productId);
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if (jsonInfo.get(TProductAttribute.PROP_productId) == null) {
			log.info("商品id为空");
			return result.error("商品id不可为空");
		}
		pagelist = tProductAttributeDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if (pagelist != null) {
			log.info("获取商品库存列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		} else {
			log.info("获取商品库存列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}

	public JsonResult addProductStock(TProductAttribute productAttribute) {
		log.info("进入新增商品库存接口");
		JsonResult result = new JsonResult();
		if (productAttribute.getProductId() == 0) {
			log.info("商品id为空");
			return result.error("商品id不可为空");
		}
		if (productAttribute.getStock() == 0) {
			log.info("商品库存为空");
			return result.error("商品库存不可为空");
		}
		TProduct product = tProductDao.loadById(productAttribute.getProductId());
		if (ConstantTool.ISDELETE_YES.equals(product.getHasColor())) {
			if (productAttribute.getProductColor() == null) {
				log.info("商品库存颜色为空");
				return result.error("商品库存颜色不可为空");
			}
		} else {
			if (productAttribute.getProductColor() != null) {
				log.info("商品库存颜色不为空");
				return result.error("商品库存颜色必须为空");
			}
		}
		if (ConstantTool.ISDELETE_YES.equals(product.getHasSize())) {
			if (productAttribute.getProductSize() == null) {
				log.info("商品库存尺码为空");
				return result.error("商品库存尺码不可为空");
			}
		} else {
			if (productAttribute.getProductSize() != null) {
				log.info("商品库存尺码不为空");
				return result.error("商品库存尺码必须为空");
			}
		}

		int stockId = tProductAttributeDao.insert(productAttribute);
		if (stockId > 0) {
			log.info("新增商品库存接口执行成功");
			result.success("新增成功");
			result.setData(tProductAttributeDao.loadById(stockId));
		} else {
			log.info("新增商品库存接口执行失败");
			result.error("新增失败");
		}
		return result;
	}

	public JsonResult updateProductStock(TProductAttribute productAttribute) {
		log.info("进入修改商品库存接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().where(TProductAttribute.SQL_id);
		if (productAttribute.getStock() == 0) {
			log.info("商品库存为空");
			return result.error("商品库存不可为空");
		} else {
			sql.addColumn(TProductAttribute.SQL_stock);
		}
		int res = tProductAttributeDao.update(sql, productAttribute);
		if (res > 0) {
			log.info("修改商品库存接口执行成功");
			result.success("修改成功");
			result.setData(tProductAttributeDao.loadById(productAttribute.getId()));
		} else {
			log.info("修改商品库存接口执行失败");
			result.error("修改失败");
		}
		return result;
	}

	public JsonResult delProductStock(int id) {
		log.info("进入删除商品库存接口");
		JsonResult result = new JsonResult();
		int res = tProductAttributeDao.deleteById(id);
		if (res > 0) {
			log.info("删除商品库存接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除商品库存接口执行失败");
			result.error("删除失败");
		}
		return result;
	}

	public JsonResult delProductStockByPrdId(int id) {
		log.info("进入商品ID删除库存接口");
		JsonResult result = new JsonResult();
		SqlDelete sql = new SqlDelete().where(TProductAttribute.SQL_productId);
		TProductAttribute productAttribute = new TProductAttribute().setProductId(id);
		int res = tProductAttributeDao.delete(sql, productAttribute);
		if (res > 0) {
			log.info("商品ID删除库存接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除商品库存接口执行失败");
			result.error("删除失败");
		}
		return result;
	}

}
