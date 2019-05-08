package com.yunqi.jhf.web.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 
 * @author Seek
 *
 */
@CrossOrigin
@RestController

public class TMapAction {

	@RequestMapping(value = "/detailMap")
	public ModelAndView detailMap(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/detailmap");
		return mav;
	}

}
