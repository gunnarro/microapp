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
			<h4>SportTeams</h4>
			<h5>Clubs</h5>
		</div>

		<datatables:table id="club" data="${clubList}" row="club" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<datatables:column title="Name">
				<spring:url value="/club/{clubId}" var="viewUrl">
					<spring:param name="clubId" value="${club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${club.name}" /></a>
			</datatables:column>
			<datatables:column title="Department">
				<c:out value="${club.departmentName}" />
			</datatables:column>
			<datatables:column title="Stadium">
				<c:out value="${club.stadiumName}"></c:out>
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="/club/delete/{clubId}" var="deleteUrl">
					<spring:param name="clubId" value="${club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
			
				<spring:url value="/listteams/{clubId}" var="viewUrl">
					<spring:param name="clubId" value="${club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">teams <span class="badge"><c:out value="${club.numberOfTeams}" /></span></a>
				
				<spring:url value="/listreferees/{clubId}" var="viewUrl">
					<spring:param name="clubId" value="${club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">referees <span class="badge"><c:out value="${club.numberOfReferees}" /></span></a>
			
				<spring:url value="/listvolunteertasks/{clubId}/current" var="viewUrl">
					<spring:param name="clubId" value="${club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">volunteer tasks <span class="badge"><c:out value="x" /></span></a>
				
			</datatables:column>
		</datatables:table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>