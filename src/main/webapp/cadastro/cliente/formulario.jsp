<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>BuscarX</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<% 
    String errorMessage = (String) request.getAttribute("ErrorCriarNovoUsuario");
    String nome = (String) request.getAttribute("nome");
    String email = (String) request.getAttribute("email");
    String senha = (String) request.getAttribute("senha");
    String cpf = (String) request.getAttribute("cpf");
    String dataNascimentoString = (String) request.getAttribute("dataNascimentoString");
    String sexoString = (String) request.getAttribute("sexoString");

    if (errorMessage != null) { 
%>

    <script>
        alert('<%= errorMessage %>');
    </script>

<% } %>

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
            <form id="form" action="criar_cliente" method="post">
                <h3>
                    <fmt:message key="cliente" />
                </h3>

                <label for="nome">
                    <fmt:message key="nome"/>
                </label> <br>
                <input
                    type="text"
                    id="nome"
                    name="nome"
                    value="<%= nome != null ? nome : "" %>"
                    required
                ><br>

                <label for="email">
                    <fmt:message key="email"/>
                </label> <br>
                <input 
                    type="email"
                    id="email"
                    name="email"
                    value="<%= email != null ? email: "" %>"
                    required
                ><br>

                <label for="senha">
                    <fmt:message key="senha"/>
                </label> <br>
                <input
                    type="password"
                    id="senha"
                    name="senha"
                    value="<%= senha != null ? senha: "" %>"
                    required
                ><br>

                <label for="cpf">
                    <fmt:message key="cpf"/>
                </label> <br>
                <input
                    type="number"
                    id="cpf"
                    name="cpf"
                    value="<%= cpf != null ? cpf : "" %>"
                    required
                ><br>

                <label for="sexo">
                    <fmt:message key="sexo"/>
                </label><br>

                <select name="sexo" id="opcao">
                    <option>
                        <fmt:message key="masculino"/>
                    </option>
                    <option>
                        <fmt:message key="feminino"/>
                    </option>
                    <option>
                        <fmt:message key="outro"/>
                    </option>
                </select> <br>

                <label for="data de nascimento">
                    <fmt:message key="nascimento"/>
                </label> <br>
                <input 
                    type="date"
                    id="nascimento"
                    name="nascimento"
                    required
                    value="<%= dataNascimentoString != null ? dataNascimentoString : "" %>"
                ><br>

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

