package org.alan.ml.services;

import org.alan.ml.domain.User;
import org.alan.ml.domain.UserRole;

public class LoginService {
	public boolean authenticate(String userName,String password) {
		if (userName==null || password==null) {
			return false;
		} else if (userName.isEmpty() || password.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	public User getUserByUserName(String userName){
		User user = new User();
		user.setUserName("admin");
		user.setUserRole(UserRole.ADMIN.getRole());
		return user;
	}
}
