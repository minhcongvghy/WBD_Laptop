package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.Product;
import com.codegym.wbdlaptop.model.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract  class FirebaseStorageService<T> {

    private StorageClient getFirebaseStorage() {
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();

//            JSONObject jsonObject = new JSONObject(environment.getProperty("GOOGLE_KEY"));
//            GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(environment.getProperty("GOOGLE_KEY").getBytes()));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl("https://cong-1995.firebaseio.com")
                    .setStorageBucket("cong-1995.appspot.com")
                    .build();

            FirebaseApp fireApp = null;
            List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
            if (firebaseApps != null && !firebaseApps.isEmpty()) {
                for (FirebaseApp app : firebaseApps) {
                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                        fireApp = app;
                }
            } else
                fireApp = FirebaseApp.initializeApp(options);
            return StorageClient.getInstance(fireApp);
        } catch (IOException ex) {
            throw new RuntimeException("Could not get admin-sdk json file. Please try again!", ex);
        }
    }

    private String getNewExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return originalFileName!=null?originalFileName.substring(originalFileName.lastIndexOf(".") + 1):"";
    }

    private String getFileName(Object object, MultipartFile file) {
        String extension = getNewExtension(file);
        if (object instanceof Product) {
            Product product = (Product) object;
            return product.getId().toString().concat(" - ").concat(product.getName()).concat(".").concat(extension);
        }

        if (object instanceof User) {
            User user = (User) object;
            return user.getId().toString().concat("-").concat(user.getUsername()).concat(".").concat(extension);
        }

        return null;
    }

    public String saveToFirebaseStorage(Object object, MultipartFile file) throws IOException {
        String fileName = getFileName(object, file);
        StorageClient storageClient = getFirebaseStorage();
        Bucket bucket = storageClient.bucket();

        try {
            InputStream testFile = file.getInputStream();
            String blobString = "";
            if (object instanceof Product) {
                blobString = "product/" + fileName;
            }

            if (object instanceof User) {
                blobString = "user/" + fileName;
            }

            Blob blob = bucket.create(blobString, testFile, Bucket.BlobWriteOption.userProject("cong-1995"));
            bucket.getStorage().updateAcl(blob.getBlobId(), Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            String blobName = blob.getName();

            if (object instanceof Product) {
                ((Product) object).setBlobString(blobName);
            }

            if (object instanceof User) {
                ((User) object).setBlobString(blobName);
            }
            return blob.getMediaLink();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public RuntimeException deleteFirebaseStorageFile(Object object) {
        try {
            StorageClient storageClient = getFirebaseStorage();
            String blobString = "";
            if (object instanceof Product) {
                blobString = ((Product) object).getBlobString();
            }

            if (object instanceof User) {
                blobString = ((User) object).getBlobString();
            }

            BlobId blobId = BlobId.of(storageClient.bucket().getName(), blobString);
            storageClient.bucket().getStorage().delete(blobId);
        } catch (Exception e) {
            return new RuntimeException("Error when delete file on Firebase" + e);
        }
        return null;
    }

}
