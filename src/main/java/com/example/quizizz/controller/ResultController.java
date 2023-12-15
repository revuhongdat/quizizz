package com.example.quizizz.controller;

import com.example.quizizz.model.Result;
import com.example.quizizz.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/results")
public class ResultController {
    final
    ResultService resultService;
    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Result> results = (List<Result>) resultService.findAll();
        if (results.isEmpty()) {
            return new ResponseEntity<>("Không có", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
