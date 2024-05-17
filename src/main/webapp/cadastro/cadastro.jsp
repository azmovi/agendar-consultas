<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>BuscarX</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <fmt:bundle basename="messages">
        <header>
            <div class="titulo">
                <h1>BuscarX</h1>
            </div>
            <a href="../index.jsp" class="btn voltar">
                <fmt:message key="voltar"/>
            </a>
        </header>

        <div class="conteiner-central">
            <div>
                <h1>
                    <fmt:message key="msg_cadastro"/>
                </h1>
            </div>
            <div class="escolha">
                <a href="cliente.jsp" class="btn cliente">
                    <fmt:message key="cliente"/>
                </a>
                <a href="profissional.jsp" class="btn profissional">
                    <fmt:message key="profissional"/>
                </a>
            </div>
        </div>
    </fmt:bundle>
</body>
</html>

