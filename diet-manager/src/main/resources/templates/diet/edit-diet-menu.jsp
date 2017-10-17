<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/menu/1" htmlEscape="true" />" >Diet Plan</a></li>
        	<li class="active">Menu</li>
    	</ul>
		
		<c:choose>
			<c:when test="${menu['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty menu.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/diet/menu/delete/{menuId}" var="deleteUrl">
								<spring:param name="menuId" value="${menu.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</div>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${menu['new']}">Legg til ny </c:if>
					 Meny
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="menu" method="${method}" id="menu-form">
					<form:hidden path="id" value="${menu.id}" />
					
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					 <c:if test="${not empty menu.id}">
						<fmt:formatDate var="formattedCreatedDate" value="${menu.createdDate}" pattern="dd.MM.yyyy HH:mm" />
						<fmt:formatDate var="formattedLastModifiedDate" value="${menu.lastModifiedDate}" pattern="dd.MM.yyyy HH:mm" />
						<form:hidden path="lastModifiedDate" value="${formattedLastModifiedDate}" />
						<form:hidden path="createdDate" value="${formattedCreatedDate}" />
						<div class="form-group">
							<label>Opprettet dato</label>
							<p class="form-control-static">${formattedCreatedDate}</p>
						</div>
						<div class="form-group">
							<label>Sist endret</label>
							<p class="form-control-static">${formattedLastModifiedDate}</p>
						</div>
					</c:if>
					
					<div class="form-group">
						<c:choose>
							<c:when test="${empty menu.id}">
								<form:label path="name">Navn</form:label>
								<form:input cssClass="form-control" path="name" value="${menu.name}" />
								<form:errors path="name" cssClass="help-block" />
							</c:when>
							<c:otherwise>
								<div class="form-group">
									<label>Navn</label>
									<p class="form-control-static">${menu.name}</p>
									<form:hidden path="name" value="${menu.name}" />
								</div>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="description">Beskrivelse</form:label>
						<form:input cssClass="form-control" path="description" value="${menu.description}" />
						<form:errors path="description" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="active">Activate</form:label>
						<form:checkbox cssClass="form-control"  path="active" value="${menu.active}" />
						<form:errors path="active" cssClass="help-block" />
					</div>
					
					<c:choose>
						<c:when test="${menu['new']}">
							<button type="submit" class="btn btn-primary">Legg til</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Oppdater</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/diet/menu/{menuId}" var="cancelUrl">
						<spring:param name="menuId" value="${menu.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
				</div>
			</div>

			<!-- end panel footer -->
		</div>
		<!-- end panel -->
	</div>
	<!-- end container -->

	<jsp:include page="../public/footer.jsp" />
</body>
</html>