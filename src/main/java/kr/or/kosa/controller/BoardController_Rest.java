package kr.or.kosa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.dto.Board;
import kr.or.kosa.dto.Domitory;
import kr.or.kosa.dto.Facility;
import kr.or.kosa.dto.Reply;
import kr.or.kosa.security.User;
import kr.or.kosa.service.BoardService;
import kr.or.kosa.service.FacilityService;

@RestController
public class BoardController_Rest {

	BoardService boardService;
	FacilityService facilityService;

	@Autowired
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	@Autowired
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	// 게시판 종류
	@RequestMapping("/categoryList")
	public ResponseEntity<List<Board>> categoryList() {
		List<Board> categoryList = new ArrayList<Board>();
		try {
			categoryList = boardService.categoryList();
			return new ResponseEntity<List<Board>>(categoryList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Board>>(categoryList, HttpStatus.BAD_REQUEST);
		}
	}


	// 게시판 댓글 보기
	@GetMapping("/{allBoard}/board/{idx}")
	public ResponseEntity<List<Reply>> replyContent(@PathVariable("idx") String idx) {
		List<Reply> replyContent = new ArrayList<Reply>();
		try {
			replyContent = boardService.replyContent(idx);
			return new ResponseEntity<List<Reply>>(replyContent, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Reply>>(replyContent, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// 게시판 대댓글 보기
	@GetMapping("/{allBoard}/{idx}/reply/{replyIdx}")
	public ResponseEntity<List<Reply>> reReplyContent(@PathVariable("idx") String idx,
			@PathVariable("replyIdx") String replyIdx) {
		List<Reply> reReplyContent = new ArrayList<Reply>();
		try {
			reReplyContent = boardService.reReplyContent(replyIdx);
			return new ResponseEntity<List<Reply>>(reReplyContent, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Reply>>(reReplyContent, HttpStatus.BAD_REQUEST);
		}
	}
	


		
		//저녁점호 위치비교 + 중복체크 + 데이터 인서트
		 @RequestMapping(value = "/eveningCall", method = RequestMethod.POST, produces = "application/text; charset=utf8")
		   public String eveningCall(@RequestParam(value = "report[]") double[] report) {
			 System.out.println("lat : "+ report);
			 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 String memberId = user.getMemberId();
			 String unicode = user.getUniversityCode();
			 System.out.println("memberid : "+memberId);
			 double lat = report[0];
			 System.out.println("lat : "+ lat);
			 double lon = report[1];
			 System.out.println("lon : "+ lon);
			 String result = boardService.eveningCall(lat, lon);
			 boardService.eveningCall(lat, lon);
			 //점호한 인원 정보 데이터 인서트
			 String result2 = boardService.eveningCallCompare(memberId, unicode);
			 System.out.println("중복 체크 결과 : "+result2);
			 String result3 = result2+" : 이미 점호 완료한 회원입니다.";
			 if(result2.equals("SUCCESS")) {
				 boardService.eveningCallInsert(memberId, unicode);
				 result3 = result2+" : 점호가 완료되었습니다.";
				 System.out.println("result3 : "+ result3);
			 }
			 System.out.println("result3 : "+ result3);
			 return result3;
		 }
		 
		//시설물 DB 인서트
			@RequestMapping("/insertItem")
			public ResponseEntity<List<Facility>> insertItem(@RequestParam String item) {
				List<Facility> faclist = new ArrayList<Facility>();
				 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				 String unicode = user.getUniversityCode();
				 System.out.println("unicode : "+unicode);
				 
				 //들어갔는지 row 수 반환
				 Integer result = facilityService.insertItem(unicode, item);
				 System.out.println("인서트 결과 추가된 ROW : "+result);
				try {
					faclist = facilityService.selectItem();
					return new ResponseEntity<List<Facility>>(faclist, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<List<Facility>>(faclist, HttpStatus.BAD_REQUEST);
				}
			}
			
			//시설물 DB 테이블만 출력
			@RequestMapping("/itemPrint")
			public ResponseEntity<List<Facility>> itemPrint() {
				List<Facility> faclist = new ArrayList<Facility>();
				try {
					faclist = facilityService.selectItem();
					return new ResponseEntity<List<Facility>>(faclist, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<List<Facility>>(faclist, HttpStatus.BAD_REQUEST);
				}
			}
		 
		//기숙사 건물  DB 인서트
//		 @RequestMapping(value = "/insertDomitory", method = RequestMethod.POST, produces = "application/text; charset=utf8")
//		   public String insertDomitory(@RequestParam(value = "domitory[]") String[] domitory) {
//			 System.out.println("domitory : "+ domitory);
//			 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			 String unicode = user.getUniversityCode();
//			 System.out.println("unicode : "+unicode);
//			 
//			 String domitoryname = domitory[0];
//			 String domitoryfloor = domitory[1];
//			 
//			 //들어갔는지 row 수 반환
//			 Integer result = facilityService.insertDomitory(unicode, domitoryname, domitoryfloor);
//			 System.out.println("인서트 결과 추가된 ROW : "+result);
//			 String result1 = "기숙사 건물 입력성공";
//			 
//			 return result1;
//		 }
		 
		
}
