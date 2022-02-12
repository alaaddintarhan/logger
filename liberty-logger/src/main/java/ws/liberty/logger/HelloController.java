package ws.liberty.logger;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/logger")
@Singleton
public class HelloController {

    @GET
    public String sayHello() {
        return "Hello Liberty Logger";
    }
}
