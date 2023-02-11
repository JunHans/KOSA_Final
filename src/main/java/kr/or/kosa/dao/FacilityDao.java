package kr.or.kosa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.kosa.dto.Facility;

public interface FacilityDao {
	
	//시설물 DB 넣기
	public int insertItem(String universitycode, String facilityname);
	
	//시설물 DB 넣기
	public int insertDomitory(String universitycode, String domitoryname, String domitoryfloor);
	
	//리스트 받아오기
	public List<Facility> selectItem();
	
	//시설물 신고 인서트
	public int insertReport(@Param("facilityidx")int facilityidx, @Param("domitoryname")String domitoryname, @Param("domitoryfloor")String domitoryfloor,@Param("facilityname")String facilityname,@Param("facilityReport")String facilityReport, @Param("memberid")String memberid);
	
	//특정 시설물 조회
	public Facility selectReportItem(String universityCode, String facilityname);

//	//점호데이터 넣기
//	public int eveningCallInsert(String memberId, String universitycode);
//	
//	//점호 데이터 중복 체킹
//	public RollCall eveningCallCompare(String memberId, String universitycode);
}