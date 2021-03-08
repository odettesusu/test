package dao;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import vo.BoardBean;

public class BoardDAO {
	DataSource ds;
	Connection con;
	private static BoardDAO boardDAO;
	
	private BoardDAO() {
		// TODO Auto-generated method stub
	}
	
	
	public static BoardDAO getInstance() {
		if(boardDAO == null) {
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}

	public void setConnection(Connection con) {
			this.con = con;
	}
	
	//글 등록
	public int insertArticle(BoardBean article) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num =0;
		String sql="";
		int insertCount=0;
	
		try {
			pstmt=con.prepareStatement("select max(board_num) from board");
			rs = pstmt.executeQuery();
		
		if(rs.next())
			num =rs.getInt(1)+1;
		else
			num=1;
		
		sql="insert into board values(?,?,?,?,?,?,?,0,0,0,now())";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, num);
		pstmt.setString(2, article.getBOARD_NAME());
		pstmt.setString(3, article.getBOARD_PASS());
		pstmt.setString(4, article.getBOARD_SUBJECT());
		pstmt.setString(5, article.getBOARD_CONTENT());
		pstmt.setString(6, article.getBOARD_FILE());
		pstmt.setInt(7, num);
		
		
		insertCount=pstmt.executeUpdate();
		
		}catch(Exception ex) {
			System.out.println("boardInsert 에러 : "+ex);
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}


	public int selectListCount() {
		int listCount =0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement("select count(*) from board");
			rs = pstmt.executeQuery();
			
			if(rs.next()) listCount = rs.getInt(1);
		}catch(Exception e) {
			System.out.println("getListCount 에러 : " + e);
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
	}


	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		ArrayList<BoardBean> list = new ArrayList<BoardBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String board_list_sql="select * from board order by BOARD_RE_REF desc, BOARD_RE_SEQ asc limit ?, ? ";
		int startrow=(page-1)*limit; //읽기 시작할 row번호
		System.out.println(startrow);		
		try {
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardBean board = new BoardBean();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				list.add(board);
			}
		}catch(Exception e) {
		
		}finally {
		close(rs);
		close(pstmt);
}
		
		return list;
	}


	public int updateReadCount(int board_num) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql="update board set BOARD_READCOUNT ="+"BOARD_READCOUNT+1 where BOARD_NUM=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {	
			close(pstmt);
		}
				
		return updateCount;
	}


	public BoardBean selectArticle(int board_num) {
		BoardBean article = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from board where board_num = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				article = new BoardBean();
				article.setBOARD_NUM(rs.getInt("board_num"));
				article.setBOARD_NAME(rs.getString("board_name"));
				article.setBOARD_SUBJECT(rs.getString("board_subject"));
				article.setBOARD_CONTENT(rs.getString("board_content"));
				article.setBOARD_FILE(rs.getString("board_file"));
				article.setBOARD_RE_REF(rs.getInt("board_re_ref"));
				article.setBOARD_RE_LEV(rs.getInt("board_re_lev"));
				article.setBOARD_RE_SEQ(rs.getInt("board_re_seq"));
				article.setBOARD_READCOUNT(rs.getInt("board_readcount"));
				article.setBOARD_DATE(rs.getDate("board_date"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {	
			close(rs);
			close(pstmt);
		}
		
		
		return article;
	}


	public boolean isArticleBoardWriter(int board_num, String pass) {
		boolean isWriter = false;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		String sql = "select * from board where board_num=? and board_pass=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				isWriter = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return isWriter;
	}


	public int updateArticle(BoardBean article) {
		int updateCount=0;
		PreparedStatement pstmt = null;
		String sql = "update board set board_subject=? , board_content=? where board_num=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, article.getBOARD_SUBJECT());
			pstmt.setString(2, article.getBOARD_CONTENT());
			pstmt.setInt(3, article.getBOARD_NUM());
			updateCount = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
		return updateCount;
	}

	public int insertReplyArticle(BoardBean article) {
		int insertCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "update board set board_re_seq = board_re_seq+1 where board_re_ref=? and board_re_seq>?";
			pstmt= con.prepareStatement(sql);
			pstmt.setInt(1, article.getBOARD_RE_REF());
			pstmt.setInt(2, article.getBOARD_RE_SEQ());
			
			pstmt.executeUpdate();
			//입력할 글 번호 생성
			int num = 1;
			pstmt = con.prepareStatement("select max(board_num) from board");
			rs = pstmt.executeQuery();
			if(rs.next()) num=rs.getInt(1)+1;
			
			sql = "insert into board value (?,?,?,?,?,'',?,?,?,0,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getBOARD_NAME());
			pstmt.setString(3, article.getBOARD_PASS());
			pstmt.setString(4, article.getBOARD_SUBJECT());
			pstmt.setString(5, article.getBOARD_CONTENT());
			pstmt.setInt(6,article.getBOARD_RE_REF());
			pstmt.setInt(7, article.getBOARD_RE_LEV()+1);
			pstmt.setInt(8, article.getBOARD_RE_SEQ()+1);
			insertCount = pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}
	
	public int deleteArticle(int board_num) {
		int deleteCount = 0;
		PreparedStatement pstmt = null;
		String sql = "delete from board where board_num=?";		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return deleteCount;
				
	}
	
}