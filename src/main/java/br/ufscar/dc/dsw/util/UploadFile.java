package br.ufscar.dc.dsw.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static br.ufscar.dc.dsw.util.Constantes.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFile {

	public void processarUpload(HttpServletRequest request, long idUsuario) throws ServletException, IOException {
        String filePath = null;
		if (ServletFileUpload.isMultipartContent(request)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);
			String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			try {
				List<FileItem> formItems = upload.parseRequest(request);

				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if (!item.isFormField() && "pdfData".equals(item.getFieldName())) {
							String fileName = idUsuario + ".pdf";
							filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);
							item.write(storeFile);
                            System.out.println("File stored at: " + filePath);
						}
					}
				}
			} catch (Exception ex) {
				request.getSession().setAttribute("message", "There was an error: " + ex.getMessage());
                System.out.println("File stored at: " + filePath);
			}
		}
	}
}
