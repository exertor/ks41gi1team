package ksmart41.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import ksmart41.mybatis.dto.Member;
import ksmart41.mybatis.dto.MemberLevel;

@Mapper
public interface MemberMapper {
	// 회원목록 조회 (검색)
	public List<Member> getMemberListBySearchKey(String searchKey, String searchValue);
	
	//회원탈퇴 tb_order 판매자일 경우 주문 상품 삭제
	public int removeOrderBySellerId(String memberId);
	
	//회원탈퇴 tb_order 구매자일 경우 주문 내역 삭제 
	public int removeOrderByOrderId(String memberId);
	
	//회원탈퇴 tb_goods 
	public int removeGoodsBySellerId(String memberId);
	
	//회원탈퇴 tb_login 
	public int removeLoginHistory(String memberId);
	
	//회원탈퇴 tb_member 
	public int removeMemberByMemberId(String memberId);
	
	//회원정보수정
	public int modifyMemberInfo(Member member);
	
	//회원조회
	public Member getMemberInfoByMemberId(String memberId);

	// 회원 중복 체크
	public int getMemberbyMemberId(String memberId);
	
	//회원 전체 조회
	public List<Member> getMemberList();
		
	// 회원등록
	public int addMember(Member member);

	public List<MemberLevel> getMemberLevelList();
	
	
}
