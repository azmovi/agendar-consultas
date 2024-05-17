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

        <div class="formulario">

            <form action="/api" method="post">

                <h3>Login Cliente </h3>

                <label for="Email">
                <fmt:message key="email"/>
                </label> <br>
                <input type="email" id="email" name="email" required>
                <br>
                
                <label for="Senha">
                    <fmt:message key="senha"/>
                </label> <br>

                <input type="password" id="senha" name="senha" required>

                <br>
                
                <input
                    type="submit"
                    class="confirmar"
                    value="<fmt:message key="confirmar"/>"
            </form>
        </div>
    </fmt:bundle>

    <script>

    </script> 
</body>
</html>

