package com.project.rest.user;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.project.dao.*;
import athabasca.ca.model.User;

public class V2_user {
	// Have to create a separate class to handle errors
	public static boolean create(User newUser) throws Exception {
		Connection conn = null;
		PreparedStatement query = null;
        Statement query2 = null;
        boolean isTrue = false;

			try {
				conn = postgreSQL.postgreSQLConn();
			
				long user_id = newUser.getUserId();
				String first_name = newUser.getFirstName();
				String last_name = newUser.getLastName();
				String birthday = newUser.getBirthday();
				String[] telephone = newUser.getTelephones();
				String[] email = newUser.getEmails();
				String otherPref = newUser.getOtherPreferences();
				// Need a new table for contact info???
				query = conn.prepareStatement("INSERT INTO user_profiles " + 
						"VALUES ('" + user_id + "', '" + first_name + "', '" +
	                    	last_name + "', '" + birthday + "', '" +
	                    	otherPref +
	                    	"')");
				
				query.executeUpdate();
				// For multiple telephones and e-mails
				// A new table called contact_info might be required
				// I don't know if this will work prior to testing; have to test it
				for (int i = 0; (i < telephone.length) || (i < email.length); i++) {
					query2.executeUpdate("INSERT INTO contact_info " +
							"VALUES ('" + user_id + "', '" + telephone[i] + "', '" + email[i] +
							"')");
				}
				isTrue = true;
				query.close();
				query2.close();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.getMessage();
			} catch (NullPointerException e) {
				e.getMessage();
			} catch (SQLException sqlError) {
				sqlError.getMessage();
			} catch (Exception e) {
				e.getMessage();
				e.printStackTrace();
			} finally {
				if (conn != null) conn.close();
			}
			// Not sure about this
			// boolean?
			return isTrue;

	}
	// Need to handle errors
	public static User select(long id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		boolean isTrue = false;
		User setUser = new User(id);

		try {	
				conn = postgreSQL.postgreSQLConn();
				query = conn.prepareStatement("SELECT user_profiles.user_id, user_profiles.first_name, user_profiles.last_name," +
						" user_profiles.birthday, contact_info.telephone, contact_info.email" +
						" FROM user_profiles" +
						" INNER JOIN contact_info " +
						" ON user_profiles.user_id = contact_info.user_id " +
						" WHERE user_id = ? ");
				query.setObject(1, id);
				ResultSet results = query.executeQuery();
				String[] telephone = new String[20];
				String[] email = new String[20];
			
				while (results.next()) {
					int i = 0;
					setUser.setUserId(results.getInt("user_id"));
					setUser.setFirstName(results.getString("first_name"));
					setUser.setLastName(results.getString("last_name"));
					setUser.setBirthday(results.getString("birthday"));
					setUser.setOtherPreferences(results.getString("other_preferences"));
					telephone[i] = results.getString("telephone");
					email[i] = results.getString("email");
					i++;	
				}
				setUser.setTelephones(telephone);
				setUser.setEmails(email);
				
				if (setUser != null)
					isTrue = true;
			
				query.close();
		} catch (NullPointerException e) {
			e.getMessage();
		} catch (SQLException sqlError) {
			sqlError.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) conn.close();
		}
		
		if (isTrue == true) {
	  		return setUser;
		} else {
			setUser = null;
			return setUser;
		}
	}
	// I think we should use a hash table to map keys instead of this way???
	// A Hash table will hold the user id's and corresponding passwords
	// Need to handle errors
	public static Boolean update(long id, Hashtable<Object, Object> args) throws Exception {
		Connection conn = null;
		PreparedStatement query = null;
        boolean isTrue = false;
            
        try {
        	String argument = null;
            
            String sql = ("UPDATE user_profiles"
    				+ " SET " + argument + " = ?"
                    + " WHERE user_id = " + args.get("user_id").toString());
            
            int i = 0;
            Enumeration<Object> e = args.keys();
            String key;
            while (i < args.size()) {
            	key = (String) e.nextElement();
            	switch (key) {
            		case "user_id":
            		{
            			argument = "user_id";
                    	query = conn.prepareStatement(sql);
                    	query.setObject(1, args.get("user_id"));
                    	query.executeUpdate();
                    	query.close();
                    	isTrue = true;
            		}
            		case "first_name":
            		{
            			argument = "first_name";
                    	query = conn.prepareStatement(sql);
                    	query.setObject(1, args.get("first_name"));
                    	query.executeUpdate();
                    	query.close();
                    	isTrue = true;
            		}
            		default:
            	}
            	
            	//if (args.containsKey("first_name")) {
                	//argument = "first_name";
                	//query = conn.prepareStatement(sql);
                	//query.setObject(1, args.get("first_name"));
                	//query.executeUpdate();
                	//query.close();
                	//isTrue = true;
                //} else if (args.containsKey("last_name")) {
                	//argument = "last_name";
                	//query = conn.prepareStatement(sql);
                	//query.setObject(1, args.get("last_name"));
                	//query.executeUpdate();
                	//query.close();
                	//isTrue = true;
                
            	// Clear argument
            	argument = "";
            	i++;
            }
        	
        } catch (SQLException sqlError) {
        	sqlError.getMessage();
        } catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (conn != null) conn.close();
    	}
        return isTrue;
    }
	// Need to handle errors
	public static boolean delete(long id) throws Exception {
		Connection conn = null;
		PreparedStatement query = null;
        boolean isTrue = false;
        
        try {
        	conn = postgreSQL.postgreSQLConn();
            	
        	query = conn.prepareStatement("DELETE FROM user_profiles"
        			+ " WHERE user_id = ? ");
            query.setObject(1, id);
        	query.executeUpdate();
            query.close();
            isTrue = true;
        	
        } catch (NullPointerException e) {
        	e.getMessage();
        } catch (SQLException sqlError) {
        	sqlError.getMessage();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (conn != null) conn.close();
        }
        return isTrue;
	}
}
