<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<div class="wrapper">
    <div class="content">
        <header>
            <a href="${pageContext.servletContext.contextPath}">
                <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
                PhoneShop
            </a>
        </header>
        <main>
            <jsp:doBody/>
        </main>
    </div>
    <div class="footer">
        <c:if test="${not empty recentlyViewedList}">
            <jsp:useBean id="recentlyViewedList" scope="request" type="com.es.phoneshop.model.RecentlyViewedList"/>
            <p>
                Recently viewed products:
            </p>
            <table class="recentlyViewedTable">
                <tr>
                    <c:forEach var="product" items="${recentlyViewedList.recentlyViewedProducts}">
                        <td>
                            <div>
                                <img class="product-tile" src="${product.imageUrl}">
                            </div>
                            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                    ${product.description}
                            </a>
                            <div>
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </c:if>
        <footer class="copyright">
            (c) Expert-Soft
        </footer>
    </div>
</div>
</body>
</html>