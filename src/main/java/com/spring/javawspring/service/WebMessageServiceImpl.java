package com.spring.javawspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawspring.dao.WebMessageDAO;
import com.spring.javawspring.vo.WebMessageVO;

@Service
public class WebMessageServiceImpl implements WebMessageService {
	
	@Autowired
	WebMessageDAO webMessageDAO;

	@Override
	public WebMessageVO getWmMessageOne(int idx, String mid, int mFlag, String receiveSw) {
		if(receiveSw.equals("n") || mFlag == 5) {
			webMessageDAO.setWmUpdate(idx, mid);
		}
		return webMessageDAO.getWmMessageOne(idx, mid, mFlag);
	}

	@Override
	public List<WebMessageVO> getWmMessageList(String mid, int mSw, int startIndexNo, int pageSize) {
		return webMessageDAO.getWmMessageList(mid, mSw, startIndexNo,pageSize);
	}

	@Override
	public void setWmInputOk(WebMessageVO vo) {
		webMessageDAO.setWmInputOk(vo);
	}

	@Override
	public void wmDeleteCheck(int idx, int mFlag) {
		webMessageDAO.wmDeleteCheck(idx, mFlag);
	}

	@Override
	public void setWmDeleteAll(String mid) {
		webMessageDAO.setWmDeleteAll(mid);
	}
}
