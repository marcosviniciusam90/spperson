package com.mvam.spperson.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source")
public class SourceResource {

    @GetMapping
    public String findSource() {
        return "https://github.com/marcosviniciusam90/spperson";
    }
}
