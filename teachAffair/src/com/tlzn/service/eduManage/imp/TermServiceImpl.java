package com.tlzn.service.eduManage.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlzn.dao.base.BaseDaoI;
import com.tlzn.model.eduManage.Tterm;
import com.tlzn.pageModel.base.DataGrid;
import com.tlzn.pageModel.eduManage.Term;
import com.tlzn.service.base.impl.BaseServiceImpl;
import com.tlzn.service.eduManage.TermServiceI;
import com.tlzn.util.base.Constants;
import com.tlzn.util.base.Generator;
import com.tlzn.util.base.Util;

@Service("termService")
public class TermServiceImpl extends BaseServiceImpl implements TermServiceI{

	private BaseDaoI<Tterm> termDAO;
	/**
	 * 
	 * 查询单条学期
	 * 
	 * @param term
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Term get(String id) throws Exception {
		Tterm tterm= termDAO.get(Tterm.class, id);
		Term term = new Term();
		BeanUtils.copyProperties(tterm, term);
//		term.setTermAttName(this.findDicName("termAtt", tterm.getTermAtt()));
		return term;
	}
	
	/**
	 * 
	 * 条件查询
	 * 
	 * @param term
	 * 
	 * @throws 	Exception
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Term term) throws Exception {
		String hql=" from Tterm t where 1=1";
		DataGrid j = new DataGrid();
		j.setRows(editTerms(find(term,hql)));
		j.setTotal(total(term,hql));
		return j;
	}
	private List<Term> editTerms(List<Tterm> termList) throws Exception {
		List<Term> terms = new ArrayList<Term>();
		if (termList != null && termList.size() > 0) {
			for (Tterm tterm: termList) {
				Term term= new Term();
				editTerm(tterm,term);
				terms.add(term);
			}
		}
		return terms;
	}
	private Term editTerm(Tterm tterm,Term term) throws Exception {
		BeanUtils.copyProperties(tterm, term);
//		term.setTermAttName(this.findDicName("termAtt", tterm.getTermAtt()));
//		term.setTeachMeth("");
//		term.setTeachBook("");
//		term.setAsMethAnReq("");
//		term.setComment("");
		return term;
	}
	private List<Tterm> find(Term term,String hql) throws Exception {
		hql = addWhere(term, hql);
		if (term.getSort() != null && term.getOrder() != null) {
			hql += " order by " + term.getSort() + " " + term.getOrder();
		}else {
			hql+=" order by t.uptTime desc, t.crtTime desc";
		}
		List<Tterm> termList = termDAO.find(hql, term.getPage(), term.getRows());
		return termList;
	}
	private Long total(Term term,String hql) throws Exception {
		hql = addWhere(term, hql);
		hql = "select count(*) "+hql;
		return termDAO.count(hql);
	}
	private String addWhere(Term term, String hql) throws Exception {
		Util util=new Util();
//		//学期号
//		if (!util.isEmpty(term.getId())) {
//			hql += " and t.id='" + term.getId().trim() + "' ";
//		}
//		//学期名称
//		if(!util.isEmpty(term.getName())){
//			hql += " and t.name like '%"+term.getName().trim()+"%'";
//		}
//		//学期英文名称
//		if(!util.isEmpty(term.getEnName())){
//			hql += " and t.enName like '%"+term.getEnName().trim()+"%'";
//		}
//		//学期序号
//		if (!util.isEmpty(term.getSortNo())) {
//			hql += " and t.sortNo='" + term.getSortNo().trim() + "' ";
//		}
//		//学期学分
//		if (!util.isEmpty(term.getCredit())) {
//			hql += " and t.credit='" + term.getCredit().trim() + "' ";
//		}
//		//学期属性
//		if (!util.isEmpty(term.getTermAtt())) {
//			hql += " and t.termAtt='" + term.getTermAtt().trim() + "' ";
//		}
//		//教学方法
//		if(!util.isEmpty(term.getTeachMeth())){
//			hql += " and t.teachMeth like '%"+term.getTeachMeth().trim()+"%'";
//		}
//		//使用教材
//		if(!util.isEmpty(term.getTeachBook())){
//			hql += " and t.teachBook like '%"+term.getTeachBook().trim()+"%'";
//		}
//		//考核方式及其要求
//		if(!util.isEmpty(term.getAsMethAnReq())){
//			hql += " and t.asMethAnReq like '%"+term.getAsMethAnReq().trim()+"%'";
//		}
		//备注
		if(!util.isEmpty(term.getComment())){
			hql += " and t.comment like '%"+term.getComment().trim()+"%'";
		}
		return hql;
	}
	
	/**
	 * 保存学期信息
	 */
	public String add(Term term,HttpSession httpSession) throws Exception {
		
		Tterm tterm = new Tterm();
		BeanUtils.copyProperties(term, tterm);
		tterm.setId(Generator.getInstance().getTermNO());
		tterm.setCrtTime(new Date());
		tterm.setUptTime(new Date());
		termDAO.save(tterm);
		
		//生成日志
		this.saveLog(Constants.LOG_TYPE_CODE_COURSE, Constants.LOG_TYPE_OPERTYPE_ADD, tterm.getId(), "[学期]保存学期");
		
		return tterm.getId();
	}
	/**
	 * 删除学期信息
	 */
	public void del(Term term) throws Exception {
		
		Util util=new Util();
		String ids = term.getIds();
		if(!util.isEmpty(ids)){
			String stringArray[]=ids.split(",");
			for (String str : stringArray) {
				if(!util.isEmpty(str)){
					String id=str.trim();
					Tterm tterm = termDAO.get(Tterm.class,id);
					termDAO.delete(tterm);
					//生成日志
					this.saveLog(Constants.LOG_TYPE_CODE_COURSE, Constants.LOG_TYPE_OPERTYPE_DEL, id, "[学期]学期删除（学期ID："+id+"）");
				}
			}
		}
	}
	
	/**
	 * 修改学期信息
	 */
	public void edit(Term term) throws Exception {
		
		String id = term.getId();
			if(id!=null && !"".equals(id)) {
			Tterm tterm = termDAO.get(Tterm.class,id);
			BeanUtils.copyProperties(term, tterm, new String[]{"id"});
			tterm.setUptTime(new Date());
			termDAO.update(tterm);
			//生成日志
			this.saveLog(Constants.LOG_TYPE_CODE_COURSE, Constants.LOG_TYPE_OPERTYPE_EDIT, id, "[学期]学期修改（学期ID："+id+"）");
		}
	}
	public BaseDaoI<Tterm> getTermDAO() {
		return termDAO;
	}
	
	@Autowired
	public void setTermDAO(BaseDaoI<Tterm> termDAO) {
		this.termDAO = termDAO;
	}
}
