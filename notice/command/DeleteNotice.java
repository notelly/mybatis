package co.yedam.notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.common.Command;
import co.yedam.notice.service.NoticeService;
import co.yedam.notice.service.impl.NoticeServiceImpl;
import co.yedam.notice.service.impl.NoticeServiceImplMybatis;
import co.yedam.notice.vo.NoticeVO;

public class DeleteNotice implements Command {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		//파라미터 읽어와서 => 삭제할 글번호.
		NoticeVO vo = new NoticeVO();
		String id = req.getParameter("num");
		
		vo.setNoticeId(Integer.parseInt(id));
		
		
		
		NoticeService service = new NoticeServiceImplMybatis();
		service.deleteNotice(Integer.parseInt(id));
		
		return "noticeList.do";
	}

}
