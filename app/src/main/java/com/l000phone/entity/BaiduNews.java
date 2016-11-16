package com.l000phone.entity;

/**
 * Created by Administrator on 2016/10/28.
 */

public class BaiduNews {
	private String title;//标题
	private String link;//链接
	private String pubDate;//时间
	private String author;//来源作者
	private String src;//图片
	private String description;//描述
	private int type;//类型

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getPubDate() {
		return pubDate;
	}

	public String getAuthor() {
		return author;
	}

	public String getSrc() {
		return src;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
