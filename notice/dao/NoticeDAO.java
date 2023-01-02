package co.yedam.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.yedam.common.DAO;
import co.yedam.common.SearchVO;
import co.yedam.notice.vo.NoticeVO;

public class NoticeDAO {
	Connection conn;
	ResultSet rs;
	PreparedStatement psmt;
	// 싱글톤 방식으로 인스턴스 생성
	private static NoticeDAO instance = new NoticeDAO();

	private NoticeDAO() {
	}

	public static NoticeDAO getInstance() {
		return instance;
	}

	// 리소스를 반환하는 코드
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 전체목록 반환.(X)
	//
	public List<NoticeVO> noticeList() {
		List<NoticeVO> list = new ArrayList<>();
		String sql = "select * from notice order by notice_id";
		conn = DAO.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				NoticeVO vo = new NoticeVO();
				vo.setNoticeId(rs.getInt("notice_id"));
				vo.setNoticeWriter(rs.getString("notice_writer"));
				vo.setNoticeTitle(rs.getString("notice_title"));
				vo.setNoticeSubject(rs.getString("notice_subject"));
				vo.setHitCount(rs.getInt("hit_count"));
				vo.setNoticeDate(rs.getDate("notice_date"));

				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	
	public List<NoticeVO> noticeListPaging(int page, SearchVO svo) {
		List<NoticeVO> list = new ArrayList<>();
		String sql = "SELECT * FROM (SELECT ROWNUM rn, a.* FROM (SELECT * FROM notice where 1=1";
				
				if(svo.getSearchCondition()!= null && svo.getSearchCondition().equals("writer")) {
					sql += " and notice_writer like '%'||?||'%' ";
				}else if (svo.getSearchCondition()!= null && svo.getSearchCondition().equals("title")) {
					sql += " and notice_title like '%'||?||'%' ";
				}else if (svo.getSearchCondition()!= null && svo.getSearchCondition().equals("subject")) {
					sql+= " and notice_subject like '%'||?||'%' ";
				}
				
				sql+= " order by notice_id DESC) a WHERE ROWNUM <= (?*10)) b WHERE b.rn >= (?*10-9)";
		
		
		
		
		conn = DAO.getConn();
		// psmt = conn.prepareStatement(sql); //예외처리를 넣어주어야함.
		try {
			psmt = conn.prepareStatement(sql);
			// 파라메터를 지정해준다.
			int row = 1;
			if(svo.getSearchCondition()!= null && !svo.getSearchCondition().equals(""))
				psmt.setString(row++, svo.getKeyword()); //얘가 있을수도 있고 없을수도 있기 때문에.
			psmt.setInt(row++, page);
			psmt.setInt(row++, page);
			// 결과값을 가져오기 위해서 쿼리를 실행
			rs = psmt.executeQuery();

			while (rs.next()) {
				NoticeVO vo = new NoticeVO();
				vo.setNoticeId(rs.getInt("notice_id"));
				vo.setNoticeWriter(rs.getString("notice_writer"));
				vo.setNoticeTitle(rs.getString("notice_title"));
				vo.setNoticeSubject(rs.getString("notice_subject"));
				vo.setHitCount(rs.getInt("hit_count"));
				vo.setNoticeDate(rs.getDate("notice_date"));
				
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;

	}
	
	//hit_count 숫자 올라가게
	public int addHitCount(int noticeId) {
		String sql = "update notice set hit_count = hit_count+1 where notice_id = ?";
		conn = DAO.getConn();
		int r = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, noticeId);
			r = psmt.executeUpdate();
			if(r>0) {
				System.out.println("변경완료");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return r;
	}
	
	
	//상세정보조회.
	public NoticeVO searchNotice(int noticeId) {
		String sql = "select * from notice where notice_id = ?";
		conn = DAO.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, noticeId);
			rs = psmt.executeQuery();
			if(rs.next()) { //조회된 결과가 있으면 NoticeVO를 반환
				NoticeVO vo = new NoticeVO();
				vo.setNoticeId(rs.getInt("notice_id")); //rs.getInt 반환되는 값을 in타입으로 가지고 오겠다.
				vo.setNoticeWriter(rs.getString("notice_writer"));
				vo.setNoticeTitle(rs.getString("notice_title"));
				vo.setNoticeSubject(rs.getString("notice_subject"));
				vo.setHitCount(rs.getInt("hit_count"));
				vo.setNoticeDate(rs.getDate("notice_date"));
			
				return vo;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;		
	} 
	
	
	//페이징 건수 메소드
	public int noticeListTotalCnt(SearchVO svo) {
		String sql = "select count(1) from notice";
		if(svo.getSearchCondition()!= null && svo.getSearchCondition().equals("writer")) {
			sql += " where notice_writer like '%'||?||'%' ";
		}else if (svo.getSearchCondition()!= null && svo.getSearchCondition().equals("title")) {
			sql += " where notice_title like '%'||?||'%' ";
		}else if (svo.getSearchCondition()!= null && svo.getSearchCondition().equals("subject")) {
			sql += " where notice_subject like '%'||?||'%' ";
		}
		
		conn = DAO.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			//null도 아니고 공백도 아니도록 하겠다는 조건을 붙여줌.
			// svo.getSearchCondition().equals("") 이걸 비교하는 이유
			// ?에 값을 넣어주려면 위에 if문이 충족해야하고. searchCondition에 값이 있어야
			// ?에 값을 넣을 수 있기 때문
			if(svo.getSearchCondition()!= null && !svo.getSearchCondition().equals(""))
			psmt.setString(1, svo.getKeyword());
			rs = psmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1); //첫번쨰 컬럼을 가지고 오겠습니다.
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(); //값이 없으면
		}
		return 0; //0을 리턴
		
	}
	
	//게시글 수정
	public int updateNotice(NoticeVO vo) { //title, subject변경.
		String sql = "update notice set notice_title = ?, notice_subject = ? where notice_id=?";
		conn = DAO.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getNoticeTitle());
			psmt.setString(2, vo.getNoticeSubject());
			psmt.setInt(3, vo.getNoticeId());
			int r = psmt.executeUpdate();
			return r ; //r은 변경된 건수를 반환하는 거임 update되면 1이올라감
 		} catch (SQLException e) {
			e.printStackTrace();
 		}finally {
 			close();
 		}
		return 0;
	}
	
	
	//게시글 등록
	public int insertNotice(NoticeVO vo) {
		String sql = "INSERT INTO notice(notice_id, notice_writer, notice_title, notice_subject, notice_date)"
				+ " VALUES(notice_seq.nextval, ?, ?, ?, sysdate)";
		conn = DAO.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getNoticeWriter());
			psmt.setString(2, vo.getNoticeTitle());
			psmt.setString(3, vo.getNoticeSubject());
			
			int result = psmt.executeUpdate();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return 0;
		
	}

	public int deleteNotice(int noticeId) {
		String sql = "DELETE notice WHERE notice_id = ?";
		conn = DAO.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, noticeId);
			
			int result = psmt.executeUpdate();
			return result;	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return 0;
	}
	
	// chart 데이터 생성
	public Map<String, Integer> chartData(){
		Map<String, Integer> mapData = new HashMap<>();
		conn = DAO.getConn();
		String sql = "select member_name, count(1) "
				+"from notice n "
				+"join member m "
				+"on n.notice_writer = m.member_id "
				+"group by member_name";
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				mapData.put(rs.getString(1), rs.getInt(2));
				// getString(컬럼명) 혹 컬럼의 개수가 작을 때는 숫자로도 표현이 가능하다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return mapData;
		//코딩한 결과를 mapData에 담는다.
	}
	
}
