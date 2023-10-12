package com.spoons.sehaehae.common.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String errorView(Exception e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "common/error";
	}

	@ExceptionHandler(NullPointerException.class)
	public String handleNullPointerException(NullPointerException e, Model model) {
		return "common/error";
	}

}
