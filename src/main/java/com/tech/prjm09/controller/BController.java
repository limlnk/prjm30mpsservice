package com.tech.prjm09.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tech.command.BCommand;
import com.tech.command.BContentCommand;
import com.tech.command.BDeleteCommand;
import com.tech.command.BListCommand;
import com.tech.command.BModifyCommand;
import com.tech.command.BModifyViewCommand;
import com.tech.command.BReplyCommand;
import com.tech.command.BReplyViewCommand;
import com.tech.command.BWriteCommand;
import com.tech.prjm09.dao.IDao;
import com.tech.prjm09.dto.BDto;
import com.tech.prjm09.dto.ReBrdimgDto;
import com.tech.prjm09.service.BContentViewService;
import com.tech.prjm09.service.BDeleteService;
import com.tech.prjm09.service.BDownloadService;
import com.tech.prjm09.service.BListService;
import com.tech.prjm09.service.BModifyService;
import com.tech.prjm09.service.BModifyViewService;
import com.tech.prjm09.service.BReplyService;
import com.tech.prjm09.service.BReplyViewService;
import com.tech.prjm09.service.BServiceinter;
import com.tech.prjm09.service.BWriteService;
import com.tech.prjm09.util.DBCon;
import com.tech.prjm09.util.SearchVO;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oracle.jdbc.proxy.annotation.Post;

@Controller
public class BController {
	//dev
	BCommand command;
	
	BServiceinter bServiceinter;
	
	@Autowired
	private IDao iDao;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,
			SearchVO searchVO,Model model) {
		System.out.println("list() ctr");
//		command=new BListCommand();
//		command.execute(model);
		
		
		model.addAttribute("request",request);
		model.addAttribute("searchVO",searchVO);
		bServiceinter=new BListService(iDao);
		bServiceinter.execute(model);
		
		return "list";
	}

	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view() ctr");
		
		return "write_view";
	}
//	@RequestMapping("/write")
//	public String write(HttpServletRequest request,
//			Model model) {
//		System.out.println("write() ctr");
////		db글쓰기동작
////		model.addAttribute("request",request);
////		command=new BWriteCommand();
////		command.execute(model);
//		
//		String bname=request.getParameter("bname");
//		String btitle=request.getParameter("btitle");
//		String bcontent=request.getParameter("bcontent");
//		iDao.write(bname, btitle, bcontent);
//		//
//		return "redirect:list";
//	}
	@RequestMapping("/write")
	public String write(MultipartHttpServletRequest mtfRequest,
			Model model) {
		System.out.println("write() ctr");
		
		model.addAttribute("mtfRequest",mtfRequest);
		bServiceinter=new BWriteService(iDao);
		bServiceinter.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/download")
	public String download(HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws Exception {
		
		model.addAttribute("request",request);
		model.addAttribute("response",response);
		bServiceinter=new BDownloadService(iDao);
		bServiceinter.execute(model);
		String bid=request.getParameter("bid");
		
		return "content_view?bid="+bid;
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request,
			Model model) {
		System.out.println("content_view() ctr");

		//model하고 service를 실행
		model.addAttribute("request",request);
		bServiceinter=new BContentViewService(iDao);
		bServiceinter.execute(model);
		
		
		return "content_view";
	}

	
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest request,
			Model model) {
		System.out.println("modify_view() ctr");
		model.addAttribute("request",request);
		
		bServiceinter=new BModifyViewService(iDao);
		bServiceinter.execute(model);
		
		return "modify_view";
	}
	@PostMapping("/modify")
	public String modify(HttpServletRequest request,
			Model model) {
		System.out.println("modify() ctr");
		model.addAttribute("request",request);

		
		bServiceinter=new BModifyService(iDao);
		bServiceinter.execute(model);
		return "redirect:list";
	}
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request,
			Model model) {
		System.out.println("reply_view() ctr");
		
		model.addAttribute("request",request);
		bServiceinter=new BReplyViewService(iDao);
		bServiceinter.execute(model);
	
		return "reply_view";
	}
	@PostMapping("/reply")
	public String reply(HttpServletRequest request,
			Model model) {
		System.out.println("reply() ctr");
//		model.addAttribute("request",request);
//		command=new BReplyCommand();
//		command.execute(model);
		
		model.addAttribute("request",request);
		bServiceinter=new BReplyService(iDao);
		bServiceinter.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,
			Model model) {
		System.out.println("delete() ctr");
		
		
		model.addAttribute("request",request);
		bServiceinter=new BDeleteService(iDao);
		bServiceinter.execute(model);
		return "redirect:list";
	}
	
	
	
	
}
