<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="pull-right">
					<spring:url value="/calendar/event/edit/{eventId}" var="editUrl">
						<spring:param name="eventId" value="${event.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">${event.summary}</h1>
			</div>

			<div class="panel-body center-block">
				<dl class="dl-horizontal">
				    
				    <dt>Start time</dt>
					<dd><fmt:formatDate value="${event.startDate}" pattern="dd.MM.yyyy HH:mm" /></dd>
					
					<dt>Summary</dt>
					<dd>
						<span class="text-primary">${event.summary}</span>
					</dd>
					
					<dt>Description</dt>
					<dd>
						<span class="text-primary">${event.description}</span>
					</dd>

					<dt>Location</dt>
					<dd>
						<span class="text-primary">${event.location}</span>
					</dd>

					<dt>Participants</dt>
					<dd>${numberOfRegistreredParticipants}/${numberOfFollowers}
						<spring:url value="/calendar/event/listfollowers/{eventId}" var="viewUrl">
							<spring:param name="eventId" value="${event.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}"><span class="glyphicon glyphicon-user player-color"></span></a>
					</dd>
				</dl>
				
				<jsp:include page="event-registrer-participants.jsp">
					<jsp:param name="eventId" value="${event.id}" />
					<jsp:param name="followers" value="${followers}" />
					<jsp:param name="numberOfRegistreredParticipants" value="${numberOfRegistreredParticipants}" />
					<jsp:param name="numberOfFollowers" value="${numberOfFollowers}" />
					<jsp:param name="status" value="${event.status}" />
				</jsp:include>
			</div>
			<!-- end panel body -->
			
			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/calendar/listevents/false/week/0/all/all" var="backUrl">
					</spring:url>
					<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm">back</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>