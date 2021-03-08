package board.svc;
import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardListService {

	public int getListCount() {
		int listCount = 0;
		Connection con = null;
		try {
			con=getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			listCount = boardDAO.selectListCount();
		}catch(Exception e){
			e.printStackTrace();
		}finally  {
		close(con);
		}
		return listCount;
	}

	public ArrayList<BoardBean> getArticleList(int page, int limit) {
		ArrayList<BoardBean> list = null;
		Connection con = null;
		try {
			con=getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			list = boardDAO.selectArticleList(page, limit);
		}catch(Exception e){
			e.printStackTrace();
		}finally  {
		close(con);
		}
		return list;
	}
}
