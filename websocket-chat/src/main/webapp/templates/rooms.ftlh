<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Rooms</title>

</head>
<body>
<div>
    <label for="message">Текст сообщения</label>
    <p>rooms here</p>

    <ul>
        <#list rooms as room>
            <li><a href="/rooms/${room.id}">${room.name}</a></li>
        </#list>
    </ul>

    <form method="post">
        <br>
        Название<input type="text" name="name">
        <br>
        <input type="submit" value="создать">
        <br>
    </form>
</div>
</body>
</html>