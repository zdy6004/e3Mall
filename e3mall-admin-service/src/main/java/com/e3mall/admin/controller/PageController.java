package com.e3mall.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex(){
		return "adminIndex";
	}
	
	@RequestMapping(value ="/{page}", method = RequestMethod.GET)
	public String showPage(@PathVariable String page){
		return page;
	}
	@RequestMapping(value ="/login", method = RequestMethod.GET)
	public String login(){
		return "login";
	}
}
