package com.scarlet.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Component;

public class Project {
	private int countOfTapeRegisters = 15;
	private JFrame frame;
	//list,which consist of all value of tape
	private List<String> tapeValueList = new ArrayList<String>();
	//list, which consist of all number of values in tape
	private List<Integer> tapeNumberList = new ArrayList<Integer>();
	
	
	//number,which repainting in tape
	private int leftMaxNumber = -7;
	private int rightMaxNumber = 7;
	
	//tape which consist values
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project window = new Project();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	//initialize the tape of machine
	public void initializeTape(){
		for(int i =0;i<this.countOfTapeRegisters;i++){
			tapeNumberList.add((-7+i));
			tapeValueList.add("-");
			table.setValueAt(tapeNumberList.get(i), 0, i);
			table.setValueAt(tapeValueList.get(i), 1, i);
			
		}
	}
	//takes the current values at tape
	public void getValueAtTape(){
		for(int i =0;i<this.countOfTapeRegisters;i++)
			tapeValueList.add(i, table.getValueAt(1, i).toString());
	}
	
	//set on table repainted values
	public void setTapeValues(){
		for(int i =0;i<this.countOfTapeRegisters;i++){
			table.setValueAt(tapeNumberList.get(i), 0, i);
			table.setValueAt(tapeValueList.get(i), 1, i);
			
		}
		
	}
	//schrolling tape to the left
	public void scrollLeft(){
		leftMaxNumber-=1;
		rightMaxNumber-=1;

		System.out.println(leftMaxNumber + " " + rightMaxNumber);
		tapeNumberList.add(0, leftMaxNumber);
		tapeNumberList.remove(tapeNumberList.size()-1);
		this.getValueAtTape();
		
		tapeValueList.add(0,"-");
		tapeValueList.remove(tapeValueList.size()-1);
		
		this.setTapeValues();
	}
	//schrolling tape to the right
	public void scrollRight(){
		this.leftMaxNumber+=1;
		this.rightMaxNumber+=1;
		

		System.out.println(leftMaxNumber + " " + rightMaxNumber);
		this.tapeNumberList.add(tapeNumberList.size(),rightMaxNumber);
		this.tapeNumberList.remove(0);
		this.getValueAtTape();
		
		tapeValueList.add("-");
		tapeValueList.remove(0);
		
		this.setTapeValues();
	}
	public Project() {
		this.initialize();
		this.initializeTape();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		
		frame.setTitle("Машина Тюрінга, Кузь П.С.,група 343Ск");
		frame.setBounds(100, 100, 633, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Головне");
		menu.setToolTipText("Меню управління програмою");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Нова програма");
		menuItem.setToolTipText("Створення нової програми");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menu.add(menuItem);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		JMenuItem menuItem_1 = new JMenuItem("Завантажити програму");
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menuItem_1.setToolTipText("Завантаження існуючої програми з файла");
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Зберегти програму");
		menuItem_2.setToolTipText("Зберегти програму в файл");
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menu.add(menuItem_2);
		
		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);
		
		JMenuItem menuItem_3 = new JMenuItem("Вихід");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		menuItem_3.setToolTipText("Вихід з програми ");
		menu.add(menuItem_3);
		
		JMenu menu_1 = new JMenu("Виконання");
		menu_1.setToolTipText("Меню режимів виконання програми");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_5 = new JMenuItem("Виконати ");
		menuItem_5.setToolTipText("Виконати автоматично програму");
		menuItem_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menu_1.add(menuItem_5);
		
		JSeparator separator_2 = new JSeparator();
		menu_1.add(separator_2);
		
		JMenuItem menuItem_4 = new JMenuItem("По-кроково");
		menuItem_4.setToolTipText("По-кроково виконати програму");
		menuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menu_1.add(menuItem_4);
		
		JMenu menu_2 = new JMenu("Швидкість");
		menu_2.setToolTipText("Швидкість виконання програми");
		menuBar.add(menu_2);
		
		JMenu menu_3 = new JMenu("Допомога");
		menu_3.setToolTipText("Меню допомоги користувачам");
		menuBar.add(menu_3);
		
		JMenuItem menuItem_7 = new JMenuItem("Інструкція користувача");
		menu_3.add(menuItem_7);
		
		JSeparator separator_3 = new JSeparator();
		menu_3.add(separator_3);
		
		JMenuItem menuItem_6 = new JMenuItem("Про програму");
		menu_3.add(menuItem_6);
		frame.getContentPane().setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 385, 32);
		frame.getContentPane().add(toolBar);
		
		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		table.setToolTipText("Стрічка машини Тюрінга");
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(47, 79, 79), new Color(0, 0, 0), null, null));
		table.setBackground(new Color(128, 0, 0));
		table.setForeground(new Color(0, 0, 0));
		table.setBounds(50, 58, 518, 32);
		frame.getContentPane().add(table);
		
		JButton btnNewButton = new JButton("<<");
		btnNewButton.addActionListener((ActionEvent e)->{
			this.scrollLeft(); 
		});
		btnNewButton.setBounds(1, 45, 49, 58);
		frame.getContentPane().add(btnNewButton);
		
		JButton button = new JButton(">>");
		button.addActionListener(e -> {
			this.scrollRight();
		});
		button.setBounds(568, 45, 49, 58);
		frame.getContentPane().add(button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\scarlet_bean\\Workspace\\MainBean\\src\\main\\resources\\arrow_linear_top.png"));
		lblNewLabel.setBounds(268, 87, 80, 80);
		frame.getContentPane().add(lblNewLabel);
		
		
	}
}