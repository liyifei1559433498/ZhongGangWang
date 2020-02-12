package com.zgw.base.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查工具类
 *
 */
public class CheckUtil {
	/** chinese code regex*/
	private static final String chinese_code_regex = "(^[\u4E00-\u9FA5]{2,16}$)|([\u4E00-\u9FA5]+[\u00b7][\u4E00-\u9FA5]+$)";

    private Context context;
	/**
     * check name
     * @param name
     * @return
     */
	public static boolean isValidName(String name) {
		if (name == null || "".equals(name)) return false;
        if (name.length() >= 2 && name.length() <= 16) {
     	   if (isValidInput(chinese_code_regex,name)) {
     		   return true;
     	   }
        } 
 	   return false;
    } 
	/**
	 * check input
	 * @param chinese_code_regex
	 * @param name
	 * @return
	 */
    public static boolean isValidInput(String chinese_code_regex,String name) {
    	try {
	 	    Pattern chinese_code = Pattern.compile(chinese_code_regex);
	        Matcher proto = chinese_code.matcher(name);
	     	return proto.find();
    	} catch (Exception ex) {
    		return false;
    	}
    }
	/**
     * check card all place of China
     * @param card
     * @return
     */
	public static  boolean isValidCard(String card){
        if (card == null || "".equals(card)) return false;
        if (card.length() == 10) {
        	return true;
        } else if (card.length() == 18) {
        	return isValidChinaCard(card);
        }
		return false;

	}
	/**
	 * check card mainland of China
	 * @param card
	 * @return
	 */
	public static boolean isValidChinaCard(String card) {
		card = card.toUpperCase();
		int year = Integer.parseInt(card.substring(6,10));
		int month = Integer.parseInt(card.substring(10,12));
		int day = Integer.parseInt(card.substring(12,14));

		int[] weight = {2,4,8,5,10,9,7,3,6,1,2,4,8,5,10,9,7};
		char[] checkCard = {'1','0','X','9','8','7','6','5','4','3','2'};

		int sum = 0;
		int[] tmpCard = new int[18];

		if (year < 1900 || month < 1 || month > 12 || day < 1 || day > 31 
				|| ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
				||(month == 2 && ((year % 4 > 0 && day > 28) || day > 29))){
			return false;
		}

		for (int i = 1; i < 18; i++) { 
			int j = 17 - i;
			tmpCard[i-1] = Integer.parseInt(card.substring(j,j+1));
		}

		for(int i = 0; i < 17; i++){ 
			sum += weight[i] * tmpCard[i];
		}
		sum = sum % 11; 
		if(card.charAt(17) != checkCard[sum]){
		    return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否是空值
	 * 
	 */
	public static String isNull(String str) {
		if (str == null || "".equals(str)) {
			return "0";
		} else {
			return str;
		}

	}
	
	/**
	 * 验证手机号
	 * @return
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		String regExp = "^1[3-9]{1}[0-9]{1}[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
	public static boolean isValidString(String Str, int mixLength, int maxLength) {
		if (TextUtils.isEmpty(Str)) {
			return false;
		} else if (Str.length() < mixLength || Str.length() > maxLength) { 
			return false;
		} else { //是否是数字或字母
			return isValidCombination(Str);
		}
    } 
	/**
	 * 判断是否是数字与字母组合
	 * @param str
	 * @return
	 */
	public static boolean isValidCombination(String str) {
		String num = "[^0-9]";
		String chart = "[^a-zA-Z]";
		Pattern p1 = Pattern.compile(num);    
		Matcher m1 = p1.matcher(str); 
		Pattern p2 = Pattern.compile(chart);    
		Matcher m2 = p2.matcher(str);
		return m1.find() || m2.find();
	}

	/**
	 * 是否是合法长度的
	 * 数字和字母的组合
	 * */
	public static boolean isMixValidString(String Str, int mixLength, int maxLength) {
		if (TextUtils.isEmpty(Str)) {
			return false;
		} else if (Str.length() < mixLength || Str.length() > maxLength) {
			return false;
		} else { //是否是数字和字母组合
			return isMixValidCombination(Str);
		}
	}

	/**
	 * 判断是否是数字和字母组合
	 * @param str
	 * @return
	 */
	public static boolean isMixValidCombination(String str) {
		String num = "[^0-9]";
		String chart = "[^a-zA-Z]";
		Pattern p1 = Pattern.compile(num);
		Matcher m1 = p1.matcher(str);
		Pattern p2 = Pattern.compile(chart);
		Matcher m2 = p2.matcher(str);
		return m1.find() && m2.find();
	}
	
	/**判断是否含有空格和数字*/
	public static boolean isNameRightful(String str){
		String chart = "^([\\s\\S]*)(\\s|[0-9])([\\s\\S]*)$";
		Pattern pattern = Pattern.compile(chart);    
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	/**判断真实姓名是否合法*/
	public static boolean isNameOk(String str){
		String chart = "^[\u4e00-\u9fa5]{1}([\u4e00-\u9fa5]|[·]|[·])*[\u4e00-\u9fa5]{1}([\u4e00-\u9fa5]|[·]|[·])*$";
		Pattern pattern = Pattern.compile(chart);    
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	
	/** 判断是否是中文 */
	public static boolean isChinese(String str) {
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				return true;
			}
		}
		return false;
	}
	
	/** 判断含中文个数 */
	public static int includeChineseNum(String str) {
		int num = 0;
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				num++;
			}
		}
		return num;
	}
	
	/**
	 * 判断可能含中文是否是合法的字符串
	 */
	public static boolean isValidChineseString(String Str, int mixLength, int maxLength) {
		int totalNum = Str.length();
		int chineseNum = includeChineseNum(Str);
		int LegalNum = totalNum-chineseNum +  chineseNum*2;
		if (TextUtils.isEmpty(Str)) {
			return false;
		} else if (LegalNum < mixLength || LegalNum > maxLength) { 
			return false;
		} else {
			return true;
		}
    } 
	
	/*** 身份证校验 */
	public static String IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为18位";
            return errorInfo;
        }
        // =======================(end)========================
 
        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "18位号码除最后一位外，都应为数字";
            return errorInfo;
        }
        // =======================(end)========================
 
        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效";
            return errorInfo;
        }
//        GregorianCalendar gc = new GregorianCalendar();
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            if ((Integer.parseInt(strYear) <= 1930 || Integer.parseInt(strYear) >= 2003)/*(gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150*/
//                    /*|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0*/) {
//                errorInfo = "身份证生日不在有效范围";
//                return errorInfo;
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================
 
        // ================ 地区码时候有效 ================
        Hashtable<?, ?> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误";
            return errorInfo;
        }
        // ==============================================
 
        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
 
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "YES";
        }
        // =====================(end)=====================
        return "YES";
    }
	
    /**
     * 身份证校验-设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }
    
    /**
     * 身份证校验-判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * 身份证校验-判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
