package com.tlzn.util.base;

import java.util.ArrayList;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.Static;

/**
 * <p>
 * Description: 常量定义管理，对于用代码代表用意的情况，统一采用常量定义方式
 * 
 * @version 1.32
 * @author liup
 * @since jdk1.4 以上
 */
public class Constants {

	/**
	 * 图片目录路径
	 */
	public static String IMGPATH = "upload/mobile";
	public static String TEMPIMGPATH = "upload/mobile/temp";

	// ------------------- BEGIN
	// Generator常量---------------------------------------------------------//
	/**
	 * Generator
	 */
	public static final String SECONDARY_ID = "secondaryId";
	public static final String LOGCODE_ID = "logCode";
	// 模块ID
	public static final String MODULE_CODE = "0";// 系统
	public static final String MODULE_CODE_PROP = "262a6415-504b-4d10-a964-6f0ebbf867b6";// 提案
	public static final String MODULE_CODE_MAINPAGE = "360b9d5e-82d1-42ab-9b34-b4b0d179ca92";// 综合首页
	// -------------------
	// 角色常量---------------------------------------------------------//
	/**
	 * Generator
	 */
	public static final String CBDW_ROLE_CID = "ccb75673-4cc3-4d3b-b93b-ad02c208e076"; // "承办单位"角色ID
	public static final String TAR_ROLE_CID = "1d2b7fa2-7b1f-44f9-bc39-afecd258d3e1"; // "提案人"角色ID
	public static final String ZFB_ROLE_CID = "96ce0f21-62a7-4fa8-89f7-221489312e6e"; // "政府办"角色ID
	public static final String SWB_ROLE_CID = "f254f6cf-a5db-4bde-a492-c2725ee2e373"; // "市委办"角色ID
	public static final String RD_ROLE_CID = "c39f8a7b-a9c7-4494-80b5-2daacea753ec"; // "人大"角色ID
	public static final String ZX_ROLE_CID = "d66bf373-13b8-467d-9e66-8b99097d200a"; // "政协"角色ID
	public static final String FY_ROLE_CID = "be85d554-9b4c-4387-b84d-886ee65bbf70"; // "法院"角色ID
	public static final String JCY_ROLE_CID = "a49facd1-d0b7-4e05-bf01-67ade4d28dc6"; // "检察院"角色ID
	public static final String JFQ_ROLE_CID = "f4d646ca-b786-4563-84d6-8d3ab7206b6e"; // "军分区"角色ID
	public static final String GLY_ROLE_CID = "137abaa8-62ac-494c-8b1a-f3397de2d3ce"; // "管理员"角色ID
	public static final String POLLXXY_ROLE_CID = "6e86f5a4-a17a-4512-af2c-74a2c122d371"; // "社情民意信息员"角色ID
	public static final String POLLSP_ROLE_CID = "2f68bf81-0dcb-4489-96f1-bf5139596ffd"; // "社情民意审批"角色ID
	public static final String POLLSQ_ROLE_CID = "6e0f6a04-e7f4-4d3c-bea5-df57d9efe87b"; // "社情民意签发"角色ID

	public static final String ZFB_CADE_CID = "2"; // "政府办"角色ID
	public static final String SWB_CADE_CID = "1"; // "市委办"角色ID
	public static final String RD_CADE_CID = "3"; // "人大"角色ID
	public static final String ZX_CADE_CID = "4"; // "政协"角色ID
	public static final String FY_CADE_CID = "5"; // "法院"角色ID
	public static final String JCY_CADE_CID = "6"; // "检察院"角色ID
	public static final String JFQ_CADE_CID = "99"; // "其它"角色ID

	public static final String NEWSTU_ROLE_CID = "7bff1d4f-0778-47b1-b240-1dea03ada562"; // "新注册学生"角色ID
	public static final String NEWTEACHER_ROLE_CID = "018b2c73-ae3f-4a28-99b5-473371813e7d"; // "新注册教师"角色ID
	// ------------------- BEGIN
	// 码表中代码定义---------------------------------------------------------//
	/**
	 * 用户组别
	 */
	public static final String DIC_TYPE_YHZB = "YHZB";
	public static final String DIC_TYPE_YHZB_WY = "1";
	public static final String DIC_TYPE_YHZB_CBDW = "2";
	public static final String DIC_TYPE_YHZB_GLY = "3";
	public static final String DIC_TYPE_YHZB_LDSP = "4";
	public static final String DIC_TYPE_YHZB_XXY = "5";
	public static final String DIC_TYPE_YHZB_XXCZ = "6";
	public static final String DIC_TYPE_YHZB_MSZ = "7";
	
