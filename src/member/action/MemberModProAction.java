package member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberInfoSvc;
import vo.ActionForward;
import vo.MemberBean;

public class MemberModProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;

		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null || !session.getAttribute("id").equals("admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자로 로그인하세요 !!')");
			out.println("location.href='loginForm.log'");
			out.println("</script>");
		} else {
			MemberBean member = new MemberBean();
			member.setId(request.getParameter("id"));
			member.setPass(request.getParameter("pass"));
			member.setName(request.getParameter("name"));
			member.setAge(
					Integer.parseInt(!(request.getParameter("age") == null || request.getParameter("age").equals(""))
							? request.getParameter("age")
							: "0"));
			member.setGender(request.getParameter("gender"));
			member.setEmail(request.getParameter("email"));

			MemberModProSvc memberModProSvc = new MemberModProSvc();
			boolean isModifySuccess = memberModProSvc.modifyMember(member);

			if (isModifySuccess) {
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("memberInfo.mem?id=" + member.getId());
			} else {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정실패');");
				out.println("history.back();");
				out.println("</script>");

			}
		}
		return forward;
	}

}
