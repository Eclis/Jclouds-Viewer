/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer.model;

import eclis.jcloudsviewer.model.connection.Connection;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;

/**
 *
 * @author usuario
 */
public class JVComputeServiceContextFactory {

    public static ComputeServiceContext createContext() {
        Connection con = Configuration.getInstance().getConnection();
        Provider provider = Configuration.getInstance().getProvider();

        ComputeServiceContext computeServiceContext = new ComputeServiceContextFactory().createContext(
                provider.getProviderCService(), con.getLogin(),
                con.getPassword());
        return computeServiceContext;
    }
}
