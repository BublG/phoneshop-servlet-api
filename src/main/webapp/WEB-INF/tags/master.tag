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
        <footer>
            (c) Expert-Soft
        </footer>
    </div>
</div>
</body>
</html>