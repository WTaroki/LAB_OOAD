package controller;

import java.util.ArrayList;

import database.Connect;
import model.User;
import view.ViewAccount;

public class UserController {

	public static Boolean validateUsername(String username) {
		
		if(username.isBlank()) {
			return false;
			
		} else if(!User.searchExistingUsername(username)) {
			return false;
			
		}
		
		return true;
	}
	
	public static Boolean validateEmail(String email) {
		
		if(!email.contains("@") || !User.searchExistingEmail(email)) {
			return false;
			
		}
		
		return true;
		
	}
	
	public static Boolean validatePassword(String password, String confirmPassword) {
		
		if(password.length() < 6 || !password.matches("^[a-zA-Z0-9]+$") || !password.equals(confirmPassword)){
			return false;
			
		}
		
		return true;
	}
	
	public static Boolean addUser(String username, String email, String password, String confirmPassword, String role) {
		
	    Boolean usernameValid = validateUsername(username);
	    Boolean emailValid = validateEmail(email);
	    Boolean passwordValid = validatePassword(password, confirmPassword);
	    
	    if(usernameValid && emailValid && passwordValid) {
	    	
	    	User.addUser(username, email, password, role);
	    	
	    	return true;
	    }
	    
	    return false;
  
	}
	
	
	private User currUser;
	private Boolean isAdmin; 
	
	// Account
	private ArrayList<User> users;
	
	
	public UserController() {
		
		// Dummy User
		this.currUser = new User(1, "admin", "admin@gmail.com", "admin", "Admin");
//		currUser = new User(1, "user1", "user1@gmail.com", "user1", "User");
		this.isAdmin = currUser.getRole().equals("Admin") ? true : false;
		
		this.users = new ArrayList<>();
	}
	
	public Scene showFanAccount() {
		this.users = User.getAllUsersInRole("Fan");
		String titleString = "Fan Accounts";
		
		ViewAccount view = new ViewAccount(this.users, titleString, this.isAdmin);
		
		view.setButtonClickHandler(() -> {
			deleteSelectedUser(view.getSelectedUsers());
			this.users = User.getAllUsersInRole("Fan");
			view.refreshUserList(this.users);
        });
        
        return view.getViewScene();

	}
	
	public Scene showInfluencerAccount() {
		this.users = User.getAllUsersInRole("Influencer");
		String titleString = "Influencer Accounts";
		
		ViewAccount view = new ViewAccount(this.users, titleString, this.isAdmin);
		
		view.setButtonClickHandler(() -> {
			deleteSelectedUser(view.getSelectedUsers());
			this.users = User.getAllUsersInRole("Influencer");
			view.refreshUserList(this.users);
        });
        
        
        return view.getViewScene();
	}
	
	public Scene showVendorAccount() {
		this.users = User.getAllUsersInRole("Vendor");
		String titleString = "Vendor Accounts";
		
		ViewAccount view = new ViewAccount(this.users, titleString, this.isAdmin);
		
		view.setButtonClickHandler(() -> {
			deleteSelectedUser(view.getSelectedUsers());
			this.users = User.getAllUsersInRole("Vendor");
			view.refreshUserList(this.users);
        });
        
        
        return view.getViewScene();
		
	}
	
	public void deleteSelectedUser(ObservableList<User> users) {
		for (User user : users) {
			User.deleteUser(user.getUserId());	
		}	
	}
	
}
