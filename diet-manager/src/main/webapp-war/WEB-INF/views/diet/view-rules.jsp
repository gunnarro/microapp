<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<head>
<jsp:include page="../public/head-includes.jsp" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<style type="text/css">

.panel-heading .accordion-toggle:after {
    /* symbol for "opening" panels */
    font-family: 'Glyphicons Halflings';  /* essential for enabling glyphicon */
    content: "\e114";    /* adjust as needed, taken from bootstrap.css */
    float: right;        /* adjust as needed */
    color: grey;         /* adjust as needed */
    padding-left: 10px;
}
.panel-heading .accordion-toggle.collapsed:after {
    /* symbol for "collapsed" panels */
    content: "\e080";    /* adjust as needed, taken from bootstrap.css */
}


.panel-heading-sm {
	  height: 28px;
      padding: 4px 10px !important;
}

.glyphicon:before {
 	visibility: visible;
}
.glyphicon.glyphicon-star-empty:checked:before {
   content: "\e006";
}
input[type=checkbox].glyphicon {
    visibility: hidden;
}

.fa:before {
 	visibility: visible;
}
.fa.fa-smile-o:checked:before {
   content: "\e006";
}
input[type=checkbox].fa {
    visibility: hidden;
}

</style>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">

		<div class="page-header">
			<h4>Pepilie</h4>
		</div>

		<div class="panel-group" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading panel-heading-sm">
					<div class="panel-title pull-left">
						<small>
							<span>
								<a data-toggle="collapse" data-parent="#accordion" href="#collapse_0">Min Status uke</a>
							</span>
						</small>
					</div>
					<div class="panel-title pull-right">
						<h5>
							<small>
								<span class="text-muted">Oppdatert: 10.11.2016 09:03</span>
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse_0"></a>
							</small>
						</h5>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div id="collapse_0" class="panel-collapse collapse in">
					<div class="panel-body">
							<dl class="dl-horizontal">
								<dt>Vektøkning</dt>
								<dd><i class="glyphicon glyphicon-thumbs-up error-color"></i></dd>
								<dt>høydeøkning</dt>
								<dd><i class="glyphicon glyphicon-thumbs-up error-color"></i></dd>
								<dt>Fulgt diet planen</dt>
								<dd><i class="glyphicon glyphicon-thumbs-up error-color"></i></dd>
								<dt>Aktivitetsnivå</dt>
								<dd><i class="glyphicon glyphicon-thumbs-up error-color"></i></dd>
								<dt>Konfliktnivå</dt>
								<dd><i class="fa fa-smile-o edit-color"></i></dd>
								<dt>Skole</dt>
								<dd><img src="<spring:url value="/resources/images/smiley-sad1.png" htmlEscape="true" />" align="middle" width="12" height="12"/></dd>
							</dl>
						
					</div>
				</div>
			</div>
		<jsp:include page="../public/footer.jsp" />
		</div>
	</div>
</body>
</html>