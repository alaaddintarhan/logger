package ws.logger.utils.resp;

import ws.logger.utils.resp.FilterServletOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class LoggerServletResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream output;

	private PrintWriter writer = null;
	private ServletOutputStream outputStream = null;

	public LoggerServletResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new ByteArrayOutputStream();
	}

	@Override public ServletOutputStream getOutputStream() {
		if ( outputStream == null )
			outputStream = new FilterServletOutputStream(output);
		return outputStream;
	}

	@Override public PrintWriter getWriter() throws UnsupportedEncodingException {
		if ( writer == null ) {
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding())));
		}

		return writer;
	}

	public byte[] getData() {
		return output.toByteArray();
	}

}