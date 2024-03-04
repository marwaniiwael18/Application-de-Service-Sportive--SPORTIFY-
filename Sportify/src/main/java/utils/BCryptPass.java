package utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPass {
    public static String hashPass(String pwd){
        return BCrypt.hashpw(pwd,BCrypt.gensalt(10));
    }
    public static boolean checkPass(String pwd,String hashPwd){
        return BCrypt.checkpw(pwd, hashPwd);
    }
}
