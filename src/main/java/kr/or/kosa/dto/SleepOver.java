package kr.or.kosa.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SleepOver {

	private String universityCode;
	private String memberId;
	private Date startDate;
	private Date endDate;
	private int status;
	private String sleepOverReason;
	private String sleepOverConfirm;
	private int sleepOverFileIdx;
	private String sleepOverFileName;
	private int sleepOverFileSize;
}
