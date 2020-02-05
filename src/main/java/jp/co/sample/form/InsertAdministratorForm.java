package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 管理者情報Insert用Formクラス.
 * 
 * 管理者情報を挿入する際にフォームに記入された情報を受け取るクラス
 * 
 * @author katsuya.fujishima
 *
 */
public class InsertAdministratorForm {
	
	
	/** 管理者氏名 */
	@NotEmpty(message="氏名は必須です")
	private String name;
	/** 管理者メールアドレス */
	@NotEmpty(message="メールアドレスは必須です")
	@Email(message="メールアドレスの形式が不正です")
	private String mailAddress;
	/** 管理者パスワード */
	@NotEmpty(message="パスワードは必須です")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,16}$"
	, message="半角英大文字、半角英小文字、半角数字をそれぞれ1文字以上使用し、8文字以上16文字以下で入力してください")
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
