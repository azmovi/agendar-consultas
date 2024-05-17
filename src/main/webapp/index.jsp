<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="br.ufscar.dc.dsw.domain.Cliente" %>
<%@ page import="br.ufscar.dc.dsw.domain.Profissional" %>

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
<% 
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    Profissional profissional = (Profissional) session.getAttribute("profissional");
%>
    <fmt:bundle basename="messages">
        <header>
            <h1>BuscarX</h1>

            <div class="usuario">
                <% if (cliente != null) { %>
                    <a href="cliente.jsp" class="btn cliente">
                        <fmt:message key="BoasVindas"/> <%= cliente.getNome() %>
                    </a>
                <% } else if (profissional != null) { %>
                    <a href="profissional.jsp" class="btn profissional">
                        Bem-vindo <%= profissional.getNome() %>
                    </a>
                <% } else { %>
                    <a href="login.jsp" class="btn login">
                        <fmt:message key="entrar"/>
                    </a>

                    <a href="cadastro/cadastro.jsp" class="btn cadastro">
                        <fmt:message key="cadastro"/>
                    </a>
                <% } %>

            </div>
        </header>
        <main>
            <h1>texto</h1>
        </main>
    </fmt:bundle>
    <script>
    </script> 
</body>
</html>

