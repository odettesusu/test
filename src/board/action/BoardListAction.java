package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int page=1;
		int limit=10;
		int limitPage=10;
		
		if(request.getParameter("page")!=null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		BoardListService boardListService = new BoardListService();
		
		int listCount=boardListService.getListCount();
		int maxPage=(int)((double)listCount/limit+0.95);
		int startPage=((int) ((double)page/limitPage +0.9) -1) * limitPage +1;
		int endPage = startPage+limitPage -1;
		if(endPage > maxPage) endPage = maxPage;
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPage(page);
		pageInfo.setStartPage(startPage);
		
		ArrayList<BoardBean> articleList=new ArrayList<BoardBean>();
		articleList = boardListService.getArticleList(page, limit);
		System.out.println(articleList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/board/qna_board_list.jsp");
		return forward;
		
	}

}
