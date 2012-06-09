/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer;

/**
 *
 * @author Eclis
 */
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.domain.ComputeMetadata;

public class TestEC2ListNodes {

	public static void main(String[] args) {
		ComputeServiceContext context = new ComputeServiceContextFactory()
				.createContext("aws-ec2", "AKIAI5KOMPUQRXRU7C4A",
						"OvdLv0yTYfyXxDP7rNC1ckMvkaUhjT5TsH+O2J3G");

		ComputeService computeService = context.getComputeService();

		for (ComputeMetadata node : computeService.listNodes()) {
                    
		node.getId(); // how does jclouds address this in a global scope
		node.getProviderId(); // how does the provider api address this in a
									// specific scope
		node.getName(); // if the node is named, what is it?
		node.getLocation(); // where in the world is the node
                //ID, ProviderId, Name, Location, iso 3166, Os family, state, hostname. hardware (ram, processors.cores, processors.speed);
		System.out.println("Node: " + node);
                System.out.println(computeService.getNodeMetadata(node.getId()).getId());

                computeService.getNodeMetadata(node.getId()).getHardware();
                computeService.getNodeMetadata(node.getId()).getHostname();
                        
                        
		}
                
                
                context.close();
	}

}