	public static final String DIC_TYPE_YHZB_STU = "8"; 		   //学生
	public static final String DIC_TYPE_YHZB_TEACHER = "9"; 	   //教师
	public static final String DIC_TYPE_YHZB_MAINTEACHER = "10";   //班主任
	public static final String DIC_TYPE_YHZB_TEACHERLEADER = "11"; //教务处处长
	public static final String DIC_TYPE_YHZB_MANAGER = "12"; 	   //管理员
	public static final String DIC_TYPE_YHZB_ADMIN= "13"; 		   //超级管理员

	/**
	 * 提案人组别
	 */
	public static final String DIC_TYPE_GROUP = "GROUP";
	public static final String DIC_TYPE_GROUP_WY = "1";
	public static final String DIC_TYPE_GROUP_ZWH = "2";
	public static final String DIC_TYPE_GROUP_DPTT = "3";
	public static final String DIC_TYPE_GROUP_JB = "4";
	public static final String DIC_TYPE_GROUP_XZ = "5";
	public static final String DIC_TYPE_GROUP_QXQ = "6";
	/**
	 * 是否状态
	 */
	public static final String CODE_TYPE_YESNO = "YESNO";
	public static final String CODE_TYPE_YESNO_NO = "0";
	public static final String CODE_TYPE_YESNO_YES = "1";
	/**
	 * 启用/停用状态
	 */
	public static final String CODE_TYPE_QYTY = "STATUS";
	public static final String CODE_TYPE_QYTY_NO = "0";
	public static final String CODE_TYPE_QYTY_YES = "1";
	/**
	 * 未发布/已发布状态
	 */
	public static final String CODE_TYPE_PUBSTATUS = "PUBSTATUS";
	public static final String CODE_TYPE_PUBSTATUS_NO = "0";
	public static final String CODE_TYPE_PUBSTATUS_YES = "1";
	/**
	 * 会上/平时状态
	 */
	public static final String CODE_TYPE_HYPS = "HYPS";
	public static final String CODE_TYPE_HYPS_HY = "0";
	public static final String CODE_TYPE_HYPS_PS = "1";
	/**
	 * 提案状态
	 */
	public static final String CODE_TYPE_TAZT = "TAZT";
	public static final String CODE_TYPE_TAZT_YBC = "0";
	public static final String CODE_TYPE_TAZT_WSC = "1";
	public static final String CODE_TYPE_TAZT_YSC = "2";
	public static final String CODE_TYPE_TAZT_WBL = "3";
	public static final String CODE_TYPE_TAZT_YBL = "4";
	public static final String CODE_TYPE_TAZT_WSP = "5";
	public static final String CODE_TYPE_TAZT_YSP = "6";
	public static final String CODE_TYPE_TAZT_STZ = "7";

	/**
	 * 提案提交状态
	 */
	public static final String CODE_TYPE_TJZT = "TJZT";
	public static final String CODE_TYPE_TJZT_WTJ = "0";
	public static final String CODE_TYPE_TJZT_YTJ = "1";
	/**
	 * 办复状态
	 */
	public static final String CODE_TYPE_HFZT = "HFZT";
	public static final String CODE_TYPE_HFZT_NEW = "1";
	public static final String CODE_TYPE_HFZT_STZ = "2";
	public static final String CODE_TYPE_HFZT_YQS = "3";
	public static final String CODE_TYPE_HFZT_YBF = "4";
	/**
	 * 立案形式
	 */
	public static final String CODE_TYPE_LAXS = "LAXS";
	public static final String CODE_TYPE_LAXS_LA = "0";
	public static final String CODE_TYPE_LAXS_BLA = "1";
	/**
	 * 提案类型
	 */
	public static final String CODE_TYPE_TALX = "TALX";
	// 委员提案
	public static final String CODE_TYPE_TALX_WYTA = "1";
	// 党派提案
	public static final String CODE_TYPE_TALX_DPTA = "2";
	// 界别提案
	public static final String CODE_TYPE_TALX_JBTA = "3";
	// 团体提案
	public static final String CODE_TYPE_TALX_TTTA = "4";
	// 专委会提案
	public static final String CODE_TYPE_TALX_ZWHTA = "5";
	/**
	 * 提案办理类型
	 */
	public static final String CODE_TYPE_BLLX = "BLLX";
	// 单办
	public static final String CODE_TYPE_BLLX_DB = "1";
	// 分办
	public static final String CODE_TYPE_BLLX_FB = "2";
	// 会办
	public static final String CODE_TYPE_BLLX_HB = "3";
	/**
	 * 办复审查状态
	 */
	public static final String CODE_TYPE_BFSC = "BFSC";
	// 驳回
	public static final String CODE_TYPE_BFSC_WTG = "0";
	// 通过
	public static final String CODE_TYPE_BFSC_YTG = "1";
	/**
	 * 沟通方式
	 */
	public static final String CODE_TYPE_GTFS = "GTFS";
	public static final String CODE_TYPE_GTFS_MT = "1"; // 面谈
	public static final String CODE_TYPE_GTFS_ZTH = "2"; // 座谈会
	public static final String CODE_TYPE_GTFS_PHONE = "3"; // 电话
	public static final String CODE_TYPE_GTFS_EMAIL = "4"; // 邮件
	public static final String CODE_TYPE_GTFS_SX = "5"; // 书信
	public static final String CODE_TYPE_GTFS_QT = "6"; // 其它

