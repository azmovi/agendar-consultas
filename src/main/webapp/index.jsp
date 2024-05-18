<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>BuscarX</title>
    <style>
        body {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
        }

        header{
            display: grid;
            grid-auto-flow : column;
            place-items: center;
        }

        .usuario {
            margin: 9%;
            border: 4px solid black;
            border-radius: 10px;
            padding: 5%;

            justify-items: center;

            display: grid;
            grid-auto-flow: column;
        }

        .btn {
            margin: 5px;
            border: 4px solid black;
            border-radius: 10px;
            padding: 1em;

            font-weight: bold;
            color: #000000;
            text-decoration: none; 
            transition: background-color 0.7s ease, color 0.4s ease;
        }

        .btn:hover {
            color: #ffffff;
            background-color: #000000;
        }

        main {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
        }
    </style>
</head>
<body>
    <c:set var="cliente" value="${sessionScope.cliente}" />
    <c:set var="profissional" value="${sessionScope.profissional}" />
    <c:set var="listaProfissionais" value="${sessionScope.listaProfissionais}" />
    <c:set var="teste" value="${sessionScope.teste}" />

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
        <h1 align="center"> Lista de Profissionais </h1>
        <table border="1">
            <tr>
                <th>Nome</th>
                <th>Especialidade</th>
            </tr>
            <c:forEach var="profissional" items="${listaProfissionais}">
                <tr>
                    <td>${profissional.nome}</td>
                    <td>${profissional.especialidade}</td>
                </tr>
            </c:forEach>
        </table>
    </main>
    </fmt:bundle>

    <script>
        window.onload = function() {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "get_profissionais", false);
            xhr.send();
        };
    </script>
</body>
</html>

