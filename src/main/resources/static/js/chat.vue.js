
var stompClient = null;
var isConnected = false;

const messageFormButton = new Vue({
    el: '.wrapper',
    data: {
        messageReceived: [],
        joinMessageReceived: [],
        username: username
    },
    methods: {
        send: function (event) {
            if (event) event.preventDefault();
            var messageContent = document.querySelector('#message').value.trim();

            if (messageContent && stompClient) {
                var boardResponse = {
                    sender: username,
                    response: document.querySelector('#message').value,
                    bid: b_id,
                    type: 'CHAT'
                };

                stompClient.send("/app/chat.sendMessage/" + b_id, {}, JSON.stringify(boardResponse));
                document.querySelector('#message').value = '';
            }
            event.preventDefault();
        },
        connect: function (event) {
            if (!isConnected) {
                isConnected = true;
                document.querySelector('#warning').classList.add('hidden');

                var socket = new SockJS('/ws');
                stompClient = Stomp.over(socket);

                stompClient.connect({}, this.onConnected, this.onError);
            }
        },
        onConnected() {
            // Subscribe to the Public Topic
            stompClient.subscribe('/board/public/' + b_id, this.onMessageReceived);

            // Tell your username to the server
            stompClient.send("/app/chat.addUser/" + b_id,
                {},
                JSON.stringify({ sender: username, type: 'JOIN', bid: b_id })
            )

            document.querySelector('.connecting').classList.add('hidden');
        },
        onError(error) {
            is_connected = false;
            document.querySelector('.connecting').textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
            document.querySelector('.connecting').style.color = 'red';
        },
        pushMessage: function (message, owner) {
            this.messageReceived.push({
                "message": message,
                "owner": owner
            });
        },
        onClose: function (event) {
            alert("Connection closed. Please refresh the page.");
        },
        onMessageReceived: function (payload) {
            var message = JSON.parse(payload.body);
            if (message.type === 'JOIN') {
                if (!checkIfOnlineMarkAlreadyAdded("sender-" + message.sender)) {
                    this.joinMessageReceived.push(message);
                } else {
                    $("#sender-currentstatus-" + message.sender).removeClass("text-dark");
                    $("#sender-currentstatus-" + message.sender).addClass("text-success");
                }
            } else if (message.type === 'LEAVE') {
                $("#sender-currentstatus-" + message.sender).removeClass("text-success");
                $("#sender-currentstatus-" + message.sender).addClass("text-dark");
            } else {
                this.messageReceived.push(message);
            }
        }
    },
    mounted: function () {
        this.connect();
    }
})

function checkIfOnlineMarkAlreadyAdded(idName) {
    if ($('#' + idName).length === 0) {
        return false;
    }
    return true;
}

function sendMessage() {
    var messageContent = document.querySelector('#message').value.trim();

    if(messageContent && stompClient) {
        var boardResponse = {
            sender: username,
            response: document.querySelector('#message').value,
            bid: b_id,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage/"+b_id, {}, JSON.stringify(boardResponse));
        document.querySelector('#message').value = '';
    }
}