package uk.gegc.ecommerce.sbecom.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.gegc.ecommerce.sbecom.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        String originalFileName = image.getOriginalFilename();
        System.out.println(originalFileName);

        String randomId = UUID.randomUUID().toString();
        String filename = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + filename;

        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        Files.copy(image.getInputStream(), Paths.get(filePath));

        return filename;
    }
}
