package com.yunqi.jhf.service.background;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.ImageDao;
import com.yunqi.jhf.dao.domain.TImage;
import com.yunqi.jhf.dao.domain.TImgCate;
import com.yunqi.jhf.dao.persistence.TImageDao;
import com.yunqi.jhf.dao.persistence.TImgCateDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.ImageInfoBean;

@Service
public class ImageService {

	protected static Logger log = Logger.getLogger(ImageService.class);

	// 服务器环境
	public static String UPLOAD_BASE_PATH = "/home/wapp/upload/";

	// 本地调试
	// public static String UPLOAD_BASE_PATH = "D:/image";

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private TImageDao tImgDao;

	@Autowired
	private TImgCateDao tImgCateDao;

	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取图片信息列表");
		JsonResult result = new JsonResult();
		PageList<ImageInfoBean> pagelist = new PageList<>();
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		} else {
			pagelist.setCurrentPage(1);
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		}
		if (jsonInfo.get(TImage.PROP_imgCateId) != null) {
			List<TImgCate> imgCate = tImgCateDao.list(null, null);
			List<Integer> ids = new ArrayList<>();
			Integer cateId = (Integer) jsonInfo.get(TImage.PROP_imgCateId);
			ids.add(cateId);
			jsonInfo.put(TImage.PROP_imgCateId, getChildId(imgCate, cateId, ids));
		}
		List<ImageInfoBean> images = imageDao.getImageList(jsonInfo);
		pagelist.setTotalSize(imageDao.getImageCount(jsonInfo));
		pagelist.setList(images);
		if (images != null) {
			log.info("获取图片信息列表接口执行成功");
			result.setData(pagelist);
			result.success("获取成功");
		} else {
			log.info("获取图片信息列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}

	public JsonResult getList() {
		return new SuccessResult().setData(tImgCateDao.list(null, null));
	}

	public JsonResult create(TImage image) {
		log.info("进入新增图片信息接口");
		JsonResult result = new JsonResult();
		if (image.getTitle() == null || "".equals(image.getTitle().trim())) {
			return result.error("图片名称不可为空");
		}
		if (image.getImgSrc() == null || "".equals(image.getImgSrc())) {
			return result.error("图片未上传,请上传图片");
		}
		if (image.getImgCateId() == 0) {
			return result.error("图片分类不可为空");
		}
		String[] src = image.getImgSrc().split(",");
		String[] name = image.getTitle().split(",");
		List<TImage> list = new ArrayList<>();
		for(int i = 0; i < src.length; i++) {
			TImage ti = new TImage();
			ti.setTitle(name[i]);
			ti.setImgCateId(image.getImgCateId());
			ti.setImgSrc(src[i]);
			list.add(ti);
		}
		int res = imageDao.addImageList(list);
		if (res > 0) {
			log.info("新增图片接口执行完成");
			result.success("新增成功");
		} else {
			log.info("新增图片接口执行失败");
			result.success("新增失败");
		}
		return result;
	}
	
	public JsonResult delete(int imageId) {
		log.info("进入图片删除接口");
		JsonResult result = new JsonResult();
		int res = tImgDao.deleteById(imageId);
		if(res > 0) {
			log.info("删除图片接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除图片接口执行失败");
			result.success("删除失败");
		}
		return result;
	}

	public JsonResult update(ModelMap jsonInfo) {
		log.info("进入图片信息修改接口");
		JsonResult result = new JsonResult();
		SqlUpdate update = new SqlUpdate().where(TImage.SQL_id).addColumn("update_time = now()");
		if (jsonInfo.get(TImage.PROP_title) != null) {
			update.addColumn(TImage.SQL_title);
		}
		if (jsonInfo.get(TImage.PROP_imgCateId) != null) {
			update.addColumn(TImage.SQL_imgCateId);
		}
		if (jsonInfo.get(TImage.PROP_imgSrc) != null) {
			TImage img = tImgDao.loadById((int) jsonInfo.get(TImage.PROP_id));
			File file = new File(img.getImgSrc());
			if (file.exists()) {
				file.delete();
			}
			jsonInfo.put(TImage.PROP_imgSrc,jsonInfo.get(TImage.PROP_imgSrc));
			update.addColumn(TImage.SQL_imgSrc);
		}
		int res = tImgDao.updateByMap(update, jsonInfo);
		if (res > 0) {
			log.info("修改图片接口执行成功");
			result.success("修改成功");
			result.setData(tImgDao.loadById((int) jsonInfo.get(TImage.PROP_id)));
		} else {
			log.info("修改图片接口执行失败");
			result.success("修改失败");
		}
		return result;
	}

	public JsonResult upload(CommonsMultipartFile file, HttpServletRequest request) {
		log.info("进入上传图片接口");
		JsonResult result = new JsonResult();
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1,
					file.getOriginalFilename().length());
			String fileId = UUID.randomUUID().toString();
			String fileName = fileId + "." + suffix;
			// 获取系统的临时目录
			String folder = UPLOAD_BASE_PATH;
			// 获取输出流
			File folderFile = new File(folder);
			if (!folderFile.exists()) {
				folderFile.mkdirs();
			}
			OutputStream os = new FileOutputStream(folder + fileName);
			// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
			InputStream is = file.getInputStream();
			int temp;
			// 一个一个字节的读取并写入
			while ((temp = is.read()) != (-1)) {
				os.write(temp);
			}
			os.flush();
			os.close();
			is.close();
			File f = new File(folder + fileName);
			if (f.exists()) {
				result.setData("/image/" + fileName);
				result.success("上传成功");
				log.info("上传文件接口执行完成");
			} else {
				result.error("上传失败");
				return result;
			}
		} catch (IOException e) {
			result.error("上传失败");
			e.printStackTrace();
			log.error(e);
		}
		return result;
	}

	private List<Integer> getChildId(List<TImgCate> imgCate, int fid, List<Integer> ids) {
		for (TImgCate t : imgCate) {
			if (t.getParentId() == fid) {
				ids.add(t.getId());
				ids = getChildId(imgCate, t.getId(), ids);
			}
		}
		return ids;
	}

}
