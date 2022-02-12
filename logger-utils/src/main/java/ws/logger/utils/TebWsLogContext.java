package ws.logger.utils;

import ws.logger.utils.model.BaseLog;

public class TebWsLogContext {

    private static final ThreadLocal logDataThreadLocal = new ThreadLocal();

    private TebWsLogContext() {
    }

    public static Object get() {
        return logDataThreadLocal.get();
    }

    private static BaseLog getLog() {
        return (BaseLog) get();
    }

    public static void set(Object log) {
        logDataThreadLocal.remove();
        logDataThreadLocal.set(log);
    }

    public static void appendRequestData(String data) {
        getLog().appendRequestData(data);
    }

    public static void setResponse(String data) {
        getLog().setResponseData(data);
    }

}
