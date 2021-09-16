<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sort" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sort=${sort}&order=${order}&query=${param.query}"
class="sort-link ${sort eq param.sort and order eq param.order ? 'current-sort' : ''}"><jsp:doBody/></a>