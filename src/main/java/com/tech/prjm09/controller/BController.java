package com.tech.prjm09.controller;

import java.io.File;
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
import com.tech.prjm09.util.DBCon;
import com.tech.prjm09.util.SearchVO;

import jakarta.servlet.http.HttpServletRequest;
import oracle.jdbc.proxy.annotation.Post;

@Controller
public class BController {
	//dev
	BCommand command;
	
	private final IDao iDao;
	
	@Autowired
	public BController(IDao iDao) {
		this.iDao=iDao;
	}
	
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
		
		//
//		searching
		String btitle="";
		String bcontent="";
		
		String[] brdTitle=request.getParameterValues("searchType");
		if(brdTitle!=null) {
			for(int i=0; i<brdTitle.length; i++) {
				System.out.println("brdTitle: "+brdTitle[i]);
			}
		}
		
		if(brdTitle!=null) {
			for(String val: brdTitle) {
				if(val.equals("btitle")) {
					model.addAttribute("btitle","true");
					btitle="btitle";
				}
				if(val.equals("bcontent")) {
					model.addAttribute("bcontent","true");
					bcontent="bcontent";
				}
			}
		}
		
		String searchKeyword=request.getParameter("sk");

		if(searchKeyword==null) 
			searchKeyword="";
		model.addAttribute("searchKeyword",searchKeyword);
		//			
//		전체글의갯수 변형
		int total=0;
		
		if(btitle.equals("btitle")&&bcontent.equals("")){
			total=iDao.selectBoardCount(searchKeyword,"1");
			System.out.println("total:11111111111");
		}else if(btitle.equals("")&&bcontent.equals("bcontent")){
			total=iDao.selectBoardCount(searchKeyword,"2");
			System.out.println("total: 222222222222");
		}else if(btitle.equals("btitle")&&bcontent.equals("bcontent")){
			total=iDao.selectBoardCount(searchKeyword,"3");
			System.out.println("total:33333333333333333");
		}else if(btitle.equals("")&&bcontent.equals("")){
			total=iDao.selectBoardCount(searchKeyword,"4");
			System.out.println("total:4444444444444");
		}
		
		
		
		
		
		
		//글의총갯수
//		int total=iDao.selectBoardCount();
		
		
//		paging
		String strPage=request.getParameter("page");
		//null검사
		if(strPage==null) {
			strPage="1";
		}
		int page=Integer.parseInt(strPage);
		searchVO.setPage(page);
		
		System.out.println("total:"+total);
		searchVO.pageCalculate(total);
		
		System.out.println("total:"+total);
		System.out.println("click page:"+strPage);
		System.out.println("pageStart:"+searchVO.getPage());
		System.out.println("pageEnd:"+searchVO.getPageEnd());
		System.out.println("pageTotal:"+searchVO.getTotPage());
		System.out.println("rowStart:"+searchVO.getRowStart());
		System.out.println("rowEnd:"+searchVO.getRowEnd());
		
		int rowStart=searchVO.getRowStart();
		int rowEnd=searchVO.getRowEnd();
		
		ArrayList<BDto> list=null;
		
		if(btitle.equals("btitle")&&bcontent.equals("")){
//			total=iDao.selectBoardCount(searchKeyword,"1");
			model.addAttribute("list",iDao.list(rowStart, rowEnd, searchKeyword, "1"));
			System.out.println("total:11111111111");
		}else if(btitle.equals("")&&bcontent.equals("bcontent")){
//			list=iDao.list(rowStart,rowEnd,searchKeyword,"2");
			model.addAttribute("list",iDao.list(rowStart, rowEnd, searchKeyword, "2"));
			System.out.println("total: 222222222222");
		}else if(btitle.equals("btitle")&&bcontent.equals("bcontent")){
			model.addAttribute("list",iDao.list(rowStart, rowEnd, searchKeyword, "3"));
			System.out.println("total:33333333333333333");
		}else if(btitle.equals("")&&bcontent.equals("")){
			model.addAttribute("list",iDao.list(rowStart, rowEnd, searchKeyword, "4"));
			System.out.println("total:4444444444444");
		}
		
//		model.addAttribute("list",list);
		
