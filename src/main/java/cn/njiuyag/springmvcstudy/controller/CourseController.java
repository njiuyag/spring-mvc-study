package cn.njiuyag.springmvcstudy.controller;

import cn.njiuyag.springmvcstudy.model.Course;
import cn.njiuyag.springmvcstudy.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


/**
 * @author hjx
 */
@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {
	
	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@RequestMapping(value="/overView", method=RequestMethod.GET)
	public String viewCourse(@RequestParam("courseId") Integer courseId, Model model) {
		log.debug("In viewCourse, courseId = {}", courseId);
		Course course = courseService.getCourse(courseId);
		model.addAttribute(course);
		return "course_overview";
	}

	@RequestMapping("/overView/{courseId}")
	public String viewCourse1(@PathVariable("courseId") Integer courseId, Map<String, Object> model) {
		log.debug("In viewCourse2, courseId = {}", courseId);
		Course course = courseService.getCourse(courseId);
		model.put("course",course);
		return "course_overview";
	}

	@RequestMapping("/overView2")
	public String viewCourse2(HttpServletRequest request) {
		Integer courseId = Integer.valueOf(request.getParameter("courseId"));		
		Course course = courseService.getCourse(courseId);
		request.setAttribute("course",course);
		return "course_overview";
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET, params = "add")
	public String createCourse(){
		return "course_admin/edit";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String  doSave(@ModelAttribute Course course){		
		
		log.debug("Info of Course:");
		log.debug(ReflectionToStringBuilder.toString(course));
		
		//在此进行业务操作，比如数据库持久化
		course.setCourseId(123);
		return "redirect:overView/"+course.getCourseId();
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String showUploadPage(@RequestParam(value= "multi", required = false) Boolean multi){	
		if(multi != null && multi){
			return "course_admin/multifile";	
		}
		return "course_admin/file";		
	}
	
	@RequestMapping(value="/doUpload", method=RequestMethod.POST)
	public String doUploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		
		if(!file.isEmpty()){
			log.debug("Process file: {}", file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis()+ file.getOriginalFilename()));
		}
		
		return "success";
	}
	
	@RequestMapping(value="/doUpload2", method=RequestMethod.POST)
	public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws IOException{
		
		Iterator<String> filesNames = multiRequest.getFileNames();
		while(filesNames.hasNext()){
			String fileName =filesNames.next();
			MultipartFile file =  multiRequest.getFile(fileName);
			if(!file.isEmpty()){
				log.debug("Process file: {}", file.getOriginalFilename());
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File("c:\\temp\\imooc\\", System.currentTimeMillis()+ file.getOriginalFilename()));
			}
			
		}
		
		return "success";
	}
	
	
	
	@RequestMapping(value="/{courseId}",method=RequestMethod.GET)
	public @ResponseBody Course getCourseInJson(@PathVariable Integer courseId){
		return  courseService.getCourse(courseId);
	}
	
	
	@RequestMapping(value="/jsontype/{courseId}",method=RequestMethod.GET)
	public ResponseEntity<Course> getCourseInJson2(@PathVariable Integer courseId){
		Course course =   courseService.getCourse(courseId);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}
	
	
	
}
