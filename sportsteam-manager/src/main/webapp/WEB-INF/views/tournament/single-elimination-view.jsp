<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.common-cell { width: 7em !important; padding: 0em; text-align: center;}
.arrow-cell { border-bottom: solid; }
.empty-cell { width: 2em important; }
.goals-cell { width: 1em important; background-color: #eeeeee; }
.quarterfinal1 { background-color: #be4dff; }
.quarterfinal2 { background-color: #e5b8ff; }
.quarterfinal3 { background-color: #4d62ff; }
.quarterfinal4 { background-color: #b8c0ff; }
.semifinal1 {}
.semifinal2 {}
.final { background-color: #00d150; }
</style>
	
	<h4>Single-elimination tournament</h4>
	
	<table class="table table-condensed">
		<tr>
			<td class="quarterfinal1 common-cell"><c:out value="${finalsSetup.quarterFinal1.homeTeam.name}"></c:out></td>
			<td class="goals-cell">${finalsSetup.quarterFinal1.numberOfGoalsHome}<td>
			<td class="empty-cell"></td>
			<td></td>
			<td class="empty-cell"></td>
			<td></td>
		</tr>
		<tr>
			<td class="quarterfinal1 common-cell">${finalsSetup.quarterFinal1.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal1.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td class="semifinal1 quarterfinal1 common-cell">${finalsSetup.semiFinal1.homeTeam.name}</td>
			<td class="goals-cell">${finalsSetup.semiFinal1.numberOfGoalsHome}<td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td class="semifinal1 quarterfinal2 common-cell">${finalsSetup.semiFinal1.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.semiFinal1.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="semifinal1 quarterfinal2 common-cell">${finalsSetup.quarterFinal2.homeTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal2.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="quarterfinal2 common-cell">${finalsSetup.quarterFinal2.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal2.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td class="final common-cell">${finalsSetup.finalMatch.homeTeam.name}</td>
			<td class="goals-cell">${finalsSetup.finalMatch.numberOfGoalsHome}<td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td class="final common-cell">${finalsSetup.finalMatch.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.finalMatch.numberOfGoalsAway}<td>
		</tr>
		<tr>
			<td class="quarterfinal3 common-cell">${finalsSetup.quarterFinal3.homeTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal3.numberOfGoalsHome}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="quarterfinal3 common-cell">${finalsSetup.quarterFinal3.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal3.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td class="semifinal2 quarterfinal3 common-cell">${finalsSetup.semiFinal2.homeTeam.name}</td>
			<td class="goals-cell">${finalsSetup.semiFinal2.numberOfGoalsHome}<td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td class="semifinal2 quarterfinal4 common-cell">${finalsSetup.semiFinal2.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.semiFinal2.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="quarterfinal4 common-cell">${finalsSetup.quarterFinal4.homeTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal4.numberOfGoalsHome}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="quarterfinal4 common-cell">${finalsSetup.quarterFinal4.awayTeam.name}</td>
			<td class="goals-cell">${finalsSetup.quarterFinal4.numberOfGoalsAway}<td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
