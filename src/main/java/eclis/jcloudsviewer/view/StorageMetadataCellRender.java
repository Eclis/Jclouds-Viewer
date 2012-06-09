/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eclis.jcloudsviewer.view;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.jclouds.blobstore.domain.StorageMetadata;

/**
 *
 * @author Eclis
 */
public class StorageMetadataCellRender extends DefaultListCellRenderer{

    public StorageMetadataCellRender() {
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, ((StorageMetadata) value).getName(), index, isSelected, cellHasFocus);
    }
}
