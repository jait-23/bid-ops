package com.predix.bidopscore.web.rest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sun.jersey.multipart.FormDataParam;

@RestController
@RequestMapping("/file")
public class UploadManualResource {

  @Autowired
  private HttpServletRequest request;

  private String uploadedFileLocation = "/Users/jait23/Extra/Sacramento/COURSE/sem2/csc230/bidopscore/src/main/webapp/swagger-ui/";

  private final Logger log = LoggerFactory.getLogger(UploadManualResource.class);

  @RequestMapping(value = "/upload",
                  method = RequestMethod.POST,
                  produces = MediaType.APPLICATION_JSON)
  public Response uploadFile(
          @FormDataParam("file") InputStream uploadedInputStream,
          @RequestParam("file") MultipartFile file) throws IOException {

      log.trace("Entering uploadFile");

      int response = HttpStatus.OK.value();
      String output = null;

      try {
          String filename = file.getOriginalFilename();
          log.debug("Request for uploading {}", filename);

          File finalfile = new File(uploadedFileLocation + filename);
          if (! finalfile.exists()) {
              file.transferTo(finalfile);
              output = filename;

              log.debug("created file {}", filename);
          }
          else if (finalfile.exists()) {
              log.debug("file {} exists", filename);

              long randomNum = System.currentTimeMillis();
              String fileNameWithoutExtn = (filename.substring(0, filename.lastIndexOf(".")));
              String extension = filename.substring(filename.lastIndexOf("."));

              // TODO - what if filename exceeds the maximum size (255 ?)
              String newFileName = fileNameWithoutExtn + "_" + randomNum + extension;

              File newfile = new File(uploadedFileLocation + newFileName);
              file.transferTo(newfile);

              log.debug("created file {}", newFileName);
              output = newFileName;
          }
      } catch (IllegalStateException | IOException e) {
          log.warn("uploadFile - Exception {} - {}", e.getClass().getName(), e.getMessage());
          response = HttpStatus.INTERNAL_SERVER_ERROR.value();
      }

      log.trace("Exiting uploadFile with {}", output);
      return Response.status(response).entity(output).build();
  }
}
