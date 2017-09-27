<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />
<style type="text/css">
	.common-cell { text-align: left;}
	.arrow-cell { border-bottom: solid; }
	.empty-cell { }
	.goals-cell { background-color: #eeeeee; }
	.quarterfinal1 { background-color: #be4dff; }
	.quarterfinal2 { background-color: #e5b8ff; }
	.quarterfinal3 { background-color: #4d62ff; }
	.quarterfinal4 { background-color: #b8c0ff; }
	.semifinal1 {}
	.semifinal2 {}
	.bronsefinal { background-color: #01fd62; }
	.goldfinal { background-color: #02a942; }
	
	.table-condensed>thead>tr>th, 
	.table-condensed>tbody>tr>th, 
	.table-condensed>tfoot>tr>th, 
	.table-condensed>thead>tr>td, 
	.table-condensed>tbody>tr>td, 
	.table-condensed>tfoot>tr>td{padding: 3px;}
	
	.table th, .table td { border-top: none !important; border-left: none !important; }
	.fixed-table-container { border:0px; }
	.table th { border-bottom: none !important; }
	.table:last-child { border:none !important;} 
</style>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h1 class="panel-title">Second Stage - ${finalsetup.tournamentId}</h1>
			</div>
				 
				<form:form modelAttribute="finalsetup" method="POST" id="tournament-secondstage-form">
				    <form:hidden path="tournamentId" value="${finalsetup.tournamentId}"/>
				    <form:hidden path="quarterFinal1.homeTeam.name" value="${finalsetup.quarterFinal1.homeTeam.name}" />
				    <form:hidden path="quarterFinal1.awayTeam.name" value="${finalsetup.quarterFinal1.awayTeam.name}" />
				    <form:hidden path="quarterFinal2.homeTeam.name" value="${finalsetup.quarterFinal2.homeTeam.name}" />
				    <form:hidden path="quarterFinal2.awayTeam.name" value="${finalsetup.quarterFinal2.awayTeam.name}" />
				    <form:hidden path="quarterFinal3.homeTeam.name" value="${finalsetup.quarterFinal3.homeTeam.name}" />
				    <form:hidden path="quarterFinal3.awayTeam.name" value="${finalsetup.quarterFinal3.awayTeam.name}" />
				    <form:hidden path="quarterFinal4.homeTeam.name" value="${finalsetup.quarterFinal4.homeTeam.name}" />
				    <form:hidden path="quarterFinal4.awayTeam.name" value="${finalsetup.quarterFinal4.awayTeam.name}" />
				    <form:hidden path="semiFinal1.homeTeam.name" value="${finalsetup.semiFinal1.homeTeam.name}" />
				    <form:hidden path="semiFinal1.awayTeam.name" value="${finalsetup.semiFinal1.awayTeam.name}" />
				    <form:hidden path="semiFinal2.homeTeam.name" value="${finalsetup.semiFinal2.homeTeam.name}" />
				    <form:hidden path="semiFinal2.awayTeam.name" value="${finalsetup.semiFinal2.awayTeam.name}" />
				    <form:hidden path="goldFinal.homeTeam.name" value="${finalsetup.goldFinal.homeTeam.name}" />
				    <form:hidden path="goldFinal.awayTeam.name" value="${finalsetup.goldFinal.awayTeam.name}" />
				    <form:hidden path="bronseFinal.homeTeam.name" value="${finalsetup.bronseFinal.homeTeam.name}" />
				    <form:hidden path="bronseFinal.awayTeam.name" value="${finalsetup.bronseFinal.awayTeam.name}" />
					<table class="table table-condensed">
						<thead>
							<tr class="info">
						    	<th colspan="8" class="text-right">
									<button type="submit" class="btn btn-primary btn-xs">Save</button>
								</th>
						    </tr>
							<tr class="info">
								<th colspan="2" class="text-left">Quarter Finals</th>
								<th class="separator-col">&nbsp;</th>
								<th colspan="2" class="text-left">Semi Finals</th>
								<th class="separator-col">&nbsp;</th>
								<th colspan="2" class="text-left">Finals</th>
							</tr>
						</thead>
						<tbody>
						<tr>
							<td class="col-md-2 quarterfinal1 common-cell"><small>${finalsetup.quarterFinal1.homeTeam.name}</small></td>
							<td class="col-md-1 goals-cell"><small><form:input cssClass="form-control" path="quarterFinal1.numberOfGoalsHome" value="${finalsetup.quarterFinal1.numberOfGoalsHome}" /></small></td>
							<td class="col-md-1 empty-cell"></td>
							<td class="col-md-2 semi"></td>
							<td class="col-md-1 empty-cell"></td>
							<td class="col-md-1 empty-cell"></td>
							<td class="col-md-2 final"></td>
							<td class="col-md-1 empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal1 common-cell"><small>${finalsetup.quarterFinal1.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal1.numberOfGoalsAway" value="${finalsetup.quarterFinal1.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class=""></td>
							<td class=""></td>
							<td class="empty-cell"></td>
							<td class="semifinal1 quarterfinal1 common-cell"><small>${finalsetup.semiFinal1.homeTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="semiFinal1.numberOfGoalsHome" value="${finalsetup.semiFinal1.numberOfGoalsHome}" /></small></td>
							<td  class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="semifinal1 quarterfinal2 common-cell"><small>${finalsetup.semiFinal1.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="semiFinal1.numberOfGoalsAway" value="${finalsetup.semiFinal1.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="semifinal1 quarterfinal2 common-cell"><small>${finalsetup.quarterFinal2.homeTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal2.numberOfGoalsHome" value="${finalsetup.quarterFinal2.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal2 common-cell"><small>${finalsetup.quarterFinal2.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal2.numberOfGoalsAway" value="${finalsetup.quarterFinal2.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="goldfinal common-cell"><small>${finalsetup.goldFinal.homeTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="goldFinal.numberOfGoalsHome" value="${finalsetup.goldFinal.numberOfGoalsHome}" /></small></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="goldfinal common-cell"><small>${finalsetup.goldFinal.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="goldFinal.numberOfGoalsAway" value="${finalsetup.goldFinal.numberOfGoalsAway}" /></small></td>
						</tr>
						<tr>
							<td class="quarterfinal3 common-cell"><small>${finalsetup.quarterFinal3.homeTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal3.numberOfGoalsHome" value="${finalsetup.quarterFinal3.numberOfGoalsHome}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal3 common-cell"><small>${finalsetup.quarterFinal3.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal3.numberOfGoalsAway" value="${finalsetup.quarterFinal3.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="semifinal2 quarterfinal3 common-cell"><small>${finalsetup.semiFinal2.homeTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="semiFinal2.numberOfGoalsHome" value="${finalsetup.semiFinal2.numberOfGoalsHome}" /></small></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="semifinal2 quarterfinal4 common-cell"><small>${finalsetup.semiFinal2.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="semiFinal2.numberOfGoalsAway" value="${finalsetup.semiFinal2.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal4 common-cell"><small>${finalsetup.quarterFinal4.homeTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal4.numberOfGoalsHome" value="${finalsetup.quarterFinal4.numberOfGoalsHome}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal4 common-cell"><small>${finalsetup.quarterFinal4.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="quarterFinal4.numberOfGoalsAway" value="${finalsetup.quarterFinal4.numberOfGoalsAway}" /></small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="bronsefinal common-cell"><small>${finalsetup.bronseFinal.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="bronseFinal.numberOfGoalsAway" value="${finalsetup.bronseFinal.numberOfGoalsAway}" /></small></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="bronsefinal common-cell"><small>${finalsetup.bronseFinal.awayTeam.name}</small></td>
							<td class="goals-cell"><small><form:input cssClass="form-control" path="bronseFinal.numberOfGoalsAway" value="${finalsetup.bronseFinal.numberOfGoalsAway}" /></small></td>
						</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="7"></td>
								<td><button type="submit" class="btn btn-primary">Save</button></td>
							</tr>
						</tfoot>
					</table>
				</form:form>
			</div>
			<!-- end panel body -->
			
			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/tournament/{tournamentId}" var="backUrl">
						<spring:param name="tournamentId" value="${finalsetup.tournamentId}"/>
					</spring:url>
					<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm">Cancel</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>