import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LogFormat extends Formatter
{
    // ANSI escape code
  // Anfang Attribute
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
  // Ende Attribute

    // Here you can configure the format of the output, and
    // it's color by using the ANSI escape codes defined above.

    // format is called for every console log message
  // Anfang Methoden
    @Override
    public String format(LogRecord record)
    {
        // This example will print date/time, class, and log level in yellow,
        // followed by the log message and it's parameters in white .
        StringBuilder builder = new StringBuilder();
        builder.append(ANSI_GREEN);

        builder.append("[");
        builder.append(calcDate(record.getMillis()));
        builder.append("]");

        builder.append(ANSI_CYAN);
        builder.append(" [");
        builder.append(padLeftSpaces(record.getSourceClassName(), 15));
        builder.append("]");
        
        builder.append(ANSI_RESET);
        builder.append(" [");
        
        if (record.getLevel() == Level.SEVERE) {
            builder.append(ANSI_RED);
        } else if (record.getLevel() == Level.WARNING) {
            builder.append(ANSI_YELLOW);
        } else if (record.getLevel() == Level.INFO) {
            builder.append(ANSI_WHITE);
        } else {
            builder.append(ANSI_GREEN);
        } 

        builder.append(padLeftSpaces(record.getLevel().getName(), 10));
        builder.append(ANSI_RESET);
        builder.append("/");
        builder.append(ANSI_PURPLE);
        builder.append(padLeftSpaces(Thread.currentThread().getName(), 10));
        builder.append(ANSI_RESET);
        builder.append("]");

        builder.append(ANSI_WHITE);
        builder.append(" - ");
        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null)
        {
            builder.append("\t");
            for (int i = 0; i < params.length; i++)
            {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        builder.append(ANSI_RESET);
        builder.append("\n");
        return builder.toString();
    }

    public String padLeftSpaces(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append(' ');
        }
        sb.append(inputString);
    
        return sb.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getSimpleName());
        logger.setUseParentHandlers(false);
        
        ConsoleHandler handler = new ConsoleHandler();
        
        Formatter formatter = new LogFormat();
        handler.setFormatter(formatter);        
        
        logger.addHandler(handler);

        return logger;
    }
  // Ende Methoden
}