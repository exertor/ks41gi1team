package ksmart41.mybatis.service;


import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart41.mybatis.dto.Member;
import ksmart41.mybatis.dto.MemberLevel;
import ksmart41.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {
		
	/**
	 * DI (의존선 주입) -> @autowired 
	 * 1. 프로퍼티
	 * 2. 세터메서드
	 * 3. 생성자메서드
	 */
		
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
	private MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	
	
	//회원 정보 수정
	public int modiftMember(Member member) {
		return memberMapper.modifyMemberInfo(member);
	}
	
	//회원 정보 조회
	public Member getMemberInfoByMemberId(String memberId) {
		
		return memberMapper.getMemberInfoByMemberId(memberId);
	}
	//회원 여부 체크
	public int getMemberByMemberId(String memberId) {
		int result =0;
		result += memberMapper.getMemberbyMemberId(memberId);

		return result;
	}	
	//회원등록
	public int addMember(Member member) {
		
		int result = memberMapper.addMember(member);
		
		return result;
	}
	

	
	//전체회원조회
	public List<Member> getMemberList(){
		List<Member> memberList = memberMapper.getMemberList();
		
		//memberList의 담겨져있는 Member 객체 별로 memberLevel를 확인한 다음 memberLevelName 값을 세팅
		if(memberList != null) {
			
			for(Member member : memberList) {
				String memberLevel = member.getMemberLevel();
			
				if(memberLevel != null) {
					if("1".equals(memberLevel)) {
					member.setMemberLevelName("관리자");
				}else if("2".equals(memberLevel)) {
					member.setMemberLevelName("판매자");
				}else if("3".equals(memberLevel)) {
					member.setMemberLevelName("구매자");
			}else {
				member.setMemberLevelName("회원");
		}
	}
}
			int memberListSize = memberList.size();
			
			for(int i=0 ; i<memberListSize; i++) {
				String memberLevel = memberList.get(i).getMemberLevel();
				
				if(memberLevel != null) {
					if("1".equals(memberLevel)) {
					memberList.get(i).setMemberLevelName("관리자");
				}else if("2".equals(memberLevel)) {
					memberList.get(i).setMemberLevelName("판매자");
				}else if("3".equals(memberLevel)) {
					memberList.get(i).setMemberLevelName("구매자");
			}else {
				memberList.get(i).setMemberLevelName("회원");
		}
	   }	
	}
		return memberList;
		//return memberMapper.getMemberList();
	}
		return memberList;
}
	//화면목록조회(검색)
	public List<Member> getMemberListBySearchKey(String searchKey, String searchValue){
		
		return memberMapper.getMemberListBySearchKey(searchKey, searchValue);
	}
	
	public List<MemberLevel> getMemberLevelList() {
		
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}
	
	//회원삭제
	public String removeMember(String memberId, String memberPw) {
	
		String result = "회원탈퇴 실패";
		
		Member member = memberMapper.getMemberInfoByMemberId(memberId);
		
		if(member != null) {
			String memberLevel = member.getMemberLevel();
			String chkMemberPw = member.getMemberPw();
			if(memberPw.equals(chkMemberPw)){
				//회원레벨에 따라서 삭제 처리
				//판매자일 경우
				if("2".equals(memberLevel)) {
					memberMapper.removeOrderBySellerId(memberId);
					memberMapper.removeGoodsBySellerId(memberId);
				}else if("3".equals(memberLevel)) {
					memberMapper.removeOrderByOrderId(memberId);
				}
				
				memberMapper.removeLoginHistory(memberId);
				memberMapper.removeMemberByMemberId(memberId);
				
				result="회원탈퇴 성공";
			}
		}
		
		return result;
	}
	
}


