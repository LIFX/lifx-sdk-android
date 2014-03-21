package lifx.java.android.entities;

public class LFXTypes
{
	public enum LFXDeviceReachability
	{
		REACHABLE,
		UNREACHABLE,
	};
	
	public enum LFXFuzzyPowerState
	{
		OFF,
		ON,
		MIXED,
	};
	
	public enum LFXPowerState
	{
		ON,
		OFF,
	};
	
	public static LFXFuzzyPowerState getLFXFuzzyPowerStateFromPowerState( LFXPowerState powerState)
	{
		switch( powerState)
		{
			case OFF:	
				return LFXFuzzyPowerState.OFF;
			case ON:	
				return LFXFuzzyPowerState.ON;
		}
		
		return LFXFuzzyPowerState.OFF;
	}
}
