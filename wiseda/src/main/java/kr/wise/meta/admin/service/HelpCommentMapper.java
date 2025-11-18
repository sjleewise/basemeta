package kr.wise.meta.admin.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface HelpCommentMapper {

	List<HelpComment> selectHelpList(HelpComment search);
}
