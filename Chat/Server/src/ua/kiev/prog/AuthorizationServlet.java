package ua.kiev.prog;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationServlet extends HttpServlet {
    HashMap<String ,String> users = new HashMap<String, String>(){{put("yulia", "123"); put("user", "123");}};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (users.containsKey(login)) {
            String pass = users.get(login);
            if (pass.equals(password)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

}
