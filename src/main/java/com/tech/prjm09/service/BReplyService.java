package com.tech.prjm09.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.tech.prjm09.dao.BDao;
import com.tech.prjm09.dao.IDao;
import com.tech.prjm09.dto.BDto;
import com.tech.prjm09.dto.ReBrdimgDto;

import jakarta.servlet.http.HttpServletRequest;

public class BReplyService implements BServiceinter{
	private IDao iDao;
	public BReplyService(IDao iDao) {
		this.iDao=iDao;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
//		model.addAttribute("request",request);
//		command=new BContentCommand();
//		command.execute(model);
		System.out.println(">>>>>BReplyService");
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String bid=request.getParameter("bid");
		String bname=request.getParameter("bname");
		String btitle=request.getParameter("btitle");
		String bcontent=request.getParameter("bcontent");
		
		String bgroup=request.getParameter("bgroup");
		String bstep=request.getParameter("bstep");
		String bindent=request.getParameter("bindent");
		
		iDao.replyShape(bgroup, bstep);
		
		iDao.reply(bid, bname, btitle, bcontent, bgroup, bstep, bindent);

	}

}
