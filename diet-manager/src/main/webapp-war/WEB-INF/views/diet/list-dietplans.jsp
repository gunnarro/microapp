<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:useBean id="toDay" class="java.util.Date" />
<table id="dietPlanTbl" class="table table-condensed">
	<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
	<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
	<caption class="text-right">
		<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
	</caption>

	<thead>
		<tr class="info">
			<th colspan="5" class="text-left">${dietMenu.description}</th>
			<%-- 
			<th class="text-right">
				<spring:url value="/diet/dietplan/edit/{dietPlanId}" var="editUrl" htmlEscape="true">
					<spring:param name="dietMenuId" value="${dietMenu.id}" />
				</spring:url> 
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-xs">
					<span class="${editIcon}"></span>
				</a>
			</th>
			--%>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="dietPlan" items="${dietPlans}">
			<c:set var="statusIcon" value="glyphicon glyphicon-unchecked unchecked-color" />
			<c:if test="${dietPlan.active}" >
				<c:set var="statusIcon" value="glyphicon glyphicon-ok-circle checked-color" />
			</c:if>
			<tr id="row_${loop.index}">
				<td class="text-left" style="border-top: none;">
					<small>
						<i class="${statusIcon}"></i>
					</small>
				</td>
				<td class="text-left" style="border-top: none;">
					<spring:url value="/diet/dietplan/{dietPlanId}" var="viewUrl">
						<spring:param name="dietPlanId" value="${dietPlan.id}" />
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" >${dietPlan.name}</a>
				</td>
				<td class="text-left" style="border-top: none;">
					${dietPlan.description}
				</td>
				<td class="text-left" style="border-top: none;">
					<spring:url value="/diet/dietplan/{dietPlanId}" var="editUrl">
						<spring:param name="dietPlanId" value="${dietPlan.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">View</a>
			
					<spring:url value="/diet/dietplan/delete/{dietPlanId}" var="deleteUrl">
						<spring:param name="dietPlanId" value="${dietPlan.id}" />
					</spring:url>
					<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
	</tfoot>
</table>
