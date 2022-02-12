package com.demo.demo.client;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/client/service")
public class ServiceController {

    @Inject
    @ConfigProperty(name = "injected.value")
    private String injectedValue;

    @Inject
    @ConfigProperty(name = "liberty.app-name")
    private String appName;



    @GET
    @Path("/{parameter}")
    public String doSomething(@PathParam("parameter") String parameter) {
        return String.format("Processed parameter value '%s', '%s' , - AppName :  %s", parameter,injectedValue,appName);
    }
}
