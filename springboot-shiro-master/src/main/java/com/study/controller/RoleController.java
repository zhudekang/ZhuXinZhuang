package com.study.controller;

import com.github.pagehelper.PageInfo;
import com.study.model.Role;
import com.study.model.RoleResources;
import com.study.model.User;
import com.study.service.RoleResourcesService;
import com.study.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/26.
 */
@RestController
@RequestMapping("/roles")
@Api(value = "RoleController", description = "角色接口")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private RoleResourcesService roleResourcesService;

    
    @ApiOperation(value = "查找角色", notes = "查找角色", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "role", value = "角色详细实体role", required = false, dataType = "Role"),
    	@ApiImplicitParam(name = "draw", value = "", required = false, dataType = "String", paramType = "query", defaultValue = ""),
		@ApiImplicitParam(name = "start", value = "分页起始", required = true, dataType = "Long", paramType = "query", defaultValue = "1"),
		@ApiImplicitParam(name = "length", value = "分页每页条数", required = true, dataType = "Long", paramType = "query", defaultValue = "10")
	})
    @ApiResponses({
      @ApiResponse(code=400,message="请求参数没填好"),
      @ApiResponse(code=401,message="未授权"),
      @ApiResponse(code=403,message="失败"),
      @ApiResponse(code=404,message="没有找到对应资源"),
      @ApiResponse(code=500,message="服务无响应")
    })
    @RequestMapping(method = RequestMethod.GET)
    public  @ResponseBody Map<String,Object> getAll( Role role, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length){

        Map<String,Object> map = new HashMap<>();
        PageInfo<Role> pageInfo = roleService.selectByPage(role, start, length);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    
    @RequestMapping(value="/rolesWithSelected",method = RequestMethod.GET)
    public List<Role> rolesWithSelected(Integer uid){
        return roleService.queryRoleListWithSelected(uid);
    }

    //分配角色
    @RequestMapping(value="/saveRoleResources",method = RequestMethod.POST)
    public String saveRoleResources(RoleResources roleResources){
        if(StringUtils.isEmpty(roleResources.getRoleid()))
            return "error";
        try {
            roleResourcesService.addRoleResources(roleResources);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(@RequestBody Role role) {
        try {
            roleService.save(role);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public String delete(Integer id){
        try{
            roleService.delRole(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }



}
