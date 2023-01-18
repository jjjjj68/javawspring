package com.spring.javawspring.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawspring.vo.GuestVO;
import com.spring.javawspring.vo.KakaoAddressVO;

public interface StudyDAO {

	public GuestVO getGuestMid(@Param("name") String mid);

	public ArrayList<GuestVO> getGuestNames(@Param("name") String mid);

	public KakaoAddressVO getKakaoAddressName(@Param("address") String address);

	public void setKakaoAddressName(@Param("vo") KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressNameList();

	public void setkakaoAddressExDelete(@Param("address") String address);

}
