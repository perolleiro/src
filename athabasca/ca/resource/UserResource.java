package athabasca.ca.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import athabasca.ca.model.User;
import athabasca.ca.util.DBEmulator;
import athabasca.ca.util.DBEmulator.BEAN;

@Path("/user")
public class UserResource
{
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") long ID)
	{
		User user = (User) DBEmulator.getDBEmulatorInstance().search(ID, BEAN.USER);
		
		if(user == null)
		{
			throw new NoContentException("Client not found!");
		}
		
		return user;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insert(User user)
	{
		return DBEmulator.getDBEmulatorInstance().insert( (Object) user);
	}
}
