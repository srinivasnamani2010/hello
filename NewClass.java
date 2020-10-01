/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samples;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author srinivas.n
 */
public class NewClass {
    public static MongoClient m = null;
    //yyyy-MM-ddTHH:mm:SS.SSSZ+5:30
	public static final String ENCODING="DES";
    public static void main1(String[] args) throws UnknownHostException {
        boolean bAdminAuthEnabled = true;
        String[] servers = args[0].split(",");
        List<ServerAddress> serversList = new ArrayList<>();
        for (String server : servers) {
            String[] serv = server.split(":");
            String serverIp = serv[0];
            String port = "27017";
            if (serv.length > 1) {
                port = serv[1];
            }
            serversList.add(new ServerAddress(serverIp, NumberUtils.toInt(port)));
        }
        m = new MongoClient(serversList);
        if (bAdminAuthEnabled) {
            String mAdminDBName = "admin";
            String uname = "AppUser";
            String password = "csKz4piRsRg1";
            System.out.println("111Mongo dbname: " + mAdminDBName + " uname: " + uname + " password: " + password);
            if (uname != null && password != null) {
                if (!m.getDB(mAdminDBName).authenticate(uname, password.toCharArray())) {
//                        return false;
                    System.out.println("[StatAggregator MongoStatReaderImpl] Mongo init failed");
                }
            }
        }

    }

    public static final String UTC_DATE_FORMAT_WITH_ZONE_XXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    static SimpleDateFormat sf = new SimpleDateFormat(UTC_DATE_FORMAT_WITH_ZONE_XXX);

    public static void main(String[] args) {
        System.out.println(sf.format(new Date()));
    }

    /* ParseWords : allows the parsing of words based upon the count, return the number of words.
     * parseLetterOrDigit : parse the message for only numbers and digits ans return the message.
     * parseLetter : parse the message for only charecters and return the message
     */
    String parseWords(String strMsg, int noOfWords) {
        StringBuffer buf = new StringBuffer();
        char[] c = strMsg.toCharArray();
        boolean bStarted = false;
        int iLen = strMsg.length();
        for (int i = 0; i < iLen; i++) {
            if (Character.isWhitespace(c[i]) && noOfWords > 0) {
                noOfWords--;
                if (noOfWords != 0) {
                    buf.append(c[i]);
                }
                if (bStarted == true && noOfWords == 0) {
                    break;
                }
            } else {
                bStarted = true;
                buf.append(c[i]);
            }
        }
        return buf.toString();
    }

    String parseLetterOrDigit(String strMsg) {
        StringBuffer buf = new StringBuffer();
        char[] c = strMsg.toCharArray();
        boolean bStarted = false;
        int iLen = strMsg.length();
        for (int i = 0; i < iLen; i++) {
            if (Character.isLetterOrDigit(c[i])) {
                bStarted = true;
                buf.append(c[i]);
            } else {
                if (bStarted == true) {
                    break;
                }
            }
        }
        return buf.toString();
    }

    String parseLetter(String strMsg) {
        StringBuffer buf = new StringBuffer();
        char[] c = strMsg.toCharArray();
        boolean bStarted = false;
        int iLen = strMsg.length();
        for (int i = 0; i < iLen; i++) {
            if (Character.isLetter(c[i])) {
                bStarted = true;
                buf.append(c[i]);
            } else {
                if (bStarted == true) {
                    break;
                }
            }
        }
        return buf.toString();
    }
}
