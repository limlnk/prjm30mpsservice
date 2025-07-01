package com.tech.prjm09.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tech.prjm09.dao.BDao;
import com.tech.prjm09.dao.IDao;
import com.tech.prjm09.dto.BDto;
import com.tech.prjm09.dto.ReBrdimgDto;

import jakarta.servlet.http.HttpServletRequest;

public class BWriteService implements BServiceinter{
	private IDao iDao;
	public BWriteService(IDao iDao) {
		this.iDao=iDao;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub

		System.out.println(">>>>>BWriteService");
		Map<String, Object> map=model.asMap();
		MultipartHttpServletRequest mtfRequest=
				(MultipartHttpServletRequest) map.get("mtfRequest");
		
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
		
		
	}

}
