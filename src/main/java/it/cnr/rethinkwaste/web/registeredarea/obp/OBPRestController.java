package it.cnr.rethinkwaste.web.registeredarea.obp;

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
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("/registeredarea/obp")
public class OBPRestController {

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

    @GetMapping(value = "/downloadOBPreport")
    public void downloadOBPreport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> pmModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(6L)).
                findFirst();
        if(pmModule.isPresent()) {
            ReportCreation reportCreation = new ReportCreation();
            String pathToDownloadFile = reportCreation.createOBPReport(pmModule.get(), reportService, ((CustomUserDetails) user).getUser());
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

    @GetMapping(value = "/downloadOBPassessment")
    public void downloadOBPassessment(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> obpModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(6L)).
                findFirst();
        if(obpModule.isPresent()) {
            AssessmentCreation assessmentCreation = new AssessmentCreation();
            String pathToDownloadFile = assessmentCreation.createAssessment(obpModule.get());
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


        if(user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 6).findFirst().isPresent()) {
            moduleInstance = user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 6).findFirst().get();
        }
        else {
            moduleInstance = new ModuleInstance();
            moduleInstance.setModule(moduleService.findById(6L));
            moduleInstance.setCategoryCategoryInstanceSortedMap(new TreeMap<>());
            moduleInstanceService.save(moduleInstance);
        }

        JSONObject jsonObject = new JSONObject(obj);
        String categoryId = jsonObject.get("categoryId").toString();

        Optional<Category> categoryOptional = categoryService.findById(Long.parseLong(categoryId));
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if (moduleInstance.getCategoryCategoryInstanceSortedMap().get(category) != null) {
                moduleInstance.getCategoryCategoryInstanceSortedMap().get(category).setQuestionAnswerSortedMap(new TreeMap<>());
            } else {
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
                    if (moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(category)) {
                        Map<Question, Answer> map = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category).getQuestionAnswerSortedMap();
                        map.put(questionOptional.get(), answerOptional.get());
                        moduleInstanceService.save(moduleInstance);
                    } else {
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

        if (!user.getModuleInstanceList().contains(moduleInstance)) {
            user.getModuleInstanceList().add(moduleInstance);
        }

        this.percentageCalc(moduleInstance);
        double overallPercentage = 0.0;

        for (ModuleInstance moduleInstance1 : user.getModuleInstanceList()) {
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
            for (Question question : category.getQuestions()) {
                if (question.getAnswerList().size() > 0) {
                    totQuestions += 1;
                }
            }
        }

        for(Category category2 : moduleInstance.getCategoryCategoryInstanceSortedMap().keySet()) {
            CategoryInstance categoryInstance = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category2);
            for (Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                if (q.getAnswerList().size() > 0) {
                    answeredQuestions += 1;
                }
            }
        }

        double percentage = (answeredQuestions/ totQuestions) * (double) 100;
        moduleInstance.setPercentage(Double.parseDouble(df.format(percentage)));
        double score = 0;

        if(percentage == Double.valueOf("100")) {
            for(Category category : moduleInstance.getCategoryCategoryInstanceSortedMap().keySet()) {
                CategoryInstance categoryInstance = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category);
                double categoryScore = 0;
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {

                    if(!q.getId().equals(63L) && !q.getId().equals(66L)) {
                        score += (categoryInstance.getQuestionAnswerSortedMap().get(q).getScore() * q.getWeight()) / 100;
                        categoryScore += (categoryInstance.getQuestionAnswerSortedMap().get(q).getScore() * q.getWeight()) / 100;
                    }
                    else if(q.getId().equals(63L)) {
                        if(categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(164L)) {
                            Question q1 = questionService.findById(62L).get();
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(160L)){
                                score += 3 * q1.getWeight() / 100;
                                categoryScore += 3 * q1.getWeight() / 100;
                            }
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(161L)){
                                score += 2.5 * q1.getWeight() / 100;
                                categoryScore += 2.5 * q1.getWeight() / 100;
                            }
                        }

                        if(categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(165L)) {
                            Question q1 = questionService.findById(62L).get();
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(160L)){
                                score += 5.5 * q1.getWeight() / 100;
                                categoryScore += 5.5 * q1.getWeight() / 100;
                            }
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(161L)){
                                score += 5 * q1.getWeight() / 100;
                                categoryScore += 5 * q1.getWeight() / 100;
                            }
                        }
                    }

                    else if(q.getId().equals(66L)) {
                        if(categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(173L)) {
                            Question q1 = questionService.findById(65L).get();
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(169L)){
                                score += 3 * q1.getWeight() / 100;
                                categoryScore += 3 * q1.getWeight() / 100;
                            }
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(170L)){
                                score += 2.5 * q1.getWeight() / 100;
                                categoryScore += 2.5 * q1.getWeight() / 100;
                            }
                        }
                        if(categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(174L)) {
                            Question q1 = questionService.findById(65L).get();
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(169L)){
                                score += 5.5 * q1.getWeight() / 100;
                                categoryScore += 5.5 * q1.getWeight() / 100;
                            }
                            if(categoryInstance.getQuestionAnswerSortedMap().get(q1).getId().equals(170L)){
                                score += 5 * q1.getWeight() / 100;
                                categoryScore += 5 * q1.getWeight() / 100;
                            }
                        }
                    }
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
