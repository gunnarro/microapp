<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<!-- previous setup 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1">
 -->
 
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- mobile friendly style -->
<meta name="viewport" content="width=device-width, initial-scale=1">


<!-- For search engines -->
<meta name="description" content="Free webapp for generating and organization of sport events, such as soccer, bandy, basket, hockey etc." />
<meta name="author" content="SporTzTeam">

<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />">

<title>Pepilie</title>

<spring:url value="/webjars/jquery/3.1.1/jquery.js" var="jQueryJs" />
<script src="${jQueryJs}"></script>

<spring:url value="/webjars/jquery/3.1.1/jquery.min.js" var="jQueryMinJs" />
<script src="${jQueryMinJs}"></script>

<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.css" var="jQueryUICss" />
<link href="${jQueryUICss}" rel="stylesheet" />

<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.js" var="jQueryUI" />
<script src="${jQueryUI}"></script>

<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js" var="jQueryUIMin" />
<script src="${jQueryUIMin}"></script>

<!-- Load bootstrap after jquery -->
<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" var="bootstrapMinCss" />
<link href="${bootstrapMinCss}" rel="stylesheet" />

<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css" var="bootstrapThemeMin" />
<link href="${bootstrapThemeMin}" rel="stylesheet" />

<spring:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js" var="bootstrapMinJS" />
<script src="${bootstrapMinJS}"></script>

<spring:url value="/resources/css/calendarmanager.css" var="calendarmanagerCss" />
<link href="${calendarmanagerCss}" rel="stylesheet" />

<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="/resources/DataTables-1.10.4/media/css/jquery.dataTables.css">
  
<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="/resources/DataTables-1.10.4/media/js/jquery.js"></script>
  
<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="/resources/DataTables-1.10.4/media/js/jquery.dataTables.js"></script>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/responsive/1.0.3/css/dataTables.responsive.css">

<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/responsive/1.0.3/js/dataTables.responsive.js"></script>

<script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.blockUI/2.66.0-2013.10.09/jquery.blockUI.js"></script>

<script type="text/javascript" charset="utf8" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.blockUI/2.66.0-2013.10.09/jquery.blockUI.min.js"></script>

<script type="text/javascript" charset="utf8" src="/resources/js/google-analytics.js"></script>

</head>