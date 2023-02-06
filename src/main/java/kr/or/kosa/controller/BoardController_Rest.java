package kr.or.kosa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.dto.Board;
import kr.or.kosa.service.BoardService;

@RestController
public class BoardController_Rest {

	BoardService boardService;

	@Autowired
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
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
	
	// 게시판 상세보기
		@GetMapping("{allBoard}/{boardIdx}")
		public ResponseEntity<Map<String, Object>> boardAndReply(@PathVariable("boardIdx") String boardIdx) {
			Map<String, Object> map = new HashMap<>();
			
			try {
				map.put("boardContent", boardService.boardContent(boardIdx));
				map.put("replyContent", boardService.replyContent(boardIdx));
				return new ResponseEntity<>(map, HttpStatus.OK);
			} catch (Exception e) {
				map.put("boardContent", boardService.boardContent(boardIdx));
				map.put("replyContent", boardService.replyContent(boardIdx));
				return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
			}
		}

}
