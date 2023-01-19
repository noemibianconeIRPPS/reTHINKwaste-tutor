package it.cnr.rethinkwaste.web;

import it.cnr.rethinkwaste.model.assessment.LearningMaterial;
import it.cnr.rethinkwaste.model.assessment.LearningMaterialType;
import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.model.assessment.ModuleInstance;
import it.cnr.rethinkwaste.service.assessment.LearningMaterialService;
import it.cnr.rethinkwaste.service.assessment.ModuleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/learningMaterials"})
public class LearningMaterialsController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private LearningMaterialService learningMaterialService;

    @GetMapping
    public String learningMaterials(@RequestParam(value = "error", required = false) String error, Model model) {
        return "learningMaterials";
    }

    @GetMapping(value = "/map")
    public String learningMaterialsMap(Model model) {
        return "learningMaterialsMap";
    }

    @GetMapping(value = "/db")
    public String learningMaterialsDb(Model model) {
        List<LearningMaterial> suggestedLearningMaterials = learningMaterialService.findByVisible(true);
        model.addAttribute("suggestedLearningMaterials", suggestedLearningMaterials);
        return "learningMaterialsDb";
    }

    @GetMapping(value = "/map/createGraph")
    public @ResponseBody String createGraph(Model model) {

        List<Module> modules = moduleService.findAll();
        List<LearningMaterial> learningMaterials = learningMaterialService.findAll();

        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();

        for(Module module : modules) {
            JSONObject node = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id", module.getAcronym());
            data.put("name", module.getText().toUpperCase());
            data.put("href", "javascript:void(0)");
            node.put("data", data);
            node.put("classes", "moduleNode");
            nodes.put(node);
        }

         for(LearningMaterial learningMaterial : learningMaterials) {
            JSONObject node = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id", learningMaterial.getId());
            data.put("name", learningMaterial.getTitle());
            data.put("href", learningMaterial.getLink());
            node.put("data", data);
            String classes = "";
            if(learningMaterial.getLearningMaterialTypes().size() == 1) {
                classes = ((LearningMaterialType) learningMaterial.getLearningMaterialTypes().toArray()[0]).getType().replace(" ", "");
                classes = classes.replace("/", "");
            }
            else {
                classes = "Other";
            }
            node.put("classes", classes);
            nodes.put(node);

            for(Module module2 : learningMaterial.getModules()) {
                JSONObject edge = new JSONObject();
                JSONObject dataForEdges = new JSONObject();
                dataForEdges.put("source", module2.getAcronym());
                dataForEdges.put("target", learningMaterial.getId());
                edge.put("data", dataForEdges);
                edges.put(edge);
            }

        }

        JSONObject result = new JSONObject();
        result.put("nodes", nodes);
        result.put("edges", edges);

        return result.toString();
    }

}
