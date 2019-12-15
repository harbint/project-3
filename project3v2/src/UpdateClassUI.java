import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateClassUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load User");
    public JButton btnSave = new JButton("Save User");

    public JTextField txtName = new JTextField(20);
    public JTextField txtType= new JTextField(20);



    public UpdateClassUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Change Passsword");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Name "));
        line1.add(txtName);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Password "));
        line2.add(txtType);
        view.getContentPane().add(line2);


        btnLoad.addActionListener(new LoadButtonListerner());

        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            UserModel user = new UserModel();
            try {
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_USER;


                user = StoreManager.getInstance().getDataAdapter().loadUser(user.mUsername);

                if (user == null) {
                    JOptionPane.showMessageDialog(null, "User NOT exists!");
                }
                else {
                    txtName.setText(user.mUsername);
                    txtType.setText(String.valueOf(user.mUserType));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            Gson gson = new Gson();
            String type = txtType.getText();

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "name cannot be empty!");
                return;
            }

            user.mUsername = name;
            user.mUserType = Integer.parseInt(type);
            try {
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_USER;
                msg.data = gson.toJson(user);

                int  res = StoreManager.getInstance().getDataAdapter().saveUser(user);

                if (res == IDataAdapter.USER_SAVE_FAILED)
                    JOptionPane.showMessageDialog(null, "user is NOT saved successfully!");
                else
                    JOptionPane.showMessageDialog(null, "user is SAVED successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}