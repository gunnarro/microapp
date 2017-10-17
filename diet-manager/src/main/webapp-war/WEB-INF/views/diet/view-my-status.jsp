<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>

	<jsp:include page="../public/body-header.jsp" />
	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h4 class="panel-title">
				Uke Rapport ${period} 
				</h4>
			</div>
			<!-- end panel header -->
			<div class="panel-body center-block">
			    <h4 class="panel-title">Min Status</h4>
			    <div>
				<dl class="dl-horizontal">
					<c:set var="statusIcon" value="icon-happy2" />
					<c:forEach var="keyValuePair" items="${myStatusItemList}">
						<c:choose>
							<c:when test="${keyValuePair.count == -2}">
								<c:set var="statusIcon" value="icon-angry2 color-satisfaction-1" />
							</c:when>
							<c:when test="${keyValuePair.count == -1}">
								<c:set var="statusIcon" value="icon-sad2 color-satisfaction-2" />
							</c:when>
							<c:when test="${keyValuePair.count == 0}">
								<c:set var="statusIcon" value="icon-neutral2 color-satisfaction-3" />
							</c:when>
							<c:when test="${keyValuePair.count == 1}">
								<c:set var="statusIcon" value="icon-smile2 color-satisfaction-4" />
							</c:when>
							<c:when test="${keyValuePair.count == 2}">
								<c:set var="statusIcon" value="icon-happy2 color-satisfaction-5" />
							</c:when>
							<c:otherwise>
								<c:set var="statusIcon" value="icon-neutral color-satisfaction-3" />
							</c:otherwise>
						</c:choose>
						<dt>${keyValuePair.key}</dt>
						<dd><i class="${statusIcon}"></i></dd>
					</c:forEach>
				</dl>
				</div>
				<div>
					<c:if test="${myStatusReportItemList != null}">
						<h4 class="panel-title">Oppsummering</h4>
						<ul class="fa-ul">
							<c:forEach var="item" items="${myStatusReportItemList}">
								<c:if test="${not empty item}">
									<li><small><i class="fa fa-check-square-o checked-color"></i>&nbsp;${fn:trim(item)}</small></li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<div>
					<c:if test="${recentConflictLog != null}">
						<h4 class="panel-title">Konflikt</h4>
						<p><small><fmt:formatDate value="${recentConflictLog.createdDate}" pattern="EEEE dd.MM.yy HH:mm:ss" /></small></p>
						<p>
							<small>${recentConflictLog.content}</small>
						</p>
					</c:if>
				</div>
			</div>
			<!-- end panel body -->
			<div class="panel-footer clearfix">
				<ul class="list-inline">
					<li><span>Vurdering:</span></li>
					<li><span class="icon-angry2 color-satisfaction-1" ></span></li>
					<li><span class="icon-sad2 color-satisfaction-2"></span></li>
					<li><span class="icon-neutral2 color-satisfaction-3"></span></li>
					<li><span class="icon-smile2 color-satisfaction-4"></span></li>
					<li><span class="icon-happy2 color-satisfaction-5"></span></li>
				</ul>
			</div>
			<!-- end panel footer -->
		</div>
		<!-- end panel -->
	</div>
	<!-- end container -->
	<jsp:include page="../public/footer.jsp" />
</body>
</html>

