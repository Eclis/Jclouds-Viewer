/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer.view;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.jclouds.blobstore.domain.StorageMetadata;

/**
 *
 * @author Eclis
 */
public class StorageMetadataTreeCellRender extends DefaultTreeCellRenderer {

    public StorageMetadataTreeCellRender() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Object valueConverted = value;
        if (value instanceof DefaultMutableTreeNode) {
            valueConverted = ((StorageMetadata)((DefaultMutableTreeNode) value).getUserObject()).getName();
        }
        return super.getTreeCellRendererComponent(tree, valueConverted, sel, expanded, leaf, row, hasFocus);
    }
}
