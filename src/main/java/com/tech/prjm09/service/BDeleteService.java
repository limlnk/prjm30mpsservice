package com.tech.prjm09.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.tech.prjm09.dao.BDao;
import com.tech.prjm09.dao.IDao;
import com.tech.prjm09.dto.BDto;
import com.tech.prjm09.dto.ReBrdimgDto;

import jakarta.servlet.http.HttpServletRequest;

public class BDeleteService implements BServiceinter{
	private IDao iDao;
	public BDeleteService(IDao iDao) {
		this.iDao=iDao;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
//		model.addAttribute("request",request);
//		command=new BContentCommand();
//		command.execute(model);
		System.out.println(">>>>>BDeleteService");
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
	
		String bid=request.getParameter("bid");
		iDao.delete(bid);
	}

}
