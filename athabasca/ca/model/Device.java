package athabasca.ca.model;

public class Device
{
	private long deviceId;
	private Feature[] availableFeatures;
	
	public Device(long deviceId)
	{
		this.setDeviceId(deviceId);
	}
	
	public Device(long deviceId, Feature[] availableFeatures)
	{
		this.setDeviceId(deviceId);
		
		this.availableFeatures = new Feature[availableFeatures.length];
		for(int i = 0; i < availableFeatures.length; ++i)
		{
			this.availableFeatures[i] = availableFeatures[i];
		}
	}

	public long getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(long deviceId)
	{
		this.deviceId = deviceId;
	}
	
	public Feature[] getAvailableFeatures()
	{
		return this.availableFeatures;
	}
	
	public void setAvailableFeatures(Feature[] availableFeatures)
	{
		this.availableFeatures = new Feature[availableFeatures.length];
		for(int i = 0; i < availableFeatures.length; ++i)
		{
			this.availableFeatures[i] = availableFeatures[i];
		}
	}
	
}
