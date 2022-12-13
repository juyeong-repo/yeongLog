<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container">

<c:forEach var="board" items="${boards.content}"> <!--BoardController의 index함수에서 모델에 담긴 값. 모델에 데이터 넣으면 화면까지 끌고감 -->
	<div class="card m-2">
		<div class="card-body">
			<h4 class="card-title">${board.title}</h4> <!--컨트롤러 - 서비스 - 레포지토리 - board 모델  -->
			<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
		</div>
	</div>
</c:forEach>

<ul class="pagination justify-content-center">
  <c:choose>
  	<c:when test="${boards.first}">
  		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
  	</c:when>
  	<c:otherwise>
  		<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
  	</c:otherwise>
  </c:choose>
  
    <c:choose>
  	<c:when test="${boards.last}">
  		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
  	</c:when>
  	<c:otherwise>
  		<li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
  	</c:otherwise>
  </c:choose>
  
</ul>
	

</div>

<%@ include file="layout/footer.jsp"%>


