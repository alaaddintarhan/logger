package ws.liberty.logger.filter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import ws.liberty.logger.Util;
import ws.liberty.logger.model.LogModel;
import ws.logger.utils.filter.GenericLoggingFilter;
import ws.logger.utils.req.LoggerServletRequestWrapper;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@WebFilter(filterName = "liberty-logging-filter",urlPatterns = "/*")
public class LoggingFilter extends GenericLoggingFilter<LogModel> {

    @ConfigProperty(name = "liberty.app-name")
    private String appName;
    private ConsumerRecord<String, String> cr;

    @Override
    protected String getAppName() {
        return appName;
    }

    @Override
    @Outgoing("systemLoad")
    public Message publish(LogModel log) {
        String msg = Util.jsonb.toJson(log);
        System.out.println(">>>>>>>>>>>>> "+msg);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("systemLoad", null, "myKey", "myValue");
        try {
            producerRecord.headers().add("HeaderKey", "HeaderValue".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Message.of(producerRecord);
    }


    @Incoming("systemLoad")
    public CompletionStage consume(Message message) {

        cr = (ConsumerRecord<String, String>) message.unwrap(ConsumerRecord.class);
        String key = cr.key();
        String value = cr.value();
        String topic = cr.topic();
        int partition = cr.partition();
        long timestamp = cr.timestamp();
        Headers headers = cr.headers();

        System.out.println(">>>>>>>>>>>>> consumer: "+value);

        return CompletableFuture.completedFuture(null);
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
