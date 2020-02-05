package jp.co.sample.form;

/**
 * 管理者情報Insert用Formクラス
 * 
 * 管理者情報を挿入する際にフォームに記入された情報を受け取るクラス
 * 
 * @author blues
 *
 */
public class InsertAdministratorForm {
	
	
	/** 管理者氏名 */
	private String name;
	/** 管理者メールアドレス */
	private String mailAddress;
	/** 管理者パスワード */
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

}
