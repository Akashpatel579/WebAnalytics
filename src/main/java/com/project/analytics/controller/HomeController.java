package com.project.analytics.controller;

import com.project.analytics.model.ClickAnalytics;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET )

	public String hello(){

		System.out.println("Hello");
		return "index";
	}

	@PostMapping(value = {"/clickAnalytics"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity storeClickEvent(@RequestBody ClickAnalytics clickAnalytics){
		System.out.println("Data logged! " + clickAnalytics.getX() + "  " + clickAnalytics.getY());
		return ResponseEntity.ok().build();
	}

//	@PostMapping(value = {"/clickAnalytics"})
//	public @ResponseBody boolean storeClickEvent(@RequestParam String x, @RequestParam String y){
//		System.out.println("Data logged! " + x + y);
//		return true;
//	}

}