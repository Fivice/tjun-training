package tjuninfo.training.task.vo;

import java.io.Serializable;

public class FileVO implements Serializable {

    private String fileName;

    public FileVO() {
    }

    public FileVO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
