var stompClient = null;


function connect() {
    var socket = new SockJS('/ws-root');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
         
        stompClient.subscribe('/ws-process/progress', function (msg) {
            console.log(msg);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

