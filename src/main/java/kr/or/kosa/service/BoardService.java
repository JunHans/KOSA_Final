package kr.or.kosa.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.kosa.aws.AwsS3;
import kr.or.kosa.dao.BoardDao;
import kr.or.kosa.dto.Board;
import kr.or.kosa.dto.Domitory;
import kr.or.kosa.dto.File;
import kr.or.kosa.dto.Post;
import kr.or.kosa.dto.Reply;
import kr.or.kosa.dto.RollCall;
import kr.or.kosa.security.User;

@Service
public class BoardService {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	// 게시판 리스트
	public List<Board> categoryList() {
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String universityCode = user.getUniversityCode();
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Board> categoryList = boardDao.categoryList(universityCode);
		return categoryList;
	}

	// 기본 제공 게시판 글 목록
	public List<Post> allBoardList(String allBoard) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String universityCode = user.getUniversityCode();
		
		List<Post> allBoardList = boardDao.allBoardList(allBoard, universityCode);
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
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String universityCode = user.getUniversityCode();
		
		List<Post> customBoardList = boardDao.customBoardList(boardName, universityCode);

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
	
	// 게시글 상세보기 (객체로)
	public Post boardContentDTO(int idx) throws ClassNotFoundException, SQLException {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		Post post = boardDao.boardContentDTO(idx);
		return post;
	}
	
	// 파일 상세보기
	public File fileContent(String idx) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		File fileContent = boardDao.fileContent(idx);
		
		return fileContent;
	}
	
	// 댓글 목록 보기
	public List<Reply> replyContent(String idx) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Reply> replyContent = boardDao.replyContent(idx);

		Date nowDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String formatNowDate = simpleDateFormat.format(nowDate);

		for (Reply r : replyContent) {
			String replyDate = r.getReplyDate().substring(0, 10);
			if (formatNowDate.equals(replyDate)) {
				r.setReplyDate(r.getReplyDate().substring(11, 16));
			} else {
				r.setReplyDate(replyDate.substring(5));
			}
		}
		return replyContent;
	}

	// 대댓글 목록 보기
	public List<Reply> reReplyContent(String replyIdx) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		List<Reply> reReplyContent = boardDao.reReplyContent(replyIdx);

		Date nowDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String formatNowDate = simpleDateFormat.format(nowDate);

		for (Reply r : reReplyContent) {
			String replyDate = r.getReplyDate().substring(0, 10);
			if (formatNowDate.equals(replyDate)) {
				r.setReplyDate(r.getReplyDate().substring(11, 16));
			} else {
				r.setReplyDate(replyDate.substring(5));
			}
		}
		return reReplyContent;
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

	// 파일첨부 글쓰기
	public int fileWrite(File file) {

		int result = 0;

		try {
			BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
			result = boardDao.fileInsert(file);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	// 추천 여부 검사
	// 추천 여부 카운트하기 > 개수에 따라 RUD하기 > 업데이트된 추천 개수 출력하기

	// 점호 위치값 비교하기
	public String eveningCall(double lat, double lon) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		System.out.println("서비스 옴?");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String domitoryname = user.getDomitoryName();
		System.out.println("회원의 domitoryname : "+domitoryname);
		Domitory domitory = boardDao.eveningCall(domitoryname);
		System.out.println("셀렉트받아온 domitory: " + domitory);
		double domitoryLat = domitory.getDomitoryLatitude();
		double domitoryLon = domitory.getDomitoryLogitude();

		String alert = "SUCCESS";
		if (!((domitoryLat - lat) < 0.005)) {
			alert = "FAIL";
			System.out.println("결과 : "+alert+"lat 계산 : "+(domitoryLat - lat));
		}
		if (!((domitoryLon - lon) < 0.005)) {
			alert = "FAIL";
			System.out.println("결과 : "+alert+"lon 계산 : "+(domitoryLon - lon));
		}

		return alert;
	}

	// 점호완료시 DB에 회원 점호데이터 인서트
			public String eveningCallInsert(String memberid, String universitycode) {
				BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
				System.out.println("memberid 서비스 옴?");
				int  rollcall = boardDao.eveningCallInsert(memberid, universitycode);

				if( rollcall >=1) {
					System.out.println("성공");
				}
				System.out.println("rollcall : " + rollcall);

				return null;
			}
		
		// 점호완료시 DB에 회원 점호데이터 인서트
		public String eveningCallCompare(String memberid, String universitycode, String rollcalldate) {
			BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
			System.out.println("Camopare 서비스 옴?");
			RollCall  rollcall = boardDao.eveningCallCompare(memberid, universitycode, rollcalldate);
			String unicode = rollcall.getUniversityCode();
			String date = rollcall.getRollCallDate();
			String dbmemberid = rollcall.getMemberId();
			int count = rollcall.getCounting();
			
			System.out.println("date : "+date);
			System.out.println("unicode : "+unicode);
			System.out.println("dbmemberid : "+ dbmemberid);
			System.out.println("count : "+ count);
			
			String result = "SUCCESS"; 
			System.out.println("점호한 회원데이터 : "+memberid+"/"+universitycode+"/"+rollcalldate +"===="+ "DB에서 가져온 비교데이터 : "+ dbmemberid+"/"+unicode+"/"+date);
			//조회된데이터 없으면
			if(count >= 1) {
				System.out.println("이미 DB에 "+memberid+" 회원의 데이터 있음");
				result = "FAIL";
			}
			System.out.println("DB에서 받아온 점호 데이터 : " + rollcall);
			
			return result;
		}
		
	//최근 파일  idx가져오기	
	public int recentFileIdx() {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		int  result = boardDao.recentFileIdx();
		return result;
	}
	
	//게시글 수정 or 삭제
	public int boardContentEdit(Post post) {
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		int result = boardDao.boardEdit(post);
		
		return result;
	}
	
	//공지사항 글쓰기
	public int noticeListInsert(Post post, File file, MultipartFile multipartfile) {
		
		int idx = 0;
		int result = 0;
		String route = "";
		
		result = this.freeBoardWrite(post);
		
		if(multipartfile.getSize() != 0) {
			
			result = this.fileWrite(file);
			idx = this.recentFileIdx();
			
			try {
				AwsS3 awsS3 = AwsS3.getInstance();
				route = post.getUniversityCode()+"/"+ "board" + "/" + idx + "/" + file.getFileName();
				System.out.println(route);
				System.out.println(awsS3.searchIcon(route));
				awsS3.upload(multipartfile, route);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
