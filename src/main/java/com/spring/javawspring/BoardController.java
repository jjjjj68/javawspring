package com.spring.javawspring;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawspring.pagination.PageProcess;
import com.spring.javawspring.pagination.PageVO;
import com.spring.javawspring.service.BoardService;
import com.spring.javawspring.service.MemberService;
import com.spring.javawspring.vo.BoardVO;
import com.spring.javawspring.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess; 
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/boardList", method=RequestMethod.GET)
	public String adminMainGet(Model model,
		@RequestParam(name="pag", defaultValue = "1", required = false) int pag, 
		@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
		
		PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "board", null, null);
		
		
		List<BoardVO> vos = boardService.getBoardList(pageVo.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos",vos);
		model.addAttribute("pageVo", pageVo);
		
		return "board/boardList";
	}
	
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet(Model model,HttpSession session ,int pag, int pageSize) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo= memberService.getMemberIdCheck(mid);
		
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("email",vo.getEmail());
		model.addAttribute("homePage",vo.getHomePage());
		
		return "board/boardInput";
	}
	
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		int res = boardService.setBoardInput(vo);
		
		if(res == 1) return "redirect:/msg/boardInputOk";
		else return "redirect:/msg/boardInputNo";
	}
	
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model, int idx, int pag, int pageSize) {
		// 조회수 증가처리
		boardService.setBoardReadNum(idx);
		
		BoardVO vo = boardService.getBoardContent(idx);
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		return "board/boardContent";
	}
}
