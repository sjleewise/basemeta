package kr.wise.commons;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class UtilEncryption {
	
//	protected static final Log logger = LogFactory.getLog(UtilEncryption.class);

	// 단방향 암호화
    public String encryptSha256(String planText, byte[] salt) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(salt);
            byte byteData[] = md.digest(planText.getBytes("UTF-8"));

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }catch(Exception e){ //시큐어코딩 조치 - Exception to String
//	        	logger.debug(ExceptionUtils.getStackTrace(e)); 
            throw new RuntimeException();
        }
    }
    
    public String encSHA256(String str) {
    	String SHA = ""; 
    	try{
    		MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
    		sh.update(str.getBytes()); 
    		byte byteData[] = sh.digest();
    		StringBuffer sb = new StringBuffer(); 
    		for(int i = 0 ; i < byteData.length ; i++){
    			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
    		}
    		SHA = sb.toString();
    		
    	}catch(NoSuchAlgorithmException e){//시큐어코딩 조치 - Exception to String
//	        	logger.debug(ExceptionUtils.getStackTrace(e)); 
    		SHA = null; 
    	}
    	return SHA;
    }
    

    /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     * @throws UnsupportedEncodingException 키값의 길이가 16이하일 경우 발생
     */
//    public static void temp(String aes_key) throws Exception {
//    	KEY_VALUE = aes_key;
//    	UtilEncryption.iv = KEY_VALUE.substring(0, 16);
//        byte[] keyBytes = new byte[16];
//        byte[] b = KEY_VALUE.getBytes("UTF-8");
//        int len = b.length;
//        if(len > keyBytes.length){
//            len = keyBytes.length;
//        }
//        System.arraycopy(b, 0, keyBytes, 0, len);
//        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//
//        UtilEncryption.keySpec = keySpec;
//    }

    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws Exception 
     */
//    public String encrypt(String str) throws Exception{
//        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
//        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
//        String enStr = new String(base64Encode(encrypted));
//        return enStr;
//    }

    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     * @param str 복호화할 문자열
     * @return
     * @throws Exception 
     */
    public static String decrypt(String str) throws Exception {
        String iv;
        Key keySpec;
        
    	String key_value = "dnltpdkdlxpr1012";
    	iv = key_value.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key_value.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec1 = new SecretKeySpec(keyBytes, "AES");

        keySpec = keySpec1;
        
    	if(str == null) {
    		return null;
    	}
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        System.out.println("keySpec="+keySpec);
        System.out.println("iv.getBytes()="+iv.getBytes());
        System.out.println("Cipher.DECRYPT_MODE="+Cipher.DECRYPT_MODE);
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = base64Decode(str);
        return new String(c.doFinal(byteStr), "UTF-8");
    }
    public static void main(String args[]) {
    	try {
			System.out.println(UtilEncryption.decrypt("decryptTest : "+"05hj84GM8hXP+2pfhcEBNg=="));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private String base64Encode(byte[] data) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(data);
        return encoded;
    }

    private static byte[] base64Decode(String encryptedData) throws Exception {

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoded = decoder.decodeBuffer(encryptedData);
        return decoded;
    }
    
    public static String getRandomStr(int len)
	{
	  StringBuffer buffer = new StringBuffer();
	  Random random = new Random();
	 
	  String chars[] = 
			  "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,0,1,2,3,4,5,6,7,8,9".split(",");
	 
	  for (int i=0 ; i<len ; i++)
	  {
	    buffer.append(chars[random.nextInt(chars.length)]);
	  }
	  return buffer.toString();
	}
}
