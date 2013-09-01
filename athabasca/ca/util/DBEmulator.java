package athabasca.ca.util;

import java.util.ArrayList;

import athabasca.ca.model.Device;
import athabasca.ca.model.Feature;
import athabasca.ca.model.User;

import com.project.rest.user.*;

public class DBEmulator
{
	public static enum BEAN { USER, DEVICE };
	
	private static DBEmulator instance;
	
	private ArrayList<User> userList;
	private long userID;
	
	private ArrayList<Device> deviceList;
	private long deviceID;
	
	private DBEmulator()
	{
		userID = 1;
		userList = new ArrayList<User>();
		User userSample = new User(userID);
		
		//userSample.setUserId(userID);
		userSample.setFirstName("John");
		userSample.setLastName("Nash");
		userSample.setBirthday("01/02/1789");
		userSample.setTelephones(new String[]{"123-1234-5678", ""});
		userSample.setEmails(null);
		userSample.setOtherPreferences("Other preferences for this new student.");
		
		this.insert((Object) userSample);
		
		/*
		deviceID = 1;
		deviceList = new ArrayList<Device>();
		Device deviceSample = new Device(deviceID);
		Feature[] features = new Feature[3];
		features[0].setName("featureTest0");
		features[1].setName("featureTest1");
		features[2].setName("featureTest2");
		deviceSample.setAvailableFeatures(features);
		this.insert((Object) deviceSample);*/
		
	}
	
	public static DBEmulator getDBEmulatorInstance()
	{
		if(instance == null)
		{
			instance = new DBEmulator();
		}
		
		return instance;
	}
	
	public ArrayList<User> getUserList()
	{
		return userList;
	}
	
	public String insert(Object object)
	{
		if(object instanceof User)
		{
			User user = (User) object;
			user.setUserId(userID++);
			userList.add(user);
			
			//V2_user addUser = new V2_user();
			//addUser.create(user);
			
			return "SUCCESS - insert " + user.toString();
		}
		
		if(object instanceof Device)
		{
			Device device = (Device) object;
			device.setDeviceId(deviceID++);
			deviceList.add(device);
			
			return "SUCCESS - insert " + device.toString();
		}
		
		return "FAILURE - insert " + object.toString();
	}
	
	public Object search(long ID, BEAN objectType)
	{
		Object result = null;
		
		switch(objectType)
		{
			case USER:
			{
				for(User user : userList)
				{
					if(user.getUserId() == ID)
					{
						result = (Object) user;
						break;
					}
				}
			}
			break;
			
			case DEVICE:
			{
				for(Device device : deviceList)
				{
					if(device.getDeviceId() == ID)
					{
						result = (Object) device;
						break;
					}
				}
			}
			break;
			
			default:
			{
				result = null;
			}
		}
		
		return result;
	}
	
	public String delete(long ID, BEAN objectType)
	{
		String result = null;
		switch(objectType)
		{
			case USER:
			{
				for(User user : userList)
				{
					if(user.getUserId() == ID)
					{
						userList.remove(user);
						result =  "SUCCESS - delete user";
						break;
					}
				}
			}
			break;
			
			default:
			{
				result = "FAILURE - No object of type "+ objectType + " found";
			}
		}
		
		return result;
	}
}
