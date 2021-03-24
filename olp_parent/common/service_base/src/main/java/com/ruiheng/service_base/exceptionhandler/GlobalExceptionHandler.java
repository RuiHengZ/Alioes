package com.ruiheng.service_base.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruiheng.common_util.R;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	@ResponseBody //returning the data
	public R error(Exception e) {
		e.printStackTrace();
		return R.error().message("executed global error... ");
	}
	
	@ExceptionHandler(ArithmeticException.class)
	@ResponseBody
	public R Specalerror(ArithmeticException e) {
		e.printStackTrace();
		return R.error().message("executed Arithmetic error... ");
	}
	
	@ExceptionHandler(AliosException.class)
	@ResponseBody
	public R SelIntroerror(AliosException e) {
		
		log.error(e.getMessage());
		e.printStackTrace();
		
		return R.error().code(e.getCode()).message(e.getMsg());
		
	}
}
