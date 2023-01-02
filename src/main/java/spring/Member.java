package spring;

public class Member {
	private String email;
	private String password;
	private String name;
	
    public Member(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	/* 
	public void changePassword(String oldPassword, String newPassword) {
		if(!password.equals(oldPassword)) {
			throw new WrongIdPasswordException();
		}
		this.password = newPassword;
	}
    */
}
