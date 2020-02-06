package jp.co.sample.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author katsuya.fujishima
 *
 */
@Repository
public class AdministratorRepository {
	
	/** JDBC操作に使用するテンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** JDBC操作に使用するテーブル名 */
	private static final String TABLE_NAME = "administrators";
	
	/** Administratorオブジェクトを生成するローマッパー */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	/** IDを自動採番するためのインサートオブジェクト */
	private SimpleJdbcInsert insert;
	
	/** INSERT時IDを自動採番するためメソッド */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName(TABLE_NAME);
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * 自動採番してテーブルに行を挿入するメソッド.
	 * 
	 * @param administrator 管理者用ドメイン
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
			insert.executeAndReturnKey(param);
	}
	
	/**
	 * メールアドレスとパスワードが一致した管理者情報を返すメソッド.
	 * 
	 * @param mailAddress 管理者メールアドレス
	 * @param password 管理者パスワード
	 * @return 管理者情報のドメイン
	 */
	public Administrator findByMailAddressAndPassWord(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM " + TABLE_NAME
					+ " WHERE mail_address = :mailAddress AND password = :password";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("mailAddress", mailAddress).addValue("password", password);
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if(administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}
	
	/**
	 * 全件検索メソッド.
	 * 
	 * 登録メールアドレスが一意かをチェックするのに使用する
	 * 
	 * @return 管理者全件のリスト
	 */
	public List<Administrator> findAll() {
		String sql = "SELECT id, name, mail_address, password FROM " + TABLE_NAME;
		List<Administrator> AdministratorList = template.query(sql, ADMINISTRATOR_ROW_MAPPER);
		return AdministratorList;
	}

}
