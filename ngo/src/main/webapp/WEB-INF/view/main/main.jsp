<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Google Picker Example</title>
  </head>
  <body>
  <c:forEach items="${fileList}" var="fileList">
  	<a href="https://www.googleapis.com/drive/v2/files/${fileList.id}">${fileList.title}</a>
  </c:forEach>
  </body>
</html>