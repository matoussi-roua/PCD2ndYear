package PCD.BACKEND.RECRAFTMARKET.service.user;


import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTOMapper;
import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;
import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.UserEntityRepository;
import PCD.BACKEND.RECRAFTMARKET.security.utility.ResponseHandler;
import PCD.BACKEND.RECRAFTMARKET.service.file.FileServiceUser;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Service
public class UserEntityServiceImpl implements UserEntityService{


    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;
    private final FileServiceUser fileService;
@Override
     public ResponseEntity<Object> getUserEntityByUsername(final String username)
     {
         final UserEntity user= userEntityRepository.fetchUserWithUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("The user with username : %s could not be found.", username)));
         final UserEntityDTO userdto=userEntityDTOMapper.apply(user);
         return ResponseHandler.generateResponse(userdto , HttpStatus.OK) ;
     }
    @Override
    public ResponseEntity<Object> getUserEntityById(UUID idUserEntity) {
        final UserEntity currentUser = userEntityRepository.findById(idUserEntity).get();
        final UserEntityDTO user = userEntityDTOMapper.apply(currentUser);
        return ResponseHandler.generateResponse(user , HttpStatus.OK);

    }
    @Override
    public ResponseEntity<Object> updateUserEntity(UUID idUserEntity, UserEntity userUpdated) {
        UserEntity userToUpdate= userEntityRepository.findById(idUserEntity).get();
        userToUpdate.setUsername(userUpdated.getUsername());
        userToUpdate.setPhonenumber(userUpdated.getPhonenumber());
        userToUpdate.setAddress(userUpdated.getAddress());
        userEntityRepository.save(userToUpdate);
        final String successResponse = String.format("User with ID %d updated successfully", idUserEntity);

        return ResponseHandler.generateResponse(successResponse, HttpStatus.OK);

    }
    @Override
    public ResponseEntity<Object> getAllUserEntity() {
    final List<UserEntityDTO> users =mapToDTOList(userEntityRepository.findAll());
    return ResponseHandler.generateResponse(users, HttpStatus.OK);
    }

    @Override
    public UserEntityDTO mapToDTOItem(UserEntity user) {
        return userEntityDTOMapper.apply(user);
    }

    @Override
    public List<UserEntityDTO> mapToDTOList(List<UserEntity> users)
    {
        return users.stream().map(userEntityDTOMapper).toList();
    }
    @Override
    public void deleteUserEntityById(UUID idUserEntity) {
        userEntityRepository.deleteById(idUserEntity);
    }

    @Override
    public void deleteAllUserEntity(List<UserEntity> users) {
        userEntityRepository.deleteAll(users);
    }

    @Override
    public ResponseEntity<Object> addImageToUser(UUID UserId, @NotNull MultipartFile image) throws IOException {
        final UserEntity existingUser =  getUserEntityById(UserId);
        final FileDataUser newImage = fileService.processUploadedFile(image);
        newImage.setUserFile(existingUser);
        userEntityRepository.save(existingUser);
        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromUser(UUID UserId, long imageId) throws IOException {
        final UserEntity exisitingUser = getUserEntityById(UserId);
        final FileDataUser existingImage = fileService.getFileDataUserById(imageId);
        if(!exisitingUser.getFileUser().contains(existingImage)){
            throw new IllegalStateException(String.format("The Image with ID : %d  does not belong to this User", imageId));
        }
        exisitingUser.getFileUser().remove(existingImage);
        existingImage.setUserFile(null);
        fileService.deleteFileFromFileSystemUser(existingImage);
        userEntityRepository.save(exisitingUser);
        final String successResponse = String.format("The image with ID : %d deleted successfully",imageId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public  ResponseEntity<byte[]> fetchImageFromUser(final UUID UserId) throws IOException {
        final UserEntity user = getUserEntityById(UserId);
        final FileDataUser fileDataUser = user.getFileUser();
        return fileService.downloadFile(fileDataUser);
    }

/*@Override
public ResponseEntity<Object> addImageToUser(UUID UserId, @NotNull MultipartFile image) throws IOException {
    // Fetch the existing user entity
    final UserEntity existingUser = getUserEntityById(UserId);

    // Process the uploaded file
    final FileDataUser newImage = fileService.processUploadedFile(image);

    // Associate the file with the user
    newImage.setUserFile(existingUser);

    // Save the user entity
    userEntityRepository.save(existingUser);

    final String successResponse ="The image is added successfully.";
    return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
}

@Override
public ResponseEntity<Object> removeImageFromUser(UUID UserId, long imageId) throws IOException {
    // Fetch the existing user entity
    final UserEntity existingUser = getUserEntityById(UserId);

    // Fetch the existing image file
    final FileDataUser existingImage = fileService.getFileDataUserById(imageId);

    // Check if the image belongs to the user
    if (!existingUser.getFileUser().contains(existingImage)) {
        throw new IllegalStateException(String.format("The Image with ID : %d does not belong to this User", imageId));
    }

    // Remove the image from the user's list of files
    existingUser.getFileUser().remove(existingImage);

    // Unset the user association from the image
    existingImage.setUserFile(null);

    // Delete the image file from the file system
    fileService.deleteFileFromFileSystemUser(existingImage);

    // Save the user entity
    userEntityRepository.save(existingUser);

    final String successResponse = String.format("The image with ID : %d deleted successfully", imageId);
    return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
}

@Override
public ResponseEntity<byte[]> fetchImageFromUser(UUID UserId) throws IOException {
    // Fetch the existing user entity
    final UserEntity user = getUserEntityById(UserId);

    // Fetch the image file associated with the user
    final FileDataUser fileDataUser = user.getFilesUser();

    // Download the file and return its byte array
    return fileService.downloadFile(fileDataUser);
}
*/





    
    
/*
 private UUID id;

    @Column(name ="username" , unique = true , nullable = false)
    private String username;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "phone")
    private Number phonenumber;
    @Column(name = "materials")
    private String materials;
    @Column(name = "address")
    private String address;
    @Column(name = "points")
    private Number points;
    private final ArticleRepository articleRepository;
    private final ArticleDTOMapper articleDTOMapper;
    private final FileService fileService;

    @Override
    public ResponseEntity<Object> fetchArticleById(final long articleId) {
        final Article currentArticle = getArticleById(articleId);
        final ArticleDTO article = articleDTOMapper.apply(currentArticle);
        return ResponseHandler.generateResponse(article , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateArticleById(final long articleId, @NotNull String articleJson) throws IOException {

        final Article existingArticle  = getArticleById(articleId);
        final Article updatedArticle = new ObjectMapper().readValue(articleJson , Article.class);

        var updatedChapters = updatedArticle.getChapters();
        var updatedDetails = updatedArticle.getDetails();

        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setPrice(updatedArticle.getPrice());
        existingArticle.setQuantity(updatedArticle.getQuantity());
        existingArticle.setReference(updatedArticle.getReference());
        existingArticle.setLayoutDescription(updatedArticle.getLayoutDescription());

        chapterService.deleteAllChapters(existingArticle.getChapters());
        detailService.deleteAllDetails(existingArticle.getDetails());

        for(var updatedChapter : updatedChapters)
        {
            updatedChapter.setArticle(existingArticle);
        }
        existingArticle.setChapters(updatedChapters);
        for(var updatedDetail : updatedDetails)
        {
            updatedDetail.setArticle(existingArticle);
        }
        existingArticle.setDetails(updatedDetails);



        articleRepository.save(existingArticle);


        final String successResponse = String.format("Article with ID %d updated successfully", articleId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteArticleById(long articleId) throws IOException {
        final Article existingArticle = getArticleById(articleId);

        chapterService.deleteAllChapters(existingArticle.getChapters());
        detailService.deleteAllDetails(existingArticle.getDetails());
        fileService.deleteAllFiles(existingArticle.getFiles());
        articleRepository.deleteArticleById(articleId);

        final String successResponse = String.format("Article with ID %d deleted successfully", articleId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addImageToArticle(long articleId, @NotNull MultipartFile image) throws IOException {
        final Article existingArticle =  getArticleById(articleId);
        final FileData newImage = fileService.processUploadedFile(image);
        newImage.setArticle(existingArticle);
        articleRepository.save(existingArticle);

        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromArticle(long articleId, long imageId) throws IOException {
        final Article exisitingArticle = getArticleById(articleId);
        final FileData existingImage = fileService.getFileDataById(imageId);

        if(!exisitingArticle.getFiles().contains(existingImage))
        {
            throw new IllegalStateException(String.format("The Image with ID : %d  does not belong to this article", imageId));
        }

        exisitingArticle.getFiles().remove(existingImage);
        existingImage.setArticle(null);
        fileService.deleteFileFromFileSystem(existingImage);
        articleRepository.save(exisitingArticle);
        final String successResponse = String.format("The image with ID : %d deleted successfully",imageId);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }
    @Override
    public  ResponseEntity<byte[]> fetchImageFromArticle(final long articleId,final int fileIndex) throws IOException {

        final Article article = getArticleById(articleId);
        if(fileIndex >= article.getFiles().size())
        {
            throw new IllegalStateException("The file index is out of range.");
        }
        final FileData fileData = article.getFiles().get(fileIndex);
        return fileService.downloadFile(fileData);
    }


    @Override
    public ResponseEntity<Object> fetchAllArticle(final long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber - 1, 10);

        final List<ArticleDTO> articles = articleRepository.fetchAllArticles(pageable).stream().map(articleDTOMapper).toList();
        if(articles.isEmpty() && pageNumber > 1)
        {
            return fetchAllArticle(1);
        }
        final long total  = articleRepository.getTotalArticleCount();
        return ResponseHandler.generateResponse(articles , HttpStatus.OK , articles.size() , total);
    }

    @Override
    public void deleteAllArticles(List<Article> articles) {
        articleRepository.deleteAllArticles(articles);
    }

    @Override
    public ArticleDTO mapToDTOItem(Article article) {
        return articleDTOMapper.apply(article);
    }

    @Override
    public List<ArticleDTO> mapToDTOList(List<Article> articles)
    {
        return articles.stream().map(articleDTOMapper).toList();
    }

    @Override
    public Article getArticleById(final long articleId)
    {
        return articleRepository.fetchArticleById(articleId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Article with ID : %d could not be found in our system", articleId))
        );
    }
}*/

}
