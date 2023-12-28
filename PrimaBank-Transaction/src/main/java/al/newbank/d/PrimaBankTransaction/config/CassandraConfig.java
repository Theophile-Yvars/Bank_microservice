package al.newbank.d.PrimaBankTransaction.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.oss.driver.api.core.CqlSession;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "al.newbank.d.PrimaBankTransaction.entities")
public class CassandraConfig extends AbstractCassandraConfiguration {

private static final String KEYSPACE = "transaction";


    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"al.newbank.d.PrimaBankTransaction.entities"};
    }


    @Override
    public String getContactPoints() {
        try {
            InetAddress address = InetAddress.getByName("cassandra1");
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            // Handle the exception
            return "localhost"; // Provide a default IP address
        }
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace
                        (KEYSPACE)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }


}
