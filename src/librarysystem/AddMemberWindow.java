package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class AddMemberWindow extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static AddMemberWindow INSTANCE =new AddMemberWindow();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addMember;
    String pathToImage;

    JPanel topPanel;
    JTextField id, fname, lname, tel, street, city, state, zip;
    JLabel idLabel, fnameLabel,lnameLabel, telLabel, streetLabel, cityLabel, stateLabel, zipLabel;


    //
    private void createForm(){

        // create the form panel
    topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());

    // Initialize text fields and add to la



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
    private AddMemberWindow(){}
    public void init() {
//        formatContentPane();
//        setPathToImage();
//        insertSplashImage();

        createMenus();
        //pack();
        setSize(660,500);
        isInitialized = true;

        AddMemberWindow.INSTANCE.setTitle("Add Member");
        AddMemberWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
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
                AddMemberWindow.INSTANCE.init();
                Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
                AddMemberWindow.INSTANCE.setVisible(true);
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
            AddMemberWindow.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);

        }

    }
    class AllBookIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
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
            AddMemberWindow.hideAllWindows();
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
}
