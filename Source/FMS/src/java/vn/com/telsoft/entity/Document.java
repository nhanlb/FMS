package vn.com.telsoft.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HaiDT
 */
public class Document implements Serializable {

    private Integer docId;
    private String fileName;
    private String fileType;
    private String filePath;
    private String fileSize;
    private String userName;
    private Date dateUpload;
    private Integer status;

    public Document() {
    }

    public Document(Document obj) {
        this.docId = obj.docId;
        this.fileName = obj.fileName;
        this.fileType = obj.fileType;
        this.filePath = obj.filePath;
        this.fileSize = obj.fileSize;
        this.userName = obj.userName;
        this.dateUpload = obj.dateUpload;
        this.status = obj.status;
    }

    /**
     * @return the docId
     */
    public Integer getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileSize
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the dateUpdate
     */
    public Date getDateUpload() {
        return dateUpload;
    }

    /**
     * @param dateUpdate the dateUpdate to set
     */
    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
