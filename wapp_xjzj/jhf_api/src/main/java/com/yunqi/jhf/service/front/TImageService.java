package com.yunqi.jhf.service.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.jhf.dao.domain.TImage;
import com.yunqi.jhf.dao.persistence.TImageDao;
import com.yunqi.jhf.service.background.OrderDetailService;
import com.yunqi.jhf.web.SplitStrUtil;

@Service
public class TImageService {

	@Resource
	private TImageDao tImgDao;
	protected Logger logger = Logger.getLogger(OrderDetailService.class);

	/**
	 * 根据 图片id 查找 图片
	 * 
	 * @return img
	 */
	public TImage getImgByimgId(int imgId) {
		TImage img = tImgDao.loadById(imgId);
		return img;
	}

	/**
	 * 根据多个图片id查找所有图片
	 * 
	 * @param id="id,id,id,id"
	 * @return imgList
	 */
	public List<TImage> getAllImg(String imgId) {
		List<TImage> imgSrcList = new ArrayList<TImage>();
		try {
			if (imgId != null) {
				List<Integer> imgIdList = SplitStrUtil.splitString(imgId);
				for (int imgid : imgIdList) {
					TImage img = tImgDao.loadById(imgid);
					imgSrcList.add(img);
				}
			} else {
				logger.info("获取所有图片失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return imgSrcList;
	}

}
