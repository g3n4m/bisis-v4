package com.gint.app.bisis4.client.editor.groupinv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.editorutils.CenteredDialog;
import com.gint.app.bisis4.format.UItem;

public class GroupInvTextDialog extends CenteredDialog {
	
	private JTextArea textArea;
	private JButton okButton;  
	private JButton cancelButton;	
	private JPanel buttonsPanel;  
	private String name;
	private String newText;
	
	
	public GroupInvTextDialog(String name){
		super(BisisApp.getMainFrame(),true);
	
		this.name = name;
		this.setSize(280, 300);		
		setTitle("Promena vrednosti za "+name);		
		initialize();
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 10 10 10 10","","[]"));
		textArea = new JTextArea(10,20);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane,"grow, wrap");
		okButton = new JButton();
		okButton.setSize(new java.awt.Dimension(88,26));
		okButton.setText("Potvrdi");
		okButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/ok.gif")));		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				handleOk();
			}			
		});
		okButton.addKeyListener(new KeyAdapter(){
   public void keyReleased(KeyEvent e){  
		     handleKeys(e);    
		   }     
		 });		
		cancelButton = new JButton("Odustani");
		cancelButton.setIcon(new ImageIcon(getClass().getResource(
		     "/com/gint/app/bisis4/client/images/remove.gif")));		
		buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		add(buttonsPanel,"grow");
		
		
		cancelButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {				
			handleCancel();
		}			
		});
		 cancelButton.addKeyListener(new KeyAdapter(){
		   public void keyReleased(KeyEvent e){  
		     handleKeys(e);    
		   }     
		 });	
		textArea.addKeyListener(new KeyAdapter(){
		   public void keyReleased(KeyEvent e){  
		     handleKeys(e);    
		   }     
		 });	
	}
	
	private void handleCancel() {
		dispose();		
	}

	private void handleKeys(KeyEvent e) {
		 if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
	      handleCancel(); 		
	}
	
	public String getNewText(){
		return newText;
	}
	
	private void handleOk(){
		newText = textArea.getText();
		setVisible(false);
	}

}
