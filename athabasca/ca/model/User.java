package athabasca.ca.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User
{
	private long userId;
	private String password;
	private String firstName;
	private String lastName;
	private String birthday;
	private String[] telephones;
	private String[] emails;
	private String otherPreferences;
	
	public User() {
	}
	
	public User(long userId)
	{
		this.userId = userId;
	}
	
	public User(long userId, String firstName,
			String lastName, String birthday, String[] telephones,
			String[] emails, String otherPreferences)
	{
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		
		this.emails = new String[emails.length];
		for(int i = 0; i < emails.length; ++i)
		{
			this.emails[i] = emails[i];
		}
		
		this.telephones = new String[telephones.length];
		for(int i = 0; i < telephones.length; ++i)
		{
			this.telephones[i] = telephones[i];
		}
		
		this.otherPreferences = otherPreferences;
	}
	
	//setters and getters
	public long getUserId()
	{
		return userId;
	}
	
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getBirthday()
	{
		return birthday;
	}
	
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	
	public String[] getTelephones()
	{
		return telephones;
	}
	
	public void setTelephones(String[] telephones)
	{
		this.telephones = telephones;
	}
	
	public String[] getEmails()
	{
		return emails;
	}
	
	public void setEmails(String[] emails)
	{
		this.emails = emails;
	}
	
	public String getOtherPreferences()
	{
		return otherPreferences;
	}
	
	public void setOtherPreferences(String otherPreferences)
	{
		this.otherPreferences = otherPreferences;
	}
	
	
}

