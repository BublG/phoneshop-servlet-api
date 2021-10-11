<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>

<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <a class="back-link" href="${pageContext.servletContext.contextPath}/cart">Cart</a>
    <a class="back-link" href="${pageContext.servletContext.contextPath}/advancedSearch">
        Advanced search
    </a>
    <c:if test="${not empty param.message}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            There was an error adding to cart
        </div>
    </c:if>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sort="description" order="asc">
                    <img width="10px" height="17px"
                         src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
                </tags:sortLink>
                <tags:sortLink sort="description" order="desc">
                    <img width="10px" height="17px"
                         src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
                </tags:sortLink>
            </td>
            <td>Quantity</td>
            <td class="price">
                Price
                <tags:sortLink sort="price" order="asc">
                    <img width="10px" height="17px"
                         src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
                </tags:sortLink>
                <tags:sortLink sort="price" order="desc">
                    <img width="10px" height="17px"
                         src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
                </tags:sortLink>
            </td>
            <td></td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td>
                    <form id="${product.id}" method="post">
                        <c:set var="error" value="${errors[product.id]}"/>
                        <input name="quantity" value="${not empty error ? param.quantity : 1}" type="number" min="1">
                        <input name="productId" value="${product.id}" type="hidden">
                    </form>
                    <c:if test="${not empty error}">
                        <div class="error">
                                ${error}
                        </div>
                    </c:if>
                </td>
                <td class="price">
                    <a href="priceHistory?productId=${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
                <td>
                    <button form="${product.id}"
                            formaction="${pageContext.servletContext.contextPath}/products">
                        Add to cart
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
