package task.recipesbook.recipesbookservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * A component that loads demo data into the application database when the application starts up.
 */
@Component
public class DemoDataLoader implements ApplicationRunner {

    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "user";
    private static final String DB_SQL_SCRIPT = "classpath:db/data.sql";

    /**
     * The resource loader to use for loading demo data.
     */
    @Autowired
    private final ResourceLoader resourceLoader;

    /**
     * Constructs a new DemoDataLoader with the given ResourceLoader.
     *
     * @param resourceLoader The ResourceLoader to use for loading demo data.
     */
    public DemoDataLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Loads demo data into the application database by executing a SQL script from file.
     *
     * @param args The ApplicationArguments passed to the application.
     * @throws Exception if an error occurs while loading the demo data.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource resource = resourceLoader.getResource(DB_SQL_SCRIPT);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(resource.getFile().toPath()));
            statement.execute(sql);
        }
    }
}
