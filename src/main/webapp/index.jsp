<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>BuscarX</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <c:set var="cliente" value="${sessionScope.cliente}" />
    <c:set var="profissional" value="${sessionScope.profissional}" />

    <fmt:bundle basename="messages">
    <header>
        <h1>BuscarX</h1>

        <div class="usuario">
            <c:choose>
                <c:when test="${cliente != null}">
                    <a href="perfil/cliente.jsp" class="btn cliente">
                        <fmt:message key="BoasVindas" /> ${cliente.nome}
                    </a>
                </c:when>
                <c:when test="${profissional != null}">
                    <a href="perfil/profissional.jsp" class="btn profissional">
                        <fmt:message key="BoasVindas" /> ${profissional.nome}
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="login/login.jsp" class="btn login">
                        <fmt:message key="entrar"/>
                    </a>
                    <a href="cadastro/cadastro.jsp" class="btn cadastro">
                        <fmt:message key="cadastro"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
    <main>
        <h1>Texto</h1>
    </main>
    </fmt:bundle>
</body>
</html>

