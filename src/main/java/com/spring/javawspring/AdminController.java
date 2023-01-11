package com.spring.javawspring;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.AdminService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/adminMain", method=RequestMethod.GET)
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@RequestMapping(value = "/adminLeft", method=RequestMethod.GET)
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@RequestMapping(value = "/adminContent", method=RequestMethod.GET)
	public String adminContentGet() {
		return "admin/adminContent";
	}
	
	@RequestMapping(value = "/member/adminMemberList", method = RequestMethod.GET)
	public String adminMemberListGet(Model model,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize) {
		
		PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "member", "", mid);
		
		List<MemberVO> vos = memberService.getMemberList(pageVo.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		model.addAttribute("mid", mid);
		
		return "admin/member/adminMemberList";
	}
	
	// 회원 등급 변경하기
	@ResponseBody
	@RequestMapping(value = "/member/adminMemberLevel", method = RequestMethod.POST)
	public String adminMemberLevelPost(int idx, int level) {
		int res = adminService.setMemberLevelCheck(idx,level);
		
		return "";
	}

	// ckeditor폴더의 파일 리스트 보여주기
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/file/fileList", method = RequestMethod.GET)
	public String fileListGet(HttpServletRequest request, Model model) {
		String realpath = request.getRealPath("resources/data/ckeditor/");
		// list() 해당폴더에 들어가있는 리스트정보를 가지고 문자배열로 담아준다.
		String[] files = new File(realpath).list();
		
		model.addAttribute("files",files);
		
		return "admin/file/fileList";
	}
}
