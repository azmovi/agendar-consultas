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
            font-family: "Comic Sans MS";
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
    <c:if test="${not empty sessionScope.ErrorCriarNovoUsuario}">
        <script>
            alert('${sessionScope.ErrorCriarNovoUsuario}');
        </script>
        <c:remove var="ErrorCriarNovoUsuario" scope="session"/>
    </c:if>

    <fmt:bundle basename="messages">
        <header>
            <div class="titulo">
                <h1>BuscarX</h1>
            </div>
            <a href="/AgendarConsultas/cadastro/cadastro.jsp" class="btn voltar">
                <fmt:message key="voltar"/>
            </a>
        </header>

        <div class="formulario">
            <form id="form" action="/AgendarConsultas/profissional" method="post">
                <h3>
                    <fmt:message key="profissional" />
                </h3>

                <label for="nome">
                    <fmt:message key="nome"/>
                </label> <br>
                <input 
                    type="text"
                    id="nome"
                    name="nome"
                ><br>

                <label for="email">
                    <fmt:message key="email"/>
                </label> <br>
                <input 
                    type="email"
                    id="email"
                    name="email"
                ><br>

                <label for="senha">
                    <fmt:message key="senha"/>
                </label> <br>
                <input
                    type="password"
                    id="senha"
                    name="senha"
                ><br>

                <label for="cpf">
                    <fmt:message key="cpf"/>
                </label> <br>
                <input
                    type="text"
                    id="cpf"
                    name="cpf"
                    maxlength="14"
                ><br>

                <label for="especialidade">
                    <fmt:message key="especialidade"/>
                </label> <br>
                <input
                    type="text"
                    id="especialidade" 
                    name="especialidade"
                ><br>

                <label for="curriculo">
                    <fmt:message key="curriculo"/>
                </label> <br>
                <input
                    type="file"
                    id="pdfData"
                    name="pdfData"
                    accept="application/pdf"
                ><br>

                <input
                    type="submit"
                    class="confirmar"
                    value="<fmt:message key='confirmar'/>"
                >
            </form>
        </div>

    </fmt:bundle>

    <script>
        // Função para formatar CPF com máscara
        function formatCPF(cpf) {
            return cpf
                .replace(/\D/g, '') // Remove caracteres não numéricos
                .replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, '$1.$2.$3-$4'); // Adiciona a máscara
        }

        // Função para remover a máscara do CPF
        function unformatCPF(cpf) {
            return cpf.replace(/\D/g, ''); // Remove todos os caracteres não numéricos
        }

        // Função para verificar e alertar sobre validade dos campos
        function validateForm() {
            const nome = document.getElementById('nome').value.trim();
            const email = document.getElementById('email').value.trim();
            const senha = document.getElementById('senha').value.trim();
            const cpf = document.getElementById('cpf').value.trim();
            const especialidade = document.getElementById('especialidade').value.trim();
            const pdf = document.getElementById('pdfData');
            const file = pdf.files[0];

            // Validar obrigatoriedade dos campos
            if (!nome) {
                alert('O campo Nome é obrigatório.');
                return false;
            }
            if (!email) {
                alert('O campo Email é obrigatório.');
                return false;
            }
            if (!senha) {
                alert('O campo Senha é obrigatório.');
                return false;
            }
            if (!cpf) {
                alert('O campo CPF é obrigatório.');
                return false;
            }
            if (!especialidade) {
                alert('O campo Especialidade é obrigatório.');
                return false;
            }
            if (!file) {
                alert('O campo Currículo é obrigatório e deve ser um arquivo PDF.');
                return false;
            }
            if (file.type !== 'application/pdf') {
                alert('O currículo deve ser um arquivo PDF.');
                return false;
            }

            // Remover a máscara do CPF antes de enviar
            document.getElementById('cpf').value = unformatCPF(cpf);

            return true;
        }

        // Configurar validações e máscara ao carregar a página
        document.addEventListener('DOMContentLoaded', function() {
            const cpfInput = document.getElementById('cpf');
            cpfInput.addEventListener('input', function() {
                this.value = formatCPF(unformatCPF(this.value));
            });

            const form = document.getElementById('form');
            form.addEventListener('submit', function(event) {
                if (!validateForm()) {
                    event.preventDefault(); // Impede o envio do formulário se a validação falhar
                }
            });
        });
    </script>
</body>
</html>
