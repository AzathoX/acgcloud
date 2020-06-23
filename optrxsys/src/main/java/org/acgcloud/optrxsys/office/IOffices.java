package org.acgcloud.optrxsys.office;

import java.io.InputStream;

public interface IOffices {

    String[] WORD_SUFFIX = {
      "doc","docx"
    };

    String[] EXCEL_SUFFIX = {
      "xls","xlsx"
    };

    String[] POWERPOINT_SUFFIX = {
       "ppt","pptx"
    };


    InputStream toHtml(String targetFile);
}
