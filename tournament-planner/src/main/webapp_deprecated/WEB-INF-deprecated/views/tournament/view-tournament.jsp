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
	.empty-cell { background-color: #ffffff;}
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
	.table-condensed>tfoot>tr>td { padding: 3px; }

</style>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<!-- 
				<div class="pull-right">
					<spring:url value="/tournament/edit/{tournamentId}" var="editUrl">
						<spring:param name="tournamentId" value="${tournament.name}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				 -->
				<h1 class="panel-title">${tournament.name}</h1>
			</div>

			<div class="panel-body center-block">
				<h4>Group stage</h4>
				<c:forEach var="group" items="${tournament.groups}">
					<table class="table table-condensed">
						<thead>
						    <tr class="info">
						    	<th colspan="4" class="text-left">${group.name}</th>
						    	<th colspan="2" class="text-right">
									<spring:url value="/tournament/groupstage/edit/{tournamentId}/{groupId}" var="newUrl">
										<spring:param name="tournamentId" value="${tournament.name}" />
										<spring:param name="groupId" value="${group.id}" />
									</spring:url>
									<a href="${fn:escapeXml(newUrl)}" class="btn btn-primary btn-xs">Update result(s)</a>
								</th>
						    </tr>
							<tr class="info">
								<th colspan="1" class="text-left">#</th>
								<th colspan="1" class="col-md-1 text-left">Start</th>
								<th colspan="1" class="col-md-2 text-center">Home</th>
								<th colspan="1" class="col-md-1 text-center">Result</th>
								<th colspan="1" class="col-md-1 text-center">Away</th>
								<th colspan="1" class="col-md-1 text-left">Venue</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="match" items="${group.matches}">
								<c:set var="dateColor" value="label label-primary" />
								<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-not-played-color" />
								<c:if test="${match.played}">
									<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-played-color" />
								</c:if>
								<tr>
									<td class="text-left" style="width:20px">
										<span class="${statusIconCls}"></span>
									</td>
									<td class="text-left">
										<small>
											<span class="${dateColor}"><fmt:formatDate value="${match.startDate}" pattern="HH:mm" /></span>
										</small>
									</td>
									<td class="text-center">${match.homeTeam.name}</td>
									<!--
									<td>${match.teamVersus}</td>
									<td><input type="text" value="${match.numberOfGoalsHome}" size="2" /> - <input type="text" value="${match.numberOfGoalsAway}" size="2" /></td>
									-->
									<td class="text-center">${match.numberOfGoalsHome} - ${match.numberOfGoalsAway}</td>
									<td class="text-center">${match.awayTeam.name}</td>
									<td class="text-left">${match.venue}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
						</tfoot>
					</table>
				</c:forEach>
				
			    <h4>Group stage result</h4>
				 <c:forEach var="group" items="${tournament.groups}">
					<table class="table table-condensed">
						<thead>
							<tr class="info">
						    	<th colspan="7" class="text-left">${group.name}</th>
						    	<th colspan="1" class="text-right">
									<spring:url value="/tournamnet/groupplay/update" var="newUrl">
									</spring:url>
									<a href="${fn:escapeXml(newUrl)}" ><i class="glyphicon glyphicon-refresh"></i></a>
								</th>
						    </tr>
							<tr class="info">
								<th colspan="1" class="col-md-1 text-left">#</th>
								<th colspan="1" class="col-md-2 text-left">Team</th>
								<th colspan="1" class="col-md-1">M</th>
								<th colspan="1" class="col-md-1">V</th>
								<th colspan="1" class="col-md-1">D</th>
								<th colspan="1" class="col-md-1">L</th>
								<th colspan="1" class="col-md-1">G</th>
								<th colspan="1" class="col-md-1">P</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="teamName" items="${group.teamNames}" varStatus="loop">
								<tr>
									<td>${loop.index + 1}</td>
									<td>${teamName}</td>
									<td>0</td>
									<td>0</td>
									<td>0</td>
									<td>0</td>
									<td>0</td>
									<td>0</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="8"> </td>
							</tr>
						</tfoot>
					</table>
				</c:forEach>
				 
				<h4>Second stage</h4>
					<table class="table table-condensed">
						<thead>
							<tr class="info">
						    	<th colspan="6" class="text-left">Finals</th>
						    	<th colspan="2" class="text-right">
									<spring:url value="/tournament/secondstage/edit/{tournamentId}" var="newUrl">
										<spring:param name="tournamentId" value="${tournament.name}" />
									</spring:url>
									<a href="${fn:escapeXml(newUrl)}" class="btn btn-primary btn-xs">Update result(s)</a>
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
							<td class="col-md-2 quarterfinal1 common-cell"><small>${tournament.finalsSetup.quarterFinal1.homeTeam.name}</small></td>
							<td class="col-md-1 goals-cell"><small>${tournament.finalsSetup.quarterFinal1.numberOfGoalsHome}</small></td>
							<td class="col-md-1 empty-cell"></td>
							<td class="col-md-2 semi"></td>
							<td class="col-md-1 empty-cell"></td>
							<td class="col-md-1 empty-cell"></td>
							<td class="col-md-2 final"></td>
							<td class="col-md-1 empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal1 common-cell"><small>${tournament.finalsSetup.quarterFinal1.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal1.numberOfGoalsAway}</small></td>
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
							<td class="semifinal1 quarterfinal1 common-cell"><small>${tournament.finalsSetup.semiFinal1.homeTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.semiFinal1.numberOfGoalsHome}</small></td>
							<td  class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="semifinal1 quarterfinal2 common-cell"><small>${tournament.finalsSetup.semiFinal1.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.semiFinal1.numberOfGoalsAway}</small></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="semifinal1 quarterfinal2 common-cell"><small>${tournament.finalsSetup.quarterFinal2.homeTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal2.numberOfGoalsAway}</small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal2 common-cell"><small>${tournament.finalsSetup.quarterFinal2.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal2.numberOfGoalsAway}</small></td>
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
							<td class="goldfinal common-cell"><small>${tournament.finalsSetup.goldFinal.homeTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.goldFinal.numberOfGoalsHome}</small></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="goldfinal common-cell"><small>${tournament.finalsSetup.goldFinal.awayTeam.name}</small></td>
							<td class="goals-cell">${tournament.finalsSetup.goldFinal.numberOfGoalsAway}</small></td>
						</tr>
						<tr>
							<td class="quarterfinal3 common-cell"><small>${tournament.finalsSetup.quarterFinal3.homeTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal3.numberOfGoalsHome}</small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal3 common-cell"><small>${tournament.finalsSetup.quarterFinal3.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal3.numberOfGoalsAway}</small></td>
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
							<td class="semifinal2 quarterfinal3 common-cell"><small>${tournament.finalsSetup.semiFinal2.homeTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.semiFinal2.numberOfGoalsHome}</small></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="semifinal2 quarterfinal4 common-cell"><small>${tournament.finalsSetup.semiFinal2.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.semiFinal2.numberOfGoalsAway}</small></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal4 common-cell"><small>${tournament.finalsSetup.quarterFinal4.homeTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal4.numberOfGoalsHome}</small></td>
							<td class="empty-cell"></td>
							<td class="semi"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="final"></td>
							<td class="empty-cell"></td>
						</tr>
						<tr>
							<td class="quarterfinal4 common-cell"><small>${tournament.finalsSetup.quarterFinal4.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.quarterFinal4.numberOfGoalsAway}</small></td>
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
							<td class="bronsefinal common-cell"><small>${tournament.finalsSetup.bronseFinal.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.bronseFinal.numberOfGoalsAway}</small></td>
						</tr>
						<tr>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="empty-cell"></td>
							<td class="bronsefinal common-cell"><small>${tournament.finalsSetup.bronseFinal.awayTeam.name}</small></td>
							<td class="goals-cell"><small>${tournament.finalsSetup.bronseFinal.numberOfGoalsAway}</small></td>
						</tr>
						</tbody>
						<tfoot>
						</tfoot>
					</table>
			</div>
			<!-- end panel body -->
			
			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listtournaments/token_xxx" var="backUrl">
					</spring:url>
					<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm">back</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>