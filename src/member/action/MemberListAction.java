package member.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberListSvc;
import vo.ActionForward;
import vo.MemberBean;
import vo.PageInfo;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();

		if (session.getAttribute("id") == null || !session.getAttribute("id").equals("admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자로 로그인하세요!!')");
			out.println("location.href='logFrom.log'");
			out.println("</script>");

		} else {
			int page = 1;
			int limit = 10;
			int limitPage = 10;

			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}

			MemberListSvc memberListSvc = new MemberListSvc();
			
			int listCount = memberListSvc.getListCount();
			int maxPage = (int) ((double) listCount / limit + 0.95);
			int startPage = ((int) ((double) page / limitPage + 0.9) - 1) * limitPage + 1;
			int endPage = startPage + limitPage - 1;
			if (endPage > maxPage) endPage = maxPage;
			PageInfo pageInfo = new PageInfo();
			pageInfo.setEndPage(endPage);
			pageInfo.setListCount(listCount);
			pageInfo.setMaxPage(maxPage);
			pageInfo.setPage(page);
			pageInfo.setStartPage(startPage);

			ArrayList<MemberBean> memberlist = memberListSvc.getMemberList(page, limit);

			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("memberList", memberlist);

			forward = new ActionForward();
			forward.setPath("/member/member_list.jsp");
		}
		return forward;
	}
}
