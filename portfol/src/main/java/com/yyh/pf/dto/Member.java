package com.yyh.pf.dto;

import org.apache.ibatis.type.Alias;

@Alias("Member")
public class Member {
	private int memberIdx;
	private int typeSeq;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberNick;
	private String email;
	private String birthDate;
	private String createDate;
	@Override
	public String toString() {
		return "Member [memberIdx=" + memberIdx + ", typeSeq=" + typeSeq + ", memberId=" + memberId + ", memberPw="
				+ memberPw + ", memberName=" + memberName + ", memberNick=" + memberNick + ", email=" + email
				+ ", birthDate=" + birthDate + ", createDate=" + createDate + "]";
	}
	public int getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}
	public int getTypeSeq() {
		return typeSeq;
	}
	public void setTypeSeq(int typeSeq) {
		this.typeSeq = typeSeq;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNick() {
		return memberNick;
	}
	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
