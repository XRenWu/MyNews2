package com.l000phone.entity;

/**
 * Created by Administrator on 2016/10/31.
 */

public class User {
	private int _id;//编号
	private String name;//用户名
	private String passward;//密码

	public int get_id() {
		return _id;
	}

	public String getName() {
		return name;
	}

	public String getPassward() {
		return passward;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public User(int _id, String name, String passward) {
		this._id = _id;
		this.name = name;
		this.passward = passward;
	}
}
