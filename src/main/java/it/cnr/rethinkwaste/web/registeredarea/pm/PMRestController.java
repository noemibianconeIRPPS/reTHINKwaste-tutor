package it.cnr.rethinkwaste.web.registeredarea.pm;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registeredarea/pm")
public class PMRestController {

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
    private ProfileService profileService;

    @Autowired
    private ModuleInstanceService moduleInstanceService;

    @Autowired
    private CategoryInstanceService categoryInstanceService;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/downloadPMreport")
    public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> pmModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(3L)).
                findFirst();
        if(pmModule.isPresent()) {
            ReportCreation reportCreation = new ReportCreation();
            String pathToDownloadFile = reportCreation.createPMreport(pmModule.get(), reportService, categoryService, profileService, ((CustomUserDetails) user).getUser());
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

    @GetMapping(value = "/downloadPMassessment")
    public void downloadPMassessment(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        Optional<ModuleInstance> mncModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(3L)).
                findFirst();
        if(mncModule.isPresent()) {
            AssessmentCreation assessmentCreation = new AssessmentCreation();
            String pathToDownloadFile = assessmentCreation.createAssessment(mncModule.get());
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


        if(user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 3).findFirst().isPresent()) {
            moduleInstance = user.getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 3).findFirst().get();
        }
        else {
            moduleInstance = new ModuleInstance();
            moduleInstance.setModule(moduleService.findById(3L));
            moduleInstance.setCategoryCategoryInstanceSortedMap(new TreeMap<>());
            moduleInstanceService.save(moduleInstance);
        }

        JSONObject jsonObject = new JSONObject(obj);
        String categoryId = jsonObject.get("categoryId").toString();

        if(Long.parseLong(categoryId) != 10) {
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

            moduleInstance = checkPath(moduleInstance);

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
        }
        response = new Response("Done", null);
        return response;
    }

    public ModuleInstance checkPath(ModuleInstance moduleInstance) {

        SortedMap<Category, CategoryInstance> categoryCategoryInstanceNewSortedMap = new TreeMap<Category, CategoryInstance>();
        categoryCategoryInstanceNewSortedMap.putAll(moduleInstance.getCategoryCategoryInstanceSortedMap());
        SortedMap<Category, CategoryInstance> categoryCategoryInstanceNewSortedMap2 = new TreeMap<Category, CategoryInstance>();
        categoryCategoryInstanceNewSortedMap2.putAll(moduleInstance.getCategoryCategoryInstanceSortedMap());

        for(Category c : categoryCategoryInstanceNewSortedMap2.keySet()) {
            if(c.getId().equals(13L)) {
                CategoryInstance categoryInstance = categoryCategoryInstanceNewSortedMap.get(c);
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if(q.getId().equals(49L) && categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(134L)) {
                        Category c14 = categoryService.findById(14L).get();
                        if(categoryCategoryInstanceNewSortedMap.containsKey(c14)) {
                            categoryCategoryInstanceNewSortedMap.remove(c14);
                        }
                    }
                }
            }

            if(c.getId().equals(16L)) {
                CategoryInstance categoryInstance = categoryCategoryInstanceNewSortedMap.get(c);
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if(q.getId().equals(52L) && categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(140L)) {
                        Category c17 = categoryService.findById(17L).get();
                        if(categoryCategoryInstanceNewSortedMap.containsKey(c17)) {
                            categoryCategoryInstanceNewSortedMap.remove(c17);
                        }
                    }
                }
            }

            if(c.getId().equals(18L)) {
                CategoryInstance categoryInstance = categoryCategoryInstanceNewSortedMap.get(c);
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if(q.getId().equals(54L) && categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(144L)) {
                        Category c19 = categoryService.findById(19L).get();
                        Category c20 = categoryService.findById(20L).get();
                        if(categoryCategoryInstanceNewSortedMap.containsKey(c19)) {
                            categoryCategoryInstanceNewSortedMap.remove(c19);
                        }
                        if(categoryCategoryInstanceNewSortedMap.containsKey(c20)) {
                            categoryCategoryInstanceNewSortedMap.remove(c20);
                        }
                    }
                }
            }

            if(c.getId().equals(19L)) {
                CategoryInstance categoryInstance = categoryCategoryInstanceNewSortedMap.get(c);
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if(q.getId().equals(55L) && categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(146L)) {
                        Category c20 = categoryService.findById(20L).get();
                        if(categoryCategoryInstanceNewSortedMap.containsKey(c20)) {
                            categoryCategoryInstanceNewSortedMap.remove(c20);
                        }
                    }
                }
            }


            if(c.getId().equals(23L)) {
                CategoryInstance categoryInstance = categoryCategoryInstanceNewSortedMap.get(c);
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if(q.getId().equals(59L) && categoryInstance.getQuestionAnswerSortedMap().get(q).getId().equals(154L)) {
                        Category c24 = categoryService.findById(24L).get();
                        if(categoryCategoryInstanceNewSortedMap.containsKey(c24)) {
                            categoryCategoryInstanceNewSortedMap.remove(c24);
                        }
                    }
                }
            }
        }
        moduleInstance.setCategoryCategoryInstanceSortedMap(categoryCategoryInstanceNewSortedMap);
        moduleInstance = moduleInstanceService.save(moduleInstance);

        return moduleInstance;
    }

    public ModuleInstance percentageCalc(ModuleInstance moduleInstance) {
        double totQuestions = 0, answeredQuestions = 0;


        Module module = moduleInstance.getModule();
        for(Category category  : module.getCategoryList()) {
            if(!category.getId().equals(10L)) {
                for (Question question : category.getQuestions()) {
                    if (question.getAnswerList().size() > 0) {
                        totQuestions += 1;
                    }
                }
            }
        }

        for(Category category2 : moduleInstance.getCategoryCategoryInstanceSortedMap().keySet()) {
            if(!category2.getId().equals(10L)) {
                CategoryInstance categoryInstance = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category2);
                for (Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if (q.getAnswerList().size() > 0) {
                        answeredQuestions += 1;
                    }
                }
            }
        }

        Optional<Question> questionN49 = questionService.findById(49L);
        Optional<Question> questionN52 = questionService.findById(52L);
        Optional<Question> questionN54 = questionService.findById(54L);
        Optional<Question> questionN55 = questionService.findById(55L);
        Optional<Question> questionN59 = questionService.findById(59L);

        Optional<Category> categoryN13 = categoryService.findById(13L);
        Optional<Category> categoryN16 = categoryService.findById(16L);
        Optional<Category> categoryN18 = categoryService.findById(18L);
        Optional<Category> categoryN19 = categoryService.findById(19L);
        Optional<Category> categoryN23 = categoryService.findById(23L);

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN13.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN13.get()).getQuestionAnswerSortedMap().containsKey(questionN49.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN13.get()).getQuestionAnswerSortedMap().get(questionN49.get()).getId() == 134L) {
                    totQuestions -=1;
                }
            }
        }

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN16.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN16.get()).getQuestionAnswerSortedMap().containsKey(questionN52.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN16.get()).getQuestionAnswerSortedMap().get(questionN52.get()).getId() == 140L) {
                    totQuestions -=1;
                }
            }
        }

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN18.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN18.get()).getQuestionAnswerSortedMap().containsKey(questionN54.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN18.get()).getQuestionAnswerSortedMap().get(questionN54.get()).getId() == 144L) {
                    totQuestions -=2;
                }
            }
        }

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN19.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN19.get()).getQuestionAnswerSortedMap().containsKey(questionN55.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN19.get()).getQuestionAnswerSortedMap().get(questionN55.get()).getId() == 146L) {
                    totQuestions -=1;
                }
            }
        }

        if(moduleInstance.getCategoryCategoryInstanceSortedMap().containsKey(categoryN23.get())) {
            if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN23.get()).getQuestionAnswerSortedMap().containsKey(questionN59.get())) {
                if(moduleInstance.getCategoryCategoryInstanceSortedMap().get(categoryN23.get()).getQuestionAnswerSortedMap().get(questionN59.get()).getId() == 154L) {
                    totQuestions -=1;
                }
            }
        }

        double percentage = (answeredQuestions/ totQuestions) * (double) 100;
        moduleInstance.setPercentage(Double.parseDouble(df.format(percentage)));
        int score = 0;
        int amber = 0;
        int red = 0;

        /* GLI SCORE DELLE RISPOSTE DEL MODULO 3 SONO:
        * - 2 RISPOSTA SENZA EFFETTI
        * - 1 RISPOSTA AMBER
        * - 0 RISPOSTA RED
        *  */
        if(percentage == Double.valueOf("100")) {
            for(Category category : moduleInstance.getCategoryCategoryInstanceSortedMap().keySet()) {
                CategoryInstance categoryInstance = moduleInstance.getCategoryCategoryInstanceSortedMap().get(category);
                for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {
                    if(categoryInstance.getQuestionAnswerSortedMap().get(q).getScore() == 1) {
                        amber++;
                    }
                    if(categoryInstance.getQuestionAnswerSortedMap().get(q).getScore() == 0) {
                        red++;
                    }
                }
            }
            if(red >= 1) {
                score = 1;
            }
            else if(amber >= 1 && red == 0) {
                score = 2;
            }
            else {
                score = 3;
            }
        }

        moduleInstance.setScore(score);
        moduleInstance = moduleInstanceService.save(moduleInstance);

        return moduleInstance;
    }

}
