package com.andrew.findduplicates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindDuplicatesController {
    private final FindDuplicatesService FindDuplicatesService;

    @Autowired
    public FindDuplicatesController(FindDuplicatesService FindDuplicatesService) {
        this.FindDuplicatesService = FindDuplicatesService;
    }

    private String fileStrategy(String fileToParse) {
        FindDuplicatesBean result = FindDuplicatesService.runDeduplicationJob(fileToParse);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<title>Check For Duplicates</title>\n");
        sb.append("</head>\n");
        sb.append("Possible Duplicates\n");
        sb.append("<ol>\n");
        result.getpossibleDuplicates()
            .forEach(s -> sb.append("<li>" + s + "</li>\n"));
        sb.append("</ol>\n");
        sb.append("Unique Records\n");
        sb.append("<ol>\n");
        result.getuniqueRecords()
            .forEach(s -> sb.append("<li>" + s + "</li>\n"));
        sb.append("</ol>\n");
        sb.append("</html>\n");
        return sb.toString();
    }

    @RequestMapping("/findduplicates")
    public String findDuplicatesNormal() {
        return fileStrategy("normal");
    }
    
    @RequestMapping("/findduplicatesadvanced")
    public String findDuplicatesAdvanced() {
        return fileStrategy("advanced");
    }

    @RequestMapping("/findduplicates.json")
    public FindDuplicatesBean dedupeJson() {
        return FindDuplicatesService.runDeduplicationJob("normal");
    }
    @RequestMapping("/findduplicatesadvanced.json")
    public FindDuplicatesBean dedupeJsonadvanced() {
        return FindDuplicatesService.runDeduplicationJob("advanced");
    }
    
}

