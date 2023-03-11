package memberManagement;

public class Login {

	private int userCode;
	private String id;
	private String password;
	private String name;
	private String datebirth;
	private String gender;
	private String email;
	private String phoneNumber;

	public Login(String id, String password, String name, String datebirth, String gender, String email,
			String phoneNumber) {
		this(0, id, password, name, datebirth, gender, email, phoneNumber);
	}

	public Login(int userCode, String id, String password, String name, String datebirth, String gender, String email,
			String phoneNumber) {
		super();
		this.userCode = userCode;
		this.id = id;
		this.password = password;
		this.name = name;
		this.datebirth = datebirth;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public int getUserCode() {
		return userCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatebirth() {
		return datebirth;
	}

	public void setDatebirth(String datebirth) {
		this.datebirth = datebirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
