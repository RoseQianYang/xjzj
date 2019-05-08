package com.yunqi.jhf.service.front;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.dao.domain.TUserAddress;
import com.yunqi.jhf.dao.persistence.TOrderDao;
import com.yunqi.jhf.dao.persistence.TUserAddressDao;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;


@Service
public class TUserAddressService {

	private static final Logger log = Logger.getLogger(TUserAddressService.class);

	@Resource
	private TUserAddressDao tUserAddressDao;
	
	@Resource
	private TOrderDao tOrderDao;
	
	/**
	 * 通过userId获取用户的默认地址
	 * @param userId
	 * @return list
	 */
	public TUserAddress getUserAddressDefault(int userId) {
		
		TUserAddress userDefaultAddress = new TUserAddress();
		JsonResult result = new JsonResult();
		SqlSelect sqlSelect = new SqlSelect().where(TUserAddress.SQL_userId).and(TUserAddress.SQL_isDefault);
		try {
			TUserAddress useraddress = new TUserAddress();
			if (userId != 0) {
				useraddress.setUserId(userId);
				useraddress.setIsDefault(ConstantTool.ISDELETE_YES);
			}else {
				log.error("获取用户id失败");
			}
			userDefaultAddress=tUserAddressDao.load(sqlSelect, useraddress);
	 	    
		} catch (Exception e) {
			result.error("获取用户默认地址失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return userDefaultAddress;
    }  
	
	
	/**
	 * 通过userId获取用户地址列表
	 * @param userId
	 * @return list
	 */
	public List<TUserAddress> getUserAddressList(int userId) {
		
		JsonResult result = new JsonResult();
		TUserAddress useraddress = new TUserAddress();
		SqlSelect sqlSelect = new SqlSelect().where(TUserAddress.SQL_userId).orderBy(" order by create_time asc");
		List<TUserAddress> useraddressList = null;
		try {
			if (userId != 0) {
				useraddress.setUserId(userId);
			}else {
				log.error("获取用户id失败");
			}
			useraddressList = tUserAddressDao.list(sqlSelect, useraddress);
	 	    
		} catch (Exception e) {
			result.error("获取用户地址列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return useraddressList;
    }  
	
	/**
	 * 通过addressId获取地址
	 * @param addressId
	 * @return TUserAddress
	 */
	public TUserAddress getAddressById(int addressId) {
		TUserAddress address = new TUserAddress();
		try {
			if (addressId != -1) {
				address = tUserAddressDao.loadById(addressId);
			}else {
				log.error("获取地址id失败");
			}
		} catch (Exception e) {
			log.error("获取地址失败");
			e.printStackTrace();
		}
        return address;
    }  
	
	
	/**
	 * 通过orderId获取用户地址列表
	 * @param orderId
	 * @return TUserAddress
	 */
	public TUserAddress getUserAddressByOid(int orderId) {
		
		JsonResult result = new JsonResult();
		TOrder order = new TOrder();
		TUserAddress useraddress = new TUserAddress();
		
		try {
			if (orderId != -1) {
				order = tOrderDao.loadById(orderId);
				int useraddressId = order.getUserAddressId();
				useraddress = tUserAddressDao.loadById(useraddressId);
			}else {
				log.error("获取订单id失败");
			}
		} catch (Exception e) {
			result.error("获取用户地址列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return useraddress;
    }  
	
	/**
	 * 加入用户地址
	 * @param userId  consignee address addressDetail isDefault mobile postcode
	 * @return result
	 */
	public JsonResult insertUserAdd(int userId,String consignee,String address,
			String addressDetail,int isDefault,String mobile,String postcode) {
		JsonResult result = new JsonResult();
		try{
			if(userId!=-1){
				TUserAddress useraddress = new TUserAddress();
				useraddress.setUserId(userId);
				if (consignee!=null||"".equals(consignee)) {
					useraddress.setConsignee(consignee);
				} else {
					log.error("收货人不能为空");
					result.error("收货人不能为空");
				}
				if (address!=null||"".equals(address)) {
					useraddress.setAddress(address);
				} else {
					log.error("收货地址不能为空");
					result.error("收货地址不能为空");
				}
				if (addressDetail!=null||"".equals(addressDetail)) {
					useraddress.setAddressDetail(addressDetail);
				} else {
					log.error("收货详情地址不能为空");
					result.error("收货详情地址不能为空");
				}
				if (mobile!=null||"".equals(mobile)) {
					useraddress.setMobile(mobile); 
				} else {
					log.error("联系方式不能为空");
					result.error("联系方式不能为空");
				}
				/*if (postcode!=null||"".equals(postcode)) {
					useraddress.setPostcode(postcode);
				} else {
					log.error("邮编不能为空");
					result.error("邮编不能为空");
				}*/
				
				SqlSelect sqlselect=new SqlSelect().where(TUserAddress.SQL_userId).and(TUserAddress.SQL_consignee)
						.and(TUserAddress.SQL_address).and(TUserAddress.SQL_addressDetail).and(TUserAddress.SQL_mobile);
						// .and(TUserAddress.SQL_postcode);
				TUserAddress tua = tUserAddressDao.load(sqlselect, useraddress);
				
				if (isDefault != -1) {
					//查询用户默认地址
					TUserAddress userAddress = new TUserAddress();
					userAddress.setUserId(userId);
					userAddress.setIsDefault(ConstantTool.ISDELETE_YES);
					SqlSelect sqlSelect = new SqlSelect().where(TUserAddress.SQL_userId).and(TUserAddress.SQL_isDefault);
					TUserAddress useraddressByUorY = tUserAddressDao.load(sqlSelect, userAddress);
					
					if (useraddressByUorY != null) {
						if (isDefault != useraddressByUorY.getId()) {
							//若默认地址不是同一个，先改变前一个默认地址为N
							TUserAddress useraddressN = new TUserAddress();
							useraddressN.setIsDefault(ConstantTool.ISDELETE_NO);
							useraddressN.setUserId(userId);
							int userAddressId = useraddressByUorY.getId();
							useraddressN.setId(userAddressId);
							tUserAddressDao.update(new SqlUpdate().where(TUserAddress.SQL_id).addColumn(TUserAddress.SQL_isDefault).addColumn("update_time = NOW()"), useraddressN);
							
							//再将这次选择的地址添加进去
							if (tua!=null) {
								log.error("这个地址已经存在");
								result.error("已有此地址");
							} else {
								useraddress.setIsDefault(ConstantTool.ISDELETE_YES);
								int useraddId = tUserAddressDao.insert(useraddress);
								if (useraddId>0) {
									log.info("用户地址添加成功");
									result.success("地址添加成功");
								}
							}
						} else {
							//再将这次选择的地址添加进去
							if (tua!=null) {
								log.error("这个地址已经存在");
								result.error("已有此地址");
							} else {
								useraddress.setIsDefault(ConstantTool.ISDELETE_YES);
								int useraddId = tUserAddressDao.insert(useraddress);
								if (useraddId>0) {
									log.info("用户地址添加成功");
									result.success("地址添加成功");
								}
							}
						}
					} else {
						if (tua!=null) {
							log.error("这个地址已经存在");
							result.error("已有此地址");
						} else {
							useraddress.setIsDefault(ConstantTool.ISDELETE_YES);
							int useraddId = tUserAddressDao.insert(useraddress);
							if (useraddId>0) {
								log.info("用户地址添加成功");
								result.success("地址添加成功");
							}
						}
					}
				} else {
					if (tua!=null) {
						log.error("这个地址已经存在");
						result.error("已有此地址");
					} else {
						useraddress.setIsDefault(ConstantTool.ISDELETE_NO);
						int useraddId = tUserAddressDao.insert(useraddress);
						if (useraddId>0) {
							log.info("用户地址添加成功");
							result.success("地址添加成功");
						}
					}
				}
			}
		}catch(Exception e){
				result.error("添加地址失败");
				log.error(e.getMessage(), e);
				e.printStackTrace();
		 }
		return result;
	}
	
	
	/**
	 * 修改用户地址基本信息
	 */
	public JsonResult updateUserAddress(int userId,int useraddressId,String consignee,String address,
			String addressDetail,int isDefault,String mobile,String postcode) {
		JsonResult result = new JsonResult();
		TUserAddress userAddress = new TUserAddress();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(useraddressId == 0) {
			log.info("传入用户地址信息Id为空");
			return result.error("传入用户地址信息Id不可为空");
		}else {
			userAddress.setUserId(userId);
			userAddress.setId(useraddressId);
			sql.where(TUserAddress.SQL_id);
		}
		if (consignee!=null||"".equals(consignee)) {
			userAddress.setConsignee(consignee);
			sql.addColumn(TUserAddress.SQL_consignee);
		} else {
			log.error("收货人不能为空");
			result.error("收货人不能为空");
		}
		if (address!=null||"".equals(address)) {
			userAddress.setAddress(address);
			sql.addColumn(TUserAddress.SQL_address);
		} else {
			log.error("收货地址不能为空");
			result.error("收货地址不能为空");
		}
		if (addressDetail!=null||"".equals(addressDetail)) {
			userAddress.setAddressDetail(addressDetail);
			sql.addColumn(TUserAddress.SQL_addressDetail);
		} else {
			log.error("收货详情地址不能为空");
			result.error("收货详情地址不能为空");
		}
		if (mobile!=null||"".equals(mobile)) {
			userAddress.setMobile(mobile);
			sql.addColumn(TUserAddress.SQL_mobile);
		} else {
			log.error("联系方式不能为空");
			result.error("联系方式不能为空");
		}
		if (postcode!=null||"".equals(postcode)) {
			userAddress.setPostcode(postcode);
			sql.addColumn(TUserAddress.SQL_postcode);
		} else {
			log.error("邮编不能为空");
			result.error("邮编不能为空");
		}
		
		if (isDefault != -1) {
			//查询用户默认地址
			TUserAddress useraddress = new TUserAddress();
			useraddress.setUserId(userId);
			useraddress.setIsDefault(ConstantTool.ISDELETE_YES);
			SqlSelect sqlSelect = new SqlSelect().where(TUserAddress.SQL_userId).and(TUserAddress.SQL_isDefault);
			TUserAddress useraddressByUorY = tUserAddressDao.load(sqlSelect, useraddress);
			
			if (useraddressByUorY != null) {
				if (isDefault != useraddressByUorY.getId()) {
					//若默认地址不是同一个，先改变前一个默认地址为N
					TUserAddress useraddressN = new TUserAddress();
					useraddressN.setIsDefault(ConstantTool.ISDELETE_NO);
					useraddressN.setUserId(userId);
					int userAddressId = useraddressByUorY.getId();
					useraddressN.setId(userAddressId);
					tUserAddressDao.update(new SqlUpdate().where(TUserAddress.SQL_id).addColumn(TUserAddress.SQL_isDefault).addColumn("update_time = NOW()"), useraddressN);
					
					//再将这次选择的默认地址设为Y
					userAddress.setIsDefault(ConstantTool.ISDELETE_YES);
					sql.addColumn(TUserAddress.SQL_isDefault);
					int res = tUserAddressDao.update(sql, userAddress);
					if(res > 0) {
						log.info("修改用户地址信息接口执行成功");
						result.success("修改成功");
						result.setData(tUserAddressDao.loadById(useraddressId));
					}else {
						log.info("修改用户地址信息接口执行失败");
						result.error("修改失败");
					}
				} else {
					//若默认地址没有，直接改变这个为默认地址
					userAddress.setIsDefault(ConstantTool.ISDELETE_YES);
					sql.addColumn(TUserAddress.SQL_isDefault);
					int res = tUserAddressDao.update(sql, userAddress);
					if(res > 0) {
						log.info("修改用户地址信息接口执行成功");
						result.success("修改成功");
						result.setData(tUserAddressDao.loadById(useraddressId));
					}else {
						log.info("修改用户地址信息接口执行失败");
						result.error("修改失败");
					}
				}
			} else {
				//若默认地址没有，直接改变这个为默认地址
				userAddress.setIsDefault(ConstantTool.ISDELETE_YES);
				sql.addColumn(TUserAddress.SQL_isDefault);
				int res = tUserAddressDao.update(sql, userAddress);
				if(res > 0) {
					log.info("修改用户地址信息接口执行成功");
					result.success("修改成功");
					result.setData(tUserAddressDao.loadById(useraddressId));
				}else {
					log.info("修改用户地址信息接口执行失败");
					result.error("修改失败");
				}
			}
		} else {
			//将这次修改的地址设为N
			userAddress.setIsDefault(ConstantTool.ISDELETE_NO);
			sql.addColumn(TUserAddress.SQL_isDefault);
			int res = tUserAddressDao.update(sql, userAddress);
			if(res > 0) {
				log.info("修改用户地址信息接口执行成功");
				result.success("修改成功");
				result.setData(tUserAddressDao.loadById(useraddressId));
			}else {
				log.info("修改用户地址信息接口执行失败");
				result.error("修改失败");
			}
		}
		return result;
	}
	
	/**
	 * 删除用户地址
	 */
	public JsonResult delete(int useraddressId) {
		JsonResult result = new JsonResult();
		TUserAddress tUserAddress = tUserAddressDao.loadById(useraddressId);
		if (tUserAddress == null) {
			log.info("用户地址不存在或已删除");
			result.error("删除失败");
		}
		int res = tUserAddressDao.deleteById(useraddressId);
		if(res > 0) {
			log.info("后台删除用户地址执行成功");
			result.success("删除成功");
		}else {
			log.info("后台删除用户地址执行失败");
			result.error("删除失败");
		}
		return result;
	}
}
