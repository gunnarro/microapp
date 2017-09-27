<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<style type="text/css">
.table-condensed>thead>tr>th, 
.table-condensed>tbody>tr>th, 
.table-condensed>tfoot>tr>th, 
.table-condensed>thead>tr>td, 
.table-condensed>tbody>tr>td, 
.table-condensed>tfoot>tr>td{padding: 3px;}
</style>
	
	<c:if test="${not empty errorMsg}">
		<div id="infoMsgId" class="alert alert-warning" role="alert">${errorMsg}</div>
	</c:if>
	
	<table id="testCalendarEventTbl" class="table table-condensed">
		<c:set var="toDay" value="<%=new java.util.Date()%>" />
		<caption class="text-right">
			<small>Number of events: ${numberOfEvents}</small><br>
			<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
		</caption>
		
		<thead>
			<tr class="info">
				<th colspan="6" class="text-left">
					Test loading data for: ${calendarInfo}
				</th>
            </tr>
		</thead>
		<tbody>
			<c:forEach var="event" items="${eventList}">
				<tr>
					<td class="text-left" style="border-top: none;"><small><fmt:formatDate value="${event.startDate}" pattern="dd.MM.YYY HH:mm" /></small></td>
					<td class="text-left" style="border-top: none;">
					<c:if test="${event.endDate != null}">
						<small><fmt:formatDate value="${event.endDate}" pattern="dd.MM.yyyy HH:mm" /></small>
					</c:if>
					</td>					
					<td class="text-left" style="border-top: none;"><small>${event.id}</small></td>
					<td class="text-left" style="border-top: none;"><small>${event.type}</small></td>
					<td class="text-left" style="border-top: none;"><small>${event.summary}</small></td>
	            	<td class="text-left" style="border-top: none;"><small>${event.location}</small></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>
					<spring:url value="/config/listcalendarconfig" var="backUrl">
					</spring:url>
					<a href="${fn:escapeXml(newUrl)}" class="btn btn-primary btn-sm">Back</a>
				</td>
			</tr>
		</tfoot>
	</table>