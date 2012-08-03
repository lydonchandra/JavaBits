package routingOsm.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * User: lydonchandra
 * Date: 1/08/12
 * Time: 12:17 AM
 */
public final class Log {
    public static final int LEVEL_NULL = 0;
    public static final int LEVEL_LOG = 1;
    public static final int LEVEL_DATA = 2;
    public static final int LEVEL_BUGFIX = 3;
    public static final int LEVEL_DEBUG = 4;
    public static final int LEVEL_PROGRESS = 5;
    public static final int LEVEL_WARN = 6;
    public static final int LEVEL_INFO = 7;
    public static final int LEVEL_ERROR = 8;
    public static final int LEVEL_FATAL = 9;

    public static final String[] LEVEL_PREFIX_ARR = {
            "       ",
            "LOG    ",
            "DATA   ",
            "BUGFIX ",
            "DEBUG  ",
            "WARN   ",
            "INFO   ",
            "ERROR  ",
            "FATAL  "
    };

    private int logLevel;

    private boolean logThreadId;

    private List<LogWriter> logWriterList = new ArrayList();

    public Log() {
        this.logLevel = 7;
    }

    public Log( int logLevel ) {
        this.logLevel = logLevel;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public Log setLogThreadId( boolean logThreadId ) {
        this.logThreadId = logThreadId;
        return this;
    }

    public boolean isLogThreadId() {
        return this.logThreadId;
    }

    public Log addLogWriter(LogWriter logWriter) {
        this.logWriterList.add(logWriter);
        return this;
    }

    public void removeLogWriter(LogWriter logWriter) {
        this.logWriterList.remove(logWriter);
    }

    public void removeAllLogWriters() {
        for( LogWriter logWriter : this.logWriterList ) {
            logWriter.close();
        }
        this.logWriterList.clear();
    }

    public final void data  (String msg) { log(msg,2); }
    public final void bugfix(String msg) { log(msg, 3); }
    public final void debug (String msg) { log(msg,4); }
    public final void progress(String msg) { log(msg, 5);}
    public final void warn  (String msg) { log(msg,6); }
    public final void info  (String msg) { log(msg, 7); }
    public final void error (String msg) { log(msg,8); }
    public final void fatal (String msg) { log(msg,9); }

    public final void error(Throwable t) { error( translateError(t)); }

    public final boolean isEnabled( int logLevel ) {
        return logLevel >= this.logLevel;
    }

    public final void log(String message, int logLevel) {
        if( logLevel >= this.logLevel ) {
            for( LogWriter lw : this.logWriterList ) {
                String msg = message;
                if( this.logThreadId ) {
                    msg = "@T" + Thread.currentThread().getId() + " " + msg;
                }
                lw.log( msg, logLevel );
            }
        }
    }

    public final void close() {
        LogWriter lw;
        for( Iterator i = this.logWriterList.iterator()
             ; i.hasNext(); lw.close() ) {

            lw = (LogWriter)i.next();
        }
    }

    public static String translateError(Throwable t) {
        String msg = t.getMessage();
        if( null == msg
            && t.getCause() != null ) {

            msg = t.getCause().getMessage();
        }

        if( null == msg )
            msg = "Unknown";

        String errString = "Exception at "
                            + new Date().toString()
                            + "\nMessage: " + msg
                            + "\nClass: " + t.getClass().getName() + "\nStacktrace";

        int lines = t.getStackTrace().length;

        // only output first 10 lines in stacktrace
        int more = lines;
        for( int i = 0
             ; (i<10) && (i<lines)
             ; more-- ) {

            errString = errString + "\n" + t.getStackTrace()[i].toString();
            i++;
        }

        if( more > 0)
            errString = errString + "\n" + more + " more...";

        return errString;
    }




}
