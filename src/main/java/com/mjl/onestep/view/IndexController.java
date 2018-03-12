package com.mjl.onestep.view;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.interceptor.IgnoreAuth;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.os.OsContent;
import com.mjl.onestep.po.os.OsStationInfo;
import com.mjl.onestep.po.os.OsTemplate;
import com.mjl.onestep.po.sys.SysToken;
import com.mjl.onestep.repository.os.OsContentRs;
import com.mjl.onestep.repository.os.OsStationInfoRs;
import com.mjl.onestep.repository.os.OsTemplateRs;
import com.mjl.onestep.repository.sys.SysTokenRs;
import com.mjl.onestep.service.StationService;
import com.xiaoleilu.hutool.json.JSONObject;
/**
 * 前端页面跳转
 * 
 * @author fcj
 *
 */
@Controller
@Api(value="前端跳转接口",tags={"前端视图层跳转"})
public class IndexController extends BaseController{
	
	
	@Autowired
    private OsContentRs osContentRs;
	@Autowired
    private SysTokenRs sysTokenRs;
	@Autowired
    private OsStationInfoRs osStationInfoRs;
	@Autowired
    private OsTemplateRs osTemplateRs;
	@Autowired
    private StationService stationService;
	@GetMapping("/")
	@ApiOperation(value="默认加载页面", notes="默认加载页面")
	public String index(Model model ,HttpServletRequest request){
		String  prefix=	stationService.getPrefix(request);
		//根据前缀获取 (默认为www)
		
		String linkurl=null;
		if(prefix==null){
			prefix="www";
		}else{
			OsStationInfo oStationInfo=	osStationInfoRs.findByPrefix(prefix);
			if(oStationInfo!=null) {
				OsTemplate	template=osTemplateRs.getOne(oStationInfo.getTemplateId()) ;
				model.addAttribute("templateId",oStationInfo.getTemplateId());
				model.addAttribute("stationId",oStationInfo.getId());
				if(template!=null){
					linkurl=template.getLinkurl();
				}
			}
		}
		return linkurl;
	}
	
	
	@ApiOperation(value="根据域名前缀相关所有内容信息", notes="根据域名前缀相关所有内容信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "prefix", value = "域名前缀",  dataType = "String" ,paramType="form")
	})
	@PostMapping("api/content/prefix")
	@ResponseBody
	@IgnoreAuth
	public AjaxJson prefix(		
								@RequestParam(required = false) String prefix
							){
			AjaxJson a=	getA();
			OsStationInfo oStationInfo=	osStationInfoRs.findByPrefix(prefix);
			if(oStationInfo==null) {
				a.errorMsg("站点不存在", "-1");
				return a ;
			}
			List<OsContent> listPo=	osContentRs.findByStationIdAndTemplateId(oStationInfo.getStationUserId(), oStationInfo.getTemplateId());
			JSONObject json=new JSONObject();
			if(listPo.size()>0) {
				for (OsContent aListPo : listPo) {
					if(2==aListPo.getType()){
						json.put(aListPo.getSignId(),"http://oz8mm7mtb.bkt.clouddn.com/"+aListPo.getContentInfo());
					}else{
						json.put(aListPo.getSignId(),aListPo.getContentInfo());
					}
				}
			}
			a.put("data", json);
		return a;
	}
	@ApiOperation(value="根据站点id和模板id获取站点相关所有内容信息", notes="根据站点id和模板id获取站点相关所有内容信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "stationId", value = "站点id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "templateId", value = "模板id",  dataType = "Long" ,paramType="form")
	})
	@PostMapping("api/content/list")
	@ResponseBody
	@IgnoreAuth
	public AjaxJson list(		
								@RequestParam(required = false) Long stationId,
								@RequestParam(required = false) Long templateId
							){
		AjaxJson a=	getA();
		List<OsContent> listPo=	osContentRs.findByStationIdAndTemplateId(stationId, templateId);
		JSONObject json=new JSONObject();
		System.out.println(listPo.size());
		if(listPo.size()>0) {
			for (OsContent aListPo : listPo) {
				if(2==aListPo.getType()){
					json.put(aListPo.getSignId(),"http://oz8mm7mtb.bkt.clouddn.com/"+aListPo.getContentInfo());
				}else{
					json.put(aListPo.getSignId(),aListPo.getContentInfo());
				}
			}
		}
		a.put("data", json);
		return a;
	}
	
	@RequestMapping("/editStation")
	@ApiOperation(value=" 跳转编辑站点页面", notes="跳转编辑站点页面")
	public String editStation(HttpServletRequest request,Model model ){

		String tokenStr = request.getParameter("token");
		//根据token获取用户id
		SysToken token=	sysTokenRs.findTokenByToken(tokenStr);
		String	prefix = request.getParameter("prefix");
		if(prefix==null){
			prefix=	stationService.getPrefix(request);
		}
		OsStationInfo oStationInfo=	osStationInfoRs.findByPrefixAndStationUserId(prefix,token.getUserId());
		//模板访问路径
		String linkurl="";
		//站点id
		Long stationId = null;
		//模板id
		Long templateId = null;
		//站点名称
		String stationName="";
		String msg = "";
		if(oStationInfo!=null) {
			stationName=oStationInfo.getName();
			stationId=oStationInfo.getId();
			OsTemplate	template=osTemplateRs.findOne(oStationInfo.getTemplateId()) ;
			if(template!=null){
				linkurl=template.getLinkurl();
				templateId=template.getId();
			}
		}else{
			msg ="权限不足 ";
		}
		model.addAttribute("stationName",stationName);
		model.addAttribute("msg",msg);
		model.addAttribute("templateId",templateId);
		model.addAttribute("token",token.getToken());
		model.addAttribute("stationId",stationId);
		model.addAttribute("linkurl",linkurl);
		return "editStation";
	}
	

}
