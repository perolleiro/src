package com.project.rest.user;

import java.io.FileOutputStream;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.Document;
import org.jdom.output.Format;
import java.io.OutputStream;
import java.sql.*;

import com.project.dao.*;

public class XMLCreator {
	public boolean create (long user_id) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		boolean isTrue = false;
		
		long di;
		long st;
		long et;
		String name;
		long usage;
		
		try {
				conn = postgreSQL.postgreSQLConn();
				query = conn.prepareStatement("SELECT device_usage.device_id," +
						" device_usage.start_time, device_usage.end_time" +
						" selected_group.name, selected_group.usage" +
						" FROM device_usage" +
						" WHERE user_id = ? " +
						" INNER JOIN selected_feature" +
						" ON device_usage.device_id = selected_feature.device_id");
				query.setObject(1, user_id);
				ResultSet results = query.executeQuery();
				while (results.next()) {
					di = results.getLong("device_id");
					st = results.getLong("start_time");
					et = results.getLong("end_time");
					name = results.getString("name");
					usage = results.getLong("usage");
				}
			
				query.close();
			
		} catch (SQLException sqlError) {
        	sqlError.getMessage();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (conn != null) conn.close();
        }
		
		return isTrue;		
	}
	
	public void output (String file, Element root) throws Exception {
        if (root != null) {
            OutputStream out = new FileOutputStream(file);
            Document doc = new Document((Element) root.clone());
            Format f = Format.getPrettyFormat();
            f.setEncoding("ISO-8859-1");
            f.setIndent("\t");
            f.setOmitDeclaration(false);
            XMLOutputter xml = new XMLOutputter(f);
            xml.output(doc, out);
            out.close();
        }
    }
}
