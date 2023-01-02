package co.yedam.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.notice.service.impl.NoticeServiceImplMybatis;

public class AddCenterInfo implements Command{

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		String cn = req.getParameter("cn");
		String pn = req.getParameter("pn");
		String add = req.getParameter("add");
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("centerName", cn);
		map.put("phoneNumber", pn);
		map.put("address", add);
		
		
		NoticeServiceImplMybatis mybatis = new NoticeServiceImplMybatis();
		int result = mybatis.addCenterInfo(map); //map타입
		
		return "{retCode: " +result+ "}.ajax";
	}
	
}
