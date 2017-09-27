<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	
 <script>
 $(function() {
     $("#dialog-info").dialog({
        autoOpen: false,  
     });
     $("#sendSMSBtn").click(function() {
        $("#dialog-info").dialog("open");
     });
  });
  </script>
	
	<jsp:include page="../public/body-header.jsp" />
	
	<div class="container-fluid">
		
		<div id="dialog-sms-info" title="Sent SMS status">
		</div>

		<c:if test="${not empty infoMsg}">
			<div id="infoMsgId" class="alert alert-warning" role="alert">${infoMsg}</div>
		</c:if>
		
		<div class="page-header">
			<h4>My Calendar</h4>
			<h5><a href="<spring:url value="/config/listcalendarconfig" htmlEscape="true" />" >Configuration</a></h5>
		</div>

			<div class="btn-group" role="group">
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default dropdown-toggle btn-sm"
						data-toggle="dropdown" aria-expanded="false">
						${searchActivity.type} 
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<c:forEach var="item" items="${calendarUrls}">
							<li><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/${searchActivity.period}/0/${item.type}/${searchActivity.name}" htmlEscape="true" />">${item.type}</a></li>
						</c:forEach>
						<li class="divider"></li>
						<li><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/${searchActivity.period}/0/all/all" htmlEscape="true" />">All</a></li>
					</ul>
				</div>
				
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-default dropdown-toggle btn-sm"
						data-toggle="dropdown" aria-expanded="false">
						${searchActivity.name} 
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<c:forEach var="teamName" items="${names}">
							<li><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/${searchActivity.period}/0/${searchActivity.type}/${teamName}" htmlEscape="true" />">${teamName}</a></li>
						</c:forEach>	
						<li class="divider"></li>
						<li><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/${searchActivity.period}/0/${searchActivity.type}/all" htmlEscape="true" />">All</a></li>
					</ul>
				</div>
			</div>

			<p></p>
			
    		<ul class="nav nav-tabs">
    			<li class="${searchActivity.period == 'day' ? 'active' : 'inactive'}"><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/day/0/${searchActivity.type}/${searchActivity.name}" htmlEscape="true" />">Day</a></li>
				<li class="${searchActivity.period == 'week' ? 'active' : 'inactive'}"><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/week/0/${searchActivity.type}/${searchActivity.name}" htmlEscape="true" />">Week</a></li>
				<li class="${searchActivity.period == 'month' ? 'active' : 'inactive'}"><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/month/0/${searchActivity.type}/${searchActivity.name}" htmlEscape="true" />">Month</a></li>
				<li class="${searchActivity.period == 'year' ? 'active' : 'inactive'}"><a href="<spring:url value="/calendar/listevents/${searchActivity.reload}/year/0/${searchActivity.type}/${searchActivity.name}" htmlEscape="true" />">Year</a></li>
    		</ul>

			<jsp:include page="list-events.jsp" />
			
			<jsp:include page="../public/footer.jsp" />
		</div>

</body>

</html>