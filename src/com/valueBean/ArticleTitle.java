package com.valueBean;

public class ArticleTitle {
	String title;
	String author;
	String pubtime;
	String url;
	public ArticleTitle(String title, String author, String pubtime, String url) {
		this.title = title;
		this.author = author;
		this.pubtime = pubtime;
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
