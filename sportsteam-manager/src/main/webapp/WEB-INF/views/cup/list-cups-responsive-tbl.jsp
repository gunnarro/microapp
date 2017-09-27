<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="table-responsive">
    <table class="table table-striped table-bordered">
      <thead>
        <tr>
          <th>#</th>
          <th>Date</th>
          <th>Time</th>
          <th>Name</th>
          <th>Venue</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
      	<c:forEach var="cup" items="${cupList}">
			<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-not-played-color" />
			<c:if test="${cup.finished}">
				<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-played-color" />
			</c:if>
	        <tr>
	          <td><span class="${statusIconCls}"></span></td>
	          <td><fmt:formatDate value="${cup.startDate}" pattern="dd.MM.yyyy" /></td>
	          <td><fmt:formatDate value="${cup.startDate}" pattern="HH:mm" /></td>
	          <td><c:out value="${cup.cupName}" /></td>
	          <td><c:out value="${cup.venue}" /></td>
	          <td>
	          	<spring:url value="/cup/edit/{cupId}" var="editUrl">
					<spring:param name="cupId" value="${cup.id}" />
					<spring:param name="teamId" value="${cup.fkTeamId}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/cup/delete/{cupId}/{teamId}" var="deleteUrl">
					<spring:param name="cupId" value="${cup.id}" />
					<spring:param name="teamId" value="${cup.fkTeamId}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
				<c:choose>
					<c:when test="${isTeamView == 'true'}">
						<spring:url value="/cup/{cupId}/{teamId}" var="viewUrl">
							<spring:param name="cupId" value="${cup.id}" />
							<spring:param name="teamId" value="${cup.fkTeamId}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">registrer player <span class="badge"><c:out value="${cup.numberOfSignedPlayers}" /></span></a>
					</c:when>
					<c:otherwise>
						<spring:url value="/cup/{cupId}" var="viewUrl">
							<spring:param name="cupId" value="${cup.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">registrer team <span class="badge"><c:out value="${cup.numberOfRegistreredTeams}" /></span></a>
					</c:otherwise>
				</c:choose>
	          </td>
	        </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>