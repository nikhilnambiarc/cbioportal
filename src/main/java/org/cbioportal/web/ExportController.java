package org.cbioportal.web;

import jakarta.servlet.http.HttpServletResponse;
import org.cbioportal.file.export.ZipOutputStreamWriterFactory;
import org.cbioportal.service.impl.ExportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    //TODO make it work for virtual studies as well
    //@PreAuthorize("hasPermission(#studyId, 'CancerStudyId', T(org.cbioportal.utils.security.AccessLevel).READ)")
    @GetMapping("/export/study/{studyId}.zip")
    public void downloadStudyData(HttpServletResponse response, @PathVariable String studyId) throws IOException {

        response.setContentType(("application/zip"));
        response.setHeader("Content-Disposition", "attachment; filename=\""+ studyId +".zip\"");

        try (OutputStream out = response.getOutputStream();
             BufferedOutputStream bof = new BufferedOutputStream(out);
             ZipOutputStreamWriterFactory zipOutputStreamWriterFactory = new ZipOutputStreamWriterFactory(bof)) {
            exportService.exportStudyData(zipOutputStreamWriterFactory, studyId);
        }
    }
}
