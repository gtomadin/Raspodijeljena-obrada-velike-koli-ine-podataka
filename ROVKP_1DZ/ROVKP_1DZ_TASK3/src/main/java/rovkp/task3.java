/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rovkp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 * @author gtoma
 */
public class task3 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        
        Configuration conf = new Configuration();
        LocalFileSystem lfs = LocalFileSystem.getLocal(conf);
	FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"), conf);
        Path localPath = new Path("C:/Code/NetBeans/ROVKP DZ.1/gutenberg.zip");
        Path hdfsPath = new Path("/user/gtoma/gutenberg.zip");
		
        System.out.println("HDFS direktoriji: " + hdfs.getFileStatus(hdfsPath));
	System.out.println("Lokalni direktoriji: " + lfs.getFileStatus(localPath));
        
	lfs.close();
	hdfs.close();
    }
}
