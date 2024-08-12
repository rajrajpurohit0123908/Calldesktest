package Savvion.Savvion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/savvion/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/instanceid")
    public List<Map<String, Object>> processEmployees(@RequestBody List<String> ids) {
       return employeeService.findEmployeesByIds(ids);
    }
}

