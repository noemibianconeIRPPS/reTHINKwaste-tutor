package it.cnr.rethinkwaste.asposeUtils;

import com.aspose.pdf.*;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.model.assessment.*;
import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.service.assessment.CategoryService;
import it.cnr.rethinkwaste.service.assessment.ProfileService;
import it.cnr.rethinkwaste.service.assessment.ReportService;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReportCreation {

    private static String serverPath = "/opt/tomcat/webapps/temp/";//"/opt/tomcat/webapps/temp/";      "C:\\Users\\noemi\\Desktop\\temp\\"

    public static String licenseServerPath = "/opt/tomcat/webapps/temp/Aspose.Total.Java.lic"; //"/opt/tomcat/webapps/temp/Aspose.Total.Java.lic";      "C:\\Users\\noemi\\Desktop\\temp\\Aspose.Total.Java.lic";

    public static String fontPath =  "/opt/tomcat/webapps/temp/Arial.ttf"; //"/opt/tomcat/webapps/temp/Arial.ttf"        "C:\\Users\\noemi\\Desktop\\temp\\Arial.ttf"

    public static String headerPath = "/opt/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + "pdf_header.png";
    //System.getProperty("user.dir") + "/src/main/resources/static/img/" + "pdf_header.png";
    //"/opt/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + "pdf_header.png";

    public String language = LocaleContextHolder.getLocale().getLanguage();

    public Map<String, String> nameAndSurnameTranslationMap = new HashMap<String, String>();
    public Map<String, String> organizationTranslationMap = new HashMap<String, String>();
    public Map<String, String> selfAssessmentProgressTranslationMap = new HashMap<String, String>();
    public Map<String, String> moduleTranslationMap = new HashMap<String, String>();
    public Map<String, String> moduleGuidanceReportTranslationMap = new HashMap<String, String>();
    public Map<String, String> countryTranslationMap = new HashMap<String, String>();
    public Map<String, String> guidelinesTranslationMap = new HashMap<String, String>();

    public void init() {
        nameAndSurnameTranslationMap.put("EN", "Name and surname");
        nameAndSurnameTranslationMap.put("IT", "Nome e cognome");
        organizationTranslationMap.put("EN", "Organization");
        organizationTranslationMap.put("IT", "Organizzazione");
        selfAssessmentProgressTranslationMap.put("EN", "Self-assessment progress");
        selfAssessmentProgressTranslationMap.put("IT", "Progresso dell'autovalutazione");
        moduleTranslationMap.put("EN", "Module");
        moduleTranslationMap.put("IT", "Modulo");
        moduleGuidanceReportTranslationMap.put("EN", "Module guidance report");
        moduleGuidanceReportTranslationMap.put("IT", "Report delle linee guida del modulo");
        countryTranslationMap.put("EN", "Country");
        countryTranslationMap.put("IT", "Paese");
        guidelinesTranslationMap.put("EN", "Guidelines");
        guidelinesTranslationMap.put("IT", "Linee guida");
    }

    public String createFullreport(CategoryService categoryService, ReportService reportService, User_ user) throws Exception {
        this.init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText("Full report");
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + " ";
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + " (" + user.getProgressPercentage() + "%)");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment module1Title = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\"> \""+ guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) +" " +  user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(1L)).
                findFirst().get().getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(module1Title);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> ttrMap = user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(1L)).
                findFirst().get().getCategoryCategoryInstanceSortedMap();
        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : ttrMap.keySet()) {
            Optional<Report> reportOpt1 = reportService.findById(user.getModuleInstanceList().stream().
                    filter(m -> m.getModule().getId().equals(1L)).
                    findFirst().get().getModule().getId());
            if (reportOpt1.isPresent()) {
                Report report = reportOpt1.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                    htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (ttrMap.get(category).getCategoryScore() >= subprofile.getLowerBoundScore() &&
                            ttrMap.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {
                        htmlSubProfileNameTextFragment += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }

        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment module2Title = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\"> \""+ guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) +" " +  user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(2L)).
                findFirst().get().getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(module2Title);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstanceForMC> armMap = user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(2L)).
                findFirst().get().getCategoryCategoryInstanceForMCSortedMap();
        String htmlSubProfileNameTextFragment2 = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : armMap.keySet()) {
            Optional<Report> reportOpt = reportService.findById(user.getModuleInstanceList().stream().
                    filter(m -> m.getModule().getId().equals(2L)).
                    findFirst().get().getModule().getId());
            if (reportOpt.isPresent()) {
                Report report = reportOpt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment2 += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";

                if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                    htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (armMap.get(category).getCategoryScore() >= subprofile.getLowerBoundScore() &&
                            armMap.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {
                        htmlSubProfileNameTextFragment2 += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment2 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/>";
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment2 += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment2));
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment module3Title = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\"> \""+ guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) +" " +  user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(3L)).
                findFirst().get().getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(module3Title);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> pmMap = user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(3L)).
                findFirst().get().getCategoryCategoryInstanceSortedMap();
        String htmlSubProfileNameTextFragment3 = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        Optional<Report> reportOpt3 = reportService.findById(user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(3L)).
                findFirst().get().getModule().getId());
        if (reportOpt3.isPresent()) {
            Report report = reportOpt3.get();
            for (Profile profile : report.getProfileList()) {
                if(profile.getId().equals(10L)) {
                    if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                        htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                    }
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    String[] questions = subprofile.getModule3questions().split(";");
                    String[] answers = subprofile.getModule3answers().split(";");

                    if(questions.length == 1) {
                        Question question = pmMap.get(profile.getCategory()).getQuestionAnswerSortedMap().entrySet().stream()
                                .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[0])))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        if(question != null) {
                            Answer answer = pmMap.get(profile.getCategory()).getQuestionAnswerSortedMap().get(question);
                            if(answer.getId().equals(Long.parseLong(answers[0]))) {
                                htmlSubProfileNameTextFragment3 += profile.getCategory().getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                                if(subprofile.getColor().equals("RED")) {
                                    htmlSubProfileNameTextFragment3 += "YOUR LEVEL: Early pioneers <br/>";
                                }
                                if(subprofile.getColor().equals("YELLOW")) {
                                    htmlSubProfileNameTextFragment3 += "YOUR LEVEL: Looking forward <br/>";
                                }
                                htmlSubProfileNameTextFragment3 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                            }
                        }
                    }
                    if(questions.length == 2) {
                        Question question1 = pmMap.get(profile.getCategory()).getQuestionAnswerSortedMap().entrySet().stream()
                                .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[0])))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        Category category1 = categoryService.findById(profile.getCategory().getId()+1).get();
                        if(pmMap.get(category1) != null) {
                            Question question2 = pmMap.get(category1).getQuestionAnswerSortedMap().entrySet().stream()
                                    .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[1])))
                                    .map(Map.Entry::getKey)
                                    .findFirst()
                                    .orElse(null);
                            if (question1 != null && question2 != null) {
                                Answer answer1 = pmMap.get(profile.getCategory()).getQuestionAnswerSortedMap().get(question1);
                                Answer answer2 = pmMap.get(category1).getQuestionAnswerSortedMap().get(question2);
                                if (answer1.getId().equals(Long.parseLong(answers[0])) && answer2.getId().equals(Long.parseLong(answers[1]))) {
                                    htmlSubProfileNameTextFragment3 += profile.getCategory().getTranslations().get(language.toUpperCase()) + " & " + category1.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                                    if(subprofile.getColor().equals("RED")) {
                                        htmlSubProfileNameTextFragment3 += "YOUR LEVEL: Early pioneers <br/>";
                                    }
                                    if(subprofile.getColor().equals("YELLOW")) {
                                        htmlSubProfileNameTextFragment3 += "YOUR LEVEL: Looking forward <br/>";
                                    }
                                    htmlSubProfileNameTextFragment3 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                                }
                            }
                        }
                    }
                    if(questions.length == 3) {
                        Question question1 = pmMap.get(profile.getCategory()).getQuestionAnswerSortedMap().entrySet().stream()
                                .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[0])))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        Category category1 = categoryService.findById(profile.getCategory().getId()+1).get();
                        Category category2 = categoryService.findById(profile.getCategory().getId()+2).get();
                        if(pmMap.get(category1) != null && pmMap.get(category2) != null) {
                            Question question2 = pmMap.get(category1).getQuestionAnswerSortedMap().entrySet().stream()
                                    .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[1])))
                                    .map(Map.Entry::getKey)
                                    .findFirst()
                                    .orElse(null);
                            Question question3 = pmMap.get(category2).getQuestionAnswerSortedMap().entrySet().stream()
                                    .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[2])))
                                    .map(Map.Entry::getKey)
                                    .findFirst()
                                    .orElse(null);
                            if (question1 != null && question2 != null && question3 != null) {
                                Answer answer1 = pmMap.get(profile.getCategory()).getQuestionAnswerSortedMap().get(question1);
                                Answer answer2 = pmMap.get(category1).getQuestionAnswerSortedMap().get(question2);
                                Answer answer3 = pmMap.get(category2).getQuestionAnswerSortedMap().get(question3);
                                if (answer1.getId().equals(Long.parseLong(answers[0])) && answer2.getId().equals(Long.parseLong(answers[1]))
                                        && answer3.getId().equals(Long.parseLong(answers[2]))) {

                                    htmlSubProfileNameTextFragment3 += profile.getCategory().getTranslations().get(language.toUpperCase()) + " & " + category1.getTranslations().get(language.toUpperCase()) + " & " + category2.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                                    if(subprofile.getColor().equals("RED")) {
                                        htmlSubProfileNameTextFragment3 += "YOUR LEVEL: Early pioneers <br/>";
                                    }
                                    if(subprofile.getColor().equals("YELLOW")) {
                                        htmlSubProfileNameTextFragment3 += "YOUR LEVEL: Looking forward <br/>";
                                    }
                                    htmlSubProfileNameTextFragment3 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                                }
                            }
                        }
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment3 += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment3));
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment module4Title = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\"> \""+ guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) +" " +  user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(4L)).
                findFirst().get().getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(module4Title);
        page.getParagraphs().add(emptyTextFragment);

        String htmlSubProfileNameTextFragment4 = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        Optional<Report> report4Opt = reportService.findById(user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(4L)).
                findFirst().get().getModule().getId());
        if (report4Opt.isPresent()) {
            Report report = report4Opt.get();
            Profile profile = report.getProfileList().get(0);


            for (Subprofile subprofile : profile.getSubprofiles()) {
                if (user.getModuleInstanceList().stream().
                        filter(m -> m.getModule().getId().equals(4L)).
                        findFirst().get().getScore() >= subprofile.getLowerBoundScore() &&
                        user.getModuleInstanceList().stream().
                                filter(m -> m.getModule().getId().equals(4L)).
                                findFirst().get().getScore() <= subprofile.getUpperBoundScore()) {
                    htmlSubProfileNameTextFragment4 += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                    htmlSubProfileNameTextFragment4 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                }
            }
        }

        htmlSubProfileNameTextFragment4 += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment4));
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment module5Title = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\"> \""+ guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) +" " +  user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(5L)).
                findFirst().get().getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(module5Title);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> mncMap = user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(5L)).
                findFirst().get().getCategoryCategoryInstanceSortedMap();

        String htmlSubProfileNameTextFragment5 = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : mncMap.keySet()) {
            Optional<Report> report5Opt = reportService.findById(user.getModuleInstanceList().stream().
                    filter(m -> m.getModule().getId().equals(5L)).
                    findFirst().get().getModule().getId());
            if (report5Opt.isPresent()) {
                Report report = report5Opt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment5 += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (mncMap.get(category).getCategoryScore() > subprofile.getLowerBoundScore() &&
                            mncMap.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {

                        if(subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment() != null && !subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment().isEmpty()) {
                            htmlSubProfileNameTextFragment5 += "DESCRIPTION: " + subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment() + "<br/><br/>";
                        }


                        htmlSubProfileNameTextFragment5 += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment5 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment5 += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment5));
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment module6Title = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\"> \""+ guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) +" " +  user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(6L)).
                findFirst().get().getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(module6Title);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> obpMap = user.getModuleInstanceList().stream().
                filter(m -> m.getModule().getId().equals(6L)).
                findFirst().get().getCategoryCategoryInstanceSortedMap();
        String htmlSubProfileNameTextFragment6 = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : obpMap.keySet()) {
            Optional<Report> report6Opt = reportService.findById(user.getModuleInstanceList().stream().
                    filter(m -> m.getModule().getId().equals(6L)).
                    findFirst().get().getModule().getId());
            if (report6Opt.isPresent()) {
                Report report = report6Opt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                    htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (obpMap.get(category).getCategoryScore() > subprofile.getLowerBoundScore() &&
                            obpMap.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {
                        htmlSubProfileNameTextFragment6 += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment6 += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }

        htmlSubProfileNameTextFragment6 += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment6));

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + "FullReport.pdf");
        return serverPath + "FullReport.pdf";

    }

    public String createARMreport(ModuleInstance moduleInstance, ReportService reportService, User_ user) throws Exception {
        init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        Module moduleArm = moduleInstance.getModule();
        String moduleName = moduleArm.getTranslations().get(language.toUpperCase());

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleGuidanceReportTranslationMap.get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + " ";
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + " (" + user.getProgressPercentage() + "%)");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        Row moduleGuidanceReportRow = table.getRows().add();
        TextFragment moduleGuidanceReport = new TextFragment((moduleTranslationMap.get(language.toUpperCase())));
        moduleGuidanceReport.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReport.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReport.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReport);
        moduleGuidanceReport.getTextState().setFontStyle(FontStyles.Bold);
        TextFragment moduleGuidanceReportValue = new TextFragment(moduleInstance.getModule().getTranslations().get(language.toUpperCase()));
        moduleGuidanceReportValue.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReportValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReportValue.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReportValue);

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment moduleTitle = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\">\"" + guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) + " " + moduleInstance.getModule().getTranslations().get(language.toUpperCase()) + "\" </p>");
        page.getParagraphs().add(moduleTitle);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstanceForMC> map = moduleInstance.getCategoryCategoryInstanceForMCSortedMap();
        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : map.keySet()) {
            Optional<Report> reportOpt = reportService.findById(moduleInstance.getModule().getId());
            if (reportOpt.isPresent()) {
                Report report = reportOpt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";

                if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                    htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (map.get(category).getCategoryScore() >= subprofile.getLowerBoundScore() &&
                            map.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {
                        htmlSubProfileNameTextFragment += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleName + " Report.pdf");
        return serverPath + moduleName + " Report.pdf";
    }

    public String createReport(ModuleInstance moduleInstance, ReportService reportService, User_ user) throws Exception {
        init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        Module moduleTtr = moduleInstance.getModule();
        String moduleName = moduleTtr.getTranslations().get(language.toUpperCase());

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleGuidanceReportTranslationMap.get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + System.lineSeparator();
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + "" + user.getProgressPercentage() + "%");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        Row moduleGuidanceReportRow = table.getRows().add();
        TextFragment moduleGuidanceReport = new TextFragment((moduleTranslationMap.get(language.toUpperCase())));
        moduleGuidanceReport.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReport.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReport.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReport);
        moduleGuidanceReport.getTextState().setFontStyle(FontStyles.Bold);
        TextFragment moduleGuidanceReportValue = new TextFragment(moduleInstance.getModule().getTranslations().get(language.toUpperCase()));
        moduleGuidanceReportValue.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReportValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReportValue.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReportValue);

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment moduleTitle = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\">\"" + guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) + " " + moduleInstance.getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(moduleTitle);
        page.getParagraphs().add(emptyTextFragment);
        Map<Category, CategoryInstance> map = moduleInstance.getCategoryCategoryInstanceSortedMap();

        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : map.keySet()) {
            Optional<Report> reportOpt = reportService.findById(moduleInstance.getModule().getId());
            if (reportOpt.isPresent()) {
                Report report = reportOpt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                    htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (map.get(category).getCategoryScore() >= subprofile.getLowerBoundScore() &&
                            map.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {
                        htmlSubProfileNameTextFragment += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleName + " Report.pdf");
        return serverPath + moduleName + " Report.pdf";
    }

    public String createIGPPReport(ModuleInstance moduleInstance, ReportService reportService, User_ user) throws Exception {
        init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        Module moduleIgpp = moduleInstance.getModule();
        String moduleName = moduleIgpp.getTranslations().get(language.toUpperCase());

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleGuidanceReportTranslationMap.get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + " ";
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + " (" + user.getProgressPercentage() + "%)");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        Row moduleGuidanceReportRow = table.getRows().add();
        TextFragment moduleGuidanceReport = new TextFragment((moduleTranslationMap.get(language.toUpperCase())));
        moduleGuidanceReport.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReport.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReport.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReport);
        moduleGuidanceReport.getTextState().setFontStyle(FontStyles.Bold);
        TextFragment moduleGuidanceReportValue = new TextFragment(moduleInstance.getModule().getTranslations().get(language.toUpperCase()));
        moduleGuidanceReportValue.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReportValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReportValue.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReportValue);

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment moduleTitle = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\">\"" + guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) + " " + moduleInstance.getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(moduleTitle);
        page.getParagraphs().add(emptyTextFragment);

        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        Optional<Report> reportOpt = reportService.findById(moduleInstance.getModule().getId());
        if (reportOpt.isPresent()) {
            Report report = reportOpt.get();
            Profile profile = report.getProfileList().get(0);


            for (Subprofile subprofile : profile.getSubprofiles()) {
                if (moduleInstance.getScore() >= subprofile.getLowerBoundScore() &&
                        moduleInstance.getScore() <= subprofile.getUpperBoundScore()) {
                    htmlSubProfileNameTextFragment += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                    htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                }
            }
        }

        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));
        page.getParagraphs().add(emptyTextFragment);
        page.getParagraphs().add(emptyTextFragment);

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleName + " Report.pdf");
        return serverPath + moduleName + " Report.pdf";
    }

    public String createMNCreport(ModuleInstance moduleInstance, ReportService reportService, User_ user) throws Exception {
        init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        Module moduleMnc = moduleInstance.getModule();
        String moduleName = moduleMnc.getTranslations().get(language.toUpperCase());

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleGuidanceReportTranslationMap.get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + " ";
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + " (" + user.getProgressPercentage() + "%)");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        Row moduleGuidanceReportRow = table.getRows().add();
        TextFragment moduleGuidanceReport = new TextFragment((moduleTranslationMap.get(language.toUpperCase())));
        moduleGuidanceReport.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReport.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReport.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReport);
        moduleGuidanceReport.getTextState().setFontStyle(FontStyles.Bold);
        TextFragment moduleGuidanceReportValue = new TextFragment(moduleInstance.getModule().getTranslations().get(language.toUpperCase()) );
        moduleGuidanceReportValue.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReportValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReportValue.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReportValue);

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment moduleTitle = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\">\"" + guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) + " " + moduleInstance.getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(moduleTitle);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> map = moduleInstance.getCategoryCategoryInstanceSortedMap();

        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : map.keySet()) {
            Optional<Report> reportOpt = reportService.findById(moduleInstance.getModule().getId());
            if (reportOpt.isPresent()) {
                Report report = reportOpt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (map.get(category).getCategoryScore() > subprofile.getLowerBoundScore() &&
                            map.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {

                        if(subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment() != null && !subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment().isEmpty()) {
                            htmlSubProfileNameTextFragment += "DESCRIPTION: " + subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment() + "<br/><br/>";
                        }


                        htmlSubProfileNameTextFragment += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleName + " Report.pdf");
        return serverPath + moduleName + " Report.pdf";
    }

    public String createPMreport(ModuleInstance moduleInstance, ReportService reportService, CategoryService categoryService,
                                 ProfileService profileService, User_ user) throws Exception {
        init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        Module modulePm = moduleInstance.getModule();
        String moduleName = modulePm.getTranslations().get(language.toUpperCase());

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleGuidanceReportTranslationMap.get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + " ";
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + " (" + user.getProgressPercentage() + "%)");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        Row moduleGuidanceReportRow = table.getRows().add();
        TextFragment moduleGuidanceReport = new TextFragment((moduleTranslationMap.get(language.toUpperCase())));
        moduleGuidanceReport.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReport.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReport.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReport);
        moduleGuidanceReport.getTextState().setFontStyle(FontStyles.Bold);
        TextFragment moduleGuidanceReportValue = new TextFragment(moduleInstance.getModule().getTranslations().get(language.toUpperCase()));
        moduleGuidanceReportValue.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReportValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReportValue.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReportValue);

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment moduleTitle = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\">\"" + guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) + " " + moduleInstance.getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(moduleTitle);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> map = moduleInstance.getCategoryCategoryInstanceSortedMap();
        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        Optional<Report> reportOpt = reportService.findById(moduleInstance.getModule().getId());
        if (reportOpt.isPresent()) {
            Report report = reportOpt.get();
            for (Profile profile : report.getProfileList()) {


                if(profile.getId().equals(10L)) {
                    if(profile.getTranslations().get(language.toUpperCase()) != null && !profile.getTranslations().get(language.toUpperCase()).isEmpty()) {
                        htmlSubProfileNameTextFragment += "DESCRIPTION: " + profile.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                    }
                }

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    String[] questions = subprofile.getModule3questions().split(";");
                    String[] answers = subprofile.getModule3answers().split(";");

                    if(questions.length == 1) {
                        Question question = map.get(profile.getCategory()).getQuestionAnswerSortedMap().entrySet().stream()
                                .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[0])))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        if(question != null) {
                            Answer answer = map.get(profile.getCategory()).getQuestionAnswerSortedMap().get(question);
                            if(answer.getId().equals(Long.parseLong(answers[0]))) {
                                htmlSubProfileNameTextFragment += profile.getCategory().getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                                if(subprofile.getColor().equals("RED")) {
                                    htmlSubProfileNameTextFragment += "YOUR LEVEL: Early pioneers <br/>";
                                }
                                if(subprofile.getColor().equals("YELLOW")) {
                                    htmlSubProfileNameTextFragment += "YOUR LEVEL: Looking forward <br/>";
                                }
                                htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                            }
                        }
                    }
                    if(questions.length == 2) {
                        Question question1 = map.get(profile.getCategory()).getQuestionAnswerSortedMap().entrySet().stream()
                                .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[0])))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        Category category1 = categoryService.findById(profile.getCategory().getId()+1).get();
                        if(map.get(category1) != null) {
                            Question question2 = map.get(category1).getQuestionAnswerSortedMap().entrySet().stream()
                                    .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[1])))
                                    .map(Map.Entry::getKey)
                                    .findFirst()
                                    .orElse(null);
                            if (question1 != null && question2 != null) {
                                Answer answer1 = map.get(profile.getCategory()).getQuestionAnswerSortedMap().get(question1);
                                Answer answer2 = map.get(category1).getQuestionAnswerSortedMap().get(question2);
                                if (answer1.getId().equals(Long.parseLong(answers[0])) && answer2.getId().equals(Long.parseLong(answers[1]))) {
                                    htmlSubProfileNameTextFragment += profile.getCategory().getTranslations().get(language.toUpperCase()) + " & " + category1.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                                    if(subprofile.getColor().equals("RED")) {
                                        htmlSubProfileNameTextFragment += "YOUR LEVEL: Early pioneers <br/>";
                                    }
                                    if(subprofile.getColor().equals("YELLOW")) {
                                        htmlSubProfileNameTextFragment += "YOUR LEVEL: Looking forward <br/>";
                                    }
                                    htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                                }
                            }
                        }
                    }
                    if(questions.length == 3) {
                        Question question1 = map.get(profile.getCategory()).getQuestionAnswerSortedMap().entrySet().stream()
                                .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[0])))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);
                        Category category1 = categoryService.findById(profile.getCategory().getId()+1).get();
                        Category category2 = categoryService.findById(profile.getCategory().getId()+2).get();
                        if(map.get(category1) != null && map.get(category2) != null) {
                            Question question2 = map.get(category1).getQuestionAnswerSortedMap().entrySet().stream()
                                    .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[1])))
                                    .map(Map.Entry::getKey)
                                    .findFirst()
                                    .orElse(null);
                            Question question3 = map.get(category2).getQuestionAnswerSortedMap().entrySet().stream()
                                    .filter(e -> e.getKey().getId().equals(Long.parseLong(questions[2])))
                                    .map(Map.Entry::getKey)
                                    .findFirst()
                                    .orElse(null);
                            if (question1 != null && question2 != null && question3 != null) {
                                Answer answer1 = map.get(profile.getCategory()).getQuestionAnswerSortedMap().get(question1);
                                Answer answer2 = map.get(category1).getQuestionAnswerSortedMap().get(question2);
                                Answer answer3 = map.get(category2).getQuestionAnswerSortedMap().get(question3);
                                if (answer1.getId().equals(Long.parseLong(answers[0])) && answer2.getId().equals(Long.parseLong(answers[1]))
                                        && answer3.getId().equals(Long.parseLong(answers[2]))) {

                                    htmlSubProfileNameTextFragment += profile.getCategory().getTranslations().get(language.toUpperCase()) + " & " + category1.getTranslations().get(language.toUpperCase()) + " & " + category2.getTranslations().get(language.toUpperCase()) + "<br/><br/>";
                                    if(subprofile.getColor().equals("RED")) {
                                        htmlSubProfileNameTextFragment += "YOUR LEVEL: Early pioneers <br/>";
                                    }
                                    if(subprofile.getColor().equals("YELLOW")) {
                                        htmlSubProfileNameTextFragment += "YOUR LEVEL: Looking forward <br/>";
                                    }
                                    htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                                }
                            }
                        }
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleName + " Report.pdf");
        return serverPath + moduleName + " Report.pdf";
    }

    public String createOBPReport(ModuleInstance moduleInstance, ReportService reportService, User_ user) throws Exception {
        init();
        Document document = new Document();
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        Module moduleMnc = moduleInstance.getModule();
        String moduleName = moduleMnc.getTranslations().get(language.toUpperCase());

        License license = new License();
        license.setLicense(fstream);

        ImageStamp imageStamp = new ImageStamp(headerPath);

        imageStamp.setTopMargin(10);
        imageStamp.setHorizontalAlignment(HorizontalAlignment.Center);
        imageStamp.setVerticalAlignment(VerticalAlignment.Top);
        imageStamp.setBackground(true);
        imageStamp.setHeight(80);

        Page page = document.getPages().add();
        page.getPageInfo().getMargin().setTop(100);
        page.getPageInfo().getMargin().setLeft(40);
        page.getPageInfo().getMargin().setRight(40);

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleGuidanceReportTranslationMap.get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        Table table = new Table();
        table.setBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .1f, Color.getBlack()));
        table.setColumnWidths("40% 60%");
        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setTop(15);
        marginInfo.setBottom(15);
        marginInfo.setLeft(10);
        marginInfo.setRight(10);
        table.setDefaultCellPadding(marginInfo);

        Row nameAndSurnameRow = table.getRows().add();
        TextFragment nameAndSurname = new TextFragment(nameAndSurnameTranslationMap.get(language.toUpperCase()));
        nameAndSurname.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurname.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurname.getTextState().setFontSize(10);
        nameAndSurname.getTextState().setFontStyle(FontStyles.Bold);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurname);
        TextFragment nameAndSurnameValue = new TextFragment(user.getFirstname() + " " + user.getLastname());
        nameAndSurnameValue.getTextState().setFont(FontRepository.openFont(fontPath));
        nameAndSurnameValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        nameAndSurnameValue.getTextState().setFontSize(10);
        nameAndSurnameRow.getCells().add().getParagraphs().add(nameAndSurnameValue);

        Row organizationRow = table.getRows().add();
        TextFragment organization = new TextFragment(organizationTranslationMap.get(language.toUpperCase()));
        organization.getTextState().setFont(FontRepository.openFont(fontPath));
        organization.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organization.getTextState().setFontSize(10);
        organization.getTextState().setFontStyle(FontStyles.Bold);
        organizationRow.getCells().add().getParagraphs().add(organization);
        TextFragment organizationValue = new TextFragment(user.getOrganizationName());
        organizationValue.getTextState().setFont(FontRepository.openFont(fontPath));
        organizationValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        organizationValue.getTextState().setFontSize(10);
        organizationRow.getCells().add().getParagraphs().add(organizationValue);

        if(user.getPlace().getCountry() != null) {
            Row countryRow = table.getRows().add();
            TextFragment country = new TextFragment(countryTranslationMap.get(language.toUpperCase()));
            country.getTextState().setFont(FontRepository.openFont(fontPath));
            country.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            country.getTextState().setFontSize(10);
            country.getTextState().setFontStyle(FontStyles.Bold);
            countryRow.getCells().add().getParagraphs().add(country);
            TextFragment countryValue = new TextFragment(user.getPlace().getCountry());
            countryValue.getTextState().setFont(FontRepository.openFont(fontPath));
            countryValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            countryValue.getTextState().setFontSize(10);
            countryRow.getCells().add().getParagraphs().add(countryValue);

        }

        if(user.getModuleInstanceList().stream().filter(t-> String.valueOf(t.getPercentage()).equals("100.0")).findAny().isPresent()) {
            Row selfAssessmentProgressRow = table.getRows().add();
            TextFragment selfAssessmentProgress = new TextFragment(selfAssessmentProgressTranslationMap.get(language.toUpperCase()));
            selfAssessmentProgress.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgress.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgress.getTextState().setFontSize(10);
            selfAssessmentProgress.getTextState().setFontStyle(FontStyles.Bold);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgress);

            String percentageAndModules = "";

            for(ModuleInstance m : user.getModuleInstanceList()) {
                if(String.valueOf(m.getPercentage()).equals("100.0")) {
                    percentageAndModules += m.getModule().getTranslations().get(language.toUpperCase()) + " ";
                }
            }

            TextFragment selfAssessmentProgressValue = new TextFragment(percentageAndModules + " (" + user.getProgressPercentage() + "%)");
            selfAssessmentProgressValue.getTextState().setFont(FontRepository.openFont(fontPath));
            selfAssessmentProgressValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
            selfAssessmentProgressValue.getTextState().setFontSize(10);
            selfAssessmentProgressRow.getCells().add().getParagraphs().add(selfAssessmentProgressValue);

        }

        Row moduleGuidanceReportRow = table.getRows().add();
        TextFragment moduleGuidanceReport = new TextFragment((moduleTranslationMap.get(language.toUpperCase())));
        moduleGuidanceReport.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReport.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReport.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReport);
        moduleGuidanceReport.getTextState().setFontStyle(FontStyles.Bold);
        TextFragment moduleGuidanceReportValue = new TextFragment(moduleInstance.getModule().getTranslations().get(language.toUpperCase()) );
        moduleGuidanceReportValue.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleGuidanceReportValue.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
        moduleGuidanceReportValue.getTextState().setFontSize(10);
        moduleGuidanceReportRow.getCells().add().getParagraphs().add(moduleGuidanceReportValue);

        page.getParagraphs().add(table);
        page.getParagraphs().add(emptyTextFragment);

        HtmlFragment moduleTitle = new HtmlFragment("<p style=\"font-family: Arial, Helvetica, sans-serif;font-size:14px;padding:10px;background-color:rgb(217, 217, 217);text-alignment:left;\">\"" + guidelinesTranslationMap.get(language.toUpperCase()) + ":  " + moduleTranslationMap.get(language.toUpperCase()) + " " + moduleInstance.getModule().getTranslations().get(language.toUpperCase()) +"\" </p>");
        page.getParagraphs().add(moduleTitle);
        page.getParagraphs().add(emptyTextFragment);

        Map<Category, CategoryInstance> map = moduleInstance.getCategoryCategoryInstanceSortedMap();

        String htmlSubProfileNameTextFragment = "<p style=\"font-family: Arial, Helvetica, sans-serif;white-space:pre-wrap;font-size:14px;padding:10px;border:1px solid #000; text-alignment:left;\">";

        for(Category category : map.keySet()) {
            Optional<Report> reportOpt = reportService.findById(moduleInstance.getModule().getId());
            if (reportOpt.isPresent()) {
                Report report = reportOpt.get();
                Profile profile = report.getProfileList().stream()
                        .filter(profile1 -> category.equals(profile1.getCategory()))
                        .findAny().orElse(null);

                htmlSubProfileNameTextFragment += category.getTranslations().get(language.toUpperCase()) + "<br/><br/>";

                for (Subprofile subprofile : profile.getSubprofiles()) {
                    if (map.get(category).getCategoryScore() > subprofile.getLowerBoundScore() &&
                            map.get(category).getCategoryScore() <= subprofile.getUpperBoundScore()) {

                        if(subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment() != null && !subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment().isEmpty()) {
                            htmlSubProfileNameTextFragment += "DESCRIPTION: " + subprofile.getTranslations().get(language.toUpperCase()).getGeneralComment() + "<br/><br/>";
                        }


                        htmlSubProfileNameTextFragment += "YOUR LEVEL: " + subprofile.getTranslations().get(language.toUpperCase()).getName() + "<br/><br/>";
                        htmlSubProfileNameTextFragment += subprofile.getTranslations().get(language.toUpperCase()).getEvaluation() + "<br/><br/>";
                    }
                }
            }
        }
        htmlSubProfileNameTextFragment += "</p>";
        page.getParagraphs().add(new HtmlFragment(htmlSubProfileNameTextFragment));

        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleName + " Report.pdf");
        return serverPath + moduleName + " Report.pdf";
    }
}
