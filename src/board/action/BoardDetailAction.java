package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int board_num = 1;
		if(request.getParameter("board_num")!=null)
			board_num=Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page");
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article =boardDetailService.getArticle(board_num);
		System.out.println(page);
		request.setAttribute("article", article);
		request.setAttribute("page", page);
		ActionForward forward = new ActionForward();
		forward.setPath("/board/qna_board_view.jsp");
		return forward;
	}

}
