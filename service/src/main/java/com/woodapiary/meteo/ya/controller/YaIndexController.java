/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class YaIndexController {

    @GetMapping
    public String showIndexPage() {
        return "index";
    }

}