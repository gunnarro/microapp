/*!
 * Name: dietmanager.js
 * Version: 1.0.-SNAPSHOT
 * Release date: 06.10.2016
 * Copyright 2016 gunnarro
 */
function registrerMenuSelection(memuItemId, serviceUri) {
	var restUrl="/diet-manager/rest/" + serviceUri;
	$.ajax({
		url : restUrl,
		type : "GET",
		data : {
		},
		cache : false,
		contentType : "application/json",
		mimeType : "application/json",
		before : function() {
			$btn.button('loading');
		},
		success : function(response) {
			$('#numOfSelectionMenuItem_' + memuItemId).html(response);
		},
		error : function(jqXhr, textStatus, errorThrown) {
			console.error("Error: " + jqXhr + ", status: " + textStatus + ", REST: " + restUrl + ", err: "
					+ errorThrown);
		}
	});
	return true;
}