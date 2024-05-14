package com.ztpai.fishqi.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FTPUploader {
    private String server = "localhost";
    private int port = 21;
    private String user = "root";
    private String pass = "root";

    public void uploadFile(InputStream inputStream, String remoteFilePath) throws IOException {
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

            String[] pathElements = remoteFilePath.split("/");
            String path = "";
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
}
