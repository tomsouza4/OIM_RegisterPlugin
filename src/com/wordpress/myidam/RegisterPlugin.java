package com.wordpress.myidam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.pluginframework.PluginException;
import oracle.iam.platformservice.api.PlatformService;
import oracle.iam.platformservice.api.PlatformUtilsService;
import oracle.iam.platformservice.exception.InvalidCacheCategoryException;
import oracle.iam.platformservice.exception.PlatformServiceAccessDeniedException;
 
public class RegisterPlugin
{
 
    public static final String OIM_HOSTNAME = "identity.oracleads.com";
    public static final String OIM_PORT = "14000";
    public static final String OIM_PROVIDER_URL = "t3://"+ OIM_HOSTNAME + ":" + OIM_PORT;
    public static final String OIM_USERNAME = "xelsysadm";
    public static final String OIM_PASSWORD = "Oracle123";
    //Assuming you're running from your computer instead of OIM Server, so add here you're computer path to oimclient.jar folder
    public static final String OIM_CLIENT_HOME = "/Users/tomsouza/Dropbox/ORACLE/OIM/Oracle_Identity_Governance_11gR2PS2_TrainingVM/Binaries/zip_files/";
    //Assuming that you have authwl.conf in the same folder as oimclient.jar
    public static final String AUTHWL_PATH = OIM_CLIENT_HOME + "/authwl.conf";
    
    /*public static final String OIM_CLIENT_HOME = "/app/Middleware/Oracle_IDM1/server/client";
    public static final String AUTHWL_PATH = OIM_CLIENT_HOME + "/conf/authwl.conf";*/
    
    //Path to the file in your local folder structure
    //public static final String PLUGIN_ZIP_PATH = "/Users/tomsouza/Downloads/CustomSchedTask1.zip";
    public static final String PLUGIN_ZIP_PATH = "/Users/tomsouza/Downloads/SampleScheduler.zip"; 
    
    public static void main (String args[])
    {
        OIMClient oimClient = null;
        FileInputStream fis = null;
         
        try
        { 
             //Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls"); 
 
            // Create an instance of OIMClient with OIM environment information 
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, "weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
            
            // Zip file conversion to byte
            String fileName = PLUGIN_ZIP_PATH;
            File zipFile = new File(fileName);
            fis = new FileInputStream(zipFile);
            int size = (int) zipFile.length();
            byte[] b = new byte[size];
            int bytesRead = fis.read(b, 0, size);
            
            while (bytesRead < size) 
            {
                bytesRead += fis.read(b, bytesRead, size - bytesRead);
                System.out.println("bytesRead: "+ bytesRead);
                System.out.println("fis.read: "+ fis.read(b, bytesRead, size - bytesRead));
            }
            
            // Register Plugin to OIM
            PlatformService service = oimClient.getService(PlatformService.class);
            service.registerPlugin(b);
 
            // Purge Cache 
            PlatformUtilsService platUtilOps = oimClient.getService(PlatformUtilsService.class);
            try {
				platUtilOps.purgeCache("ALL");
			} catch (InvalidCacheCategoryException e) {
				e.printStackTrace();
			}
        }
        
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(RegisterPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        catch (PlatformServiceAccessDeniedException ex) 
        {
            Logger.getLogger(RegisterPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        catch (PluginException ex)
        {
            Logger.getLogger(RegisterPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        catch (IOException ex) 
        {
            Logger.getLogger(RegisterPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        catch (LoginException ex) 
        {
            Logger.getLogger(RegisterPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        finally
        {
            // Logout user from OIMClient
            if(oimClient != null)   
            { 
                oimClient.logout();
            }
            try
            {
                fis.close();                  
            } 
            
            catch (IOException ex) 
            {
                Logger.getLogger(RegisterPlugin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
