<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../../../public/head-tag.jsp" />

<script type="text/javascript">
	function startStopTask(btnId, serviceParam, action) {
		var serviceUri="scheduledtask/start/";
		if ( action=="stop" ) {
			serviceUri="scheduledtask/stop/";
	    } 
		var restUrl="/rest/sportsteam/" + serviceUri + "/" + serviceParam;
		$.ajax({
			url : restUrl,
			type : "POST",
			data : {
			},
			cache : false,
			contentType : "application/json",
			mimeType : "application/json",
			before : function() {
				$btn.button('...');
			},
			success : function(response) {
				if ( action=="stop" ) {
					$('#btn_icon_' + btnId).toggleClass("task-start-color");
					$('#task_status_' + btnId).toggleClass("task-stop-color");
				} else {
					$('#btn_icon_' + btnId).toggleClass("task-stop-color");
					$('#task_status_' + btnId).toggleClass("task-start-color");
				}
			},
			error : function(jqXhr, textStatus, errorThrown) {
				alert("Error: " + jqXhr + ", status: " + textStatus + ", err: "
						+ errorThrown);
			}
		});
		return true;
	}
</script>

<body>
	<jsp:include page="../../../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>Scheduled Tasks</h4>
		</div>

		<datatables:table id="taskTbl" data="${taskList}" row="task" 
			theme="bootstrap3" cssClass="table table-striped" pageable="false"
			info="false">

			<c:set var="restServiceParam" value="${task.id}" />

			<c:choose>
				<c:when test="${task.enabled}">
					<c:set var="btnClass" value="glyphicon glyphicon-stop task-stop-color" />
					<c:set var="statusClass" value="glyphicon glyphicon-ok-circle task-start-color" />
					<c:set var="action" value="stop" />
				</c:when>
				<c:otherwise>
					<c:set var="btnClass" value="glyphicon glyphicon-play task-start-color" />
					<c:set var="statusClass" value="glyphicon glyphicon-ok-circle task-stop-color" />
					<c:set var="action" value="start" />
				</c:otherwise>
			</c:choose> 
						
			<datatables:column title="Enabled">
				<span id="task_status_${task.id}" class="${statusClass}" aria-hidden="true"></span>
			</datatables:column>
			
			<datatables:column title="Task name">
				${task.name}
			</datatables:column>
			
			<datatables:column title="Type">
				${task.type}
			</datatables:column>

			<datatables:column title="Team">
				<spring:url value="/team/{teamId}" var="viewUrl">
					<spring:param name="teamId" value="${task.teamId}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">${task.teamId}</a>
			</datatables:column>

			<datatables:column title="Cron Expression">
				${task.cronExpression}
			</datatables:column>

			<datatables:column title="Description">
				${task.description}
			</datatables:column>
			
			<datatables:column title="#">
				<button type="button" class="btn btn-default btn-xs" onclick="startStopTask(${task.id},'${restServiceParam}', '${action}');">
					<span id="btn_icon_${task.id}" class="${btnClass}" aria-hidden="true"></span>
				</button>
					
				<spring:url value="/admin/listscheduledtask/edit/{taskId}" var="editUrl">
					<spring:param name="taskId" value="${task.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/admin/listscheduledtask/delete/{taskId}" var="deleteUrl">
					<spring:param name="taskId" value="${task.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

			</datatables:column>
		</datatables:table>

		<spring:url value="/admin/task/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Task</a>

		<jsp:include page="../../../public/footer.jsp" />
	</div>
</body>

</html>