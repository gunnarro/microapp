<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container-fluid">
		<div class="page-header">
			<h4><spring:message code="weightlog.page.title"/></h4>
			<spring:url value="/diet/body/measurement/graph" var="graphUrl" htmlEscape="true">
			</spring:url> 
			<h5>
				<a href="${fn:escapeXml(graphUrl)}">Vis Graf</a>
			</h5>
		</div>
		
		<h4><spring:message code="weightlog.header.formulas" /></h4>
		<div class="panel-body center-block">
			<dl class="dl-horizontal"> 
				<dt><spring:message code="weightlog.label.bmi" /></dt>
				<dd><spring:message code="weightlog.formula.bmi" /></dd>
				<dt><spring:message code="weightlog.label.geneticheightgirls" /></dt>
				<dd><spring:message code="weightlog.formula.geneticheightgirls" /></dd>
				<dt><spring:message code="weightlog.label.geneticheightboys" /></dt>
				<dd><spring:message code="weightlog.formula.geneticheightboys" /></dd>
			</dl>
		</div>
				
		<h4><spring:message code="weightlog.header.developmentpercentile50" /></h4>
		<div class="panel-body center-block">
			<dl class="dl-horizontal">
				<dt><spring:message code="weightlog.label.geneticheight" /></dt>
				<dd>166</dd>
				<dt><spring:message code="weightlog.label.age" /></dt>
				<dd>${referenceData.ageYears} <spring:message code="label.year" />
					<c:if test="${referenceData.ageMonths > 0}"> 
						<spring:message code="label.and" /> ${referenceData.ageMonths} <spring:message code="label.months" /> 
					</c:if>
				</dd>
				<dt><spring:message code="weightlog.label.weightkg" /></dt>
				<dd>${referenceData.weightP50}</dd>
				<dt><spring:message code="weightlog.label.heightcm" /></dt>
				<dd>${referenceData.heightP50}</dd>
				<dt><spring:message code="weightlog.label.bmi" /></dt>
				<dd>${referenceData.bmiP50}</dd>
				<dt><spring:message code="weightlog.label.reference" /></dt>
				<dd><a href="<spring:message code="weightlog.url.whoreferancedata" />"><spring:message code="weightlog.url.whoreferancedata.txt" /></a></dd>
				<dd><a href="<spring:message code="weightlog.url.percentileschema" />"><spring:message code="weightlog.url.percentileschema.txt" /></a></dd>
				<dd><a href="<spring:message code="weightlog.url.growth2to19" />"><spring:message code="weightlog.url.growth2to19.txt" /></a></dd>
				<dd><a href="<spring:message code="weightlog.url.kmi2to19" />"><spring:message code="weightlog.url.kmi2to19.txt" /></a></dd>
			</dl>
		</div>
		
		<h4><spring:message code="weightlog.header.progression" /></h4>
		<div class="panel-body">
			<dl class="dl-horizontal">
				<dt>For perioden</dt>
				<dd><fmt:formatDate value="${myStatistic.startDate}" pattern="dd.MM.yy" /> to <fmt:formatDate value="${myStatistic.endDate}" pattern="dd.MM.yy" /></dd>
				<dt><spring:message code="weightlog.label.weightrecommended" /></dt>
				<dd>250 g</dd>
				<dt>Nåværende vekt/høyde</dt>
				<dd>${myStatistic.weight} ${myStatistic.weightMetric} / ${myStatistic.height} ${myStatistic.heightMetric}</dd>
				<dt><spring:message code="weightlog.label.weightaverage" /></dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.averageWeight}" /> ${myStatistic.weightMetric}, målinger ${myStatistic.numberOfMeasurements}</dd>
				<dt>Vektøking gj pr uke</dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.averageWeightUp}" /> ${myStatistic.weightMetric}, målinger ${myStatistic.numberOfUp}</dd>
				<dt>Vektreduksjon gj pr uke</dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.averageWeightDown}" /> ${myStatistic.weightMetric}, målinger ${myStatistic.numberOfDown}</dd>
				<dt>Vektøking gj pr mnd</dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.averageWeightUpStep}" /> ${myStatistic.weightMetric}, målinger ${myStatistic.numberOfUpStep}</dd>
				<dt>Vektreduksjon gj pr mnd</dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.averageWeightDownStep}" /> ${myStatistic.weightMetric}, målinger ${myStatistic.numberOfDownStep}</dd>
				<dt><spring:message code="weightlog.label.heightaverage" /></dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.averageHeight}" /> ${myStatistic.heightMetric}</dd>
				<dt>Min/Max vekt</dt>
				<dd>${myStatistic.minWeight} / ${myStatistic.maxWeight}</dd>
				<dt>Min/Max høyde</dt>
				<dd>${myStatistic.minHeight} / ${myStatistic.maxHeight}</dd>
				<dt>Max nedgang/Max oppgang vekt</dt>
				<dd><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.maxWeightDecrease}" /> / <fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${myStatistic.maxWeightIncrease}" /></dd>
				<dt>Antall målinger</dt>
				<dd>${myStatistic.numberOfMeasurementsStep} mnd</dd>
				<dt>Antall økninger</dt>
				<dd>${myStatistic.numberOfUp} uker</dd>
				<dt>Antall reduksjoner</dt>
				<dd>${myStatistic.numberOfDown} uker</dd>
				<dt>Antall nøytral</dt>
				<dd>${myStatistic.numberOfNeutral} uker</dd>
			</dl>
		</div>
		
		<jsp:include page="list-log-data.jsp" /> 
	</div>
	<!-- end container -->
	<jsp:include page="../public/footer.jsp" />
</body>
</html>