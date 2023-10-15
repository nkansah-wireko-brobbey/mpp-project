package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.ControllerInterface;
import business.SystemController;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	//somthing
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private JTextField searchTextField;

	private JButton copyButton;

	private JPanel formPanel;

	private JTable jTable;
	private JScrollPane scrollPane;

	private DefaultTableModel tableModel;

	private int selectedRow = -1;

	//Singleton class
	private AllBookIdsWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		formPanel();
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel AllIDsLabel = new JLabel("All Book IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		labelPanel.add(AllIDsLabel);
//		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.setLayout(new BorderLayout());
		topPanel.add(labelPanel, BorderLayout.NORTH);
		topPanel.add(formPanel, BorderLayout.SOUTH);
	}

	public void formPanel(){
		formPanel = new JPanel();
		// Set the layout manager for the panel
		formPanel.setLayout(new FlowLayout());

		// Create a JTextField
		searchTextField = new JTextField(20); // 20 is the initial column width
		formPanel.add(searchTextField);

		// Create a JButton
		copyButton = new JButton("Add Copy");
		formPanel.add(copyButton);

		// Add an ActionListener to the button
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = searchTextField.getText();
				int count = jTable.getSelectedRowCount();
				if (count == 1){
					selectedRow = jTable.getSelectedRow();
					String isbn = (String) jTable.getValueAt(selectedRow, 0);

					searchTextField.setText(isbn);
					ControllerInterface ci = new SystemController();

				if (!ci.bookIdExists(isbn)){
					JOptionPane.showMessageDialog(null,"Book" +
							" Not Found","ISBN Check",JOptionPane.ERROR_MESSAGE);
					return;
				}

				Book newBook = ci.copyBook(isbn);
				searchTextField.setText("");

				tableModel.setValueAt(newBook.getNumCopies(),selectedRow,2);




				JOptionPane.showMessageDialog(null, "A copy of" +
						" "+text+" has been added successfully");

				}else if (count > 1) {
					JOptionPane.showMessageDialog(null, "Please select single a book.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Select a book row", "", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
//		tableMouseListener();
	}
	

	
	public void defineLowerPanel() {
		
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
	}
	
	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}

	public void tableMouseListener(){
		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int count = jTable.getSelectedRowCount();
				if (count == 1) {
					selectedRow = jTable.getSelectedRow();
					Book book = ci.getBook((String) tableModel.getValueAt(selectedRow, 0));
					searchTextField.setText(book.getTitle());

				} else {
					searchTextField.setText("");
				}
				super.mouseClicked(e);
			}
		});
	}
	
//	public void setData(String data) {
//		textArea.setText(data);
//	}
//	public void defineMiddlePanel() {
//		middlePanel = new JPanel();
//		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
//		middlePanel.setLayout(fl);
//		textArea = new TextArea(8, 20);
//		//populateTextArea();
//		middlePanel.add(textArea);
//
//	}
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());

		// Create a JTable with six columns
		tableModel = new DefaultTableModel();
		tableModel.addColumn("ISBN");
		tableModel.addColumn("Title");
		tableModel.addColumn("Copies");
		tableModel.addColumn("Authors");
		tableModel.addColumn("Available");


		jTable = new JTable(tableModel);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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

			tableModel.addRow(rowData);
		}

		scrollPane = new JScrollPane(jTable);
		middlePanel.add(scrollPane, BorderLayout.CENTER);
		tableMouseListener();
	}


//	private void populateTextArea() {
//		//populate
//		List<String> ids = ci.allBookIds();
//		Collections.sort(ids);
//		StringBuilder sb = new StringBuilder();
//		for(String s: ids) {
//			sb.append(s + "\n");
//		}
//		textArea.setText(sb.toString());
//	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
}
