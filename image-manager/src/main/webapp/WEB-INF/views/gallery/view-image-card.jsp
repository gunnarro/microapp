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
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/gallery" htmlEscape="true" />" >Mine Bilder</a></li>
        	<li class="active">Bilde</li>
    	</ul>
		
		<div class="panel-header clearfix">
			<h4>${imageDetail.title}</h4>
			<div class="pull-right">
				<spring:url value="/image/edit/{id}" var="editImgUrl">
					<spring:param name="id" value="${imageDetail.id}" />
				</spring:url>
		   		<spring:url value="/image/delete/{id}" var="delImgUrl">
					<spring:param name="id" value="${imageDetail.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editImgUrl)}" class="glyphicon glyphicon-edit"></a>
				<a href="${fn:escapeXml(delImgUrl)}" class="glyphicon glyphicon-trash"></a>
			</div>
		</div>
		
		<div class="caption">
			<p><fmt:formatDate value="${imageDetail.createdDate}" pattern="EEEE dd MMM yyyy HH:mm" /></p>
		    <p>${imageDetail.geoLocation}</p>
		    <p>${imageDetail.description}</p>
	    </div>

		<div class="thumbnail">
                  <a href="<c:url value="${imageDetail.mappedAbsoluteFilePath}/${imageDetail.name}" />">
                  	<img src="<c:url value="${imageDetail.mappedAbsoluteFilePath}/${imageDetail.name}" />">
                  </a>
        </div>
		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>

