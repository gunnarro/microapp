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
	<fmt:setLocale value="no_NO" scope="session"/>
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/mychoices" htmlEscape="true" />" >Mine Valg</a></li>
        	<li class="active">Mine Valg Top 10</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Mine Valg - Top 10</h4>
		</div>
		
		<table id="top10TblId" class="table table-condensed">
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>
			<thead>
				<tr class="info">
					<th class="text-left" style="border-buttom: 1;"><small>Måltid</small></th>
					<th class="text-left" style="border-buttom: 1;"><small>Beskrivelse</small></th>
					<th class="text-left" style="border-buttom: 1;"><small>Antall</small></th>
				</tr>
			</thead>
			<tbody>
			    <c:set var="index" value="0" />
				<c:set var="sumNumberOfMeals" value="0" />
				<c:set var="sumNumberOfConflicts" value="0" />
				<c:set var="previousName" value="default" />
				<c:forEach var="keyValuePair" items="${top10List}">	
					<c:set var="sumNumberOfMeals" value="${sumNumberOfMeals + keyValuePair.count}" />
					<c:set var="sumNumberOfConflicts" value="${sumNumberOfConflicts + keyValuePair.totalCount}" />		
					<c:if test="${keyValuePair.key != previousName}" >
						<c:set var="index" value="0" />
						<tr class="active">
							<td colspan="3" class="text-left">
								<span class="label label-primary">${keyValuePair.key}</span>
							</td>
						</tr>
					</c:if>
					<c:set var="previousName" value="${keyValuePair.key}" />
					<c:set var="index" value="${index + 1}" />
					<tr>
						<td class="text-left" style="border-top: none;">${index}.</td>
						<td class="text-left" style="border-top: none;">
						<spring:url value="/diet/menuitem/view/{menuItemId}" var="viewUrl" htmlEscape="true">
							<spring:param name="menuItemId" value="${keyValuePair.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}">
							<small><span>${keyValuePair.value}</span></small>
						</a>
						</td>
						<td class="text-left" style="border-top: none;">${keyValuePair.count}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
			</tfoot>
		</table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>