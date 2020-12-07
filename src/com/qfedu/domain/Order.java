/** 
* 订单表
*/
package com.qfedu.domain;
public class Order {
	private String id;
	private int uid;
	private int uaid;
	private String createtime;
	private double money;
	private int flag;
	private String username;
	private String address;//涓嶆槸鏁版嵁搴撶殑瀛楁  璁板綍鏀惰揣鍦板潃 鏀惰揣浜�+鎵嬫満鍙�+鏀惰揣鍦板潃
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getUaid() {
		return uaid;
	}
	public void setUaid(int uaid) {
		this.uaid = uaid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", uaid=" + uaid + ", createtime=" + createtime + ", money=" + money
				+ ", flag=" + flag + ", username=" + username + ", address=" + address + "]";
	}
	
}