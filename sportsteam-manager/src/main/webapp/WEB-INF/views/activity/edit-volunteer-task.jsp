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
			<c:when test="${volunteerTask['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">
					<c:if test="${task['new']}">New </c:if>
					Volunteer Task, Season
					<c:out value="${task.season.period}" />
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="task" method="${method}" id="edit-volunteer-task-form">
					<form:hidden path="id" value="${task.id}" />
					<form:hidden path="fkSeasonId" value="${task.fkSeasonId}" />
					<form:hidden path="fkClubId" value="${task.fkClubId}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<c:choose>
						<c:when test="${task['new']}">
							<div class="form-group">
								<form:label path="fkClubId">Club</form:label>
								<form:select cssClass="form-control" path="fkClubId">
									<form:options items="${clubList}" itemValue="id" itemLabel="fullName" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label for="club" class="control-label col-xs-2">Club</label>
								<div class="col-xs-10">
									<p class="form-control-static">${task.club.fullName}</p>
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<spring:bind path="startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="startDate">Start Date</form:label>
							<fmt:formatDate var="formattedStartDate" value="${task.startDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="startDate" value="${formattedStartDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="deadlineDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="deadlineDate">Deadline date</form:label>
							<fmt:formatDate var="formattedDeadlineDate" value="${task.deadlineDate}" pattern="dd.MM.yyyy" />
							<form:input cssClass="form-control" path="deadlineDate" value="${formattedDeadlineDate}" placeholder="dd.MM.yyyy" />
							<form:errors path="deadlineDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="taskName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="taskName">Task name</form:label>
							<form:input cssClass="form-control" path="taskName" value="${task.taskName}" />
							<form:errors path="taskName" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="description">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="description">Description</form:label>
							<form:input cssClass="form-control" path="description" value="${task.description}" />
							<form:errors path="description" cssClass="help-block" />
						</div>
					</spring:bind>

					<c:choose>
						<c:when test="${Task['new']}">
							<button type="submit" class="btn btn-primary">Add Volunteer task</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update Volunteer task</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listactivities/{teamId}/{seasonId}" var="editUrl">
						<spring:param name="teamId" value="${task.fkTeamId}" />
						<spring:param name="seasonId" value="${task.fkSeasonId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Cancel</a>
					<spring:url value="/listactivities/{teamId}/{seasonId}" var="editUrl">
						<spring:param name="teamId" value="${task.fkTeamId}" />
						<spring:param name="seasonId" value="${task.fkSeasonId}" />
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