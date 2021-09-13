<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
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
                <a id="description_asc" class="sort-link" href="?sort=description&order=asc&query=${param.query}">
                    &#129045;
                </a>
                <a id="description_desc" class="sort-link" href="?sort=description&order=desc&query=${param.query}">
                    &#129047;
                </a>
            </td>
            <td class="price">
                Price
                <a id="price_asc" class="sort-link" href="?sort=price&order=asc&query=${param.query}">
                    &#129045;
                </a>
                <a id="price_desc" class="sort-link" href="?sort=price&order=desc&query=${param.query}">
                    &#129047;
                </a>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                </td>
                <td>${product.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>

<script>
    let url = new URL(window.location)
    let sort = url.searchParams.get('sort')
    let order = url.searchParams.get('order')
    document.getElementById(sort + '_' + order).setAttribute('class', 'current-link')
</script>
