var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

//        console.log('Connected: ' + $("#name").val());
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/chat', function (chat) {
            showChat(JSON.parse(chat.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendGreeting() {
    stompClient.send("/app/study.chat.chatDemo.hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(greetingMessage) {
    console.log("showGreeting");
     $("#messages").append("<tr><td>" + greetingMessage + "</td></tr>");
}

function sendChat() {
    stompClient.send("/app/chat", {}, JSON.stringify({'name': $("#name").val(), 'message': $("#chatMessage").val()}));
}

function showChat(chat){
    $("#messages").append("<tr><td>" + chat.name + " : " + chat.message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });

    $( "#login" ).click(function() { sendGreeting(); });

    $( "#chatSend" ).click(function() { sendChat(); });
});