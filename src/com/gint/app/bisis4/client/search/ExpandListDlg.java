package com.gint.app.bisis4.client.search;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import com.gint.util.gui.WindowUtils;

public class ExpandListDlg extends JDialog {

	public ExpandListDlg(Frame owner) {
		super(owner, "Ekspandovani prefiks", true);
		
		spExpandList.setViewportView(lbExpandList);
		spExpandList.setFocusable(false);
		lbExpandList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnOK.doClick();
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					btnCancel.doClick();
					return;
				}

			}
		});
		lbExpandList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() > 1)
					btnOK.doClick();
			}
		});

		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				selected = true;
				setVisible(false);
			}
		});
		btnOK.setFocusable(false);
		getRootPane().setDefaultButton(btnOK);
		btnOK.setIcon(new ImageIcon(getClass().getResource(
				"/com/gint/app/bisis4/client/images/ok.gif")));

		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				selected = false;
				setVisible(false);
			}
		});
		btnCancel.setFocusable(false);
		btnCancel.setIcon(new ImageIcon(getClass().getResource(
				"/com/gint/app/bisis4/client/images/remove.gif")));

		MigLayout layout = new MigLayout("insets dialog, wrap", "[270lp]",
				"[300lp][]");
		setLayout(layout);
		add(spExpandList, "grow, wrap");
		add(btnCancel, "gaptop para, split 2, tag cancel");
		add(btnOK, "gaptop para, wrap, tag ok");
		pack();
		WindowUtils.centerOnScreen(this);
	}

	public boolean isSelected() {
		return selected;
	}
    public void setList(List exp){
    	plm = new ExpandListModel(exp);
    	lbExpandList.setModel(plm);
    }
	public String getSelectedItem() {
		return (String) plm.getElementAt(lbExpandList.getSelectedIndex());
	}

	private JList lbExpandList = new JList();

	private JScrollPane spExpandList = new JScrollPane();

	private JButton btnOK = new JButton("Izaberi");

	private JButton btnCancel = new JButton("Odustani");

	private boolean selected = false;

	private ExpandListModel plm;

}