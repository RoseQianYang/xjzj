package com.yunqi.jhf.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.dao.domain.TSysConfig;
import com.yunqi.jhf.dao.persistence.TSysConfigDao;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class SysConfigService {

	protected static Logger log = Logger.getLogger(SysConfigService.class);

	// 积分返现 比例
	public final static String CONFIG_KEY_RETURN_THREE = "config.key.return.three"; // 三级（本级）
	public final static String CONFIG_KEY_RETURN_TWO = "config.key.return.two"; // 二级
	public final static String CONFIG_KEY_RETURN_ONE = "config.key.return.one"; // 一级
	public final static String CONFIG_KEY_RETURN_TAXES = "config.key.return.taxes"; // 财富提现税费 

	@Autowired
	private TSysConfigDao tSysConfigDao;

	/**
	 * 获取财富提现税费配置参数
	 * @return
	 */
	public double getTaxes() {
		TSysConfig s = tSysConfigDao.loadById(CONFIG_KEY_RETURN_TAXES);
		if (s == null) {
			return 0.0;
		}
		if (StringUtils.isEmpty(s.getConfigValue())) {
			return 0.0;
		}
		try {
			double d = Double.parseDouble(s.getConfigValue());
			return d;
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	
	/**
	 * 获取3级返现配置参数
	 * 
	 * @return
	 */
	public double getIntegral3() {
		TSysConfig s = tSysConfigDao.loadById(CONFIG_KEY_RETURN_THREE);
		if (s == null) {
			return 0.0;
		}
		if (StringUtils.isEmpty(s.getConfigValue())) {
			return 0.0;
		}
		try {
			double d = Double.parseDouble(s.getConfigValue());
			return d;
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * 获取2级返现配置参数 、 3级返现配置参数 、财富提现税费配置参数修改
	 * 
	 * @return
	 */
	public double getIntegral2() {
		TSysConfig s = tSysConfigDao.loadById(CONFIG_KEY_RETURN_TWO);
		if (s == null) {
			return 0.0;
		}
		if (StringUtils.isEmpty(s.getConfigValue())) {
			return 0.0;
		}
		try {
			double d = Double.parseDouble(s.getConfigValue());
			return d;
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	
	/**
	 * 获取1级返现配置参数
	 * 
	 * @return
	 */
	public double getIntegral1() {
		TSysConfig s = tSysConfigDao.loadById(CONFIG_KEY_RETURN_ONE);
		if (s == null) {
			return 0.0;
		}
		if (StringUtils.isEmpty(s.getConfigValue())) {
			return 0.0;
		}
		try {
			double d = Double.parseDouble(s.getConfigValue());
			return d;
		} catch (Exception e) {
			return 0.0;
		}
	}


	public List<TSysConfig> getConfigList() {
		return tSysConfigDao.list(null, null);
	}

	/**
	 * 2级返现配置参数
	 * @param jsonMap
	 */
	public void saveAll(ModelMap jsonMap) {
		TSysConfig tsc = new TSysConfig();
		String configKey = (String) jsonMap.get(TSysConfig.PROP_configKey);
		String configValue = (String) jsonMap.get(TSysConfig.PROP_configValue);
		if (CONFIG_KEY_RETURN_TWO.equals(configKey)) {
			tsc.setConfigKey(CONFIG_KEY_RETURN_TWO);
			tsc.setConfigValue(configValue);
			save(tsc);
		}else if (CONFIG_KEY_RETURN_THREE.equals(configKey)) {
			tsc.setConfigKey(CONFIG_KEY_RETURN_THREE);
			tsc.setConfigValue(configValue);
			save(tsc);
		}else if (CONFIG_KEY_RETURN_TAXES.equals(configKey)) {
			tsc.setConfigKey(CONFIG_KEY_RETURN_TAXES);
			tsc.setConfigValue(configValue);
			save(tsc);
		}else if (CONFIG_KEY_RETURN_ONE.equals(configKey)) {
			tsc.setConfigKey(CONFIG_KEY_RETURN_ONE);
			tsc.setConfigValue(configValue);
			save(tsc);
		}
	}

	public void save(TSysConfig sysConfig) {
		if (sysConfig == null) {
			return;
		}
		TSysConfig tsc = tSysConfigDao.loadById(sysConfig.getConfigKey());
		if (tsc == null) {
			tSysConfigDao.insert(tsc);
			log.info("新增");
		} else {
			tSysConfigDao.update(new SqlUpdate().addColumn(TSysConfig.SQL_configValue).where(TSysConfig.SQL_configKey), sysConfig);
		}
	}

}
