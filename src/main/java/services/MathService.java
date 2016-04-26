package main.java.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/math")
public class MathService {
 
  @GET
  @Path("/{number}")
  public String sayHello(@PathParam("number") float number) {
    String output = "Doubled it: " + (number * 2);
    return output;
  }
}