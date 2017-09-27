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
			<c:when test="${subTask['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty subTask.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/subTask/delete/{subTaskId}" var="deleteUrl">
								<spring:param name="subTaskId" value="${subTask.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${subTask['new']}">New </c:if>
					Sub Task for ${subTask.parentTask.taskName}
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="subTask" method="${method}" id="edit-sub-task-form">
					<form:hidden path="id" value="${subTask.id}" />
					<form:hidden path="fkParentTaskId" value="${subTask.fkParentTaskId}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<!-- 
					<spring:bind path="assignedTeamId">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="assignedTeamId">Assign to team</form:label>
							<form:select cssClass="form-control" path="assignedTeamId">
								<form:options items="${teamList}" itemValue="id" itemLabel="name" />
							</form:select>
						</div>
					</spring:bind>
					 -->

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
							<fmt:formatDate var="formattedStartDate" value="${subTask.startDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="startDate" value="${formattedStartDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="endDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="endDate">End Date</form:label>
							<fmt:formatDate var="formattedEndDate" value="${subTask.endDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="endDate" value="${formattedEndDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="endDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="taskName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="taskName">Sub Task name</form:label>
							<form:input cssClass="form-control" path="taskName" value="${subTask.taskName}" />
							<form:errors path="taskName" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="description">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="description">Description</form:label>
							<form:input cssClass="form-control" path="description" value="${subTask.description}" />
							<form:errors path="description" cssClass="help-block" />
						</div>
					</spring:bind>

					<c:choose>
						<c:when test="${subTask['new']}">
							<button type="submit" class="btn btn-primary">Add Sub task</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update Sub task</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/volunteertask/{volunteerTaskId}" var="editUrl">
						<spring:param name="volunteerTaskId" value="${subTask.fkParentTaskId}" />
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