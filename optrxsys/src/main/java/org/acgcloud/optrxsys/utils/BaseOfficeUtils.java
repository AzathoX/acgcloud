package org.acgcloud.optrxsys.utils;

import org.acgcloud.optrxsys.office.IOffices;
import org.acgcloud.optrxsys.office.docx.Word;
import org.acgcloud.optrxsys.office.ppt.PowerPoint;
import org.acgcloud.optrxsys.office.xls.Excel;
import org.nrocn.lib.utils.BaseFileUtils;

public abstract class BaseOfficeUtils {


    public  static  final IOffices officesFactory(String path){
        String fileSuffix = BaseFileUtils.getFileSuffix(path, false);
        switch (fileSuffix){
            case "doc": case "docx":{
              return Word.newInstance(path);
            }
            case "xls": case "xlsx":{
                return Excel.newInstance(path);
            }
            case "ppt": case "pptx":{
                return PowerPoint.newInstance(path);
            }
            default:
                return  null;
        }
    }

}