	/**
	 * 委员满意度
	 */
	public static final String CODE_TYPE_WYYJ = "WYYJ";
	// 满意
	public static final String CODE_TYPE_WYYJ_MY = "1";
	// 基本满意
	public static final String CODE_TYPE_WYYJ_JBMY = "2";
	// 不满意
	public static final String CODE_TYPE_WYYJ_BMY = "3";
	/**
	 * 解决程度
	 */
	public static final String CODE_TYPE_JJCD = "JJCD";
	// 解决或基本解...
	public static final String CODE_TYPE_JJCD_A = "1";
	// 列入计划拟解...
	public static final String CODE_TYPE_JJCD_B = "2";
	// 因条件所限等...
	public static final String CODE_TYPE_JJCD_C = "3";
	/**
	 * 编编状态
	 */
	public static final String CODE_TYPE_UPDATE = "UPDATE";
	public static final String CODE_TYPE_UPDATE_YES = "1";
	public static final String CODE_TYPE_UPDATE_NO = "0";

	/**
	 * 附件保存类型
	 */
	public static final String CODE_TYPE_FJBCLX = "FJBCLX";
	// 数据库
	public static final String CODE_TYPE_FJBCLX_SJK = "1";
	// 文件
	public static final String CODE_TYPE_FJBCLX_WJ = "2";
	/**
	 * 性别
	 */
	public static final String CODE_TYPE_ID_SEX = "SEX";
	// 女
	public static final String CODE_TYPE_SEX_WOMAN = "2";
	// 男
	public static final String CODE_TYPE_SEX_MAN = "1";

	/**
	 * 工作单位
	 */
	public static final String CODE_TYPE_ID_JOB = "JOB";

	/**
	 * 界别
	 */
	public static final String CODE_TYPE_ID_CIRCLE = "CIRCLE";

	/**
	 * 专委会
	 */
	public static final String CODE_TYPE_ID_COMMITTEE = "COMMITTEE";

	/**
	 * 单位类型
	 */
	public static final String CODE_TYPE_COMPANYTYPE = "COMPANYTYPE";
	public static final String CODE_TYPE_COMPANYTYPE_DQ = "1"; // 党群
	public static final String CODE_TYPE_COMPANYTYPE_ZF = "2"; // 政府
	public static final String CODE_TYPE_COMPANYTYPE_RD = "3"; // 人大
	public static final String CODE_TYPE_COMPANYTYPE_ZX = "4"; // 政协
	public static final String CODE_TYPE_COMPANYTYPE_FY = "5"; // 法院
	public static final String CODE_TYPE_COMPANYTYPE_JCY = "6"; // 检察院
	public static final String CODE_TYPE_COMPANYTYPE_QT = "99"; // 其它
	/**
	 * 地区
	 */
	public static final String CODE_TYPE_ID_BSAREA = "BSAREA";
	/**
	 * 省
	 */
	public static final String CODE_TYPE_ID_BSPROVINCE = "BSPROVINCE";
	/**
	 * 市
	 */
	public static final String CODE_TYPE_ID_BSCITY = "BSCITY";
	/**
	 * 分公司
	 */
	public static final String CODE_TYPE_ID_BSCOMPANY = "BSCOMPANY";
	/**
	 * 默认的记录数
	 */
	public static final String CODE_TYPE_ID_EC_TOTALROWS = "11";
	/**
	 * 基础代码维护之状态
	 */
	public static final String CODE_TYPE_ID_DMZT = "DMZT";
	/**
	 * 系统功能模块
	 */
	public static final String CODE_TYPE_SYSMODULE = "SYSMODULE";

