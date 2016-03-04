package servlets;

import accountServer.AccountServer;
import accountServer.AccountServerI;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author a.akbashev
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class HomePageServletTest {
    private AccountServerI getMockedAccount(int i) {
        AccountServerI accountServer = mock(AccountServer.class);
        when(accountServer.getUsersLimit()).thenReturn(i);
        return accountServer;
    }

    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);

        final PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        return response;
    }

    private HttpServletRequest getMockedRequest(String url) {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn(url);

        return request;
    }

    @Test
    public void testRemove() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(HomePageServlet.PAGE_URL);
        AccountServerI accountServer = getMockedAccount(5);
        when(request.getParameter("remove")).thenReturn("");

        HomePageServlet homePage = new HomePageServlet(accountServer);

        homePage.doGet(request, response);

        assertEquals("5", stringWriter.toString().trim());
        verify(accountServer, times(0)).removeUser();
    }
}
