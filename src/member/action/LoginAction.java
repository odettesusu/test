package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.ActionForward;
import vo.MemberBean;

public class LoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		LoginSvc loginSvc = new LoginSvc();
		MemberBean member = loginSvc.getMember(id);
		if(member !=null) {
		if(member.getPass().equals(pass)) {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("main.jsp");
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 실패');");
			out.println("history.back();");
			out.println("</script>");
			}
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 실패');");
			out.println("history.back();");
			out.println("</script>");
		}
			return forward;
	}
}
