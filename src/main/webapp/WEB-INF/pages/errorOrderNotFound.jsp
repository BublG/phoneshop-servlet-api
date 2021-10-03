<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Order not found">
    <a class="back-link" href="${pageContext.request.contextPath}/products">Home</a>
    <h1>${pageContext.exception.message}</h1>
</tags:master>
