package eclis.jcloudsviewer.model;

import eclis.jcloudsviewer.model.connection.Connection;

/**
 *
 * @author Eclis
 */
public class Configuration {

    private static Configuration instance;
    private Provider provider;
    private Connection connection;

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
