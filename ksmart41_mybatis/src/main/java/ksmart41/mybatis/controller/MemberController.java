package ksmart41.mybatis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksmart41.mybatis.dto.Member;
import ksmart41.mybatis.dto.MemberLevel;
import ksmart41.mybatis.mapper.MemberMapper;
import ksmart41.mybatis.service.MemberService;

@Controller
@RequestMapping(value= "/member")
public class MemberController {
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	//프로퍼티DI 주입방식 1개 프로퍼티만 적용이 된다. 다중으로 이용하려면 계속 넣어줘야 한다.
		/*@Autowired  
		private MemberMapper memberMapper; */
		
		//세터 DI 주입방식
		/*private MemberMapper memberMapper;
		
		@Autowired
		public void setMemberMapper(MemberMapper memberMapper) {
			this.memberMapper=memberMapper; 
			}
			*/

		//생성자메서드 주입방식
		private MemberService memberService;
		
		public MemberController(MemberService memberService) {
			this.memberService = memberService;
		}
		@PostMapping("/removeMember")
		public String removeMember(@RequestParam(value="memberId", required=false)String memberId,
				@RequestParam(value="memberPw", required=false)String memberPw, RedirectAttributes reAttr) {
			// 화면에서 전달받은 회원아이디 확인, 화면에서 전달받은 회원pw 확인
			log.info("삭제요청시 화면에 전달될 memberId 값:{}", memberId);
			log.info("삭제요청시 화면에 전달될 memberPw 값:{}", memberPw);
			
			//service
			String result = memberService.removeMember(memberId, memberPw);	
			
			// 회원정보 확인
			if("회원삭제 실패".equals(result)) {
				reAttr.addAttribute("result", result);
				// /member/removeMember/id001?result=회원삭제 실패
				return "redirect:/member/removeMember/" + memberId;
			}
			
			
			// 회원탈퇴
			return "redirect:/member/memberList";
		}
		
		@GetMapping("/removeMember/{memberId}")
		public String removeMember(@PathVariable(value="memberId", required = false)String memberId,
				@RequestParam(value="memberId", required=false)String result,
				Model model) {
			
			log.info("삭제요청시 화면에 전달될 memberId 값:{}", memberId);
			
			model.addAttribute("title", "회원탈퇴폼");
			model.addAttribute("memberId", memberId);
			if(result != null) model.addAttribute(result, result);
			
			return "member/removeMember";
		}
		
		@PostMapping("/modifyMember")
		public String modifyMember(Member member) {
			//회원수정
			memberService.modiftMember(member);
			return "redirect:/member/memberList";
		}

		@GetMapping("/modifyMember")
		public String modifyMember(@RequestParam(value="memberId", required = false) String memberId, Model model) {
			
			//memberId 콘솔 화면에 출력(log4j)
			log.info("modifyMember memberId: {}", memberId);
			
			//회원의 정보
			if(memberId != null && !"".equals(memberId)) {
				Member memberInfo = memberService.getMemberInfoByMemberId(memberId);
				model.addAttribute("memberInfo", memberInfo);
			}
			model.addAttribute("title", "회원수정화면");
			model.addAttribute("memberLevelList", memberService.getMemberLevelList());
			
			return "member/modifyMember";
		}
		
		@PostMapping("/idCheck")
		@ResponseBody
		public boolean idCheck(@RequestParam(value="memberId", required=false) String memberId){
			
			System.out.println("ajax 통신으로 요청받은 파라미터 memberId: " + memberId);
			
			boolean checkResult = false;
			
			int check = memberService.getMemberByMemberId(memberId);
			
			if(check > 0 ) checkResult = true;
			//중복이다 checkResult = true
			//중복이 아니다 checkResult = false
			
			return checkResult; 
		}
		
		/**
		 * 커맨드 캑체 : controller 클래스 안 메서드
		 *
		 * 
		 */
		@PostMapping("/addMember")
		public String addMember(Member member) {
			
			System.out.println("MemberController 회원등록 화면에서 입력받은 값:" + member);
			//insert처리
			//null 체크
			String memberId = member.getMemberId();
			if(memberId != null && !"".equals(memberId)) {
				memberService.addMember(member);
			}

			return "redirect:/member/memberList";
		}
		
		//회원가입 폼
		@GetMapping("/addMember")
		public String addMember(Model model) {
			System.out.println("/addMember GET방식 요청");
			model.addAttribute("title", "회원등록");
			//DB 레벨 등급 LIST
			List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
			model.addAttribute("memberLevelList", memberLevelList);
			
			return "member/addMember";
		}
	
		//전체회원 검색
		@PostMapping("/memberList")
		public String getSearchMemberList(
				 @RequestParam(value="searchKey", required = false) String searchKey
				,@RequestParam(value="searchValue", required = false)String searchValue
				,Model model) {
			
			if(searchKey != null && "memberId".equals(searchKey)) {
				searchKey = "m_id";
			}else if(searchKey != null && "memberName".equals(searchKey)) {
				searchKey = "m_name";				
			}else {
				searchKey = "m_email";				
			}
			// 검색키 검색어를 통해서 회원목록 조회
			
			List<Member> memberList = memberService.getMemberListBySearchKey(searchKey, searchValue);
			
			// 조회된 회원목록 model에 값을 저장
			model.addAttribute("title", "회원목록조회");
			model.addAttribute("memberList", memberList);
			
			return "member/memberList";
		}
		
	/**
	 * localhost/member/memberList
	 */
	@GetMapping("/memberList")
	public String getMemberList(Model model) {
		List<Member> memberList = memberService.getMemberList();
		
		model.addAttribute("title", "회원전체조회");
		model.addAttribute("memberList", memberList);
		return "member/memberList";
	}
	
	
	
}
