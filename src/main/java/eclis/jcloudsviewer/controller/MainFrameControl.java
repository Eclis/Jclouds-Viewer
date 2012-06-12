package eclis.jcloudsviewer.controller;

import eclis.jcloudsviewer.model.JVBlobStoreContextFactory;
import eclis.jcloudsviewer.model.JVComputeServiceContextFactory;
import eclis.jcloudsviewer.view.CreateBlobDialog;
import eclis.jcloudsviewer.view.CreateContainerDialog;
import eclis.jcloudsviewer.view.CreateNodeDialog;
import eclis.jcloudsviewer.view.MainFrame;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.domain.BlobBuilder;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;

/**
 *
 * @author Eclis
 */
public class MainFrameControl {

    private MainFrame mainFrame;
    private CreateContainerDialog dialogCreateContainer;
    private CreateBlobDialog dialogCreateBlob;
    private CreateNodeDialog dialogCreateNode;
    private File file;

    public MainFrameControl() {
        dialogCreateContainer = new CreateContainerDialog(this);
        dialogCreateBlob = new CreateBlobDialog(this);
        dialogCreateNode = new CreateNodeDialog(this);
    }

    public void openMainFrame() {
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }

    public void listContainers() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BlobStoreContext context = JVBlobStoreContextFactory.createContext();
                ArrayList<StorageMetadata> metadata = new ArrayList<StorageMetadata>();
                BlobStore blobStore = context.getBlobStore();
                DefaultListModel list = new DefaultListModel();

                for (StorageMetadata storage : blobStore.list()) {
                    metadata.add(storage);
                    list.addElement(storage);
                }

