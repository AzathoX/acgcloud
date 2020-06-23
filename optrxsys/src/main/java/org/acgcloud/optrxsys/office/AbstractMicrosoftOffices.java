package org.acgcloud.optrxsys.office;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.nrocn.lib.utils.BaseFileUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

@Setter
@Getter
public abstract class AbstractMicrosoftOffices extends File implements  IOffices{

    private String version;

    private String suffix;

    public AbstractMicrosoftOffices(String pathname) {
        super(pathname);
    }

    public AbstractMicrosoftOffices(String parent, String child) {
        super(parent, child);
    }

    public AbstractMicrosoftOffices(File parent, String child) {
        super(parent, child);
    }

    public AbstractMicrosoftOffices(URI uri) {
        super(uri);
    }

    protected abstract InputStream converter2007PlusToHtml(String target);

    protected abstract InputStream converter2003ToHtml(String target);

    @Override
    public InputStream toHtml(String target) {
        File file = BaseFileUtils.find(target);
        if(ObjectUtils.isEmpty(file)){
            return null;
        }
        File imageFolderFile = new File(target + "/img");
        if(!imageFolderFile.exists()){
            BaseFileUtils.mkdir(imageFolderFile.getAbsolutePath());
        }
        if(version.equals("2003")){
            return converter2003ToHtml(target);
        }
        else{
            return converter2007PlusToHtml(target);
        }
    }


}
