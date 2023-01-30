package kr.or.kosa.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
	
	private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

	@GetMapping("")
	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
		return "index";
	}
	
	@GetMapping("/mypage")
	public String myPage() {
		
		return "member/mypage";
	}
	
	@GetMapping("/community")
	public String community() {
		
		return "member/community";
	}
	
	@GetMapping("/mealticket")
	public String mealticket() {
		
		return "member/mealticket";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "common/login";
	}
	
}
