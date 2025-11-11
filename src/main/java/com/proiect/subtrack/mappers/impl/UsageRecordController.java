package com.proiect.subtrack.mappers.impl;

import com.proiect.subtrack.services.UsageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usage")
@RequiredArgsConstructor
public class UsageRecordController {


    final private UsageRecordService usageRecordService;
    final private UsageRecordMapperImpl usageRecordMapper;


//    GetMapping("/{id}")
//        ResponseEntity<List<>>
}
