<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">

<jsp:include page="../../public/head-tag.jsp" />

<body>
	<jsp:include page="../../public/body-header.jsp" />
	<div class="container">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">${user.userName}</h1>
			</div>

			<div class="panel-body center-block">
			    ${user.id}<br/>
				${user.userName}<br/>
				${user.roles}
			</div>
		</div>
		
		<jsp:include page="../../calendar/list-events.jsp" />
		
		<jsp:include page="../../public/footer.jsp" />
	</div>
</body>

</html>