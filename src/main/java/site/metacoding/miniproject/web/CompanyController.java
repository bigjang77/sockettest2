package site.metacoding.miniproject.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.miniproject.domain.company.Company;
import site.metacoding.miniproject.domain.job.Job;
import site.metacoding.miniproject.service.CompanyService;
import site.metacoding.miniproject.service.IntroService;
import site.metacoding.miniproject.service.JobService;
import site.metacoding.miniproject.web.dto.request.company.CompanyJoinDto;
import site.metacoding.miniproject.web.dto.request.company.CompanyLoginDto;
import site.metacoding.miniproject.web.dto.request.company.CompanyUpdateDto;
import site.metacoding.miniproject.web.dto.request.intro.UpdateDto;
import site.metacoding.miniproject.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class CompanyController {

    private final CompanyService companyService;
    private final HttpSession session;
    private final IntroService introService;
    private final JobService jobService;

    @PostMapping("/co/login")
    public @ResponseBody CMRespDto<?> login(@RequestBody CompanyLoginDto loginDto, HttpServletResponse response) {
        System.out.println("===============");
        System.out.println(loginDto.isRemember());
        System.out.println("===============");

        // if (loginDto.isRemember() == true) {
        // Cookie cookie = new Cookie("companyUsername", loginDto.getCompanyUsername());
        // cookie.setMaxAge(60 * 60 * 24);
        // response.addCookie(cookie);

        // } else {
        // Cookie cookie = new Cookie("companyUsername", null);
        // cookie.setMaxAge(0);
        // response.addCookie(cookie);
        // }

        Company principal = companyService.?????????(loginDto);
        if (principal == null) {
            return new CMRespDto<>(-1, "???????????????", null);
        }
        session.setAttribute("principal", principal);
        return new CMRespDto<>(1, "???????????????", null);
    }

    @GetMapping("/co/companyInfo/{companyId}")
    public String ??????????????????(@PathVariable Integer companyId, Model model) {// ???????????? ???????????? ?????? ????????? ??? ?????? ??? company ?????????
        List<Job> jobPS = jobService.??????????????????();
        model.addAttribute("jobPS", jobPS);
        Company companyPS = (Company) session.getAttribute("principal");
        model.addAttribute("company", companyPS);
        return "company/companyInfo";
    }

    @PutMapping("/co/companyUpdate/{companyId}")
    public @ResponseBody CMRespDto<?> companyUpdate(@PathVariable Integer companyId,
            @RequestBody CompanyUpdateDto companyupdateDto) {
        Company companyPS = companyService.????????????????????????(companyId, companyupdateDto);
        session.setAttribute("principal", companyPS);
        return new CMRespDto<>(1, "????????????", null);
    }

    @DeleteMapping("/co/companyDelete/{companyId}")
    public @ResponseBody CMRespDto<?> companyDelete(@PathVariable Integer companyId) {
        companyService.??????????????????(companyId);
        session.invalidate();
        return new CMRespDto<>(1, "??????????????????", null);
    }

    @GetMapping("/co/companyIntroInsert")
    public String ??????????????????() {// ?????????
        return "company/coIntroInsert";
    }

    @GetMapping("/co/companyIntroDetail")
    public String ??????????????????() {// ???????????? ???????????? intro ?????????
        return "company/coIntroDetail";
    }

    @GetMapping("/co/companyIntroUpdate/{companyId}")
    public String getIntroUpdate(@PathVariable Integer companyId, Model model) {
        model.addAttribute("intro", introService.??????????????????????????????(companyId));
        return "company/coIntroUpdate";
    }

    @PutMapping("/co/companyIntroUpdate/{companyId}/update")
    public @ResponseBody CMRespDto<?> putIntroUpdate(@PathVariable Integer companyId,
            @RequestBody UpdateDto updateDto) {
        introService.????????????????????????(companyId, updateDto);
        return new CMRespDto<>(1, "????????????", null);
    }

    @PostMapping("/co/join")
    public @ResponseBody CMRespDto<?> companyJoin(@RequestBody CompanyJoinDto companyJoinDto) {
        companyService.????????????(companyJoinDto);
        return new CMRespDto<>(1, "??????????????????", null);
    }

    @GetMapping("/company/usernameSameCheck")
    public @ResponseBody CMRespDto<?> usernameSameCheck(String companyUsername) {
        System.out.println("company??????:" + companyUsername);
        boolean isSame = companyService.??????????????????????????????(companyUsername);
        return new CMRespDto<>(1, "??????", isSame);
    }

    @GetMapping("/co/logout")
    public String Companylogout() {
        session.invalidate();
        return "redirect:/co";
    }

}
