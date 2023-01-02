package co.yedam.notice.service.impl;

import java.util.List;
import java.util.Map;

import co.yedam.common.SearchVO;
import co.yedam.notice.dao.NoticeDAO;
import co.yedam.notice.service.NoticeService;
import co.yedam.notice.vo.NoticeVO;

public class NoticeServiceImpl implements NoticeService{
	//jdbc 사용처리.
	NoticeDAO dao = NoticeDAO.getInstance();
	
	//서비스 로직을 구현하는 곳
	@Override
	public List<NoticeVO> noticeList(){
		return null;
	}

	@Override
	public List<NoticeVO> noticeListPaging(int pageNum, SearchVO svo) {
		return dao.noticeListPaging(pageNum, svo);
	}
	
	//조회조건이 svo에 들어가야한다.
	@Override
	public int noticeListPagingTotalCnt(SearchVO svo) {
		return dao.noticeListTotalCnt(svo);
	}
	
	//dao class에 다가 id값이 나오면 num 값이 나오도록 해야한다. NoticeDAO로 가기
	@Override
	public NoticeVO searchNotice(int noticeId) {
		dao.addHitCount(noticeId); //조회수도 올리고
		return dao.searchNotice(noticeId); //조회도 할 것 임
	}

	@Override
	public int updateNotice(NoticeVO vo) {
		return dao.updateNotice(vo);
	}

	@Override
	public int insertNoitce(NoticeVO vo) {
		return dao.insertNotice(vo);
	}

	@Override
	public int deleteNotice(int noticeId) {
		return dao.deleteNotice(noticeId);
	}

	@Override
	public Map<String, Integer> chartData() {
		return dao.chartData();
	}

	
	
}
