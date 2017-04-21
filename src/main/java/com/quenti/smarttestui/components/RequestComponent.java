package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.service.EjecucionPruebaService;
import com.quenti.smarttestui.service.SeguridadService;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.*;
import org.json.JSONObject;
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

    public JSONObject init(EjecucionPruebaDTO ejecucionPruebaDTO) throws InterruptedException {
        //TODO: (No muy necesario) No devolver un string sino un JSON porque en Postman se ve como un string ---> Listo
        //TODO: Determinar como mostrar Json en pantalla

        Long jhUserId = new Long(ejecucionPruebaDTO.getJhUserId());
        String token = seguridadService.findBySeguridadId(jhUserId).getToken(); //token del usuario actual
        JSONObject result;
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
        Thread.sleep(15000);
        result = makePostCall(requestDTO);

        //crear una ejecucion de prueba pendiente con el resultado
        ejecucionPruebaDTO.setResultado(result.toString());
        ejecucionPruebaDTO.setEstado("Completado");
        ejecucionPruebaService.save(ejecucionPruebaDTO);

        return result;
    }

    private JSONObject makePostCall(RequestDTO testDTO) {
        //TODO: Hacer que no se caiga si el request no tiene cuerpo --->Listo

        JSONObject jsonObj;
        String result = "";
        if(testDTO.getBody().isEmpty()){
            String error = "{\n" +"  \"message\": \"Faltan valores en el Request. El cuerpo no debe ser null\",\n" +"}";//;
            return jsonObj = new JSONObject(error);
        }

        try {
            HttpResponse<String> mainResponse = Unirest.post(testDTO.getUrl())
                .headers(testDTO.getHeaders())
                .body(testDTO.getBody())
                .asString();

            result = mainResponse.getBody();

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        jsonObj = new JSONObject(result.toString());
        return jsonObj;
    }
}

