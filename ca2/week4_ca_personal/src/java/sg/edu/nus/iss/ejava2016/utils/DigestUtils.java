/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.ejava2016.view.login.UsersView;

/**
 *
 * @author sanja
 */
public class DigestUtils {
    public static String sha256hex(String input) {
    byte[] output = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            output = md.digest(input.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UsersView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < output.length; i++) {
            String hex = Integer.toHexString(0xff & output[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
}
}
