package org.nrocn.lib;


import org.junit.Test;
import org.nrocn.lib.baseobj.Directory;
import org.nrocn.lib.utils.BaseFileUtils;
import java.io.*;


public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(BaseFileUtils.getFileSuffix(new File("aaa"),false));
    }




    @Test
    public void getCatalogTree() throws FileNotFoundException {
         File file = new File("D:\\哥布林");
         Directory directory = new Directory(file);
    }


    //	@Test
    public void readText() throws IOException {
        for (int i = 1; i <= 4; i++) {

            String fileName = "/Users/fairytail/Downloads/eica/eica" + i + ".txt";
            String utf8FileName = "/Users/fairytail/Downloads/eica/eica" + i + "UTF8.txt";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileName)
                            , "UTF-8"));
            String context;
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(utf8FileName)
                    )
            );

            while ((context = reader.readLine()) != null) {
                out.write(context);
                out.flush();
            }
            out.close();
        }
    }

}
