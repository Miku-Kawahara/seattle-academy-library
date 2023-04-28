package jp.co.seattle.library.controller;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

@Controller
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

	@Autowired
	private UsersService usersService;
	
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String PasswordReset(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		logger.info("Welcome PasswordResetController.java! The client locale is {}.", locale);

	
		//メールアドレス，パスワード，確認用パスワードを入力してパスワード変更ボタンを押下
		//
		if (password.length() >= 8 && password.matches("[0-9a-zA-Z]+")) {
			if (password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.resetUser(userInfo);
				return "redirect:/login";
			} else {
				model.addAttribute("errorMessages", "パスワードは8桁以上の半角英数字で設定してください。");
				return "PasswordReset";
			}
		} else {
			model.addAttribute("errorMessages", "パスワードと確認用パスワードが一致していません。");
			return "PasswordReset";
		}
	}
}