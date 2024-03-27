package PCD.BACKEND.RECRAFTMARKET.service.file;

import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface FileService {

    public FileData processUploadedFile(@NotNull final MultipartFile file) throws IOException;
    public void deleteAllFiles(@NotNull final List<FileData> files) throws IOException;
    public String determineContentType(@NotNull String filePath);
    public FileData getFileDataById(long fileDataId);
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileData fileData) throws IOException ;
    public void deleteFileFromFileSystem(@NotNull final FileData fileData) throws IOException ;




}
