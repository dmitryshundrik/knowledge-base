package com.dmitryshundrik.knowledgebase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class SitemapController {

    @GetMapping("/sitemap.xml")
    public void getSitemap(HttpServletResponse response) {
        String sitemap = "";
        try {
            File file = ResourceUtils.getFile("classpath:sitemap.xml");
            sitemap = Files.readString(Path.of(file.getPath()));

            response.setContentType("application/xml");
            ServletOutputStream outStream = response.getOutputStream();
            outStream.println(sitemap);
            outStream.flush();
            outStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
