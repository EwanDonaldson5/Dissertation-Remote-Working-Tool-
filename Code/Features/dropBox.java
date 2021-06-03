package Features;

import DropBoxFunctionality.DropBoxGUI;
import com.dropbox.core.DbxDownloader;
import java.io.IOException;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.common.PathRoot;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.DbxUserUsersRequests;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JPanel;


public class dropBox{
    private static final String ACCESS_TOKEN = "PF4kCu84tTgAAAAAAAAAAcoT9VbxYs5Yx6d1kedJBaEUPfH8q6aRsw_g1DA4YPuO";
    static DbxClientV2 client;
    public DropBoxGUI dropBoxUI = null;
    
    List<JPanel> documentsList = new ArrayList<JPanel>();//create a list to hold the file and folder jpanels in
    
    //constructor
    public dropBox() throws IOException{
        ListFolderResult list = setupDropBoxConnection();
                
        //load the files from the meta list
        List<Metadata> metaList = list.getEntries();//list holds the files and folders that exists within the dropbox api
        System.out.println("dropbox: printing files");
        for(int i = 0; i < metaList.size(); i++){
            System.out.println("dropbox: " + metaList.get(i));
        }
        
        dropBoxUI = new DropBoxGUI(metaList, client);//create fresh dropbox gui
    }
    
    public ListFolderResult setupDropBoxConnection(){
        ListFolderResult result = null;
        try {

            DbxRequestConfig config;
            config = new DbxRequestConfig("dropbox/RemotelyFileSystem");
            
            client = new DbxClientV2(config, ACCESS_TOKEN);
            FullAccount account;
            DbxUserUsersRequests r1 = client.users();
            account = r1.getCurrentAccount();
            System.out.println(account.getName().getDisplayName());

            // Get files and folder metadata from Dropbox root directory
            result = client.files().listFolder("");
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                        System.out.println(metadata.getPathLower());
                }

                if (!result.getHasMore()) {
                        break;
                }

                result = client.files().listFolderContinue(result.getCursor());
            }
            
            //uploadFile(); 


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex);
        }
        
        return result;
    }
        

//    public List<String> getFilesAndFolders(){
//        List<String> files = new ArrayList<String>();
//        try{
//            ListFolderResult listFolderResult = client.files().listFolder("");
//            for (Metadata metadata : listFolderResult.getEntries()) {
//                String name = metadata.getName();
//                if (name.endsWith(".backup")) {
//                    files.add(name);
//                }
//            }
//            Collections.sort(files, new Comparator<String>() {
//                @Override
//                public int compare(String s1, String s2) {
//                    return s2.compareTo(s1);
//                }
//            });
//            
//        }catch(Exception e){System.out.println("ERROR");}
//        
//        return files;
//    }

}