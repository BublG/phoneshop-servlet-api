<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <p>
        Cart: ${cart}
    </p>
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
                    <img width="10px" height="17px" src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
                </tags:sortLink>
                <tags:sortLink sort="description" order="desc">
                    <img width="10px" height="17px" src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
                </tags:sortLink>
            </td>
            <td class="price">
                Price
                <tags:sortLink sort="price" order="asc">
                    <img width="10px" height="17px" src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
                </tags:sortLink>
                <tags:sortLink sort="price" order="desc">
                    <img width="10px" height="17px" src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
                </tags:sortLink>
            </td>
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
                <td class="price">
                    <a href="priceHistory?productId=${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>
