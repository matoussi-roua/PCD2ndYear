package PCD.BACKEND.RECRAFTMARKET.repository;


import PCD.BACKEND.RECRAFTMARKET.model.file.FileDataUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface FileDataUserRepository extends JpaRepository< FileDataUser,Long> {
    @Query(value = "select fd from FileDataUser fd where fd.id = :id")
    Optional<FileDataUser> fetchFileDataUserById(long id);

    @Transactional
    @Modifying
    @Query(value = "delete from FileDataUser f where f.id = :id")
    void deleteFileDataUserById(final long id);

    @Transactional
    @Modifying
    @Query(value = "delete from FileDataUser f where f in :files")
    void deleteAllFiles(List<FileDataUser> files);
}
