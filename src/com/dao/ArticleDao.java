package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toolsBean.Change;
import com.toolsBean.DB;
import com.valueBean.ArticleSingle;

public class ArticleDao extends SuperDao{
	/**
	 * @功能：获取在个人主页中显示的文章
//	 */
	public List getDefaultArticle(int id) throws SQLException{
		String sql="select top 4 * from tb_article where art_whoId=? order by art_pubTime desc";
		Object[] params={id};		
		List articlelist=getList(sql,params);		
		return articlelist;
	}
	/**
	 * @功能：获取当前页显示的所有文章
	 */
	public List getListArticle(int id,String showPage,String goWhich) throws SQLException{
		String sqlall="select * from tb_article where art_whoId=?";
		Object[] params={id};
		
		setPerR(5);
		createPage(sqlall,params,showPage,goWhich);

		int currentP=getPage().getCurrentP();
		int top1=getPage().getPerR();
		int top2=(currentP-1)*top1;
		String sql="";
		if(currentP<=1)
			sql="SELECT TOP "+top1+" * FROM tb_article WHERE art_whoid=? ORDER BY art_pubtime DESC";
		else
			sql="SELECT TOP "+top1+" * FROM tb_article i WHERE (art_whoId = ?) AND (art_pubTime < (SELECT MIN(art_pubTime) FROM (SELECT TOP "+top2+" * FROM tb_article WHERE art_whoId = i.art_whoId ORDER BY art_pubTime DESC) AS minv)) ORDER BY art_pubTime DESC";
		
		List articlelist=getList(sql,params);
		return articlelist;
	}
	/**
	 * @功能：获取文章详细内容
	 */	
	public ArticleSingle getSingleArticle(int id) throws SQLException{
		String sql="select * from tb_article where id=?";
		Object[] params={id};
		
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			return (ArticleSingle)list.get(0);
		else
			return null;
	}
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				ArticleSingle single=new ArticleSingle();
				single.setId(rs.getInt(1));
				single.setArtWhoId(rs.getInt(2));
				single.setArtTitle(rs.getString(3));
				single.setArtContent(rs.getString(4));
				single.setArtPubTime(Change.dateTimeChange(rs.getTimestamp(5)));
				single.setArtCount(rs.getInt(6));
				single.setRevCount(getRevCount(single.getId()));
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}
	/**
	 * @throws SQLException 
	 * @功能：获取文章评论数
	 */
	public int getRevCount(int id) throws SQLException{
		int count=0;
		String sql="select count(id) from tb_articleR where artReview_rootId=?";
		Object[] params={id};
		
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs = mydb.getRs();
		if(rs!=null&&rs.next())
			count=rs.getInt(1);
		rs.close();
		mydb.closed();
		return count;
	}
	
	/**
	 * @throws SQLException 
	 * @功能：获取该博主文章总数
	 */
	public int getActicleCount(int id) throws SQLException{
		int count=0;
		String sql="select count(id) from tb_article where art_whoId=?";
		Object[] params={id};
		
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs = mydb.getRs();
		if(rs!=null&&rs.next())
			count=rs.getInt(1);
		rs.close();
		mydb.closed();
		return count;
	}
	
	
	/**
	 * @功能：累加文章访问次数
	 */
	public void setArtCount(int id) throws SQLException{
		String sql="update tb_article set art_count=art_count+1 where id=?";
		Object[] params={id};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	/**
	 * @throws SQLException 
	 * @功能：修改文章
	 */
	public int modify(Object[] params) throws SQLException{
		String sql="update tb_article set art_title=?,art_content=? where id=?";
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=mydb.getCount();
		mydb.closed();
		return i;
	}
	/**
	 * @throws SQLException 
	 * @功能：删除文章
	 */
	public int delete(int id) throws SQLException{
		String sql="delete from tb_article where id=?";
		DB mydb=new DB();
		mydb.doPstm(sql, new Object[]{id});
		int i=mydb.getCount();
		mydb.closed();
		return i;
	}
	/**
	 * @throws SQLException 
	 * @功能：发表文章
	 */
	public int insert(Object[] params) throws SQLException{
		String sql="insert into tb_article values(?,?,?,?,?)";
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		int i=mydb.getCount();
		mydb.closed();
		return i;
	}
	
	/**
	 * @throws SQLException 
	 * @功能： 获取最新博文（10条记录）
	 */
	public List getNewArticle() throws SQLException{
		String sql="select top 10 id,art_whoId,art_title,art_content,art_count from tb_article order by art_pubTime desc";
		List newArticleList=null;
		
		DB mydb=new DB();
		mydb.doPstm(sql,null);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			newArticleList=new ArrayList();
			while(rs.next()){
				ArticleSingle single=new ArticleSingle();
				single.setId(rs.getInt(1));
				single.setArtWhoId(rs.getInt(2));
				single.setArtTitle(rs.getString(3));
				single.setArtContent(rs.getString(4));
				single.setArtCount(rs.getInt(5));
				
				newArticleList.add(single);
			}
		}
		return newArticleList;
	}
	
	
	/**
	 * @throws SQLException 
	 * @功能： 获取最新博文（10条记录）
	 */
	public List getMostArticle() throws SQLException{
		String sql="select top 10 t1.id,t1.art_whoId,t1.art_title,t1.art_content,t1.art_count,convert(varchar, t1.art_pubTime, 111),t2.user_name from tb_article t1,tb_user t2 where t1.art_whoId=t2.id  order by art_count desc";
		List newArticleList=null;
		
		DB mydb=new DB();
		mydb.doPstm(sql,null);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			newArticleList=new ArrayList();
			while(rs.next()){
				ArticleSingle single=new ArticleSingle();
				single.setId(rs.getInt(1));
				single.setArtWhoId(rs.getInt(2));
				single.setArtTitle(rs.getString(3));
				single.setArtContent(rs.getString(4));
				single.setArtCount(rs.getInt(5));
				single.setArtPubTime(rs.getString(6));
				single.setUserName(rs.getString(7));
				System.out.println(rs.getString(6));
				newArticleList.add(single);
			}
		}
		return newArticleList;
	}
	
}
