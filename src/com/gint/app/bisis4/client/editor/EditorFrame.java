package com.gint.app.bisis4.client.editor;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicTreeUI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.actions.NewRecordAction;
import com.gint.app.bisis4.client.editor.editorutils.CardFrame;
import com.gint.app.bisis4.client.editor.editorutils.ProcTypeChooserDialog;
import com.gint.app.bisis4.client.editor.formattree.CurrFormat;
import com.gint.app.bisis4.client.editor.formattree.FormatUtils;
import com.gint.app.bisis4.client.editor.inventar.InventarPanel;
import com.gint.app.bisis4.client.editor.inventar.MonographInventarPanel;
import com.gint.app.bisis4.client.editor.inventar.SerialInventarPanel;
import com.gint.app.bisis4.client.editor.recordtree.CurrRecord;
import com.gint.app.bisis4.client.editor.recordtree.RecordUtils;
import com.gint.app.bisis4.client.editor.recordtree.RecordTree;
import com.gint.app.bisis4.client.editor.uploadfile.UploadPanel;
import com.gint.app.bisis4.format.UValidatorException;
import com.gint.app.bisis4.librarian.ProcessType;
import com.gint.app.bisis4.records.Record;



public class EditorFrame extends JInternalFrame {	
	
	private ZapisPanel zapisPanel = null;
	private JPanel panel;
	private InventarPanel inventarPanel = null;
	private UploadPanel uploadPanel = null;
	
	private JToolBar toolBar;	
	private JButton addUField;
	private JButton saveRecord;
	private JButton validateRecord;
	private JToggleButton inventarButton;
	private JToggleButton zapisButton;
	private JToggleButton uploadButton;
	private JButton listiciButton;
	private JButton procTypesButton;
  
  private CardLayout cardLayout; 
	
  private boolean recordUpdated = false; 
  private static Log log = LogFactory.getLog(EditorFrame.class.getName());
  
