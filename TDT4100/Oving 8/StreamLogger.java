package patterns.delegation;

import java.io.OutputStream;
import java.io.PrintStream;

public class StreamLogger implements ILogger
{
	private PrintStream stream;
	private String formatString = "%1$-10s: %2$s | Exception: %3$s"; // hadde lyst til å bruke Uppercase, men JUnit liker ikke det
	
	@Override
	public void log(String severity, String message, Exception exception)
	{
		String logMessage = String.format(formatString, severity, message, exception);
		stream.println(logMessage);
		stream.flush();
	}
	
	StreamLogger(OutputStream stream)
	{
		setOutputStream(stream);
	}
	
	private void setOutputStream(OutputStream stream)
	{
		this.stream = new PrintStream(stream);
	}
	
	public void setFormatString(String formatString)
	{
		this.formatString = formatString;
	}
	
	public static void main(String[] args)
	{
		StreamLogger logg = new StreamLogger(System.out);
		logg.log(INFO, "This is a test", null);
		Exception ls = new Exception("Testing");
		logg.log(WARNING, "Fuck off", ls);
		logg.log(ILogger.INFO, "Fuck off", ls);
	}
}
