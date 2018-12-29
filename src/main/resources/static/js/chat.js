'use strict';
var warning = document.querySelector('#warning');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var is_connected = false;
var stompClient = null;

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

function checkIfOnlineMarkAlreadyAdded(idName){
    if($('#'+idName).length === 0) {
        return false;
    }
    return true;
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if(message.type === 'JOIN') {
        if(!checkIfOnlineMarkAlreadyAdded("sender-" + message.sender)){
            var contactElement = document.createElement('li');
            contactElement.id = "sender-" + message.sender;
            var messageImg = document.createElement('img');
            messageImg.classList.add("contacts-list-img");
            messageImg.setAttribute('src', message.userImagePath);
            contactElement.appendChild(messageImg);
            var contactNameElement = document.createElement('span');
            contactNameElement.classList.add("contacts-list-name");
            var senderNameText = document.createTextNode(message.sender + "  ");
            contactNameElement.appendChild(senderNameText);
            var onlineMark = document.createElement('i');
            onlineMark.id = "sender-currentstatus-" + message.sender;
            onlineMark.classList.add("fa");
            onlineMark.classList.add("fa-circle");
            onlineMark.classList.add("text-success");
            // var pullRight = document.createElement('small');
            // pullRight.classList.add("contacts-list-date");
            // pullRight.classList.add("pull-right");
            // pullRight.appendChild(onlineMark);
            // contactNameElement.appendChild(pullRight);
            contactNameElement.appendChild(onlineMark);
            var contactInfoElement = document.createElement('div');
            contactInfoElement.classList.add("contacts-list-info");
            var contactMsgElement = document.createElement('span');
            contactMsgElement.classList.add("contacts-list-msg");
            contactInfoElement.appendChild(contactNameElement);
            contactElement.appendChild(contactInfoElement);
            $("#contacts-list").append(contactElement);
        } else {
            $("#sender-currentstatus-" + message.sender).removeClass("text-dark");
            $("#sender-currentstatus-" + message.sender).addClass("text-success");
        }
    } else if (message.type === 'LEAVE') {
        $("#sender-currentstatus-" + message.sender).removeClass("text-success");
        $("#sender-currentstatus-" + message.sender).addClass("text-dark");
    } else {
        var messageElement = document.createElement('div');
        messageElement.classList.add("direct-chat-msg");
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
        var timeStampText = document.createTextNode(message.updated);
        timeStamp.appendChild(timeStampText);

        //Sender image
        var senderImage = document.createElement('img');
        senderImage.classList.add("direct-chat-img");
        senderImage.setAttribute('src', message.userImagePath);

        //Message text
        var chatMessage = document.createElement('pre');
        chatMessage.classList.add("direct-chat-text");
        var chatMessageText = document.createTextNode(message.response);
        chatMessage.appendChild(chatMessageText);

        messageSenderInfo.appendChild(senderName);
        messageSenderInfo.appendChild(timeStamp);
        messageElement.appendChild(messageSenderInfo);
        messageElement.appendChild(senderImage);
        messageElement.appendChild(chatMessage);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;

    }

}

connect();
messageForm.addEventListener('submit', sendMessage, true);
