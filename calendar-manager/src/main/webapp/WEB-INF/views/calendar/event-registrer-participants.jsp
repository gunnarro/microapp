<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	function registrerPlayer(btnId, serviceParam) {
		var serviceUri="event/deregistrer/";
		var btnText="deregistrer";
		if ( $('#btn_' + btnId).hasClass('btn-danger') ) {
			serviceUri="event/registrer/";
	    } 
		var restUrl="/rest/sportsteam/" + serviceUri + "/" + serviceParam;
		$.ajax({
			url : restUrl,
			type : "POST",
			data : {
			},
			cache : false,
			contentType : "application/json",
			mimeType : "application/json",
			before : function() {
				$btn.button('loading');
			},
			success : function(response) {
				$('#btn_' + btnId).button('reset');
				$('#numOfRegPlayers').html(response);
				if (parseInt(response) > 3) {
					$('#numOfRegPlayers').removeClass("text-danger");
					$('#numOfRegPlayers').addClass("text-success");
				} else {
					$('#numOfRegPlayers').removeClass("text-success");
					$('#numOfRegPlayers').addClass("text-danger");
				}
				$('#btn_' + btnId).toggleClass("btn-success btn-danger");
				if ( $('#btn_' + btnId).hasClass('btn-danger') ) {
					btnText="registrer";
			    } 
			    $('#btn_' + btnId).text(btnText);
			},
			error : function(jqXhr, textStatus, errorThrown) {
				alert("Error: " + jqXhr + ", status: " + textStatus + ", err: "
						+ errorThrown);
			}
		});
		return true;
	}
</script>

<div class="panel-body">
	<section>
		<h4 class="panel-title">
			Registrered Participants <span class="badge alert-info"> <c:set var="textColor" value="text-danger" /> <c:if
					test="${param.numberOfRegistreredParticipants > 1}">
					<c:set var="textColor" value="text-success" />
				</c:if> <span id="numOfRegPlayers" class="${textColor}">${param.numberOfRegistreredParticipants}</span> / ${param.numberOfFollowers}
			</span>
		</h4>

		<table class="table borderless">
			<c:forEach var="item" items="${followers}" varStatus="loop">
				<tr>
					<td>${item.value}</td>
					<td>&nbsp;&nbsp;</td>
					<td><c:choose>
							<c:when test="${item.selected}">
								<c:set var="linkClass" value="btn-registrer-width btn btn-success btn-xs" />
								<c:set var="restServiceParam" value="${item.type}/${param.eventId}/${item.id}" />
								<c:set var="btnText" value="deregistrer" />
							</c:when>
							<c:otherwise>
								<c:set var="linkClass" value="btn-registrer-width btn btn-danger btn-xs" />
								<c:set var="restServiceParam" value="${item.type}/${param.eventId}/${item.id}" />
								<c:set var="btnText" value="registrer" />
							</c:otherwise>
						</c:choose> 
						<c:choose>
							<c:when test="${param.finished}">
								<button disabled="disabled" id="btn_${loop.index}" type="button" data-loading-text="updating..." class="${linkClass}"
									onclick="registrerPlayer(${loop.index},'${restServiceParam}');">
									<c:out value="${btnText}" />
								</button>
							</c:when>
							<c:otherwise>
								<button id="btn_${loop.index}" type="button" data-loading-text="updating..." class="${linkClass}"
									onclick="registrerPlayer(${loop.index},'${restServiceParam}');">
									<c:out value="${btnText}" />
								</button>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
	</section>
</div>
