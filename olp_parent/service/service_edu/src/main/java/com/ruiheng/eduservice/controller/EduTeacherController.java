package com.ruiheng.eduservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruiheng.common_util.R;
import com.ruiheng.eduservice.entity.EduTeacher;
import com.ruiheng.eduservice.entity.vo.TeacherQuery;
import com.ruiheng.eduservice.service.EduTeacherService;
import com.ruiheng.service_base.exceptionhandler.AliosException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-22
 */
@Api(description="Teacher Managment")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
	
	//inject service
	@Autowired
	private EduTeacherService teacherService;
	
	//1 query teacher data(all)
	//rest style
	@ApiOperation(value = "sensei List")
	@GetMapping("findAll")
	public R finaAllTeacher() {
		List<EduTeacher> list = teacherService.list(null);
		
		/*
		 * try { int i = 10/0; } catch(Exception e) { throw new AliosException(20001,
		 * "Executed AliosException"); } 
		 */
		return R.ok().data("items", list);
	}
	
	@ApiOperation(value = "logic deleting sensei")
	@DeleteMapping("{id}")
	public R removeTeacher(@ApiParam(name = "id",value="sensei id",required=true) 
							@PathVariable String id) {
		
		boolean flag = teacherService.removeById(id);
		
		if(flag) {
			return R.ok();
		}else {
			return R.error();
		}
	}
	
	@GetMapping("pageSensei/{current}/{limit}")
	public R pageListQuery(@PathVariable long current, 
							@PathVariable long limit) {
		
		//create page object
		Page<EduTeacher> teacher = new Page<>(current, limit);
		teacherService.page(teacher, null);
		
		long total = teacher.getTotal();
		
		List<EduTeacher> records = teacher.getRecords();
		/*
		 * Map map = new HashMap(); map.put("total", total); map.put("rows", records);
		 */
		return R.ok().data("total", total).data("rows", records);
	}
	
	@PostMapping("pageTeacherCondition/{current}/{limit}")
	public R pageTeacherCondition(@PathVariable long current,
									@PathVariable long limit, 
									@RequestBody(required = false) TeacherQuery teacherQuery) {
		
		//create page object
		Page<EduTeacher> pageTeacher = new Page<>(current,limit);
		//create condition
		QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
		
		String name = teacherQuery.getName();
		Integer level = teacherQuery.getLevel();
		String begin = teacherQuery.getBegin();
		String end = teacherQuery.getEnd();
		
		if(!StringUtils.isEmpty(name)) {
			//condition
			wrapper.like("name", name);
			
		}
		
		if(null != level) {
            wrapper.eq("level",level);
        }
		
		if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
		
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
		
		teacherService.page(pageTeacher, wrapper);
		long total = pageTeacher.getTotal();
		
		List<EduTeacher> records = pageTeacher.getRecords();
		return R.ok().data("total", total).data("rows", records);
	}
	
	@PostMapping("addTeacher")
	public R addTeacher(@RequestBody EduTeacher eduTeacher) {
		
		boolean save = teacherService.save(eduTeacher);
		if(save = true) {
			return R.ok();
		}else {
			return R.error();
		}
	}
	
	//query
	@GetMapping("getTeacher/{id}")
	public R getTeacher(@PathVariable String id) {
		EduTeacher eduTeacher = teacherService.getById(id);
		return R.ok().data("name", eduTeacher);
	}
	
	@PostMapping("updateTeacher")
	public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
		
		boolean flag = teacherService.updateById(eduTeacher);
		if(flag) {
			return R.ok();
		}else {
			return R.error();
		}
	}
}

