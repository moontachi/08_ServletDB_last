package svy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SurveyDao {
	
	
	
	
	/*
	private String driver="oracle.jdbc.driver.OracleDriver";
	private String url="jdbc:oracle:thin:@localhost:1521:orcl";
	private String user="jspid";
	private String password="jsppw";
	*/
	
	private String driver;
	private String url;
	private String user;
	private String password;
	
	private Connection conn  = null ;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	/*
	public SurveyDao() {
		try {
			Class.forName( driver ) ;

			conn = DriverManager.getConnection( url, user, password ) ;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//생성자,드라이버로드,계정 접속
	*/
	
	
	public SurveyDao(String driver, String url, String user, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		try {
			Class.forName(driver) ;

			conn = DriverManager.getConnection(url,user,password) ;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public void insertSurvey(SurveyBean sb) {
		String sql = "insert into survey values(seqmy.nextval,?,?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sb.getName());
			pstmt.setString(2, sb.getCompany());
			pstmt.setString(3, sb.getEmail());
			pstmt.setString(4, sb.getSatisfaction());
			pstmt.setString(5, sb.getPart());
			pstmt.setString(6, sb.getHowto());
			pstmt.setInt(7, sb.getAgree());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(SQLException e) {

			}
		}
	}
	
	public ArrayList<SurveyBean> getSurveyList(){
		
		ArrayList<SurveyBean> lists = new ArrayList<SurveyBean>();
		String sql = "select * from survey order by no asc";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String company = rs.getString("company");
				String email = rs.getString("email");
				String satisfaction = rs.getString("satisfaction");
				String part = rs.getString("part");
				String howto = rs.getString("howto");
				int agree = rs.getInt("agree");
				
				SurveyBean sb = new SurveyBean(no, name, company, email, satisfaction,part, howto, agree);
				lists.add(sb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(SQLException e) {

			}
		}
		return lists;
	}
	
	public int deleteSurvey(String no) {
		String sql = "delete from survey where no = ?";
		int cnt = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(SQLException e) {

			}
		}
		return cnt;
	}
	
	public SurveyBean getOneSelect(String no) {
		String sql = "select * from survey where no = ?";
		SurveyBean sb = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int no2 = rs.getInt("no");
				String name = rs.getString("name");
				String company = rs.getString("company");
				String email = rs.getString("email");
				String satisfaction = rs.getString("satisfaction");
				String part = rs.getString("part");
				String howto = rs.getString("howto");
				int agree = rs.getInt("agree");
				
				sb = new SurveyBean(no2, name, company, email, satisfaction,part, howto, agree);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(SQLException e) {

			}
		}
		return sb;
	}
	
	public int updateSurvey(SurveyBean sb) {
		int cnt = -1;
		String sql = "update survey set name=?,company=?,email=?,satisfaction=?,part=?,howto=?,agree=? where no = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sb.getName());
			pstmt.setString(2, sb.getCompany());
			pstmt.setString(3, sb.getEmail());
			pstmt.setString(4, sb.getSatisfaction());
			pstmt.setString(5, sb.getPart());
			pstmt.setString(6, sb.getHowto());
			pstmt.setInt(7, sb.getAgree());
			pstmt.setInt(8, sb.getNo());
			
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}catch(SQLException e) {

			}
		}
		return cnt;
	}
}
