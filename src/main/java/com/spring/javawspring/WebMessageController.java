package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.service.WebMessageService;
import com.spring.javawspring.vo.MemberVO;
import com.spring.javawspring.vo.WebMessageVO;

@Controller
@RequestMapping("/webMessage")
public class WebMessageController {
	
	@Autowired
	WebMessageService webMessageService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/webMessage", method = RequestMethod.GET)
	public String webMessageGet(Model model, HttpSession session,
			@RequestParam(name="mSw", defaultValue = "1", required = false) int mSw,
			@RequestParam(name="mFlag", defaultValue = "1", required = false) int mFlag,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(name="receiveSw", defaultValue = "", required = false) String receiveSw,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx) {
		
		String mid = (String) session.getAttribute("sMid");
		
		if(mSw == 0) {	// 메세지 작성하기
			
		}
		else if(mSw == 6) {	// 메세지 내용 상세보기
			WebMessageVO vo = webMessageService.getWmMessageOne(idx, mid, mFlag, receiveSw);
			model.addAttribute("vo", vo);
			model.addAttribute("mFlag",1);
		}
		else if(mSw == 7) { // 휴지통 비우기를 선택할때는 휴지통에 들어있는 메일 모두의 receiveSw = 'x'로 처리한다.
			webMessageService.setWmDeleteAll(mid);
			// sendSw와 receiveSw 가 둘다 'x' 인것을 찾아서 모두 삭제처리한다.(delete)
			return "redirect:/msg/wmDeleteAll";
		}
		
		else {	// 받은메세지, 새메세지, 보낸메세지, 수신확인, 휴지통
			PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "webMessage", mid, mSw+"");
			List<WebMessageVO> vos = webMessageService.getWmMessageList(mid, mSw, pageVo.getStartIndexNo(), pageSize);

			model.addAttribute("vos", vos);
			model.addAttribute("pageVo", pageVo);
		}
		model.addAttribute("mSw", mSw);
		
		return "webMessage/wmMessage";
	}
	
	@RequestMapping(value = "/wmInput", method = RequestMethod.POST)
	public String wmInputPost(WebMessageVO vo) {
		MemberVO vo2 = memberService.getMemberIdCheck(vo.getReceiveId());
		if(vo2 == null) return "redirect:/msg/wmMemberIdNo";
		
		webMessageService.setWmInputOk(vo);
		return "redirect:/msg/wmInputOk";
	}
	
	// 받은 메세지함에서의 휴지통 보내기
	@RequestMapping(value = "/webDeleteCheck", method = RequestMethod.GET)
	public String webDeleteCheckGet(int idx, int mSw, int mFlag, Model model) {
		webMessageService.wmDeleteCheck(idx, mFlag);
		
		model.addAttribute("mSw", mSw);
		return "redirect:/webMessage/webMessage";
	}

	// 보낸 메세지함에서의 바로 삭제
	@ResponseBody
	@RequestMapping(value = "/wmDelete", method = RequestMethod.POST)
	public String webDeletePost(int idx, int mFlag) {
		webMessageService.wmDeleteCheck(idx, mFlag);
		return "";
	}
}