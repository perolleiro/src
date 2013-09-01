package com.project.rest.user;

import java.sql.*;
import java.util.Hashtable;

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
	public static Boolean update(User user, Hashtable<Object, Integer> keys) throws Exception {
		Connection conn = null;
		PreparedStatement query = null;
        boolean isTrue = false;
            
        try {
            	
        	conn = postgreSQL.postgreSQLConn();
        	// Check again...
        	if (keys.get(user.getFirstName()) == 1) {
        		query = conn.prepareStatement("UPDATE user_profiles"
        				+ " SET first_name = " + user.getFirstName()
                        + " WHERE user_id = ? ");
        		query.setObject(1, user.getUserId());
        		query.executeUpdate();
                isTrue = true;
                query.close();
        	} else if (user.getLastName() != null) {
        		query = conn.prepareStatement("UPDATE user_profiles"
        				+ " SET last_name = " + user.getLastName()
                        + " WHERE user_id = ? ");
        		query.setObject(1, user.getUserId());
        		query.executeUpdate();
                isTrue = true;
                query.close();
        	} else if (user.getBirthday() != null) {
        		query = conn.prepareStatement("UPDATE user_profiles"
        				+ " SET birthday = " + user.getBirthday()
                        + " WHERE user_id = ? ");
        		query.setObject(1, user.getUserId());
        		query.executeUpdate();
        		isTrue = true;
        		query.close();
        	} else if (user.getOtherPreferences() != null) {
        		query = conn.prepareStatement("UPDATE user_profiles"
        				+ " SET other_preferences = " + user.getOtherPreferences()
                        + " WHERE user_id = ? ");
        		query.setObject(1, user.getUserId());
        		query.executeUpdate();
                isTrue = true;
                query.close();
        	} else if (user.getTelephones() != null) {
        		String[] telephones = user.getTelephones();
        		for (int i = 0; i < telephones.length; i++) {
        			query = conn.prepareStatement("UPDATE contact_info"
            				+ " SET telephone = " + telephones[i]
                            + " WHERE user_id = ? ");
        			query.setObject(1, user.getUserId());
            		query.executeUpdate();
            		query.close();
        		}
        		isTrue = true;
        	} else if (user.getEmails() != null) {
        		String[] emails = user.getEmails();
        		for (int i = 0; i < emails.length; i++) {
        			query = conn.prepareStatement("UPDATE contact_info"
            				+ " SET telephone = " + emails[i]
                            + " WHERE user_id = ? ");
        			query.setObject(1, user.getUserId());
            		query.executeUpdate();
            		query.close();
        		}
        		isTrue = true;
        	} 
        	
        } catch (SQLException sqlError) {
        		sqlError.printStackTrace();
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
