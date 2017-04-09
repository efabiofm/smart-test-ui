package com.quenti.smarttestui.service.dto;

import com.quenti.smarttestui.domain.Parametro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by efabiofm on 09/04/17.
 */
public class PruebaUrlDTO implements Serializable {
    private String url;
    private HashMap<String,String> parametros;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String,String> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String,String> parametros) {
        this.parametros = parametros;
    }
}
