package board.svc;


import static db.JdbcUtil.*;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardDeleteProService {

	public boolean isArticleWriter(int board_num, String pass) {
		boolean isArticleWriter = false;
		Connection con = null;
		try {
			con = getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			isArticleWriter = boardDAO.isArticleBoardWriter(board_num, pass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con);
		}

		return isArticleWriter;

	}

	public boolean removeArticle(int board_num) {
		boolean isDeleteSuccess = false;
		Connection con = null;
		try {
			con = getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			
			int deleteCount = boardDAO.deleteArticle(board_num);
			
			if(deleteCount > 0) {
				commit(con);
				isDeleteSuccess= true;
			}else{
				rollback(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con);
		}
		return isDeleteSuccess;
	}
}
