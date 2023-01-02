package co.yedam.notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.common.Command;
import co.yedam.notice.service.NoticeService;
import co.yedam.notice.service.impl.NoticeServiceImpl;
import co.yedam.notice.service.impl.NoticeServiceImplMybatis;
import co.yedam.notice.vo.NoticeVO;

public class InsertNotice implements Command{

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		NoticeVO vo = new NoticeVO();
		String writer = req.getParameter("writer");
		String title = req.getParameter("title");
		String subject = req.getParameter("subject");
		
		vo.setNoticeWriter(writer);
		vo.setNoticeTitle(title);
		vo.setNoticeSubject(subject);

		NoticeService service = new NoticeServiceImplMybatis();
		service.insertNoitce(vo);
		
		//return "notice/noticeList.tiles"가 아니라
		return "noticeList.do";
	}
	
}
