package jp.co.sample.domain;

/**
 * 管理者のドメイン
 * 
 * @author blues
 *
 */
public class Administrator {
	
	/**	管理者ID */
	private Integer id;
	/**	管理者名 */
	private String name;
	/**	管理者メールアドレス */
	private String mailAdress;
	/**	管理者パスワード */
	private String password;

	public Administrator() {
	}

	public Administrator(Integer id, String name, String mailAdress, String password) {
		super();
		this.id = id;
		this.name = name;
		this.mailAdress = mailAdress;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAdress() {
		return mailAdress;
	}

	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Administrator [id=" + id + ", name=" + name + ", mailAdress=" + mailAdress + ", password=" + password
				+ "]";
	}
	
}
