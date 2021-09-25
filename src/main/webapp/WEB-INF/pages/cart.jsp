<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <c:if test="${not empty param.message}">
        <div class="success">
                Success
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            There were errors updating the cart
        </div>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
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
            <c:forEach var="cartItem" items="${cart.items}" varStatus="status">
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
                        <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
                        <c:set var="error" value="${errors[cartItem.product.id]}"/>
                        <input name="quantity" value="${not empty error ? paramValues.quantity[status.index] : quantity}">
                        <c:if test="${not empty error}">
                            <div class="error">
                                    ${error}
                            </div>
                        </c:if>
                        <input type="hidden" name="productId" value="${cartItem.product.id}">
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p>
            <button>Update</button>
        </p>
    </form>
</tags:master>