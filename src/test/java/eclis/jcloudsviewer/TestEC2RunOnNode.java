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
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.domain.InstanceType;

//import com.google.common.collect.Iterables;

public class TestEC2RunOnNode {

	public static void main(String[] args) {
		// get a context with ec2 that offers the portable ComputeService API
		ComputeServiceContext context = new ComputeServiceContextFactory()
				.createContext("aws-ec2", "AKIAI5KOMPUQRXRU7C4A",
						"OvdLv0yTYfyXxDP7rNC1ckMvkaUhjT5TsH+O2J3G");

		// here's an example of the portable api
		/*Set<? extends Location> locations = context.getComputeService()
				.listAssignableLocations();*/

//		Set<? extends Image> images = (Set<? extends Image>) context
//				.getComputeService().listImages();

                // use the m1 small with amazon linux
                ComputeService computeService = context.getComputeService();
                
		Template template = computeService.templateBuilder()
				.hardwareId(InstanceType.T1_MICRO)
				.osFamily(OsFamily.AMZN_LINUX).build();
                
		// pick the highest version of the RightScale CentOS template
//		Template template = context.getComputeService().templateBuilder()
//				.osFamily(OsFamily.CENTOS).build();

		// specify your own groups which already have the correct rules applied
                
		// specify your own keypair for use in creating nodes
                
               
		// run a couple nodes accessible via group
		try {
			computeService.createNodesInGroup("eclisserver", 1, template);

			// when you need access to very ec2-specific features, use the
			// provider-specific context
			/*AWSEC2Client ec2Client = AWSEC2Client.class.cast(context
					.getProviderSpecificContext().getApi());*/

			// ex. to get an ip and associate it with a node
			//NodeMetadata node = Iterables.get(nodes, 0);
			
			//context.getComputeService().runScriptOnNode(arg0, arg1)
		} catch (RunNodesException e) {
			e.printStackTrace();
		}

		context.close();
	}

}
