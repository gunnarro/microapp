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
		.table-condensed>tfoot>tr>td {padding: 1px;}		
		.col-icon-fixed {width: 25px;}
	</style>
<script type="text/javascript">
$(document).ready(function () {
    (function ($) {
        $('#filterInputId').keyup(function () {
            var rex = new RegExp($(this).val(), 'i');
            $('.table-tbody tr').hide();
            //console.log("id: " + $('.table-tbody tr').attr('id'));
            $('.table-tbody tr').filter(function () {
                return rex.test($(this).text());
            }).show();
        })
    }(jQuery));
});
</script>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<fmt:setLocale value="no_NO" scope="session"/>
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/menu/1" htmlEscape="true" />" >Diet Menu</a></li>
        	<li class="active">Mine Valg</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Mine Valg</h4>
			<h5>
					<spring:url value="/diet/mychoices/statistic/log" var="controlledByTableUrl" />
					<a href="${fn:escapeXml(controlledByTableUrl)}" >Vis Kontrollert av oversikt</a><br>
					<spring:url value="/diet/mychoices/statistic/graph" var="controlledByGraphUrl" />
					<a href="${fn:escapeXml(controlledByGraphUrl)}" >Vis Kontrollert av graf</a><br>
					<spring:url value="/diet/mychoices/statistic/top10" var="top10choicesUrl" />
					<a href="${fn:escapeXml(top10choicesUrl)}" >Vis mest valget</a>
			</h5>
		</div>

		<c:if test="${missingMealsMap != null}">
			<jsp:include page="diet-mychoices-missing-meals.jsp" />
		</c:if>
		
		<div class="input-group input-group-margin" >
				<span class="input-group-addon">
					<i class="glyphicon glyphicon-search"></i>
				</span>
				<label for="filterInputId" class="sr-only">Filter</label>
				<input id="filterInputId" type="text" class="form-control" placeholder="Filter text" />
		</div>
		
		<table id="myChoicesTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>
			<thead>
				<tr class="info">
					<th colspan="4" class="text-left"><small>Mine valg fra ${period}</small></th>
					<th colspan="2" class="text-right">
						<spring:url value="/diet/mychoices/new/{menuId}/{forDate}/" var="editUrl" htmlEscape="true">
							<spring:param name="menuId" value="1" />
							<spring:param name="forDate" value="today" />
						</spring:url> 
						<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">
							<span>Legg til</span>
						</a>
					</th>
				</tr>
			</thead>
			<tbody class="table-tbody">
				<c:set var="nameColor" value="label label-primary" />
				<c:set var="itemIcon" value="glyphicon glyphicon-check" />
				<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
				<c:set var="txtColor" value="text-muted" />
				<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
				<c:set var="nameColor" value="label label-primary" />
				<c:set var="selected" value="glyphicon glyphicon-stop checked-color" />
				<c:set var="notselected" value="glyphicon glyphicon-stop unchecked-color" />
				
				<c:forEach var="mapEntry" items="${menuItemMap}" varStatus="loop">	
					<jsp:useBean id="dateValue" class="java.util.Date"/>
					<jsp:setProperty name="dateValue" property="time" value="${mapEntry.key}"/>
					<fmt:formatDate var="createdDate" value="${dateValue}" pattern="EEEE dd.MM.yy" />
					<c:choose>
						<c:when test="${fn:startsWith(createdDate, 'søn')}">
							<c:set var="dateColor" value="label label-primary" />
						</c:when>
						<c:when test="${fn:startsWith(createdDate, 'lør')}">
							<c:set var="dateColor" value="label label-danger" />
						</c:when>
						<c:when test="${fn:startsWith(createdDate, 'man')}">
							<c:set var="dateColor" value="label label-success" />
						</c:when>
						<c:when test="${fn:startsWith(createdDate, 'tir')}">
							<c:set var="dateColor" value="label label-info" />
						</c:when>
						<c:when test="${fn:startsWith(createdDate, 'ons')}">
							<c:set var="dateColor" value="label label-warning" />
						</c:when>
						<c:when test="${fn:startsWith(createdDate, 'tor')}">
							<c:set var="dateColor" value="label label-yellow" />
						</c:when>
						<c:when test="${fn:startsWith(createdDate, 'fre')}">
							<c:set var="dateColor" value="label label-purple" />
						</c:when>
						<c:otherwise>
					    	<c:set var="dateColor" value="label label-muted" />
					    </c:otherwise>
					</c:choose>
					
				<tr id="title-row_${loop.index}" class="active-row">
					<td colspan="5" class="text-left">
			            <span class="${dateColor}">${createdDate}</span>
					</td>
					<!-- hack for jquery filter in order to link tilte to data row in search result -->
					<td hidden="true">${mapEntry.value}</td>    
				</tr>
				<c:forEach var="menuItem" items="${mapEntry.value}">
					<tr id="data-row_${loop.index}">
						<!-- hack for jquery filter in order to link data row to tiltle in search result-->
						<td hidden="true">${createdDate}</td> 
						<c:set var="controlledByUserIcon" value="glyphicon glyphicon-king king-color" />
						<c:choose>
							<c:when test="${menuItem.controlledByUserId == 6}">
								<c:set var="controlledByUserIcon" value="glyphicon glyphicon-queen queen-color" />
							</c:when>
							<c:when test="${menuItem.controlledByUserId == 4}">
								<c:set var="controlledByUserIcon" value="glyphicon glyphicon-pawn pawn-color" />
							</c:when>
						</c:choose>
						
						<c:set var="txtColor" value="text-muted" />
						<c:if test="${menuItem.hasConflict()}">
							<c:set var="txtColor" value="text-danger" />
							<c:set var="controlledByUserIcon" value="${controlledByUserIcon} error-color" />
						</c:if>
					
						<td class="col-icon-fixed" style="border-top: none;">
							<spring:url value="/diet/log/event/view/{logId}" var="eventLogUrl" htmlEscape="true">
								<spring:param name="logId" value="${menuItem.fkLogId}" />
							</spring:url>
							<a href="${fn:escapeXml(eventLogUrl)}" title="Se event log">
								<i class="${controlledByUserIcon}" ></i>
							</a>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="${txtColor}">${menuItem.name}</span></small>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="${txtColor}">${menuItem.description}</span></small>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="badge badge-info">${menuItem.selectedCount}</span></small>
						</td>
						<td class="col-icon-fixed" style="border-top: none;">
							<spring:url value="/diet/mychoices/delete/{id}" var="deleteUrl">
									<spring:param name="id" value="${menuItem.primaryKeyId}" />
							</spring:url>
							<small><a href="${fn:escapeXml(deleteUrl)}" ><i class="glyphicon glyphicon-trash" ></i></a></small>
						</td>
					</tr>
					<tr id="trend-row_${loop.index}">
						<td colspan="2" style="border-top: none;"></td>
						<td colspan="1" class="text-left" style="border-top: none;">
								<small>Trend:</small>
								<c:forEach var="keyValuePair" items="${menuItem.selectionTrends}">
									<c:if test="${keyValuePair.value == 'false'}">
										<small><a href="#" title="Ikke valgt"><i class="${notselected}"></i></a></small>
									</c:if>
									<c:if test="${keyValuePair.value == 'true'}">
										<small><a href="#"><i class="${selected}" title="Valgt ${keyValuePair.key}"></i></a></small>
									</c:if>
								</c:forEach>
						</td>
						<td colspan="2" style="border-top: none;"></td>
					</tr>
				</c:forEach>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
				</tr>
				<tr class="info">
					<td colspan="4">
						<small>
							<span><i class="glyphicon glyphicon-king king-color"></i> for Pappa, </span>
							<span><i class="glyphicon glyphicon-queen queen-color"></i> for Mamma,</span>
							<span><i class="glyphicon glyphicon-pawn pawn-color"></i> for Pepilie,</span><br/>
							<span>Hvor <span class="label label-danger">rødt</span> ikon indikerer at det har vært konflikter rundt måltidet, se da <a href="${fn:escapeXml(eventLogUrl)}" title="Se event log">her</a> eller klikk på ikonet</span>
						</small>
					</td>
					<td colspan="2" class="text-right">
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