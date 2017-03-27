package patterns.delegation;

import java.util.HashMap;
import java.util.Map;

public class DistributingLogger implements ILogger
{
	private Map<String, ILogger> mappedLoggers = new HashMap<String, ILogger>();
	
	@Override
	public void log(String severity, String message, Exception exception)
	{
		ILogger localLogger = mappedLoggers.get(severity);
		localLogger.log(severity, message, exception);
	}

	public DistributingLogger(ILogger errorLogger, ILogger warningLogger, ILogger infoLogger)
	{
		setLogger(ERROR, errorLogger);
		setLogger(WARNING, warningLogger);
		setLogger(INFO, infoLogger);
	}
	
	public void setLogger(String severity, ILogger logger)
	{
		mappedLoggers.put(severity, logger);
	}
	
	public static void main(String[] args)
	{
		ILogger syserrLogger = new StreamLogger(System.err);
		ILogger sysoutLogger = new StreamLogger(System.out);
		DistributingLogger logger = new DistributingLogger(syserrLogger, syserrLogger, sysoutLogger);
		logger.log(ILogger.ERROR, "Denne meldingen er alvorlig og skrives til System.err", null);
		logger.log(ILogger.WARNING, "Denne meldingen er en advarsel og skrives til System.err", null);
		logger.log(ILogger.INFO, "Denne meldingen er til informasjon og skrives til System.out", null);
		logger.setLogger(ILogger.WARNING, sysoutLogger);
		logger.log(ILogger.WARNING, "Denne meldingen er en advarsel, men nå skrives den til System.out", null);
	}
}
