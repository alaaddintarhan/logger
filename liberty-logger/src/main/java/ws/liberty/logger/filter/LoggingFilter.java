package ws.liberty.logger.filter;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Message;
import ws.liberty.logger.Util;
import ws.liberty.logger.model.LogModel;
import ws.logger.utils.filter.GenericLoggingFilter;
import ws.logger.utils.req.LoggerServletRequestWrapper;

import javax.inject.Inject;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "liberty-logging-filter",urlPatterns = "/*")
public class LoggingFilter extends GenericLoggingFilter<LogModel> {

    @Inject
    @ConfigProperty(name = "liberty.app-name")
    private String appName;

    @Override
    protected String getAppName() {
        return appName;
    }

    @Override
    protected Message<String> publish(LogModel log) {
        String msg = Util.jsonb.toJson(log);
        System.out.println(">>>>>>>>>>>>> "+msg);
        return Message.of(msg);
    }

    @Override
    protected LogModel customize(LoggerServletRequestWrapper reqWrapper) {

        return new LogModel();
    }

  /*  @Outgoing("systemLoad")
    public Publisher<LogModel> done() {
        // tag::flowableInterval[]
        return Flowable.interval(15, TimeUnit.SECONDS)
                .map((interval -> new SystemLoad(getHostname(),
                        new Double(osMean.getSystemLoadAverage()))));
        // end::flowableInterval[]
    }*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }


}
