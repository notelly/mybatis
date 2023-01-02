package co.yedam.notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.common.Command;
import co.yedam.notice.service.NoticeService;
import co.yedam.notice.service.impl.NoticeServiceImpl;
import co.yedam.notice.service.impl.NoticeServiceImplMybatis;
import co.yedam.notice.vo.NoticeVO;

public class SearchNotice implements Command {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		//그냥 페이지를 열어주는게 아니라 파라메터를 가지고 가야함
		
		//페이지 정보
		String pageNum = req.getParameter("pageNum");
		String searchCondition = req.getParameter("searchCondition");
		String keyword = req.getParameter("keyword");
		//num id값을 가지고 한건 조회하는 기능이 있어야함 NoticeService에 기능이 있는지 확인하고 없으면 만든다.
		
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("searchCondition", searchCondition);
		req.setAttribute("keyword", keyword);
		
		String num = req.getParameter("num");
		NoticeService service = new NoticeServiceImplMybatis();
		//service.searchNotice(Integer.parseInt(num));
		//searchNotice를 하면 반환되는 값이 vo
		//NoticeVO vo =null;
		NoticeVO vo = service.searchNotice(Integer.parseInt(num));
				
		req.setAttribute("vo", vo);
				
		return "notice/searchNotice.tiles";//어디로갈지 값을 return 해준다.
	}

}
