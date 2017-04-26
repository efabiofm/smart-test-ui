package com.quenti.smarttestui.service.dto;

import com.quenti.smarttestui.domain.Parametro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by efabiofm on 09/04/17.
 */
public class PruebaUrlDTO implements Serializable {
    private String url;
    private Set<Parametro> parametros;
    private String body;
    private Long serviceProviderId;

    private Long serviceGroupId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(Set<Parametro> parametros) {
        this.parametros = parametros;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Long getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }
}
