package com.tlzn.service.base.impl;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.model.poll.Tpoll;
import com.tlzn.model.sys.Tsecondary;
import com.tlzn.pageModel.activity.Activitypeo;
import com.tlzn.pageModel.eduManage.FamilyMem;
import com.tlzn.pageModel.eduManage.Stu;
import com.tlzn.pageModel.info.Proposal;
import com.tlzn.pageModel.info.Proposaler;
import com.tlzn.pageModel.meeting.MeetingMan;
import com.tlzn.pageModel.speech.Speech;
import com.tlzn.pageModel.sys.Comm;
import com.tlzn.pageModel.sys.Hand;
import com.tlzn.service.base.CreateWordServiceI;
import com.tlzn.service.eduManage.FmServiceI;
import com.tlzn.service.eduManage.StuServiceI;
import com.tlzn.service.info.PropServiceI;
import com.tlzn.service.info.SponsorServiceI;
import com.tlzn.service.sys.HandServiceI;
import com.tlzn.service.sys.SecoServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.JacobToWord;
import com.tlzn.util.base.Util;

@Service("createWordService")
public class CreateWordServiceImpl extends BaseServiceImpl implements
		CreateWordServiceI {
	private PropServiceI propService;

	private SponsorServiceI sponsorService;

	private HandServiceI handService;

	private SecoServiceI secoService;

	private StuServiceI stuServiceI;

	private FmServiceI fmServiceI;

	/**
	 * 社情民意Word生成
	 * 
	 * @param pollId
	 * @return void
	 */
	public String pollDownload(Tpoll poll) throws Exception {

		Map<String, Object> dataMap = getPollData(poll);
		Properties p = new Properties();
		p.load(this.getClass().getResourceAsStream("/config.properties")); // 读取config.properties中的路径
		String file = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/templet_poll.doc";
		String newfile = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/poll_" + poll.getTsecondary().getYear() + poll.getPollId()
				+ ".doc";
		copyFile(new File(file), new File(newfile));

		JacobToWord.download(newfile, dataMap);
		return newfile;

	}

	/**
	 * 委员履职Word生成
	 * 
	 * @param pollId
	 * @return void
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String fulFilDownload(String code, String secondaryCode,
			String year, Comm comm, List<Proposal> proposalList,
			List<Tpoll> pollList, List<Speech> speechList,
			List<MeetingMan> meetingManList, List<Activitypeo> activitypeoList)
			throws Exception {

		Map<String, Object> dataMap = getFulFilData(code, secondaryCode, year,
				comm, proposalList, pollList, speechList, meetingManList,
				activitypeoList);
		Properties p = new Properties();
		p.load(this.getClass().getResourceAsStream("/config.properties")); // 读取config.properties中的路径
		String file = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/templet_fulfil.doc";
		String newfile = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/fulfil_" + year + comm.getCode() + ".doc";
		copyFile(new File(file), new File(newfile));

		JacobToWord.download(newfile, dataMap);
		return newfile;

	}

	/**
	 * 学生基本信息Word生成
	 * 
	 * @param stuNo
	 * @return void
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String stuBasInfoDownload(String stuNo) throws Exception {

		Map<String, Object> dataMap = getStuBasInoData(stuNo);
		Properties p = new Properties();
		p.load(this.getClass().getResourceAsStream("/config.properties")); // 读取config.properties中的路径
		String file = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/templet_stuBaseinfo.doc";
		String newfile = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/stuBaseinfo_" + stuNo + ".doc";
		copyFile(new File(file), new File(newfile));

		JacobToWord.download(newfile, dataMap);
		return newfile;

	}

	/**
	 * 发言明细Word生成
	 * 
	 * @param pollId
	 * @return void
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String speechDownload(String speakId, String secondaryCode,
			String year, Speech speech, List<String> attList) throws Exception {

		Map<String, Object> dataMap = getSpeechData(speakId, secondaryCode,
				year, speech, attList);
		Properties p = new Properties();
		p.load(this.getClass().getResourceAsStream("/config.properties")); // 读取config.properties中的路径
		String file = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/templet_speech.doc";
		String newfile = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/speech_" + year + speech.getSpeakId() + ".doc";
		copyFile(new File(file), new File(newfile));

		JacobToWord.download(newfile, dataMap);
		return newfile;

	}

	/**
	 * Word生成
	 * 
	 * @param proposalId
	 * @return void
	 */
	public String download(String proposalId) throws Exception {

		Map<String, Object> dataMap = getData(proposalId);
		Properties p = new Properties();
		p.load(this.getClass().getResourceAsStream("/config.properties")); // 读取config.properties中的路径
		String file = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/templet.doc";
		String newfile = Constants.ROOTPATH + p.getProperty("wordPath")
				+ "/proposal_" + dataMap.get("propcode") + ".doc";
		copyFile(new File(file), new File(newfile));

		JacobToWord.download(newfile, dataMap);
		return newfile;

	}

	// 复制文件
	public void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	private Map<String, Object> getData(String proposalId) throws Exception {
		Proposal prop = new Proposal();
		prop.setProposalId(proposalId);
		prop = propService.findProp(prop);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Tsecondary seco = secoService.find(prop.getSecondaryId());
		if (seco == null) {
			dataMap.put("secondary", "");
		} else {
			if (Constants.CODE_TYPE_HYPS_HY.equals(seco.getPeriod())) {
				dataMap.put(
						"secondary",
						"第"
								+ seco.getSecondayName()
										.substring(
												0,
												seco.getSecondayName().indexOf(
														"届") + 1)
								+ "委员会第"
								+ seco.getSecondayName()
										.substring(
												seco.getSecondayName().indexOf(
														"届") + 1,
												seco.getSecondayName().length())
								+ "会议");
			} else {
				dataMap.put(
						"secondary",
						"第"
								+ seco.getSecondayName()
										.substring(
												0,
												seco.getSecondayName().indexOf(
														"届") + 1) + "委员会"
								+ seco.getYear() + "年度");
			}

		}
		if (prop.getProposalCode() == null) {
			dataMap.put("year", "");
		} else {
			dataMap.put(
					"year",
					prop.getProposalCode().substring(0,
							prop.getProposalCode().length() - 3));
		}

		if (prop.getProposalCode() == null) {
			dataMap.put("code", "");
		} else {
			dataMap.put(
					"code",
					prop.getProposalCode().substring(
							prop.getProposalCode().length() - 3));
		}

		if (prop.getProposalCode() == null) {
			dataMap.put("propcode", "");
		} else {
			dataMap.put("propcode", prop.getProposalCode());
		}

		if (prop.getPropTypeName() == null) {
			dataMap.put("type", "");
		} else {
			dataMap.put("type", prop.getPropTypeName());
		}

		if (prop.getTypeId() == null) {
			dataMap.put("typeid", "");
		} else {
			dataMap.put("typeid", prop.getTypeId());
		}

		if (prop.getTitle() == null) {
			dataMap.put("title", "");
		} else {
			if (prop.getTitle().length() > 25) {
				if (prop.getTitle().length() > 50) {
					dataMap.put("title", prop.getTitle().substring(0, 25));
					dataMap.put("title2", prop.getTitle().substring(25));
				} else {
					dataMap.put(
							"title",
							prop.getTitle().substring(0,
									(prop.getTitle().length() / 2)));
					dataMap.put(
							"title2",
							prop.getTitle().substring(
									(prop.getTitle().length() / 2)));
				}

			} else {
				dataMap.put("title", prop.getTitle());
				dataMap.put("title2", "");
			}
		}

		if (prop.getFistProposaler() == null) {
			dataMap.put("tar", "");
		} else {
			dataMap.put("tar", prop.getFistProposaler().replace(",", "、"));
		}

		if (prop.getWebFlgName() == null) {
			dataMap.put("web", "");
		} else {
			dataMap.put("web", prop.getWebFlgName());
		}

		if (prop.getUndertakeUnit() == null) {
			dataMap.put("unit", "");
		} else {
			dataMap.put("unit", prop.getUndertakeUnit());
		}

		if (prop.getInstructions() == null) {
			dataMap.put("instructions", "");
		} else {
			dataMap.put("instructions", prop.getInstructions());
		}

		if (prop.getFileMethod() == null) {
			dataMap.put("file", "");
			dataMap.put("checktime", "");
		} else {
			if (Constants.CODE_TYPE_LAXS_LA.equals(prop.getFileMethod())) {
				dataMap.put("file", "同意立案");
			} else {
				dataMap.put("file", "未立案");
			}
			dataMap.put("checktime", prop.getCheckTime());

		}
		if (prop.getContent() == null) {
			dataMap.put("content", "");
		} else {
			String filterStr = Util.filterHTMLTags(prop.getContent());
			dataMap.put("content", filterStr);
		}

		Proposaler proposaler = new Proposaler();
		proposaler.setProposalId(proposalId);
		List<Proposaler> listsponsor = sponsorService.find(proposaler);
		String addres = "";
		String posts = "";
		String phones = "";
		String emails = "";
		String seconds = "";
		for (Proposaler t : listsponsor) {
			if (t.getHostFlg().equals(Constants.CODE_TYPE_YESNO_YES)) {
				if (t.getComparyAddress() != null) {
					addres += t.getComparyAddress() + "、";
				}
				if (t.getComparyPost() != null) {
					posts += t.getComparyPost() + "、";
				}
				if (t.getEmail() != null) {
					emails += t.getEmail() + "、";
				}
				if (t.getTelephone() != null) {
					if (!"1".equals(t.getTelephone().substring(0, 1))) {
						phones += t.getTelephone().substring(
								t.getTelephone().length() - 11)
								+ "、";
					} else {
						phones += t.getTelephone() + "、";
					}
				}
			} else {
				if (t.getName() != null) {
					seconds += t.getName() + "、";
				}
			}
		}
		if (!"".equals(addres))
			addres = addres.substring(0, addres.length() - 1);
		System.out.println("address==" + addres);
		dataMap.put("add", addres);
		if (!"".equals(posts))
			posts = posts.substring(0, posts.length() - 1);
		dataMap.put("post", posts);
		if (!"".equals(emails))
			emails = emails.substring(0, emails.length() - 1);
		dataMap.put("email", emails);
		if (!"".equals(seconds))
			seconds = seconds.substring(0, seconds.length() - 1);
		dataMap.put("second", seconds);
		if (!"".equals(phones))
			phones = phones.substring(0, phones.length() - 1);
		dataMap.put("phone", phones);
		Hand hand = new Hand();
		hand.setProposalId(proposalId);
		List<Hand> listHands = handService.find(hand);
		String comps = "";
		if (listHands != null && listHands.size() > 0) {
			comps = "，建议转";
			for (Hand t : listHands) {
				comps += t.getCompanyName() + "、";
			}

		}
		if (!"".equals(comps)) {
			comps = comps.substring(0, comps.length() - 1);
			comps += "办理";
		}
		if (comps.length() > 17) {
			dataMap.put("comp", comps.substring(0, 17));
			dataMap.put("comp2", comps.substring(17, comps.length()));
		} else {
			dataMap.put("comp", comps);
			dataMap.put("comp2", "");
		}

		return dataMap;
	}

	private Map<String, Object> getPollData(Tpoll poll) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		if (poll.getPollCode() == null) {
			dataMap.put("code", "");
		} else {
			dataMap.put("code", poll.getPollCode());
		}

		if (poll.getMaster() == null) {
			dataMap.put("master", "");
		} else {
			dataMap.put("master", poll.getMaster());
		}

		if (poll.getTitle() == null) {
			dataMap.put("title", "");
		} else {
			if (poll.getTitle().length() > 11) {
				if (poll.getTitle().length() > 22) {
					dataMap.put("title", poll.getTitle().substring(0, 11));
					dataMap.put("title1", poll.getTitle().substring(11));
				} else {
					dataMap.put(
							"title",
							poll.getTitle().substring(0,
									(poll.getTitle().length() / 2)));
					dataMap.put(
							"title1",
							poll.getTitle().substring(
									(poll.getTitle().length() / 2)));
				}

			} else {
				dataMap.put("title", poll.getTitle());
				dataMap.put("title1", "");
			}
		}

		if (poll.getCopyMan() == null) {
			dataMap.put("copyMan", "");
		} else {
			dataMap.put("copyMan", poll.getCopyMan());
		}

		String today = Util.getInstance().getFormatDate(new Date(),
				"yyyy年MM月dd日");
		dataMap.put("date", today);

		if (poll.getEditorName() == null) {
			dataMap.put("editor", "");
		} else {
			dataMap.put("editor", poll.getEditorName());
		}

		if (poll.getIssuerName() == null) {
			dataMap.put("issuer", "");
		} else {
			dataMap.put("issuer", poll.getIssuerName());

		}
		System.out.println("content===" + poll.getEndContent());
		if (poll.getEndContent() == null) {
			dataMap.put("content", "");
		} else {
			String filterStr = Util.filterHTMLTags(poll.getEndContent());
			if (filterStr.length() > 175) {
				dataMap.put("content", filterStr.substring(0, 175));
				dataMap.put("content1", filterStr.substring(176));
			} else {
				dataMap.put("content", filterStr);
				dataMap.put("content1", "");
			}

		}
		return dataMap;
	}

	private Map<String, Object> getFulFilData(String code,
			String secondaryCode, String year, Comm comm,
			List<Proposal> proposalList, List<Tpoll> pollList,
			List<Speech> speechList, List<MeetingMan> meetingManList,
			List<Activitypeo> activitypeoList) throws Exception {
		Util util = new Util();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		if (util.isEmpty(year)) {
			dataMap.put("year", "");
		} else {
			dataMap.put("year", year);
		}
		if (util.isEmpty(comm.getName())) {
			dataMap.put("name", "");
		} else {
			dataMap.put("name", comm.getName());
		}
		if (util.isEmpty(comm.getSexName())) {
			dataMap.put("sex", "");
		} else {
			dataMap.put("sex", comm.getSexName());
		}
		if (util.isEmpty(comm.getNationName())) {
			dataMap.put("nation", "");
		} else {
			dataMap.put("nation", comm.getNationName());
		}
		if (util.isEmpty(comm.getPartyName())) {
			dataMap.put("party", "");
		} else {
			dataMap.put("party", comm.getPartyName());
		}
		if (util.isEmpty(comm.getCommitteeName())) {
			dataMap.put("committee", "");
		} else {
			dataMap.put("committee", comm.getCommitteeName());
		}
		if (util.isEmpty(comm.getCompanyName())) {
			dataMap.put("company", "");
		} else {
			dataMap.put("company", comm.getCompanyName());
		}
		if (util.isEmpty(comm.getBirthDate())) {
			dataMap.put("birth", "");
		} else {
			String birthDate = Util.getInstance().getFormatDate(
					comm.getBirthDate(), "yyyy-MM");
			dataMap.put("birth", birthDate);
		}
		// 提案
		if (proposalList != null && proposalList.size() > 0) {
			String proposalListString = "";
			for (int i = 0; i < proposalList.size(); i++) {
				Proposal proposal = proposalList.get(i);
				String fileMethod = "";
				// 已立案
				if (Constants.CODE_TYPE_LAXS_LA
						.equals(proposal.getFileMethod())) {
					fileMethod = "已立案";
					// 未立案
				} else if (Constants.CODE_TYPE_LAXS_BLA.equals(proposal
						.getFileMethod())) {
					fileMethod = "未立案";
				} else {
					fileMethod = "";
				}
				String propTypeName = util.isEmpty(proposal.getPropTypeName()) ? ""
						: proposal.getPropTypeName() + " ";
				proposalListString += propTypeName
						+ proposal.getTitle()
						+ " "
						+ Util.getInstance().getFormatDate(
								proposal.getCreateTime(), "yyyy-MM-dd") + " "
						+ fileMethod + "\n";
			}
			dataMap.put("proposalList", proposalListString);
		} else {
			dataMap.put("proposalList", "");
		}
		// 社情民意
		if (pollList != null && pollList.size() > 0) {
			String pollListString = "";
			for (int i = 0; i < pollList.size(); i++) {
				Tpoll poll = pollList.get(i);
				String adoptFlg = "";
				// 采用
				if (Constants.CODE_TYPE_YESNO_YES.equals(poll.getAdoptFlg())) {
					adoptFlg = "采用";
					// 未采用
				} else if (Constants.CODE_TYPE_YESNO_NO.equals(poll
						.getAdoptFlg())) {
					adoptFlg = "未采用";
				} else {
					adoptFlg = "";
				}
				pollListString += poll.getTitle()
						+ " "
						+ Util.getInstance().getFormatDate(
								poll.getCreateTime(), "yyyy-MM-dd") + " "
						+ adoptFlg + "\n";
			}
			dataMap.put("pollList", pollListString);
		} else {
			dataMap.put("pollList", "");
		}
		// 大会发言
		if (speechList != null && speechList.size() > 0) {
			String speechListString = "";
			for (int i = 0; i < speechList.size(); i++) {
				Speech speech = speechList.get(i);
				String meetTypeName = util.isEmpty(speech.getMeetTypeName()) ? ""
						: speech.getMeetTypeName() + " ";
				speechListString += meetTypeName
						+ speech.getTitle()
						+ " "
						+ Util.getInstance().getFormatDate(
								speech.getCreateTime(), "yyyy-MM-dd") + " "
						+ speech.getUseSituationName() + "\n";
			}
			dataMap.put("speechList", speechListString);
		} else {
			dataMap.put("speechList", "");
		}

		// 参加会议情况
		if (meetingManList != null && meetingManList.size() > 0) {
			String meetingManListString = "";
			for (int i = 0; i < meetingManList.size(); i++) {
				MeetingMan meetingMan = meetingManList.get(i);
				String meetTime = "";
				if (!util.isEmpty(meetingMan.getBeginTime())
						&& !util.isEmpty(meetingMan.getEndTime())) {
					meetTime = util.getFormatDate(meetingMan.getBeginTime(),
							"yyyy-MM-dd")
							+ "至"
							+ util.getFormatDate(meetingMan.getEndTime(),
									"yyyy-MM-dd");
				}
				String meetTypeName = util
						.isEmpty(meetingMan.getMeetTypeName()) ? ""
						: meetingMan.getMeetTypeName() + " ";
				meetingManListString += meetTypeName + meetingMan.getMeetName()
						+ " " + meetTime + " " + meetingMan.getStatusName()
						+ "\n";
			}
			dataMap.put("meetingManList", meetingManListString);
		} else {
			dataMap.put("meetingManList", "");
		}
		// 参加活动情况
		if (activitypeoList != null && activitypeoList.size() > 0) {
			String activitypeoListString = "";
			for (int i = 0; i < activitypeoList.size(); i++) {
				Activitypeo activitypeo = activitypeoList.get(i);
				String actTime = "";
				if (!util.isEmpty(activitypeo.getTimebeg())
						&& !util.isEmpty(activitypeo.getTimeend())) {
					actTime = util.getFormatDate(activitypeo.getTimebeg(),
							"yyyy-MM-dd")
							+ "至"
							+ util.getFormatDate(activitypeo.getTimeend(),
									"yyyy-MM-dd");
				}
				String aspeciesName = util.isEmpty(activitypeo
						.getAspeciesName()) ? "" : activitypeo
						.getAspeciesName() + " ";

				activitypeoListString += aspeciesName + activitypeo.getAtheme()
						+ " " + actTime + " " + activitypeo.getAgency() + " "
						+ activitypeo.getAstatusName() + "\n";
			}
			dataMap.put("activitypeoList", activitypeoListString);
		} else {
			dataMap.put("activitypeoList", "");
		}
		// 其他
		dataMap.put("other", "");
		// 当前日期
		String nowDate = util.getFormatDate(new Date(), "yyyy") + "年"
				+ util.getFormatDate(new Date(), "MM") + "月"
				+ util.getFormatDate(new Date(), "dd") + "日";
		dataMap.put("nowDate", nowDate);
		return dataMap;
	}

	private Map<String, Object> getSpeechData(String speakId,
			String secondaryCode, String year, Speech speech,
			List<String> attList) throws Exception {
		Util util = new Util();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (!util.isEmpty(speech.getYear())) {
			dataMap.put("year", speech.getYear());
		} else {
			dataMap.put("year", "");
		}
		if (!util.isEmpty(speech.getTitle())) {
			dataMap.put("title", speech.getTitle());
		} else {
			dataMap.put("title", "");
		}
		if (!util.isEmpty(speech.getMeetTypeName())) {
			dataMap.put("meetTypeName", speech.getMeetTypeName());
		} else {
			dataMap.put("meetTypeName", "");
		}
		if (!util.isEmpty(speech.getMeetName())) {
			dataMap.put("meetName", speech.getMeetName());
		} else {
			dataMap.put("meetName", "");
		}
		// 采用情况
		if (!util.isEmpty(speech.getUseSituationName())) {
			dataMap.put("useSituationName", speech.getUseSituationName());
		} else {
			dataMap.put("useSituationName", "");
		}
		// 发言人
		if (!util.isEmpty(speech.getName())) {
			dataMap.put("name", speech.getName());
		} else {
			dataMap.put("name", "");
		}
		if (!util.isEmpty(speech.getTelephone())) {
			dataMap.put("telephone", speech.getTelephone());
		} else {
			dataMap.put("telephone", "");
		}
		if (!util.isEmpty(speech.getEmail())) {
			dataMap.put("email", speech.getEmail());
		} else {
			dataMap.put("email", "");
		}
		if (!util.isEmpty(speech.getContent())) {
			String filterStr = Util.filterHTMLTags(speech.getContent());
			dataMap.put("content", filterStr);
		} else {
			dataMap.put("content", "");
		}
		return dataMap;
	}

	/*
	 * 学生基本信息
	 */
	private Map<String, Object> getStuBasInoData(String stuNo) throws Exception {
		Util util = new Util();
		Stu stu = stuServiceI.getStuArch(stuNo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (!util.isEmpty(stuNo)) {
			dataMap.put("name", util.isEmpty(stu.getName())?"":stu.getName());
			dataMap.put("sexName", util.isEmpty(stu.getSexName())?"":stu.getSexName());
			dataMap.put("nickName", util.isEmpty(stu.getNickName())?"":stu.getNickName());
			dataMap.put("birthDate", util.isEmpty(stu.getBirthDate())?"":stu.getBirthDate());
			dataMap.put("politicalName", util.isEmpty(stu.getPoliticalName())?"":stu.getPoliticalName());
			dataMap.put("nationName", util.isEmpty(stu.getNationName())?"":stu.getNationName());
			dataMap.put("nativePlace", util.isEmpty(stu.getNativePlace())?"":stu.getNativePlace());
			dataMap.put("birthPlace", util.isEmpty(stu.getBirthPlace())?"":stu.getBirthPlace());
			dataMap.put("identCardNo", util.isEmpty(stu.getIdentCardNo())?"":stu.getIdentCardNo());
			dataMap.put("mobilePhone", util.isEmpty(stu.getMobilePhone())?"":stu.getMobilePhone());
			dataMap.put("healthName", util.isEmpty(stu.getHealthName())?"":stu.getHealthName());
			dataMap.put("foreignTypeName", util.isEmpty(stu.getForeignTypeName())?"":stu.getForeignTypeName());
			dataMap.put("nowAdd", util.isEmpty(stu.getNowAdd())?"":stu.getNowAdd());
			dataMap.put("stuNo", util.isEmpty(stu.getStuNo())?"":stu.getStuNo());
			dataMap.put("deptName", util.isEmpty(stu.getDeptName())?"":stu.getDeptName());
			dataMap.put("majorName", util.isEmpty(stu.getMajorName())?"":stu.getMajorName());
			dataMap.put("className", util.isEmpty(stu.getClassName())?"":stu.getClassName());
			if (!util.isEmpty(stu.getFamilymemId())) {
				String[] familymemIds = stu.getFamilymemId().split(",");
				for (int i = 0; i <= 8; i++) {
					if(i+1>familymemIds.length) {
						dataMap.put("familyRole" + i, "");
						dataMap.put("name" + i, "");
						dataMap.put("unit" + i, "");
						dataMap.put("duty" + i, "");
						dataMap.put("tel" + i, "");
						continue;
					}
					FamilyMem fm = fmServiceI.get(familymemIds[i]);
					if (!util.isEmpty(fm)) {
						dataMap.put("familyRole" + i, util.isEmpty(fm.getFamilyRole())?"":fm.getFamilyRole());
						dataMap.put("name" + i, util.isEmpty(fm.getName())?"":fm.getName());
						dataMap.put("unit" + i, util.isEmpty(fm.getUnit())?"":fm.getUnit());
						dataMap.put("duty" + i, util.isEmpty(fm.getDuty())?"":fm.getDuty());
						dataMap.put("tel" + i, util.isEmpty(fm.getTel())?"":fm.getTel());
					}
				}
			} else {
				for (int i = 0; i <= 8; i++) {
					dataMap.put("familyRole" + i, "");
					dataMap.put("name" + i, "");
					dataMap.put("unit" + i, "");
					dataMap.put("duty" + i, "");
					dataMap.put("tel" + i, "");
				}
			}
			String path=ServletActionContext.getServletContext().getRealPath("/");
			dataMap.put("picAddr", util.isEmpty(stu.getPicName())?"":"IMG:"+path+"upload\\mobile\\"+stu.getPicName());
		} else {
			dataMap.put("stuNo", "");
		}
		// if (!util.isEmpty(speech.getTitle())) {
		// dataMap.put("title", speech.getTitle());
		// } else {
		// dataMap.put("title", "");
		// }
		// if (!util.isEmpty(speech.getMeetTypeName())) {
		// dataMap.put("meetTypeName", speech.getMeetTypeName());
		// } else {
		// dataMap.put("meetTypeName", "");
		// }
		// if (!util.isEmpty(speech.getMeetName())) {
		// dataMap.put("meetName", speech.getMeetName());
		// } else {
		// dataMap.put("meetName", "");
		// }
		// //采用情况
		// if (!util.isEmpty(speech.getUseSituationName())) {
		// dataMap.put("useSituationName", speech.getUseSituationName());
		// } else {
		// dataMap.put("useSituationName", "");
		// }
		// //发言人
		// if (!util.isEmpty(speech.getName())) {
		// dataMap.put("name", speech.getName());
		// } else {
		// dataMap.put("name", "");
		// }
		// if (!util.isEmpty(speech.getTelephone())) {
		// dataMap.put("telephone", speech.getTelephone());
		// } else {
		// dataMap.put("telephone", "");
		// }
		// if (!util.isEmpty(speech.getEmail())) {
		// dataMap.put("email", speech.getEmail());
		// } else {
		// dataMap.put("email", "");
		// }
		// if (!util.isEmpty(speech.getContent())) {
		// String filterStr=Util.filterHTMLTags(speech.getContent());
		// dataMap.put("content", filterStr);
		// } else {
		// dataMap.put("content", "");
		// }
		return dataMap;
	}

	public FmServiceI getFmServiceI() {
		return fmServiceI;
	}

	@Autowired
	public void setFmServiceI(FmServiceI fmServiceI) {
		this.fmServiceI = fmServiceI;
	}

	public PropServiceI getPropService() {
		return propService;
	}

	@Autowired
	public void setPropService(PropServiceI propService) {
		this.propService = propService;
	}

	public SponsorServiceI getSponsorService() {
		return sponsorService;
	}

	@Autowired
	public void setSponsorService(SponsorServiceI sponsorService) {
		this.sponsorService = sponsorService;
	}

	public HandServiceI getHandService() {
		return handService;
	}

	@Autowired
	public void setHandService(HandServiceI handService) {
		this.handService = handService;
	}

	public SecoServiceI getSecoService() {
		return secoService;
	}

	@Autowired
	public void setSecoService(SecoServiceI secoService) {
		this.secoService = secoService;
	}

	public StuServiceI getStuServiceI() {
		return stuServiceI;
	}

	@Autowired
	public void setStuServiceI(StuServiceI stuServiceI) {
		this.stuServiceI = stuServiceI;
	}
}
