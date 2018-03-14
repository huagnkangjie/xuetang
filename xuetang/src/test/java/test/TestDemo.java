package test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.ziyue.xuetang.constant.Resource;
import com.ziyue.xuetang.utils.JsonUtil;

import base.JUnitActionBase;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年10月30日
 * @version v1.0.
 * 
 */
public class TestDemo extends JUnitActionBase {
	
	@Test
	public void tests(){
		//添加问题
	}

	
	public void add() {
		try {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put(Resource.REQ_ACCESS_TOKEN, "641d05ef-3702-482a-8021-ec1f8ec63d35");

			String json = JsonUtil.toJson(map);
			System.out.println(json);

			MvcResult result = mockMvc.perform(post("/stu/kill/testyyy")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json)

			).andDo(MockMvcResultHandlers.print()).andReturn();

			System.out.println(status().isNoContent());

			System.out.println(content().toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
