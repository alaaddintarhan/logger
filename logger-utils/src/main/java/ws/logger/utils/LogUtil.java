package ws.logger.utils;

import ws.logger.utils.model.BaseLog;
import ws.logger.utils.req.LoggerServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogUtil {

	public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static final String DASHED = "---------------------------\n";//+ Charset.defaultCharset();

	private static final List<String> SENSITIVE_HEADERS = Arrays.asList("authorization", "proxy-authorization");

	private LogUtil() {
	}

	public static String getTime() {
		return formatDate(Calendar.getInstance().getTime());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		return f.format(date);
	}

	public static BaseLog init(String appName, LoggerServletRequestWrapper req) throws UnknownHostException {
		BaseLog log = new BaseLog();
		log.setAppName(appName);
		log.setCurrentServiceHost(InetAddress.getLocalHost().getHostName());
		log.setRequestDate(new Date());
		log.appendRequestData(adjustRequestData(req));
		return log;
	}

	public static String adjustRequestData(LoggerServletRequestWrapper req) {
		StringBuilder body = new StringBuilder();
		byte[] data = req.getData();
		if ( data.length > 0 ) {
			body.append(new String(data));
		}
		else {
			body.append(req.getRequestURL().toString());
			String queryString = req.getQueryString();
			if ( queryString != null ) {
				body.append('?').append(queryString).toString();
			}
		}

		StringBuilder reqBuilder = new StringBuilder();
		reqBuilder.append("Outbound Message :\n").append(DASHED)
				.append("Headers :").append(getHeadersInfo(req)).append("\n")
				.append("Body: " + body)
				.append("\n").append(DASHED);
		return reqBuilder.toString();
	}

	private static String getHeadersInfo(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();

			String value = isSensitiveHeader(headerName) ? " ***** " : request.getHeader(headerName);
			builder.append("\n ").append(headerName).append(" ").append(value);
		}

		return builder.toString();
	}

	public static boolean isSensitiveHeader(String headerName) {
		return SENSITIVE_HEADERS.contains(headerName.toLowerCase());
	}

	/*public static String tojSon(TebWsLog log) {
		String formatedDate = formatDate(log.getRequestDate());

		return new JSONObject().put("app_name", log.getAppName()).put("source_host", log.getCallerIpAddres()).put("request_date", formatedDate)
				.put("kvkk_yn", log.getIsKvkk()).put("request_message", log.getRequestData()).put("response_message", log.getResponseData())
				.put("current_server_host", log.getCurrentServiceHost()).put("caller_id", log.getCallerId()).put("caller_purpose", log.getCallerPurpose())
				.put("caller_app", log.getCallerApp()).put("caller_branchno", log.getCallerBranchNo()).toString();
	}
*/
}
