package librarysystem;

import javax.swing.*;
import java.awt.*;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

public class BookCheckout extends JFrame implements LibWindow {

    private static final long serialVersionUID = 2L;
    public static final BookCheckout INSTANCE = new BookCheckout();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    //somthing
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;

    private JPanel lowerPanel;

    private JLabel title;


    private JTable table;

    private DefaultTableModel tableModel;
    public BookCheckout(){
        init();
    }

    @Override
    public void init() {
        defineTopPanel();
        defineLowerPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);

        setTitle("Book Checkout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(mainPanel);
        setSize(660,500);
        Main.centerFrameOnDesktop(this);
        isInitialized = true;
    }

    private void defineTopPanel(){
        title = new JLabel("Book Checkout");
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(title);

    }



    public void defineLowerPanel() {

        JButton backToMainButn = new JButton("<= Back to Main");
        backToMainButn.addActionListener((e)->{
            this.setVisible(false);
            LibrarySystem.INSTANCE.init();
            LibrarySystem.INSTANCE.setVisible(true);
        });
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
        lowerPanel.add(backToMainButn);
    }
    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void isInitialized(boolean val) {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var book =new BookCheckout();
            book.setVisible(true);
        });
    }
}
