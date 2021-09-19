<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <a class="back-link" href="${pageContext.servletContext.contextPath}/products">Home</a>
    <p>
        Cart: ${cart}
    </p>
    <c:if test="${not empty param.message and empty error}">
        <div class="success">
            ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">
            There was an error adding to cart
        </div>
    </c:if>
    <p>
            ${product.description}
    </p>
    <form method="post">
        <table>
            <tr>
                <td>Image</td>
                <td>
                    <img src="${product.imageUrl}">
                </td>
            </tr>
            <tr>
                <td>Code</td>
                <td>${product.code}</td>
            </tr>
            <tr>
                <td>Price</td>
                <td>${product.price}</td>
            </tr>
            <tr>
                <td>Stock</td>
                <td>${product.stock}</td>
            </tr>
        </table>
        <div class="quantity">
            <span>Quantity:</span>
            <span>
                <input name="quantity" value="${not empty error ? param.quantity : 1}" type="number" min="1">
                <c:if test="${not empty error}">
                    <div class="error">
                            ${error}
                    </div>
                </c:if>
            </span>
        </div>
        <p>
            <button>Add to cart</button>
        </p>
    </form>
</tags:master>
