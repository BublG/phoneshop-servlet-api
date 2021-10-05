<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Too many requests error">
    <a class="back-link" href="${pageContext.request.contextPath}/products">Home</a>
    <h1>Too many requests. Try later.</h1>
</tags:master>