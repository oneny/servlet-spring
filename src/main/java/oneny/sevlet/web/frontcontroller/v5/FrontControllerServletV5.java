package oneny.sevlet.web.frontcontroller.v5;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oneny.sevlet.web.frontcontroller.ModelView;
import oneny.sevlet.web.frontcontroller.MyView;
import oneny.sevlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import oneny.sevlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import oneny.sevlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import oneny.sevlet.web.frontcontroller.v4.ControllerV4;
import oneny.sevlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import oneny.sevlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import oneny.sevlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import oneny.sevlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import oneny.sevlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

  private final Map<String, Object> handlerMappdingMap = new HashMap<>(); // V3, V4 타입들을 받아야 하기 때문에 Object로 타입 지정
  private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

  public FrontControllerServletV5() {
    initHandlerMappingMap();
    initHandlerAdapters();
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Object handler = getHandler(request); // new MemberFormControllerV3
    if (handler == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    MyHandlerAdapter adapter = getHandlerAdapter(handler); // new ControllerV3HandlerAdapter
    ModelView mv = adapter.handle(request, response, handler); // adapter.handle(request, response, new MemberFormControllerV3)

    MyView view = viewResolver(mv.getViewName());
    view.render(mv.getModel(), request, response);
  }

  private MyHandlerAdapter getHandlerAdapter(Object handler) {
    for (MyHandlerAdapter adapter : handlerAdapters) {
      if (adapter.supports(handler)) {
        return adapter;
      }
    }

    throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다.");
  }

  private Object getHandler(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return handlerMappdingMap.get(requestURI);
  }

  private void initHandlerAdapters() {
    handlerAdapters.add(new ControllerV3HandlerAdapter());
    handlerAdapters.add(new ControllerV4HandlerAdapter());
  }

  private void initHandlerMappingMap() {
    // v3
    handlerMappdingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
    handlerMappdingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
    handlerMappdingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

    // v4
    handlerMappdingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
    handlerMappdingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
    handlerMappdingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
  }

  private MyView viewResolver(String viewName) {
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
  }
}
