package com.spring.javawspring.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.KakaoAddressVO;
import com.spring.javawspring.vo.TransactionVO;

public interface StudyDAO {

	public GuestVO getGuestMid(@Param("name") String mid);

	public ArrayList<GuestVO> getGuestNames(@Param("name") String mid);

	public KakaoAddressVO getKakaoAddressName(@Param("address") String address);

	public void setKakaoAddressName(@Param("vo") KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressNameList();

	public void setkakaoAddressExDelete(@Param("address") String address);

	public void setTransInput1(@Param("vo") TransactionVO vo);

	public void setTransInput2(@Param("vo") TransactionVO vo);

	public List<TransactionVO> setTransList();

	public void setTransInput(@Param("vo") TransactionVO vo);

}
