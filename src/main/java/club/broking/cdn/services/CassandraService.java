package club.broking.cdn.services;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.MappingManager;

public class CassandraService {

    private static CassandraService instance;

    private Cluster cluster;
    private Session session;
    private MappingManager mappingManager;

    private CassandraService() {}

    public static CassandraService getInstance() {
        if(CassandraService.instance == null) {
            CassandraService.instance = new CassandraService();
        }

        return CassandraService.instance;
    }

    public void connect(String node) {
        this.cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
        Metadata meta = cluster.getMetadata();

        System.out.println("Connected to Cassandra: " + meta.getClusterName());

        for(Host host:meta.getAllHosts()) {
            System.out.println("Datacenter: " + host.getDatacenter() + ", Host: " + host.getAddress() + ", Rack: " + host.getRack());
        }

        this.session = this.cluster.connect();
        this.mappingManager = new MappingManager(this.session);
    }

    public void createSchema() {
        this.session.execute("CREATE KEYSPACE IF NOT EXISTS shop WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 3 };");
        this.session.execute("CREATE TABLE IF NOT EXISTS shop.users (id uuid PRIMARY KEY, email text, name text, password text, admin boolean);");
    }

    public void querySchema() {
        ResultSet results = this.session.execute("SELECT * FROM shop.users;");

        for(Row row:results) {
            System.out.println(row.toString());
        }
    }

    public Session getSession() {
        return this.session;
    }

    public MappingManager getMappingManager() {
        return this.mappingManager;
    }

    public void close() {
        this.cluster.close();
    }

}
