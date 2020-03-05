package com.tvj.internaltool.controller;

import com.tvj.internaltool.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class DummyController {

    // test role-permission
    @GetMapping(value = "/{id}/list/{listId}")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("Hello: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/list2/{listId}")
    public ResponseEntity<?> test2() {
        return new ResponseEntity<>("Hello2: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/list/{listId}/item")
    public ResponseEntity<?> test3() {
        return new ResponseEntity<>("Hello3: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/list")
    public ResponseEntity<?> test4() {
        return new ResponseEntity<>("Hello4: " + UserUtils.getCurrentUsername(), HttpStatus.OK);
    }

}
