package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.service.ScheduleService;
import com.spring.javawspring.vo.ScheduleVO;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	
	@RequestMapping(value = "/schedule", method=RequestMethod.GET)
	public String scheduleGet() {
		scheduleService.getSchedule();
		return "schedule/schedule";
	}
	
	@RequestMapping(value = "/scheduleMenu", method=RequestMethod.GET)
	public String scheduleMenuGet(HttpSession session, String ymd, Model model) {		
		String mid = (String) session.getAttribute("sMid");
		List<ScheduleVO> vos = scheduleService.getScheduleMenu(mid,ymd);
		
		model.addAttribute("vos",vos);
		model.addAttribute("ymd",ymd);
		model.addAttribute("scheduleCnt", vos.size());
		
		return "schedule/scheduleMenu";
	}
	
	// 스케줄 저장하기
	@ResponseBody
	@RequestMapping(value = "/scheduleInputOk", method=RequestMethod.POST)
	public String scheduleInputOkPost(ScheduleVO vo) {		
		scheduleService.setScheduleInputOk(vo);
		
		return "";
	}
	
	// 스케줄 수정하기
	@ResponseBody
	@RequestMapping(value = "/scheduleUpdateOk", method=RequestMethod.POST)
	public String scheduleUpdateOkPost(ScheduleVO vo) {		
		scheduleService.setScheduleUpdateOk(vo);
		
		return "";
	}
	
	// 스케줄 삭제하기
	@ResponseBody
	@RequestMapping(value = "/scheduleDeleteOk", method=RequestMethod.POST, produces = "application/text; charset=utf8")
	public String scheduleDeleteOkPost(ScheduleVO vo) {		
		scheduleService.setScheduleDeleteOk(vo);
		// 이게 스트링형으로 받아져서 요거 써줘야지 리턴에 ㅇ벗어도 되는거라네 ㅇ.ㅇ
		return "";// 똑같은은디 저기서만 안됨 ㅇ.ㅇ
	}
	
}
