package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者用serviceクラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository repository;
	
	/**
	 * 管理者情報をテーブルに挿入する処理.
	 * 
	 * @param administrator
	 */
	public void insert(Administrator administrator) {
		repository.insert(administrator);
	}
	
	/**
	 * 管理者のログイン処理.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return メールアドレスとパスワードから得た管理者情報
	 */
	public Administrator login(String mailAddress, String password) {
		return repository.findByMailAddressAndPassWord(mailAddress, password);
	}
	
}
