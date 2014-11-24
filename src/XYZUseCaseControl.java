import javax.sql.DataSource;
import java.sql.*;

/**
 * Controller of the XYZ use case
 */
public class XYZUseCaseControl {

    AuthenticationService auth = new AuthenticationService();

    public void doIt() throws AuthenticationService.AuthenticationFailedException {
        // ... credentials from user
        String username = "danbj";
        String password = "Vi€e Pre$ident";

        auth.signin(username, password);
        // .... some more stuff
    }
}

class AuthenticationService {
    DataSource dataSource = null;

    public SecToken signin(String username, String password)
            throws AuthenticationFailedException {
        String sql =
                String.format("SELECT id FROM iwauser " +
                        "WHERE username = '%s' AND password='%s'",
                        username, password);

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return new SecToken();
            } else {
                throw new AuthenticationFailedException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    class SecToken {
    }

    class AuthenticationFailedException extends Exception {
    }
}
