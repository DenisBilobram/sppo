package is.lab1.is_lab1.service;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    /**
     * Загружает файл в MinIO и возвращает уникальное имя (ключ) файла.
     */
    public String uploadFile(MultipartFile file) throws Exception {
        // Проверяем, существует ли бакет
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // Загружаем файл
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucketName)
                .object(uniqueFileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build()
        );
        return uniqueFileName;
    }

    /**
     * Удаляет файл из MinIO по его имени.
     */
    public void deleteFile(String fileName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                                                   .bucket(bucketName)
                                                   .object(fileName)
                                                   .build());
    }

    /**
     * (Опционально) Метод для получения временной (presigned) ссылки на скачивание файла.
     */
    public String getFileUrl(String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                                     .method(Method.GET)
                                     .bucket(bucketName)
                                     .object(fileName)
                                     .build()
        );
    }

    public InputStream getFileStream(String fileName) throws Exception {
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("Ошибка при получении файла из MinIO: " + e.getMessage(), e);
        }
    }
}

