package it.cnr.rethinkwaste.web.registeredarea.arm;

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
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@RestController
@RequestMapping("/registeredarea/arm")
public class ARMRestController {

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
    private CategoryInstanceForMCService categoryInstanceForMCService;

    @Autowired
    private AnswerListService answerListService;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/downloadARMreport")
    public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> armModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(2L)).
                findFirst();
        if(armModule.isPresent()) {
            ReportCreation reportCreation = new ReportCreation();
            String pathToDownloadFile = reportCreation.createARMreport(armModule.get(), reportService, ((CustomUserDetails) user).getUser());
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

    @GetMapping(value = "/downloadARMassessment")
    public void downloadARMassessment(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> armModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(2L)).
                findFirst();
        if(armModule.isPresent()) {
            AssessmentCreation assessmentCreation = new AssessmentCreation();
            String pathToDownloadFile = assessmentCreation.createARMAssessment(armModule.get());
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

        if(user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 2).findFirst().isPresent()) {
            moduleInstance = user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 2).findFirst().get();
        }
        else {
            moduleInstance = new ModuleInstance();
            moduleInstance.setModule(moduleService.findById(2L));
            moduleInstance.setCategoryCategoryInstanceForMCSortedMap(new TreeMap<>());
            moduleInstanceService.save(moduleInstance);
            user.getModuleInstanceList().add(moduleInstance);
        }

        JSONObject jsonObject = new JSONObject(obj);
        String categoryId = jsonObject.get("categoryId").toString();
        Optional<Category> categoryOptional = categoryService.findById(Long.parseLong(categoryId));
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            CategoryInstanceForMC old = moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category);
            CategoryInstanceForMC categoryInstance = new CategoryInstanceForMC();
            categoryInstance.setQuestionAnswerSortedMap(new TreeMap<>());
            categoryInstanceForMCService.save(categoryInstance);
            moduleInstance.getCategoryCategoryInstanceForMCSortedMap().put(category, categoryInstance);
            if(old != null) {
                categoryInstanceForMCService.delete(old);
            }

            JSONArray arr = jsonObject.getJSONArray("infos");
            for (int i = 0; i < arr.length(); i++) {
                String questionId = arr.getJSONObject(i).getString("questionId");
                Optional<Question> questionOptional = questionService.findById(Long.parseLong(questionId));
                if (questionOptional.isPresent()) {
                    if (questionOptional.get().isMultipleChoice()) {
                        String[] answerIds = arr.getJSONObject(i).getJSONArray("answerId").toString().split(",");
                        AnswerList answerList = new AnswerList();
                        if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category).getQuestionAnswerSortedMap().get(questionOptional.get()) != null) {
                            answerList = moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category).getQuestionAnswerSortedMap().get(questionOptional.get());
                        }
                        answerList.setAnswers(new ArrayList<>());
                        answerListService.save(answerList);
                        for (int j = 0; j < answerIds.length; j++) {
                            String answerId = answerIds[j].replace("[", "");
                            answerId = answerId.replace("]", "");
                            answerId = answerId.replaceAll("\"", "");
                            Answer answer = answerService.findById(Long.parseLong(answerId)).get();
                            answerList.getAnswers().add(answer);
                        }
                        answerListService.save(answerList);
                        if (moduleInstance.getCategoryCategoryInstanceForMCSortedMap().containsKey(category)) {
                            Map<Question, AnswerList> map = moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category).getQuestionAnswerSortedMap();
                            map.put(questionOptional.get(), answerList);
                            moduleInstanceService.save(moduleInstance);
                        }
                        else {
                            CategoryInstanceForMC categoryInstanceForMC = new CategoryInstanceForMC();
                            categoryInstanceForMC.setQuestionAnswerSortedMap(new TreeMap<>());
                            categoryInstanceForMC.getQuestionAnswerSortedMap().put(questionOptional.get(), answerList);
                            categoryInstanceForMC = categoryInstanceForMCService.save(categoryInstanceForMC);
                            moduleInstance.getCategoryCategoryInstanceForMCSortedMap().put(category, categoryInstanceForMC);
                            moduleInstanceService.save(moduleInstance);
                        }
                    } else {
                        String answerId = arr.getJSONObject(i).getString("answerId");
                        Optional<Answer> answerOptional = answerService.findById(Long.parseLong(answerId));
                        if (moduleInstance.getCategoryCategoryInstanceForMCSortedMap().containsKey(category)) {
                            Map<Question, AnswerList> map = moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category).getQuestionAnswerSortedMap();

                            if (map.get(questionOptional.get()) == null) {
                                AnswerList answerList = new AnswerList();
                                answerList.setAnswers(new ArrayList<>());
                                answerList = answerListService.save(answerList);

                                answerList.getAnswers().add(answerOptional.get());
                                answerListService.save(answerList);
                                map.put(questionOptional.get(), answerList);
                                moduleInstanceService.save(moduleInstance);
                            } else {
                                AnswerList answerList = map.get(questionOptional.get());
                                answerList.setAnswers(new ArrayList<>());
                                answerList = answerListService.save(answerList);

                                answerList.getAnswers().add(answerOptional.get());
                                answerListService.save(answerList);
                                map.put(questionOptional.get(), answerList);
                                moduleInstanceService.save(moduleInstance);

                            }
                        } else {
                            CategoryInstanceForMC categoryInstanceForMC = new CategoryInstanceForMC();
                            categoryInstanceForMC.setQuestionAnswerSortedMap(new TreeMap<>());
                            AnswerList answerList = new AnswerList();
                            answerList.getAnswers().add(answerOptional.get());
                            answerListService.save(answerList);
                            categoryInstanceForMC.getQuestionAnswerSortedMap().put(questionOptional.get(), answerList);
                            categoryInstanceForMC = categoryInstanceForMCService.save(categoryInstanceForMC);
                            TreeMap<Category, CategoryInstanceForMC> categoryCategoryInstanceForMCMap = new TreeMap<>();
                            categoryCategoryInstanceForMCMap.put(category, categoryInstanceForMC);
                            moduleInstance.setCategoryCategoryInstanceForMCSortedMap(categoryCategoryInstanceForMCMap);
                            moduleInstanceService.save(moduleInstance);
                        }
                    }
                }
                else {
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

        for(Category category2 : moduleInstance.getCategoryCategoryInstanceForMCSortedMap().keySet()) {
            CategoryInstanceForMC categoryInstance = moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category2);
            for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                answeredQuestions += 1;
            }
        }

        Optional<Question> questionN83 = questionService.findById(83L);
        Optional<Category> categoryN29 = categoryService.findById(29L);
        Optional<Question> questionN85 = questionService.findById(85L);

        if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().containsKey(categoryN29.get())) {
            if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().containsKey(questionN83.get())) {
                if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().get(questionN83.get()).getAnswers().stream().filter(a -> !a.getId().equals(223L)).findFirst().isPresent()) {
                    totQuestions -=2;
                }
            }
            if(!moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().containsKey(questionN85.get())) {
                totQuestions -=2;
            }
            if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().containsKey(questionN85.get())) {
                if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().get(questionN85.get()).getAnswers().stream().filter(a -> !a.getId().equals(230L)).findFirst().isPresent()) {
                    totQuestions -=1;
                }
            }
            if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().containsKey(questionN85.get())) {
                if(moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(categoryN29.get()).getQuestionAnswerSortedMap().get(questionN85.get()).getAnswers().stream().filter(a -> !a.getId().equals(232L)).findFirst().isPresent()) {
                    totQuestions -=1;
                }
            }
        }

        double percentage = (answeredQuestions/ totQuestions) * (double) 100;
        moduleInstance.setPercentage(Double.parseDouble(df.format(percentage)));
        int score = 0;

        if(percentage == Double.valueOf("100")) {
            for(Category category : moduleInstance.getCategoryCategoryInstanceForMCSortedMap().keySet()) {
                CategoryInstanceForMC categoryInstance = moduleInstance.getCategoryCategoryInstanceForMCSortedMap().get(category);
                int categoryScore = 0;
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    for(Answer a : categoryInstance.getQuestionAnswerSortedMap().get(q).getAnswers()) {
                        score += a.getScore();
                        categoryScore += a.getScore();
                    }
                }
                categoryInstance.setCategoryScore(categoryScore);
                categoryInstanceForMCService.save(categoryInstance);
            }
        }

        moduleInstance.setScore(score);
        moduleInstance = moduleInstanceService.save(moduleInstance);

        return moduleInstance;
    }

}
