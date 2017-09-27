<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>
				<spring:url value="/listteams/{clubId}" var="viewUrl">
					<spring:param name="clubId" value="${team.club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${team.name}" /></a>
			</h4>
			<h5>Contacts</h5>
		</div>

		<datatables:table id="contact" data="${contactList}" row="contact" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<c:set var="statusClass" value="text-muted" />
			<c:if test="${contact.active}">
				<c:set var="statusClass" value="text-primary" />
			</c:if>
			<datatables:column title="<>">
				<c:if test="${contact.teamLead}">
					<img src="/resources/images/teamlead.png" alt="teamlead" class="img-rounded teamleader">
				</c:if>
				<c:if test="${contact.coach}">
					<img src="/resources/images/coach.png" alt="coach" class="img-rounded coach">
				</c:if>
			</datatables:column>
			<datatables:column title="Name">
				<spring:url value="/contact/{contactId}" var="editUrl">
					<spring:param name="contactId" value="${contact.id}" />
				</spring:url>
				<a class="${statusClass}" href="${fn:escapeXml(editUrl)}">${contact.fullName}</a>
			</datatables:column>
			<datatables:column title="Mobile">
				<c:if test="${not empty contact.mobileNumber}">
					<a href="tel://${contact.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${contact.mobileNumber}</a>
				</c:if>
			</datatables:column>
			<datatables:column title="Email">
				<c:if test="${not empty contact.emailAddress}">
					<a href="mailto://${contact.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${contact.emailAddress}</a>
				</c:if>
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="/contact/edit/{contactId}" var="editUrl">
					<spring:param name="contactId" value="${contact.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/contact/delete/{contactId}" var="deleteUrl">
					<spring:param name="contactId" value="${contact.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
			</datatables:column>
		</datatables:table>

		<spring:url value="/contact/new/{teamId}" var="addUrl">
			<spring:param name="teamId" value="${team.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Contact</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>