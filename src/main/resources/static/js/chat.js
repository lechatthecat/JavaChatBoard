'use strict';
var warning = document.querySelector('#warning');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var is_connected = false;
var stompClient = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    if(!is_connected) {
        is_connected = true;
        warning.classList.add('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/board/public/'+b_id, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser/"+b_id,
        {},
        JSON.stringify({sender: username, type: 'JOIN', bid: b_id})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    is_connected = false;
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var boardResponse = {
            sender: username,
            response: messageInput.value,
            bid: b_id,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage/"+b_id, {}, JSON.stringify(boardResponse));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('div');
    messageElement.classList.add("direct-chat-msg");
    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
    } else {
        //Sender info
        var messageSenderInfo = document.createElement('div');
        messageSenderInfo.classList.add("direct-chat-info");
        messageSenderInfo.classList.add("clearfix");

        //Sender Name
        var senderName =  document.createElement('span');
        senderName.classList.add("direct-chat-name");
        if(message.sender == username){
            messageElement.classList.add("right");
            senderName.classList.add("pull-right");
        }else{
            messageElement.classList.add("left");
            senderName.classList.add("pull-left");
        }
        var senderNameText = document.createTextNode(message.sender);
        senderName.appendChild(senderNameText);

        //Time stamp
        var timeStamp = document.createElement('span');
        timeStamp.classList.add("direct-chat-timestamp");
        if(message.sender == username){
            timeStamp.classList.add("pull-left");
        }else{
            timeStamp.classList.add("pull-right");
        }
        var timeStampText = document.createTextNode("23 Jan 2:05 pm");
        timeStamp.appendChild(timeStampText);

        //Sender image
        var senderImage = document.createElement('img');
        senderImage.classList.add("direct-chat-img");
        senderImage.setAttribute('src', message.userMainImage.path);

        //Message text
        var chatMessage = document.createElement('div');
        chatMessage.classList.add("direct-chat-text");
        var chatMessageText = document.createTextNode(message.response);
        chatMessage.appendChild(chatMessageText);

        messageSenderInfo.appendChild(senderName);
        messageSenderInfo.appendChild(timeStamp);
        messageElement.appendChild(messageSenderInfo);
        messageElement.appendChild(senderImage);
        messageElement.appendChild(chatMessage);

    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

//usernameForm.addEventListener('submit', connect, true);
connect();
messageForm.addEventListener('submit', sendMessage, true);
