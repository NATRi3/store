package by.epam.store.controller;

import by.epam.store.command.Command;
import by.epam.store.command.impl.UploadFileCommand;
import by.epam.store.util.RequestParameter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@WebServlet("/imageServlet")
public class ImageServlet extends HttpServlet {
    public static final String IMAGE_AVATAR_PATH = "D:/projectImg/avatars/";
    public static final String IMAGE_PRODUCT_PATH = "D:/projectImg/products/";
    public static final String IMAGE_NEWS_COLLECTIONS_PATH = "D:/projectImg/news/";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = req.getParameter(RequestParameter.IMAGE_NAME);
        String command = req.getParameter(RequestParameter.COMMAND);
        String file = null;
        if(command.equals("user")){
            file = IMAGE_AVATAR_PATH+imageName;
        }
        if(command.equals("product")){
            file = IMAGE_PRODUCT_PATH+imageName;
        }
        if(command.equals("news")){
            file = IMAGE_NEWS_COLLECTIONS_PATH+imageName;
        }
        byte[] image = Files.readAllBytes(Paths.get(file));
        resp.setContentType("image/jpeg");
        resp.setContentLength(image.length); // imageBytes - image in bytes
        resp.getOutputStream().write(image);//
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = new UploadFileCommand();
        String page = command.execute(req);
    }
}
