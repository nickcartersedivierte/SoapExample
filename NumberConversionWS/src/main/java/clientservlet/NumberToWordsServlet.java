/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservlet;

import com.dataaccess.webservicesserver.NumberConversion;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Ana Ospitaletche 2017253
 */
@WebServlet(name = "NumberToWordsServlet", urlPatterns = {"/NumberToWordsServlet"})
public class NumberToWordsServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/www.dataaccess.com/webservicesserver/NumberConversion.wso.wsdl")
   
    private NumberConversion service;
    protected BigInteger ubiNum;  
    private String result;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

        // Now you can access an https URL without having the certificate in the truststore
        try {
            URL url = new URL("https://hostname/index.html");
        } catch (MalformedURLException e) {
        }
     
        response.setContentType("text/html;charset=UTF-8");

        // Call Web Service Operation
        try  (PrintWriter out = response.getWriter()){ 
             
            com.dataaccess.webservicesserver.NumberConversionSoapType port = service.getNumberConversionSoap();
            
            // TODO initialize WS operation arguments here
            String num1 = request.getParameter("n1");
                     
            java.math.BigInteger ubiNum = new java.math.BigInteger(num1);
             
            // TODO process result here
            result = port.numberToWords(ubiNum);

            
        out.println("<html>");
        out.println("<head>");
        
        //Display the report's name as a title in the browser's titlebar:
        out.println("<title>Number Convertion Report</title>");
        out.println("</head>");
        out.println("<body>");
        
        //Display the report's name as a header within the body of the report:
        out.println("<h2><font color='blue'>Number to Words Result</font></h2>");
        
        //Display the number entered by user
        out.println("<hr><b>Your number was:</b> \"" + num1 + "\"" + "<p>");
        out.println("<hr><b>The Word is:</b> \""  + result + "\"" + "<p>");
          
        
        out.println("</body>");
        out.println("</html>");
        
        
        
        } catch (Exception ex) {
                    out.println(ex);        }


        
        
        
        
    }     
        
             
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
   

}