	// ------------------- END
	// 码表中代码定义---------------------------------------------------------//
	// -------------------BEGIN
	// 社情民意代码定义---------------------------------------------------------//
	/**
	 * 社情民意状态
	 */
	public static final String CODE_TYPE_POLL_STATUS = "POLLSTATUS";
	public static final String CODE_TYPE_POLL_STATUS_YBC = "0";
	public static final String CODE_TYPE_POLL_STATUS_WSC = "1";
	public static final String CODE_TYPE_POLL_STATUS_WCY = "2";
	public static final String CODE_TYPE_POLL_STATUS_YCY = "3";
	public static final String CODE_TYPE_POLL_STATUS_YSH = "4";
	public static final String CODE_TYPE_POLL_STATUS_YSQ = "5";
	public static final String CODE_TYPE_POLL_STATUS_YF = "6";
	public static final String CODE_TYPE_POLL_STATUS_ZBZ = "7";
	public static final String CODE_TYPE_POLL_STATUS_WBL = "8";
	public static final String CODE_TYPE_POLL_STATUS_YBL = "9";
	public static final String CODE_TYPE_POLL_STATUS_STZ = "10";
	/**
	 * 社情民意类型
	 */
	public static final String DIC_TYPE_POLLTYPE = "POLLTYPE";
	public static final String DIC_TYPE_POLLTYPE_WY = "1";
	public static final String DIC_TYPE_POLLTYPE_ZWH = "2";
	public static final String DIC_TYPE_POLLTYPE_QXQ = "3";
	public static final String DIC_TYPE_POLLTYPE_SHZJ = "4";
	public static final String DIC_TYPE_POLLTYPE_QT = "9";
	/**
	 * 计分类型
	 */
	public static final String DIC_TYPE_RULESTYPE = "RULESTYPE";
	public static final String DIC_TYPE_RULESTYPE_SJBM = "1"; // 上级部门
	public static final String DIC_TYPE_RULESTYPE_MT = "2"; // 媒体
	public static final String DIC_TYPE_RULESTYPE_LDPS = "3"; // 领导批示

	/**
	 * 会议发言状态
	 */
	public static final String CODE_TYPE_SPEECH_STATUS = "SPEECHSTATUS";
	public static final String CODE_TYPE_SPEECH_STATUS_YBC = "0";
	public static final String CODE_TYPE_SPEECH_STATUS_WSC = "1";
	public static final String CODE_TYPE_SPEECH_STATUS_YSC = "2";
	public static final String CODE_TYPE_SPEECH_STATUS_DG = "3";
	
	/**
	 * 会议发言采用情况
	 */
	public static final String CODE_TYPE_SPEECHADOPTED_STATUS = "USESITUATION";
	public static final String CODE_TYPE_SPEECHADOPTED_STATUS_SMFY = "1";
	public static final String CODE_TYPE_SPEECHADOPTED_STATUS_KTFY = "2";
	public static final String CODE_TYPE_SPEECHADOPTED_STATUS_ZQ = "3";
	
	/**
	 * 会议转弃方向
	 */
	public static final String CODE_TYPE_SPEECH_DISCARDTYPE = "DISCARDTYPE";
	public static final String CODE_TYPE_SPEECH_DISCARDTYPE_POLL = "1";
	// -------------------END
	// 社情民意代码定义---------------------------------------------------------//
	// -------------------BEGIN
	// 日志代码定义---------------------------------------------------------//

