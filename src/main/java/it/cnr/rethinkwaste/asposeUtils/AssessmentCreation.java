package it.cnr.rethinkwaste.asposeUtils;

import com.aspose.pdf.*;
import it.cnr.rethinkwaste.model.assessment.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.FileInputStream;
import java.util.Map;

public class AssessmentCreation {

    private static String serverPath = "/opt/tomcat/webapps/temp/";//"/opt/tomcat/webapps/temp/";      "C:\\Users\\noemi\\Desktop\\temp\\"

    public static String licenseServerPath = "/opt/tomcat/webapps/temp/Aspose.Total.Java.lic"; //"/opt/tomcat/webapps/temp/Aspose.Total.Java.lic";      "C:\\Users\\noemi\\Desktop\\temp\\Aspose.Total.Java.lic";

    public static String fontPath =  "/opt/tomcat/webapps/temp/Arial.ttf"; //"/opt/tomcat/webapps/temp/Arial.ttf"        "C:\\Users\\noemi\\Desktop\\temp\\Arial.ttf"

    public static String headerPath = "/opt/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + "pdf_header.png";
    // System.getProperty("user.dir") + "/src/main/resources/static/img/" + "pdf_header.png";
    // "/opt/tomcat/webapps/ROOT/WEB-INF/classes/static/img/" + "pdf_header.png";

    public String language = LocaleContextHolder.getLocale().getLanguage();

    public String createAssessment(ModuleInstance moduleInstance) throws Exception {
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        License license = new License();
        license.setLicense(fstream);

        Document document = new Document();
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

        Map<Category, CategoryInstance> map = moduleInstance.getCategoryCategoryInstanceSortedMap();

        TextFragment titleTextFragment = new TextFragment();
        titleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        titleTextFragment.getTextState().setFontSize(10);
        titleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
        titleTextFragment.setText(moduleInstance.getModule().getTranslations().get(language.toUpperCase()));
        titleTextFragment.setHorizontalAlignment(HorizontalAlignment.Center);
        page.getParagraphs().add(titleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        for(Category category : map.keySet()) {

            TextFragment categoryTitleTextFragment = new TextFragment();
            categoryTitleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
            categoryTitleTextFragment.getTextState().setFontSize(10);
            categoryTitleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
            categoryTitleTextFragment.setText(category.getTranslations().get(language.toUpperCase()));
            page.getParagraphs().add(categoryTitleTextFragment);
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

            CategoryInstance categoryInstance = map.get(category);

            for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {

                Row questionRow = table.getRows().add();
                TextFragment question = new TextFragment(q.getTranslations().get(language.toUpperCase()).getText());
                question.getTextState().setFont(FontRepository.openFont(fontPath));
                question.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
                question.getTextState().setFontSize(10);
                question.getTextState().setFontStyle(FontStyles.Bold);
                questionRow.getCells().add().getParagraphs().add(question);

                TextFragment answer = new TextFragment(categoryInstance.getQuestionAnswerSortedMap().get(q).getTranslations().get(language.toUpperCase()));
                answer.getTextState().setFont(FontRepository.openFont(fontPath));
                answer.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
                answer.getTextState().setFontSize(10);
                questionRow.getCells().add().getParagraphs().add(answer);
            }
            page.getParagraphs().add(table);
            page.getParagraphs().add(emptyTextFragment);
        }
        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleInstance.getModule().getText() + " Report.pdf");
        return serverPath + moduleInstance.getModule().getText() + " Report.pdf";
    }

    public String createARMAssessment(ModuleInstance moduleInstance) throws Exception {
        FileInputStream fstream = new FileInputStream(licenseServerPath);

        License license = new License();
        license.setLicense(fstream);

        Document document = new Document();
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

        Map<Category, CategoryInstanceForMC> map = moduleInstance.getCategoryCategoryInstanceForMCSortedMap();

        TextFragment moduleTitleTextFragment = new TextFragment();
        moduleTitleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
        moduleTitleTextFragment.getTextState().setFontSize(18);
        moduleTitleTextFragment.setText(moduleInstance.getModule().getText());
        page.getParagraphs().add(moduleTitleTextFragment);

        TextFragment emptyTextFragment = new TextFragment();
        emptyTextFragment.setText("");
        page.getParagraphs().add(emptyTextFragment);

        for(Category category : map.keySet()) {

            TextFragment categoryTitleTextFragment = new TextFragment();
            categoryTitleTextFragment.getTextState().setFont(FontRepository.openFont(fontPath));
            categoryTitleTextFragment.getTextState().setFontSize(10);
            categoryTitleTextFragment.getTextState().setForegroundColor(Color.fromArgb(0, 112, 192));
            categoryTitleTextFragment.setText(category.getTranslations().get(language.toUpperCase()));
            page.getParagraphs().add(categoryTitleTextFragment);
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

            CategoryInstanceForMC categoryInstance = map.get(category);

            for(Question q : categoryInstance.getQuestionAnswerSortedMap().keySet()) {

                Row questionRow = table.getRows().add();
                TextFragment question = new TextFragment(q.getTranslations().get(language.toUpperCase()).getText());
                question.getTextState().setFont(FontRepository.openFont(fontPath));
                question.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
                question.getTextState().setFontSize(10);
                question.getTextState().setFontStyle(FontStyles.Bold);
                questionRow.getCells().add().getParagraphs().add(question);

                String answers = "";

                for(Answer a : categoryInstance.getQuestionAnswerSortedMap().get(q).getAnswers()) {
                    answers += a.getTranslations().get(language.toUpperCase())+ "; ";
                }

                TextFragment answer = new TextFragment(answers);
                answer.getTextState().setFont(FontRepository.openFont(fontPath));
                answer.getTextState().setForegroundColor(Color.fromArgb(0, 0, 128));
                answer.getTextState().setFontSize(10);
                questionRow.getCells().add().getParagraphs().add(answer);
            }
            page.getParagraphs().add(table);
            page.getParagraphs().add(emptyTextFragment);
        }
        document.getPages().get_Item(1).addStamp(imageStamp);

        document.save(serverPath + moduleInstance.getModule().getText() + " Report.pdf");
        return serverPath + moduleInstance.getModule().getText() + " Report.pdf";
    }

}
