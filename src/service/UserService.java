package service;

public class UserService {
	public UserService() {
		System.out.println("userService 实例化了");
	}

	public boolean login(String username,String password) {
		if("zhangsan".equals(username) && "123456".equals(password)) {
			return true;
		}else {
			return false;
		}
	}
}