	/**
	 * 日志类型
	 */
	public static final String LOG_TYPE_CODE = "LOGTYPE";
	public static final String LOG_TYPE_CODE_SYS = "1";
	public static final String LOG_TYPE_CODE_PROP = "2";
	public static final String LOG_TYPE_CODE_POLL = "3";
	public static final String LOG_TYPE_CODE_DAILYWORK = "4";
	public static final String LOG_TYPE_CODE_SPEECH = "5";
	public static final String LOG_TYPE_CODE_MEETING = "6";
	public static final String LOG_TYPE_CODE_ACT = "7";
	public static final String LOG_TYPE_CODE_MEMBER = "8";
	public static final String LOG_TYPE_CODE_ARCH = "9";
	public static final String LOG_TYPE_CODE_BASINFOPERS = "10";
	public static final String LOG_TYPE_CODE_FAMILY = "11";
	public static final String LOG_TYPE_CODE_STU = "12";
	public static final String LOG_TYPE_CODE_COURSE = "13";
	public static final String LOG_TYPE_CODE_TEACHER = "14";
	public static final String LOG_TYPE_CODE_PROFESS = "15";
	public static final String LOG_TYPE_CODE_PROFESSTIME = "16";
	/**
	 * 日志类型
	 */
	public static final String LOG_TYPE_OPERTYPE = "OPERTYPE";
	public static final String LOG_TYPE_OPERTYPE_LOGIN = "0";
	public static final String LOG_TYPE_OPERTYPE_ADD = "1";
	public static final String LOG_TYPE_OPERTYPE_EDIT = "2";
	public static final String LOG_TYPE_OPERTYPE_DEL = "3";
	public static final String LOG_TYPE_OPERTYPE_CHECK = "4";
	public static final String LOG_TYPE_OPERTYPE_SET = "5";
	public static final String LOG_TYPE_OPERTYPE_BACK = "6";
	public static final String LOG_TYPE_OPERTYPE_REPLY = "7";
	public static final String LOG_TYPE_OPERTYPE_SUBMIT = "8";
	public static final String LOG_TYPE_OPERTYPE_REPORT = "9";

	// -------------------
	// END日志代码定义---------------------------------------------------------//

	// -------------------
	// BEGIN状态定义（通用）---------------------------------------------------------//

	/**
	 * 未填城市的名称
	 */
	public static final String City_Null = "不详";// 没有填城市
	/**
	 * 有效标志
	 */
	public static final String CODE_TYPE_ID_YXBZ = "YXBZ";
	/**
	 * 激活、是、有效、未终止(都可用此标识)
	 */
	public static final String ACTIVE_TRUE = "1";
	/**
	 * 停止、否、无效、已终止(都可用此标识)
	 */
	public static final String ACTIVE_FALSE = "0";

	/**
	 * 系统初始化(锁定)状态，不能删除
	 */
	public static final String ACTIVE_LOCK = "44";
	/**
	 * 系统自动操作时的操作人
	 */
	public static final String SYS_UPDATEMAN = "sys";
	// -------------------
	// END状态定义（通用）----------------------------------------------------------//

	// -------------------
	// BEGIN数据内存缓存定义-------------------------------------------------------------//
	/**
	 * 所有代码定义（Codedefine对象集合）
	 */
	public static ArrayList LIST_ALL_CODEDEFINE = null;
	/**
	 * 所有权限资源URL（URL String对象集合）
	 */
	public static ArrayList LIST_ALL_RIGHTURL = null;
	/**
	 * 所有产品类别列表（信息类别）
	 */
	public static ArrayList PRODUCT_ALL_TYPE_LIST = null;
	/**
	 * 所有栏目列表
	 */
	public static ArrayList COLUMNS_ALL_LIST = null;
	/**
	 * 所有栏目内容来源设置产品类别列表
	 */
	public static ArrayList COLUMNS_PRODUCT_TYPE_ALL_LIST = null;
	/**
	 * 所有自定义信息属性
	 */
	public static ArrayList LIST_ALL_INFOATTRIBUTE = null;
	/**
	 * 所有发布的信息
	 */
	public static ArrayList LIST_ALL_INFO = null;
	/**
	 * 所有权限授权信息
	 */
	public static ArrayList LIST_ALL_ACL = null;
	// -------------------
	// END数据内存缓存定义---------------------------------------------------------------//

	// ------------------- BEGIN
	// 系统初始化路径定义（系统在启动时就初始化）--------------------------------//
	/**
	 * 应用服务名（如：hqm ）
	 */
	public static String CONTEXTPATH = "";
	/**
	 * 系统应用目录路径(如：/home/tomcat5/webapps/)
	 */
	public static String ROOTPATH = "";
	/**
	 * 访问基本路径(如：http://www.nmtlzn.com/)
	 */
	public static String BASEURL = "";
	/**
	 * 编码类型(在web.xml中设置)
	 */
	public static String ENCODING = "";
	// ------------------- END
	// 系统初始化路径定义（系统在启动时就初始化）--------------------------------//
	// ------------------- BEGIN
	// 会话session关键字定义定义-------------------------------------------------//
	/**
	 * VIP系统后台用户信息会话session关键字定义
	 */
	public static final String SESSION_VIP_USER_MAINTAIN = "SESSION_VIP_USER_MAINTAIN";
	/**
	 * HQM系统后台用户信息会话session关键字定义
	 */
	public static final String SESSION_HQM_USER_MAINTAIN = "SESSION_HQM_USER_MAINTAIN";

