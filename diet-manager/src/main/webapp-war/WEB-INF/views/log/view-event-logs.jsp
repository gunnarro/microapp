<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en">
<head>
<jsp:include page="../public/head-includes.jsp" /> 

<style type="text/css">
.page-header { 
	border-bottom:none; 
}
.panel-heading a:after {
	font-family: 'Glyphicons Halflings';
	content: "\e114";
	float: right;
	color: grey;
}

.panel-heading a.collapsed:after {
	content: "\e080";
}

.panel-heading-sm, .panel-footer-sm {
	height: 28px;
	padding: 4px 10px;
}
pre {
  font-family: "Times New Roman", Georgia, Serif;
  white-space: pre-wrap;
  word-break: normal;
  color: #333;
  background-color: #FFFFFF; 
  padding: 1px;
  margin: 0 0 1px;
  font-size: 12px;
  border: none;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	$("#txtSearchPage").keyup(function() {
	    var search = $(this).val();
	    $(".panel").show();
	    if (search) {
	        $(".panel").not(":containsNoCase(" + search + ")").hide();
	    }
	});
});

$.expr[":"].containsNoCase = function (el, i, m) {
	var search = m[3];
	if (!search) { return false };
	console.log("search: " + search + ", el: " + el);
	return new RegExp(search,"i").test($(el).text());
};

	var options = {
		  valueNames: [ 'panel-title' ]
	};

	var userList = new List('loglist', options);

	function closeAll() {
		$('.panel-collapse.in').collapse('hide');
	}
	function openAll() {
		$('.panel-collapse:not(".in")').collapse('show');
	};
</script>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">

		<div class="page-header">
			<h4>Event Log</h4>
			<!-- 
			<form action="/diet-manager/diet/log/events/filter" method="GET" id="event-log-filter-form" enctype="application/x-www-form-urlencoded">
					<select class="form-control" id="filterBy" name="filterBy">
						<option value="type" label="Type" />
						<option value="title" label="Tittel" />
						<option value="content" label="Innhold" />
					</select>
					<input id="filterValue" name="filterValue" class="search" placeholder="filter value" />
	  				<input type="submit" class="btn btn-primary" />
  			</form>
  			
			<div class="btn-group btn-group-xs" role="group">
				<button onclick="openAll();" type="button" class="btn btn-default">Lukk alle</button>
				<button onclick="closeAll();" type="button" class="btn btn-default">Åpne alle</button>
			</div>
			 -->
			<div class="input-group input-group-margin" >
				<span class="input-group-addon">
					<i class="glyphicon glyphicon-search"></i>
				</span>
				<label for="txtSearchPage" class="sr-only">Filter log</label>
				<input id="txtSearchPage" type="text" class="form-control" placeholder="Filter text" />
			</div>
			<p></p>
			<div class="pull-right">
				<spring:url value="/diet/log/event/new" var="newUrl">
				</spring:url>
				<a href="${fn:escapeXml(newUrl)}" class="btn btn-primary btn-xs">Ny logg</a>
				<spring:url value="/diet/log/events/txt" var="viewPlainUrl">
				</spring:url>
				<a href="${fn:escapeXml(viewPlainUrl)}" class="btn btn-primary btn-xs">Vis tekst</a>
			</div>
		</div>
		<!-- End page header -->
		
		<div class="list panel-group" id="accordion">
			<c:forEach var="log" items="${logs}" varStatus="loop">
				<c:if test="${log.level != 'ALL'}">
					<c:choose>
						<c:when test="${log.level == 'INFO'}">
							<c:set var="levelClass" value="label label-info" />
						</c:when>
						<c:when test="${log.level == 'ACTIVITY'}">
							<c:set var="levelClass" value="label label-success" />
						</c:when>
						<c:when test="${log.level == 'REPORT'}">
							<c:set var="levelClass" value="label label-warning" />
						</c:when>
						<c:when test="${log.level == 'CONFLICT'}">
							<c:set var="levelClass" value="label label-danger" />
						</c:when>
				</c:choose>
				<div id="panel-child" class="panel panel-default">
						<div class="panel-heading panel-heading-sm">
							<h6 class="panel-title">
								<small> <span class="label label-primary"> <fmt:formatDate value="${log.createdDate}" pattern="EEEE dd.MM.yyyy" />
								</span> <span class="label label-default"> <fmt:formatDate value="${log.createdDate}" pattern="HH:mm" />
								</span> <span class="${levelClass}">${log.level}</span> <span> <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse_${loop.index}">${log.title}</a>
								</span>
								</small>
							</h6>
						</div>
						<div id="collapse_${loop.index}"
							class="panel-collapse collapse in">
							<div class="panel-body">
								${log.contentHtml}
							</div>
							<div class="panel-footer clearfix panel-footer-sm">
								<div class="pull-left">
									<small><span>Created by ${log.createdByUser}</span></small>
								</div>
								<div class="pull-right">
									<sec:authentication var="user" property="principal" />
									<c:set var="linkCls" value="btn btn-default btn-xs disabled" />
								
									<spring:url value="/diet/log/event/view/{logId}" var="viewUrl">
										<spring:param name="logId" value="${log.id}" />
									</spring:url>
									<a href="${fn:escapeXml(viewUrl)}" class="btn btn-default btn-xs"><small>View Comments(${log.numberOfComments})</small></a>
									
									<c:if test="${log.fkUserId == user.id}">
										<c:set var="linkCls" value="btn btn-default btn-xs" />
									</c:if>
									<spring:url value="/diet/log/event/edit/{logEventId}" var="addUrl">
										<spring:param name="logEventId" value="${log.id}" />
									</spring:url>
									<a href="${fn:escapeXml(addUrl)}" class="${linkCls}"> <i class="glyphicon glyphicon-edit"></i></a>
									<spring:url value="/diet/log/event/delete/{logEventId}" var="deleteUrl">
										<spring:param name="logEventId" value="${log.id}" />
									</spring:url>
									<a href="${fn:escapeXml(deleteUrl)}" class="${linkCls}"> <i class="glyphicon glyphicon-trash"></i></a>
								</div>
							</div>
					</div>
				</div> <!-- panel child -->
			</c:if>
		</c:forEach>
	</div>
</div>
	<jsp:include page="../public/footer.jsp" />
</body>
</html>