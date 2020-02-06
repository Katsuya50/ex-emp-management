package jp.co.sample.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 従業員情報の処理制御クラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService service;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public InsertAdministratorForm setUpAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/**
	 * 管理者登録画面にフォワードするメソッド.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報登録メソッド.
	 * administratorsテーブルに新しく行を挿入する
	 * 
	 * @param form 挿入する情報を受け取ったform
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "administrator/insert";
		}
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		//入力されたメールアドレスが登録済かを検証、登録済であればエラー
		for(Administrator ad: service.findAllOfmailAddress()) {
			if(administrator.getMailAddress().equals(ad.getMailAddress())) {
				FieldError fieldError = new FieldError(
						result.getObjectName(), "mailAddress", "入力されたメールアドレスは登録済のため使用できません");
				result.addError(fieldError);
				model.addAttribute("mailAddress", result);
				return "administrator/insert";
			}
		}
		
		service.insert(administrator);
		return "redirect:/";
	}
	
	/**
	 * ログイン画面にフォワードするメソッド.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * 管理者がログインをするメソッド.
	 * 
	 * @param form ログイン画面から情報を受け取ったフォーム
	 * @param resultOfErrorMessage ログイン失敗時に記載されるエラーメッセージ
	 * @param model リクエストスコープ
	 * @return ログイン失敗ならばログイン画面に戻る、成功ならば従業員リスト画面にリダイレクト
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, BindingResult resultOfErrorMessage, Model model) {
		Administrator administrator = service.login(form.getMailAddress(), form.getPassword());
		FieldError fieldError = new FieldError(resultOfErrorMessage.getObjectName(), "mailAddress", "メールアドレスまたはパスワードが不正です");
		resultOfErrorMessage.addError(fieldError);
		if(Objects.equals(administrator, null)) {
			model.addAttribute("mailAddress", resultOfErrorMessage);
			return toLogin();
		}
		session.setAttribute("administratorName", administrator.getName());
		return "redirect:/employee/showList";
	}
	
	/**
	 * ログアウトしてログイン画面にリダイレクトするメソッド.
	 * セッションスコープ情報をクリアする
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}
