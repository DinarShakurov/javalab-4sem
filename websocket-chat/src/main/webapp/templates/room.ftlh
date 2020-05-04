<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Чат</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script>
        var webSocket;
        function connect() {

            webSocket = new SockJS("http://localhost:8080/chat?room=${roomDto.roomId}");


            webSocket.onmessage = function receiveMessage(response) {
                let data = response['data'];
                let json = JSON.parse(data);
                $('#messagesList').first().append("<li>" + json['from'] + ': ' + json['text'] + "</li>")
            }
            webSocket.onerror = function(e) {
                alert("ошибка")
            }
            webSocket.onclose = function() {
                alert("вы не зарегистрированы")
            };
        }

        function sendMessage(text) {
            let message = {
                "text": text
            };

            webSocket.send(JSON.stringify(message));
        }
    </script>
</head>
<body onload="connect()">
<div>
    <label for="message">Текст сообщения</label>
    <input name="message" id="message" placeholder="Сообщение">
    <button onclick="sendMessage($('#message').val())">Отправить</button>
    <h3>Сообщения</h3>
    <ul id="messagesList">
        <#list roomDto.messages as message>
            <li>${message.user.login}: ${message.message}</li>
        </#list>
    </ul>
</div>
</body>
</html>


