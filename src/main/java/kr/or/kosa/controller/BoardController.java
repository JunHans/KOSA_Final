package kr.or.kosa.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.kosa.dto.Member;
import kr.or.kosa.dto.Post;
import kr.or.kosa.service.BoardService;
import kr.or.kosa.service.MemberService;

// 페이지 이동 Controller
@Controller
public class BoardController {

	private BoardService boardService;
	private MemberService memberService;

	@Autowired
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 기본 제공 게시판
	@GetMapping("{allBoard}")
	public String allBoardView(Model model, @PathVariable String allBoard) {

		String param = "";
		
		if (allBoard.equals("noticeList")) {
			param = "공지사항";
		} else if (allBoard.equals("opinionList")) {
			param += "건의사항";
		} else if (allBoard.equals("freeBoardList")) {
			param += "자유게시판";
		} else if (allBoard.equals("productBoardList")) {
			param += "거래게시판";
		}

		String viewPage = "member/board/" + allBoard;

		List<Post> allBoardList = boardService.allBoardList(param); // 글목록
		System.out.println("POST: " + allBoardList);
		model.addAttribute("allBoardList", allBoardList);

		return viewPage;
	}

	// 커스텀 생성 게시판
	@GetMapping("board/customBoardList")
	public String boardList(Model model, @PathVariable String boardName) {

		List<Post> boardList = boardService.customBoardList(boardName);// 글목록
		model.addAttribute("boardList", boardList);
		model.addAttribute("boardName", boardName);

		return "member/board/customBoardList";
	}

	// 게시판 글쓰기 이것도 게시판마다 달라야함

	@GetMapping("/{boardName}/boardWrite")
	public String BoardWrite() {
		return "member/board/boardWrite";
	}

	@PostMapping("/{boardName}/boardWrite")
	public String BoardWriteOk() {
		return "member/board/boardWrite";
	}

	// 공지사항 글쓰기
	@GetMapping("/공지사항/noticeWrite")
	public String noticeWrite() {
		return "member/board/noticeWrite";
	}

	// 공지사항 글쓰기
	@PostMapping("/공지사항/noticeWrite")
	public String noticeWriteOk(Principal principal, Model model, @PathVariable("title") String title,
			@PathVariable("content") String content) {
		Member member = null;
		String memberid = principal.getName();
		member = memberService.getMemberById(memberid);
		return "member/board/noticeWrite";
	}

	// 건의사항 글쓰기
	@GetMapping("/건의사항/opinionWrite")
	public String opinionWrite() {
		return "member/board/opinionWrite";
	}

	// 건의사항 글쓰기
	@PostMapping("/건의사항/opinionWrite")
	public String opinionWriteOk() {
		return "member/board/opinionWrite";
	}

	// 자유게시판 글쓰기
	@GetMapping("/자유게시판/freeBoardWrite")
	public String freeBoardWrite() {
		return "member/board/freeBoardWrite";
	}

	// 자유게시판 글쓰기
	@PostMapping("/자유게시판/freeBoardWrite")
	public String freeBoardWriteOk(Principal principal, Model model, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		String msg = "";
		String url = "";
		int result = 0;

		Member member = null;
		String memberid = principal.getName();
		member = memberService.getMemberById(memberid);

		if (memberid == null) {
			msg = "세션이 만료되었습니다.";
			url = "/";
		} else {
			Post post = new Post();

			post.setBoardIdx(3);
			post.setUniversityCode(member.getUniversityCode());
			post.setMemberId(member.getMemberId());
			post.setTitle(title);
			post.setContent(content);

			result = boardService.freeBoardWrite(post);

			if (result < 1) {
				msg = "글 작성이 실패했습니다.";
				url = "/자유게시판/freeBoardWrite";
			} else {
				msg = "글 작성이 완료되었습니다!";
				url = "/자유게시판";
			}

			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
		}
		return "/common/redirect";
	}

}