                mainFrame.getjListContainers().setModel(list);
            }
        }).start();
    }

    private void addChildrenContainer(StorageMetadata container, DefaultMutableTreeNode parentNode) {

        BlobStoreContext context = JVBlobStoreContextFactory.createContext();
        BlobStore blobStore = context.getBlobStore();

        ListContainerOptions options = new ListContainerOptions();
        options.recursive();

        for (StorageMetadata child : blobStore.list(container.getName(), options)) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode();
            node.setUserObject(child);
            parentNode.add(node);

            /*if (child.getType().equals(StorageType.RELATIVE_PATH)) {
            addChildrenFolder(blobStore, container, child, node);
            }*/
        }
    }

    private void addChildrenFolder(BlobStore blobStore, StorageMetadata container,
            StorageMetadata folder, DefaultMutableTreeNode parentNode) {

        ListContainerOptions options = new ListContainerOptions();
        options.inDirectory(folder.getName());

        for (StorageMetadata child : blobStore.list(container.getName(), options)) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode();
            node.setUserObject(child);

            parentNode.add(node);

            if (child.getName().contains("/")) {
//                String[] strs = child.getName().split("/");
//                StorageMetadata newParent = new StorageMetadataImpl(StorageType.RELATIVE_PATH,
//                        null, strs[0], null, null, null, null, new HashMap<String, String>(0));
//                StorageMetadata newChild = new StorageMetadataImpl(StorageType.RELATIVE_PATH,
//                        null, strs[1], null, null, null, null, new HashMap<String, String>(0));
//                addChildrenFolder(blobStore, container, newChild, newParent);
            }
        }
    }

    public void listContents() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                StorageMetadata container = (StorageMetadata) mainFrame.getjListContainers().getSelectedValue();
                DefaultMutableTreeNode root = new DefaultMutableTreeNode();
                root.setUserObject(container);

                addChildrenContainer(container, root);

                DefaultTreeModel model = new DefaultTreeModel(root);

                mainFrame.getjTreeContents().setModel(model);
            }
        }).start();

    }

    public void openDialogCreateContainer() {
        dialogCreateContainer.setLocationRelativeTo(null);
        dialogCreateContainer.setVisible(true);
    }

    public void createContainer() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BlobStoreContext context = JVBlobStoreContextFactory.createContext();
                BlobStore blobStore = context.getBlobStore();
                String container = dialogCreateContainer.getjTextFieldContainer().getText().trim().toLowerCase();

                if (container.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Oops! Please enter a valid name!");
                } else {
                    boolean containerCreated;
                    containerCreated = blobStore.createContainerInLocation(null, container);
                    if (containerCreated) {
                        JOptionPane.showMessageDialog(null, "Container created!");
                        dialogCreateContainer.dispose();
                        listContainers();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Name!");
                    }
                }
            }
        }).start();
    }

    public void deleteContainer() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                StorageMetadata storage = (StorageMetadata) mainFrame.getjListContainers().getSelectedValue();
                String container = storage.getName();
                BlobStoreContext context = JVBlobStoreContextFactory.createContext();
                BlobStore blobStore = context.getBlobStore();

                if (container != null && blobStore.containerExists(container)) {
                    blobStore.deleteContainer(container);
                    JOptionPane.showMessageDialog(null, "Container deleted!");
                    listContainers();
                } else {
                    JOptionPane.showMessageDialog(null, "Container doesn't exist");
                }
            }
        }).start();
    }

    public void openDialogCreateBlob() {
        dialogCreateBlob.setLocationRelativeTo(null);
        dialogCreateBlob.setVisible(true);
    }

    public void chooseBlob() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(dialogCreateBlob.getjTextFieldFilePath());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    dialogCreateBlob.getjTextFieldFilePath().setText(fileChooser.getSelectedFile().getAbsolutePath());
                    if (!dialogCreateBlob.getjTextFieldFilePath().getText().isEmpty()) {
                        file = fileChooser.getSelectedFile();
                    }
                }
            }
        }).start();
    }

    public void uploadBlob() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BlobStoreContext context = JVBlobStoreContextFactory.createContext();
                BlobStore blobStore = context.getBlobStore();
                String container = null;
                if (!mainFrame.getjListContainers().isSelectionEmpty()) {
                    container = ((StorageMetadata) (mainFrame.getjListContainers().getSelectedValue())).getName();
                } else {
                    JOptionPane.showMessageDialog(null, "Please, choose a container");
                }

                if (file != null && container != null) {
                    BlobBuilder bb = blobStore.blobBuilder(file.getName());
                    Blob blob = bb.build();
                    blob.setPayload(file);
                    blobStore.putBlob(container, blob);
                    JOptionPane.showMessageDialog(null, "Success!!!");
                    dialogCreateBlob.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Fail!!!");
                }
            }
        }).start();
    }

    public void createBlob() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BlobStoreContext context = JVBlobStoreContextFactory.createContext();
                BlobStore blobStore = context.getBlobStore();
                String container = null;
                if (!mainFrame.getjListContainers().isSelectionEmpty()) {
                    container = ((StorageMetadata) (mainFrame.getjListContainers().getSelectedValue())).getName();
                } else {
                    JOptionPane.showMessageDialog(null, "Please, choose a container");
                }

                if (container != null) {
                    BlobBuilder bb = blobStore.blobBuilder(dialogCreateBlob.getjTextFieldFileName().getText() + ".txt");//Cria o blob
                    Blob blob = bb.build();
                    blob.setPayload(dialogCreateBlob.getjTextArea().getText());//seta conteudo
                    if (dialogCreateBlob.getjTextFieldFileName().getText() != null) {
                        blobStore.putBlob(container, blob);
                        listContents();
                        JOptionPane.showMessageDialog(null, "Success!!!");
                        dialogCreateBlob.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Fail!!!");
                    }
                }
            }
        }).start();
    }

    public void deleteBlob() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                BlobStoreContext context = JVBlobStoreContextFactory.createContext();
                BlobStore blobStore = context.getBlobStore();
                String container = ((StorageMetadata) (mainFrame.getjListContainers().getSelectedValue())).getName();
                String blob = ((StorageMetadata) ((DefaultMutableTreeNode) mainFrame.getjTreeContents().getSelectionPath().getLastPathComponent()).getUserObject()).getName();
                if (mainFrame.getjTreeContents().getSelectionPath().getLastPathComponent() != null
                        && container != null) {
                    blobStore.removeBlob(container, blob);
                    JOptionPane.showMessageDialog(null, "Success!!!");
                } else {
                    JOptionPane.showMessageDialog(null, "Fail!!!");
                }
            }
        }).start();
    }

    public void listNodes() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                ComputeServiceContext context = JVComputeServiceContextFactory.createContext();
                ArrayList<ComputeMetadata> metadatas = new ArrayList<ComputeMetadata>();
                ComputeService compute = context.getComputeService();
                JTable table = mainFrame.getjTableNodes();
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                while (tableModel.getRowCount() > 0) {
                    tableModel.removeRow(0);
                }
                String nodeId = "";
                String providerId = "";
                String nodeName = "";
                String location = "";
                String oS = "";
                String state = "";
                String hostname = "";

                for (ComputeMetadata node : compute.listNodes()) {
                    metadatas.add(node);
                    NodeMetadata metadata = compute.getNodeMetadata(node.getId());
                    nodeId = metadata.getId();
                    providerId = metadata.getProviderId();
                    nodeName = metadata.getName();
                    location = metadata.getLocation().getDescription();
                    oS = metadata.getOperatingSystem().getFamily().name();
                    state = metadata.getState().name();
                    hostname = metadata.getHostname();
                    String[] data = new String[]{nodeId, providerId, nodeName, location, oS, state, hostname};
                    tableModel.addRow(data);
                }
            }
        }).start();
    }

    public void deleteNode() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                ComputeServiceContext context = JVComputeServiceContextFactory.createContext();
                ComputeService compute = context.getComputeService();
                JTable table = mainFrame.getjTableNodes();
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                int rowSelected = table.getSelectedRow();
                if (rowSelected == -1) {
                    JOptionPane.showMessageDialog(null, "Please, select a node to delete!!!");
                } else {
                    String nodeId = (String) tableModel.getValueAt(rowSelected, 0);
                    compute.destroyNode(nodeId);
                    JOptionPane.showMessageDialog(null, "Node deleted!!!");
                    listNodes();
                }
            }
        }).start();
    }

    public void openDialogCreateNode() {
        dialogCreateNode.setLocationRelativeTo(null);
        dialogCreateNode.setVisible(true);

        ComputeServiceContext context = JVComputeServiceContextFactory.createContext();
        ComputeService compute = context.getComputeService();

        for (Hardware hd : compute.listHardwareProfiles()) {
            dialogCreateNode.getjComboBoxInstance().addItem(hd.getId());
        }

        for (OsFamily os : OsFamily.values()) {
            dialogCreateNode.getjComboBoxOS().addItem(os);
        }
    }

    public void createNode() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                ComputeServiceContext context = JVComputeServiceContextFactory.createContext();
                ComputeService compute = context.getComputeService();

                String instance = dialogCreateNode.getjComboBoxInstance().getSelectedItem().toString();
                OsFamily os = (OsFamily) dialogCreateNode.getjComboBoxOS().getSelectedItem();

                String nodeName = dialogCreateNode.getjTextFieldName().getText().toLowerCase().trim();
                int numberOfInstances = Integer.parseInt(dialogCreateNode.getjTextFieldNumber().getText());

                Template template = compute.templateBuilder().hardwareId(instance).osFamily(os).build();

                try {
                    compute.createNodesInGroup(nodeName, numberOfInstances, template);
                } catch (RunNodesException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Node created!!!");
                dialogCreateNode.dispose();
                listNodes();
            }
        }).start();
    }
}