package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.JoinProSvc;
import vo.ActionForward;
import vo.MemberBean;

public class JoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		MemberBean member = new MemberBean();
		member.setId(request.getParameter("id"));
		member.setPass(request.getParameter("pass"));
		member.setName(request.getParameter("name"));
		member.setAge(Integer.parseInt(request.getParameter("age")));
		member.setGender(request.getParameter("gender"));
		member.setEmail(request.getParameter("email"));
		JoinProSvc joinProSvc = new JoinProSvc();
		boolean isJoinSuccess = joinProSvc.joinMember(member);
		if(isJoinSuccess) {
			forward = new ActionForward();
			forward.setPath("loginForm.log");
			forward.setRedirect(true);
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원등록실패');");
			out.println("history.back();");
			out.println("</script>");
		}
		return forward;
	}

}
