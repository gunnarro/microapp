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

		<!-- 
		<c:choose>
			<c:when test="${agenda['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>
		 -->
		 
		<!-- Panel -->
		<div class="panel panel-default">

			<div class="panel-heading clearfix">
				<c:if test="${not empty agenda.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/calendar/event/agenda/delete/{agendaId}" var="deleteUrl">
								<spring:param name="eventId" value="${agenda.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					${agenda.name}
				</h1>
			</div>

			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="agenda" method="PUT" id="calendar-event-agenda-form">
					<div class="form-group">
						<form:label path="text">Agenda</form:label>
						<form:textarea rows="20" cssClass="form-control" path="text" value="${agenda.text}" placeholder="Message" />
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/calendar/event/agenda/{eventId}" var="cancelUrl">
					  <spring:param name="eventId" value="${agenda.id}" />
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm">Cancel</a>
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