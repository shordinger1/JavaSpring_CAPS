package com.team4.caps.service;

import com.team4.caps.model.CourseLecturer;
import com.team4.caps.model.CourseSchedule;
import com.team4.caps.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ScheduleArrangement {

    private final CourseLecturerService courseLecturerService;
    private final ClassroomService classroomService;
    private final CourseScheduleService courseScheduleService;
    private final ScheduleService scheduleService;
    @Autowired
    public ScheduleArrangement(CourseLecturerService courseLecturerService, ClassroomService classroomService,CourseScheduleService courseScheduleService,ScheduleService scheduleService) {
        this.courseLecturerService = courseLecturerService;
        this.classroomService = classroomService;
        this.courseScheduleService=courseScheduleService;
        this.scheduleService=scheduleService;
    }


    public Boolean generate()
    {
        var courseLecturers= courseLecturerService.getAllCourseLecturers();
        var classrooms=classroomService.getAllClassRoom();
        int day=5;
        Schedule[][] schedules=new Schedule[classrooms.size()][day];
        CourseLecturer[][] lecturers=new CourseLecturer[classrooms.size()][day];
        Collections.shuffle(courseLecturers);
        if(courseLecturers.size()>5*classrooms.size()){
            return null;
        }
        int id=1;
        for (var courseLecturer:courseLecturers) {
            var dayTimeSchedule=generateTime();
            boolean flag=false;
            for(int i=0;i< day;i++)
            {
                for(int j=0;j< classrooms.size();j++)
                {
                    if(schedules[i][j].isAvailable())
                    {
                        schedules[i][j]=new Schedule(id,i, dayTimeSchedule.get(0), dayTimeSchedule.get(1));
                        id++;
                        lecturers[i][j]=courseLecturer;
                        flag=true;
                        break;
                    }
                    if(lecturers[i][j].getLecturer().equals(courseLecturer.getLecturer()))break;
                }
            }
            if(!flag)return false;
        }
        for(int i=0;i< day;i++)
        {
            for(int j=0;j< classrooms.size();j++) {
                scheduleService.createSchedule(schedules[i][j]);
                lecturers[i][j].setClassroom(classrooms.get(j));
                courseLecturerService.updateCourseLecturerById(lecturers[i][j].getId(),lecturers[i][j]);
                courseScheduleService.createCourseSchedule(new CourseSchedule(lecturers[i][j],schedules[i][j]));
            }
        }
        return true;
    }


        public List<Integer> generateTime() {
            Random random = new Random();
            List<Integer> result=new ArrayList<>();
            // 生成第一段时间
            int startTime1 = 8 * 60;  // 早上8点的分钟数
            int endTime1 = 10 * 60;  // 上午10点的分钟数
            int duration1 = (random.nextInt((endTime1 - startTime1) / 15) + 1) * 15;  // 生成15分钟的整倍数
            int endTimeMinutes1 = startTime1 + duration1;
            result.add(endTimeMinutes1);

            // 生成第二段时间
            int startTime2 = 15 * 60;  // 下午3点的分钟数
            int endTime2 = 17 * 60;  // 下午5点的分钟数
            int duration2 = (random.nextInt((endTime2 - startTime2) / 15) + 1) * 15;  // 生成15分钟的整倍数
            int endTimeMinutes2 = startTime2 + duration2;
            result.add(endTimeMinutes2);
            return result;


        }

        // 辅助方法：将分钟数转换为时:分格式
        private static String minutesToTimeString(int minutes) {
            int hours = minutes / 60;
            int mins = minutes % 60;
            return String.format("%02d:%02d", hours, mins);
        }


}
