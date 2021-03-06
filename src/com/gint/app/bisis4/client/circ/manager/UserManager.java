package com.gint.app.bisis4.client.circ.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.time.StopWatch;


import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.commandservice.Service;
import com.gint.app.bisis4.client.circ.commands.ArchiveUserCommand;
import com.gint.app.bisis4.client.circ.commands.DeleteObjectCommand;
import com.gint.app.bisis4.client.circ.commands.DischargeBookCommand;
import com.gint.app.bisis4.client.circ.commands.GetAllCommand;
import com.gint.app.bisis4.client.circ.commands.GetAllUserDataCommand;
import com.gint.app.bisis4.client.circ.commands.GetChargedUserCommand;
import com.gint.app.bisis4.client.circ.commands.GetCtlgNosCommand;
import com.gint.app.bisis4.client.circ.commands.GetGroupCommand;
import com.gint.app.bisis4.client.circ.commands.GetLastUserIDCommand;
import com.gint.app.bisis4.client.circ.commands.GetLendingCommand;
import com.gint.app.bisis4.client.circ.commands.GetMembershipCommand;
import com.gint.app.bisis4.client.circ.commands.GetObjectCommand;
import com.gint.app.bisis4.client.circ.commands.GetPinCommand;
import com.gint.app.bisis4.client.circ.commands.GetSysIDCommand;
import com.gint.app.bisis4.client.circ.commands.GetUserCommand;
import com.gint.app.bisis4.client.circ.commands.SaveObjectCommand;
import com.gint.app.bisis4.client.circ.commands.SaveUserCommand;
import com.gint.app.bisis4.client.circ.model.Configs;
import com.gint.app.bisis4.client.circ.model.Duplicate;
import com.gint.app.bisis4.client.circ.model.EduLvl;
import com.gint.app.bisis4.client.circ.model.Groups;
import com.gint.app.bisis4.client.circ.model.Languages;
import com.gint.app.bisis4.client.circ.model.Lending;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.MmbrTypes;
import com.gint.app.bisis4.client.circ.model.Organization;
import com.gint.app.bisis4.client.circ.model.Picturebooks;
import com.gint.app.bisis4.client.circ.model.Primerci;
import com.gint.app.bisis4.client.circ.model.Signing;
import com.gint.app.bisis4.client.circ.model.Sveske;
import com.gint.app.bisis4.client.circ.model.UserCategs;
import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.model.Warnings;
import com.gint.app.bisis4.client.circ.view.Group;
import com.gint.app.bisis4.client.circ.view.Membership;
import com.gint.app.bisis4.client.circ.view.Report;
import com.gint.app.bisis4.client.circ.view.SearchBooks;
import com.gint.app.bisis4.client.circ.view.SearchUsers;
import com.gint.app.bisis4.client.circ.view.User;
import com.gint.app.bisis4.client.circ.view.UserData;
import com.gint.app.bisis4.client.circ.common.Utils;

public class UserManager {
	
  private Users userModel;
  private Groups groupModel;
  private String chargedUser;
  private String chargeBook = "";
  private List warnings = null;
  private String env = null;
  private String validator = null;
  private Service service;
  private Service serviceArchive;
  private List children = null;
  private List removedchildren = null;
	
	public UserManager(){		
		service = Cirkulacija.getApp().getService();
		serviceArchive = Cirkulacija.getApp().getServiceArchive();
		children = new ArrayList();
		removedchildren = new ArrayList();
	}
	
