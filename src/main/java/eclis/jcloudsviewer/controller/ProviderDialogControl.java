package eclis.jcloudsviewer.controller;

import eclis.jcloudsviewer.model.Configuration;
import eclis.jcloudsviewer.model.Provider;
import eclis.jcloudsviewer.model.connection.Connection;
import eclis.jcloudsviewer.view.ProviderDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Eclis
 */
public class ProviderDialogControl {

    private ProviderDialog dialog;

    public ProviderDialogControl() {
        dialog = new ProviderDialog(this);
    }

    public void openDialog() {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void select() {
        final String login = dialog.getjTextFieldAKI().getText();
        final String password = dialog.getjTextFieldSAK().getText();

        if (login.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Oops! Please complete all fields!");
        } else {

            dialog.dispose();

            final MainFrameControl mainFrameControl = new MainFrameControl();
            mainFrameControl.openMainFrame();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    Connection conn = new Connection();
                    conn.setLogin(login);
                    conn.setPassword(password);

                    Provider provider = (Provider) dialog.getjComboBoxProvider().getSelectedItem();

                    Configuration.getInstance().setProvider(provider);
                    Configuration.getInstance().setConnection(conn);

                    mainFrameControl.listContainers();
                    mainFrameControl.listNodes();
                }
            }).start();
        }
    }
}
