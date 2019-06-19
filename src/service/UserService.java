package service;

public class UserService {

	public boolean login(String username,String password) {
		if("zhangsan".equals(username) && "123456".equals(password)) {
			return true;
		}else {
			return false;
		}
	}
}
