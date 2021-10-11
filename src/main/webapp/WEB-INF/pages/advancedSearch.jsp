<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced search">
    <a class="back-link" href="${pageContext.servletContext.contextPath}/products">Home</a>
    <h1>Advanced search</h1>
    <form>
        <p>
            Description
            <input name="query" value="${param.query}">
            <select name="searchOption">
                <c:forEach var="option" items="${searchOptions}">
                    <c:if test="${option == param.searchOption}">
                        <option selected>${option}</option>
                    </c:if>
                    <c:if test="${option != param.searchOption}">
                        <option>${option}</option>
                    </c:if>
                </c:forEach>
            </select>
        </p>
        <p>
            Min price
            <input name="minPrice" value="${param.minPrice}">
            <c:if test="${not empty errors['minPrice']}">
                <div class="error">
                     ${errors['minPrice']}
                </div>
            </c:if>
        </p>
        <p>
            Max price
            <input name="maxPrice" value="${param.maxPrice}">
            <c:if test="${not empty errors['maxPrice']}">
                <div class="error">
                    ${errors['maxPrice']}
                </div>
        </c:if>
        </p>
        <button>Search</button>
    </form>
    <c:if test="${not empty products}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
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
    </c:if>
</tags:master>
