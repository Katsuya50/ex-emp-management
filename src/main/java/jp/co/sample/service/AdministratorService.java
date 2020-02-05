package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者用serviceクラス
 * 
 * @author blues
 *
 */
@Service
@Transactional
public class AdministratorService {
	
	/** リポジトリクラスの参照情報を注入して定義 */
	@Autowired
	private AdministratorRepository repository;
	
	
	
}
