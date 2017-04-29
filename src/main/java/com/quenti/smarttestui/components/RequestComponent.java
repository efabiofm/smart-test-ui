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
        Long serviceGroupId = ejecucionPruebaDTO.getServiceGroupId();
        Long serviceProviderId = ejecucionPruebaDTO.getServiceProviderId();
        JSONObject result = null;
        try {
            requestDTO.setUrl(ejecucionPruebaDTO.getUrl());
            requestDTO.setHeaders( new HashMap<String, String>() {
                {
                    put("content-type","application/json");
                    put("accept","application/json");
                    put("token",token);
                    put("service-group-id", serviceGroupId != null ? serviceGroupId.toString() : "");
                    put("service-provider-id", serviceProviderId != null ? serviceProviderId.toString() : "");
                }
            } );
            requestDTO.setBody(ejecucionPruebaDTO.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long start = System.currentTimeMillis();
        try{
            result = makePostCall(requestDTO);
            ejecucionPruebaDTO.setResultado(result.toString());
            if(!result.isNull("apiResult")){
                JSONObject apiResult = result.getJSONObject("apiResult");
                if(!apiResult.isNull("operationSuccessful")){
                    Boolean success = apiResult.getBoolean("operationSuccessful");
                    ejecucionPruebaDTO.setEstado(success?"Pass":"Fail");
                }else{
                    ejecucionPruebaDTO.setEstado("Fail");
                }
            }else{
                ejecucionPruebaDTO.setEstado("Pass");
            }
        } catch(Exception e){
            ejecucionPruebaDTO.setEstado("Fail");
            ejecucionPruebaDTO.setResultado(e.getMessage());
        }
        Long elapsed = System.currentTimeMillis() - start;
        ejecucionPruebaDTO.setTiempoRespuesta(elapsed);
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

