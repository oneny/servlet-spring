package oneny.sevlet.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import oneny.sevlet.domain.member.Member;
import oneny.sevlet.domain.member.MemberRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

  private MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    response.setCharacterEncoding("utf-8");

    List<Member> members = memberRepository.findAll();

    PrintWriter w = response.getWriter();
    w.write("<html>");
    w.write("<head>");
    w.write("    <meta charset=\"UTF-8\">");
    w.write("    <title>Title</title>");
    w.write("</head>");
    w.write("<body>");
    w.write("<a href=\"/index.html\">메인</a>");
    w.write("<table>");
    w.write("    <thead>");
    w.write("    <th>id</th>");
    w.write("    <th>username</th>");
    w.write("    <th>age</th>");
    w.write("    </thead>");
    w.write("    <tbody>");

    for (Member member : members) {
      w.write("    <tr>");
      w.write("        <td>" + member.getId() + "</td>");
      w.write("        <td>" + member.getUsername() + "</td>");
      w.write("        <td>" + member.getAge() + "</td>");
      w.write("    </tr>");
    }
    w.write("    </tbody>");
    w.write("</table>");
    w.write("</body>");
    w.write("</html>");
  }
}
