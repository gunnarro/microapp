/*!
 * Name: geolocation.js
 * Version: 1.0.-SNAPSHOT
 * Release date: 11.04.2017
 * Copyright 2017 gunnarro
 */
console.log("init findbutton geolocation.js");
//var options = {timeout:60000};
//var findMeButton = $('.find-me');

// Check if the browser has support for the Geolocation API
//if (!navigator.geolocation) {
//	findMeButton.addClass("disabled");
//	$('.no-browser-support').addClass("visible");
//	console.log("find location..no browser support");
//} else {
//	console.log("findbutton listen for click event...");
//	findMeButton.on('click', function(e) {
//		console.log("find location...");
		//e.preventDefault();
//		navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
//	});
//}

function getLocation(){
    if(navigator.geolocation){
    	console.log("get location...");
    	// timeout at 60000 milliseconds (60 seconds)
       var options = {timeout:60000};
       navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
    }
    else{
       console.log("Sorry, browser does not support geolocation!");
    }
 }

function showLocation(position) {
	// Get the coordinates of the current position.
	console.log("show location...");
	var lat = position.coords.latitude;
	var lng = position.coords.longitude;

	$('.latitude').text(lat.toFixed(3));
	$('.longitude').text(lng.toFixed(3));
	$('.coordinates').addClass('visible');

	// Create a new map and place a marker at the device location.
	//var map = new GMaps({
	//	el : '#map',
	//	lat : lat,
	//	lng : lng
	//});

	//map.addMarker({
	//	lat : lat,
	//	lng : lng
	//});
}

function errorHandler(error) {
    console.log("Error: " + error);
}
