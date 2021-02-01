/*
package com.epam.web.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ImageService {

    public void getImage(){
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(1024*1024);
    File tempDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
    factory.setRepository(tempDir);
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setSizeMax(1024 * 1024 * 10);
    List items = upload.parseRequest(request);
    Iterator iter = items.iterator();
    while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        if (item.isFormField()) {
            //если принимаемая часть данных является полем формы
            processFormField(item);
        } else {
            //в противном случае рассматриваем как файл
            processUploadedFile(item);
        }
    }
}

    private void processUploadedFile(FileItem item) throws Exception {
        File uploadetFile = null;
        //выбираем файлу имя пока не найдём свободное
        do{
            String path = getServletContext().getRealPath("/upload/"+random.nextInt() + item.getName());
            uploadetFile = new File(path);
        }while(uploadetFile.exists());

        //создаём файл
        uploadetFile.createNewFile();
        //записываем в него данные
        item.write(uploadetFile);
    }



      Выводит на консоль имя параметра и значение
     @param item


    private void processFormField(FileItem item) {
        System.out.println(item.getFieldName()+"="+item.getString());
    }
}
*/
