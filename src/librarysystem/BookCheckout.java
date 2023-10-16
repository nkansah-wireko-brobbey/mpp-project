package librarysystem;

import javax.swing.*;
import java.awt.*;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.function.IntFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class BookCheckout extends JFrame implements LibWindow {

    private static final long serialVersionUID = 2L;
    public static final BookCheckout INSTANCE = new BookCheckout();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    //somthing
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;

    private JPanel titlePanel;
    private JPanel topMiddlePanel;

    private JComboBox<String> memberCombo;

    private JComboBox<String> bookCombo;

    private JPanel lowerPanel;

    private JLabel title;


    private JTable table;

    private DefaultTableModel tableModel;

    private JScrollPane scrollPane;

    private JButton checkoutButton;
    public BookCheckout(){
        init();
    }

    @Override
    public void init() {
        defineTopMost();
        defineTopMiddle();

        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);

        setTitle("Book Checkout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(mainPanel);
        setSize(660,500);
        Main.centerFrameOnDesktop(this);
        isInitialized = true;
    }

    private void defineTopPanel(){

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(topMiddlePanel, BorderLayout.SOUTH);

    }
    private void defineTopMost(){
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("Book Checkout");
        titlePanel.add(title);

    }

    private void defineTopMiddle(){
        topMiddlePanel = new JPanel();
        topMiddlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        checkoutButton = new JButton("Checkout");

        JLabel memberLabel = new JLabel("Member Ids:");
        JLabel bookLabel = new JLabel("Book Ids:");

        ControllerInterface ci = new SystemController();

        List<String> ISBNList = ci.allBookIds();
        String[] memberIdList = ci.allMemberIds().toArray(new String[ci.allMemberIds().size()]);

        bookCombo = new JComboBox<>(ISBNList.toArray(new String[ISBNList.size()]));
        memberCombo = new JComboBox<>(memberIdList);
        topMiddlePanel.add(memberLabel);
        topMiddlePanel.add(memberCombo);
        topMiddlePanel.add(bookLabel);
        topMiddlePanel.add(bookCombo);
        topMiddlePanel.add(checkoutButton);

        checkoutButton.addActionListener(e -> {
            String memberId = (String) memberCombo.getSelectedItem();
            String bookIsbn = (String) bookCombo.getSelectedItem();

            if (memberId == null || memberId.isEmpty() || bookIsbn == null || bookIsbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "", JOptionPane.ERROR_MESSAGE);
                return;
            }


//            memberId = memberId.substring(0, memberId.indexOf(" - ")).trim();
//            bookIsbn = bookIsbn.substring(0, bookIsbn.indexOf(" - ")).trim();

            try {
                emptyCombo();
                ci.checkBook(memberId, bookIsbn);
                JOptionPane.showMessageDialog(this, "Book successfully checked out", "", INFORMATION_MESSAGE);

                Book newBook = ci.getBook(bookIsbn);

                //Get the JTable

                int rowCount = table.getRowCount();
                int columnIndexToSearch = 0; // The index of the column to search
                int selectedRow = 0;

                for (int row = 0; row < rowCount; row++) {
                    Object cellValue = table.getValueAt(row, columnIndexToSearch);
                    if (cellValue != null && cellValue.toString().equals(bookIsbn)) {
                        // Found the row with the specific text
//                        JOptionPane.showMessageDialog(null, "Text found in row: " + row);
                        selectedRow = row;
                        break;
                    }
                }

                tableModel.setValueAt(newBook.getCopyNums().size(),selectedRow,5);
//                getCheckoutHistoryList();
            } catch (LibrarySystemException k) {
                JOptionPane.showMessageDialog(this, k.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }

        });

    }

    void emptyCombo() {
        memberCombo.setSelectedItem(null);
        bookCombo.setSelectedItem(null);
    }

    private void defineMiddlePanel(){
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create a JTable with six columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Title");
        tableModel.addColumn("Copies");
        tableModel.addColumn("Authors");
        tableModel.addColumn("Available");
        tableModel.addColumn("Available Copies");


        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Populate the JTable with sample data
        ControllerInterface ci = new SystemController();

        List<Book> bookList = ci.getAllBooks();

        if (bookList == null){
            Util.displayMessage("No books found");
            return;
        }

        for (Book book:
                bookList) {
            Vector<String> rowData = new Vector<>();
            rowData.add(book.getIsbn());
            rowData.add(book.getTitle());
            Integer a = book.getNumCopies();
            rowData.add(a.toString());
            rowData.add(book.getAuthors().toString());
            rowData.add(book.isAvailable()?"Yes":"False");
            Integer num = book.getMaxCheckoutLength();
            rowData.add(num.toString());

            tableModel.addRow(rowData);
        }

        scrollPane = new JScrollPane(table);
        middlePanel.add(scrollPane, BorderLayout.CENTER);
    }
    private void defineTable(){

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
