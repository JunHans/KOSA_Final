package kr.or.kosa.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.dto.Member;
import kr.or.kosa.service.MemberService;

@RestController
@RequestMapping("/mypage")
public class MypageController {
	
	MemberService memberservice;
	
	@Autowired
    public void setMemberService(MemberService memberservice) {
        this.memberservice = memberservice;
    }
	
	//비동기를 위한 RestController임
	
	//내 정보 조회
	@GetMapping("/myinfo")
	public ResponseEntity<Member> myinfo(Principal principal){
		Member member = null;
		String memberid = principal.getName();
		member = memberservice.getMemberById(memberid);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
	
	//내 정보 수정
	
	//내 외박 내역 조회
	
	//내 벌점 조회
	
	//내 커뮤니티 조회
	
	//쪽지함 ???
}
