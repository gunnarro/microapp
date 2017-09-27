<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:useBean id="toDay" class="java.util.Date" />
	<jsp:include page="../public/diet-body-header.jsp" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
        	<li><a href="<spring:url value="/diet/listdietplans/1" htmlEscape="true" />" >Diet Plans</a></li>
        	<li class="active">Gallery</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Diet Plans Gallery</h4>
			<h5>Meals</h5>
		</div>
		
		<div class="row">
			<c:forEach var="imageDetail" items="${imageDetails}">
				<div class="col-lg-4 col-sm-6 col-xs-12">
					<div class="thumbnail">
	                    <a href="<c:url value="${imageDetail.mappedAbsoluteFilePath}/${imageDetail.name}" />">
	                    	<img src="<c:url value="${imageDetail.mappedAbsoluteFilePath}/${imageDetail.name}" />">
	                    </a>
	                    <div class="caption">
	                    	<h5><fmt:formatDate value="${imageDetail.createdDate}" pattern="EEEE dd MMM yyyy HH:mm" /></h5>
	                        <p>${imageDetail.description}</p>
	                    </div>
	                </div>
				</div>
			</c:forEach>
		</div>
		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>