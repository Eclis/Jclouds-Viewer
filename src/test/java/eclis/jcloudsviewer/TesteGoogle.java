/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;

/**
 *
 * @author Aluno_Enfase
 */
public class TesteGoogle {
    public static void main(String[] args) {
        BlobStoreContext context = new BlobStoreContextFactory().createContext(
                                    "googlestorage", "GOOGCREIZ4BSOOV2WFCO",
                                    "J1vCxAHg1yOEOAtnl1pSLQca0BoDb/t1qhtdVMM/");

        BlobStore blobStore = context.getBlobStore();
        System.out.println(blobStore);

    }
}
