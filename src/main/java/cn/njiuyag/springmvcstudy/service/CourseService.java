package cn.njiuyag.springmvcstudy.service;

import cn.njiuyag.springmvcstudy.model.Course;
import org.springframework.stereotype.Service;

/**
 * @author yangjingliu
 * @date 2020/5/1
 */
@Service
public interface CourseService {

    /**
     * 通过课程id获取课程
     * @param courseId
     * @return
     */
    Course getCourse(Integer courseId);
}

