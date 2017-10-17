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
	
	<div class="container-fluid">

		<c:if test="${not empty infoMsg}">
			<div id="infoMsgId" class="alert alert-warning" role="alert">${infoMsg}</div>
		</c:if>
		
		<div class="page-header">
			<h4>Diet Plans</h4>
		</div>

		<jsp:include page="list-dietplans.jsp" />
			
		<jsp:include page="../public/footer.jsp" />
	</div>

</body>

</html>