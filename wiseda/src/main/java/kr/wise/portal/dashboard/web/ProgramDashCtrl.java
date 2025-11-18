package kr.wise.portal.dashboard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.JqGridListVO;
import kr.wise.portal.dashboard.service.ProgramCountDashProcVO;
import kr.wise.portal.dashboard.service.ProgramDashService;
import kr.wise.portal.dashboard.service.ProgramDocumentCountProcVO;
import kr.wise.portal.dashboard.service.ProgramSourceUpdateProcVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/portal/dashboard")
public class ProgramDashCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ProgramDashService programDashService;
	
    //프로그램현황 탭	
	@RequestMapping("/ProgramDash.do")
	public ModelAndView getProcAssist(@ModelAttribute("searchVO") BoardVO boardVO, HttpServletRequest request)throws Exception{
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);
		
//		List<ProgramCountDashProcVO> listProc = programDashService.getProcAssist();
		
		ModelAndView mv = new ModelAndView();
		
//		mv.addObject("listProc", listProc);
		mv.setViewName("/portal/dashboard/programdash");
		
		return mv;
		
	}
	
	/** 프로그램 본수-폼내용 */
	@RequestMapping("/ajaxgrid/programBon.do")
	public String programBonForm() {
		
		return "/portal/dashboard/ajaxgrid/programbon";
	}
	
	/** 프로그램 본수-ajaxgrid */
	@RequestMapping("/ajaxgrid/ProgramList.do")
	@ResponseBody
	public JqGridListVO<ProgramCountDashProcVO> selectProgramBonList() throws Exception {
		List<ProgramCountDashProcVO> listProc = programDashService.getProcAssist();
		
		return new JqGridListVO<ProgramCountDashProcVO>(listProc);
	}
	
	/** 업무별 프로그램 변경-폼내용 */
	@RequestMapping("/ajaxgrid/programUpdate.do")
	public String programUpdateForm() {
		
		return "/portal/dashboard/ajaxgrid/programUpdate";
	}
	
	/** 업무별 프로그램 변경-ajaxgrid */
	@RequestMapping("/ajaxgrid/ProgramUpdateList.do")
	@ResponseBody
	public JqGridListVO<ProgramSourceUpdateProcVO> selectProgramUpdateList(HttpServletRequest request) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));
		
		List<ProgramSourceUpdateProcVO> listUpdateProc = programDashService.getProcAssistUpdate(map);
		
		return new JqGridListVO<ProgramSourceUpdateProcVO>(listUpdateProc);
	}

	/** 업무별  요청서집계-폼 */
	@RequestMapping("/ajaxgrid/programRequest.do")
	public String programRequestForm() {
		
		return "/portal/dashboard/ajaxgrid/programRequest";
	}
	
	/** 업무별  요청서집계-ajaxgrid */
	@RequestMapping("/ajaxgrid/ProgramReqList.do")
	@ResponseBody
	public JqGridListVO<ProgramDocumentCountProcVO> selectProgramReqList(HttpServletRequest request) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("startDateSub", request.getParameter("startDateSub"));
		map.put("endDateSub", request.getParameter("endDateSub"));
		
