<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>


<datatables:table id="volunteerTask" data="${volunteerTasks}" row="volunteerTask" theme="bootstrap3" cssClass="table table-striped" pageable="true" info="true">
	<datatables:column title="#">
		<span class="glyphicon glyphicon-tag volunteertask-color"></span>
	</datatables:column>

	<datatables:column title="Status" cssStyle="width: 60px;">
		<c:out value="${volunteerTask.status.name}" />
	</datatables:column>

	<datatables:column title="Date" cssStyle="width: 60px;">
		<fmt:formatDate value="${volunteerTask.startDate}" pattern="dd.MM.yyyy" />
	</datatables:column>

	<datatables:column title="Name">
		<c:out value="${volunteerTask.taskName}" />
	</datatables:column>

	<datatables:column title="Assignee">
		<c:if test="${subTask.assignee != null}">
			<a href="/contact/${subTask.assignee.id}">${subTask.assignee.shortName}</a>
		</c:if>
	</datatables:column>

	<datatables:column title="#">
		<spring:url value="/volunteertask/edit/{volunteertaskId}" var="editUrl">
			<spring:param name="volunteertaskId" value="${volunteerTask.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

		<spring:url value="/volunteertask/delete/{volunteertaskId}" var="deleteUrl">
			<spring:param name="volunteertaskId" value="${volunteerTask.id}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

		<spring:url value="/volunteertask/{volunteertaskId}" var="viewUrl">
			<spring:param name="volunteertaskId" value="${volunteerTask.id}" />
		</spring:url>
		<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">registrer <span class="badge"><c:out value="x" /></span></a>
	</datatables:column>
</datatables:table>