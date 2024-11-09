package com.project.template.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.project.template.dto.SysUserDTO;
import com.project.template.entity.SysUser;
import com.project.template.exception.CustomException;
import com.project.template.service.impl.SysUserServiceImpl;
import com.project.template.utils.JwtUtils;
import com.project.template.utils.UserThreadLocal;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserServiceImpl sysUserService;
//    定义一个私有变量，用于存储系统用户服务的实例，以便在拦截器的方法中调用系统用户相关的业务逻辑方法

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 打印请求信息
        log.info("------------- LoginInterceptor 开始 -------------");
//        记录日志，表示拦截器开始处理请求
        long startTime = System.currentTimeMillis();
//        记录请求开始时间
        request.setAttribute("requestStartTime", startTime);
//        将请求开始时间存储在请求属性中，以便在后续的postHandle和afterCompletion方法中使用

        // OPTIONS请求不做校验,
        // 前后端分离的架构, 前端会发一个OPTIONS请求先做预检, 对预检请求不做校验
        if(request.getMethod().toUpperCase().equals("OPTIONS")){
//            如果请求方法是OPTIONS，则直接返回true，表示不进行拦截，因为前端在进行跨域请求时可能会先发送一个OPTIONS预检请求
            return true;
        }

        String path = request.getRequestURL().toString();
//        获取请求的 URL 路径
        log.info("接口登录拦截：，path：{}", path);
//记录请求的 URL 路径

        //获取header的token参数
        String token = request.getHeader("token");
        log.info("登录校验开始，token：{}", token);
        if (token == null || token.isEmpty()) {
//            如果token为空或为空字符串，则设置响应状态码为401 Unauthorized，并返回false，表示请求被拦截
            log.info( "token为空，请求被拦截" );
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return false;
        }


        Claims claims = JwtUtils.verifyJwt(token);
//        使用JwtUtils.verifyJwt方法验证token，并获取Claims对象，其中包含了 JWT 中的声明信息

        //获取用户ID
        if (claims == null) {
//            如果claims为null，表示token无效，抛出CustomException异常，并设置响应状态码为401 Unauthorized
            log.warn( "token无效，请求被拦截" );
            throw new CustomException(401,"token无效，请求被拦截");
        } else {
            Integer userId = (Integer)claims.get("userId");
//            从claims中获取用户 ID
            SysUser byId = sysUserService.getById(userId);
//            创建一个系统用户数据传输对象
            if(byId != null){

//                if(!RoleType.ROLE_ADMIN.equals(byId.getRoleType())){
//                    if (!Utils.isRole(path,byId.getRoleType().getKey())) {
//                        throw new CustomException(401,"该接口无访问权限");
//                    }
//                }

                //输出
                SysUserDTO sysUserDTO = new SysUserDTO();
//                创建一个系统用户数据传输对象。
                BeanUtil.copyProperties(byId,sysUserDTO);
//                使用BeanUtil.copyProperties方法将系统用户实体的属性复制到数据传输对象中
                UserThreadLocal.set(sysUserDTO);
//将数据传输对象存储在UserThreadLocal中，以便在当前线程的其他地方获取当前登录用户的信息
            }
            log.info("已登录：{}", userId);
//记录用户已登录的信息
            return true;
//            表示请求通过拦截器的校验，可以继续处理
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        实现了HandlerInterceptor接口中的postHandle方法，在请求处理完成后，但在视图渲染之前进行一些处理
        long startTime = (Long) request.getAttribute("requestStartTime");
//        从请求属性中获取请求开始时间
        log.info("------------- LoginInterceptor 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
//        记录拦截器处理请求的耗时
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//实现了HandlerInterceptor接口中的afterCompletion方法，在请求处理完成后，视图渲染完成后进行一些清理工作
        UserThreadLocal.clear();
//        清除UserThreadLocal中存储的当前登录用户信息
        log.info("LogInterceptor 结束");
//        记录拦截器结束的信息
    }
}
