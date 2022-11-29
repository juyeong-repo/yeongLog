package com.project.blog.model;

public enum RoleType {
	USER, ADMIN, CUSTOMER
	
	//ROLE_ADMIN("관리자"), ROLE_MANAGER("사용자"), ROLE_CUSTOMER("일반사용자");
}

 //roleType에 customer 추가할 필요.. 어차피 비회원은 user테이블안의 값이 모두 null 이여야한다.