	// ------------------- END
	// 会话session关键字定义定义---------------------------------------------------------//
	// -------------------
	// BEGIN用户类型定义-------------------------------------------------------------//
	/**
	 * HQM总部用户
	 */
	public static final String USER_HQM_TYPE = "8";
	/**
	 * 店铺用户（VIP系统使用用户）
	 */
	public static final String USER_VIP_TYPE = "9";
	/**
	 * 系统管理员（超级用户）
	 */
	public static final String USER_SUPER_TYPE = "10";
	// -------------------
	// END用户类型定义---------------------------------------------------------------//

	// ------------------- BEGIN
	// 系统消息定义-------------------------------------------------------------//
	/**
	 * 报表打印时间段符号
	 */
	public static final String REPORT_DATE_TITLE = " 至 ";
	/**
	 * 物理删除
	 */
	public static final String DELETE_PHYSIC = "彻底删除";
	/**
	 * 物理删除提示信息
	 */
	public static final String DELETE_PHYSIC_AFFIRM = "点击确定后，当前界面没有保存的商品信息丢失，是否确定要删除？";
	/**
	 * 系统基础表编码和名称分隔符定义
	 */
	public static final String SYS_SEPARAT = "/";
	/**
	 * 系统中无效数据标识符定义
	 */
	public static final String SYS_INVALID_SEPARAT = "＊";
	/**
	 * 系统中下拉框默认提示信息
	 */
	public static final String SYS_TITLE = "全 部";
	/**
	 * 试题答案分隔符 如：A|B|C|D
	 */
	public static final String CODE_SPLIT_QUESTION = "|";
	// ------------------- END
	// 系统消息定义-------------------------------------------------------------//

	/**
	 * 数据库类型
	 */
	public static String DATABASE_TYPE = "";

	// ------------------- BEGIN 属性类型定义--------------------------------//
	/**
	 * 简单文字型(1-100个字)
	 */
	public static final String ATTRIBUTE_INPUT_TYPE = "1";
	/**
	 * 文本型
	 */
	public static final String ATTRIBUTE_TEXT_TYPE = "2";
	/**
	 * 图文混合型
	 */
	public static final String ATTRIBUTE_CLOBTEXT_TYPE = "3";
	/**
	 * 日期型
	 */
	public static final String ATTRIBUTE_DATE_TYPE = "4";
	/**
	 * 下拉框型
	 */
	public static final String ATTRIBUTE_SELECT_TYPE = "5";
	/**
	 * 图片类型
	 */
	public static final String ATTRIBUTE_PICTURE_TYPE = "6";
	/**
	 * 文件类型
	 */
	public static final String ATTRIBUTE_FILE_TYPE = "7";
	// ------------------- END 属性类型定义--------------------------------//
	// -------------------------BEGIN 模板类别定义----------------------------//
	/**
	 * 类别种类：产品类别
	 */
	public static final String PRODUCTTYPE_PROD = "1";
	/**
	 * 类别种类：内容类别
	 */
	public static final String PRODUCTTYPE_RES = "2";
	/**
	 * 主页面模板
	 */
	public static final String TEMPLATE_INDEX_PAGE_TYPE = "1";
	/**
	 * 栏目模板
	 */
	public static final String TEMPLATE_COLUMN_TYPE = "2";
	/**
	 * 详细页面模板
	 */
	public static final String TEMPLATE_DETAIL_PAGE_TYPE = "3";
	/**
	 * 查询搜索结果模板
	 */
	public static final String TEMPLATE_SEARCH_TYPE = "4";
	// -------------------------END 模板类别定义----------------------------//
	// -------------------------BEGIN 栏目类型定义----------------------------//
	/**
	 * 功能型栏目
	 */
	public static final String COLUMN_FUNCTION_KIND = "11";
	/**
	 * 动态内容栏目
	 */
	public static final String COLUMN_DYNAMIC_KIND = "12";
	/**
	 * 带条件动态内容栏目
	 */
	public static final String COLUMN_DYNAMIC_BY_KIND = "13";
	/**
	 * 静态编辑栏目
	 */
	public static final String COLUMN_STATIC_KIND = "14";
	// -------------------------END 栏目类型定义----------------------------//
	// -------------------------BEGIN 栏目内容提取条件定义----------------------------//
	/**
	 * 按标签中指定条件提取产品
	 */
	public static final String COLUMN_CONTENT_BY_AA = "AA";
	/**
	 * 列出所有有效产品大类
	 */
	public static final String COLUMN_CONTENT_BY_BB = "BB";
	/**
	 * 按类别typeID提取该类下的所有信息[包括子类下的信息]（注：从栏目信息发布库中提取）
	 */
	public static final String COLUMN_CONTENT_BY_C = "C";
	/**
	 * 按类别typeID只提取该类下的信息（注：从信息库中提取）
	 */
	public static final String COLUMN_CONTENT_BY_G = "G";
	/**
	 * 按类别typeID提取该类下的所有信息[包括子类下的信息]（注：从信息库中提取）
	 */
	public static final String COLUMN_CONTENT_BY_K = "K";
	// -------------------------END 栏目内容提取条件定义----------------------------//
	// -------------------
	// BEGIN生成静态页面截取页面的始终标记--------------------------------//
	/**
	 * 开始标记
	 */
	public static final String HTML_BEGIN_TAG = "<!--HTML_BEGIN_TAG-->";
	/**
	 * 结束标记
	 */
	public static final String HTML_END_TAG = "<!--HTML_END_TAG-->";
	// ------------------- END生成静态页面截取页面的始终标记--------------------------------//
	// -------------------------BEGIN模板引用标记（尹德明于2005-11-22添加）----------------------------//
	/**
	 * 模板被引用
	 */
	public static final boolean TEMPLATE_USED = true;
	/**
	 * 模板未被引用
	 */
	public static final boolean TEMPLATE_NOT_USED = false;
	// -------------------------END模板引用标记----------------------------//
	// -------------------------BEGIN
	// 类别的ID定义（包括基类ID、类别ID）基类ID----------------------------//
	/**
	 * 基类ID（父类）标识
	 */
	public static final String PARENTID_BASE_KEY = "-1";
	/**
	 * 公告信息typeid
	 */
	public static final String GGXX_TYPEID = "4028e4b821f167380121f1c22f370001";

