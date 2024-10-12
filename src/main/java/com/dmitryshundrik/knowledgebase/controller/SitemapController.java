package com.dmitryshundrik.knowledgebase.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Controller
public class SitemapController {

    @GetMapping("/robots.txt")
    public void getRobots(HttpServletResponse response) {
        String sitemap = "";
        try {
            sitemap = IOUtils.toString(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResourceAsStream("robots.txt"))));

            response.setContentType("text/plain");
            ServletOutputStream outStream = response.getOutputStream();
            outStream.println(sitemap);
            outStream.flush();
            outStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/sitemap.xml")
    public void getSitemap(HttpServletResponse response) {
        String sitemap = "";
        try {
            sitemap = IOUtils.toString(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResourceAsStream("sitemap.xml"))));

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
