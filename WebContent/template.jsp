<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Template Test</title>
<style type="text/css">
	table {
	margin : auto;
	width : 960px;
	color: gray;
	border : 1px solid gray;
	}
	</style>
</head>
<body>
<table border=0>
	<tr>
		<td align="center"><br>
		<jsp:include page="menu_top.jsp"></jsp:include>
	</td>
	</tr>
	<tr>
		<td align="center">
			<jsp:include page='${pagefile }'/>
		</td>
	</tr>		
</table>
</body>
</html>