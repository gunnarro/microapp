<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<c:choose>
			<c:when test="${club['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty club.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/club/delete/{clubId}" var="deleteUrl">
								<spring:param name="clubId" value="${club.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${club['new']}">New </c:if>
					club
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="club" method="${method}" id="club-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<c:choose>
						<c:when test="${club['new']}">
							<div class="form-group">
								<form:label path="name">Name</form:label>
								<form:input cssClass="form-control" path="name" value="${club.name}" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Name</label>
								<p class="form-control-static">${club.name}</p>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<form:label path="name">Department</form:label>
						<form:input cssClass="form-control" path="departmentName" value="${club.departmentName}" />
					</div>

					<div class="form-group">
						<form:label path="name">Stadium</form:label>
						<form:input cssClass="form-control" path="stadiumName" value="${club.stadiumName}" />
					</div>

					<div class="form-group">
						<form:label path="name">HomePage</form:label>
						<form:input cssClass="form-control" path="homePageUrl" value="${club.homePageUrl}" />
					</div>
					
					<jsp:include page="../common/address-input.jsp" >
						<jsp:param name="address" value="${club.address}" />
					</jsp:include>
					
					<div class="pull-left">
						<c:choose>
							<c:when test="${club['new']}">
								<button type="submit" class="btn btn-primary">New Club</button>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn btn-primary">Update Club</button>
							</c:otherwise>
						</c:choose>
					</div>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/club/{clubId}" var="editUrl">
						<spring:param name="clubId" value="${club.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Cancel</a>
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