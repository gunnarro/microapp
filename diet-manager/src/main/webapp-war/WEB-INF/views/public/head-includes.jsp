<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- previous setup 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1">
 -->

<!-- for html5 -->
<meta charset="UTF-8">
<!-- support html 4.01 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

<!-- google chart import AJAX API -->
<!--  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script> -->
<spring:url value="/resources/js/google-chart-loader.js" var="gchartloaderJs" />
<script src="${gchartloaderJs}"></script>

<!-- 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
 -->
 
<!-- for sorting, filer, etc. list, tables, etc  -->
<spring:url value="/resources/js/list_v1.5.0.js" var="listJs" />
<script src="${listJs}"></script>


<!-- dietmanager custom -->
<spring:url value="/resources/css/dietmanager.css" var="dietmanagerCss" />
<link href="${dietmanagerCss}" rel="stylesheet" />

<spring:url value="/resources/css/bootstrap-social.css" var="bootstrapSocialCss" />
<link href="${bootstrapSocialCss}" rel="stylesheet" />

<spring:url value="/resources/js/dietmanager.js" var="dietmanagerJs" />
<script src="${dietmanagerJs}"></script>

<spring:url value="/resources/css/icomoon-icon-font.css" var="icomoonCss" />
<link href="${icomoonCss}" rel="stylesheet" />
