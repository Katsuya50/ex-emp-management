package jp.co.sample.repository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ
 * 
 * @author blues
 *
 */
@Repository
public class EmployeeRepository {
	
	/** JDBC操作に使用するテンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** JDBC操作に使用するテーブル名 */
	private static final String TABLE_NAME = "employees";
	
	/** JDBC操作で取得した行をEmployeeドメインに詰めて返すオブジェクト */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("iamge"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};
	
	/** テーブル内の従業員情報を全て返すメソッド */
	public List<Employee> findAll() {
		String sql = "SELECT id, name, iamge, gender, hire_date, mail_address, zip_code, address, "
					+ "telephone, salary, characteristics, dependents_count "
					+ "FROM " + TABLE_NAME + " ORDER BY hire_date";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	/** idが一致した従業員情報を返すメソッド */
	public Employee load(Integer id) {
		String sql = "SELECT id, name, iamge, gender, hire_date, mail_address, zip_code, address, "
				+ "telephone, salary, characteristics, dependents_count "
				+ "FROM " + TABLE_NAME + " WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	
	/** idが一致した従業員の不要人数を変更するメソッド */
	public void update(Employee employee) {
		String sql = "UPDATE " + TABLE_NAME + " SET dependents_count = :dependentsCount";
		SqlParameterSource param = new MapSqlParameterSource().addValue("dependentsCount", employee.getDependentsCount());
		template.update(sql, param);
	}

}
