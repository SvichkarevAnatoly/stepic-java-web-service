package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author a.akbashev
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class ResourcesPageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ResourcesPageServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private TestResource testResource;

    public ResourcesPageServlet(TestResource testResource) {
        this.testResource = testResource;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String filePath = request.getParameter("path");
        logger.info(filePath);

        readFile(filePath);

        final TestResource testResourceFromXML = (TestResource) ReadXMLFileSAX.readXML(filePath);
        assert testResourceFromXML != null;

        testResource.setAge(testResourceFromXML.getAge());
        testResource.setName(testResourceFromXML.getName());
        logger.info(testResource);
    }

    private void readFile(String filePath) {
        try (RandomAccessFile aFile = new RandomAccessFile(filePath, "rw")) {
            FileChannel inChannel = aFile.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(500);
            inChannel.read(buf);
            final String s = new String(buf.array(), "ASCII");
            System.out.printf(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
