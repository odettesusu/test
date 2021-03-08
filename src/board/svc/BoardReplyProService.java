package board.svc;
import static db.JdbcUtil.*;
import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardReplyProService {

	public boolean replyArticle(BoardBean article) {
		boolean isReplySuccess = false;
		Connection con = null;
		try {
			con = getConnection();
			BoardDAO boardDAO = BoardDAO.getInstance();
			boardDAO.setConnection(con);
			int insertCount = boardDAO.insertReplyArticle(article);
			
			if(insertCount > 0) {
				commit(con);
				isReplySuccess = true;
			}else {
				rollback(con);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			close(con);
		}
		return isReplySuccess;
	}

}
