/*
 * 
 * This is the summary dialog for displaying all Employee details
 * 
 * */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class EmployeeSummaryDialog extends JDialog implements ActionListener {
	// vector with all Employees details
	private JTable employeeTable;
	private DefaultTableModel tableModel;
	private JButton back;
    private Vector<Vector<Object>> FixedEmployees;
	
	public EmployeeSummaryDialog(Vector<Object> allEmployees) {
		setTitle("Employee Summary");
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(850, 500);
		setLocation(350, 250);
		
		//Fix allEmployees for listAll but converting to a proper Vector<Vector<Object>>
		
        FixedEmployees = new Vector<>();
        for (Object obj : allEmployees) {
            if (obj instanceof Vector) {
                FixedEmployees.add((Vector<Object>) obj);
            }
        }
        
		JScrollPane scrollPane = new JScrollPane(summaryPane());
		setContentPane(scrollPane);
		setVisible(true);
	}
	
	// initialise container
	public Container summaryPane() {
		JPanel summaryDialog = new JPanel(new MigLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		
		// column center alignment
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		// column left alignment 
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		
		
        Vector<String> columnHeaders = new Vector<>();
        columnHeaders.add("ID");
        columnHeaders.add("PPS Number");
        columnHeaders.add("Surname");
        columnHeaders.add("First Name");
        columnHeaders.add("Gender");
        columnHeaders.add("Department");
        columnHeaders.add("Salary");
        columnHeaders.add("Full Time");
        
		// column widths
		int[] colWidth = { 15, 100, 120, 120, 50, 120, 80, 80 };
		
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);

		// construnct table and choose table model for each column
		tableModel = new DefaultTableModel(this.FixedEmployees, columnHeaders) {
			
			public Class getColumnClass(int columnNumber) {
				switch (columnNumber) {
                case 0: return Integer.class;   // ID
                case 4: return Character.class; // Gender
                case 6: return Double.class;    // Salary
                case 7: return Boolean.class;   // Full-Time
                default: return String.class;
				}// end switch
			}// end getColumnClass
		};

		employeeTable = new JTable(tableModel);

		// set alignments
		employeeTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		employeeTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		employeeTable.getColumnModel().getColumn(6).setCellRenderer(new DecimalFormatRenderer());

		employeeTable.setEnabled(false);
		employeeTable.setPreferredScrollableViewportSize(new Dimension(800, (15 * employeeTable.getRowCount() + 15)));
		employeeTable.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(employeeTable);

		buttonPanel.add(back = new JButton("Back"));
		back.addActionListener(this);
		back.setToolTipText("Return to main screen");
		
		summaryDialog.add(buttonPanel,"growx, pushx, wrap");
		summaryDialog.add(scrollPane,"growx, pushx, wrap");
		scrollPane.setBorder(BorderFactory.createTitledBorder("Employee Details"));
		
		return summaryDialog;
	}// end summaryPane

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back){
			dispose();
		}

	}
	// format for salary column
	static class DecimalFormatRenderer extends DefaultTableCellRenderer {
		 private static final DecimalFormat format = new DecimalFormat("\u20ac ###,###,##0.00" );

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			 JLabel label = (JLabel) c;
			 label.setHorizontalAlignment(JLabel.RIGHT);
			 // format salary column
			value = format.format((Number) value);

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}// end getTableCellRendererComponent
	}// DefaultTableCellRenderer
}// end class EmployeeSummaryDialog
