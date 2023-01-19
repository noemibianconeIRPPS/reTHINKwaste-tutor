package it.cnr.rethinkwaste.web.registeredarea.admin;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.assessment.*;
import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.service.UserService;
import it.cnr.rethinkwaste.service.assessment.*;
import it.cnr.rethinkwaste.web.registration.FormConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registeredarea/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private LearningMaterialService learningMaterialService;

    @Autowired
    private SubprofileService subprofileService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LearningMaterialTypeService learningMaterialTypeService;

    @GetMapping
    public String admin(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        model.addAttribute("loggedUser", customUser.getUser());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin";
    }

    @GetMapping(value = "/reportGeneralComments")
    public String reportGeneralComments(Model model) throws IOException {

        List<Report> reports = reportService.findAll();
        model.addAttribute("reports", reports);
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: tableFragment";
    }

    @GetMapping(value = "/saveChangeReportGeneralComments")
    public String saveChangeReportGeneralComments(Model model, @RequestParam("profileId") String profileId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Profile profile = profileService.getProfileById(Long.parseLong(profileId));
        profile.getTranslations().put(language, newText);
        profileService.save(profile);

        List<Report> reports = reportService.findAll();
        model.addAttribute("reports", reports);
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: tableFragment";
    }

    @GetMapping(value = "/saveChangeCategoryGeneralComments")
    public String saveChangeCategoryGeneralComments(Model model, @RequestParam("categoryId") String categoryId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Category category = categoryService.findById(Long.parseLong(categoryId)).get();
        category.getTranslations().put(language, newText);
        categoryService.save(category);

        List<Report> reports = reportService.findAll();
        model.addAttribute("reports", reports);
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: tableFragment";
    }

    @GetMapping(value = "/reportProfileLevel")
    public String reportProfileLevel(Model model) throws IOException {

        List<Profile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: reportProfileLevelFragment";
    }

    @GetMapping(value = "/saveChangeSuprofileName")
    public String saveChangeSuprofileName(Model model, @RequestParam("subprofileId") String subprofileId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Subprofile subprofile = subprofileService.findById(Long.parseLong(subprofileId)).get();
        SubprofileTranslationObject subprofileTranslationObject = subprofile.getTranslations().get(language);
        subprofileTranslationObject.setName(newText);
        subprofileService.save(subprofileTranslationObject);

        List<Profile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: reportProfileLevelFragment";
    }

    @GetMapping(value = "/saveChangeSuprofileEvaluation")
    public String saveChangeSuprofileEvaluation(Model model, @RequestParam("subprofileId") String subprofileId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Subprofile subprofile = subprofileService.findById(Long.parseLong(subprofileId)).get();
        SubprofileTranslationObject subprofileTranslationObject = subprofile.getTranslations().get(language);
        subprofileTranslationObject.setEvaluation(newText);
        subprofileService.save(subprofileTranslationObject);

        List<Profile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: reportProfileLevelFragment";
    }

    @GetMapping(value = "/saveChangeSuprofileGeneralComment")
    public String saveChangeSuprofileGeneralComment(Model model, @RequestParam("subprofileId") String subprofileId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Subprofile subprofile = subprofileService.findById(Long.parseLong(subprofileId)).get();
        SubprofileTranslationObject subprofileTranslationObject = subprofile.getTranslations().get(language);
        subprofileTranslationObject.setGeneralComment(newText);
        subprofileService.save(subprofileTranslationObject);

        List<Profile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: reportProfileLevelFragment";
    }

    @GetMapping(value = "/questions")
    public String questions(Model model) throws IOException {

        List<Module> modules = moduleService.findAll();
        model.addAttribute("modules", modules);
        model.addAttribute("modulesForAnswers", modules);
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: questionsFragment";
    }

    @GetMapping(value = "/saveChangeQuestion")
    public String saveChangeQuestion(Model model, @RequestParam("questionId") String questionId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Question question = questionService.findById(Long.parseLong(questionId)).get();
        QuestionTranslationObject questionTranslationObject = question.getTranslations().get(language);
        questionTranslationObject.setText(newText);
        questionService.save(questionTranslationObject);

        List<Module> modules = moduleService.findAll();
        model.addAttribute("modules", modules);
        model.addAttribute("modulesForAnswers", modules);
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: questionsFragment";
    }

    @GetMapping(value = "/saveChangeQuestionGuideline")
    public String saveChangeQuestionGuideline(Model model, @RequestParam("questionId") String questionId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Question question = questionService.findById(Long.parseLong(questionId)).get();
        QuestionTranslationObject questionTranslationObject = question.getTranslations().get(language);
        questionTranslationObject.setGuideline(newText);
        questionService.save(questionTranslationObject);

        List<Module> modules = moduleService.findAll();
        model.addAttribute("modules", modules);
        model.addAttribute("modulesForAnswers", modules);
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: questionsFragment";
    }

    @GetMapping(value = "/answers")
    public String answers(Model model) throws IOException {

        List<Module> modules = moduleService.findAll();
        model.addAttribute("modulesForAnswers", modules);
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: answersFragment";
    }

    @GetMapping(value = "/saveChangeAnswer")
    public String saveChangeAnswer(Model model, @RequestParam("answerId") String answerId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Answer answer = answerService.findById(Long.parseLong(answerId)).get();
        answer.getTranslations().put(language, newText);
        answerService.save(answer);

        List<Module> modules = moduleService.findAll();
        model.addAttribute("modulesForAnswers", modules);
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: answersFragment";
    }

    @GetMapping(value = "/learningMaterials")
    public String learningMaterials(Model model) throws IOException {

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/saveChangeLearningMaterialLink")
    public String saveChangeLearningMaterialLink(Model model, @RequestParam("learningMaterialId") String learningMaterialId, @RequestParam("newText") String newText) throws IOException {

        LearningMaterial learningMaterial = learningMaterialService.findById(Long.parseLong(learningMaterialId));
        learningMaterial.setLink(newText);
        learningMaterialService.save(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/saveChangeLearningMaterialTitle")
    public String saveChangeLearningMaterialTitle(Model model, @RequestParam("learningMaterialId") String learningMaterialId, @RequestParam("newText") String newText) throws IOException {

        LearningMaterial learningMaterial = learningMaterialService.findById(Long.parseLong(learningMaterialId));
        learningMaterial.setTitle(newText);
        learningMaterialService.save(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/saveChangeLearningMaterialLanguage")
    public String saveChangeLearningMaterialLanguage(Model model, @RequestParam("learningMaterialId") String learningMaterialId, @RequestParam("newText") String newText) throws IOException {

        LearningMaterial learningMaterial = learningMaterialService.findById(Long.parseLong(learningMaterialId));
        learningMaterial.setLanguage(languageService.findByAcronym(newText));
        learningMaterialService.save(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/saveChangeLearningMaterialTypes")
    public String saveChangeLearningMaterialTypes(Model model, @RequestParam("learningMaterialId") String learningMaterialId, @RequestParam("newText") String newText) throws IOException {

        LearningMaterial learningMaterial = learningMaterialService.findById(Long.parseLong(learningMaterialId));
        List<LearningMaterialType> types = new ArrayList<>();

        String[] idsStrings = newText.split(",");
        for(String idString : idsStrings) {
            Long id = Long.parseLong(idString.trim());
            LearningMaterialType type = learningMaterialTypeService.findById(id);
            types.add(type);
        }
        learningMaterial.setLearningMaterialTypes(types);
        learningMaterialService.save(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/saveChangeLearningMaterialModules")
    public String saveChangeLearningMaterialModules(Model model, @RequestParam("learningMaterialId") String learningMaterialId, @RequestParam("newText") String newText) throws IOException {

        LearningMaterial learningMaterial = learningMaterialService.findById(Long.parseLong(learningMaterialId));
        List<Module> modules = new ArrayList<>();

        String[] idsStrings = newText.split(",");
        for(String idString : idsStrings) {
            Long id = Long.parseLong(idString.trim());
            Module module = moduleService.findById(id);
            modules.add(module);
        }
        learningMaterial.setModules(modules);
        learningMaterialService.save(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/addLearningMaterial")
    public String addLearningMaterial(Model model, @RequestParam("modules") String modules, @RequestParam("types") String types,
                                      @RequestParam("language") String language, @RequestParam("title") String title,
                                      @RequestParam("link") String link) throws IOException {

        LearningMaterial learningMaterial = new LearningMaterial();
        learningMaterial.setLink(link);
        learningMaterial.setTitle(title);

        List<Module> modulesList = new ArrayList<>();

        String[] modulesIds = modules.split(",");
        for(String idString : modulesIds) {
            Long id = Long.parseLong(idString.trim());
            Module module = moduleService.findById(id);
            modulesList.add(module);
        }
        learningMaterial.setModules(modulesList);

        List<LearningMaterialType> typesList = new ArrayList<>();

        String[] typesIds = types.split(",");
        for(String idString : typesIds) {
            Long id = Long.parseLong(idString.trim());
            LearningMaterialType type = learningMaterialTypeService.findById(id);
            typesList.add(type);
        }
        learningMaterial.setLearningMaterialTypes(typesList);
        learningMaterial.setLanguage(languageService.findByAcronym(language));

        learningMaterialService.save(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/deleteLearningMaterial")
    public String deleteLearningMaterial(Model model, @RequestParam("learningMaterialId") String learningMaterialId) throws IOException {

        LearningMaterial learningMaterial = learningMaterialService.findById(Long.parseLong(learningMaterialId));
        learningMaterialService.delete(learningMaterial);

        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();
        model.addAttribute("learningMaterials", learningMaterials);
        model.addAttribute("modulesForAnswers", new ArrayList<>());
        model.addAttribute("modules", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: learningMaterialsFragment";
    }

    @GetMapping(value = "/saveChangeCategory")
    public String saveChangeCategory(Model model, @RequestParam("categoryId") String categoryId, @RequestParam("language") String language, @RequestParam("newText") String newText) throws IOException {

        Category category = categoryService.findById(Long.parseLong(categoryId)).get();
        category.getTranslations().put(language, newText);
        categoryService.save(category);

        List<Module> modules = moduleService.findAll();
        model.addAttribute("modules", modules);
        model.addAttribute("modulesForAnswers", modules);
        model.addAttribute("learningMaterials", new ArrayList<>());
        model.addAttribute("profiles", new ArrayList<>());
        model.addAttribute("reports", new ArrayList<>());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("learningMaterialTypes", learningMaterialTypeService.findAll());
        model.addAttribute("learningMaterialModules", moduleService.findAll());

        return "/registeredarea/admin/admin :: questionsFragment";
    }

}
