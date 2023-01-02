package co.yedam.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import co.yedam.common.Criteria;
import co.yedam.common.DataSource;
import co.yedam.common.SearchVO;
import co.yedam.notice.mapper.NoticeMapper;
import co.yedam.notice.service.NoticeService;
import co.yedam.notice.vo.NoticeVO;

public class NoticeServiceImplMybatis implements NoticeService{
	public NoticeServiceImplMybatis() {
		System.out.println("Mybatis 실행 중");
	}
	SqlSession session = DataSource.getInstance().openSession(true);
	//true 오토커밋?
	NoticeMapper mapper = session.getMapper(NoticeMapper.class);
	
	//foreach 연습.
	public List<NoticeVO> forEachTest(List<NoticeVO> list){
		return mapper.forEachTest(list);
	}
	
	public int addCenterInfo(Map<String, Object> map) {
		return mapper.addCenterInfo(map);
	}
	
	
	
	@Override
	public List<NoticeVO> noticeList() {
		return null;
	}

	@Override
	public List<NoticeVO> noticeListPaging(int pageNum, SearchVO svo) {
		//mybatis parameter로 넘기기 위해 Criterial 활용
		//Criteria를 SearchVO에 상속시키고 아래 내용 추가
		Criteria cri = new Criteria(pageNum, 10);
		cri.setSearchCondition(svo.getSearchCondition());
		cri.setKeyword(svo.getKeyword());
		return mapper.noticeListPaging(cri);
	}

	@Override
	public int noticeListPagingTotalCnt(SearchVO svo) {
		return mapper.noticeListPagingTotalCnt(svo);
	}

	@Override
	public NoticeVO searchNotice(int noticeId) {
		mapper.addHitCount(noticeId);
		return mapper.searchNotice(noticeId);
	}

	@Override
	public int updateNotice(NoticeVO vo) {
		return mapper.updateNotice(vo);
	}

	@Override
	public int insertNoitce(NoticeVO vo) {
		return mapper.insertNotice(vo);
		//Member은 session에 했는데
		//이번에는 mapper로 바로
		//이것이 가능한 이유는 notice는 인터페이스를 따로 생성했기 때문임.
	}

	@Override
	public int deleteNotice(int noticeId) {
		return mapper.deleteNotice(noticeId);
	}

	@Override
	public Map<String, Integer> chartData() {
		return null;
	}



}
