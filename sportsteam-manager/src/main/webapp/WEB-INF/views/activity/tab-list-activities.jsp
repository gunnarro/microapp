<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />



<body>
	<script type="text/javascript">
	$("#loadingDlg").dialog({
		    modal: true,
		    resizable: false,
		    hide: "slide",
			show: "slide",
			autoOpen: false
		}).dialog("widget").find(".ui-dialog-titlebar").hide();
	
	function sendmail(serviceParam) {
		var serviceUri="activity/sendmail"; 
		var restUrl="/rest/sportsteam/" + serviceUri + "/" + serviceParam;
		var infoMsg = "";
		$.ajax({
			url : restUrl,
			type : "POST",
			data : {
			},
			cache : false,
			contentType : "application/json",
			mimeType : "application/json",
			beforeSend: function() {
				
		    },
			success: function(response) {
				$("#infoMsgId").text(response.result);
			},
			error: function(jqXhr, textStatus, errorThrown) {
				alert("Error sendmail: " + jqXhr + ", status: " + textStatus + ", err: " + errorThrown);
			},
			complete: function () {
				
            }
		});
		return true;
	}
</script>

	<jsp:include page="../public/body-header.jsp" />
	
	<c:if test="${not empty infoMsg}">
			<div id="infoMsgId" class="alert alert-warning" role="alert">${infoMsg}</div>
		</c:if>
	
	<div class="container">
		<div id="sendEmailResultId" class="hidden">
		</div>
		
		<div class="page-header">
			<h4>
				<spring:url value="/team/{teamId}" var="viewUrl">
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${team.name}" /></a>
			</h4>
			<h5>
				Activities for season: <c:out value="${season.period}" />
			</h5>
			<ul class="dropdown-menu" role="menu">
				<c:forEach var="item" items="${seasons}">
					<li><a href="<spring:url value="/listactivities/${team.id}/${item.period}}" htmlEscape="true" />">${item.period}</a></li>
				</c:forEach>
			</ul>
		</div>

		<ul class="nav nav-tabs nav-justified">
			<li class="active"><a data-toggle="tab" href="#sectionUpcoming">Upcoming
					<span class="badge"><c:out value="${numberOfUpcomingActivities}" /></span>
			</a></li>
			<li><a data-toggle="tab" href="#sectionTraining">Trainings <span class="badge"><c:out value="${numberOfTrainings}" /></span></a></li>
			<li><a data-toggle="tab" href="#sectionMatch">Matches <span class="badge"><c:out value="${numberOfMatches}" /></span></a></li>
			<li><a data-toggle="tab" href="#sectionCup">Cups <span class="badge"><c:out value="${numberOfCups}" /></span></a></li>
			<li><a data-toggle="tab" href="#sectionTask">Volunteer Tasks <span class="badge"><c:out value="${numberOfVolunteerTasks}" /></span>
			</a></li>
		</ul>

		<div class="tab-content">
			<div id="sectionUpcoming" class="tab-pane fade in active">
				<jsp:include page="activity-upcoming.jsp">
					<jsp:param name="upcomingActivityList" value="${upcomingActivityList}" />
				</jsp:include>
			</div>

			<div id="sectionTraining" class="tab-pane fade">
				<jsp:include page="../training/list-trainings.jsp" />
			</div>

			<div id="sectionMatch" class="tab-pane fade">
				<jsp:include page="../match/list-matches.jsp" />
			</div>

			<div id="sectionCup" class="tab-pane fade">
				<jsp:include page="../cup/list-cups.jsp">
					<jsp:param name="isTeamView" value="true" />
				</jsp:include>
			</div>

			<div id="sectionTask" class="tab-pane fade">
				<jsp:include page="../volunteertask/list-volunteer-tasks.jsp" />
			</div>
		</div>

		<c:set var="restServiceParam" value="${team.id}/current/upcoming" />
		<a href="#" onclick="sendmail('${restServiceParam}');" class="btn btn-default btn-xs">Send Email</a>

		<spring:url value="/training/new/{teamId}" var="addUrl">
			<spring:param name="teamId" value="${team.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Training</a>

		<spring:url value="/match/new/{teamId}" var="addUrl">
			<spring:param name="teamId" value="${team.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Match</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>