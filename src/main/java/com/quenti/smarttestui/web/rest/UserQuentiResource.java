package com.quenti.smarttestui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.quenti.smarttestui.components.RequestComponent;
import com.quenti.smarttestui.service.UserService;
import com.quenti.smarttestui.service.dto.UserQuentiDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by juarez on 13/03/17.
 */

@RestController
@RequestMapping("/api")
public class UserQuentiResource {

    private final Logger log = LoggerFactory.getLogger(UserQuentiResource.class);

    private final RequestComponent requestComponent;

//    private final UserQuentiResource userQuentiResource;

    public UserQuentiResource(RequestComponent requestComponent) {
        this.requestComponent = requestComponent;
//        this.userQuentiResource = userQuentiResource;
    }

    @PostMapping("/start")
    @Timed
    public String testRequestComponentCall(HttpServletRequest request) {
        log.debug("Starting test request call");
        //DTO SHOULD BE HERE AS A PARAMETER
        return requestComponent.init(request.getMethod());
    }


    @PutMapping("/start")
    @Timed
    public String testRequestComponentCallPut(HttpServletRequest request) {
        log.debug("Starting test request call");
        //DTO SHOULD BE HERE AS A PARAMETER
        return requestComponent.init(request.getMethod());
    }

    @GetMapping("/start")
    @Timed
    public String testRequestComponentCallGet(HttpServletRequest request) {
        log.debug("Starting test request call");
        //DTO SHOULD BE HERE AS A PARAMETER
        return requestComponent.init(request.getMethod());
    }

}
