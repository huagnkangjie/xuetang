package  com.ziyue.xuetang.common.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tencent.XingeMessage;

public class XingeMessageModule {

	public static void generalEntMessage(int type,List<String> to,String msgId,String title,String content,String hint,String time){
		
		Map<String, Object> map = new HashMap<String, Object> ();
		
		Map<String, Object> data= new HashMap<String, Object> ();
		
		 map.put("type", type);
		
		 map.put("data", data);
		 
		 data.put("msg_id", msgId);
		 
		// data.put("to", to);
		 
		 data.put("title",title);
		 
		 data.put("hint",hint);
		 
		 data.put("time",time);
		 
		 XingeMessage.instance().pushEntAccountList(title, title, map, to);
		 
		 XingeMessage.instance().pushEntAccountListIos(title, map, to);
		 
		 
		 
	}
	
}
