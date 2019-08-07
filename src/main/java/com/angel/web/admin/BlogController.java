package com.angel.web.admin;

import com.angel.po.Blog;
import com.angel.po.User;
import com.angel.service.BlogService;
import com.angel.service.TagService;
import com.angel.service.TypeService;
import com.angel.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Title: BlogController
 * @Package: com.angel.web.admin.BlogController
 * @Description: 登录拦截器
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 02:48
 */

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/admin-release-blog";  // 跳转发布博客页面
    private static final String LIST = "admin/admin-list-blog";  // 跳转博客列表页面
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";  // 重定向到博客列表页面


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;        // 分类
    @Autowired
    private TagService tagService;          // 标签


    /**
     * "/blogs" : 就能看到博客管理页面.所以需要过滤所有以/admin/*的请求，只有当登录成功才能访问
     * size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC)
     *   每页2条数据，并且按照 updateTime 进行倒序分页
     * @return
     */
    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 7,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){

        // 加载 分类的下拉框的数据
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;

    }
    // 用于局部渲染表格。不需要整体刷新
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/admin-list-blog :: blogList"; // 返回admin/admin-list-blog.html 中的 blogList片段

    }



    // 跳转到发布博客的页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        // 初始化分类的下拉框的数据
        model.addAttribute("types",typeService.listType());
        // 初始化标签的下拉框的数据
        model.addAttribute("tags",tagService.listTag());

        model.addAttribute("blog",new Blog());
        return INPUT;
    }


    // 新增和修改是可以共用一个方法

    /**
     *
     * @param blog
     * @param session : 因为Session里有user对象
     * @return
     *
     * 这边没有做后台的验证，后期可以加上
     */
    @PostMapping("/blogs")
    public String insertAndUpdate(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));

        Blog blog1 = blogService.saveBlog(blog);// 保存到数据库中

        // 需要设置标签、分类，进行存放到数据库中
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));  // 从页面
        if (blog1 == null){
            // 新增失败，没保存成功
            attributes.addFlashAttribute("message", "操作失败!");

        }else{
            // 新增成功，保存成功
            attributes.addFlashAttribute("message", "操作成功!");

        }
        return REDIRECT_LIST;
    }
}
