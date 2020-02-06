package jp.co.sample.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author katsuya.fujishima
 *
 */
@Repository
public class EmployeeRepository {
	
	/** JDBC操作に使用するテンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** JDBC操作に使用するテーブル名 */
	private static final String TABLE_NAME = "employees";
	
	/** Employeeオブジェクトを生成するローマッパー */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
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
	
	/**
	 *  テーブル内の従業員情報を全て返すメソッド.
	 * 
	 * @return 従業員情報のリスト
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, "
					+ "telephone, salary, characteristics, dependents_count "
					+ "FROM " + TABLE_NAME + " ORDER BY hire_date";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	/**
	 *  idが一致した従業員情報を返すメソッド.
	 * 
	 * @param id 従業員id
	 * @return 従業員情報のドメイン
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, "
				+ "telephone, salary, characteristics, dependents_count "
				+ "FROM " + TABLE_NAME + " WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		return employee;
	}
	
	/**
	 *  idが一致した従業員の扶養人数を変更するメソッド.
	 * 
	 * @param employee 従業員情報のドメイン
	 */
	public void update(Employee employee) {
		String sql = "UPDATE " + TABLE_NAME + " SET dependents_count = :dependentsCount WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("dependentsCount", employee.getDependentsCount()).addValue("id", employee.getId());
		template.update(sql, param);
	}
	
	/**
	 * 従業員情報を分割しリストにして返すメソッド.
	 * 
	 * @return 分割した複数の従業員リストの集合
	 */
	public List<List<Employee>> divPar10Rows() {
		String sql = "SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, "
					+ "telephone, salary, characteristics, dependents_count "
					+ "FROM " + TABLE_NAME + " ORDER BY id "
					+ "LIMIT :rows OFFSET :offsetRows";
		List<List<Employee>> allOfDividedEmployeeLists = new ArrayList<>();
		List<Employee> employeeList = findAll();
		for(int i = 0; i < employeeList.size() + 10; i += 10) {
			SqlParameterSource param = new MapSqlParameterSource()
					.addValue("rows", 10).addValue("offsetRows", i);
			List<Employee> dividedEmployeeList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
			allOfDividedEmployeeLists.add(dividedEmployeeList);
		}
		return allOfDividedEmployeeLists;
	}

}
