package com.yunqi.jhf.vo;

/** 
 * @author Seek
 * 
 */
public class ShoppingCartDetailBean {
	private int id;   					//主键ID
	
	private int shoppingcartId;			//购物车ID
	
	private int shoppingcartDetailId; 	//购物车详情ID
	
	private int productId; 				//产品Id

	private String productTitle; 		// 产品名称
	
	private String productCover;		//产品封面
	
	private int productNum;			// 产品数量

	private int productPrice; 		// 产品价格
	
	private String productColor; 		// 产品颜色
	
	private String productSize; 		// 产品尺码

	
	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShoppingcartId() {
		return shoppingcartId;
	}

	public void setShoppingcartId(int shoppingcartId) {
		this.shoppingcartId = shoppingcartId;
	}

	public int getShoppingcartDetailId() {
		return shoppingcartDetailId;
	}

	public void setShoppingcartDetailId(int shoppingcartDetailId) {
		this.shoppingcartDetailId = shoppingcartDetailId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}

	

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	
}
