
package com.jwy.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import com.jwy.dao.ICourseDao;
import com.jwy.dao.ISpecialtyDao;
import com.jwy.dao.IStuUserDao;
import com.jwy.dto.Course;
import com.jwy.dto.Specialty;
import com.jwy.dto.StuUser;

/** 
 * 
 * XDoclet definition:
 * @struts.action path="/stuInfo" name="stuInfoForm" input="/stu/stuInfo.jsp" scope="request" validate="true"
 */
public class StuUserAction extends DispatchAction {
	private IStuUserDao stuUserDao;
	private ICourseDao courseDao;
	private ISpecialtyDao specialtyDao;
	/**
	 * @param stuUserDao the stuUserDao to set
	 */
	public void setStuUserDao(IStuUserDao stuUserDao) {
		this.stuUserDao = stuUserDao;
	}
	/**
	 * @param courseDao the courseDao to set
	 */
	public void setCourseDao(ICourseDao courseDao) {
		this.courseDao = courseDao;
	}
	/**
	 * @param specialtyDao the specialtyDao to set
	 */
	public void setSpecialtyDao(ISpecialtyDao specialtyDao) {
		this.specialtyDao = specialtyDao;
	}
	
	/**
	 * 学生添加基础信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm stu = (DynaActionForm) form;
		StuUser user = new StuUser();
		user.setId((Integer)request.getSession().getAttribute("id"));
		user.setStuName(stu.getString("stuName"));
		user.setStuNo(stu.getString("stuNo"));
		user.setSpecialtyId(Integer.valueOf(stu.getString("specialtyId")));
		user.setStuSex(stu.getString("stuSex"));
		user.setBirthday(stu.getString("birthday"));
		user.setHomeAddr(stu.getString("homeAddr"));
		user.setTel(stu.getString("tel"));
		user.setAddr(stu.getString("addr"));
		stuUserDao.insert(user);
		Specialty specialty = specialtyDao.findById(user.getSpecialtyId());
		request.setAttribute("stuUser", user);
		request.setAttribute("specialty", specialty);
		return mapping.findForward("welcome");
	}
	/**
	 * 进入学生模块首页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward welcome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Integer id = (Integer) request.getSession().getAttribute("id");
		StuUser stuUser = stuUserDao.findById(id);
		Specialty specialty = specialtyDao.findById(stuUser.getSpecialtyId());
		request.setAttribute("stuUser", stuUser);
		request.setAttribute("specialty", specialty);
		return mapping.findForward("welcome");
	}
	/**
	 * 学生退出系统
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return mapping.findForward("exit");
	}
	/**
	 * 学生查询已选课程
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward selected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Integer id = (Integer) request.getSession().getAttribute("id");
		List<Object[]> list = stuUserDao.findSelected(id);
		request.setAttribute("list", list);
		return mapping.findForward("selected");
	}
	/**
	 * 学生查询可选课程
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Integer id = (Integer) request.getSession().getAttribute("id");
		List<Object[]> list = stuUserDao.findSelect(id);
		request.setAttribute("list", list);
		return mapping.findForward("select");
	}
	/**
	 * 学生选课操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward selectting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Integer id = (Integer) request.getSession().getAttribute("id");
		String[] courseIds = request.getParameterValues("courseId");
		if(courseIds!=null){
			stuUserDao.insertSC(courseIds,id);
		}
		return select(mapping, form, request, response);
	}
	/**
	 * 查询课程详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward courseInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.valueOf(request.getParameter("id"));
		String path = request.getParameter("path");
		Course course = courseDao.findByID(id);
		request.setAttribute("course", course);
		request.setAttribute("path", path);
		return mapping.findForward("courseInfo");
	}
	
}