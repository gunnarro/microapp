<!DOCTYPE html>
<html lang="en">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="../public/head-tag.jsp" />

<body>

	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">Volunteer Task</h1>
			</div>

			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>Status</dt>
					<dd>${volunteerTask.status.name}</dd>

					<dt>Name</dt>
					<dd>${volunteerTask.taskName}</dd>

					<dt>Description</dt>
					<dd>${volunteerTask.description}</dd>

					<dt>Club</dt>
					<dd>${volunteerTask.club.fullName}</dd>

					<dt>Start Date</dt>
					<dd>
						<fmt:formatDate value="${volunteerTask.startDate}" pattern="dd.MM.yyyy" />
					</dd>
					<dt>End Date</dt>
					<dd>
						<fmt:formatDate value="${volunteerTask.endDate}" pattern="dd.MM.yyyy" />
					</dd>
					<dt>Deadline Date</dt>
					<dd>
						<fmt:formatDate value="${volunteerTask.deadlineDate}" pattern="dd.MM.yyyy" />
					</dd>
				</dl>

				<div class="page-header">
					<h5>Sub Tasks</h5>
				</div>

				<ul class="list-group">
					<c:forEach var="subTask" items="${volunteerTask.taskList}" varStatus="loop">
						<li class="list-group-item">
							<a href="/subtask/${subTask.id}">${subTask.name} ${subTask.description}</a> 
							<spring:url value="/subtask/delete/{subTaskId}" var="deleteUrl">
								<spring:param name="subTaskId" value="${subTask.id}" />
							</spring:url> 
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default btn-xs">Assign To</a>
							
							<spring:url value="/subtask/delete/{subTaskId}" var="deleteUrl">
								<spring:param name="subTaskId" value="${subTask.id}" />
							</spring:url> 
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</li>
						
					</c:forEach>
				</ul>

				<div class="pull-right">
					<spring:url value="/subtask/new/{volunteerTaskId}" var="editUrl">
						<spring:param name="volunteerTaskId" value="${volunteerTask.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">New Sub Task</a>
				</div>

			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listvolunteertasks/{clubId}/current" var="editUrl">
						<spring:param name="clubId" value="${volunteerTask.fkClubId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Back</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>