package com.tlzn.service.base;

import java.util.List;
import java.util.Map;

import com.tlzn.model.poll.Tpoll;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.speech.Speech;
import com.tlzn.pageModel.sys.Comm;

public interface CreateWordServiceI {
	/**
	 * Word生成
	 * @param proposalId
	 * @return void
	 */
//	public String createWord(String proposalId)throws Exception;

	public String download(String proposalId)throws Exception;
	public String pollDownload(Tpoll poll)throws Exception;
	public String fulFilDownload(String code, String secondaryCode,String year,Comm comm,
			List<Proposal> proposalList, List<Tpoll> pollList,
			List<Speech> speechList, List<MeetingMan> meetingManList,
			List<Activitypeo> activitypeoList) throws Exception;
	public String stuBasInfoDownload(String stuNo) throws Exception;
	public String speechDownload(String speakId, String secondaryCode,String year,Speech speech,List<String> attList) throws Exception;
	
}
