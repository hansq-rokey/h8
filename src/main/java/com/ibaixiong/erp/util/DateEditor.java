package com.ibaixiong.erp.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text){
		if(StringUtils.isBlank(text)) return;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		try {
			date = format.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		setValue(date);
	}
}
