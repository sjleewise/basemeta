package kr.wise.meta.admin.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.admin.service.AdminHelpCommentService;
import kr.wise.meta.admin.service.HelpComment;
import kr.wise.meta.admin.service.HelpCommentMapper;
import kr.wise.meta.admin.service.WaaInfoSys;

@Service("adminHelpCommentService")
public class AdminHelpCommentServiceImpl implements AdminHelpCommentService {
	@Inject
	private HelpCommentMapper mapper;
	
	@Override
	public List<HelpComment> getHelpList(HelpComment search) {
		return mapper.selectHelpList(search);
	}
}
