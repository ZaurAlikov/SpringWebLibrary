package ru.zauralikov.springlibrary.servlets;

import ru.zauralikov.springlibrary.objects.LibraryFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet(name = "PdfContent", urlPatterns = {"/PdfContent"})
public class PdfContent extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");

        try(OutputStream out = resp.getOutputStream()){
            long id = Long.valueOf(req.getParameter("id"));
            boolean save = Boolean.valueOf(req.getParameter("save"));
            LibraryFacade libraryFacade = (LibraryFacade) getServletContext().getAttribute("libraryFacade");
            byte[] content = libraryFacade.getContent(id);
            resp.setContentLength(content.length);

            if(save){
                String name = URLEncoder.encode(req.getParameter("filename"), "UTF-8").replace("+", " ");
                resp.setHeader("Content-Disposition", "attachment;filename=" + name + ".pdf");
            }
            out.write(content);
        }
    }
}
