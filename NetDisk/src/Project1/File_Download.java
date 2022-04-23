package Project1;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@WebServlet("/FileDownload")
public class File_Download extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session =req.getSession(false);

        String fileName = req.getParameter("download_filename");
        String userPath = req.getServletContext().getRealPath("/WEB-INF/");
        String real_filepath=userPath+"File/"+session.getAttribute("email") +"/"+ fileName; //寻找文件路径
        File file = new File(real_filepath);

        if (file.exists() && file.isFile()) {
            resp.setContentType("application/x-msdownload");
            resp.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); //下载框内容

            FileInputStream is = new FileInputStream(file);
            ServletOutputStream os = resp.getOutputStream();//创建输入流和输出流

            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = is.read(temp)) != -1) {
                os.write(temp, 0, len);
            }//下载文件

            os.close();
            is.close();
        }

        else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要下载的文件不存在</h2>");
            resp.getWriter().close(); }

    }
}
