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
            <a href="../cadastro.jsp" class="btn voltar">
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

