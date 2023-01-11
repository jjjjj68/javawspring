package com.spring.javawspring;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
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
import com.spring.javawspring.vo.GoodVO;
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
		// content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 /resources/data/board 폴더에 저장시켜준다.
		boardService.imgCheck(vo.getContent());
		
		// 이미지 복사작업이 끝나면, board폴더에 실제로 저장된 파일명을  DB에 저장시켜준다.(/resources/data/ckeditor -> /resources/data/board)
		vo.setContent(vo.getContent().replace("/data/ckeditor/","/data/ckeditor/board/"));
		
		
		int res = boardService.setBoardInput(vo);
		
		if(res == 1) return "redirect:/msg/boardInputOk";
		else return "redirect:/msg/boardInputNo";
	}
	
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model,
			int idx, int pag, int pageSize, HttpSession session) {
		// 조회수 증가처리
		boardService.setBoardReadNum(idx);
		
		BoardVO vo = boardService.getBoardContent(idx);
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		
		//DB에서 현재 게시글에 '좋아요' 가 체크되어있는지를 알아보자.
		String mid = (String) session.getAttribute("sMid");
		GoodVO goodVo = boardService.getBoardGoodCheck(idx, "board", mid);
		model.addAttribute("goodVo",goodVo);
		
		// 이전글 / 다음글 가져오기(값을 두개처리하기위해 어레이리스트) idx 현재 글번호
		ArrayList<BoardVO> pnVos = boardService.getPrevNext(idx);
		//System.out.println("pnVos :" + pnVos);
		model.addAttribute("pnVos",pnVos);
		
		return "board/boardContent";
	}
	
	// 게시글 삭제하기...
	@RequestMapping(value = "/boardDeleteOk", method = RequestMethod.GET)
	public String boardDeleteOkGet(int idx, int pag, int pageSize, Model model) {
		// 게시글에 사진이 존재한다면 서버에 있는 사진파일을 먼저 삭제한다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// DB에서 실제로 존재하는 게시글을 삭제처리한다.
		boardService.setBoardDeleteOk(idx);
		
		model.addAttribute("flag","?pag="+pag+"&pageSize="+pageSize);
		
		return "redirect:/msg/boardDeleteOk";
	}
	
	
	// 수정하기 폼 호출
	@RequestMapping (value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(Model model ,int idx, int pag, int pageSize) {
		// 수정창으로 이동시에는 먼저 원본파일에 그림파일이 있다면, 
		// 현재폴더(board)의 그림파일을 ckeditor폴더로 복사시켜둔다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheckUpdate(vo.getContent());
		
		model.addAttribute("vo",vo);
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		
		
		
		return "board/boardUpdate";
	}
	
	// 수정하기 실행
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVO vo, int pag, int pageSize) {
		// 수정된 자료가 원본자료와 완전히 동일하다면 수정할 필요가 없기에, DB에 저장된 원본 자료를 불러와서 비교해준다.
		BoardVO origVo = boardService.getBoardContent(vo.getIdx());
		
		// content의 내용이 조금이라도 변경된것이 있다면 아래 내용을 수행처리시킨다.
		if(!origVo.getContent().equals(vo.getContent())) {
			// 실제로 수정하기버튼을 클릭하게되면 기존의 board폴더에 저장된 현재 content의 그림파일 모두를 삭제 시킨다.
			if(origVo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVo.getContent());
			
			// vo.GetContent()에 들어있는 파일의 경로는 'ckeditor/board'경로를 'ckeditor'변경시켜줘야한다.
			vo.setContent(vo.getContent().replace("/data/ckeditor/board/", "/data/ckeditor/"));
			
			// 앞의 모든준비가 끝나면, 파일을 처음 업로드한것과 같은 작업을 처리한다.
			// 이 작업은 처음 게시글을 올릴때의 파일복사 작업과 동일한 작업이다.
			boardService.imgCheck(vo.getContent());
			
			// 파일 업로드가 끝나면 다시 경로를 수정한다.'ckeditor'경로를 'ckeditor/board'변경시켜줘야한다.(즉, 변경된 vo.getContent()를 vo.setContent() 처리한다.)
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/ckeditor/board/"));
		}
		
		
		// 잘 정비된 vo를 DB에 Update시켜준다.
		boardService.setBoardUpdateOk(vo);
		
		model.addAttribute("flag","?pag="+pag+"&pageSize="+pageSize);
		
		return "redirect:/msg/boardUpdateOk";
	}
	
	
}
