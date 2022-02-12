package ws.logger.utils.req;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class LoggerServletRequestWrapper extends HttpServletRequestWrapper {

    private ServletInputStream inputStream;
    private byte[] data;

    public LoggerServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        try {
            InputStream is = request.getInputStream();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            if (is != null) {
                byte buf[] = new byte[1024];
                int letti;
                while ((letti = is.read(buf)) > 0) {
                    output.write(buf, 0, letti);
                }

            }
            data = output.toByteArray();
        } catch (IOException e) {
            throw new IOException("[ LoggerServletRequestWrapper.getInputStream ERROR ]" + getStackTrace(e));
        }

    }

    public ServletInputStream getInputStream() throws IOException {
        try {
            inputStream = new FilterServletInputStream(new ByteArrayInputStream(data));
        } catch (Exception e) {
            throw new IOException("[ LoggerServletRequestWrapper.getInputStream ERROR ]" + getStackTrace(e));
        }
        return inputStream;
    }

    public byte[] getData() {
        return data;
    }

    public String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}