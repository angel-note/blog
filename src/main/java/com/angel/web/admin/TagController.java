package com.angel.web.admin;

import com.angel.po.Tag;
import com.angel.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Title: TagController
 * @Package: com.angel.web.admin.TagController
 * @Description:
 * @Auther: Angel.zhou
 * @Version: 1.0
 * @create 2019-08-04 04:06
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    // pageable 根据前端的页面构造好的参数自动封装成一个 Pageable对象

    /**
     * @param pageable : SpringBoot分页封装好的对象
     * @param model    :  前端页面展示的模型
     * @return
     * @PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) : 分页参数的设置
     * size = 10 : 按每页10条记录进行分页
     * sort = {"id"} : 根据 id 进行排序
     * direction = Sort.Direction.DESC : 进行倒序排序
     */
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));   // 把查询出来的东西放到前端展示的Model对象中
        return "admin/admin-tags-blog";
    }


    /**
     * 新增功能-页面跳转
     *      进入新增界面
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag",new Tag());
        return "admin/admin-tags-input-blog";
    }

    /**
     * 新增功能-保存数据
     *      新增页面上的一个提交功能，保存到数据库中
     * @return
     * @PostMapping("/tags") 和 @GetMapping("/tags")上边的查询分页提否会冲突？
     *    答：不会冲突，因为它们是不一样的提交方式。一个是Post提交，一个是Get方式提交。
     *        如果两个都用一样的提交方式，且提交路径一致，，则启动程序时就会报错。
     *
     * 由于在po中的Tag做了非空校验，那么在这里需要把错误的message传入到前端进行展示.
     *      @Valid: 所以需在这个Tag的类型上加个校验规则，代表要校验这个Tag对象.
     *      BindingResult result : 同时需要接收校验后的结果
     *
     * 注意：
     *      @Valid Tag tag, BindingResult result : 这种绑定的值要和需要绑定的值进行挨着。中间不能加任何参数
     */
    @PostMapping("/tags")
    public String postSave(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {

        // 校验需求二：在新增之前，需要再校验一次，不可插入相同的数据
        // 根据这个tag.getName()，去数据库里查，如果存在，则给出错误消息。
        // 需要在Service里添加一个方法
        Tag tag1 = tagService.getTagByName(tag.getName()); // 从数据库查询是否已经存在该分类
        if (tag1 != null) {
            // 如果有错误，则会在下面result进行设置。然后下面的 result.hasErrors()，则会返回值到前端
            result.rejectValue("name", "nameError", "不能重复添加分类");  // 这个也可以到前端进行接收
        }

        // 校验需求一: 后台校验非空,先校验，后保存
        if (result.hasErrors()){    // 如果后端校验有错，则重新返回到输入界面
            return "admin/admin-tags-input-blog";
        }

        // 所以有的校验通过，则保存数据到数据库中
        Tag t_result = tagService.saveTag(tag);     // 保存到数据库中
        if (t_result == null){
            // 新增失败，没保存成功
            attributes.addFlashAttribute("message", "新增失败!");

        }else{
            // 新增成功，保存成功
            attributes.addFlashAttribute("message", "新增成功!");

        }
        /**
         *  不要直接采用  return "admin/admin-tags-blog", 因为你没有经过页面的查询
         *  所以需要采用重定向的方式进行请求，并完成查询的请求。则会再次调用上面定义好的查询分面方法list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
         */
        return "redirect:/admin/tags";
    }


    /**
     * 编辑功能-页面跳转
     *      共用新增的页面
     *  思路：返回页面前，需要查询到该条记录，然后把值带过去并填充到对应的输入框中。
     *  注意：
     *      @PathVariable Long id ： 中的id要和请求路径中的{id}要一致
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){       // 根据 id 查询对应的数据记录从数据库中
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/admin-tags-input-blog";          // 把 查询到的tag值返回到页面上

    }

    /**
     * 编辑功能 - 保存
     * 修改成功后需要保存
     */
    @PostMapping("/tags/{id}")
    public String postEditSave(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {

        // 校验需求二：在新增之前，需要再校验一次，不可插入相同的数据
        // 根据这个tag.getName()，去数据库里查，如果存在，则给出错误消息。
        // 需要在Service里添加一个方法
        Tag tag1 = tagService.getTagByName(tag.getName()); // 从数据库查询是否已经存在该分类
        if (tag1 != null) {
            // 如果有错误，则会在下面result进行设置。然后下面的 result.hasErrors()，则会返回值到前端
            result.rejectValue("name", "nameError", "不能重复添加分类");  // 这个也可以到前端进行接收
        }

        // 校验需求一: 后台校验非空,先校验，后保存
        if (result.hasErrors()){    // 如果后端校验有错，则重新返回到输入界面
            return "admin/admin-tags-input-blog";
        }

        // 所以有的校验通过，则根据id更新保存数据到数据库中
        Tag t_result = tagService.updateTag(id,tag);     // 调用更新方法
        if (t_result == null){
            // 新增失败，没保存成功
            attributes.addFlashAttribute("message", "更新失败!");

        }else{
            // 新增成功，保存成功
            attributes.addFlashAttribute("message", "更新成功!");

        }
        /**
         *  不要直接采用  return "admin/admin-tags-blog", 因为你没有经过页面的查询
         *  所以需要采用重定向的方式进行请求，并完成查询的请求。则会再次调用上面定义好的查询分面方法list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
         */
        return "redirect:/admin/tags";
    }


    /**
     * 删除功能
     *  注意：
     *      @PathVariable Long id ： 中的id要和请求路径中的{id}要一致,拿到请求路径中的id参数
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteInput(@PathVariable Long id, RedirectAttributes attributes){       // 根据 id 删除数据
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功!");
        return  "redirect:/admin/tags";         // 重定向请求，并重新查询列表。

    }





}
