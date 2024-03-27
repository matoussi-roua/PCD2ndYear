package PCD.BACKEND.RECRAFTMARKET.dto.file;

import PCD.BACKEND.RECRAFTMARKET.model.file.FileData;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FileDtoMapper implements Function<FileData, FileDto> {
        @Override
        public FileDto apply(FileData fileData) {
            return new FileDto(
                    fileData.getId(),
                    fileData.getName(),
                    fileData.getType(),
                    fileData.getFilePath()
            );
        }
    }

