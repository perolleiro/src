package athabasca.ca.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NoContentException extends WebApplicationException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6551490821814886618L;

	public NoContentException(String message)
	{
		//building the exception message
		//this is way is not necessary to map error status code to a status
		super(Response.status(Status.NOT_FOUND).entity(message).build());
	}
	
}
