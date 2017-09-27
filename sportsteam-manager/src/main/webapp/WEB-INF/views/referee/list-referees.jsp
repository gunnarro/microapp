<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>
				<spring:url value="/listclubs" var="viewUrl">
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${clubName}" /></a>
			</h4>
			<h5>Referees</h5>
		</div>

		<datatables:table id="referee" data="${refereeList}" row="referee" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<datatables:column title="Name">
				<spring:url value="/referee/{refereeId}" var="editUrl">
					<spring:param name="refereeId" value="${referee.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}"><c:out value="${referee.fullName}" /></a>
			</datatables:column>
			<datatables:column title="Mobile">
				<c:if test="${not empty referee.mobileNumber}">
					<a href="tel://${referee.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${referee.mobileNumber}</a>
				</c:if>
			</datatables:column>
			<datatables:column title="Email">
				<c:if test="${not empty referee.emailAddress}">
					<a href="mailto://${referee.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${referee.emailAddress}</a>
				</c:if>
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="/referee/edit/{refereeId}" var="editUrl">
					<spring:param name="refereeId" value="${referee.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/referee/delete/{refereeId}" var="editUrl">
					<spring:param name="refereeId" value="${referee.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-danger btn-xs">delete</a>

			</datatables:column>
		</datatables:table>

		<spring:url value="/referee/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Referee</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>