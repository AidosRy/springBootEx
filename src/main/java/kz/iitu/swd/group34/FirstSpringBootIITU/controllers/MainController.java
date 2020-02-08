
package kz.iitu.swd.group34.FirstSpringBootIITU.controllers;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Courses;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Groups;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Students;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.CoursesRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.GroupsRepository;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @GetMapping(value = "/")
    public String index(ModelMap model){
        return "index";
    }

    @GetMapping(value="/courses")
    public String courses(ModelMap model){
        List<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping(value="/groups")
    public String groups(ModelMap model){
        List<Groups> groups = groupsRepository.findAll();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @GetMapping(value="/students")
    public String students(ModelMap model){
        List<Students> students = studentsRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @PostMapping(value="/addgroup")
    public String addgroup(@RequestParam String name,@RequestParam String shortName){
        Groups group = new Groups(null, name, shortName);
        groupsRepository.save(group);
        return "redirect:/groups";
    }

    @PostMapping(value="/addcourse")
    public String addcourse(@RequestParam String name,@RequestParam int credits){
        Courses group = new Courses(null, name, credits);
        coursesRepository.save(group);
        return "redirect:/courses";
    }

    @PostMapping(value = "/addstudent")
    public String addstudent(@RequestParam String name, @RequestParam String surname, @RequestParam int year){
        Students students = new Students(null, name, surname, year, new HashSet<Courses>(), new HashSet<Groups>());
        studentsRepository.save(students);
        return "redirect:/students";
    }

    @GetMapping(value="/detailsstud/{id}")
    public String detailsstud(ModelMap map,  @PathVariable Long id) {
        Students stud = studentsRepository.findById(id).orElse(null);
        map.addAttribute("stud", stud);
        map.addAttribute("courses", stud.getCourse());
        map.addAttribute("groups",stud.getGroup());
        map.addAttribute("allcourses", coursesRepository.findAll());
        map.addAttribute("allgroups",groupsRepository.findAll());
        return "detailsstud";
    }

    @GetMapping(value="/detailscourses/{id}")
    public String detailscourse(ModelMap map,  @PathVariable(name = "id") Long id) {
        Optional<Courses> item = coursesRepository.findById(id);
        map.addAttribute("cat", item.orElse(new Courses(0L, "No Name",0)));
        return "detailscourses";
    }

    @GetMapping(value="/detailsgroup/{id}")
    public String detailsgroup(ModelMap map,  @PathVariable(name = "id") Long id) {
        Optional<Groups> item = groupsRepository.findById(id);
        map.addAttribute("cat", item.orElse(new Groups(0L, "No Name","a")));
        return "detailsgroup";
    }

    @PostMapping(value="/updatestud")
    public String updatestud(@RequestParam String name, @RequestParam String surname, @RequestParam int year, @RequestParam Long id){
        Students stud = studentsRepository.findById(id).orElse(null);
        stud.setName(name);
        stud.setSurname(surname);
        stud.setYearOfAddmission(year);
        studentsRepository.save(stud);
        return "redirect:/students";
    }

    @PostMapping(value="/updatecourse")
    public String updatecourse(@RequestParam String name, @RequestParam int credits, @RequestParam Long id){
        Courses stud = coursesRepository.findById(id).orElse(null);
        stud.setName(name);
        stud.setCredits(credits);
        coursesRepository.save(stud);
        return "redirect:/courses";
    }

    @PostMapping(value="/updategroup")
    public String updategroup(@RequestParam String name, @RequestParam String shortName, @RequestParam Long id){
        Groups stud = groupsRepository.findById(id).orElse(null);
        stud.setName(name);
        stud.setShortName(shortName);
        groupsRepository.save(stud);
        return "redirect:/groups";
    }

    @PostMapping(value="/deletegroup")
    public String deletegroup(@RequestParam Long studId, @RequestParam Long groupId, ModelMap map){
        Students stud = studentsRepository.findById(studId).orElse(null);
        Groups group = groupsRepository.findById(groupId).orElse(null);
        stud.getGroup().remove(group);
        studentsRepository.save(stud);
        map.addAttribute("stud", stud);
        map.addAttribute("courses", stud.getCourse());
        map.addAttribute("groups",stud.getGroup());
        map.addAttribute("allcourses", coursesRepository.findAll());
        map.addAttribute("allgroups",groupsRepository.findAll());
        return "detailsstud";
    }

    @PostMapping(value="/deletestud")
    public String deletestud(@RequestParam Long id){
        Students stud = studentsRepository.findById(id).orElse(null);
        studentsRepository.delete(stud);
        return "redirect:/students";
    }

    @PostMapping(value="/deletecourse")
    public String deletecourse(@RequestParam Long courseId, @RequestParam Long studId,ModelMap map){
        Students stud = studentsRepository.findById(studId).orElse(null);
        Courses course = coursesRepository.findById(courseId).orElse(null);
        stud.getCourse().remove(course);
        studentsRepository.save(stud);
        map.addAttribute("stud", stud);
        map.addAttribute("courses", stud.getCourse());
        map.addAttribute("groups",stud.getGroup());
        map.addAttribute("allcourses", coursesRepository.findAll());
        map.addAttribute("allgroups",groupsRepository.findAll());
        return "detailsstud";
    }

    @PostMapping(value="/assigncourse")
    public String assigncourse(@RequestParam Long courseId, @RequestParam Long studId,ModelMap map){
        Students stud = studentsRepository.findById(studId).orElse(null);
        Courses course = coursesRepository.findById(courseId).orElse(null);
        stud.getCourse().add(course);
        studentsRepository.save(stud);
        map.addAttribute("stud", stud);
        map.addAttribute("courses", stud.getCourse());
        map.addAttribute("groups",stud.getGroup());
        map.addAttribute("allcourses", coursesRepository.findAll());
        map.addAttribute("allgroups",groupsRepository.findAll());
        return "detailsstud";
    }

    @PostMapping(value="/assigngroup")
    public String assigngroup(@RequestParam Long groupId, @RequestParam Long studId, ModelMap map){
        Students stud = studentsRepository.findById(studId).orElse(null);
        Groups group = groupsRepository.findById(groupId).orElse(null);
        stud.getGroup().add(group);
        studentsRepository.save(stud);
        map.addAttribute("stud", stud);
        map.addAttribute("courses", stud.getCourse());
        map.addAttribute("groups",stud.getGroup());
        map.addAttribute("allcourses", coursesRepository.findAll());
        map.addAttribute("allgroups",groupsRepository.findAll());
        return "detailsstud";
    }
}