	/**
	 * 根据信息类别ID得到该类下的所有子类的TYPEID的SQL语句定义
	 */
	public static final String getAllChildTypeID(String typeid) {
		return "select CONSTANT_PT.typeid from Producttype CONSTANT_PT start with CONSTANT_PT.typeid='"
				+ typeid.trim()
				+ "' connect by prior CONSTANT_PT.typeid=CONSTANT_PT.parentid";
	}

	// -------------------------END
	// 类别的ID定义（包括基类ID、类别ID）基类ID---------------------------//
	// -------------------------BEGIN 人员类型标识----------------------------//
	/**
	 * 系统管理员（超级用户）
	 */
	public static final String USER_SYS_TYPE = "1";
	/**
	 * 全局用户
	 */
	public static final String USER_PUB_TYPE = "2";
	/**
	 * 普通用户
	 */
	public static final String USER_COMM_TYPE = "3";
	// -------------------------END 人员类型标识---------------------------//
	/**
	 * 信息属性自定义预留属性字段
	 */
	public static final String INFORMATION_CUSTOM_ATTRIBUTE_FIELD[] = {
			"attributeColumn1", "attributeColumn2", "attributeColumn3",
			"attributeColumn4", "attributeColumn5", "attributeColumn6",
			"attributeColumn7", "attributeColumn8", "attributeColumn9",
			"attributeColumn10", "attributeColumn11", "attributeColumn14",
			"attributeColumn15", "attributeColumn17", "attributeColumn18",
			"attributeColumn19", "attributeColumn20", };
	/**
	 * 信息属性自定义预留属性字段（图文混合类型字段）
	 */
	public static final String INFORMATION_CUSTOM_ATTRIBUTE_FIELD_CLOBTEXT[] = {
			"html_column", "attributeColumn12", "attributeColumn13",
			"attributeColumn16", };
	/**
	 * 订单属性自定义预留属性字段
	 */
	public static final String ORDER_CUSTOM_ATTRIBUTE_FIELD[] = {
			"attributeColumn1", "attributeColumn2", "attributeColumn3",
			"attributeColumn4", "attributeColumn5", "attributeColumn6",
			"attributeColumn7", "attributeColumn8", "attributeColumn9",
			"attributeColumn10", "attributeColumn11", "attributeColumn12",
			"attributeColumn13", "attributeColumn14", "attributeColumn15",
			"attributeColumn16", "attributeColumn17", "attributeColumn18",
			"attributeColumn19", "attributeColumn20", };


