package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.domain.EjecucionPrueba;
import com.quenti.smarttestui.service.EjecucionPruebaService;
import com.quenti.smarttestui.service.SeguridadService;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import java.util.HashMap;

/**
 * Created by juarez on 13/03/17.
 * Modified by efabiofm on 12/04/17.
 */
@Component
public class RequestComponent {

    private final Logger log = LoggerFactory.getLogger(RequestComponent.class);

    @Inject
    private UserService userService;

    @Inject
    private SeguridadService seguridadService;

    @Inject
    private EjecucionPruebaService ejecucionPruebaService;

    RequestDTO requestDTO = new RequestDTO();

    public String init(EjecucionPruebaDTO ejecucionPruebaDTO){
        //TODO: (No muy necesario) No devolver un string sino un JSON porque en Postman se ve como un string

        Long jhUserId = new Long(ejecucionPruebaDTO.getJhUserId());
        String token = seguridadService.findBySeguridadId(jhUserId).getToken(); //token del usuario actual
        String result = "";
        try {
            requestDTO.setUrl(ejecucionPruebaDTO.getUrl());
            requestDTO.setHeaders( new HashMap<String, String>() {
                {
                    put("content-type","application/json");
                    put("accept","application/json");
                    put("token",token);
                }
            } );
            requestDTO.setBody(ejecucionPruebaDTO.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = makePostCall(requestDTO);

        //crear una ejecucion de prueba pendiente con el resultado
        ejecucionPruebaDTO.setResultado(result);
        ejecucionPruebaDTO.setEstado("pendiente");
        ejecucionPruebaService.save(ejecucionPruebaDTO);

        return result;
    }



    private String makePostCall(RequestDTO testDTO) {
        //TODO: Hacer que no se caiga si el request no tiene cuerpo

        String result = "";
        try {
            HttpResponse<String> mainResponse = Unirest.post(testDTO.getUrl())
                .headers(testDTO.getHeaders())
                .body(testDTO.getBody())
                .asString();

            result = mainResponse.getBody();

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return result;
    }
}

