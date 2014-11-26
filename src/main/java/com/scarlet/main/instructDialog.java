package com.scarlet.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class instructDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	/**
	 * Create the dialog.
	 */
	public instructDialog() {
		setTitle("\u0406\u043D\u0441\u0442\u0440\u0443\u043A\u0446\u0456\u044F \u043A\u043E\u0440\u0438\u0441\u0442\u0443\u0432\u0430\u0447\u0430");

		setBounds(100, 100, 600, 581);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(311, 507, 263, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						instructDialog.this.hide();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		JButton button = new JButton("");
		button.addActionListener(e ->{instructDialog.this.hide();});
		button.setIcon(new ImageIcon("C:\\Users\\\u041F\u0430\u0432\u043B\u043E\\workspace\\Turings Machine\\src\\main\\resources\\Untitled.png"));
		button.setBounds(10, 33, 575, 463);
		getContentPane().add(button);
		
		JLabel label = new JLabel("\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u0430 \u0440\u043E\u0437\u0440\u043E\u0431\u043B\u0435\u043D\u0430: \u041A\u0443\u0437\u044C \u041F.\u0421. ");
		label.setFont(new Font("Times New Roman", Font.BOLD, 14));
		label.setBounds(45, 501, 232, 14);
		getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("\u0413\u0440\u0443\u043F\u0430: 343\u0421\u043A");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(45, 526, 200, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\u041C\u0430\u0448\u0438\u043D\u0430 \u0422\u044E\u0440\u0456\u043D\u0433\u0430, \u0432\u0435\u0440\u0441\u0456\u044F 1.1");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		label_1.setBounds(183, 8, 263, 25);
		getContentPane().add(label_1);
	}
}
