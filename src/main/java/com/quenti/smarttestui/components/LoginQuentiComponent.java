package com.quenti.smarttestui.components;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.RequestDTO;
import com.quenti.smarttestui.service.dto.UserQuentiDTO;
import com.quenti.smarttestui.web.rest.vm.LoginVM;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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

    UserQuentiDTO userQuentiDTO = new UserQuentiDTO();
    RequestDTO requestDTO = new RequestDTO();
    ObjectMapperCustom objectMapperCustom = new ObjectMapperCustom();


    public Boolean init(LoginVM loginVM) {
        Boolean result = null;
        String resultunirest = "";

                userQuentiDTO.setUsername(loginVM.getUsername());
                userQuentiDTO.setPassword(loginVM.getPassword());
                userQuentiDTO.setUserIPAdress(loginVM.getIpAdress());
                userQuentiDTO.setUserClientId(loginVM.getUserClienteID());
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
                resultunirest = makePostCall(requestDTO);
                JSONObject jsonResult = new JSONObject(resultunirest).getJSONObject("apiResult");
                result = jsonResult.getBoolean("operationSuccessful");

                System.out.println(result);
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


}
