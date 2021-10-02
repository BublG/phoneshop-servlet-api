<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.Order" scope="request"/>
<tags:master pageTitle="Checkout">
    <a class="back-link" href="${pageContext.servletContext.contextPath}/products">Home</a>
    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            There were errors placing order
        </div>
    </c:if>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
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
            <tags:orderFormRow name="firstName" label="First Name" errors="${errors}" order="${order}"/>
            <tags:orderFormRow name="lastName" label="Last Name" errors="${errors}" order="${order}"/>
            <tags:orderFormRow name="phone" label="Phone" errors="${errors}" order="${order}"/>
            <tags:orderFormRow name="deliveryAddress" label="Delivery Address" errors="${errors}" order="${order}"/>
            <tr>
                <td>Delivery date<span style="color: red">*</span></td>
                <td>
                    <c:set var="error" value="${errors['deliveryDate']}"/>
                    <input type="date" name="deliveryDate"
                           value="${not empty error ? param.deliveryDate : order.deliveryDate}">
                    <c:if test="${not empty error}">
                        <div class="error">
                                ${error}
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>Payment method<span style="color: red">*</span></td>
                <td>
                    <select name="paymentMethod">
                        <option></option>
                        <c:forEach var="method" items="${paymentMethods}">
                            <c:if test="${method == order.paymentMethod}">
                                <option selected>${method}</option>
                            </c:if>
                            <c:if test="${method != order.paymentMethod}">
                                <option>${method}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <c:set var="error" value="${errors['paymentMethod']}"/>
                    <c:if test="${not empty error}">
                        <div class="error">
                                ${error}
                        </div>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Place order</button>
        </p>
    </form>
</tags:master>