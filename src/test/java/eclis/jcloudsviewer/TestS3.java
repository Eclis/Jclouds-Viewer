/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer;

/**
 *
 * @author Eclis
 */
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextFactory;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobBuilder;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.StorageMetadata;

public class TestS3 {

	public static void main(String[] args) {
		// get a context with amazon that offers the portable BlobStore API
		BlobStoreContext context = new BlobStoreContextFactory().createContext(
				"aws-s3", "AKIAI5KOMPUQRXRU7C4A",
				"OvdLv0yTYfyXxDP7rNC1ckMvkaUhjT5TsH+O2J3G");

		String bucket = "eclisbucket";
                
                //Eclis test begin
                
                //Criar container ** FUNCIONANDO
                boolean verificaAi;
                BlobStore blobStore = context.getBlobStore();
		verificaAi = blobStore.createContainerInLocation(null, bucket);
                if(verificaAi){
                    System.out.println("++++++++Bucket criado++++++");
                }
                
                //listar container **FUCNIONANDO AMEM
                for (StorageMetadata blobs : blobStore.list()) {
			System.out.println(blobs.getName());
		}

                boolean podeDeletar = false;//Criarei uma variavel pra parar de deltar o bucket kkk
                //deletar container **FUNCIONANDO
                if(podeDeletar == true && blobStore.containerExists(bucket)){
                    blobStore.deleteContainer(bucket);
                    System.out.println("++++++++Bucket DELETADO++++++");
                }
                
                //Criar blob **FUNCIONANDO
                BlobBuilder bb = blobStore.blobBuilder("blobTest1.txt");//Cria o blob
		Blob blob = bb.build();
		blob.setPayload("We are the champion");//seta conteudo
		blobStore.putBlob(bucket, blob);
                
                //listar blob ** FUNCIONANDO AMEM.
                blobStore = context.getBlobStore();

		for (StorageMetadata blobs : blobStore.list(bucket)) {
			System.out.println(blobs.getName());
                        System.out.println(blobs.getUri());
                        System.out.println(blobs.getLastModified());
		}
                
                //deletar blob **FUNCIONANDO
                blobStore.removeBlob(bucket, "blobTest1");
                
                //Eclis tests finish

		/*// create a container in the default location
		BlobStore blobStore = context.getBlobStore();
		blobStore.createContainerInLocation(null, bucket);
                

		// add blob
                BlobBuilder bb = blobStore.blobBuilder("blobTest");
		Blob blob = bb.build();
		blob.setPayload("We are the champion");
		blobStore.putBlob(bucket, blob);
		

		// when you need access to s3-specific features,
		// use the provider-specific context
		AWSS3Client s3Client = AWSS3Client.class.cast(context
				.getProviderSpecificContext().getApi());

		// make the object world readable
		String publicReadWriteObjectKey = "public-read-write-acl";
		S3Object object = s3Client.newS3Object();

		object.getMetadata().setKey(publicReadWriteObjectKey);
		object.setPayload("hello world");
		s3Client.putObject(bucket, object, PutObjectOptions.NONE);
		// withAcl(CannedAccessPolicy.PUBLIC_READ)*/

		context.close();
	}

}
