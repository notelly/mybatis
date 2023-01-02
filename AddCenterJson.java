package co.yedam.common;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.JSONParser;

public class AddCenterJson implements Command {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		try {
			ServletInputStream sis = req.getInputStream(); //return type => ServletInputStream : byte의 형태
			byte[] bytes = sis.readAllBytes();
			//문자열을 생성할떄 string을 사용
			String json = new String(bytes); // String json = "test";
			System.out.println(json);
			
			//json => object
			JSONParser parser = new JSONParser();
			Object obj =  parser.parse(json); //return type = object
			
			//[{},{},{},{}]
			ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) obj;
			for(Map<String, Object> map : list) {
				Set<String> keys = map.keySet();
				for(String key : keys) {
					System.out.println(key + " : " + map.get(key));
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "{reCode: OK}.ajax";
	}

}
