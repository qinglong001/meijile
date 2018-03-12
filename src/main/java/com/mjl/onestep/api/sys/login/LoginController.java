package com.mjl.onestep.api.sys.login;
import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.interceptor.IgnoreAuth;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.os.OsStationInfo;
import com.mjl.onestep.po.sys.SysToken;
import com.mjl.onestep.po.sys.SysUser;
import com.mjl.onestep.po.sys.SysUserRole;
import com.mjl.onestep.repository.os.OsStationInfoRs;
import com.mjl.onestep.repository.sys.SysTokenRs;
import com.mjl.onestep.repository.sys.SysUserRoleRs;
import com.mjl.onestep.repository.sys.SysUserRs;
import com.mjl.onestep.service.LoginService;
import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
/**
 * @author fcj
 * 登录 注册 相关接口控制器
 */
@RestController
@RequestMapping("/api")
@Api(value="登陆退出相关接口 LoginController",tags={"SYS系统 登陆退出相关接口"})
public class LoginController  extends BaseController{
	
	
	@Autowired
    private SysTokenRs sysTokenRs;
	@Autowired
    private SysUserRs sysUserRs;
	@Autowired
    private LoginService loginService;
	@Autowired
    private OsStationInfoRs osStationInfoRs;
	@Autowired
    private SysUserRoleRs sysUserRoleRs;

	@ApiOperation(value="用户登录", notes="用户登录接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "password", value = "密码", dataType = "String",paramType="form"),
	})
	@PostMapping("/login")
	
	@IgnoreAuth
	public AjaxJson login(@RequestParam(required = false) String mobile,
						  @RequestParam(required = false) String password
	){
		AjaxJson a=	getA();
		if(StrUtil.isNotBlank(mobile)&&StrUtil.isNotBlank(password)){
			SysUser finduser=sysUserRs.findUserByMobileAndPassword(mobile, password);
			if(finduser!=null){
				final int expire = 3600 * 12;
				HashMap<String, Object> map = new HashMap<String, Object>( 5);
				map.put("expire", expire);
				map.put("userName", finduser.getUsername());
				map.put("userId", finduser.getUserId());
				SysToken tonken=loginService.createToken(finduser.getUserId(),finduser.getUsername());
				map.put("token",  tonken.getToken());
				//查询是否创建过站点
				List<OsStationInfo> osStationInfos=	osStationInfoRs.findByStationUserId(finduser.getUserId());
				if(osStationInfos.size()>0){
					map.put("stationFlag", true);
				}else{
					map.put("stationFlag", false);
				}
				a.put("data", map);
				return a;
			}else{
				a.errorMsg("手机号或密码不正确", "-1");
			}
		}else{
			a.errorMsg("手机号或密码不能为空", "-1");
		}
		return a;
	}
	
	
	@ApiOperation(value="用户注册", notes="用户注册接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String",paramType="form"),
		@ApiImplicitParam(name = "password", value = "密码", dataType = "String",paramType="form"),
		@ApiImplicitParam(name = "username", value = "用户名", dataType = "String",paramType="form")
	})
	@PostMapping("/register")
	@IgnoreAuth
	public AjaxJson register(@RequestParam(required = false) String mobile,
							 @RequestParam(required = false) String password,
							 @RequestParam(required = false) String username
	){
		AjaxJson a=	getA();
		if(StrUtil.isNotBlank(mobile)&&StrUtil.isNotBlank(password)&&StrUtil.isNotBlank(username)){
			SysUser finduser=sysUserRs.findUserByMobile(mobile);
			if(ObjectUtil.isNull(finduser)){
				SysUser user=new SysUser();
				user.setMobile(mobile);
				user.setPassword(password);
				user.setUsername(username);
				user.setCreateTime(DateUtil.now());
				user=sysUserRs.save(user);
				SysUserRole sysUserRole=new SysUserRole();
				sysUserRole.setRoleId(Convert.toLong(2));
				sysUserRole.setUserId(user.getUserId());
				sysUserRoleRs.save(sysUserRole);
				a.successMsg("注册成功");
			}else{
				a.setSuccess(false);
				a.setMsg("该手机号已经被注册");
			}
		}else{
			a.errorMsg("手机号、密码、用户名不能为空", "-1");
		}
		return a;
	}
	
	
	@ApiOperation(value="获取短信验证码", notes="根据手机号和token令牌获取注册的短信验证码")
	@ApiImplicitParam(name = "mobile", value = "注册手机号", dataType = "String",paramType="form")
	@PostMapping("/getSmsCode")
	@IgnoreAuth
	public AjaxJson getSmsCode(@RequestParam(required = false) String mobile
	){
		AjaxJson a=	getA();
		SysUser finduser=sysUserRs.findUserByMobile(mobile);
		if(ObjectUtil.isNull(finduser)){
			String code="";
			a.successMsg("发送成功");
			a.put("code", code);
		}else{
			a.errorMsg("该手机号已注册", "-1");
		}
		return a;
	}

	@ApiOperation(value="校验token是否失效", notes="校验token是否失效")
	@PostMapping("/checkToken")
	public AjaxJson checkToken(
	){
		AjaxJson a=	getA();
		a.successMsg("token未失效");
		return a;
	}
	
	
	
	@ApiOperation(value="退出登录")
	@PostMapping("/loginOut")
	@CacheEvict(value="Token", allEntries=true)
	public AjaxJson loginOut(){
		AjaxJson a=	getA();
		//获取请求的token
		SysToken t = getToken();
		
		sysTokenRs.delete(t.getUserId());
		a.successMsg("退出成功");
		return a;
	}
	
}
