<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Users</title>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>State</th>
        <th>Role</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.state}</td>
            <td>${user.role}</td>
        </tr>
    </#list>
</table>
</body>
</html>