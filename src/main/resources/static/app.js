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

//        console.log('Connected: ' + $("#id").val());
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
    stompClient.send("/app/study.chat.chatDemo.hello", {}, JSON.stringify({'id': $("#id").val()}));
}

function callLoginApi() {
     stompClient.send("/app/login", {}, $("#id").val());
}

function showGreeting(greetingMessage) {
    console.log("showGreeting");
     $("#messages").append("<tr><td>" + greetingMessage + "</td></tr>");
}

function sendChat() {
    stompClient.send("/app/chat", {}, JSON.stringify({'id': $("#id").val(), 'message': $("#chatMessage").val()}));
}

function showChat(chat){
    $("#messages").append("<tr><td>" + chat.nicName + " : " + chat.message + "</td></tr>");
}

function callMacroApi() {
    stompClient.send("/app/macro", {}, $("#id").val());
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });

    $( "#login" ).click(function() { callLoginApi(); });

    $( "#chatSend" ).click(function() { sendChat(); });

    $( "#callMacro" ).click(function () { callMacroApi(); });
});