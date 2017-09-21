package com.study.controller;

import com.github.pagehelper.PageInfo;
import com.study.model.User;
import com.study.model.UserRole;
import com.study.service.UserRoleService;
import com.study.service.UserService;
import com.study.util.PasswordHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

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
import java.util.Map;

/**
 * Created by yangqj on 2017/4/22.
 */
@RestController
@RequestMapping("/users")
@Api("UserController相关api")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;

    @ApiIgnore
    @RequestMapping
    public Map<String,Object> getAll(User user, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<User> pageInfo = userService.selectByPage(user, start, length);
        System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }


    /**
     * 保存用户角色
     * @param userRole 用户角色
     *  	  此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
//    @ApiIgnore
    @RequestMapping(value="/saveUserRoles")
//    @RequestMapping(value="/saveUserRoles",method = RequestMethod.POST)
    public String saveUserRoles(UserRole userRole){
        if(StringUtils.isEmpty(userRole.getUserid()))
            return "error";
        try {
            userRoleService.addUserRole(userRole);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

//    @ApiOperation(value = "添加用户", notes = "添加用户")
//    @ApiResponses({
//         @ApiResponse(code=400,message="请求参数没填好"),
//         @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//    })
//    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @RequestMapping(value = "/add")
    public String add(@RequestBody User user) {
        User u = userService.selectByUsername(user.getUsername());
        if(u != null)
            return "error";
        try {
            user.setEnable(1);
            PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(user);
            userService.save(user);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

//    @ApiOperation(value = "删除用户", notes = "删除用户")
   /* @ApiImplicitParams({
 	   @ApiImplicitParam(paramType="header",name="userId",dataType="Integer",required=true,value="用户的id",defaultValue="0"),
    })*/
//    @ApiResponses({
//        @ApiResponse(code=400,message="请求参数没填好"),
//        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//   })
//    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @RequestMapping(value = "/delete")
    public String delete(@RequestBody Integer id){
      try{
          userService.delUser(id);
          return "success";
      }catch (Exception e){
          e.printStackTrace();
          return "fail";
      }
    }

}