	/**
	 * 短信类型
	 */
	public static final String CODE_TYPE_XXFL = "XXFL";
	public static final String CODE_TYPE_XXFL_FWFZ = "1";
	public static final String CODE_TYPE_XXFL_SPFFZ = "2";

	// BEGIN活动类型定义-------------------------------------------------------------//
	
	/**
	 * 会议发布单位名称
	 */
	public static final String ACT_TYPE_PUB_DEPT = "政协巴彦淖尔市委员会";//
	
	/**
	 * 活动状态
	 */
	public static final String ACT_TYPE_STATUS = "HSTATUS";//
	public static final String ACT_TYPE_STATUS_YFB = "1";// 已发布
	public static final String ACT_TYPE_STATUS_WFB = "2";// 未发布
	public static final String ACT_TYPE_STATUS_BJ = "3"; // 办结
	/**
	 * 活动种类
	 */
	public static final String ACT_TYPE_ASPECIES = "ASPECIES";//
	public static final String ACT_TYPE_ASPECIES_SCHD = "1";// 视察活动
	public static final String ACT_TYPE_ASPECIES_MSDY = "2";// 民生活动
	public static final String ACT_TYPE_ASPECIES_KCHD = "3";// 考察活动
	public static final String ACT_TYPE_ASPECIES_QTHD = "4";// 其他活动

	/**
	 * 活动状态
	 */
	public static final String ACT_TYPE_ASTATUS = "ASTATUS";//
	public static final String ACT_TYPE_ASTATUS_CX = "1";// 出席
	public static final String ACT_TYPE_ASTATUS_QJ = "2";// 请假
	public static final String ACT_TYPE_ASTATUS_QX = "3";// 缺席

	/**
	 * 出席状态
	 */
	public static final String PRESENT_TYPE_STATUS = "PRESENTSTATE";//
	public static final String PRESENT_TYPE_STATUS_CX = "1";// 出席
	public static final String PRESENT_TYPE_STATUS_QJ = "2";// 请假
	public static final String PRESENT_TYPE_STATUS_QX = "3";// 缺席
	/**
	 * 活动请假类型
	 */
	public static final String ACT_TYPE_LEAVE = "LEAVE";//
	public static final String ACT_TYPE_LEAVE_CG = "1";// 出国
	public static final String ACT_TYPE_LEAVE_CC = "2";// 出差
	public static final String ACT_TYPE_LEAVE_SB = "3";// 生病
	public static final String ACT_TYPE_LEAVE_QT = "4";// 其它

	/**
	 * 请假类型
	 */
	public static final String CODE_TYPE_LEAVE = "LEAVETYPE";//
	public static final String CODE_TYPE_LEAVE_CG = "1";// 出国
	public static final String CODE_TYPE_LEAVE_CC = "2";// 出差
	public static final String CODE_TYPE_LEAVE_SB = "3";// 生病
	public static final String CODE_TYPE_LEAVE_QT = "4";// 其它
	// -------------------
	// END活动类型定义---------------------------------------------------------------//
	// -------------------
	// BEGIN会议类型定义-------------------------------------------------------------//
	/**
	 * 会议发布单位名称
	 */
	public static final String MEETING_TYPE_PUB_DEPT = "政协巴彦淖尔市委员会";//
	
	/**
	 * 会议状态
	 */
	public static final String MEETING_TYPE_STATUS = "MEETINGSTATUS";//
	public static final String MEETING_TYPE_STATUS_YFB = "2";// 已发布
	public static final String MEETING_TYPE_STATUS_WFB = "1";// 未发布
	public static final String MEETING_TYPE_STATUS_BJ = "3"; // 办结
	/**
	 * 会议种类
	 */
	public static final String MEETING_TYPE = "MEETINGTYPE";
	public static final String MEETING_TYPE_QH = "1";// 全委会议
	public static final String MEETING_TYPE_CWH = "2";// 常委会议
	public static final String MEETING_TYPE_PTHY = "3";// 其他会议

	// -------------------
	// END会议类型定义---------------------------------------------------------------//
			
		/**
		 * 委员分组类型
		 */
		public static final String GROUP_TYPE="GTYPE";
			public static final String GROUP_TYPE_HY="1";//会议分组
			public static final String GROUP_TYPE_HD="2";//活动分组
//-----------教务-------------------------

//-----------教务-------------------
}