<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/dietplan/current" htmlEscape="true" />" >Diet Plan</a></li>
        	<li class="active">Bytteliste</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Bytteliste</h4>
		</div>

		<table id="dietProductChangeTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th colspan="2" class="text-left">Bytteliste for produkter</th>
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
				<c:forEach var="foodProduct" items="${foodProducts}">
					<c:set var="nameColor" value="label label-primary" />
					<c:set var="txtColor" value="text-muted" />
					<c:set var="itemIcon" value="glyphicon glyphicon-check" />
					<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
					<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
					<c:set var="nameColor" value="label label-primary" />
					
					<tr id="date_${foodProduct.id}" class="active">
						<td colspan="2" class="text-left">
							<span class="${nameColor}">${foodProduct.name}</span>
						</td>
					</tr>

					<c:forEach var="keyValuePair" items="${foodProduct.productEquivalents}">
						<tr id="row_${foodProduct.id}">
							<td colspan="2" class="text-left" style="border-top: none;">
								<small><i class="${itemIcon}"></i><span class="${txtColor}">&nbsp;${keyValuePair.value}</span></small>
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
						<spring:url value="/diet/listdietplans" var="backUrl" >
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