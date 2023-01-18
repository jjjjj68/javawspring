<%-- kakaoMenu.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<div>
	<p>
		<a href="${ctp}/study/kakaomap/kakaoEx1" class="btn btn-success">마커표시/DB저장</a>&nbsp;
		<a href="${ctp}/study/kakaomap/kakaoEx2" class="btn btn-success">DB에저장된지명검색/삭제</a>&nbsp;
		<a href="${ctp}/study/kakaomap/kakaoEx3" class="btn btn-success">지명검색</a>&nbsp;
		<a href="${ctp}/study/kakaomap/kakaoEx4" class="btn btn-success">지명검색</a>&nbsp;
		<a href="${ctp}/study/kakaomap/kakaoEx5" class="btn btn-success">지명검색</a>&nbsp;
	</p>
</div>