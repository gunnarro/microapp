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
			<c:when test="${taskGroup['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">
					${taskGroup.parentTask.taskName} -
					<c:if test="${taskGroup['new']}">New </c:if>
					Sub Task
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="subTask" method="${method}" id="edit-task-group-form">
					<form:hidden path="id" value="${taskGroup.id}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<spring:bind path="name">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="name">Task Group Name</form:label>
							<form:input cssClass="form-control" path="name" value="${taskGroup.name}" />
							<form:errors path="name" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="description">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="description">Description</form:label>
							<form:input cssClass="form-control" path="description" value="${taskGroup.description}" />
							<form:errors path="description" cssClass="help-block" />
						</div>
					</spring:bind>

					<c:choose>
						<c:when test="${taskGroup['new']}">
							<button type="submit" class="btn btn-primary">Add Task Group</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update Task Group</button>
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