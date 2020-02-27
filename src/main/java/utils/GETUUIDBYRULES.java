package utils;

import java.util.Random;
import java.util.UUID;

/**
 * 自动生成32位编码规则如下
 *
 * @author 2016-06-03
 * 两种方法都适用
 */
public class GETUUIDBYRULES {
    /**
     * 生成32位编码规则
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     * 自定义规则生成32位编码
     *
     * @param rules
     * @return
     */
    public static String getUUIDByRules(String rules) {
        int rpoint = 0;
        String radStr = "";
        StringBuffer generateRandStr = new StringBuffer();
        Random rand = new Random();
        int length = 32;
        for (int i = 0; i < length; i++) {
            if (rules != null) {
                rpoint = rules.length();
                int randNum = rand.nextInt(rpoint);
                generateRandStr.append(radStr.substring(randNum, randNum + 1));
            }
        }
        return generateRandStr + "";
    }

}
