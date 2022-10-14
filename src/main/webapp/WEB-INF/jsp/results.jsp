<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="scheme" value="${pageContext.request.scheme}"/>
<c:set var="serverName" value="${pageContext.request.serverName}"/>
<c:set var="serverPort" value="${pageContext.request.serverPort}"/>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h2>CBIR Engine (Based on CNN - VGG16)</h2>
    <br/>
    <h4>.:Query:.</h4>
    <div style="background: #F1F5F9; border: 1px solid gray; width: 80px; box-sizing: content-box;">
        <img width="80px" height="80px"
             src="${scheme}://${serverName}:${serverPort}/cbir/static/requests/${name}"/>
    </div>
    <h4>.:Results:.</h4>
    <div class="row">
        <c:forEach items="${products}" var="product">
            <div class="col-md-1" style="padding: 4px">
                <div style="background: #F1F5F9; border: 1px solid gray;">
                    <img width="80px" height="80px" src="${product.thumbnail}"/>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
