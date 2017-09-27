<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>


<datatables:table id="training" data="${trainingList}" row="training" theme="bootstrap3" cssClass="table table-striped" pageable="true" info="true">
	<datatables:column title="#" cssStyle="width: 15px !important;">
		<span class="glyphicon glyphicon-tag training-color"></span>
	</datatables:column>
	
	<datatables:column title="Date">
		<span class="label label-primary">${training.startWeekDayName}</span>
		<span class="label label-primary"><fmt:formatDate value="${training.startDate}" pattern="dd.MM.yy" /></span>
		<span class="label label-primary"><fmt:formatDate value="${training.startDate}" pattern="HH:mm" /></span>
	</datatables:column>
	
	<datatables:column title="training">
		<c:out value="${training.type}" />
	</datatables:column>
	
	<datatables:column title="Venue">
		<c:out value="${training.venue}" />
	</datatables:column>
	
	<datatables:column title="#">
		<spring:url value="/training/edit/{trainingId}" var="editUrl">
			<spring:param name="trainingId" value="${training.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

		<spring:url value="/training/delete/{trainingId}" var="deleteUrl">
			<spring:param name="trainingId" value="${training.id}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

		<spring:url value="/training/{trainingId}" var="viewUrl">
			<spring:param name="trainingId" value="${training.id}" />
		</spring:url>
		<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">registrer <span class="badge"><c:out value="${training.numberOfSignedPlayers}" /></span></a>
	</datatables:column>
</datatables:table>