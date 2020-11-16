package cn.mulanbay.web.bind;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 未使用
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class MyWebBinding implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder webDataBinder) {
		// 注册自定义的属性编辑器
		// 1、日期
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumIntegerDigits(2);
		CustomNumberEditor numberEditor = new CustomNumberEditor(Double.class,
				nf, true);
		// 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
		webDataBinder.registerCustomEditor(Date.class, dateEditor);
		webDataBinder.registerCustomEditor(Double.class, numberEditor);
	}

}