	public String saveUser(User user){
		if (user.getDirty() || !children.isEmpty() || !removedchildren.isEmpty()){
			SaveUserCommand saveUser = new SaveUserCommand();
			saveUser.setUserID(user.getMmbrship().getUserID());
			if (user.getDirty()){
				GetSysIDCommand sysidcomm = new GetSysIDCommand(user.getMmbrship().getUserID());
				sysidcomm = (GetSysIDCommand)service.executeCommand(sysidcomm);
				if (sysidcomm == null)
					return "Gre\u0161ka u konekciji s bazom podataka!";
				Integer sysid = sysidcomm.getSysID();
				if (userModel == null){
					if (sysid != null)
						return "Broj korisnika vec postoji!";
					userModel = new Users();		
				} else {
				  if (sysid != null && sysid.intValue() != userModel.getSysId())
					  return "Broj korisnika vec postoji!";
				}
				userModel = toObjectModel(user, userModel);
			}
			if (BisisApp.getINIFile().getBoolean("pincode", "enabled") && (userModel.getPass() == null || userModel.getPass().equals(""))){
				Integer sysid = new Integer(0);
				String pin = Utils.generatePin();
//				while (sysid != null){
//					pin = Utils.generatePin();
//					GetPinCommand pincomm = new GetPinCommand(pin);
//					pincomm = (GetPinCommand)service.executeCommand(pincomm);
//					if (pincomm == null)
//						return "Gre\u0161ka u konekciji s bazom podataka!";
//					sysid = pincomm.getSysID();
//				}
				userModel.setPass(pin);
				user.getUserData().setPinCode(pin);
			}
			saveUser.setUser(userModel);
			if (!children.isEmpty()){
				saveUser.setChildren(children);
				saveUser.setBooks(Cirkulacija.getApp().getRecordsManager().getList());
			}
			if (!removedchildren.isEmpty()){
				saveUser.setRemovedChildren(removedchildren);
			}
			saveUser = (SaveUserCommand)service.executeCommand(saveUser);
			if (saveUser == null)
				return "Gre\u0161ka u konekciji s bazom podataka!";
			String message = "";
			if (saveUser.isSaved()){
				GetUserCommand getUser = new GetUserCommand(userModel.getUserId());
				userModel = ((GetUserCommand)service.executeCommand(getUser)).getUser();
				loadUser(user, userModel);
				//userModel = saveUser.getUser();
				//refreshInfo(user, userModel);
				message = "ok";
			} else {
				if (saveUser.getStaleID() != null){
					GetObjectCommand getObject = new GetObjectCommand(saveUser.getStaleID(), saveUser.getStaleName());
					Object book = ((GetObjectCommand)service.executeCommand(getObject)).getObject();
					if (book instanceof Primerci){
				        message = "Status primerka " + ((Primerci)book).getInvBroj() + ": " 
				          + ((Primerci)book).getStatusPrimerka().getStatusOpis();
				      } else {
				        message = "Status primerka " + ((Sveske)book).getInvBr() + ": " 
				          + ((Sveske)book).getStatusPrimerka().getStatusOpis();
				      }
				} else {
					message = saveUser.getMessage();
				}
				GetUserCommand getUser = new GetUserCommand(userModel.getUserId());
				userModel = ((GetUserCommand)service.executeCommand(getUser)).getUser();
				loadUser(user, userModel);
			}
			Cirkulacija.getApp().getRecordsManager().getList().clear();
			children.clear();
			removedchildren.clear();
			return message;
		} else {
			return "ok";
		}
	}
	
	public void releaseUser(){
		userModel = null;
		chargeBook = "";
		Cirkulacija.getApp().getMainFrame().setRequestedPanel(0);
	}
  
  public boolean gotUser(){
    return userModel != null;
  }
  
  public String saveGroup(Group group){
    if (groupModel == null){
      groupModel = new Groups();  
    }
    groupModel = toObjectModel(group, groupModel);
    SaveObjectCommand saveGroup = new SaveObjectCommand(groupModel);
    saveGroup = (SaveObjectCommand)service.executeCommand(saveGroup);
    if (saveGroup == null)
		return "Gre\u0161ka u konekciji s bazom podataka!";
    
    if (saveGroup.isSaved()){
    	return "ok";
    } else {
    	return saveGroup.getMessage();
    }
  }
  
  public void releaseGroup(){
    groupModel = null;
  }
  
  public void initialiseUser(User user){
    userModel = new Users();
    user.getMmbrship().setTableModel(userModel.getSignings());
    user.getLending().setTableModel(userModel.getLendings());
    user.getUserData().setDupTableModel(userModel.getDuplicates());
  }
  
