<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

        header {
            display: grid;
            grid-auto-flow: column;
            place-items: center;
        }

        .btn {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
            width: 45%;
            text-align: center;
            font-weight: bold;
            color: #000000;
            text-decoration: none; 
            transition: background-color 0.7s ease, color 0.4s ease;
        }

        .btn:hover {
            color: #ffffff;
            background-color: #000000;
        }

        .formulario {
            margin: 5%;
            border: 4px solid black;
            border-radius: 25px;
            padding: 5%;
        }

        h3 {
            margin-top: -5%;
        }

        label {
            font-weight: bold;
        }

        input, select {
            width: 85%;
            margin: 2%;
            padding: 1%;
            background-color: white;
            border: 4px solid black;
            border-radius: 10px;
        }

        .confirmar {
            margin-left: 30%;
            margin-right: 30%;
            background-color: white;
            font-weight: bold;
            cursor: pointer;
            width: 35%;
            transition: border-color 0.3s ease, color 0.4s ease;
        }

        .confirmar:hover {
            background-color: #28e673;
            border-color: white;
        }
    </style>
</head>
<body>
    <fmt:bundle basename="messages">
        <header>
            <div class="titulo">
                <h1>BuscarX</h1>
            </div>
            <a href="cadastro.jsp" class="btn voltar">
                <fmt:message key="voltar"/>
            </a>
        </header>

        <div class="formulario">
            <form action="/cadastrar/profissional" method="post">
                <h3>
                    <fmt:message key="profissional" />
                </h3>

                <label for="nome">
                    <fmt:message key="nome"/>
                </label> <br>
                <input type="text" id="nome" name="nome" required><br>

                <label for="email">
                    <fmt:message key="email"/>
                </label> <br>
                <input type="email" id="email" name="email" required><br>

                <label for="senha">
                    <fmt:message key="senha"/>
                </label> <br>
                <input type="password" id="senha" name="senha" required><br>

                <label for="cpf">
                    <fmt:message key="cpf"/>
                </label> <br>
                <input type="number" id="cpf" name="cpf" required><br>

                <label for="especialidade">
                    <fmt:message key="especialidade"/>
                </label> <br>
                <input type="text" id="especialidade" name="especialidade" required><br>

                <label for="curriculo">
                    <fmt:message key="curriculo"/>
                </label> <br>
                <input type="file" accpet=".pdf" id="curriculo" name="curriculo" required><br>

                <input
                    type="submit"
                    class="confirmar"
                    value="<fmt:message key="confirmar"/>"
                >

            </form>
        </div>

    </fmt:bundle>
</body>
</html>
