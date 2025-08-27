<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Sản Phẩm</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .button-container {
            text-align: center;
            margin-bottom: 20px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            margin: 0 10px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button a {
            color: white;
            text-decoration: none;
        }
        button:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        img {
            border-radius: 4px;
            max-width: 50px;
            max-height: 50px;
        }
        .message {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
        /* CSS cho form tìm kiếm */
        .search-container {
            text-align: center;
            margin: 20px 0;
        }
        .search-container input[type="text"] {
            padding: 10px;
            width: 300px;
            border: 1px solid #ccc;
            border-radius: 4px;
            transition: border-color 0.3s;
        }
        .search-container input[type="text"]:focus {
            border-color: #4CAF50;
            outline: none;
        }
        .search-container button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            margin-left: 10px;
            transition: background-color 0.3s;
        }
        .search-container button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<c:if test="${not empty message}">
    <div class="message">
        <script>
            alert("${message}");
        </script>
    </div>
</c:if>
<h2>Danh Sách Sản Phẩm</h2>
<div class="button-container">
    <button><a href="/products/add">Thêm mới sản phẩm</a></button>
    <button><a href="/auth/logout">Đăng xuất</a></button>
</div>
<div class="search-container">
    <form action="/products" method="get">
        <input type="text" name="searchNameProduct" placeholder="Tìm kiếm sản phẩm" value="${search}">
        <button type="submit">Tìm kiếm</button>
    </form>
</div>
<table>
    <tr>
        <th>Tên Sản Phẩm</th>
        <th>Giá</th>
        <th>Số Lượng</th>
        <th>Hình Ảnh</th>
        <th>Trạng Thái</th>
        <th>Hành Động</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.productName}</td>
            <td>${product.price}</td>
            <td>${product.stock}</td>
            <td><img src="${product.image}" alt="${product.productName}"/></td>
            <td>${product.status ? 'Còn bán' : 'Ngừng bán'}</td>
            <td>
                <button style="background-color: orangered"><a href="/products/edit/${product.id}">Sửa</a></button>
                <button style="background-color: red"><a href="/products/delete/${product.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này chứ?')">Xóa</a></button>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>