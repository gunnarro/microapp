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
			<div class="panel-heading clearfix">
				<div class="pull-right">
					<spring:url value="/volunteertask/edit/{volunteerTaskId}" var="editUrl">
						<spring:param name="volunteerTaskId" value="${volunteerTask.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">${volunteerTask.taskName}</h1>
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
					<dt>Assignee</dt>
					<dd>
						<c:if test="${volunteerTask.assignee != null}">
							<a href="/contact/${volunteerTask.assignee.id}">${volunteerTask.assignee.fullName}</a>
						</c:if>
					</dd>
				</dl>

				<div class="page-header">
					<h5>Sub Tasks</h5>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="table-responsive">
							<table class="table">
								<tbody>
									<c:forEach var="subTask" items="${volunteerTask.subTaskList}" varStatus="loop">
										<c:set var="statusColor" value="default" />
										<c:if test="${subTask.toBePerformedToday}">
											<c:set var="statusColor" value="active" />
										</c:if>
										<c:set var="todayColor" value="label-info" />
										<c:if test="${subTask.toBePerformedToday}">
											<c:set var="todayColor" value="label-warning" />
										</c:if>
										<c:if test="${subTask.finished}">
											<c:set var="statusColor" value="muted" />
											<c:set var="todayColor" value="label-success" />
										</c:if>
										<tr class="${statusColor}">
											<td><span class="label ${todayColor}"><fmt:formatDate value="${subTask.startDate}" pattern="dd.MM.yyyy" /></span> <span
												class="label ${todayColor}"><fmt:formatDate value="${subTask.startDate}" pattern="HH:mm" /></span></td>
											<td><span class="text-capitalize">${subTask.status.name}</span></td>
											<td><a href="/subtask/${subTask.id}">${subTask.name}</a></td>
											<td><c:if test="${subTask.assignee != null}">
													<a href="/contact/${subTask.assignee.id}">${subTask.assignee.fullName}</a>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
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