  public void showUser(User user, String userID){
//  	StopWatch clock = new StopWatch();
//    clock.start();
	GetUserCommand getUser = new GetUserCommand(userID);
	getUser = (GetUserCommand)service.executeCommand(getUser);
	if (getUser != null){
		userModel = getUser.getUser();
	    if (userModel != null){
	    	children.clear();
	    	removedchildren.clear();
	    	Cirkulacija.getApp().getRecordsManager().getList().clear();
	    	loadUser(user, userModel);
	    }
	}
//    clock.stop();
//    System.out.println("clock:"+clock.getTime());
  }
  
  public int showChargedUser(User user){
    return getUser(user, null, chargedUser);
  }
  
  public void chargeUser(String ctlgno){
    if (userModel == null){
      chargeBook = ctlgno;
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(3);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    } else {
      Cirkulacija.getApp().getMainFrame().getUserPanel().getLending().lendBook(ctlgno);
      Cirkulacija.getApp().getMainFrame().previousTwoPanels();
      //Cirkulacija.getApp().getMainFrame().showPanel("userPanel");
    }
  }
  
  public String archiveUser(User user){
	  GetAllUserDataCommand getUserData = new GetAllUserDataCommand(user.getMmbrship().getUserID());
	  getUserData = (GetAllUserDataCommand)service.executeCommand(getUserData);
		if (getUserData != null){
			Users userdata = getUserData.getUser();
			ArchiveUserCommand archiveUser = new ArchiveUserCommand();
			archiveUser.setUserID(user.getMmbrship().getUserID());
			archiveUser.setUser(userdata);
			List<Object> list = new ArrayList<Object>();
			list.addAll(userdata.getSignings());
			list.addAll(userdata.getAlllendings());
			list.addAll(userdata.getDuplicates());
			list.addAll(userdata.getPicturebooks());
			for (Object lending: userdata.getAlllendings()){
				list.addAll(((Lending)lending).getWarningses());
			}
			archiveUser.setChildren(list);
			archiveUser = (ArchiveUserCommand)serviceArchive.executeCommand(archiveUser);
			if (archiveUser == null){
				return "Gre\u0161ka u konekciji s bazom podataka!";
			}
			if (archiveUser.isSaved()){
				DeleteObjectCommand deleteUser = new DeleteObjectCommand(userModel);
				deleteUser = (DeleteObjectCommand)service.executeCommand(deleteUser);
				if (deleteUser.isSaved()){
					return "ok";
				}else{
					return deleteUser.getMessage();
				}
				
			} else {
				return archiveUser.getMessage();
			}
		} else {
			return "Gre\u0161ka u konekciji s bazom podataka!";
		}
  }
	
  public int getUser(User user, Group group, String userID){
    int found = 0;
//  StopWatch clock = new StopWatch();
//  clock.start();
    GetUserCommand getUser = new GetUserCommand(userID);
    getUser = (GetUserCommand)service.executeCommand(getUser);
    if (getUser != null){
		userModel = getUser.getUser();
		if (userModel != null){
			children.clear();
			removedchildren.clear();
			Cirkulacija.getApp().getRecordsManager().getList().clear();
			loadUser(user, userModel);
			found = 1;
			return found;
		}
//  clock.stop();
//  System.out.println("clock:"+clock.getTime());
    }
    
    GetGroupCommand getGroup = new GetGroupCommand(userID);
    getGroup = (GetGroupCommand)service.executeCommand(getGroup);
    if (getGroup != null){
    	groupModel = getGroup.getUser();
    	if (groupModel != null){
    		group.loadGroup(groupModel.getUserId(), groupModel.getInstName(), groupModel.getSignDate(), groupModel.getAddress(), groupModel.getCity(), Utils.getString(groupModel.getZip()), 
    		groupModel.getPhone(), groupModel.getEmail(), groupModel.getFax(), groupModel.getSecAddress(), Utils.getString(groupModel.getSecZip()), groupModel.getSecCity(),
            groupModel.getSecPhone(), groupModel.getContFname(), groupModel.getContLname(), groupModel.getContEmail());
    		found = 2;
    		return found;
    	}
    }
    return found;
  }
  
