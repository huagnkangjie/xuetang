<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
   	<title></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="renderer" content="webkit">
	<script type="text/javascript" src="<%=path %>/resources/js/jquery-1.11.1.min.js?v.<%=System.currentTimeMillis()%>" ></script>
	<script type="text/javascript">
	$(function(){
		alert("OK")
// 		$.ajax({                
// 			cache: false,    
// 			async: false, 
// 			type: "POST",                
<%-- 			url:  '<%=path%>/entMeetingNoticeController/del.do',                 --%>
// 			data:{
// 				meetingId : encodeURI(newContents)
// 			},              
// 			error: function(request) { 
// 			},
// 			beforeSend : function(request){
// 			},
// 			success: function(data) {
				
// 			},
// 			complete: function(data) { 
// 				var dataStr = data.responseText;
// 			}
// 		});
	});
	</script>
	</head>
	<body>
	</body>
	
</html>

