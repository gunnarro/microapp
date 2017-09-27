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
			<div class="panel-heading clearfix">
				<c:if test="${not empty volunteerTask.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/volunteerTask/delete/{volunteerTaskId}" var="deleteUrl">
								<spring:param name="volunteerTaskId" value="${volunteerTask.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${volunteerTask['new']}">New </c:if>
					VolunteerTask
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="volunteerTask" method="${method}" id="edit-volunteer-task-form">
					<form:hidden path="id" value="${volunteerTask.id}" />
					<form:hidden path="fkSeasonId" value="${volunteerTask.fkSeasonId}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<c:choose>
						<c:when test="${volunteerTask['new']}">
							<div class="form-group">
								<form:label path="fkClubId">Club</form:label>
								<form:select cssClass="form-control" path="fkClubId">
									<form:options items="${clubList}" itemValue="id" itemLabel="fullName" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Assign to club</label>
								<p class="form-control-static">${volunteerTask.club.fullName}</p>
							</div>
						</c:otherwise>
					</c:choose>

					<spring:bind path="fkAssignedToPersonId">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="fkAssignedToPersonId">Assign to person</form:label>
							<form:select cssClass="form-control" path="fkAssignedToPersonId">
								<form:options items="${contactList}" itemValue="id" itemLabel="fullName" />
							</form:select>
						</div>
					</spring:bind>

					<div class="form-group">
						<form:label path="fkTaskStatusId">Status</form:label>
						<form:select cssClass="form-control" path="fkTaskStatusId">
							<form:options items="${statusOptions}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>

					<spring:bind path="startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="startDate">Start Date</form:label>
							<fmt:formatDate var="formattedStartDate" value="${volunteerTask.startDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="startDate" value="${formattedStartDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="endDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="endDate">End Date</form:label>
							<fmt:formatDate var="formattedEndDate" value="${volunteerTask.endDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="endDate" value="${formattedEndDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="endDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="deadlineDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="deadlineDate">Deadline date</form:label>
							<fmt:formatDate var="formattedDeadlineDate" value="${volunteerTask.deadlineDate}" pattern="dd.MM.yyyy" />
							<form:input cssClass="form-control" path="deadlineDate" value="${formattedDeadlineDate}" placeholder="dd.MM.yyyy" />
							<form:errors path="deadlineDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="taskName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="taskName">Task name</form:label>
							<form:input cssClass="form-control" path="taskName" value="${volunteerTask.taskName}" />
							<form:errors path="taskName" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="description">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="description">Description</form:label>
							<form:input cssClass="form-control" path="description" value="${volunteerTask.description}" />
							<form:errors path="description" cssClass="help-block" />
						</div>
					</spring:bind>

					<c:choose>
						<c:when test="${volunteerTask['new']}">
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
					<spring:url value="/listvolunteertasks/{clubId}/{season}" var="editUrl">
						<spring:param name="clubId" value="${volunteerTask.fkClubId}" />
						<spring:param name="season" value="${volunteerTask.season.period}" />
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