package librarysystem;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AllMemberIdsWindow extends JFrame implements LibWindow {
	public static AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
	private boolean isInitialized = false;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;

	private JPanel mainPanel;

	private JPanel topPanel;

	private JPanel lowerPanel;

	private JPanel middlePanel;

	private ControllerInterface ci = new SystemController();

	AllMemberIdsWindow() {
	}

	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel allIDsLabel = new JLabel("All Member IDs");
		Util.adjustLabelFont(allIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(allIDsLabel);
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());

		// Create a JTable with a DefaultTableModel
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Member ID");
		tableModel.addColumn("First Name");
		tableModel.addColumn("Last Name");
		tableModel.addColumn("Tel");
		tableModel.addColumn("Address");


		// Fetch member data and add it to the table model
		List<LibraryMember> members = ci.getAllMembers(); // Assuming you have a method to fetch all member data
		for (LibraryMember member : members) {
			tableModel.addRow(new Object[]{
					member.getMemberId(),
					member.getFirstName(),
					member.getLastName(),
					member.getTel(),

					member.getAddress() == null ? "N/A":member.getAddress().toString(),

			});
		}

		table = new JTable(tableModel);

		// Wrap the table in a scroll pane to handle scrolling
		scrollPane = new JScrollPane(table);

		middlePanel.add(scrollPane, BorderLayout.CENTER);
	}

	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
	}

	public void setData(String data) {
		// You may not need this method as you're populating data in the table directly.
	}

	private void addBackButtonListener(JButton button) {
		button.addActionListener(evt -> {
			setVisible(false);
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}


}

