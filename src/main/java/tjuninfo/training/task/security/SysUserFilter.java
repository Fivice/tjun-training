package tjuninfo.training.task.security;

import org.apache.shiro.web.filter.PathMatchingFilter;

import tjuninfo.training.task.service.ISysUserService;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义用户过滤器
 * @author shenxianyan
 * @date 2018年5月16日
 */
public class SysUserFilter extends PathMatchingFilter {
	
	@Resource
	private ISysUserService sysUserService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //可以参考http://jinnianshilongnian.iteye.com/blog/2025656
    	return true;
    }
}
