package board.svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import vo.BoardBean;
import dao.BoardDAO;

public class BoardModifyFormSvc {

	public vo.BoardBean getArticle(int board_num) {
		BoardBean article = null;		
		Connection con = null;
		try {
			con = getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			
			article = boardDAO.selectArticle(board_num);
			
		}catch(Exception e){
			e.printStackTrace();
		
		}finally {
			close(con);
		}
		
		return article;
	} 
}
