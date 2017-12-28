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
        	<li class="active">Mine Valg Statistikk</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Mine Valg - måltider statestikk</h4>
		</div>
		
		<table id="summaryTblId" class="table table-condensed">
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>
			<thead>
				<tr class="info">
					<th colspan="4" class="text-left">Alle måltider for ${period}</th>
				</tr>
				<tr>
					<th class="text-left" style="border-buttom: 1;"><small>Bruker</small></th>
					<th class="text-left" style="border-buttom: 1;"><small>kontrollert måltider</small></th>
					<th class="text-left" style="border-buttom: 1;"><small>Tilberedt måltider</small></th>
					<th class="text-left" style="border-buttom: 1;"><small>Konflikter</small></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="totalNumberOfControlledMeals" value="0" />
				<c:set var="totalNumberOfPreparedMeals" value="0" />
				<c:set var="totalNumberOfConflicts" value="0" />
				<c:forEach var="sumStatistic" items="${statisticSummaryList}">	
					<c:set var="totalNumberOfControlledMeals" value="${totalNumberOfControlledMeals + sumStatistic.mealsControlledByUserCount}" />
					<c:set var="totalNumberOfPreparedMeals" value="${totalNumberOfPreparedMeals + sumStatistic.mealsPreparedByUserCount}" />
					<c:set var="totalNumberOfConflicts" value="${totalNumberOfConflicts + sumStatistic.mealsCausedConflictCount}" />		
					<tr>
						<td class="text-left" style="border-top: none;"><small>${sumStatistic.userName}</small></td>
						<td class="text-left" style="border-top: none;"><small>${sumStatistic.mealsControlledByUserCount}</small></td>
						<td class="text-left" style="border-top: none;"><small>${sumStatistic.mealsPreparedByUserCount}</small></td>
						<td class="text-left" style="border-top: none;"><small>${sumStatistic.mealsCausedConflictCount}</small></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td class="text-left" style="border-top: 1;"><small>Totalt</small></td>
					<td class="text-left" style="border-top: 1;"><small>${totalNumberOfControlledMeals}</small></td>
					<td class="text-left" style="border-top: 1;"><small>${totalNumberOfPreparedMeals}</small></td>
					<td class="text-left" style="border-top: 1;"><small>${totalNumberOfConflicts}</small></td>
				</tr>
			</tfoot>
		</table>

		<table id="myChoicesStatisticTbl" class="table table-condensed">
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th colspan="4" class="text-left">Oversikt over hvem som har kontrollert måltidene pr. uke</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="mapEntry" items="${statisticMap}">	
					<c:set var="dateColor" value="label label-primary" />
					<c:choose>
							<c:when test="${fn:startsWith(mapEntry.key.value, 'Week')}">
								<c:set var="dateColor" value="label label-primary" />
							</c:when>
							<c:otherwise>
						    	<c:set var="dateColor" value="label label-primary" />
						    </c:otherwise>
					</c:choose>
				
					<tr class="active">
						<td colspan="4" class="text-left">
				            <span class="${dateColor}">${mapEntry.key.value}</span>
						</td>    
					</tr>
					<tr>
						<th class="text-left" style="border-buttom: 1;"><small>Bruker</small></th>
						<th class="text-left" style="border-buttom: 1;"><small>Kontrollert måltider</small></th>
						<th class="text-left" style="border-buttom: 1;"><small>Tilberedt måltider</small></th>
						<th class="text-left" style="border-buttom: 1;"><small>Konflikter</small></th>
					</tr>
					<c:set var="userNumberOfControlledMeals" value="0" />
					<c:set var="userNumberOfPreparedMeals" value="0" />
					<c:set var="userNumberOfConflicts" value="0" />
					<c:forEach var="mealStatistic" items="${mapEntry.value}">
						<c:set var="userNumberOfControlledMeals" value="${userNumberOfControlledMeals + mealStatistic.mealsControlledByUserCount}" />
						<c:set var="userNumberOfPreparedMeals" value="${userNumberOfPreparedMeals + mealStatistic.mealsPreparedByUserCount}" />
						<c:set var="userNumberOfConflicts" value="${userNumberOfConflicts + mealStatistic.mealsCausedConflictCount}" />		
						<tr>
							<td class="text-left" style="border-top: none;"><small>${mealStatistic.userName}</small></td>
							<td class="text-left" style="border-top: none;"><small>${mealStatistic.mealsControlledByUserCount}</small></td>
							<td class="text-left" style="border-top: none;"><small>${mealStatistic.mealsPreparedByUserCount}</small></td>
							<td class="text-left" style="border-top: none;"><small>${mealStatistic.mealsCausedConflictCount}</small></td>
						</tr>
					</c:forEach>
					<td class="text-left" style="border-top: 1;"><small>Totalt</small></td>
					<td class="text-left" style="border-top: 1;"><small>${userNumberOfControlledMeals}</small></td>
					<td class="text-left" style="border-top: 1;"><small>${userNumberOfPreparedMeals}</small></td>
					<td class="text-left" style="border-top: 1;"><small>${userNumberOfConflicts}</small></td>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
				</tr>
				<tr class="info">
					<td colspan="3">
					</td>
					<td colspan="1" class="text-right">
						<spring:url value="/diet/menu/{dietMenuItemId}" var="backUrl" >
							<spring:param name="dietMenuItemId" value="1"/>
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