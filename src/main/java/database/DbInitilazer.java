package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbInitilazer {
    private final String driver;
    private final String url;

    public DbInitilazer(String driver, String url) {
        this.driver = driver;
        this.url = url;
    }

    public void init() {
        try{
            //načte driver
            Class.forName(driver);
            //vytvoří connection
            Connection connection = DriverManager.getConnection(url);
            //vytvoří statement
            Statement statement = connection.createStatement();

            String slqDropMessagesTabble = "DROP TABLE ChatMessages";
            //statment.executeUpdate(sqlDropTable);
            String sqlCreateMessageTable = "CREATE TABLE ChatMessages " + "(" + "id INT NOT NULL GENERATED ALWAYS AS IDENTITY " +
                    "CONSTRAINT ChatMessages_PK PRIMARY KEY, author VARCHAR(50), text VARCHAR(1000), created timestamp" + ")";
            statement.executeUpdate(sqlCreateMessageTable);
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
