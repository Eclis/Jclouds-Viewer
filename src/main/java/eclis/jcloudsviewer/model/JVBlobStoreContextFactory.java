/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer.model;

import eclis.jcloudsviewer.model.connection.Connection;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;

/**
 *
 * @author Eclis
 */
public class JVBlobStoreContextFactory {

    public static BlobStoreContext createContext() {
        Connection con = Configuration.getInstance().getConnection();
        Provider provider = Configuration.getInstance().getProvider();

        BlobStoreContext blobContext = new BlobStoreContextFactory().createContext(
                provider.getProviderBService(), con.getLogin(),
                con.getPassword());
        return blobContext;
    }
}
