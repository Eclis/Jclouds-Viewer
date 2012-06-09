/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer;

/**
 *
 * @author Eclis
 */
//import java.util.Set;

//import org.jclouds.aws.ec2.AWSEC2Client;
import java.awt.Image;
import java.util.Set;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.domain.Location;
//import org.jclouds.ec2.domain.InstanceType;

//import com.google.common.collect.Iterables;

public class TestEC2IP {

    public static void main(String[] args) {
        // get a context with ec2 that offers the portable ComputeService API
        ComputeServiceContext context = new ComputeServiceContextFactory().createContext("aws-ec2", "AKIAJV5NEFKWHXS5ZH4A",
                "qzVz/hOyIGfexe3aYYFyr72uKybMIOLqdEzZfI4W");

        // here's an example of the portable api
        Set<? extends Location> locations = context.getComputeService().listAssignableLocations();

	Set<? extends Image> images = (Set<? extends Image>) context.getComputeService().listImages();

        // use the m1 small with amazon linux
       // Template template = context.getComputeService().templateBuilder().hardwareId(InstanceType.T1_MICRO).osFamily(OsFamily.AMZN_LINUX).build();

//		// pick the highest version of the RightScale CentOS template
//		Template template = context.getComputeService().templateBuilder()
//				.osFamily(OsFamily.CENTOS).build();

        // specify your own groups which already have the correct rules applied
        //template.getOptions().as(AWSEC2TemplateOptions.class).securityGroups("quicklaunch-1");

        // specify your own keypair for use in creating nodes
        //template.getOptions().as(AWSEC2TemplateOptions.class).keyPair("Eclis");

        // run a couple nodes accessible via group
//        Set<? extends NodeMetadata> nodes;
//        try {
//            nodes = context.getComputeService().createNodesInGroup("eclisserver",
//                    2, template);
//
//            // when you need access to very ec2-specific features, use the
//            // provider-specific context
//            AWSEC2Client ec2Client = AWSEC2Client.class.cast(context.getProviderSpecificContext().getApi());
//
//            // ex. to get an ip and associate it with a node
//            //NodeMetadata node = Iterables.get(nodes, 0);
//            String ip = ec2Client.getElasticIPAddressServices().allocateAddressInRegion(node.getLocation().getId());
//            ec2Client.getElasticIPAddressServices().associateAddressInRegion(
//                    node.getLocation().getId(), ip, node.getProviderId());
//        } catch (RunNodesException e) {
//            e.printStackTrace();
        }

       // context.close();
    }
//}