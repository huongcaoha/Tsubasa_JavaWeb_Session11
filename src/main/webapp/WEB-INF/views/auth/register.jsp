<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 7/31/2025
  Time: 8:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        form {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        form input[type="text"],
        form input[type="password"],
        form input[type="email"],
        form input[type="file"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        form button {
            background-color: #5cb85c;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        form button:hover {
            background-color: #4cae4c;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h2>Register</h2>
<form:form action="/auth/register" method="post" modelAttribute="userRegisterDTO" enctype="multipart/form-data">
    <form:input path="username" placeholder="Username" />
    <form:errors path="username" cssClass="error" />
    <form:input path="password" placeholder="Password" />
    <form:errors path="password" cssClass="error" />
    <form:input path="email" placeholder="Email" />
    <form:errors path="email" cssClass="error" />
    <label for="avatar">Avatar</label>
    <form:input path="avatar" type="file" />
    <form:errors path="avatar" cssClass="error"/>
    <button type="submit">Register</button>
</form:form>
</body>
</html>
