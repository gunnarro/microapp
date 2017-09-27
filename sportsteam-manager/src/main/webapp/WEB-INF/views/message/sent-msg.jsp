<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		Message is successfully sent!
		<a href="/calendar/listevents/false/day/0/all/all">Back</a>
	</div>
	<!-- end container -->

	<jsp:include page="../public/footer.jsp" />
</body>
</html>