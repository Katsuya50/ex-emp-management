package jp.co.sample.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報の処理制御クラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@Autowired
	private ServletContext application;
	
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 従業員リストをリクエストスコープに格納して従業員リスト画面にフォワードするメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @return 従業員リスト画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = service.showList();
		model.addAttribute("employeeList", employeeList);
		return "/employee/list";
	}
	
	/**
	 * idに一致する従業員の詳細画面にフォワードするメソッド.
	 * 
	 * @param id 従業員ID
	 * @param model リクエストスコープ
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		model.addAttribute("employee", service.showDetail(Integer.parseInt(id)));
		return "/employee/detail";
	}
	
	/**
	 * 従業員の扶養人数を更新し従業員リスト画面にリダイレクトするメソッド.
	 * 
	 * @param form 更新する従業員の情報を受け取ったform
	 * @return 従業員リスト画面にリダイレクト
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = new Employee();
		employee.setId(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		service.update(employee);
		return "redirect:/employee/showList";
	}
	
	/**
	 * 分割された従業員リストをアプリケーションスコープに格納して従業員リスト画面ページ1にフォワードするメソッド.
	 * 
	 * @return 従業員リスト画面ページ1
	 */
	@RequestMapping("/showList1")
	public String showDividedList1(Model model) {
		List<List<Employee>> allOfDividedEmployeeLists = service.showDividedLists();
		application.setAttribute("allOfDividedEmployeeLists", allOfDividedEmployeeLists);
		model.addAttribute("dividedEmployeeList", allOfDividedEmployeeLists.get(0));
		model.addAttribute("index", 0);
		return "/employee/lists";
	}
	
	/**
	 * 送られてきたインデックスに応じた従業員リスト画面にフォワードするメソッド.
	 * 
	 * @return 従業員リスト画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/showLists")
	public String showDividedLists(int index, Model model) {
		List<List<Employee>> allOfDividedEmployeeLists = (List<List<Employee>>) application.getAttribute("allOfDividedEmployeeLists");
		model.addAttribute("dividedEmployeeList", allOfDividedEmployeeLists.get(index));
		model.addAttribute("index", index);
		return "/employee/lists";
	}

}
