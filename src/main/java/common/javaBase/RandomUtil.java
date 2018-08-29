package common.javaBase;

import java.util.Random;

/**
* <p>Title:RandomUtil </p>
* <p>Description:随机数操作类 </p>
* @author xn042142 付品欣
* @date 2018年2月24日 下午3:27:29
 */
public class RandomUtil {
	
	public static Random random = new Random();

	/**
	 * getRandom:获取指定长多的由（有字母（大小写）和0到9的数字组成随机字符串
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param length 
	 * @return 
	 * @since JDK 1.8
	 * 2018年2月24日 下午3:28:00
	 */
	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}


}
