package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.domain.Parametro;
import com.quenti.smarttestui.domain.Prueba;
import com.quenti.smarttestui.repository.PruebaRepository;
import com.quenti.smarttestui.service.PruebaService;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.*;
import com.quenti.smarttestui.web.rest.PruebaResource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by juarez on 13/03/17.
 */
@Component
public class RequestComponent {

    private final Logger log = LoggerFactory.getLogger(RequestComponent.class);

    @Inject
    private UserService userService;

    @Inject
    private PruebaRepository pruebaRepository;

    @Inject
    private PruebaService pruebaService;

    UserQuentiDTO userQuentiDTO = new UserQuentiDTO();
    RequestDTO requestDTO = new RequestDTO();
    ObjectMapperCustom objectMapperCustom = new ObjectMapperCustom();


    public String init(EjecucionPruebaDTO epDTO){
        String result = "";
        Long id = 1l;

        try {
            PruebaUrlDTO prueba = pruebaService.ObtenerURIPorIdPrueba(id);
            requestDTO.setUrl(prueba.getUrl());
            requestDTO.setHeaders( new HashMap<String, String>() {{  put("content-type","application/json"); put("accept","application/json"); }} );
            requestDTO.setBody(prueba.getBody());
            Set<Parametro> pruebaParams = prueba.getParametros();
            Map<String, String> parametros = new HashMap<String,String>();
            for (Parametro param : pruebaParams){
                parametros.put(param.getNombre(), param.getValor());
            }
            requestDTO.setParams(parametros);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    private String makePostCall(RequestDTO testDTO) {

        String result = "";
        try {
            log.debug("Doing unirest call");
            HttpResponse<String> mainResponse = Unirest.post("http://localhost:8080/api/ambientes/getModules/1")
                .headers(testDTO.getHeaders())
                .body(testDTO.getBody()).asString();

            result = mainResponse.getBody();
            log.debug("unirest call done");

        } catch (UnirestException e) {
            e.printStackTrace();
            log.debug("unable to make unirest call");
        }


        return result;
    }
}

