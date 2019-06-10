<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<ul class="breadcrumb">
			<li><a
				href="<spring:url value="/calendar/listevents/false/day/0/all/all" htmlEscape="true" />">Calendar</a></li>
			<li><a
				href="<spring:url value="/tournament" htmlEscape="true" />">Tournaments</a></li>
			<c:choose>
				<c:when test="${tournament['new']}">
					<li class="active">New</li>
				</c:when>
				<c:otherwise>
					<li class="active">Update</li>
				</c:otherwise>
			</c:choose>
		</ul>

		<c:choose>
			<c:when test="${tournament['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">

			<div class="panel-heading clearfix">
				<c:if test="${not empty tournament.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/tournament/delete/{tournamentId}"
								var="deleteUrl">
								<spring:param name="tournamentId" value="${tournament.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}"
								class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${tournament['new']}">New </c:if>
					Tournament
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="tournament" method="${method}" id="tournament-generate-form">

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<div class="form-group">
						<form:label path="name">Tournament name</form:label>
						<form:input cssClass="form-control" path="name"
							value="${tournament.name}" />
					</div>

					<div class="form-group">
						<form:label path="name">Tournament type</form:label>
						<form:input cssClass="form-control" path="name"
							value="${tournament.type}" />
					</div>

					<div class="form-group">
						<form:label path="type">Tournament type</form:label>
						<form:select cssClass="form-control" path="type">
							<form:options items="${tournamentTypeOptions}" itemValue="value" itemLabel="type" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="teams">Team names</form:label>
						<textarea class="form-control" rows="5" id="teams"></textarea>
					</div>

					<c:choose>
						<c:when test="${tournament['new']}">
							<button type="submit" class="btn btn-primary">Generate</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update</button>
						</c:otherwise>
					</c:choose>

				</form:form>
			</div>
			<!-- end panle body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/tournament" var="listUrl">
					</spring:url>
					<a href="${fn:escapeXml(listUrl)}" class="btn btn-default btn-sm">Cancel</a>
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