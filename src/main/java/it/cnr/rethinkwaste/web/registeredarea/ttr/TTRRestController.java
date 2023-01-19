package it.cnr.rethinkwaste.web.registeredarea.ttr;

import it.cnr.rethinkwaste.asposeUtils.AssessmentCreation;
import it.cnr.rethinkwaste.asposeUtils.ReportCreation;
import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.Response;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.model.assessment.*;
import it.cnr.rethinkwaste.service.UserService;
import it.cnr.rethinkwaste.service.assessment.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@RestController
@RequestMapping("/registeredarea/ttr")
public class TTRRestController {

    private static DecimalFormat df = new DecimalFormat("#.##");

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleInstanceService moduleInstanceService;

    @Autowired
    private CategoryInstanceService categoryInstanceService;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/downloadTTRreport")
    public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> ttrModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(1L)).
                findFirst();
        if(ttrModule.isPresent()) {
            ReportCreation reportCreation = new ReportCreation();
            String pathToDownloadFile = reportCreation.createReport(ttrModule.get(), reportService, ((CustomUserDetails) user).getUser());
            File file = new File(pathToDownloadFile);

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());

            InputStream inputStream = new FileInputStream(file);;
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @GetMapping(value = "/downloadTTRassessment")
    public void downloadTTRassessment(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> ttrModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(1L)).
                findFirst();
        if(ttrModule.isPresent()) {
            AssessmentCreation assessmentCreation = new AssessmentCreation();
            String pathToDownloadFile = assessmentCreation.createAssessment(ttrModule.get());
            File file = new File(pathToDownloadFile);

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());

            InputStream inputStream = new FileInputStream(file);;
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/saveStep", method = RequestMethod.POST)
    public Response saveStep(@RequestBody String obj) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User_ user = ((CustomUserDetails) userService.loadUserByUsername(email)).getUser();
        ModuleInstance moduleInstance = null;
        Response response = null;

        if(user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 1).findFirst().isPresent()) {
            moduleInstance = user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 1).findFirst().get();
        }
        else {
            moduleInstance = new ModuleInstance();
            moduleInstance.setModule(moduleService.findById(1L));
            moduleInstance.setCategoryCategoryInstanceSortedMap(new TreeMap<>());
            moduleInstanceService.save(moduleInstance);
        }

        JSONObject jsonObject = new JSONObject(obj);
        String categoryId = jsonObject.get("categoryId").toString();
        Optional<Category> categoryOptional = categoryService.findById(Long.parseLong(categoryId));
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(category) != null) {
                moduleInstance.getCategoryCategoryInstanceSortedMap().get(category).setQuestionAnswerSortedMap(new TreeMap<>());
            }
            else {
                CategoryInstance categoryInstance = new CategoryInstance();
                categoryInstance.setQuestionAnswerSortedMap(new TreeMap<>());
                categoryInstanceService.save(categoryInstance);
                moduleInstance.getCategoryCategoryInstanceSortedMap().put(category, categoryInstance);
            }

            JSONArray arr = jsonObject.getJSONArray("infos");
            for (int i = 0; i < arr.length(); i++) {
                String questionId = arr.getJSONObject(i).getString("questionId");
                Optional<Question> questionOptional = questionService.findById(Long.parseLong(questionId));
                if (questionOptional.isPresent()) {
                    String answerId = arr.getJSONObject(i).getString("answerId");
                    Optional<Answer> answerOptional = answerService.findById(Long.parseLong(answerId));
                    if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(category)) {
                        Map<Question, Answer> map = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category).getQuestionAnswerSortedMap();
                        map.put(questionOptional.get(), answerOptional.get());
                        moduleInstanceService.save(moduleInstance);
                    }
                    else {
                        CategoryInstance categoryInstance = new CategoryInstance();
                        categoryInstance.setQuestionAnswerSortedMap(new TreeMap<>());
                        categoryInstance.getQuestionAnswerSortedMap().put(questionOptional.get(), answerOptional.get());
                        categoryInstance = categoryInstanceService.save(categoryInstance);
                        moduleInstance.getCategoryCategoryInstanceSortedMap().put(category, categoryInstance);
                        moduleInstanceService.save(moduleInstance);
                    }
                } else {
                    response = new Response("Something went wrong, please retry", null);
                }
            }
        }

        if(!user.getModuleInstanceList().contains(moduleInstance)) {
            user.getModuleInstanceList().add(moduleInstance);
        }
        this.percentageCalc(moduleInstance);
        double overallPercentage = 0.0;

        for(ModuleInstance moduleInstance1 : user.getModuleInstanceList()) {
            overallPercentage += (moduleInstance1.getPercentage() * 16.67) / (double) 100;
        }
        if(overallPercentage > 100.00) {
            user.setProgressPercentage(Double.parseDouble("100.00"));
        }
        else {
            user.setProgressPercentage(Double.parseDouble(df.format(overallPercentage)));
        }

        userService.saveUser(user);
        response = new Response("Done", null);
        return response;
    }

    public ModuleInstance percentageCalc(ModuleInstance moduleInstance) {
        double totQuestions = 0, answeredQuestions = 0;


        Module module = moduleInstance.getModule();
        for(Category category  : module.getCategoryList()) {
            for(Question question :category.getQuestions()) {
                if(question.getAnswerList().size() > 0) {
                    totQuestions += 1;
                }
                if(question.getQuestionList().size() != 0) {
                    totQuestions += question.getQuestionList().size();
                }
            }
        }

        for(Category category2 : moduleInstance.getCategoryCategoryInstanceSortedMap().keySet()) {
            CategoryInstance categoryInstance = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category2);
            for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                answeredQuestions += 1;
            }
        }

        Optional<Question> questionN26 = questionService.findById(26L);
        Optional<Question> questionN13 = questionService.findById(13L);
        Optional<Category> categoryN3 = categoryService.findById(3L);
        Optional<Category> categoryN2 = categoryService.findById(2L);

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN3.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN3.get()).getQuestionAnswerSortedMap().containsKey(questionN26.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN3.get()).getQuestionAnswerSortedMap().get(questionN26.get()).getId() != 69L) {
                    totQuestions -=1;
                }
            }
        }

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN2.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().containsKey(questionN13.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().get(questionN13.get()).getId() == 34L) {
                    totQuestions -=10;
                }
                else {
                    Optional<Question> questionN15 = questionService.findById(15L);
                    Optional<Question> questionN16 = questionService.findById(16L);
                    Optional<Question> questionN17 = questionService.findById(17L);
                    Optional<Question> questionN18 = questionService.findById(18L);
                    Optional<Question> questionN19 = questionService.findById(19L);
                    if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().containsKey(questionN15.get())) {
                        if (moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().get(questionN15.get()).getId() == 37L) {
                            totQuestions -= 1;
                        }
                    }
                    if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().containsKey(questionN16.get())) {
                        if (moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().get(questionN16.get()).getId() == 40L) {
                            totQuestions -= 1;
                        }
                    }
                    if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().containsKey(questionN17.get())) {
                        if (moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().get(questionN17.get()).getId() == 43L) {
                            totQuestions -= 1;
                        }
                    }
                    if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().containsKey(questionN18.get())) {
                        if (moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().get(questionN18.get()).getId() == 46L) {
                            totQuestions -= 1;
                        }
                    }
                    if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().containsKey(questionN19.get())) {
                        if (moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN2.get()).getQuestionAnswerSortedMap().get(questionN19.get()).getId() == 49L) {
                            totQuestions -= 1;
                        }
                    }
                }
            }
        }

        double percentage = (answeredQuestions/ totQuestions) * (double) 100;
        moduleInstance.setPercentage(Double.parseDouble(df.format(percentage)));
        int score = 0;

        if(percentage == Double.valueOf("100")) {
            for(Category category : moduleInstance.getCategoryCategoryInstanceSortedMap().keySet()) {
                CategoryInstance categoryInstance = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category);
                int categoryScore = 0;
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    score += categoryInstance.getQuestionAnswerSortedMap().get(q).getScore();
                    categoryScore += categoryInstance.getQuestionAnswerSortedMap().get(q).getScore();
                }
                categoryInstance.setCategoryScore(categoryScore);
                categoryInstanceService.save(categoryInstance);
            }
        }

        moduleInstance.setScore(score);
        moduleInstance = moduleInstanceService.save(moduleInstance);

        return moduleInstance;
    }

}
