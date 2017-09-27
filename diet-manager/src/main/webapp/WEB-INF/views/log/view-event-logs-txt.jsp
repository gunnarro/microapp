<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">

		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/log/events" htmlEscape="true" />">Event Logs</a></li>
			<li class="active">Event Log Text</li>
		</ul>

		<div class="page-header">
			<h4>Event Log Text</h4>
		</div>
		<div class="main">
			<c:forEach var="log" items="${logs}" varStatus="loop">
				<section class="post">
					<header>
						<h4 class="post-title">				
							<fmt:formatDate value="${log.createdDate}" pattern="EEEE dd.MM.yyyy" />
							<fmt:formatDate value="${log.createdDate}" pattern="HH:mm" />
							${log.title}
						</h4>
						<h5>Opprettet av ${log.createdByUser}</h5>
					</header>
					<p class="post-content">
						<small>${log.content}</small>
					</p>
				</section>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="../public/footer.jsp" />
</body>
</html>