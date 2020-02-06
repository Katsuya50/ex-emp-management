package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員情報処理のserviceクラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	/**
	 * 従業員リストを取得する処理.
	 * 
	 * @return 従業員リスト
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = repository.findAll();
		return employeeList;
	}
	
	/**
	 * idに一致する従業員情報を取得する処理.
	 * 
	 * @param id 従業員ID
	 * @return 従業員情報
	 */
	public Employee showDetail(Integer id) {
		return repository.load(id);
	}
	
	/**
	 * 従業員の扶養人数を更新する処理.
	 * 
	 * @param employee　従業員情報
	 */
	public void update(Employee employee) {
		repository.update(employee);
	}
	
	/**
	 * 分割された従業員リストを取得する処理.
	 * 
	 * @return 分割された従業員リスト
	 */
	public List<List<Employee>> showDividedLists() {
		List<List<Employee>> allOfDividedEmployeeLists = repository.divPar10Rows();
		return allOfDividedEmployeeLists;
	}
	
}
