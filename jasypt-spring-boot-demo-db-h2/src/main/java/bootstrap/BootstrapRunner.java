package bootstrap;

import demo.Person;
import demo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Performs idempotent database bootstrap under the 'bootstrap' profile:
 * - Creates user ULI with password 'chupacabras' if absent
 * - Grants privileges
 * - Inserts sample Person rows if missing
 */
@Component
@ConditionalOnProperty(name = "demo.bootstrap", havingValue = "true")
public class BootstrapRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapRunner.class);

    @Autowired
    private DataSource dataSource; // connected as 'sa'

    @Autowired
    private PersonRepository repo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("H2 bootstrap start");
        try (Connection c = dataSource.getConnection(); Statement st = c.createStatement()) {
            boolean userExists;
            try (ResultSet rs = st.executeQuery("SELECT 1 FROM INFORMATION_SCHEMA.USERS WHERE USER_NAME='ULI'")) {
                userExists = rs.next();
            }
            if (!userExists) {
                LOG.info("Creating user ULI");
                st.execute("CREATE USER IF NOT EXISTS ULI PASSWORD 'chupacabras'");
                st.execute("GRANT ALL ON SCHEMA PUBLIC TO ULI");
            } else {
                LOG.info("User ULI already exists");
            }
        }
        if (!repo.findByLastName("Bocchio").isEmpty()) {
            LOG.info("Sample data already present");
        } else {
            LOG.info("Inserting sample persons");
            repo.save(new Person("Uli", "Bocchio"));
            repo.save(new Person("Luca", "Bocchio"));
            repo.save(new Person("Fiorella", "Bocchio"));
        }
        LOG.info("H2 bootstrap complete");
    }
}
