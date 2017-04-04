package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.RequestDTO;
import com.quenti.smarttestui.service.dto.UserQuentiDTO;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * Created by juarez on 13/03/17.
 */
@Component
public class RequestComponent {

    // todo: IF LOCALHOST GET THE URL AND PORT FROM THE SYSTEM ENV.
    // todo: USE TYPE AS ENUM OR CONSTANT
    // todo: MAKE THIS CLASS TRULY A COMPONENT, THIS METHODS ARE JUST A POC
    // todo: DONT' TEST THIS USING POST CONSTRUCT, THE SERVER IS NOT AVAILABLE WHEN SPRING IoC RUNS THIS BLOCK OF CODE.

    private final Logger log = LoggerFactory.getLogger(RequestComponent.class);

    @Inject
    private UserService userService;

    UserQuentiDTO userQuentiDTO = new UserQuentiDTO();
    RequestDTO requestDTO = new RequestDTO();
    ObjectMapperCustom objectMapperCustom = new ObjectMapperCustom();


    public String init(String type){
        String result = "";

        switch (type){

            case "POST":
                userQuentiDTO.setUsername("Hopware");
                userQuentiDTO.setPassword("C3nf0t3c");
                userQuentiDTO.setUseripAddress("127.0.0.1");
                userQuentiDTO.setUserClientId("Hopware");
                try {

                    requestDTO.setUrl("http://quenti-usrmgmti.cloudapp.net/users/login");
                    requestDTO.setHeaders( new HashMap<String, String>() {{  put("content-type","application/json"); put("accept","application/json"); }} );
                    requestDTO.setBody(objectMapperCustom.writeValueAsString(userQuentiDTO));


                } catch (Exception e) {
                    e.printStackTrace();
                }


                log.debug("Making post call");
                result = makePostCall(requestDTO);
                JSONObject jsonResult = new JSONObject(result).getJSONObject("apiResult");
//                String jsonData = jsonResult.get("data").toString();
                JSONObject jsonData = new JSONObject(jsonResult.toString()).getJSONObject("data");
                String jsonSession = jsonData.get("sessionKey").toString();
                System.out.println(jsonSession);
//                String json
//                Boolean operationSuccessful = jsonResult.getBoolean("operationSuccessful");
//                if (operationSuccessful){
//
//                    try{
//                        UserQuentiDTO userDto = objectMapperCustom.readValue(requestDTO.getBody().toString(), UserQuentiDTO.class);
//                        JSONObject data = (JSONObject) obj.get("data");
//                        JSONObject profile = (JSONObject) data.get("profile");
//
//                        userDto.getObjTokenDTO.(data.getString("sessionKey"));
//                        userDto.setFirstName(profile.getString("FirstName"));
//                        userDto.setFirstName(profile.getString("LastName"));
//                    }catch (IOException o){
//
//                    }
//
//                }
                break;




            case "PUT":   // same thing as the post
                userQuentiDTO.setUsername("Hopware");
                userQuentiDTO.setPassword("C3nf0t3c");
//                userQuentiDTO.setUseripAddress("127.0.0.1");
//                userQuentiDTO.setUserClientId("Hopware");
                try {

                    requestDTO.setUrl("http://quenti-usrmgmti.cloudapp.net/users/login");
                    requestDTO.setHeaders( new HashMap<String, String>() {{  put("content-type","application/json"); put("accept","application/json"); }} );
                    requestDTO.setBody(objectMapperCustom.writeValueAsString(userQuentiDTO));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.debug("Making post call");
                result = makePostCall(requestDTO);
//                JSONObject jsonResult = new JSONObject(result).getJSONObject("apiResult");
////                String jsonData = jsonResult.get("data").toString();
//                JSONObject jsonData = new JSONObject(jsonResult.toString()).getJSONObject("data");
//                String jsonSession = jsonData.get("sessionKey").toString();
//                System.out.println(jsonSession);
                break;

        }

        log.debug(result);
        return result;
    }



    private String makePostCall(RequestDTO testDTO) {

        String result = "";
        try {
            log.debug("Doing unirest call");
            HttpResponse<String> mainResponse = Unirest.post(testDTO.getUrl()).headers(testDTO.getHeaders()).body(testDTO.getBody()).asString();
            result = mainResponse.getBody();
            log.debug("unirest call done");

        } catch (UnirestException e) {
            e.printStackTrace();
            log.debug("unable to make unirest call");
        }


        return result;
    }


    private String makePutCall(RequestDTO requestDTO) {

        String result = "";
        try {
            log.debug("Doing unirest call");
            HttpResponse<String> mainResponse = Unirest.put(requestDTO.getUrl()).headers(requestDTO.getHeaders()).body(requestDTO.getBody()).asString();
            result = mainResponse.getBody();
            log.debug("unirest call done");
        } catch (UnirestException e) {
            e.printStackTrace();
            log.debug("unable to make unirest call");
        }

        return result;
    }

    private String makeGetCall(RequestDTO requestDTO) {
        String result = "";
        try {
            HttpResponse<String> mainResponse = Unirest.get(requestDTO.getUrl()).headers(requestDTO.getHeaders()).asString();
            result = mainResponse.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return result;
    }


}

