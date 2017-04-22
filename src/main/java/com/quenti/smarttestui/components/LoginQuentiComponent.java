package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.service.SeguridadService;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.RequestDTO;
import com.quenti.smarttestui.service.dto.UserDTO;
import com.quenti.smarttestui.service.dto.UserQuentiDTO;
import com.quenti.smarttestui.web.rest.vm.LoginVM;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by juarez on 20/03/17.
 */
@Component
public class LoginQuentiComponent {

    // todo: IF LOCALHOST GET THE URL AND PORT FROM THE SYSTEM ENV.
    // todo: USE TYPE AS ENUM OR CONSTANT
    // todo: MAKE THIS CLASS TRULY A COMPONENT, THIS METHODS ARE JUST A POC
    // todo: DONT' TEST THIS USING POST CONSTRUCT, THE SERVER IS NOT AVAILABLE WHEN SPRING IoC RUNS THIS BLOCK OF CODE.

    private final Logger log = LoggerFactory.getLogger(LoginQuentiComponent.class);

    @Inject
    private UserService userService;

    @Inject
    private SeguridadService seguridadService;

    UserQuentiDTO userQuentiDTO = new UserQuentiDTO();
    RequestDTO requestDTO = new RequestDTO();
    ObjectMapperCustom objectMapperCustom = new ObjectMapperCustom();


    public UserQuentiDTO init(LoginVM loginVM) {
        String result = "";
        UserQuentiDTO userMapeado = new UserQuentiDTO();

//        Boolean isSuccessful = null;

        userQuentiDTO.setUsername(loginVM.getUsername());
        userQuentiDTO.setPassword(loginVM.getPassword());
        userQuentiDTO.setUseripAddress(loginVM.getipAddress());
        userQuentiDTO.setUserClientId(loginVM.getuserClientId());
        try {

            requestDTO.setUrl("http://quenti-usrmgmti.cloudapp.net/users/login");
            requestDTO.setHeaders(new HashMap<String, String>() {{
                put("content-type", "application/json");
                put("accept", "application/json");
            }});
            requestDTO.setBody(objectMapperCustom.writeValueAsString(userQuentiDTO));

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("Making post call");
        result = makePostCall(requestDTO);
        JSONObject jsonResult = new JSONObject(result).getJSONObject("apiResult");
        Boolean operationSuccessful = jsonResult.getBoolean("operationSuccessful");

        if (operationSuccessful) {
            try {
                userMapeado = objectMapperCustom.readValue(requestDTO.getBody().toString(), UserQuentiDTO.class);
                JSONObject data = (JSONObject) jsonResult.get("data");
                JSONObject profile = (JSONObject) data.get("profile");
                userMapeado.setObjTokenDTO(data.getString("sessionKey"));
                userMapeado.setFirstName(profile.getString("FirstName"));
                userMapeado.setLastName(profile.getString("LastName"));
            } catch (IOException o) {

            }
        }

        return userMapeado;
    }

    public Boolean setOrganizationsCode(String ptoken){

        Boolean band = false;
        String result = "";
        try {

            requestDTO.setUrl("http://quenti-usrmgmti.cloudapp.net/users/setOrganizationCodes?tenantId=1&organizationCode=00001&organizationalUnitCode=001");
            requestDTO.setHeaders(new HashMap<String, String>() {{
                put("content-type", "application/json");
                put("accept", "application/json");
                put("token", ptoken);
            }});
            requestDTO.setBody("");

        } catch (Exception e) {
            e.printStackTrace();
        }

        result = makePostCall(requestDTO);
        JSONObject jsonResult = new JSONObject(result).getJSONObject("apiResult");
        Boolean operationSuccessful = jsonResult.getBoolean("operationSuccessful");

        if (operationSuccessful) {
            band = true;
        }


        return band;
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


}
