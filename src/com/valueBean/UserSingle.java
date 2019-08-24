package com.valueBean;

public class UserSingle {
	private int id;
	private String userName;
	private String userPswd;
	private String aginPswd;
	private String userIco;
	private String userMotto;
	private String userBlogName;
	private String userCTTime;
	private int userHitNum;
	
	public UserSingle(){
		userName="";
		userPswd="";
		aginPswd="";
		userIco="";
		userMotto="";
		userBlogName="";
		userHitNum=0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPswd() {
		return userPswd;
	}
	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}	
	public String getAginPswd() {
		return aginPswd;
	}
	public void setAginPswd(String aginPswd) {
		this.aginPswd = aginPswd;
	}
	public String getUserIco() {
		return userIco;
	}
	public void setUserIco(String userIco) {
		this.userIco = userIco;
	}
	public String getUserMotto() {
		return userMotto;
	}
	public void setUserMotto(String userMotto) {
		this.userMotto = userMotto;
	}
	
	public String getUserBlogName() {
		return userBlogName;
	}
	public String getCutUserBlogName() {
		int len=14;
		if(len>=userBlogName.length()||len<=0)
			return userBlogName;
		else{
			return (userBlogName.substring(0,len)+"..");
		}
	}
	public void setUserBlogName(String userBlogName) {
		this.userBlogName = userBlogName;
	}
	public String getUserCTTime() {
		return userCTTime;
	}
	public void setUserCTTime(String userCTTime) {
		this.userCTTime = userCTTime;
	}
	public int getUserHitNum() {
		return userHitNum;
	}
	public void setUserHitNum(int userHitNum) {
		this.userHitNum = userHitNum;
	}
}
