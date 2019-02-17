
var stompClient = null;
var isConnected = false;
var loginUsers = [];
var listItems = $(".contacts-list li");
listItems.each(function(i, obj) {
    loginUsers.push(obj.getAttribute('id'));
}); 

const messageFormButton = new Vue({
    el: '.wrapper',
    data: {
        messageReceived: [],
        joinMessageReceived: [],
        joinedUserList: [],
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
        onConnected: function() {
            // Subscribe to the specific and public topic 
            stompClient.subscribe('/board/public/' + b_id, this.onMessageReceived);

            // Tell your username to the server
            stompClient.send("/app/chat.addUser/" + b_id,
                {},
                JSON.stringify({ sender: username, type: 'JOIN', bid: b_id })
            )

            document.querySelector('.connecting').classList.add('hidden');
        },
        onError: function (error) {
            isConnected = false;
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
            let message = JSON.parse(payload.body);
            message['isLogin'] = true;
            if (message.type === 'JOIN') {
                if(!loginUsers.includes('sender-' + message.sender)){
                    this.joinMessageReceived.push(message);
                    loginUsers.push(message.sender);
                }
            } else if (message.type === 'LEAVE') {
                var index = indexOfObject(this.joinMessageReceived, message.sender);
                if (index > -1) {
                    this.joinMessageReceived.splice(index, 1);
                }
                var index = loginUsers.indexOf('sender-'+message.sender);
                if (index > -1) {
                    loginUsers.splice(index, 1);
                }
                $('#sender-' + message.sender).remove();
            } else {
                this.messageReceived.push(message);
                $("html, body").animate({ scrollTop: $("#messageArea").height() }, 1000);
            }
        }
    },
    isUserLogin: function(sender){
        return this.joinedUserList.indexOf(sender) < 0;
    },
    mounted: function () {
        this.connect();
        $("html, body").animate({ scrollTop: $("#messageArea").height() }, 1000);
    }
})

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

function checkIfOnlineMarkAlreadyAdded(idName) {
    if ($('#' + idName).length === 0) {
        return false;
    }
    return true;
}

function indexOfObject(arry, userName){
    for(var i = 0; i < arry.length; i++) {
        if(arry[i].sender === userName) {
          return i;
        }
    }
    return -1;
}