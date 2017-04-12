package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.domain.Parametro;
import com.quenti.smarttestui.domain.Prueba;
import com.quenti.smarttestui.repository.PruebaRepository;
import com.quenti.smarttestui.service.PruebaService;
import com.quenti.smarttestui.service.SeguridadService;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.*;
import com.quenti.smarttestui.web.rest.PruebaResource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
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
    private SeguridadService seguridadService;

    @Inject
    private PruebaService pruebaService;

    //UserQuentiDTO userQuentiDTO = new UserQuentiDTO();
    RequestDTO requestDTO = new RequestDTO();
    //ObjectMapperCustom objectMapperCustom = new ObjectMapperCustom();


    public String init(EjecucionPruebaDTO ejecucionPruebaDTO){
        Long jhUserId = new Long(ejecucionPruebaDTO.getJhUserId());
        SeguridadDTO seguridadDTO = seguridadService.findBySeguridadId(jhUserId);
        String result = "";
        try {
            requestDTO.setUrl(ejecucionPruebaDTO.getUrl());
            requestDTO.setHeaders( new HashMap<String, String>() {
                {
                    put("content-type","application/json");
                    put("accept","application/json");
                    put("token",seguridadDTO.getToken());
                }
            } );
            requestDTO.setBody(ejecucionPruebaDTO.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = makePostCall(requestDTO);
        return result;
    }



    private String makePostCall(RequestDTO testDTO) {

        String result = "";
        try {
            log.debug("Doing unirest call");
            HttpResponse<String> mainResponse = Unirest.post(testDTO.getUrl())
                .headers(testDTO.getHeaders())
                .body(testDTO.getBody())
                .asString();

            result = mainResponse.getBody();
            log.debug("unirest call done");

        } catch (UnirestException e) {
            e.printStackTrace();
            log.debug("unable to make unirest call");
        }


        return result;
    }
}

