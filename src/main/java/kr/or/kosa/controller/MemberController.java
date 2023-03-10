package kr.or.kosa.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.kosa.dto.Member;
import kr.or.kosa.dto.PaymentHistory;
import kr.or.kosa.dto.SleepOver;
import kr.or.kosa.security.CustomUser;
import kr.or.kosa.security.CustomUserDetailService;
import kr.or.kosa.security.User;
import kr.or.kosa.service.MemberService;
import kr.or.kosa.service.PaymentService;
import kr.or.kosa.service.SleepOverService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberservice;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	SleepOverService sleepoverService;
	
	private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/nightOver")
	public String application() {
		
		return "member/nightOver";
	}
	
	@PostMapping("/nightOver")
	public String nightOver(SleepOver over, MultipartFile file, Model model) {
		int result = 0;
		try {
			result = sleepoverService.insertSleepOver(over, file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String msg = "";
		String url = "/";
		String icon = "";
		if(result == 400) {
			icon = "error";
			msg = "?????? ?????? ?????? ????????? ????????????.";
		} else if (result == 1) {
			icon = "success";
			msg = "?????? ?????? ??????!";
		} else{
			icon = "error";
			msg = "????????? ???????????????";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		model.addAttribute("icon", icon);
		return "/common/redirect";
	}
	
	@GetMapping("/mealticket")
	public String mealticket() {
		
		return "member/mealticket";
	}
	
	@RequestMapping("/payments")
	public String mealticketPayment(@RequestParam("memberid") String memberid, @RequestParam("amount")String amount, Model model) {
		System.out.println("??????????????? ????????? ... : " + memberid + " " + amount);
		String kind = "??????";
		int result = 0;
		result = paymentService.insertPayment(memberid, amount, kind);
		
		String msg = "";
		String url = "";
		String icon = "";
		if (result > 0) {
			//???????????? ???????????? ?????? ?????? ??????
			int newpoint = memberservice.getMemberById(memberid).getMemberPoint();
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user.setMemberPoint(newpoint);
			
			icon = "success";
			msg = "????????? ?????? ??????!";
			url = "/";
		} else {
			icon = "error";
			msg = "????????? ???????????????";
			url = "/";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		model.addAttribute("icon", icon);
		return "/common/redirect";
	}
	
	
	
	//????????????....
	@GetMapping("/testmember")
	public String test() {
		List<Member> list = new ArrayList<Member>();
		list = memberservice.getAllMember("kosa");
		for(Member m : list) {
			System.out.println(m);
		}
		return null;
	}
	
	@GetMapping("/testgetmemberid")
	public String testSearchById(@AuthenticationPrincipal Object principal) {
		System.out.println(principal.toString());
//		Member member = new Member();
//		member = service.getMemberById("2017109210");
//		System.out.println(member);
		return null;
	}
	@GetMapping("/testgetmember")
	public String testSearchByName(Principal principal) {
		Member member = memberservice.getMemberById(principal.getName());
		List<Member> list = new ArrayList<Member>();
//		list = service.getMemberByName("?????????", "kosa");
		list = memberservice.getMemberByName(member.getName(), member.getUniversityCode());
		System.out.println(list);
		return null;
	}
	
	@GetMapping("/testpayments")
	public List<PaymentHistory> testpay() {
		List<PaymentHistory> list = paymentService.getPaymentHistoryById("2017109210");
		System.out.println(list);
		return list;
	}
}