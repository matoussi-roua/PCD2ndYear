package PCD.BACKEND.RECRAFTMARKET.service.file;

import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;

import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import PCD.BACKEND.RECRAFTMARKET.repository.FileDataUserRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceUserImpl implements FileServiceUser{
    private final FileDataUserRepository fileDataUserRepository;

    @Autowired
    public FileServiceUserImpl(FileDataUserRepository fileDataUserRepository)
    {
        this.fileDataUserRepository = fileDataUserRepository;
    }

    public FileDataUser save(FileDataUser fileDataUser)
    {
        return fileDataUserRepository.save(fileDataUser);
    }

    @Transactional
    public void deleteFileById(final long fileId)
    {
        FileDataUser fileToDelete = getFileDataUserById(fileId);
        fileDataUserRepository.deleteFileDataUserById(fileId);
    }

    private final String  FILE_SYSTEM_PATH= Paths.get("").toAbsolutePath().resolve("src").resolve("main").resolve("resources").resolve("FileSystemUser").toString() + "/";

    @Override
    public FileDataUser processUploadedFile(@NotNull final MultipartFile file) throws IOException {
        var originalFileName = file.getOriginalFilename();
        var fileName = originalFileName.substring(0, originalFileName.indexOf('.'));
        var extension = originalFileName.substring(originalFileName.indexOf('.'));
        var filePath = FILE_SYSTEM_PATH + fileName + UUID.randomUUID() + extension;

        FileDataUser fileDataUser = FileDataUser.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build();
        fileDataUserRepository.save(fileDataUser);
        file.transferTo(new File(filePath));
        return fileDataUser;
    }
    /*the downloadFile method retrieves the content of a file specified by the fileDataUser,
    prepares the necessary HTTP headers to indicate how the file should be handled by the client,
    and creates a ResponseEntity object containing the file content and headers to be returned as
    the HTTP response. This allows clients to download the file from the server.*/
    @Override
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileDataUser fileDataUser) throws IOException {
        final String filePath = fileDataUser.getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        HttpHeaders headers = new HttpHeaders();
        String contentType = determineContentType(filePath);
        headers.setContentDispositionFormData("attachment", fileDataUser.getFilePath());
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }


    @Override
    @Transactional
    public void deleteFileFromFileSystemUser(@NotNull final FileDataUser fileDataUser) throws IOException {
        File fileToDelete = new File(fileDataUser.getFilePath());
        if(!fileToDelete.delete())
        {
            throw new IOException(String.format("Failed to delete file with ID : %d",fileDataUser.getId()));
        }
        fileDataUserRepository.deleteFileDataUserById(fileDataUser.getId());
    }

    @Override
    @Transactional
    public void deleteAllFiles(@NotNull final List<FileDataUser> files) throws IOException {
        for(var file : files)
        {
            File fileToDelete = new File(file.getFilePath());
            if(!fileToDelete.delete())
            {
                throw new IOException(String.format("Failed to delete file with file path : %s",file.getFilePath()));
            }
        }
        fileDataUserRepository.deleteAllFiles(files);
    }
    public String determineContentType(@NotNull String filePath) {

        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

        HashMap<String, String> extensionToContentTypeMap = new HashMap<>();
        extensionToContentTypeMap.put("png", "image/png");
        extensionToContentTypeMap.put("jpg", "image/jpeg");
        extensionToContentTypeMap.put("jpeg", "image/jpeg");
        extensionToContentTypeMap.put("gif", "image/gif");
        extensionToContentTypeMap.put("bmp", "image/bmp");
        extensionToContentTypeMap.put("ico", "image/vnd.microsoft.icon");
        extensionToContentTypeMap.put("tiff", "image/tiff");
        return extensionToContentTypeMap.getOrDefault(extension, "application/octet-stream");
    }
    public FileDataUser getFileDataUserById(long fileDataId) {
        return fileDataUserRepository.fetchFileDataUserById(fileDataId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The file with ID : %s could not be found.", fileDataId)));
    }
}
