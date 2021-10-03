<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.Order" scope="request"/>
<tags:master pageTitle="Order overview">
    <a class="back-link" href="${pageContext.servletContext.contextPath}/products">Home</a>
    <h1>Order overview</h1>
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td class="price">
                    Price
                </td>
                <td>
                    Quantity
                </td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${order.items}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src="${cartItem.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
                                ${cartItem.product.description}
                        </a>
                    </td>
                    <td class="price">
                        <a href="priceHistory?id=${cartItem.product.id}">
                            <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                              currencySymbol="${cartItem.product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <fmt:formatNumber value="${cartItem.quantity}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p>
            Subtotal: <fmt:formatNumber value="${order.subTotal}" type="currency"
                                        currencySymbol="${order.items[0].product.currency.symbol}"/>
        </p>
        <p>
            Delivery cost: <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                             currencySymbol="${order.items[0].product.currency.symbol}"/>
        </p>
        <p>
            Total cost: <fmt:formatNumber value="${order.totalCost}" type="currency"
                                          currencySymbol="${order.items[0].product.currency.symbol}"/>
        </p>
        <h2>Your details</h2>
        <table>
            <tr>
                <td>First Name</td>
                <td>${order.firstName}</td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td>${order.lastName}</td>
            </tr>
            <tr>
                <td>Phone</td>
                <td>${order.phone}</td>
            </tr>
            <tr>
                <td>Delivery Address</td>
                <td>${order.deliveryAddress}</td>
            </tr>
            <tr>
                <td>Delivery date</td>
                <td>${order.deliveryDate}</td>
            </tr>
            <tr>
                <td>Payment method</td>
                <td>${order.paymentMethod}</td>
            </tr>
        </table>
</tags:master>