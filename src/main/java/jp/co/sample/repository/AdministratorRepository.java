package jp.co.sample.repository;

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
 * administratorsテーブルを操作するリポジトリ
 * 
 * @author blues
 *
 */
@Repository
public class AdministratorRepository {
	
	/** JDBC操作に使用するテンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** JDBC操作に使用するテーブル名 */
	private static final String TABLE_NAME = "administrators";
	
	/** JDBC操作で取得した行をAdministratorドメインに詰めて返すオブジェクト */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAdress(rs.getString("mail_address"));
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
	
	/** 自動採番してテーブルに行を挿入するメソッド */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		insert.executeAndReturnKey(param);
	}
	
	/** メールアドレスとパスワードが一致した管理者情報を返すメソッド */
	public Administrator findByMailAddressAndPassWord(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM " + TABLE_NAME
					+ " WHERE mail_address = :mailAddress AND password = :password";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("mailAddress", mailAddress).addValue("password", password);
		Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		return administrator;
	}

}