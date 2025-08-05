<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm Mới</title>
    <style>
        /* CSS cho giao diện form */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        input, select {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px;
            width: 100%;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
<h2>Thêm Sản Phẩm Mới</h2>
<form:form action="/products/add" modelAttribute="product" method="post" enctype="multipart/form-data">
    <label for="productName">productName</label>
    <form:input path="productName" />
    <form:errors path="productName" cssClass="error"/>
    <br>
    <label for="price">price</label>
    <form:input path="price" type="number"/>
    <form:errors path="price" cssClass="error"/>
    <br>
    <label for="stock">stock</label>
    <form:input path="stock" />
    <form:errors path="stock" cssClass="error"/>
    <br>
    <label for="status">status</label>
    <form:select path="status" >
        <form:option value="true" >active</form:option>
        <form:option value="false" >inactive</form:option>
    </form:select>
    <br>
    <form:input path="image" type="file"/>
    <form:errors path="image" cssClass="error" />
    <br>
    <button type="submit">Thêm</button>
</form:form>
</body>
</html>