package patterns.delegation;

import java.util.ArrayList;
import java.util.Arrays;

public class FilteringLogger implements ILogger
{
	private ArrayList<String> severitiesToLog;
	private ILogger logger;
	
	@Override
	public void log(String severity, String message, Exception exception)
	{
		if (isLogging(severity))
		{
			logger.log(severity, message, exception);
		}
	}

	public FilteringLogger(ILogger logger, String... severities)
	{
		this.severitiesToLog = new ArrayList<>(Arrays.asList(severities));
		this.logger = logger;
	}
	
	public boolean isLogging(String severity)
	{
		// returns true if this logger sends logs for the severity
		if (severitiesToLog.contains(severity))
		{
			return true;
		}
		return false;
	}
	
	public void setIsLogging(String severity, boolean value)
	{
		// If we're going to be logging
		if (value)
		{
			// We check if we're already logging
			if (isLogging(severity))
			{
				return;
			}
			// if we weren't already logging we start logging
			severitiesToLog.add(severity);
			return;
		}
		// if we're not going to be logging we check if we're logging
		if (isLogging(severity))
		{
			// If we were logging we remove to flag setting logging on.
			severitiesToLog.remove(severity);
		}
	}
	
	public static void main(String[] args)
	{
		
	}
}
