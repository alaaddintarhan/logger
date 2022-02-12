package ws.logger.utils.req;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LogServletStream extends ServletOutputStream {

	private ByteArrayOutputStream baos;

	public LogServletStream(ByteArrayOutputStream baos) {
		super();
		this.baos = baos;

	}

	public void write(int param) throws IOException {
		baos.write(param);
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener listener) {

	}
}
