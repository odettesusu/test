package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberInfoSvc;
import vo.ActionForward;
import vo.MemberBean;

import static db.JdbcUtil.*;
public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		if(session.getAttribute("id")==null || ! session.getAttribute("id").equals("admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자로 로그인하세요 !!')");
			out.println("location.href='loginForm.log'");
			out.println("</script>");
		}else {
			String id = request.getParameter("id");
			
			MemberInfoSvc memberInfoSvc = new MemberInfoSvc();
			MemberBean member = memberInfoSvc.getMember(id);
			if(member != null) {
				request.setAttribute("member", member);
				forward = new ActionForward();
				forward.setPath("/member/member_info.jsp");
			}else {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원정보가 없습니다.');");
				out.println("location.href='memberList.mem;");
				out.println("</script>");
			}
		}
		return forward;
	}
}