//		List<ProgramCountDashProcVO> listProc = programDashService.getProcAssist();
		List<ProgramDocumentCountProcVO> listRequestProc = programDashService.getProgramProcAssistRequest(map);
		
		return new JqGridListVO<ProgramDocumentCountProcVO>(listRequestProc);
	}
	
	//프로그램본수	
	@RequestMapping("/ajax/ProgramList.do")
	public ModelAndView getProcList(@ModelAttribute("searchVO") BoardVO boardVO, HttpServletRequest request)throws Exception{
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		log.debug("USER : {}", user);

		List<ProgramCountDashProcVO> listProc = programDashService.getProcAssist();
		ModelAndView mv = new ModelAndView();
		
		if(listProc.size() > 0) {
			int sogeCntClient =0;
			int sogeCntService =0;
			int sogeCntBatch =0;
			int sogeCntFunc =0;
			int sogeCntJsp =0;
			int sogeCntJava =0;
			int sogeCntEtc =0;
			int sogeCntWaste =0;
			
			for(int i=listProc.size();i>0;i--) {
				ProgramCountDashProcVO vo = listProc.get(i-1);
				vo.setCntTotal(Integer.parseInt(vo.getCntClient()) +
						       Integer.parseInt(vo.getCntService())+
						       Integer.parseInt(vo.getCntBatch())  +
						       Integer.parseInt(vo.getCntFunc())   +
						       Integer.parseInt(vo.getCntJsp())    +
						       Integer.parseInt(vo.getCntJava())   +
						       Integer.parseInt(vo.getCntEtc())    +
						       Integer.parseInt(vo.getCntWaste())  +
						       "");
				
				listProc.add(i, vo);
				sogeCntClient  += Integer.parseInt(vo.getCntClient());
				sogeCntService += Integer.parseInt(vo.getCntService());
				sogeCntBatch   += Integer.parseInt(vo.getCntBatch());
				sogeCntFunc    += Integer.parseInt(vo.getCntFunc());
				sogeCntJsp     += Integer.parseInt(vo.getCntJsp());
				sogeCntJava    += Integer.parseInt(vo.getCntJava());
				sogeCntEtc     += Integer.parseInt(vo.getCntEtc());
				sogeCntWaste   += Integer.parseInt(vo.getCntWaste());
				
				listProc.remove(i-1);
			}
			ProgramCountDashProcVO vo = new ProgramCountDashProcVO();
			vo.setPrName("소계");
			vo.setCntClient(sogeCntClient+"");
			vo.setCntService(sogeCntService+"");
			vo.setCntBatch(sogeCntBatch+"");
			vo.setCntFunc(sogeCntFunc+"");
			vo.setCntJsp(sogeCntJsp+"");
			vo.setCntJava(sogeCntJava+"");
			vo.setCntEtc(sogeCntEtc+"");
			vo.setCntWaste(sogeCntWaste+"");
			
			vo.setCntTotal((sogeCntClient  + 
					        sogeCntService + 
					        sogeCntBatch   +
					        sogeCntFunc    +
					        sogeCntJsp     +
					        sogeCntJava    +
					        sogeCntEtc     +
					        sogeCntWaste)  +
					        "");
			
			listProc.add(0, vo);
		}
				
		mv.addObject("listProc", listProc);
		mv.setViewName("/portal/dashboard/programdash");
		
		return mv;
		
	}
	
	//업무별 프로그램 변경
	@RequestMapping("/ajax/ProgramProcAssistUpdate.do")
	public ModelAndView getProcAssistUpdate(HttpServletRequest request)throws Exception{
		
		ModelAndView mv = new ModelAndView();
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));
		
//		List<ProgramCountDashProcVO> listProc = programDashService.getProcAssist();
		List<ProgramSourceUpdateProcVO> listUpdateProc = programDashService.getProcAssistUpdate(map);
		
		if(listUpdateProc.size() > 0) {
			int sogeCntDelete = 0;
			int sogeCntModify = 0;
			int sogeCntCntNew = 0;
			
			for(int i=listUpdateProc.size();i>0;i--) {
				ProgramSourceUpdateProcVO vo = listUpdateProc.get(i-1);
				vo.setCntTotal((Integer.parseInt(vo.getCntDelete())+Integer.parseInt(vo.getCntModify())+Integer.parseInt(vo.getCntNew()))+"");
				listUpdateProc.add(i, vo);
				sogeCntDelete += Integer.parseInt(vo.getCntDelete());
				sogeCntModify += Integer.parseInt(vo.getCntModify());
				sogeCntCntNew += Integer.parseInt(vo.getCntNew());
				listUpdateProc.remove(i-1);
			}
			ProgramSourceUpdateProcVO vo = new ProgramSourceUpdateProcVO();
			vo.setPrName("소계");
			vo.setCntDelete(sogeCntDelete+"");
			vo.setCntModify(sogeCntModify+"");
			vo.setCntNew(sogeCntCntNew+"");
			vo.setCntTotal((sogeCntDelete + sogeCntModify + sogeCntCntNew)+"");
			listUpdateProc.add(0, vo);
		}
		
