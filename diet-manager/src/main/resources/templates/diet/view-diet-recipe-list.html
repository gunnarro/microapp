<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<head>
	<jsp:include page="../public/head-includes.jsp" />
	<style type="text/css">
		.table-condensed>thead>tr>th, 
		.table-condensed>tbody>tr>th,
		.table-condensed>tfoot>tr>th, 
		.table-condensed>thead>tr>td,
		.table-condensed>tbody>tr>td, 
		.table-condensed>tfoot>tr>td {padding: 3px;}
	</style>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<jsp:useBean id="toDay" class="java.util.Date" />
	<div class="container-fluid">
		
		<div class="page-header">
			<h4>Matoppskrifter</h4>
		</div>

		<table id="recipesTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th colspan="2" class="text-left">Oppskrifter</th>
					<%--
					<th class="text-right"><spring:url value="/diet/dietplan/edit/{dietPlanId}" var="editUrl" htmlEscape="true">
						<spring:param name="dietMenuId" value="1" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-xs">
						<span class="${editIcon}"></span>
					</a></th>
					 --%>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="keyValuePair" items="${recipes}">
					<c:set var="nameColor" value="label label-primary" />
					<c:set var="txtColor" value="text-muted" />
					<c:set var="itemIcon" value="glyphicon glyphicon-check" />
					<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
					<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
					<c:set var="nameColor" value="label label-primary" />
					
					<tr id="date_${keyValuePair.id}" class="active">
						<td colspan="2" class="text-left">
							<span class="${nameColor}">${keyValuePair.key}</span>
						</td>
					</tr>

					<c:forEach var="item" items="${keyValuePair.value}">
						<tr id="row_${foodProduct.id}">
							<td colspan="2" class="text-left" style="border-top: none;">
								<small><i class="${itemIcon}"></i><span class="${txtColor}">&nbsp;${item}</span></small>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
					</td>
				</tr>
				<tr class="info">
					<td colspan="2" class="text-right">
						<spring:url value="/home" var="backUrl" >
							<spring:param name="userId" value="1"/>
						</spring:url>
						<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
					</td>
				</tr>
			</tfoot>
		</table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>