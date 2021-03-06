package com.hacker.oa.controller.modular.oa.user;

import com.hacker.oa.bean.PageResult;
import com.hacker.oa.common.JsonViewFactory;
import com.hacker.oa.entity.TUserTask;
import com.hacker.oa.service.TUserTaskService;
import javax.annotation.Resource;
import com.hacker.oa.common.DateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("tUserTask")
public class TUserTaskController  {

	@Resource(name = "tUserTaskService")
	private TUserTaskService tUserTaskService;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
    public String all() throws Exception {
        return "t_user_task_all";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String save(@ModelAttribute TUserTask tUserTaskWhere) throws Exception {
    	Assert.notNull(tUserTaskWhere);
		TUserTask tUserTask = (TUserTask) tUserTaskWhere;
    	if (tUserTaskWhere.getId() == null) {
			tUserTaskService.insert(tUserTaskWhere);
    	} else {
			tUserTaskService.updateById(tUserTaskWhere);
    	}
    	return "redirect:/tusertask/list";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save/json")
    @ResponseBody
    public Object saveJson(@ModelAttribute TUserTask tUserTaskWhere) throws Exception {
    	Assert.notNull(tUserTaskWhere);
    	TUserTask tUserTask = (TUserTask) tUserTaskWhere;
    	if (tUserTaskWhere.getId() == null) {
			tUserTaskService.insert(tUserTaskWhere);
		} else {
			tUserTaskService.updateById(tUserTaskWhere);
		}
        return JsonViewFactory.create()
				.toJson();
    }

	
	@RequestMapping(method = RequestMethod.GET, value = "/toEdit/{id}")
    public String toEdit(@PathVariable Integer id, Model model) throws Exception {
		TUserTask tUserTask = tUserTaskService.getById(id);
    	model.addAttribute("tUserTask", tUserTask);
        return "tusertask/edit";
    }
    
	@RequestMapping(method = RequestMethod.GET, value = "/view/{id}")
    public String view(@PathVariable Integer id, Model model) throws Exception {
    	Assert.notNull(id);
    	TUserTask tUserTask = tUserTaskService.getById(id);
    	model.addAttribute("tUserTask", tUserTask);
        return "tusertask/view";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/view/json/{id}")
    @ResponseBody
    public Object viewJson(@PathVariable Integer id, Model model) throws Exception {
    	Assert.notNull(id);
    	TUserTask tUserTask = tUserTaskService.getById(id);
    	return JsonViewFactory.create()
				.setDateFormat("yyyy-MM-dd hh:mm:ss")
				.put("data", tUserTask)
				.toJson();
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String list(@ModelAttribute TUserTask tUserTaskWhere, Model model) {
		tUserTaskWhere.setOrder("ID desc");
		PageResult<TUserTask> tUserTasks = tUserTaskService.findByTUserTaskWhere(tUserTaskWhere);
		model.addAttribute("tUserTasks", tUserTasks);
		return "tusertask/list";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list/json")
	@ResponseBody
	public Object listJson(@ModelAttribute TUserTask tUserTaskWhere, Model model) {
		tUserTaskWhere.setOrder("ID desc");
		PageResult<TUserTask> tUserTasks = tUserTaskService.findByTUserTaskWhere(tUserTaskWhere);
		return JsonViewFactory.create()
				/*.setDateFormat("yyyy-MM-dd hh:mm:ss")
				.put("data", tUserTasks)*/
				.toJson();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/deleteById/json/{id}")
	@ResponseBody
	public Object deleteJsonById(@PathVariable("id")Integer id) {
		TUserTask tusertask = tUserTaskService.getById(id);
        Assert.notNull(tusertask);
		tUserTaskService.deleteById(id);
        return JsonViewFactory.create()
				.toJson();
        }

	
}