  private void loadUser(User user, Users userModel){
    boolean blocked = false;
    String blockedInfo = "";
    if (userModel.getBlockReasons()!=null && !"".equals(userModel.getBlockReasons())){
      blockedInfo = "Blokirano: "+userModel.getBlockReasons();
      blocked = true;  
    }
    
    Iterator it = userModel.getDuplicates().iterator();
    String dupno = "";
    while (it.hasNext()){
      Duplicate dup = (Duplicate)it.next();
      dupno = "Duplikat " + dup.getDupNo();
    }
    
    user.getUserData().loadUser(userModel.getFirstName(), userModel.getLastName(), userModel.getParentName(), 
        userModel.getAddress(), Utils.getString(userModel.getZip()), userModel.getCity(), userModel.getPhone(), 
        userModel.getEmail(), userModel.getGender(), userModel.getAge(), userModel.getSecAddress(),
        userModel.getSecCity(), Utils.getString(userModel.getSecZip()), userModel.getSecPhone(), userModel.getJmbg(),
        userModel.getDocId().intValue(), userModel.getDocNo(), userModel.getDocCity(), userModel.getCountry(), 
        userModel.getTitle(), userModel.getOccupation(), userModel.getIndexNo(), Utils.getString(userModel.getClassNo()),
        userModel.getOrganization(), userModel.getEduLvl(), userModel.getLanguages(), userModel.getNote(),
        userModel.getInterests(), userModel.getWarningInd().intValue(), blocked, userModel.getBlockReasons(), userModel.getDuplicates(), userModel.getPass());
    
    user.getMmbrship().loadUser(userModel.getUserId(), userModel.getMmbrTypes(), userModel.getUserCategs(), userModel.getGroups(), userModel.getSignings());

    Date maxDate = null;
    Date date = null;
    it = userModel.getSignings().iterator();
    while (it.hasNext()){
      date = ((Signing)it.next()).getUntilDate();
      if (date != null && (maxDate == null || maxDate.before(date))){
        maxDate = date;
      }
    }
    
    if (maxDate != null && maxDate.before(new Date())){
      if (!blockedInfo.equals(""))
        blockedInfo = blockedInfo + ", ";
      blockedInfo = blockedInfo + "Istekla \u010dlanarina";
    }
    
    warnings = new ArrayList<Warnings>();
    Set<Lending> lendings = userModel.getLendings();
    for (Lending lend : lendings){
      Set<Warnings> warns = lend.getWarningses();
      for (Warnings warn : warns){
        warnings.add(warn);
      }
    }
    
    user.getLending().loadUser(userModel.getUserId(), userModel.getFirstName(), userModel.getLastName(), maxDate, userModel.getNote(), dupno, blockedInfo, userModel.getLendings(), !warnings.isEmpty());
    
    user.setDirty(false);
    
    if (!chargeBook.equals("")){
      user.getLending().lendBook(chargeBook);
      chargeBook = "";
      user.setDirty(true);
    }
    
  }
  
