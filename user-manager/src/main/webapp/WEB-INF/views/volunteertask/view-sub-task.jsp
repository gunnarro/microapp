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
					<spring:url value="/subtask/edit/{subTaskId}" var="editUrl">
						<spring:param name="subTaskId" value="${subTask.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">Sub Task - ${subTask.taskName}</h1>
			</div>

			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>Status</dt>
					<dd>${subTask.status.name}</dd>

					<dt>Name</dt>
					<dd>${subTask.taskName}</dd>

					<dt>Description</dt>
					<dd>${subTask.description}</dd>

					<dt>Parent task</dt>
					<dd>
						<a href="/volunteertask/${subTask.parentTask.id}">${subTask.parentTask.taskName}</a>
					</dd>

					<dt>Start Date</dt>
					<dd>
						<fmt:formatDate value="${subTask.startDate}" pattern="dd.MM.yyyy" />
					</dd>
					<dt>End Date</dt>
					<dd>
						<fmt:formatDate value="${subTask.endDate}" pattern="dd.MM.yyyy" />
					</dd>
					<dt>Assignee</dt>
					<dd>
						<c:if test="${subTask.assignee != null}">
							<a href="/contact/${subTask.assignee.id}">${subTask.assignee.fullName}</a>
						</c:if>
					</dd>
				</dl>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/volunteertask/{volunteerTaskId}" var="editUrl">
						<spring:param name="volunteerTaskId" value="${subTask.fkParentTaskId}" />
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