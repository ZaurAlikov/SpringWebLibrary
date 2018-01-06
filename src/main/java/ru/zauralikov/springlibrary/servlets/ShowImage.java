package ru.zauralikov.springlibrary.servlets;

import ru.zauralikov.springlibrary.objects.LibraryFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "ShowImage", urlPatterns = {"/ShowImage"})
public class ShowImage extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg");

        try(OutputStream out = resp.getOutputStream()){
            int index = Integer.valueOf(req.getParameter("index"));
            LibraryFacade libraryFacade = (LibraryFacade) getServletContext().getAttribute("libraryFacade");
            byte[] image = libraryFacade.getBooks().get(index).getImage();
            resp.setContentLength(image.length);
            out.write(image);
        }
    }
}