  public void refreshInfo(User user, Users userModel){
  	boolean blocked = false;
    String blockedInfo = "";
    if (userModel.getBlockReasons()!=null && !"".equals(userModel.getBlockReasons())){
      blockedInfo = "Blokirano: "+userModel.getBlockReasons();
      blocked = true;  
    }
    
    Iterator it = userModel.getDuplicates().iterator();
    String dupno = "";
    while (it.hasNext()){
      Duplicate dup = (Duplicate)it.next();
      dupno = "Duplikat " + dup.getDupNo();
    }
    
    Date maxDate = null;
    Date date = null;
    it = userModel.getSignings().iterator();
    while (it.hasNext()){
      date = ((Signing)it.next()).getUntilDate();
      if (date != null && (maxDate == null || maxDate.before(date))){
        maxDate = date;
      }
    }
    
    if (maxDate != null && maxDate.before(new Date())){
      if (!blockedInfo.equals(""))
        blockedInfo = blockedInfo + ", ";
      blockedInfo = blockedInfo + "Istekla \u010dlanarina";
    }
    
    user.getLending().refreshInfo(userModel.getUserId(), userModel.getFirstName(), userModel.getLastName(), maxDate, userModel.getNote(), dupno, blockedInfo);
    
  }
	
	
  public void loadCombos(User user) throws Exception {
	GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(EduLvl.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getUserData().loadEduLvl(getAll.getList());
	getAll.setArg(Languages.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getUserData().loadLanguage(getAll.getList());
	getAll.setArg(Organization.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getUserData().loadOrganization(getAll.getList());
	getAll.setArg(Groups.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getMmbrship().loadGroups(getAll.getList());
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getMmbrship().loadLocation(getAll.getList());
	user.getMmbrship().loadBranchID(getAll.getList());
	getAll.setArg(MmbrTypes.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getMmbrship().loadMmbrType(getAll.getList());
	getAll.setArg(UserCategs.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getMmbrship().loadUserCateg(getAll.getList());
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
	user.getLending().loadLocation(getAll.getList());
  }
  
  public void loadCombos(Group group) throws Exception {
  	GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    group.loadBranchID(getAll.getList());
  }
  
  public void loadCombos(SearchUsers searchusers) throws Exception {
  	GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    searchusers.loadCmbLoc1(getAll.getList());
    searchusers.loadCmbLoc2(getAll.getList());
  }
  
  public void loadCombos(SearchBooks searchusers) throws Exception {
  	GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    searchusers.loadCmbLocL(getAll.getList());
    searchusers.loadCmbLocR(getAll.getList());
  }
  
  public void loadCombos(Report report) throws Exception {
  	GetAllCommand getAll = new GetAllCommand();
	getAll.setArg(Location.class);
	getAll = (GetAllCommand)service.executeCommand(getAll);
	if (getAll == null)
		throw new Exception("Gre\u0161ka u konekciji s bazom podataka!");
    report.loadCmbLocation(getAll.getList());
    
  }
	
	private Users toObjectModel(User user, Users userModel){
		UserData data = user.getUserData();
		userModel.setAddress(data.getAddress().trim());
		userModel.setAge(data.getAge());
		userModel.setCity(data.getCity().trim());
		userModel.setClassNo(Utils.getInteger(data.getClassNo()));
		userModel.setCountry(data.getCountry().trim());
		userModel.setDocCity(data.getDocCity().trim());
		userModel.setDocId(Integer.valueOf(data.getDocId()));
		userModel.setDocNo(data.getDocNo().trim());
		userModel.setEduLvl(data.getEduLvl());
		userModel.setEmail(data.getEmail().trim());
		userModel.setFirstName(data.getFirstName().trim());
		userModel.setGender(data.getGender());
		userModel.setIndexNo(data.getIndexNo().trim());
		userModel.setInterests(data.getInterests().trim());
		userModel.setJmbg(data.getJmbg().trim());
		userModel.setLanguages(data.getLanguages());
		userModel.setLastName(data.getLastName().trim());
		userModel.setNote(data.getNote().trim());
		userModel.setOccupation(data.getOccupation().trim());
		userModel.setOrganization(data.getOrganization());
		userModel.setParentName(data.getParentName().trim());
		userModel.setPhone(data.getPhone().trim());
		userModel.setSecAddress(data.getTmpAddress().trim());
		userModel.setSecCity(data.getTmpCity().trim());
		userModel.setSecPhone(data.getTmpPhone().trim());
		userModel.setSecZip(Utils.getInteger(data.getTmpZip()));
		userModel.setTitle(data.getTitle().trim());
		userModel.setWarningInd(Integer.valueOf(data.getWarning()));
		userModel.setZip(Utils.getInteger(data.getZip()));
    if (data.getBlocked()){
      userModel.setBlockReasons(data.getBlockedReason().trim());
    } else {
      userModel.setBlockReasons("");
    }
		
		Membership mmbrship = user.getMmbrship();
		userModel.setGroups(mmbrship.getGroup());
		userModel.setUserId(mmbrship.getUserID());
		userModel.setMmbrTypes(mmbrship.getMmbrType());
		userModel.setUserCategs(mmbrship.getUserCateg());
		
		return userModel;
	}
  
  private Groups toObjectModel(Group group, Groups groupModel){
    groupModel.setAddress(group.getAddress().trim());
    groupModel.setCity(group.getCity().trim());
    groupModel.setContEmail(group.getContactEmail().trim());
    groupModel.setContFname(group.getContactFirstName().trim());
    groupModel.setContLname(group.getContactLastName().trim());
    groupModel.setEmail(group.getEmail().trim());
    groupModel.setFax(group.getFax().trim());
    groupModel.setInstName(group.getOrganization().trim());
    groupModel.setPhone(group.getPhone().trim());
    groupModel.setSecAddress(group.getTmpAddress().trim());
    groupModel.setSecCity(group.getTmpCity().trim());
    groupModel.setSecPhone(group.getTmpPhone().trim());
    groupModel.setSecZip(Utils.getInteger(group.getTmpZip()));
    groupModel.setSignDate(group.getSignDate());
    groupModel.setUserId(group.getUserID());
    groupModel.setZip(Utils.getInteger(group.getZip()));
    return groupModel;
  }
  
  public void addSigning(Signing sig){
    userModel.addSigning(sig);
    if (!children.contains(sig)){
    	children.add(sig);
    }
  }
  
  public void removeSigning(Signing sig){
    userModel.removeSigning(sig);
    if (children.contains(sig)){
    	children.remove(sig);
    }
    removedchildren.add(sig);
  }
  
  public void updateSigning(Signing sig){
  	if (!children.contains(sig)){
    	children.add(sig);
    }
  }
  
  public void addLending(Lending lend){
    userModel.addLending(lend);
    if (!children.contains(lend)){
    	 children.add(lend);
    }
   
  }
  
  public void updateLending(Lending lend){
  	if (!children.contains(lend)){
   	  children.add(lend);
  	}
  }
  
  public void addDuplicate(Duplicate dup){
    userModel.addDuplicate(dup);
    if (!children.contains(dup)){
    	children.add(dup);
    }
  }
  
  public void removeDuplicate(Duplicate dup){
    userModel.removeDuplicate(dup);
    if (children.contains(dup)){
    	children.remove(dup);
    }
    removedchildren.add(dup);
  }
  
  public void updateDuplicate(Duplicate dup){
  	if (!children.contains(dup)){
    	 children.add(dup);
    }
  }
  
  public void addPicturebooks(Picturebooks pic){
    userModel.addPicturebooks(pic);
    if (!children.contains(pic)){
    	children.add(pic);
   }
  }
  
  public void updateWarning(Warnings warn){
  	if (!children.contains(warn)){
    	children.add(warn);
    }
  }
	
  public Double getMembership(MmbrTypes mt, UserCategs uc){ 
  	GetMembershipCommand getMmbrship = new GetMembershipCommand(mt, uc);
  	getMmbrship = (GetMembershipCommand)service.executeCommand(getMmbrship);
  	if (getMmbrship == null)
  		//return new Double(0);
  		return null;
    com.gint.app.bisis4.client.circ.model.Membership mmbrship = getMmbrship.getMembership();
    if (mmbrship != null){
      return mmbrship.getCost();
    } else {
      return null;
    }
  }
  
  public String getUserId(String location){
    String loc = location;
    if (loc.equals(""))
      loc = "0";
      
    GetLastUserIDCommand getLastID = new GetLastUserIDCommand(Integer.valueOf(loc));
    getLastID = (GetLastUserIDCommand)service.executeCommand(getLastID);
    if (getLastID == null)
    	return null;
    int last = getLastID.getLastID();
    String userId = Utils.makeUserId(loc, Integer.toString(last));
    while (existsUser(userId)){
    	getLastID = (GetLastUserIDCommand)service.executeCommand(getLastID);
    	if (getLastID == null)
        	return null;
    	last = getLastID.getLastID();
    	userId = Utils.makeUserId(loc, Integer.toString(last));
    }
    return Integer.toString(last);

  }
  
  public boolean existsUser(String userID){
  	GetSysIDCommand getSysID = new GetSysIDCommand(userID);
  	getSysID = (GetSysIDCommand)service.executeCommand(getSysID);
  	if (getSysID == null)
  		return true;
    Integer sysid = getSysID.getSysID();
    if (sysid != null)
      return true;
    return false;
  }
  
  public String getChargedUser(String ctlgno){
  	GetChargedUserCommand getUser = new GetChargedUserCommand(ctlgno);
  	getUser = (GetChargedUserCommand)service.executeCommand(getUser);
  	if (getUser == null)
  		return "";
    Object[] user = getUser.getUser();
    String result = "";
    if (user != null){
      chargedUser = (String)user[0];
      result = (user[0] != null ? (String)user[0]+", ": "")+(user[1] != null ? (String)user[1]+" ": "")+(user[2] != null ? (String)user[2]: "") ;
    }
    return result;
  }
  
  public boolean dischargeUser(String ctlgno){
  	GetLendingCommand getLending = new GetLendingCommand(ctlgno);
  	getLending = (GetLendingCommand)service.executeCommand(getLending);
  	if (getLending == null)
  		return false;
    Lending lending = getLending.getLending();
    if (lending != null){
	    lending.setReturnDate(new Date());
	    lending.setLibrarianReturn(Cirkulacija.getApp().getLibrarian().getUsername());
    }
    Object primerak = Cirkulacija.getApp().getRecordsManager().changeStanje(ctlgno);
    DischargeBookCommand discharge = new DischargeBookCommand(lending, primerak);
    discharge = (DischargeBookCommand)service.executeCommand(discharge);
    return discharge.isSaved();
  }
  
  public List getCtlgNos(Date startDateL, Date endDateL, Location locationL, 
                                  Date startDateR, Date endDateR, Location locationR){
    Date startL = null;
    Date endL = null;
    if (startDateL != null){
      startL = Utils.setMinDate(startDateL);
      if (endDateL != null){
        endL = Utils.setMaxDate(endDateL);
      }else{
        endL = Utils.setMaxDate(startDateL);
      }
    }
    
    Location location;
    if (locationL != null){
      location = locationL;
    }else{
      location = locationR;
    }
    Date startR = null;
    Date endR = null;
    if (startDateR != null){
      startR = Utils.setMinDate(startDateR);
      if (endDateR != null){
        endR = Utils.setMaxDate(endDateR);
      }else{
        endR = Utils.setMaxDate(startDateR);
      }
    }
    
    GetCtlgNosCommand getCtlgNos = new GetCtlgNosCommand(startL, endL, location, startR, endR);
    getCtlgNos = (GetCtlgNosCommand)service.executeCommand(getCtlgNos);
    if (getCtlgNos == null)
    	return null;
    return getCtlgNos.getList();
  }
  
  public List getWarnings(){
    return warnings;
  }
  
  public Set getPicturebooks(){
  	if (userModel != null){
  		return userModel.getPicturebooks();
  	} else {
  		return null;
  	}
  }
  
  public String getEnvFile(){
  	if (env == null){
  		try{
  			GetAllCommand getAll = new GetAllCommand();
  			getAll.setArg(Configs.class);
  			getAll = (GetAllCommand)service.executeCommand(getAll);
  			if (getAll == null)
  				return null;
	  		List list = getAll.getList();
	  		if (list != null){
		  		Iterator it = list.iterator();
		  		while (it.hasNext()){
		  			Configs config = (Configs)it.next();
		  			if (config.getName().equals("circ-options")){
		  				env = config.getText();
		  			}
		  			if (config.getName().equals("circ-validator")){
		  				validator = config.getText();
		  			}
		  		}
	  		}
  		}catch (Exception e) {
  		}
  	}
  	return env;
  }
  
  public String getValidatorFile(){
  	if (validator == null){
  		try{
  			GetAllCommand getAll = new GetAllCommand();
  			getAll.setArg(Configs.class);
  			getAll = (GetAllCommand)service.executeCommand(getAll);
  			if (getAll == null)
  				return null;
	  		List list = getAll.getList();
	  		if (list != null){
		  		Iterator it = list.iterator();
		  		while (it.hasNext()){
		  			Configs config = (Configs)it.next();
		  			if (config.getName().equals("circ-options")){
		  				env = config.getText();
		  			}
		  			if (config.getName().equals("circ-validator")){
		  				validator = config.getText();
		  			}
		  		}
	  		}
  		}catch (Exception e) {
  		}
  	}
  	return validator;
  }

}
