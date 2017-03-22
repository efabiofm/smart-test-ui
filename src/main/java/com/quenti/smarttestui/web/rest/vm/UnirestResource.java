package com.quenti.smarttestui.web.rest.vm;


import com.codahale.metrics.annotation.Timed;

import com.quenti.smarttestui.components.LoginQuentiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
// todo: REMAKE THIS CONTROLLER
public class UnirestResource {

    private final Logger log = LoggerFactory.getLogger(UnirestResource.class);



//    private final  loan;

    public UnirestResource(LoginQuentiComponent loginQuentiComponent) {
//        this.login = requestComponent;

    }

    @PostMapping("/start")
    @Timed
    public Boolean testRequestComponentCall() {
        LoginQuentiComponent loginQuentiComponent = new LoginQuentiComponent();
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("Hopware");
        loginVM.setPassword("C3nf0t3c");
        loginVM.setipAddress("127.0.0.1");
        loginVM.setuserClientId("Hopware");
        log.debug("Starting test request call");
        //DTO SHOULD BE HERE AS A PARAMETER
//        return loginQuentiComponent.init(loginVM);
        return null;
    }


}
