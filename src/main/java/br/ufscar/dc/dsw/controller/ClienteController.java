package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ClientInfoStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Sexo;

@WebServlet(urlPatterns = {"/teste"})

public class ClienteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String sexoString = request.getParameter("sexo");

        String dataNascimentoString = request.getParameter("nascimento");
        Date dataNascimento = ConverterStringData(dataNascimentoString);
        Sexo sexo = ConverterStringSexo(sexoString);

        System.out.println(dataNascimento);

        out.print(nome);
        out.print(email);
        out.print(senha);
        out.print(cpf);
        out.print(sexo);
        out.print(dataNascimento);
        Cliente cliente = new Cliente(nome, email, senha, cpf, sexo, dataNascimento);
    }

    public Date ConverterStringData(String dataString)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dataFormatada = null;
        try
        {
            dataFormatada = format.parse(dataString);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        return dataFormatada;
    }
    public Sexo ConverterStringSexo(String sexoString) {
        if (sexoString.equals("man") || sexoString.equals("masculino"))
        {
            return Sexo.MASCULINO;
        }
        else 
        {
            if (sexoString.equals("woman") || sexoString.equals("mulher"))
            {
                return Sexo.FEMININO;
            } 
            else 
            {
                return Sexo.OUTRO; //other || outro 
            }
        }
    }
}
