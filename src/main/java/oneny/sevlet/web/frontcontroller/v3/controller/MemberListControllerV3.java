package oneny.sevlet.web.frontcontroller.v3.controller;

import oneny.sevlet.domain.member.Member;
import oneny.sevlet.domain.member.MemberRepository;
import oneny.sevlet.web.frontcontroller.ModelView;
import oneny.sevlet.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

  private MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public ModelView process(Map<String, String> paramMap) {
    List<Member> members = memberRepository.findAll();

    ModelView mv = new ModelView("members");
    mv.getModel().put("members", members);

    return mv;
  }
}
