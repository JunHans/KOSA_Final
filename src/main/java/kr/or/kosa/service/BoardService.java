package kr.or.kosa.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.kosa.dao.BoardDao;
import kr.or.kosa.dto.Board;
import kr.or.kosa.dto.Domitory;
import kr.or.kosa.dto.Post;
import kr.or.kosa.dto.Reply;

@Service
public class BoardService {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	// 게시판 리스트
	public List<Board> categoryList() {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Board> categoryList = boardDao.categoryList();
		return categoryList;
	}

	// 기본 제공 게시판 글 목록
	public List<Post> allBoardList(String allBoard) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Post> allBoardList = boardDao.allBoardList(allBoard);
		Date nowDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String formatNowDate = simpleDateFormat.format(nowDate);

		for (Post p : allBoardList) {
			String writedate = p.getWriteDate().substring(0, 10);
			if (formatNowDate.equals(writedate)) {
				p.setWriteDate(p.getWriteDate().substring(11, 16));
			} else {
				p.setWriteDate(writedate.substring(5));
			}
		}
		return allBoardList;
	}

	// 커스텀 생성 게시판 목록
	public List<Post> customBoardList(String boardName) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Post> customBoardList = boardDao.customBoardList(boardName);

		Date nowDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String formatNowDate = simpleDateFormat.format(nowDate);

		for (Post p : customBoardList) {
			String writedate = p.getWriteDate().substring(0, 10);
			if (formatNowDate.equals(writedate)) {
				p.setWriteDate(p.getWriteDate().substring(11, 16));
			} else {
				p.setWriteDate(writedate.substring(5));
			}
		}
		return customBoardList;
	}

	// 게시글 상세보기
	public List<Post> boardContent(String idx) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Post> boardContent = boardDao.boardContent(idx);
		
		boardContent.get(0).setWriteDate(boardContent.get(0).getWriteDate().substring(2, 16));
				
		return boardContent;
	}
	
	// 댓글 목록 보기
	public List<Reply> replyContent(String idx) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		System.out.println("t서비스??");
		List<Reply> replyContent = boardDao.replyContent(idx);
		System.out.println("replyContent: " + replyContent);
		
		return replyContent;
	}

	// 자유게시판 글쓰기
	public int freeBoardWrite(Post post) {

		int result = 0;

		try {
			BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
			result = boardDao.boardInsert(post);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	// 점호 위치값 비교하기
	public String eveningCall(double lat, double lon) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		System.out.println("서비스 옴?");
		Domitory domitory = boardDao.eveningCall(lat, lon);
		System.out.println("replyContent: " + domitory);
		double domitoryLat = domitory.getDomitoryLatitude();
		double domitoryLon = domitory.getDomitoryLogitude();
		
		String alert = "SUCCESS";
		if(!((domitoryLat - lat)<0.005)) {
			alert = "FAIL";
		}
		if(!((domitoryLon - lon)<0.005)) {
			alert = "FAIL";
		}
		
		
		return alert;
	}

}
