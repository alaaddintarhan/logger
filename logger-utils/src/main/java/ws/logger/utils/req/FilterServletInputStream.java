package ws.logger.utils.req;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

public class FilterServletInputStream extends ServletInputStream {

	ByteArrayInputStream bais;

	public FilterServletInputStream(ByteArrayInputStream bais) {
		this.bais = bais;
	}

	public int available() {
		return bais.available();
	}

	public int read() {
		return bais.read();
	}

	public int read(byte[] buf, int off, int len) {
		return bais.read(buf, off, len);
	}

	@Override public boolean isFinished() {
		return false;
	}

	@Override public boolean isReady() {
		return false;
	}

	@Override public void setReadListener(ReadListener listener) {

	}

}
