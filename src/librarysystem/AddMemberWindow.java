package librarysystem;

import business.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddMemberWindow extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static AddMemberWindow INSTANCE =new AddMemberWindow();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addMember;
    String pathToImage;

    JPanel topPanel, midPanel, botPanel, buttonPanel;
    JTextField id, fname, lname, tel, street, city, state, zip;
    JLabel idLabel, fnameLabel,lnameLabel, telLabel, streetLabel, cityLabel, stateLabel, zipLabel;

    JButton submitButton;
    //
    void submitButtonActionListener(){
        submitButton.addActionListener((evt)->{
            String id = this.id.getText();
            String fname = this.fname.getText();
            String lname = this.lname.getText();

            String tel = this.tel.getText();
            String zip = this.zip.getText();
            String city = this.city.getText();
            String state = this.state.getText();
            String street = this.street.getText();

            System.out.print(fname+", "+lname+", "+ ", "+id);

            if (this.id.getText().isEmpty() || this.fname.getText().isEmpty() || this.lname.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "" +
                        "Please provide firstname, lastname" +
                        " and memberId","ERROR",JOptionPane.ERROR_MESSAGE);

                setFieldsEmpty();
                return;
            }

            if (ci.memberIdExist(id) == false){
                JOptionPane.showMessageDialog(this, "" +
                        "Duplicate Id. Please try again with a different Id." +
                        " and memberId","ERROR",JOptionPane.ERROR_MESSAGE);
                setFieldsEmpty();
                return;

            }
            CreateMemberDto request = new CreateMemberDto();


            String[] arrUserInputs = {street,city,state,zip,id,fname,lname,tel};




           try {
                ci.saveNewMember(fname, lname, id,tel,street,city,zip,state);
                JOptionPane.showMessageDialog(this, "" +
                        "Member Added Successfully" +
                        " and memberId");
                setFieldsEmpty();
            } catch (LibrarySystemException ex) {
                JOptionPane.showMessageDialog(this, "" +
                        ex.getMessage() +
                        " and memberId","ERROR",JOptionPane.ERROR_MESSAGE);
                setFieldsEmpty();
                return;
            }


        });
    }
    private void createForm() {
        // Create the form panel with GridLayout
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 2)); // 2 columns for labels and text fields

        // Initialize text fields and labels, and add them to the layout
        idLabel = new JLabel("Member Label");
        id = new JTextField(20);

        fnameLabel = new JLabel("First Name");
        fname = new JTextField(20);

        lnameLabel = new JLabel("Last Name");
        lname = new JTextField(20);

        telLabel = new JLabel("Tel");
        tel = new JTextField(20);

        streetLabel = new JLabel("Street");
        street = new JTextField(20);

        cityLabel = new JLabel("City");
        city = new JTextField(20);

        stateLabel = new JLabel("State");
        state = new JTextField(20);

        zipLabel = new JLabel("Zip");
        zip = new JTextField(20);

        // Add labels and text fields to the panel
        topPanel.add(idLabel);
        topPanel.add(id);
        topPanel.add(fnameLabel);
        topPanel.add(fname);
        topPanel.add(lnameLabel);
        topPanel.add(lname);
        topPanel.add(telLabel);
        topPanel.add(tel);
        topPanel.add(streetLabel);
        topPanel.add(street);
        topPanel.add(cityLabel);
        topPanel.add(city);
        topPanel.add(stateLabel);
        topPanel.add(state);
        topPanel.add(zipLabel);
        topPanel.add(zip);

        // Create and add the submit button
         submitButton = new JButton("Add Member");
        topPanel.add(submitButton);

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel);

        var content = getContentPane();
        content.setLayout(new BorderLayout());
        content.add(mainPanel, BorderLayout.CENTER);
        setFieldsEmpty();
        submitButtonActionListener();
    }

    private void setFieldsEmpty(){
        this.id.setText("");
        this.fname.setText("");
        this.lname.setText("");
        this.tel.setText("");
        this.zip.setText("");
        this.city.setText("");
        this.state.setText("");
        this.street.setText("");
    }




    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            AllMemberIdsWindow.INSTANCE,
            AllBookIdsWindow.INSTANCE,
            AddMemberWindow.INSTANCE
    };

    public static void hideAllWindows() {

        for(LibWindow frame: allWindows) {
            frame.setVisible(false);

        }
    }
    public AddMemberWindow(){
        init();
    }
    public void init() {
//        formatContentPane();
//        setPathToImage();
//        insertSplashImage();
//        clearContent();
        createForm();

        createMenus();
        //pack();
        setSize(660,500);
        isInitialized = true;
        setFieldsEmpty();
        setTitle("Add Member");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        AddMemberWindow.INSTANCE.setTitle("Add Member");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Util.centerFrameOnDesktop(INSTANCE);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    private void addMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);
        login = new JMenuItem("Login");
        login.addActionListener(new AddMemberWindow.LoginListener());
        allBookIds = new JMenuItem("All Book Ids");
        allBookIds.addActionListener(new AddMemberWindow.AllBookIdsListener());
        allMemberIds = new JMenuItem("All Member Ids");
        allMemberIds.addActionListener(new AddMemberWindow.AllMemberIdsListener());
        addMember= new JMenuItem("Add Member");
        addMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                INSTANCE.hideAllWindows();
//                AddMemberWindow.INSTANCE.init();
//                Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
//                AddMemberWindow.INSTANCE.setVisible(true);
                setVisible(false);
                AddMemberWindow newWindow = new AddMemberWindow();
                Util.centerFrameOnDesktop(newWindow);
                newWindow.setVisible(true);

            }
        });


        options.add(login);
        options.add(allBookIds);
        options.add(allMemberIds);
        options.add(addMember);
    }
    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            AddMemberWindow.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);

        }

    }
    class AllBookIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            AddMemberWindow.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for(String s: ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllBookIdsWindow.INSTANCE.setData(sb.toString());
            AllBookIdsWindow.INSTANCE.pack();
            //AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);

        }

    }
    class AllMemberIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            AddMemberWindow.hideAllWindows();
            AllMemberIdsWindow.INSTANCE = new AllMemberIdsWindow();
            AllMemberIdsWindow.INSTANCE.init();
            AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);


            AddMemberWindow.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for(String s: ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            //AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);


        }

    }

    public void clearContent() {
//        removeAll();
        revalidate();
        repaint();
    }
}