	/**
	 * This is the default constructor
	 */
	public EditorFrame() {
		super(Messages.getString("EDITOR_EDITORFRAMETITLE"), false, true, true, true);       //$NON-NLS-1$
		initialize();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);    
	}
	
	public boolean editorInitialize(Record rec){			
			if(rec!=null && !FormatUtils.isPubTypeDefined(BisisApp.getLibrarian(), rec.getPubType())){
				JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
						Messages.getString("EDITOR_OPTIONPANEMISSINGPROCESSTYPE"), //$NON-NLS-1$
						"Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				RecordUtils.unlockRecord(rec);
				return false;
			}			
			zapisPanel.initializeRecordPanel(rec);
			if (BisisApp.isFileMgrEnabled())
				uploadPanel.initializeDocList(rec);
			initializeInventarPanel();				
    zapisButton.doClick();
    saveRecord.setEnabled(false);
    if(rec==null)
    	procTypesButton.setEnabled(true);
    else
    	procTypesButton.setEnabled(false);
    return true;
		
	}
	
	private void initializeInventarPanel(){	
		if(RecordUtils.returnHoldingsNumber()==1){
      inventarPanel = new MonographInventarPanel();
      inventarButton.setEnabled(true);
    }
    else if(RecordUtils.returnHoldingsNumber()==2){
      inventarPanel = new SerialInventarPanel();
      inventarButton.setEnabled(true);
    }
    else{
    	inventarButton.setEnabled(false);      
    }        
	  inventarPanel.initializeForm();
   panel.add(inventarPanel,inventarPanel.getName());
		
	}
	
	
	
	private void initialize() {    
  	 setSize(1000,600);    
    saveRecord = new JButton(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/save.gif"))); //$NON-NLS-1$
    saveRecord.setToolTipText(Messages.getString("EDITOR_TOOLTIPSAVINGRECORD")); //$NON-NLS-1$
    addUField = new JButton(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/add.gif"))); //$NON-NLS-1$
    addUField.setToolTipText(Messages.getString("EDITOR_TOOLTIPADDNEWFIELD"));  //$NON-NLS-1$
    validateRecord = new JButton(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/Check.png"))); //$NON-NLS-1$
    validateRecord.setToolTipText(Messages.getString("EDITOR_TOOLTIPRECORDVALIDATION"));   //$NON-NLS-1$
    listiciButton = new JButton(new javax.swing.ImageIcon(EditorFrame.class.getResource(
    "/com/gint/app/bisis4/client/images/doc_rich16.png"))); //$NON-NLS-1$
    listiciButton.setToolTipText(Messages.getString("EDITOR_TOOLTIPCARDS"));     //$NON-NLS-1$
    
    procTypesButton = new JButton(new ImageIcon(getClass()
    		.getResource("/com/gint/app/bisis4/client/images/refresh.gif"))); //$NON-NLS-1$
    procTypesButton.setToolTipText(Messages.getString("EDITOR_TOOLTIPCHANGEPROCESSTYPE")); //$NON-NLS-1$
  
    zapisButton = new JToggleButton(Messages.getString("EDITOR_TOGGLEBUTTONRECORD")); //$NON-NLS-1$
    zapisButton.setIcon(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/edit.gif"))); //$NON-NLS-1$
    zapisButton.setMnemonic(KeyEvent.VK_Z);
    
    inventarButton = new JToggleButton(Messages.getString("EDITOR_TOGGLEBUTTONINVENTAR")); //$NON-NLS-1$
    inventarButton.setIcon(new ImageIcon(EditorFrame.class
        .getResource("/com/gint/app/bisis4/client/images/book16.png"))); //$NON-NLS-1$
    inventarButton.setMnemonic(KeyEvent.VK_I);    
    
    uploadButton = new JToggleButton("Dokumenta");
    inventarButton.setMnemonic(KeyEvent.VK_D);  
    
    
    // tool bar
  	toolBar = new JToolBar();    
  	toolBar.setOrientation(JToolBar.HORIZONTAL);		
  	toolBar.add(new NewRecordAction());       
  	toolBar.add(saveRecord);	
  	toolBar.add(addUField);		
  	toolBar.add(validateRecord);		 
  	toolBar.add(listiciButton);	
  	toolBar.add(procTypesButton);
    toolBar.add(Box.createGlue()); 
    ButtonGroup btnGroup = new ButtonGroup();    
    btnGroup.add(zapisButton);
    btnGroup.add(inventarButton);
    btnGroup.add(uploadButton);
    zapisButton.setSelected(true);   
    toolBar.add(zapisButton);	
    toolBar.add(inventarButton);
    toolBar.add(uploadButton);
    setUploadEnabled(false);
  	
  	zapisPanel = new ZapisPanel();    
  	inventarPanel = new InventarPanel();
  	uploadPanel = new UploadPanel();
   layoutPanels(); 
   
    // action listeners
    
  	addInternalFrameListener(new InternalFrameAdapter(){
  		public void internalFrameClosing(InternalFrameEvent e){
  			handleCloseEditor();
      }
  	});		
    
    addComponentListener(new ComponentAdapter(){      
      public void componentShown(ComponentEvent arg0) {
        zapisPanel.getFormatTree().requestFocus();        
      }
    });
  	
  	addUField.addActionListener(new ActionListener(){
  		public void actionPerformed(ActionEvent e) {			
  			handleAddUField();				
  		}		
  	});		
  	
  	saveRecord.addActionListener(new ActionListener(){
  		public void actionPerformed(ActionEvent e) {
  			handleSaveRecord();				
  		}			
  	});
    validateRecord.addActionListener(new ActionListener(){
  		public void actionPerformed(ActionEvent e) {
  			handleValidateRecord();				
  		}			
  	});
    inventarButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {      	
        cardLayout.show(panel, inventarPanel.getName());        
      }      
    });
    zapisButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        cardLayout.show(panel, zapisPanel.getName());        
      }      
    });
    uploadButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
							cardLayout.show(panel, uploadPanel.getName());
					}    	
    });
    
    listiciButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {       
        handleShowCardFrame();
      }    
    });    
    zapisPanel.getRecordTree().addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent arg0) {
          handleKeys(zapisPanel.getRecordTree(),arg0);        
      }
    });
    zapisPanel.getFormatTree().addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent arg0) {
          handleKeys(zapisPanel.getRecordTree(),arg0);        
      }
    });
    procTypesButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						handleOpenProcessTypeDilaog();				
					}    	
    });
  }
	
  private boolean handleSaveRecord(){
  	zapisPanel.getRecordTree().refreshView();  	  	
   String message = "Potvrda za snimanje zapisa:\n"+CurrRecord.saveRecordReport();
   Object[] options = {"Snimi", "Odustani"};
   int ret = JOptionPane.showOptionDialog(null, message , "Snimanje zapisa",  //$NON-NLS-1$
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[1]);
			if(ret==0){
				try {
					boolean ok = zapisPanel.getRecordTree().saveRecord();
					if(!ok){		
						String message1 = Messages.getString("EDITOR_SAVERECORDERROR"); //$NON-NLS-1$
						JOptionPane.showMessageDialog(BisisApp.getMainFrame(),message1,Messages.getString("EDITOR_SAVINGRECORDS"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
						return false;
					}		      
     JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Zapis je uspe\u0161no sa\u010Duvan!",Messages.getString("EDITOR_SAVINGRECORDS"),JOptionPane.INFORMATION_MESSAGE);		 //$NON-NLS-1$
     saveRecord.setEnabled(false);
     recordUpdated = false;
     setUploadEnabled(true);
     return true;
		   } catch (UValidatorException e) {  
		      JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$		      
		      return false;
		   }catch(Exception ex){
		   	JOptionPane.showMessageDialog(BisisApp.getMainFrame(),
		   			"Zapis nije sa\u010Duvan!\n"+ex.getClass(),Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$		   	
		   	log.fatal(ex);		   	
		   }
			}
			return false;
	}
	
  private void handleAddUField(){    
    zapisPanel.getFormatTree().addElementAction();    
  }	
	private void handleValidateRecord(){
		String mess = CurrRecord.validateRecord();
		JOptionPane.showMessageDialog(BisisApp.getMainFrame(),mess,Messages.getString("EDITOR_VALIDITYREPORT"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
	} 
  
   private void handleShowCardFrame() {
    CardFrame cardFrame = new CardFrame();
    cardFrame.setVisible(true);     
   } 
   
   public void handleChangeProcessType(ProcessType pt){  	 
  		 CurrFormat.changeProcessType(pt);
  		 zapisPanel.getFormatTree().refreshView();  	
  		 zapisPanel.getRecordTree().refreshView();
  		 initializeInventarPanel();
  		 zapisButton.doClick();      
   }   
   
   private void handleOpenProcessTypeDilaog(){
  	 ProcTypeChooserDialog dialog = new ProcTypeChooserDialog();
  	 dialog.setVisible(true);
   }
   
   private void handleKeys(Component comp,KeyEvent e){
     if(e.getKeyCode()==KeyEvent.VK_L && e.getModifiers()== InputEvent.CTRL_MASK)
       listiciButton.doClick();
     if(e.getKeyCode()==KeyEvent.VK_V && e.getModifiers()== InputEvent.CTRL_MASK)
       validateRecord.doClick();
     /*if(e.getKeyCode()==KeyEvent.VK_NUMPAD7)
       addUField.doClick();  */
     if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
       if(comp.equals(zapisPanel.getRecordTree())){
         if(!((BasicTreeUI)zapisPanel.getRecordTree().getUI()).isEditing(zapisPanel.getRecordTree()))
           handleCloseEditor();
       }else
         handleCloseEditor();
     }
     if(e.getKeyCode()== KeyEvent.VK_S){
    	 saveRecord.doClick();
     }
     if(e.getKeyCode()==KeyEvent.VK_F6 
    		 && e.getModifiers()== InputEvent.CTRL_MASK){    	 
    	 BisisApp.getMainFrame().selectNextInternalFrame();
     }
   }
  
  private void layoutPanels(){
    cardLayout = new CardLayout();
    panel = new JPanel();
    panel.setLayout(cardLayout);    
    this.getContentPane().setLayout(cardLayout);
    this.setLayout(cardLayout);
    getContentPane().setLayout(new BorderLayout());    
    this.getContentPane().add(toolBar,BorderLayout.NORTH);
    panel.add(zapisPanel,zapisPanel.getName());   
    panel.add(inventarPanel,inventarPanel.getName());  
    panel.add(uploadPanel,uploadPanel.getName());
    this.getContentPane().add(panel,BorderLayout.CENTER);     
  }
	
  private void closeEditor(){
  	sendFocus();
    this.setVisible(false);
    CurrRecord.unlockRecord();
    
  }
  
  private void sendFocus(){
//  prebacivanje fokusa na ostale prozore
  	if(BisisApp.getMainFrame().getHitListFrame()==null || 
  			!BisisApp.getMainFrame().getHitListFrame().isVisible()){
	  	BisisApp.getMainFrame().getSearchFrame().show();
	  	try {
				BisisApp.getMainFrame().getSearchFrame().setSelected(true);
			} catch (PropertyVetoException e) {		
			}
	  	BisisApp.getMainFrame().getSearchFrame().setDefaultFocus();
  	}else{  		
  		BisisApp.getMainFrame().getHitListFrame().show();
	  	try {
				BisisApp.getMainFrame().getHitListFrame().setSelected(true);
			} catch (PropertyVetoException e) {		
			}	   		
  	}  	
  }
  
	public boolean handleCloseEditor() {
    boolean closable = true;
    if(recordUpdated && isVisible()){      
    		String message = Messages.getString("EDITOR_SAVINGRECORDQUESTION"); //$NON-NLS-1$
    		Object[] options = { Messages.getString("EDITOR_YES"), Messages.getString("EDITOR_NO"),Messages.getString("EDITOR_CANCEL") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    		int ret = JOptionPane.showOptionDialog(null, message , Messages.getString("EDITOR_EXITEDITOR"),  //$NON-NLS-1$
    				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
    				null, options, options[2]);
        switch(ret){
        case 0: // da 
            boolean saved = handleSaveRecord();
            if(saved){              
              closable =  true;
            }else{
              closable =  false;
            }
            break;
        case 1: //ne 
            closable = true;
            break;
        case 2: //odustani 
          closable = false;
        }   
    }
    if (closable) closeEditor();
    return closable; 
  } 
	
	public void showInventarPanel(){
		inventarButton.doClick();
	}
  
  public RecordTree getRecordTree(){
    return zapisPanel.getRecordTree();
  }
  
  
  public InventarPanel getInventarPanel(){
    return inventarPanel;
  }

  public JButton getAddUField() {
    return addUField;
  }
  
  public ZapisPanel getZapisPanel(){
    return zapisPanel;
  }
  
  public JButton getAddUFieldButton(){
  	return addUField;
  }
  
  public void recordUpdated(){
    recordUpdated = true;
    procTypesButton.setEnabled(false);
    saveRecord.setEnabled(true);        
  }
  
  public void setRecordUpdated(boolean updated){
    recordUpdated = updated;    
  }
  
  public void setUploadEnabled(boolean enabled){
  	if(BisisApp.isFileMgrEnabled() && CurrRecord.record!=null && CurrRecord.record.getRecordID()>0){
  		uploadButton.setEnabled(enabled);
  	}else{
  		uploadButton.setEnabled(false);
  	}

  }
  
}

			
		




