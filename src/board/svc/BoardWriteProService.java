package board.svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;
import static db.JdbcUtil.*;

public class BoardWriteProService {

	public boolean registArticel(BoardBean boardBean) throws Exception {
		// TODO Auto-generated method stub

		boolean isWriteSuccess = false;
		Connection con = null;
		try {
		con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int insertCount = boardDAO.insertArticle(boardBean);

		if (insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		close(con);
		}
		return isWriteSuccess;
	}
}
