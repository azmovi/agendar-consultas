package br.ufscar.dc.dsw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Conversor {

    public Date ConverterStringData(String dataString) {
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
        else if (sexoString.equals("woman") || sexoString.equals("mulher"))
        {
            return Sexo.FEMININO;
        } 
        else 
        {
            return Sexo.OUTRO;
        }
    }
}
