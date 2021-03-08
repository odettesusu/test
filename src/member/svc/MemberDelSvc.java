package member.svc;

import java.sql.Connection;
import static db.JdbcUtil.*;
import dao.MemberDAO;

public class MemberDelSvc {

	public boolean deleteMember(String id) {
		boolean isDeleteSuccess = false;
		Connection con = null;
		try {
			con = getConnection();
			MemberDAO memberDAO = MemberDAO.getInstance();
			memberDAO.setConnection(con);
			int deleteCount = memberDAO.deleteMember(id);
			if(deleteCount > 0) {
				commit(con);
				isDeleteSuccess = true;
			}else {
				rollback(con);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return isDeleteSuccess;
	}

}
