package testutil;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.ArrayList;
import java.util.List;

public class LogTestUtil implements AutoCloseable {
    private final List<String> logMessages = new ArrayList<>();
    private final Appender appender;
    private final Logger rootLogger;

    public LogTestUtil(Class<?> clazz) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        rootLogger = ctx.getRootLogger();
        rootLogger.setLevel(Level.INFO);

        String appenderName = "TestAppender-" + clazz.getSimpleName() + "-" + System.nanoTime();
        appender = new AbstractAppender(appenderName, null, PatternLayout.createDefaultLayout(), false, null) {
            @Override
            public void append(LogEvent event) {
                logMessages.add(event.getMessage().getFormattedMessage());
            }
        };
        appender.start();
        rootLogger.addAppender(appender);
        
        // Test log to verify appender
        rootLogger.info("LogTestUtil initialized for {} with appender {}", clazz.getName(), appenderName);
        if (logMessages.isEmpty()) {
            System.err.println("LogTestUtil: No logs captured after initialization for " + clazz.getName());
        } else {
            System.err.println("LogTestUtil: Captured initialization log: " + logMessages);
        }
    }

    public List<String> getMessagesContaining(String keyword) {
        List<String> result = new ArrayList<>();
        for (String message : logMessages) {
            if (message.contains(keyword)) {
                result.add(message);
            }
        }
        return result;
    }

    public void clear() {
        logMessages.clear();
    }

    public List<String> getAllMessages() {
        return new ArrayList<>(logMessages);
    }

    @Override
    public void close() {
        if (appender != null && rootLogger != null) {
            rootLogger.removeAppender(appender);
            appender.stop();
        }
    }
}