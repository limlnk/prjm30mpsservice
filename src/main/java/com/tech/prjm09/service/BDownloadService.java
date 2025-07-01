package com.tech.prjm09.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
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

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BDownloadService implements BServiceinter{
	private IDao iDao;
	public BDownloadService(IDao iDao) {
		this.iDao=iDao;
	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub

		System.out.println(">>>>>BDownloadService");
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		HttpServletResponse response=
				(HttpServletResponse) map.get("response");
		String fname=request.getParameter("f");
		String bid=request.getParameter("bid");
		System.out.println(fname+":"+bid);
		
		try {	

			//첨부파일
			response.setHeader("Content-Disposition","Attachment;filename="+URLEncoder.encode(fname,"UTF-8"));
			String workPath=System.getProperty("user.dir");
			String realPath=workPath+"\\src\\main\\resources\\static\\files\\"+fname;
			System.out.println(realPath);
			FileInputStream fin=new FileInputStream(realPath);
			ServletOutputStream sout=response.getOutputStream();
			
					
			byte[] buf=new byte[1024];
			int size=0;
			while((size=fin.read(buf,0,1024))!=-1) {
				sout.write(buf,0,size);
			}
			fin.close();
			sout.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
