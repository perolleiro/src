package athabasca.ca.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/helloworld")
public class HelloWorldResource
{
	@GET
	@Produces("text/plain")
	public String showHelloWorld()
	{
		return "Ol� Mundo!";
	}
	
}
