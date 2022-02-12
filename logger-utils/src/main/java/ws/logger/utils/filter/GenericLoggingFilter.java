package ws.logger.utils.filter;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import ws.logger.utils.LogUtil;
import ws.logger.utils.TebWsLogContext;
import ws.logger.utils.model.BaseLog;
import ws.logger.utils.req.LoggerServletRequestWrapper;
import ws.logger.utils.resp.LoggerServletResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Logger;

public abstract class GenericLoggingFilter<T extends BaseLog> implements Filter {

    private static final Logger logger = Logger.getLogger(GenericLoggingFilter.class.getName());

    abstract protected String getAppName();

    @Outgoing("systemLoad")
    protected abstract Message<String> publish(T t);

    protected abstract T  customize(LoggerServletRequestWrapper reqWrapper) ;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        try {
            final LoggerServletRequestWrapper reqWrapper = new LoggerServletRequestWrapper((HttpServletRequest) req);
            LoggerServletResponseWrapper resWrapper = new LoggerServletResponseWrapper((HttpServletResponse) res);

            T tLog = customize(reqWrapper);
            tLog.setAppName(getAppName());
            tLog.setCurrentServiceHost(InetAddress.getLocalHost().getHostName());
            tLog.setRequestDate(new Date());
            tLog.appendRequestData(LogUtil.adjustRequestData(reqWrapper));

            TebWsLogContext.set(tLog);

            //handle the request
            chain.doFilter(reqWrapper, resWrapper);

            byte[] data = resWrapper.getData();
            // write and close the stream
            OutputStream out = res.getOutputStream();
            out.write(data);
            out.close();

            tLog.setResponseData(new String(data, resWrapper.getCharacterEncoding()));
            resWrapper.getOutputStream().close();

            publish(tLog);
        } catch (Exception e) {
            e.printStackTrace();
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}