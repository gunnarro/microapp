<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:useBean id="toDay" class="java.util.Date" />
	<jsp:include page="../public/body-header.jsp" />

	<div class="container-fluid">
		
		<c:if test="${not empty status}">
			<p class="bg-info">
				<c:out value="${status}" />
			</p>
		</c:if>
		
		<div class="page-header">
			<h4>Bilder</h4>
			<h5><a href="<spring:url value="/import" htmlEscape="true" />">Import</a></h5>
			<h5><a href="<spring:url value="/listuploadedfiles" htmlEscape="true" />">List images</a></h5>
		</div>
		
		<div class="row">
			<c:forEach var="imageDetail" items="${imageDetails}">
				<div class="col-lg-4 col-sm-6 col-xs-12">
					<div class="thumbnail">
					   <div class="caption">
							<spring:url value="/image/edit/{id}" var="editImgUrl">
								<spring:param name="id" value="${imageDetail.id}" />
							</spring:url>
					   		<spring:url value="/image/delete/{id}" var="delImgUrl">
								<spring:param name="id" value="${imageDetail.id}" />
							</spring:url>
							<span class="pull-left">
								<strong>${imageDetail.title}</strong>
							</span>
							<span class="pull-right">
								<a href="${fn:escapeXml(editImgUrl)}" class="glyphicon glyphicon-edit"></a>
								<a href="${fn:escapeXml(delImgUrl)}" class="glyphicon glyphicon-trash"></a>
							</span>
					   </div>
					   <spring:url value="/image/view/{id}" var="viewUrl">
							<spring:param name="id" value="${imageDetail.id}" />
						</spring:url>
	                    <a href="${fn:escapeXml(viewUrl)}" >
	                    	<img src="<c:url value="${imageDetail.mappedAbsoluteFilePath}/${imageDetail.name}" />">
	                    </a>
	                    <div class="caption">
							<p><fmt:formatDate value="${imageDetail.createdDate}" pattern="EEEE dd MMM yyyy HH:mm" /></p>
						    <p>${imageDetail.geoLocation}</p>
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

