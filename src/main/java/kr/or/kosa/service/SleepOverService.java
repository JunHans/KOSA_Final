package kr.or.kosa.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.kosa.aws.AwsS3;
import kr.or.kosa.dao.MemberDao;
import kr.or.kosa.dao.SleepOverDao;
import kr.or.kosa.dto.Member;
import kr.or.kosa.dto.SleepOver;
import kr.or.kosa.dto.SleepOverHistory;
import kr.or.kosa.dto.SleepOverTime;
import kr.or.kosa.security.User;

@Service
public class SleepOverService {
	
	private SqlSession sqlsession;
	 
	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
	   this.sqlsession = sqlsession;
	}
	
	//외박 신청(insert)
	public int insertSleepOver(SleepOver over, MultipartFile file) throws ParseException {
		int result = 0;
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SleepOverDao overdao = sqlsession.getMapper(SleepOverDao.class);
		
		Date now = Calendar.getInstance().getTime();
		 // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        // 포맷팅 적용
        String formatedNow = formatter.format(now);
        now = formatter.parse(formatedNow);
        
        //유저가 속한 학교의 sleepOverTime을 가져옴
        SleepOverTime overtime = overdao.getSleepOverTime(user.getUniversityCode());
        
        String starttime = formatter.format(overtime.getStartTime());
        String endtime = formatter.format(overtime.getEndTime());
        
        
        Date nowdate = new Date();
        String nowdateString = formatter.format(nowdate);

        Date start = formatter.parse(starttime);
        Date end = formatter.parse(endtime);
        nowdate = formatter.parse(nowdateString);
//        System.out.println("starttime : " + start);
//        System.out.println("endtime : " + end);
//        System.out.println("now : " + nowdate);
//        System.out.println("now.after 스타트타임 : " + nowdate.after(start));
//        System.out.println("now.before 엔드타임 + " + nowdate.before(end));
        
		//외박 신청 시간이 아니면 reject할것임
//        String started = formatter.parse(overtime.getStartTime());
        if(!(nowdate.after(start) && nowdate.before(end))){
//        	System.out.println("시간 범위 밖임");
        	result = 400;
        	return result;
        }
        
		over.setUniversityCode(user.getUniversityCode());
		over.setMemberId(user.getMemberId());

		//파일 업로드
		if (file.getSize() != 0) {
			over.setSleepOverFileName(file.getOriginalFilename());
			over.setSleepOverFileSize((int)file.getSize());
			
			try {
				AwsS3 awsS3 = AwsS3.getInstance();
				String route = user.getUniversityCode()+"/sleepOver/" + user.getMemberId() + "/" +file.getOriginalFilename();
				System.out.println(route);
				awsS3.upload(file, route);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		result = overdao.insertSleepOver(over);
		
		return result;
	}
	
	
	//외박 신청 조회
	public List<SleepOver> getTodaysList(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String universitycode = user.getUniversityCode();
		SleepOverDao overdao = sqlsession.getMapper(SleepOverDao.class);
		MemberDao memberdao = sqlsession.getMapper(MemberDao.class);
		
		List<SleepOver> list = overdao.getTodaysSleepOver(universitycode);
		
		for(SleepOver over : list) {
			Member member = memberdao.getMember(over.getMemberId());
			String username = member.getName();
			over.setUsername(username);
		}
		
		return list;
	}
	
	//외박 수정(update) : 사감이 승인을 했을 경우
	public int confirm(int idx) {
		int result = 0;
		
		SleepOverDao overdao = sqlsession.getMapper(SleepOverDao.class);
		result = overdao.confirmSleepOver(idx);
		
		return result;
	}
	
	//외박이력 조회
	public List<SleepOverHistory> getHistory(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SleepOverDao overdao = sqlsession.getMapper(SleepOverDao.class);
		MemberDao memberdao = sqlsession.getMapper(MemberDao.class);
		List<SleepOverHistory> list = overdao.getSleepOverHistory(user.getUniversityCode());
		for(SleepOverHistory over : list) {
			Member member = memberdao.getMember(over.getMemberId());
			String username = member.getName();
			over.setUsername(username);
		}
		return list;
	}
	
	//현 시각 외박 조회
	public List<SleepOverHistory> getTodaysHistory(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SleepOverDao overdao = sqlsession.getMapper(SleepOverDao.class);
		MemberDao memberdao = sqlsession.getMapper(MemberDao.class);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(date);
		
		List<SleepOverHistory> list = overdao.searchHistoryWithDate(today, today, null, user.getUniversityCode());
		for(SleepOverHistory over : list) {
			Member member = memberdao.getMember(over.getMemberId());
			String username = member.getName();
			over.setUsername(username);
		}
		return list;
	}
	
	//외박 기간별 검색
	public List<SleepOverHistory> searchIntervalHistory(String startdate, String enddate, String memberid){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SleepOverDao overdao = sqlsession.getMapper(SleepOverDao.class);
		MemberDao memberdao = sqlsession.getMapper(MemberDao.class);

		List<SleepOverHistory> list = overdao.searchHistoryWithDate(startdate, enddate, memberid, user.getUniversityCode());

		for(SleepOverHistory over : list) {
			Member member = memberdao.getMember(over.getMemberId());
			String username = member.getName();
			over.setUsername(username);
		}
		return list;
	}
}
