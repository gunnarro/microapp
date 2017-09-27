<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
function incrementAmount() {
    var amount=document.getElementById("amount").value;
    amount=parseInt(amount) + 1;
    document.getElementById("amount").value=amount;
}

function decrementAmount() {
	var amount=document.getElementById("amount").value;
	amount=parseInt(amount) - 1;
    document.getElementById("amount").value=amount;
}

function deleteEvent(eventDate, eventId) {
	var restUrl="/rest/sportsteam/calendar/event/delete/" + eventId;
	$.ajax({
		url : restUrl,
		type : "GET",
		data : {
		},
		cache : false,
		contentType : "application/json",
		mimeType : "application/json",
		before : function() {
		},
		success : function(response) {
			 var element = document.getElementById("row_" + eventId);
			 element.parentNode.removeChild(element);
			 $("#infoMsgId").text(response.result);
		},
		error : function(jqXhr, textStatus, errorThrown) {
			alert("Error delete event: " + jqXhr + ", status: " + textStatus + ", err: "
					+ errorThrown);
		}
	});
	return true;
}

function sendsms(eventId) {
	var serviceUri=""; 
	var restUrl="/rest/sportsteam/calendar/sendsms/" + eventId;
	$.ajax({
		url : restUrl,
		type : "GET",
		data : {
		},
		cache : false,
		contentType : "application/json",
		mimeType : "application/json",
		beforeSend: function() {
	    },
		success: function(response) {
			$("#dialog-sms-info").text(response.result);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$("#dialog-sms-info").text("Error sendsms: " + jqXhr + ", status: " + textStatus + ", err: " + errorThrown);
		},
		complete: function () {
			$("#dialog-sms-info").dialog("open");
        }
	});
	return true;
}
</script>
<style type="text/css">
.table-condensed>thead>tr>th, 
.table-condensed>tbody>tr>th, 
.table-condensed>tfoot>tr>th, 
.table-condensed>thead>tr>td, 
.table-condensed>tbody>tr>td, 
.table-condensed>tfoot>tr>td{padding: 3px;}
</style>

	<table id="calendarEventTbl" class="table table-condensed">
		<caption class="text-right">
			<small>Number of events: ${numberOfMatches} (total: ${numberOfMatchesTotal})</small><br>
			<small>Last updated: <fmt:formatDate value="${lastReloaded}" pattern="dd.MM.yy HH:mm:ss" /></small>
		</caption>
		
		<thead>
			<tr class="info">
				<th colspan="5" class="text-left">
					<spring:url value="/calendar/search/events" var="searchUrl">
					</spring:url>
					<form:form modelAttribute="searchActivity" method="GET" action="${fn:escapeXml(searchUrl)}" id="calendar-search-form-id"> 
						<form:hidden path="reload" value="${searchActivity.reload}"/>
						<form:hidden path="period" value="${searchActivity.period}"/>
						<form:hidden path="amount" value="${searchActivity.amount}"/>
						<form:hidden path="type" value="${searchActivity.type}"/>
						<form:hidden path="name" value="${searchActivity.name}"/>
						<div class="btn-group">
							<button onclick="decrementAmount();return true;" class="btn btn-info btn-xs">
								<span class="glyphicon glyphicon-minus-sign"></span>
							</button>
							<button class="btn btn-primary btn-xs" disabled="disabled">${selectedPeriodTxt}</button>
							<button onclick="incrementAmount();return true" class="btn btn-info btn-xs">
								<span class="glyphicon glyphicon-plus-sign"></span>
							</button>
						</div>
					</form:form>
				</th>
				<th class="text-right">
					<!-- 
					<spring:url value="/calendar/event/copy" var="copyUrl">
					</spring:url>
					<a href="${fn:escapeXml(copyUrl)}" class="btn btn-primary btn-xs">Copy</a>
					 -->
					<spring:url value="/calendar/event/new" var="newUrl">
					</spring:url>
					<a href="${fn:escapeXml(newUrl)}" class="btn btn-primary btn-xs">New</a>
				</th>
            </tr>
		</thead>
		<tbody>
			<c:forEach var="mapEntry" items="${entryEventList}">
				<c:set var="dateColor" value="label label-primary" />
				<c:set var="txtColor" value="text-primary" />
				<c:set var="eventIcon" value="glyphicon glyphicon-user" />
				<c:set var="playedIcon" value="glyphicon glyphicon-check" />
				<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
				<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
				<c:set var="phoneIcon" value="glyphicon glyphicon-phone" />
				<c:set var="emailIcon" value="glyphicon glyphicon-envelope" />
				<c:set var="copyIcon" value="glyphicon glyphicon-copy" />
				<fmt:formatDate var="eventDate" value='${mapEntry.key}' pattern='EEEE, dd. MMM yyyy' />
				<jsp:useBean id="toDay" class="java.util.Date"/>
				
				<c:choose>
					<c:when test="${fn:startsWith(eventDate, 'lør')}">
						<c:set var="dateColor" value="label label-danger" />
					</c:when>
					<c:when test="$fn:startsWith({mapEntry.key, 'søn')}">
						<c:set var="dateColor" value="label label-primary" />
					</c:when>
					<c:when test="${fn:startsWith(eventDate, 'man')}">
						<c:set var="dateColor" value="label label-success" />
					</c:when>
					<c:when test="${fn:startsWith(eventDate, 'tir')}">
						<c:set var="dateColor" value="label label-info" />
					</c:when>
					<c:when test="${fn:startsWith(eventDate, 'ons')}">
						<c:set var="dateColor" value="label label-warning" />
					</c:when>
					<c:when test="${fn:startsWith(eventDate, 'tor')}">
						<c:set var="dateColor" value="label label-yellow" />
					</c:when>
					<c:when test="${fn:startsWith(eventDate, 'fre')}">
						<c:set var="dateColor" value="label label-purple" />
					</c:when>
					<c:otherwise>
				    	<c:set var="dateColor" value="label label-muted" />
				    </c:otherwise>
				</c:choose>
				
				<c:if test="${mapEntry.key lt toDay}" >
					<c:set var="dateColor" value="label label-muted" />
				</c:if>
				
				<tr id="date_${eventDate}" class="active">
					<td colspan="6" class="text-left">
			            <span class="${dateColor}">${eventDate}</span>
					</td>    
				</tr>
				<c:set var="toDay" value="<%=new java.util.Date()%>" />
				<c:forEach var="event" items="${mapEntry.value}">
					<c:if test="${event.startDate.time < toDay.time}" >
						<c:set var="txtColor" value="text-muted" />
						<c:set var="eventTypeIcon" value="glyphicon glyphicon-user muted-color" />
						<c:set var="playedIcon" value="glyphicon glyphicon-check muted-color" />
						<c:set var="editIcon" value="glyphicon glyphicon-edit muted-color" />
						<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle muted-color" />
						<c:set var="phoneIcon" value="glyphicon glyphicon-phone muted-color" />
						<c:set var="emailIcon" value="glyphicon glyphicon-envelope muted-color" />
						<c:set var="copyIcon" value="glyphicon glyphicon-copy muted-color" />
					</c:if>
					<tr id="row_${event.id}">
						<td class="text-left" style="border-top: none;">
							<spring:url value="/calendar/event/{eventId}" var="viewUrl" htmlEscape="true">
								<spring:param name="eventId" value="${event.id}" />
							</spring:url>
							<a href="${fn:escapeXml(viewUrl)}">
								<i class="${playedIcon}"></i>
							</a>
							<small>
								<span class="${txtColor}">&nbsp;<fmt:formatDate value="${event.startDate}" pattern="HH:mm" /> 
								<c:if test="${event.endDate != null}">
									- <fmt:formatDate value="${event.endDate}" pattern="HH:mm" /></span>
								</c:if>
							</small>
						</td>  
						<td class="text-left" style="border-top: none;">
							<!-- <small><span class="${txtColor}">${event.shortType}</span></small> -->
							<!-- <small><span class="${eventTypeIcon}"></span></small> -->
						</td>
						
						<c:choose>
							<c:when test="${event.type == 'match'}">
								<td class="text-left" style="border-top: none;"><small><a class="${txtColor}" href="${event.link}">${event.summary}</a></small></td>
		            			<td class="text-left" style="border-top: none;"><small><a class="${txtColor}" href="https://www.fotball.no/fotballdata/anlegg/hjem/?fiksId=${event.location}&clubs=false&teams=false&persons=false&stadiums=true">${event.location}</a></small></td>
		            		</c:when>
		            		<c:when test="${event.type == 'social'}">
								<td class="text-left" style="border-top: none;">
								<spring:url value="/calendar/event/agenda/{eventId}" var="viewUrl" htmlEscape="true">
										<spring:param name="eventId" value="${event.id}" />
									</spring:url>
									<small><a class="${txtColor}" href="${fn:escapeXml(viewUrl)}" >${event.summary}</a></small>
								</td>
		            			<td class="text-left" style="border-top: none;"><small><a class="${txtColor}" href="https://maps.google.com/?q=${event.location}">${event.location}</a></small></td>
		            		</c:when>
		            		<c:otherwise>
		            			<td class="text-left" style="border-top: none;"><small><a class="${txtColor}" href="${event.link}">${event.summary}</a></small></td>
				    			<!-- use this <td class="text-left" style="border-top: none;"><small><a class="${txtColor}" href="${event.link == null ? '#' : event.link}">${event.summary}</a></small></td>-->
		            			<td class="text-left" style="border-top: none;"><small><a class="${txtColor}" href="https://maps.google.com/?q=${event.location}">${event.location}</a></small></td>
				    		</c:otherwise>
		            	</c:choose>
		            	
		            	<!-- 
						<td class="text-left" style="border-top: none;">
							<c:if test="${event.type == 'training' || event.type == 'tournament' || event.type == 'cup' || event.type == 'social' || event.type == 'volunteer'}">					
								<a href="#" onclick="deleteEvent('${eventDate}','${event.id}');"><i class="${deleteIcon}"></i></a>
								
								<spring:url value="/calendar/event/edit/{eventId}" var="editUrl" htmlEscape="true">
									<spring:param name="eventId" value="${event.id}" />
								</spring:url>
								<a href="${fn:escapeXml(editUrl)}"><i class="${editIcon}"></i></a>
							</c:if>
						</td>
						 -->
						 
						<td style="border-top: none;">
							<c:if test="${event.type == 'meeting' || event.type == 'training' || event.type == 'tournament' || event.type == 'cup' || event.type == 'social' || event.type == 'volunteer'}">
								<div class="btn-group">
									
									<a href="#" onclick="deleteEvent('${eventDate}','${event.id}');" class="btn btn-default btn-xs">
										<span class="${deleteIcon}"></span>
									</a>
									
									<spring:url value="/calendar/event/edit/{eventId}" var="editUrl" htmlEscape="true">
										<spring:param name="eventId" value="${event.id}" />
									</spring:url>
									<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-xs">
										<span class="${editIcon}"></span>
									</a>
									
									<spring:url value="/calendar/event/copy/{eventId}" var="copyEventUrl" htmlEscape="true">
										<spring:param name="eventId" value="${event.id}" />
									</spring:url>
									<a href="${fn:escapeXml(copyEventUrl)}" class="btn btn-default btn-xs">
										<span class="${copyIcon}"></span>
									</a>
								</div>
							</c:if>
						</td>
						<td style="border-top: none;">
							<!-- 
							<c:set var="restServiceParam" value="${event.id}" />
							<spring:url value="/sms/calendarevent/edit/{eventId}" var="smsUrl" htmlEscape="true">
								<spring:param name="eventId" value="${event.id}" />
							</spring:url>
							<a href="${fn:escapeXml(smsUrl)}" class="btn btn-default btn-xs" title="${sms.reminderHistory != null ? 'Sent reminder ' + sms.reminderHistory.summary : 'No reminders sent!'}">
								<span class="${phoneIcon}"></span>
							</a>
							-->
							<!-- 
							<spring:url value="/email/calendarevent/edit/{eventId}" var="emailUrl" htmlEscape="true">
								<spring:param name="eventId" value="${event.id}" />
							</spring:url>
							<a href="${fn:escapeXml(emailUrl)}" class="btn btn-default btn-xs" title="${email.reminderHistory != null ? 'Sent reminder ' + email.reminderHistory.summary : 'No reminders sent!'}">
								<span class="${emailIcon}"></span>
							</a>
							 -->
							<!-- 		
							<a href="#" onclick="sendsms('${event.id}');" title="${sms.reminderHistory != null ? 'Sent reminder ' + sms.reminderHistory.summary : 'No reminders sent!' }">
								 <span class="${phoneIcon}"></span>
							</a>
							 -->
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
		<tfoot>
		<!-- 
			<tr>
				<td>
					<spring:url value="/calendar/event/new" var="newUrl">
					</spring:url>
					<a href="${fn:escapeXml(newUrl)}" class="btn btn-primary btn-sm">New Event</a>
				</td>
			</tr>
		 -->
		</tfoot>
	</table>