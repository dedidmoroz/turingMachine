package com.scarlet.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.swing.JFrame;

import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;

import org.controlsfx.dialog.Dialogs;

public class Project {
	//Main executer
	private Timer timer = null;
	private Integer interval = Integer.valueOf("1000");
	
	
	//parametrs of command
	private String charFromAlp;
	private String carretWay;
	private Integer carretMachineState;
	
	
	//Looger console
	private DefaultListModel logger = new DefaultListModel();
	//current coordinate of the carret
	private int currentPosition=0;
	
	private String currentValue;
	
	//count of registers
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
	private JTextField charsField;
	private JTable table_1;
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
			tapeValueList.add(" ");
			table.setValueAt(tapeNumberList.get(i), 1, i);
			table.setValueAt(tapeValueList.get(i), 0, i);
			
		}
		
		
	}
	//takes the current values at tape
	public void getValueAtTape(){
		for(int i =0;i<this.countOfTapeRegisters;i++)
			tapeValueList.add(i, table.getValueAt(0, i).toString());
	}
	//set on table repainted values
	public void setTapeValues(){
		for(int i =0;i<this.countOfTapeRegisters;i++){
			table.setValueAt(tapeNumberList.get(i), 1, i);
			table.setValueAt(tapeValueList.get(i), 0, i);
			
		}
		
	}

	
	   //get the value of register which is selected
		public String getRegisterValue(){
			for(int i = 0;i<this.countOfTapeRegisters;i++){
				if(Integer.valueOf(table.getValueAt(1, i).toString()).equals(this.currentPosition)){
					this.currentValue =table.getValueAt(0, i).toString();
					System.out.println("Value is:"+this.currentValue);
					return this.currentValue;
					
				} 
			}
			return this.currentValue;
		}
		//set the value of register which is selected
		public void setRegisterValue(String value){
			for(int i = 0;i<this.countOfTapeRegisters;i++){
				if(Integer.valueOf(table.getValueAt(1, i).toString()).equals(this.currentPosition)){
					this.table.setValueAt(value, 0, i);
				}
			}
		} 
	
	//schrolling tape to the left
	public void scrollLeft(){
		leftMaxNumber-=1;
		rightMaxNumber-=1;
		this.currentPosition -=1;
		
		
		System.out.println(leftMaxNumber + " " +currentPosition+" "+ rightMaxNumber);
		tapeNumberList.add(0, leftMaxNumber);
		tapeNumberList.remove(tapeNumberList.size()-1);
		this.getValueAtTape();
		
		tapeValueList.add(0," ");
		tapeValueList.remove(tapeValueList.size()-1);
		
		this.setTapeValues();
	}
	//schrolling tape to the right
	public void scrollRight(){
		this.leftMaxNumber+=1;
		this.rightMaxNumber+=1;
		this.currentPosition+=1;

		System.out.println(leftMaxNumber + " " +currentPosition+" "+ rightMaxNumber);
		this.tapeNumberList.add(tapeNumberList.size(),rightMaxNumber);
		this.tapeNumberList.remove(0);
		this.getValueAtTape();
		
		tapeValueList.add(" ");
		tapeValueList.remove(0);
		this.getRegisterValue();
		this.setTapeValues();
	 
	}
	
	public Project() {
		this.initialize();
		this.initializeTape();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/**
	 * 
	 */
	//makes the editor for a new program
	private void newProgram(){
		for(int i =0;i<table.getColumnCount();i++){
			table.setValueAt("", 0, i);
		}
		
		for(int i=0;i<table_1.getRowCount();i++){
			for(int j = 0;j<table_1.getColumnCount();j++){
				table_1.setValueAt("", i, j);
			}
		}
		logger.clear();
	}
	//show window in which you can change interval
	private void setIntervalByDialog(){
		Project.this.interval = Integer.valueOf(JOptionPane.showInputDialog("Введіть інтервал(мілісекунди):"));
	}
	
	
	//add a alphabet to table
	private void addAlphabet(){
		if(charsField.getText().length()!=0){
			char [] array = charsField.getText().toCharArray();
			for(int i = 0;i<array.length;i++){
				table_1.setValueAt(array[i], i, 0);
			}
		}else {
			JOptionPane.showMessageDialog(null,  "Введіть алфавіт","Помилка", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	private void initialize() {
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Павло\\workspace\\mavenproject1\\src\\main\\resources\\css\\icon.png"));
		frame.setResizable(false);
		
		frame.setTitle("Машина Тюрінга, Кузь П.С.,група 343Ск");
		frame.setBounds(100, 100, 633, 464);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Головне");
		//menu.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\template.gif"));
		menu.setToolTipText("Меню управління програмою");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Нова програма");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					Project.this.newProgram();
			}
		});
		menuItem.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\edit.gif"));
		//menuItem.setIcon(new ImageIcon("C:\\Users\\scarlet_bean\\Workspace\\MainBean\\src\\main\\resources\\ico\\beige\\document.gif"));
		menuItem.setToolTipText("Створення нової програми");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menu.add(menuItem);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		JMenuItem menuItem_1 = new JMenuItem("Завантажити програму");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Project.this.makeLoadFromFile();
			}
		});
		menuItem_1.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\clipboard.gif"));
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menuItem_1.setToolTipText("Завантаження існуючої програми з файла");
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Зберегти програму");
		menuItem_2.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\diskette.gif"));
		menuItem_2.setToolTipText("Зберегти програму в файл");
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menuItem_2.addActionListener(e ->{
			try {
				Project.this.makeSaveToFile();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		menu.add(menuItem_2);
		
		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);
		
		JMenuItem menuItem_3 = new JMenuItem("Вихід");
		menuItem_3.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\database.gif"));
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		menuItem_3.setToolTipText("Вихід з програми ");
		menu.add(menuItem_3);
		
		JMenu menu_1 = new JMenu("Виконання");
		menu_1.setIcon(new ImageIcon("C:\\Users\\scarlet_bean\\Workspace\\MainBean\\src\\main\\resources\\ico\\beige\\arrow-right.gif"));
		menu_1.setToolTipText("Меню режимів виконання програми");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_5 = new JMenuItem("Виконати ");
		menuItem_5.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\play.gif"));
		menuItem_5.setToolTipText("Виконати автоматично програму");
		menuItem_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menuItem_5.addActionListener((ActionEvent e)->{
			this.executeProgram();
		});
		menu_1.add(menuItem_5);
		
		JSeparator separator_2 = new JSeparator();
		menu_1.add(separator_2);
		
		JMenuItem menuItem_4 = new JMenuItem("По-кроково");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Project.this.stepExecute();
			}
		});
		menuItem_4.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\fastforward.gif"));
		menuItem_4.setToolTipText("По-кроково виконати програму");
		menuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menu_1.add(menuItem_4);
		
		JMenu menu_2 = new JMenu("Швидкість");
		menu_2.setIcon(new ImageIcon("C:\\Users\\scarlet_bean\\Workspace\\MainBean\\src\\main\\resources\\ico\\beige\\clock.gif"));
		menu_2.setToolTipText("Швидкість виконання програми");
		menuBar.add(menu_2);
		
		JMenuItem menuItem_8 = new JMenuItem("Інтервал");
		menuItem_8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Project.this.setIntervalByDialog();
			}
		});
		menuItem_8.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\chart.gif"));
		menu_2.add(menuItem_8);
		
		JMenu menu_3 = new JMenu("Допомога");
		menu_3.setIcon(new ImageIcon("C:\\Users\\scarlet_bean\\Workspace\\MainBean\\src\\main\\resources\\ico\\beige\\two-docs.gif"));
		menu_3.setToolTipText("Меню допомоги користувачам");
		menuBar.add(menu_3);
		
		JMenuItem menuItem_7 = new JMenuItem("Інструкція користувача");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					instructDialog dialog = new instructDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		menu_3.add(menuItem_7);
		
		JSeparator separator_3 = new JSeparator();
		menu_3.add(separator_3);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		table.setForeground(Color.WHITE);
		table.setBounds(50, 58, 518, 16);
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
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\arrow_linear_top.png"));
		lblNewLabel.setBounds(268, 74, 80, 80);
		frame.getContentPane().add(lblNewLabel);
		
		charsField = new JTextField();
		charsField.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		charsField.setForeground(Color.RED);
		charsField.setBackground(Color.BLACK);
		charsField.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		charsField.setBounds(93, 175, 160, 32);
		frame.getContentPane().add(charsField);
		charsField.setColumns(10);
		
		JLabel label = new JLabel("Алфавіт:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(10, 178, 80, 23);
		frame.getContentPane().add(label);
		
		JList list = new JList();
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(128, 128, 128), new Color(128, 128, 128), Color.GRAY, Color.GRAY));
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);
		list.setBounds(386, 193, 231, 208);
		frame.getContentPane().add(list);
		
		JLabel label_1 = new JLabel("Хід виконання:");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		label_1.setBounds(386, 170, 117, 23);
		frame.getContentPane().add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 211, 366, 190);
		frame.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		table_1.setForeground(Color.WHITE);
		table_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		table_1.setBackground(new Color(139, 0, 0));
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		String [] tableHeadersName = new String[20];
		tableHeadersName[0] = "T*";
		for(int i =1;i<20;i++) tableHeadersName[i] = "q"+String.valueOf(i);
	 
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			tableHeadersName
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(35);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(6).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(7).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(8).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(9).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(10).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(11).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(12).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(13).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(14).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(15).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(16).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(17).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(18).setPreferredWidth(50);
		table_1.getColumnModel().getColumn(19).setPreferredWidth(50);
		scrollPane.setViewportView(table_1);
		
		list.setModel(logger);
		JButton btnNewButton_1 = new JButton("Добавити");
		btnNewButton_1.addActionListener((ActionEvent e)->{
			this.addAlphabet();
		});
		
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		btnNewButton_1.setBounds(263, 176, 113, 32);
		frame.getContentPane().add(btnNewButton_1);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(1, 0, 269, 27);
		frame.getContentPane().add(toolBar);
		
		JButton button_1 = new JButton("");
		button_1.setToolTipText("Нова програма");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project.this.newProgram();
			}
		});
		button_1.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\edit.gif"));
		toolBar.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.setToolTipText("Завантажити програму");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project.this.makeLoadFromFile();
			}
		});
		button_2.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\clipboard.gif"));
		toolBar.add(button_2);
		
		JButton button_3 = new JButton("");
		button_3.setToolTipText("Зберегти програму");
		button_3.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\diskette.gif"));
		button_3.addActionListener(e -> {
			try {
				Project.this.makeSaveToFile();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		toolBar.add(button_3);
		
		JButton button_4 = new JButton("");
		button_4.setToolTipText("Запустити алгоритм");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project.this.executeProgram();
			}
		});
		button_4.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\play.gif"));
		toolBar.add(button_4);
		
		JButton button_5 = new JButton("");
		button_5.setToolTipText("Запустити по-кроково");
		button_5.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\fastforward.gif"));
		button_5.addActionListener(e -> {
			Project.this.stepExecute();
		});
		toolBar.add(button_5);
		
		JButton button_6 = new JButton("");
		button_6.setToolTipText("Встановити інтервал");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Project.this.setIntervalByDialog();
			}
		});
		button_6.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\chart.gif"));
		toolBar.add(button_6);
		
		JButton button_7 = new JButton("");
		button_7.setToolTipText("Вийти");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button_7.setIcon(new ImageIcon("C:\\Users\\Павло\\workspace\\Turings Machine\\src\\main\\resources\\ico\\beige\\database.gif"));
		toolBar.add(button_7);
		
		
	}
	
	
	Pattern pattern = Pattern.compile("^[0-9a-zA-Z_-]\\/[RLN]\\/q[\\d]{1,3}$");
	Matcher matcher = null;
	public void parseComand(String comand){
		
		 matcher = pattern.matcher(comand);
		if(matcher.matches()) {
			String [] pars = comand.split("/");
			if(pars.length==3){
				this.charFromAlp = pars[0];
				this.carretWay = pars[1];
				this.carretMachineState = Integer.parseInt(pars[2].substring(1));
				System.out.println(charFromAlp+" "+carretWay+" "+carretMachineState);
				this.goCarret(this.charFromAlp, this.carretWay, this.carretMachineState);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Помилка в команді", "Помилка", JOptionPane.ERROR_MESSAGE, null);
		}

	}
	
	public void goCarret(String charFromAlp,String carretWay,int state){
		if(!charFromAlp.equals("_"))
			this.setRegisterValue(charFromAlp);
		switch (carretWay) {
		case "L":
			this.scrollLeft();
			break;
		case "R":
			this.scrollRight();
			break;
		default:
			break;
		}
		
	}
	private String command = null;
	
	@SuppressWarnings("unchecked")
	public void executeProgram(){
		logger.clear();
		String value = table_1.getValueAt(0,1).toString();
		this.parseComand(value);
		logger.addElement(this.getRegisterValue()+" ->"+this.charFromAlp+"| go to "+this.carretMachineState+" state");
		
		this.timer = new Timer(this.interval, e ->{
			for(int i =0;i<charsField.getText().length();i++){	
				 
					if(!this.getRegisterValue().equals(" ")){
						if( this.getRegisterValue().toString().equals(table_1.getValueAt(i, 0).toString()) ) {
							try{
								command = table_1.getValueAt(i, this.carretMachineState).toString();
							} catch(NullPointerException q){
								logger.addElement("-------------");
								logger.addElement("Аварійно завершено!");
								JOptionPane.showMessageDialog(null, "Помилка десь");
								timer.stop();
								break;
							}
					
							
							if(command !=null){
								System.out.println("Executing " + table_1.getValueAt(i, this.carretMachineState).toString()+ " State:"+this.carretMachineState);
								logger.addElement(this.getRegisterValue()+" ->"+this.charFromAlp+"| go to "+this.carretMachineState+" state");
								parseComand(command);
								command = null;
							} else {
								
								logger.addElement("-------------");
								logger.addElement("Завершено!");
								JOptionPane.showMessageDialog(null, "Завершено!");
								timer.stop();
								break;
							}
						}
					} else {
						logger.addElement("-------------");
						logger.addElement("Завершено! =)");
						logger.addElement("Кількість дій: "+(this.logger.getSize()-2));
						JOptionPane.showMessageDialog(null, "Завершено!");
						timer.stop();
						break;
					}
			}
		 
			
		});
		timer.start();
	}
	//execute program in steps
	@SuppressWarnings("unchecked")
	private Boolean isFirst = true;
	public void stepExecute(){
		if(this.isFirst){
			logger.clear();
			String value = table_1.getValueAt(0,1).toString();
			this.parseComand(value);
			logger.addElement(this.getRegisterValue()+" ->"+this.charFromAlp+"| go to "+this.carretMachineState+" state");
		
			this.isFirst = false;
			
		} else {
				for(int i =0;i<charsField.getText().length();i++){	
				   	 if(!this.getRegisterValue().equals(" ")){
						if( this.getRegisterValue().toString().equals(table_1.getValueAt(i, 0).toString()) ) {
							try{
								command = table_1.getValueAt(i, this.carretMachineState).toString();
							} catch(NullPointerException q){
								logger.addElement("-------------");
								logger.addElement("Аварійно завершено!");
								JOptionPane.showMessageDialog(null, "Помилка десь");
								this.isFirst = true;
								break;
							}
					
							
							if(command !=null){
								System.out.println("Executing " + table_1.getValueAt(i, this.carretMachineState).toString()+ " State:"+this.carretMachineState);
								logger.addElement(this.getRegisterValue()+" ->"+this.charFromAlp+"| go to "+this.carretMachineState+" state");
								parseComand(command);
								command = null;
							} else {
				
								logger.addElement("-------------");
								logger.addElement("Завершено!");
								JOptionPane.showMessageDialog(null, "Завершено!");
								this.isFirst = true;
								break;
							}
						}
					} else {
						logger.addElement("-------------");
						logger.addElement("Завершено успішно! ");
						logger.addElement("Кількість дій: "+(this.logger.getSize()-2));
						JOptionPane.showMessageDialog(null, "Завершено!");
						this.isFirst = true;
						break;
					}
			}
		}
	}
	
	private List<String> listOfComands = new ArrayList<>();
	//make saving to file
	
	private boolean flag = false;
	public void makeSaveToFile() throws IOException{
		flag = false;
		listOfComands.clear();
		JFileChooser chooser = new JFileChooser();
		
		chooser.setMultiSelectionEnabled(false);
		int choice = chooser.showSaveDialog(null);
		if(choice == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			//Create a file if not exist
			if(!file.exists()) 
				file.createNewFile();
			
			try(PrintWriter writer = new PrintWriter(file);){
				//make load commands to collection
				for(int i =1;i<table_1.getColumnCount();i++){
					for (int j = 0; j < table_1.getRowCount(); j++) {
						try{
							if(table_1.getValueAt(j, i)!=null 
												/*|| !table_1.getValueAt(j, i).toString().startsWith("")*/){
								this.listOfComands.add(i+","+j+","+table_1.getValueAt(j, i).toString());
								System.out.println(table_1.getValueAt(j, i));
								
							}
							
						}catch(NullPointerException e) {
							JOptionPane.showMessageDialog(null, "Список команд пустий");
						}
					}
				}
				
				//make saving to file
				for(String i: listOfComands){
					writer.println(i);
				}
				if(!this.charsField.getText().isEmpty()) {
					writer.println("[АЛФАВІТ]");
					writer.write(this.charsField.getText());
				}
			}catch(IOException e){
				JOptionPane.showMessageDialog(null, "Проблеми з збереженням");
			}
		}
	}
	//make load from file
	public void makeLoadFromFile(){
		//initialized
		this.listOfComands.clear();
		JFileChooser chooser = new JFileChooser();
		int choose = chooser.showOpenDialog(null);
		
		//connect to file
		if(choose == JFileChooser.APPROVE_OPTION){
			File f = chooser.getSelectedFile();
			//make load from file
			try(BufferedReader br = new BufferedReader(new FileReader(f));){
				String s = "";
				while((s = br.readLine())!=null){
					listOfComands.add(s);
					System.out.println(s);
				}
				
			}catch(IOException e){
				JOptionPane.showMessageDialog(null, "Проблеми з завантаження з файла");
			}
			
			String [][] dataFromFile = new String[20][20];
			int i =0; int j = 0,colCount = 0;
			if(!listOfComands.isEmpty()){
				for(String str : listOfComands){
					if(!str.startsWith("[")){
						String [] mas = str.split(",");
						i = Integer.parseInt(mas[0]);
						j = Integer.parseInt(mas[1]);
						String comand = mas[2];
						table_1.setValueAt(comand, j, i);
					} else {
						break;
					}
				}
				
				this.charsField.setText(listOfComands.get(listOfComands.size()-1));
				this.addAlphabet();
			}
		}
	}
}
