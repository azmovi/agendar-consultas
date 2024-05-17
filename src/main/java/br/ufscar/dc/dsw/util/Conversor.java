package br.ufscar.dc.dsw.util;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Conversor {

    public Date StringParaData(String dataString) {
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

    public Sexo StringParaSexo(String sexoString) {
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
    public byte[] StringParaPdf(String pdfString)
    {
        byte[] pdfData = pdfString.getBytes(StandardCharsets.UTF_8);

        return pdfData;
    }
}
