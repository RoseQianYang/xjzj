package com.yunqi.jhf.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.yunqi.common.ServiceException;
import com.yunqi.common.imgfile.ImageFile;
import com.yunqi.common.imgfile.IndexGenerator;
import com.yunqi.common.util.Util;
import com.yunqi.common.util.Validator;
import com.yunqi.jhf.web.StrUtil;

@Service
public class ImageFileService {

	private final static Logger log = Logger.getLogger(ImageFileService.class);

	public String uploadImageFile(MultipartFile mutpFile, String baseDir) throws Exception {

		Date d = new Date();
		// provide meta data
		String year = Util.getYear(d);
		String month = Util.getMonth(d);
		String day = Util.getDay(d);
		String index = IndexGenerator.getIndexStr();
		String random = Util.randomStr(3);

		// usefule data

		String orgFilename = mutpFile.getOriginalFilename();
		String subDir = "/" + year + "/" + month + day;
		String filename = index + "_" + random + "." + StrUtil.getFileExt(orgFilename);
		String imageUri = year + "/" + month + day + "/" + filename;
		String fullFilename = baseDir + subDir + "/" + filename;

		if (StringUtils.isEmpty(orgFilename)) {
			throw new ServiceException("文件名不能为空");
		}
		if (!Validator.isValidImage(orgFilename)) {
			throw new ServiceException("文件类型暂不支持");
		}

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		String imageType = StrUtil.getFileExt(filename);
		File file = new File(
				baseDir + "/temp_" + System.currentTimeMillis() + "_" + IndexGenerator.getIndexStr() + ".tmp");
		mutpFile.transferTo(file);
		System.out.println(file.length());
		if (file.length() > 1024*1024*10) {
			throw new ServiceException("上传文件大小不能超过10M");
		}

		// make sure dir is exist!
		File fullDir = new File(baseDir + subDir);
		if (!fullDir.exists()) {
			fullDir.mkdirs();
		}

		try {
			BufferedImage src = ImageIO.read(file);
			int srcWidth = src.getWidth();
			int srcHeight = src.getHeight();

			boolean isSquare = srcWidth == srcHeight;
			boolean isVertical = srcWidth < srcHeight;

			// 75 * 75, crop & resize
			String fullSquareFile = StrUtil.pathSquare(fullFilename);
			// 100 * 100, resize
			String fullSmallFile = StrUtil.pathSmall(fullFilename);
			// 480 * 480, resize
			String fullMiddleFile = StrUtil.pathMiddle(fullFilename);
			// origin image
			String fullOrgFile = StrUtil.pathOrg(fullFilename);

			// process origin image
			FileUtils.copyFile(file, new File(fullOrgFile));

			// process square image
			BufferedImage croped = null;
			if (isSquare) {
				croped = src;
			} else {
				if (isVertical) {
					int tmp = (srcHeight - srcWidth) / 2;
					croped = Scalr.crop(src, 0, tmp, srcWidth, srcWidth);
				} else {
					int tmp = (srcWidth - srcHeight) / 2;
					croped = Scalr.crop(src, tmp, 0, srcHeight, srcHeight);
				}
			}
			BufferedImage square = null;
			if (srcWidth <= 75 && srcHeight <= 75) {
				square = croped;
			} else {
				square = Scalr.resize(croped, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 75, 75, Scalr.OP_ANTIALIAS);
			}
			ImageIO.write(square, imageType, new File(fullSquareFile));

			// process small image 100*100
			if (srcWidth <= 180 && srcHeight <= 180) {
				FileUtils.copyFile(file, new File(fullSmallFile));
			} else {
				BufferedImage small = Scalr.resize(src, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 180, 180,
						Scalr.OP_ANTIALIAS);
				ImageIO.write(small, imageType, new File(fullSmallFile));
			}

			// process middle image 480*480
			if (srcWidth <= 480 && srcHeight <= 480) {
				FileUtils.copyFile(file, new File(fullMiddleFile));
			} else {
				BufferedImage middle = Scalr.resize(src, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 480, 480,
						Scalr.OP_ANTIALIAS);
				ImageIO.write(middle, imageType, new File(fullMiddleFile));
			}
			log.info("uploaded and processed image file: " + fullFilename);
			return imageUri;
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally {
			try {
				file.delete();
			} catch (Exception e) {
			}
		}

	}

	// String baseDir = config.getValue(Config.CONFIG_KEY_UPLOAD_BASE_DIR);
	public void deleteImageFile(String baseDir, ImageFile imageFile) throws Exception {

		if (imageFile == null) {
			throw new Exception("input image file is null!");
		}
		String fullFilename = baseDir + imageFile.getUri();
		// 75 * 75, crop & resize
		String fullSquareFile = Util.pathSquare(fullFilename);
		// 240 * 240, resize
		String fullSmallFile = Util.pathSmall(fullFilename);
		// 500 * 500, resize
		String fullMiddleFile = Util.pathMiddle(fullFilename);
		// origin image
		String fullOrgFile = Util.pathOrg(fullFilename);

		try {
			File squareFile = new File(fullSquareFile);
			squareFile.delete();
		} catch (Exception e) {
		}
		try {
			File smallFile = new File(fullSmallFile);
			smallFile.delete();
		} catch (Exception e) {
		}
		try {
			File middleFile = new File(fullMiddleFile);
			middleFile.delete();
		} catch (Exception e) {
		}
		try {
			File orgFile = new File(fullOrgFile);
			orgFile.delete();
		} catch (Exception e) {
		}

	}

}
