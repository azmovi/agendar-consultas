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
    <c:if test="${not empty sessionScope.erroNovoUsuario}">
        <script>
            alert("${sessionScope.erroNovoUsuario}");
        </script>
        <c:remove var="erroNovoUsuario" scope="session"/>
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
            <form id="form" action="/AgendarConsultas/cliente" method="post">
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
                    value="${sessionScope.nome != null ? sessionScope.nome : ''}"
                ><br>

                <label for="email">
                    <fmt:message key="email"/>
                </label> <br>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value="${sessionScope.email != null ? sessionScope.email : ''}"
                ><br>

                <label for="senha">
                    <fmt:message key="senha"/>
                </label> <br>
                <input
                    type="password"
                    id="senha"
                    name="senha"
                    value="${sessionScope.senha != null ? sessionScope.senha : ''}"
                ><br>

                <label for="cpf">
                    <fmt:message key="cpf"/>
                </label> <br>
                <input
                    type="text"
                    id="cpf"
                    name="cpf"
                    maxlength="14"
                    value="${sessionScope.cpf != null ? sessionScope.cpf : ''}"
                ><br>

                <label for="sexo">
                    <fmt:message key="sexo"/>
                </label><br>

                <select name="sexo" id="opcao">
                    <option value="masculino" ${'masculino' eq sessionScope.sexoString ? 'selected' : ''}>
                        <fmt:message key="masculino"/>
                    </option>
                    <option value="feminino" ${'feminino' eq sessionScope.sexoString ? 'selected' : ''}>
                        <fmt:message key="feminino"/>
                    </option>
                    <option value="outro" ${'outro' eq sessionScope.sexoString ? 'selected' : ''}>
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
                    value="${sessionScope.dataNascimentoString != null ? sessionScope.dataNascimentoString : ''}"
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

        function validateForm(event) {
            const nome = document.getElementById('nome').value.trim();
            const email = document.getElementById('email').value.trim();
            const senha = document.getElementById('senha').value.trim();
            const cpf = document.getElementById('cpf').value.trim();
            const nascimento = document.getElementById('nascimento').value;

            if (!nome) {
                alert('Nome é obrigatório.');
                return false;
            }

            if (!email) {
                alert('Email inválido.');
                return false;
            }

            if (!senha) {
                alert('Senha é obrigatória.');
                return false;
            }

            if (!cpf) {
                alert('CPF é obrigatório e deve ter 11 dígitos.');
                return false;
            }

            if (!nascimento) {
                alert('Data de nascimento é obrigatória.');
                return false;
            }

            // Remover a máscara do CPF antes de enviar
            document.getElementById('cpf').value = unformatCPF(cpf);
            return true;
        }

        // function applyCPFMask(event) {
        //     let input = event.target;
        //     input.value = formatCPF(input.value);
        // }

        function formatDateToYYYYMMDD(date) {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
        }

        function validateDate() {
            const dateInput = document.getElementById('nascimento');
            const today = new Date();
            const formattedDate = formatDateToYYYYMMDD(today);

            // Definir o atributo max
            dateInput.setAttribute('max', formattedDate);

            // Adicionar o manipulador de eventos para validar a data selecionada
            dateInput.addEventListener('change', function() {
                const selectedDate = new Date(this.value);
                if (selectedDate > today) {
                    alert('A data não pode ser no futuro.');
                    this.value = ''; // Limpar o campo se a data for inválida
                }
            });
        }

        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('form').addEventListener('submit', function(event) {
                if (!validateForm(event)) {
                    event.preventDefault(); // Impede o envio do formulário se a validação falhar
                }
            });
            const cpfInput = document.getElementById('cpf');
            cpfInput.addEventListener('input', function() {
                this.value = formatCPF(unformatCPF(this.value));
            });
            document.getElementById('nascimento').addEventListener('input', validateDate);
        });
    </script>
</body>
</html>
