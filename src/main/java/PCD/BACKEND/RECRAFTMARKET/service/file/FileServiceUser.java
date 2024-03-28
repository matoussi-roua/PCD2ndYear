package PCD.BACKEND.RECRAFTMARKET.service.file;


import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileServiceUser {

    public FileDataUser processUploadedFile(@NotNull final MultipartFile file) throws IOException;
    public void deleteAllFiles(@NotNull final List<FileDataUser> files) throws IOException;
    public String determineContentType(@NotNull String filePath);
    public FileDataUser getFileDataUserById(long fileDataId);
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileDataUser fileDataUser) throws IOException ;
    public void deleteFileFromFileSystemUser(@NotNull final FileDataUser fileDataUser) throws IOException ;




}
