package cn.lxw.frame.service;

import cn.lxw.frame.entity.Student;
import cn.lxw.frame.entity.Course;
import cn.lxw.frame.coursedb.CourseMapper;
import cn.lxw.frame.studentdb.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TransactionService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private StudentMapper studentMapper;

    @Transactional(readOnly = false)
    public void testTransaction() {

        courseMapper.insert(new Course(){
            {
                setName("Chinese");
            }
        });

        studentMapper.insert(new Student(){
            {
                setName("David");
                setAge(16);
            }
        });

        throw new RuntimeException("User-defined exception occurred");
    }

}
