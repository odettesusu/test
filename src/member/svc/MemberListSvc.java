package member.svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.ArrayList;

import dao.MemberDAO;
import vo.MemberBean;

public class MemberListSvc {

	public ArrayList<MemberBean> getMemberList(int page, int limit) {
		ArrayList<MemberBean> memberList = null;
		Connection con = null;
		try {
			con = getConnection();
			MemberDAO memberDAO = MemberDAO.getInstance();
			memberDAO.setConnection(con);

			memberList = memberDAO.selectMemberList(page, limit);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con);
		}
		return memberList;
	}

	public int getListCount() {
		int listCount = 0;
		Connection con = null;
		try {
			con = getConnection();
			MemberDAO memberDAO = MemberDAO.getInstance();
			memberDAO.setConnection(con);

			listCount = memberDAO.selectListCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con);
		}
		return listCount;
	}

}
