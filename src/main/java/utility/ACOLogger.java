package utility;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.*;

public class ACOLogger {
    public static Logger createLogger(String loggerName, String logFileName) {
        Logger logger = Logger.getLogger(loggerName);
        // We don't want console output.
        logger.setUseParentHandlers(false);
        FileHandler fh;
        try {
            fh = new FileHandler(logFileName);
        } catch (IOException e) {
            throw new RuntimeException("Was not able to access the logging file: "+logFileName);
        }
        fh.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm:ss,SSS").withLocale(Locale.GERMANY).withZone(ZoneId.systemDefault());
                return "[" + record.getLevel() + "] " + formatter.format(record.getInstant()) + " - " + record.getMessage() + "\n";
            }
        });
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        return logger;
    }
}
