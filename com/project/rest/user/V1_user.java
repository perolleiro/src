package com.project.rest.user;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;

import com.project.dao.*;

@Path("/v1/user")
public class V1_user {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle () {
		return "<p>Java Web Service</p> ";
	}
	
	@Path("/select")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response select(@QueryParam("user_id") String user, @QueryParam("password") String pass) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		Response r = null;
		String[] userArray = new String[7];
		boolean isTrue = false;
		
		try {
			if ((user == null) || (pass == null)) {
				return Response.status(400).entity("Please specify user id and/or password.").build();
			}
			
			conn = postgreSQL.postgreSQLConn();
			query = conn.prepareStatement("SELECT user_id, first_name, last_name," +
                    " birthday, oi, telephone, email" +
                    " FROM user_profiles" +
                    " WHERE user_id = ? ");
				//  " AND pass = ? ");
			query.setString(1, user);
		//  query.setString(2, pass);
			ResultSet results = query.executeQuery();
			
			while (results.next()) {
				userArray[0] = results.getString("user_id");
				userArray[1] = results.getString("first_name");
				userArray[2] = results.getString("last_name");
				userArray[3] = results.getString("birthday");
				userArray[4] = results.getString("oi");
				userArray[5] = results.getString("telephone");
				userArray[6] = results.getString("email");
            }
			
			query.close();
			
			for (int i = 0; i < userArray.length; i++) {
				if (userArray[i] == null) {
					isTrue = false;
					break;
				} else
					isTrue = true;
			}
			
			r = Response.ok(userArray).build();
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) conn.close();
		}
		
		if (isTrue == true) {
	  		Response.status(200).entity("Success!").build();
		} else
			Response.status(400).entity("Failed.").build();

		return r;
	}
}
