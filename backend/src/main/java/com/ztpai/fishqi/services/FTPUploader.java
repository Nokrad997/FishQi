package com.ztpai.fishqi.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FTPUploader {
    private String server = "172.21.0.3";
    private int port = 21;
    private String user = "root";
    private String pass = "root";

    public void uploadFile(InputStream inputStream, String remoteFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            System.out.println("Attempting to upload file to: " + remoteFilePath);
            ftpClient.connect(server, port);
            int reply = ftpClient.getReplyCode();
            System.out.println("FTP server reply code: " + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection, response code: " + reply);
            }

            System.out.println("Connected to FTP server");

            if (!ftpClient.login(user, pass)) {
                ftpClient.logout();
                throw new IOException("FTP login failed with user: " + user);
            }

            System.out.println("Logged in successfully");

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            String[] pathElements = remoteFilePath.split("/");
            String path = "";

            System.out.println("Path elements: " + pathElements);
            for (int i = 0; i < pathElements.length - 1; i++) {
                path = pathElements[i] + "/";

                System.out.println("Attempting to access or create directory: " + path);

                if (!ftpClient.changeWorkingDirectory(path)) {
                    System.out.println("Directory does not exist, attempting to create: " + path);
                    if (!ftpClient.makeDirectory(path)) {
                        throw new IOException("Unable to create directory: " + path + " - Server reply: "
                                + ftpClient.getReplyString());
                    }
                    if (!ftpClient.changeWorkingDirectory(path)) {
                        throw new IOException("Created directory, but unable to change into it: " + path);
                    }
                }
            }
            path = pathElements[pathElements.length - 1];
            if (!ftpClient.storeFile(path, inputStream)) {
                throw new IOException("File upload failed: " + ftpClient.getReplyString());
            } else {
                System.out.println("File has been uploaded successfully to: " + remoteFilePath);
            }
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection, response code: " + reply);
            }

            if (!ftpClient.login(user, pass)) {
                ftpClient.logout();
                throw new IOException("FTP login failed with user: " + user);
            }

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            String file = remoteFilePath.split("/")[remoteFilePath.split("/").length - 1];
            try (OutputStream outputStream = new FileOutputStream(localFilePath + file)) {
                if (!ftpClient.retrieveFile(remoteFilePath, outputStream)) {
                    throw new IOException("File download failed: " + ftpClient.getReplyString());
                } else {
                    System.out.println("File has been downloaded successfully to: " + localFilePath);
                }
            }
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void deleteUserCatalog(Long userId) {
        String remoteDirectoryPath = "/FISHQI/" + userId;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection, response code: " + reply);
            }

            if (!ftpClient.login(user, pass)) {
                ftpClient.logout();
                throw new IOException("FTP login failed with user: " + user);
            }

            ftpClient.enterLocalPassiveMode();

            deleteDirectoryContents(ftpClient, remoteDirectoryPath);
            ftpClient.removeDirectory(remoteDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting user catalog", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void deleteSetCatalog(Long userId, Long setId) {
        String remoteDirectoryPath = "/FISHQI/" + userId + "/" + setId;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection, response code: " + reply);
            }

            if (!ftpClient.login(user, pass)) {
                ftpClient.logout();
                throw new IOException("FTP login failed with user: " + user);
            }

            ftpClient.enterLocalPassiveMode();

            deleteDirectoryContents(ftpClient, remoteDirectoryPath);
            ftpClient.removeDirectory(remoteDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting set catalog", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void deleteDirectoryContents(FTPClient ftpClient, String parentDir) throws IOException {
        FTPFile[] files = ftpClient.listFiles(parentDir);

        if (files != null && files.length > 0) {
            for (FTPFile file : files) {
                String currentFilePath = parentDir + "/" + file.getName();
                if (file.isDirectory()) {
                    deleteDirectoryContents(ftpClient, currentFilePath); // Recursively delete subdirectories
                    ftpClient.removeDirectory(currentFilePath);
                } else {
                    ftpClient.deleteFile(currentFilePath);
                }
            }
        }
    }
}
