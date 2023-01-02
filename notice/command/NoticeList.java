package co.yedam.notice.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.common.Command;
import co.yedam.common.Criteria;
import co.yedam.common.PageDTO;
import co.yedam.common.SearchVO;
import co.yedam.notice.service.NoticeService;
import co.yedam.notice.service.impl.NoticeServiceImpl;
import co.yedam.notice.service.impl.NoticeServiceImplMybatis;
import co.yedam.notice.vo.NoticeVO;

public class NoticeList implements Command {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		//getParameter : jsp 에서 지정된 name = ""의 값을 가지고 옴
		String searchCondition = req.getParameter("searchCondition");
		String keyword = req.getParameter("keyword");
		SearchVO svo = new SearchVO();
		svo.setSearchCondition(searchCondition);
		svo.setKeyword(keyword);
		
		String pageNum = req.getParameter("pageNum"); //pageNum이라는 정보를 받아들인다.
		//파라메터 값이 없으면 pageNum을 1로 하고 있으면 그값을 pageNum으로 지정한다.
		pageNum = pageNum == null? "1" : pageNum;
		
		int pageNumInt = Integer.parseInt(pageNum);
		
		//데이터를 조회하거나 결과값을 반환해주고 싶어서.
		//NoticeDAO dao = NoticeDAO.getInstance();
		//이렇게 안쓰고 서비스에서 호출해야한다.
		NoticeService dao = new NoticeServiceImplMybatis();
		int total = dao.noticeListPagingTotalCnt(svo);
		//list의 반환되는 타입 NoticeVO
		//noticeListPaging에 searchContion과 keyword도 넘기겠다.
		List<NoticeVO> list = dao.noticeListPaging(pageNumInt, svo);
		
		//setAttribute("a", b); b의 이름을 "a"로 정하고
		//jsp에서 ${a }로 값을 가지고 오게 만들어줌.
		req.setAttribute("noticeList", list);
		// list ->dao.noticeList() 의 주소값을 가지고 있다.
		// 주소값을 따라가면 페이지리스트가 있다.
		
		//현재페이지를 기준으로 해서
		//시작페이지가 어디고
		//마지막페이지가 어딘지 나타내줘야한다.
		//현재페이지정보, 페이지당건수, 전체데이터건수
		Criteria cri = new Criteria(pageNumInt, 10); //10페이지당 보여지는 건수
		PageDTO dto = new PageDTO(cri, total);
		req.setAttribute("pageDTO", dto);
		
		req.setAttribute("searchvo", svo);
		
		
		//WEB INF 하위 폴더는 실행이 불가능하므로 이렇게 설정해주어야한다.
		//noticeList.do를 하게 되면 들어올 수 있게 됨.
		return "notice/noticeList.tiles"; //어떤 처리를 하고 이페이지로 이동하겠습니다.
	}

}
