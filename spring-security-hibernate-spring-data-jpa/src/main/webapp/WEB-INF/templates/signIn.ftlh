<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#if error??>
    <h1>Логин или пароль введены неверно</h1>
    <br>
</#if>
<a href="/signUp">Sign Up</a>
<br>
<br>
<form method="post" action="/signIn">
    <input type="text" name="email" placeholder="email">
    <input type="password" name="password" placeholder="password">
    <input type="checkbox" name="remember-me" value="Remember me">
    <input type="submit" value="Sign In">
</form>
</body>
</html>