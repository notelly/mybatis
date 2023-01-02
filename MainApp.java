package co.yedam;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.Criteria;
import co.yedam.common.DAO;
import co.yedam.common.PageDTO;
import co.yedam.notice.dao.NoticeDAO;
import co.yedam.notice.service.impl.NoticeServiceImplMybatis;
import co.yedam.notice.vo.NoticeVO;

public class MainApp {
//	public static void main(String[] args) {
//		Criteria cri = new Criteria(3, 20);
//		PageDTO dto = new PageDTO(cri, 150); //15page
//		
//		System.out.println(dto);
//
//	}
	public static void main(String[] args) {
		List<NoticeVO> list = new ArrayList<>();
		NoticeVO v1 = new NoticeVO();
		v1.setNoticeId(1040);
		NoticeVO v2 = new NoticeVO();
		v2.setNoticeId(1050);
		NoticeVO v3 = new NoticeVO();
		v3.setNoticeId(1060);
		
		list.add(v1);
		list.add(v2);
		list.add(v3);
		
		NoticeServiceImplMybatis mybatis = new NoticeServiceImplMybatis();
		for(NoticeVO notice : mybatis.forEachTest(list)) {
			System.out.println(notice);
		}
		
		
	}

}