		model.addAttribute("totRowCnt",total);
		model.addAttribute("searchVo",searchVO);
		
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
		
		String bname=mtfRequest.getParameter("bname");
		String btitle=mtfRequest.getParameter("btitle");
		String bcontent=mtfRequest.getParameter("bcontent");
		
		System.out.println("title: "+btitle);
		iDao.write(bname, btitle, bcontent);
		
		String workPath=System.getProperty("user.dir");
		System.out.println(workPath);
		
//		String root="C:\\hsts2025\\sts25_work\\prjm29replyboard_mpsupdown_multi\\"
//				+ "src\\main\\resources\\static\\files";
		List<MultipartFile> fileList=mtfRequest.getFiles("file");
		
		String root=workPath+"\\src\\main\\resources\\static\\files";
		int bid=iDao.selBid();
		System.out.println("bid: "+bid);
		
		for(MultipartFile mf:fileList) {
			String originalFile=mf.getOriginalFilename();
			System.out.println("original files : "+originalFile);
			long longtime=System.currentTimeMillis();
			String changeFile=longtime+"_"+originalFile;
			System.out.println("change files :"+changeFile);
			
			String pathfile=root+"\\"+changeFile;
			try {
				if(!originalFile.equals("")) {
					mf.transferTo(new File(pathfile));
					System.out.println("upload success~~");
					//db기록
					iDao.imgwrite(bid,originalFile,changeFile);
					System.out.println("rebrdimgtb write sucess");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:list";
	}
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request,
			Model model) {
		System.out.println("content_view() ctr");
//		model.addAttribute("request",request);
//		command=new BContentCommand();
//		command.execute(model);
		String bid=request.getParameter("bid");
		iDao.upHit(bid);
		BDto dto=iDao.contentView(bid);
//		
		model.addAttribute("content_view",dto);
		
		ArrayList<ReBrdimgDto> imgList=iDao.selectImg(bid);
		model.addAttribute("imgList",imgList);
		return "content_view";
	}
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest request,
			Model model) {
		System.out.println("modify_view() ctr");
		model.addAttribute("request",request);
//		command=new BModifyViewCommand();
//		command.execute(model);
		String bid=request.getParameter("bid");
		BDto dto=iDao.modifyView(bid);
		
		model.addAttribute("content_view",dto);
		return "modify_view";
	}
	@PostMapping("/modify")
	public String modify(HttpServletRequest request,
			Model model) {
		System.out.println("modify() ctr");
//		model.addAttribute("request",request);
//		command=new BModifyCommand();
//		command.execute(model);
		String bid=request.getParameter("bid");
		String bname=request.getParameter("bname");
		String btitle=request.getParameter("btitle");
		String bcontent=request.getParameter("bcontent");
	 
		iDao.modify(bid, bname, btitle, bcontent);
		return "redirect:list";
	}
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request,
			Model model) {
		System.out.println("reply_view() ctr");
		
//		model.addAttribute("request",request);
//		command=new BReplyViewCommand();
//		command.execute(model);
		String bid=request.getParameter("bid");
		BDto dto=iDao.replyView(bid);
		
		model.addAttribute("reply_view",dto);
		return "reply_view";
	}
	@PostMapping("/reply")
	public String reply(HttpServletRequest request,
			Model model) {
		System.out.println("reply() ctr");
//		model.addAttribute("request",request);
//		command=new BReplyCommand();
//		command.execute(model);
		
		String bid=request.getParameter("bid");
		String bname=request.getParameter("bname");
		String btitle=request.getParameter("btitle");
		String bcontent=request.getParameter("bcontent");
		
		String bgroup=request.getParameter("bgroup");
		String bstep=request.getParameter("bstep");
		String bindent=request.getParameter("bindent");
		
		iDao.replyShape(bgroup, bstep);
		
		iDao.reply(bid, bname, btitle, bcontent, bgroup, bstep, bindent);

		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,
			Model model) {
		System.out.println("delete() ctr");
//		model.addAttribute("request",request);
//		command=new BDeleteCommand();
//		command.execute(model);
		String bid=request.getParameter("bid");
		iDao.delete(bid);
		return "redirect:list";
	}
	
	
	
	
}
