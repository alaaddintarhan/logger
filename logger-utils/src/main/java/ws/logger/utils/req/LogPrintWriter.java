package ws.logger.utils.req;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class LogPrintWriter {

	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private boolean writerFlag = false;
	private boolean sosFlag = false;

	private PrintWriter pw = new PrintWriter(baos);

	private ServletOutputStream sos = new LogServletStream(baos);

	public PrintWriter getWriter() {
		return pw;
	}

	public ServletOutputStream getStream() {
		return sos;
	}

	public byte[] toByteArray() {
		return baos.toByteArray();
	}

	public boolean isWriterFlag() {
		return writerFlag;
	}

	public void setWriterFlag(boolean writerFlag) {
		this.writerFlag = writerFlag;
	}

	public boolean isSosFlag() {
		return sosFlag;
	}

	public void setSosFlag(boolean sosFlag) {
		this.sosFlag = sosFlag;
	}

}
