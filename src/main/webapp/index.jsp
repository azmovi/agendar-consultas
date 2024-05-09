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

        header{
            display: grid;
            grid-auto-flow : column;
            place-items: center;
        }

        .usuarios {

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
    <header>
        <h1>BuscarX</h1>

        <div class="usuarios">
            <a href="login.jsp" class="btn login">
                login
            </a>

            <a href="cadastro.jsp" class="btn cadastro">
                cadastro
            </a>

        </div>
    </header>

    <main>

        <h1>texto</h1>

    </main>


    <script>

    </script> 
</body>
</html>

