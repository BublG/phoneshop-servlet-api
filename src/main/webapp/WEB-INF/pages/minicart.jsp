<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>
Cart: ${cart.totalQuantity} items; total cost -
<fmt:formatNumber value="${cart.totalCost}" type="currency"
                  currencySymbol="${cart.items[0].product.currency.symbol}"/>
