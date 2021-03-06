<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- previous setup 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1">
 -->
 
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- mobile friendly style -->
<meta name="viewport" content="width=device-width, initial-scale=1">


<!-- For search engines -->
<meta name="description" content="private web app for logging personal data." />
<meta name="author" content="gunnarro">

<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico" />">

<title>Pepilie</title>

<spring:url value="/webjars/jquery/3.1.1/jquery.js" var="jQueryJs" />
<script src="${jQueryJs}"></script>

<spring:url value="/webjars/jquery/3.1.1/jquery.min.js" var="jQueryMinJs" />
<script src="${jQueryMinJs}"></script>

<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.js" var="jQueryUI" />
<script src="${jQueryUI}"></script>

<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js" var="jQueryUIMin" />
<script src="${jQueryUIMin}"></script>

<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.css" var="jQueryUICss" />
<link href="${jQueryUICss}" rel="stylesheet" />

<!-- Load bootstrap after jquery -->
<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" var="bootstrapMinCss" />
<link href="${bootstrapMinCss}" rel="stylesheet" />

<spring:url value="/webjars/bootstrap/3.3.7/css/bootstrap-theme.min.css" var="bootstrapThemeMin" />
<link href="${bootstrapThemeMin}" rel="stylesheet" />

<spring:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js" var="bootstrapMinJS" />
<script src="${bootstrapMinJS}"></script>

<!-- calendarmanager custom -->
<spring:url value="/resources/css/calendarmanager.css" var="calendarmanagerCss" />
<link href="${calendarmanagerCss}" rel="stylesheet" />
