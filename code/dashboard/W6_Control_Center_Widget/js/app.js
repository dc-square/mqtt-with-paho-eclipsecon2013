//Create a new Client object with your broker's hostname, port and your own clientId
var client = new Messaging.Client("localhost", 8000, "monitoring-dashboard");

var gauge;
$(document).ready(function () {


    gauge = new JustGage({
        id: "gauge",
        value: 0,
        min: -20,
        max: 60,
        title: " ",
        showMinMax: false,
        levelColors: ["#007CFF", "#a9d70b", "#ff0000"],
        levelColorsGradient: false,
        label: "° Celsius"
    });


    var options = {

        //connection attempt timeout in seconds
        timeout: 3,

        //Gets Called if the connection has successfully been established
        onSuccess: function () {

            client.subscribe("eclipsecon/weather");
            client.subscribe("eclipsecon/temperature");
            client.subscribe("eclipsecon/status");
        },

        //Gets Called if the connection could not be established
        onFailure: function (message) {
            alert("Connection failed: " + message.errorMessage);
        }
    };

    //Gets called whenever you receive a message for your subscriptions
    client.onMessageArrived = function (message) {

        var topic = message.destinationName;

        if (topic === "eclipsecon/status") {
            if (message.payloadString === "offline") {
                $("#client_disconnected").html('Client is disconnected!').removeClass("hide").hide().fadeIn("slow");
                $("#client_connected").addClass("hide").hide();
            } else {
                $("#client_connected").html('Client is connected!').removeClass("hide").hide().fadeIn("slow");
                $("#client_disconnected").addClass("hide").hide();
            }
        } else if (topic === "eclipsecon/weather") {
            setWeatherIcon(message.payloadString);
        } else if (topic === "eclipsecon/temperature") {
            setTemperature(message.payloadString);
        }

    };

//Attempt to connect
    client.connect(options);
});


function publishInterval(interval) {

    var message = new Messaging.Message(interval);
    message.destinationName = "eclipsecon/interval";
    message.qos = 2;

    client.send(message);
}


function setWeatherIcon(weather) {

    var weathericon = $('#weathericon');
    weathericon.removeClass();
    weathericon.addClass('weather-icon');

    if (weather === 'STORM') {
        weathericon.addClass('wi-thunderstorm');
    } else if (weather === 'SNOW') {
        weathericon.addClass('wi-snow');
    } else if (weather === 'RAIN') {
        weathericon.addClass('wi-rain');
    } else if (weather === 'CLOUD') {
        weathericon.addClass('wi-cloudy');
    } else if (weather === 'SUN') {
        weathericon.addClass('wi-day-sunny');
    } else {
        weathericon.addClass('wi-cloud-refresh');
    }
}

function setTemperature(temp) {
    gauge.refresh(parseFloat(temp));
}

//function addGlazeAlert() {
//    var alertComponent = $("#glaze_warning");
//    if (alertComponent.hasClass("hide")) {
//
//        alertComponent.removeClass("hide").hide().fadeIn("slow");
//    }
//}
//
//function removeGlazeAlert() {
//
//    var alertComponent = $("#glaze_warning");
//    if (!alertComponent.hasClass("hide")) {
//
//        alertComponent.addClass("hide");
//    }
//}
