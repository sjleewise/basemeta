package kr.wise.meta.aocd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface PusAocdMapper {

	List<PusAocdVO> getStndWordPusAocd(PusAocdVO search);

	List<PusAocdVO> getDmnPusAocd(PusAocdVO search);

	List<PusAocdVO> getItmPusAocd(PusAocdVO search);

	List<PusAocdVO> getTblPusAocd(PusAocdVO search);

	List<PusAocdVO> getTblPusAocdWhereused(PusAocdVO search);

}