//		mv.addObject("listProc", listProc);
		mv.addObject("listUpdateProc", listUpdateProc);
		mv.addObject("parammap", map);
		mv.setViewName("/portal/dashboard/programdash");
		
		return mv;
		
	}
	
	//업무별 요청서 집계
	@RequestMapping("/ajax/ProgramProcAssistRequest.do")
	public ModelAndView getProgramProcAssistRequest(HttpServletRequest request)throws Exception{
		
		ModelAndView mv = new ModelAndView();
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("startDateSub", request.getParameter("startDateSub"));
		map.put("endDateSub", request.getParameter("endDateSub"));
		
//		List<ProgramCountDashProcVO> listProc = programDashService.getProcAssist();
		List<ProgramDocumentCountProcVO> listRequestProc = programDashService.getProgramProcAssistRequest(map);
		
		if(listRequestProc.size() > 0) {
			int sogeCntRequest   =0;
			int sogeCntCklist    =0;
			int sogeCntReport    =0;
			int sogeCntDistNormal=0;
			int sogeCntDistUrgent=0;
			int sogeCntOnLine    =0;
			int sogeCntDatabase  =0;
			int sogeCntFml       =0;
			int sogeCntBatch     =0;
			int sogeCntWindow    =0;
			int sogeCntCode      =0;
			int sogeCntWaste     =0;
			
			for(int i=listRequestProc.size();i>0;i--) {
				ProgramDocumentCountProcVO vo = listRequestProc.get(i-1);
				vo.setCntTotal(Integer.parseInt(vo.getCntRequest())   +
						       Integer.parseInt(vo.getCntCklist())    +
						       Integer.parseInt(vo.getCntReport())    +
						       Integer.parseInt(vo.getCntDistNormal())+
						       Integer.parseInt(vo.getCntDistUrgent())+
						       Integer.parseInt(vo.getCntOnLine())    +
						       Integer.parseInt(vo.getCntDatabase())  +
						       Integer.parseInt(vo.getCntFml())       +
						       Integer.parseInt(vo.getCntBatch())     +
						       Integer.parseInt(vo.getCntWindow())    +
						       Integer.parseInt(vo.getCntCode())      +
						       Integer.parseInt(vo.getCntWaste())     +
						       "");
				listRequestProc.add(i, vo);

				sogeCntRequest   +=Integer.parseInt(vo.getCntRequest());
				sogeCntCklist    +=Integer.parseInt(vo.getCntCklist());
				sogeCntReport    +=Integer.parseInt(vo.getCntReport());
				sogeCntDistNormal+=Integer.parseInt(vo.getCntDistNormal());
				sogeCntDistUrgent+=Integer.parseInt(vo.getCntDistUrgent());
				sogeCntOnLine    +=Integer.parseInt(vo.getCntOnLine());
				sogeCntDatabase  +=Integer.parseInt(vo.getCntDatabase());
				sogeCntFml       +=Integer.parseInt(vo.getCntFml());
				sogeCntBatch     +=Integer.parseInt(vo.getCntBatch());
				sogeCntWindow    +=Integer.parseInt(vo.getCntWindow());
				sogeCntCode      +=Integer.parseInt(vo.getCntCode());
				sogeCntWaste     +=Integer.parseInt(vo.getCntWaste());
				
				listRequestProc.remove(i-1);
			}
			ProgramDocumentCountProcVO vo = new ProgramDocumentCountProcVO();
			vo.setPrName("소계");
			vo.setCntRequest(sogeCntRequest+"");
			vo.setCntCklist(sogeCntCklist+"");
			vo.setCntReport(sogeCntReport+"");
			vo.setCntDistNormal(sogeCntDistNormal+"");
			vo.setCntDistUrgent(sogeCntDistUrgent+"");
			vo.setCntOnLine(sogeCntOnLine+"");
			vo.setCntDatabase(sogeCntDatabase+"");
			vo.setCntFml(sogeCntFml+"");
			vo.setCntBatch(sogeCntBatch+"");
			vo.setCntWindow(sogeCntWindow+"");
			vo.setCntCode(sogeCntCode+"");
			vo.setCntWaste(sogeCntWaste+"");

			vo.setCntTotal((sogeCntRequest + 
					        sogeCntCklist + 
					        sogeCntReport +
					        sogeCntDistNormal +
					        sogeCntDistUrgent + 
					        sogeCntOnLine + 
					        sogeCntDatabase +
					        sogeCntFml +
					        sogeCntBatch + 
					        sogeCntWindow + 
					        sogeCntCode + 
					        sogeCntWaste)+
					        "");
			
			listRequestProc.add(0, vo);
		}
//		mv.addObject("listProc", listProc);
		mv.addObject("listRequestProc", listRequestProc);
		mv.addObject("parammap3", map);
		mv.setViewName("/portal/dashboard/programdash");
		
		return mv;
		
	}
	
}
