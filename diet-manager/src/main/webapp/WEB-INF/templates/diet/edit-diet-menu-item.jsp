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
			<li><a href="<spring:url value="/diet/menu/1" htmlEscape="true" />" >Diet Menu</a></li>
        	<li class="active">Menu Item</li>
    	</ul>
		
		<c:choose>
			<c:when test="${menuItem['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty menuItem.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/diet/menuitem/delete/{menuItemId}" var="deleteUrl">
								<spring:param name="menuItemId" value="${menuItem.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</div>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${menuItem['new']}">Legg til nytt </c:if>
					Måltid for meny ${menuItem.fkDietMenuId}
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="menuItem" method="${method}" id="menu-item-form">
					<form:hidden path="id" value="${menuItem.id}" />
					<form:hidden path="fkDietMenuId" value="${menuItem.fkDietMenuId}" />
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					 <c:if test="${not empty menuItem.id}">
						<fmt:formatDate var="formattedCreatedDate" value="${menuItem.createdDate}" pattern="dd.MM.yyyy HH:mm" />
						<fmt:formatDate var="formattedLastModifiedDate" value="${menuItem.lastModifiedDate}" pattern="dd.MM.yyyy HH:mm" />
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
							<c:when test="${empty menuItem.id}">
								<form:label path="name">Velg måltid</form:label>
								<form:select cssClass="form-control" path="name">
									<form:options items="${menuItemTypes}" itemValue="name" itemLabel="name" />
								</form:select>
							</c:when>
							<c:otherwise>
								<div class="form-group">
									<label>Måltid</label>
									<p class="form-control-static">${menuItem.name}</p>
									<form:hidden path="name" value="${menuItem.name}" />
								</div>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="category">Kategori</form:label>
						<form:select cssClass="form-control" path="category">
							<form:option value="" label="" />
							<form:option value="kjøtt" label="Kjøtt" />
							<form:option value="fisk" label="Fisk" />
							<form:option value="vegetar" label="Vegetar" />
							<form:option value="frukt" label="Frukt" />
						</form:select>
					</div>
					
					<div class="form-group">
						<form:label path="description">Beskrivelse</form:label>
						<form:input cssClass="form-control" path="description" value="${menuItem.description}" />
						<form:errors path="description" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="description">Energi (KCal)</form:label>
						<form:input cssClass="form-control" path="energy" value="${menuItem.energy}" />
						<form:errors path="energy" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="imageLink">Link til bilde</form:label>
						<form:input cssClass="form-control" path="imageLink" value="${menuItem.imageLink}" />
						<form:errors path="imageLink" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<c:if test="${not empty menuItem.imageLink}">
						<div class="thumbnail">
							<img src="${menuItem.imageLink}">
						</div>
						</c:if>
					</div>
										
					<p />

					<c:choose>
						<c:when test="${menuItem['new']}">
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
						<spring:param name="menuId" value="1"/>
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