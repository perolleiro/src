package athabasca.ca.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import athabasca.ca.model.Device;
import athabasca.ca.util.DBEmulator;

@Path("/device")
public class DeviceResource
{
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Device getDevice(@PathParam("id") long ID)
	{
		Device device = (Device) DBEmulator.getDBEmulatorInstance().search(ID, DBEmulator.BEAN.DEVICE);
		
		return device;
	}
	

}
