<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<script>
		$(function() {
			$("#startDateId").datepicker();
		});

		$(function() {
			$("#formattedDeadlineDate").datepicker();
		});
	</script>

	<div class="container">

		<c:choose>
			<c:when test="${cup['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">
					<c:if test="${cup['new']}">New </c:if>
					Cup, Season
					<c:out value="${cup.season.period}" />
					, id:
					<c:out value="${cup.id}" />
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="cup" method="${method}" id="edit-cup-form">
					<form:hidden path="id" value="${cup.id}" />
					<form:hidden path="fkSeasonId" value="${cup.fkSeasonId}" />
					<form:hidden path="fkTeamId" value="${cup.fkTeamId}" />
					
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					 
					<spring:bind path="startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="startDate">Start Date</form:label>
							<fmt:formatDate var="formattedStartDate" value="${cup.startDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="startDate" value="${formattedStartDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="deadlineDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="deadlineDate">Deadline date</form:label>
							<fmt:formatDate var="formattedDeadlineDate" value="${cup.deadlineDate}" pattern="dd.MM.yyyy" />
							<form:input cssClass="form-control" path="deadlineDate" value="${formattedDeadlineDate}" placeholder="dd.MM.yyyy" />
							<form:errors path="deadlineDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="cupName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="cupName">Cup name</form:label>
							<form:input cssClass="form-control" path="cupName" value="${cup.cupName}" />
							<form:errors path="cupName" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="clubName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="clubName">Organizer club</form:label>
							<form:input cssClass="form-control" path="clubName" value="${cup.clubName}" />
							<form:errors path="clubName" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="venue">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="venue">Place</form:label>
							<form:input cssClass="form-control" path="venue" value="${cup.venue}" />
							<form:errors path="venue" cssClass="help-block" />
						</div>
					</spring:bind>

					<c:choose>
						<c:when test="${cup['new']}">
							<button type="submit" class="btn btn-primary">Add cup</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update cup</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listactivities/{teamId}/{seasonId}" var="editUrl">
						<spring:param name="teamId" value="${cup.fkTeamId}" />
						<spring:param name="seasonId" value="${cup.fkSeasonId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Cancel</a>
					<spring:url value="/listactivities/{teamId}/{seasonId}" var="editUrl">
						<spring:param name="teamId" value="${cup.fkTeamId}" />
						<spring:param name="seasonId" value="${cup.fkSeasonId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Back</a>
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