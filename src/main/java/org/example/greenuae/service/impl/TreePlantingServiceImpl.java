package org.example.greenuae.service.impl;


import org.example.greenuae.config.TreeIdentifierConfig;
import org.example.greenuae.exception.ResourceNotFoundException;
import org.example.greenuae.model.Tree;
import org.example.greenuae.model.TreeInfo;
import org.example.greenuae.repository.TreeInfoRepository;
import org.example.greenuae.service.OpenAIService;
import org.example.greenuae.service.TreePlantingService;
import org.example.greenuae.util.PointAllocator;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Configuration
public class TreePlantingServiceImpl implements TreePlantingService {

    private OpenAIService openAIService;
    private TreeIdentifierConfig treeIdentifierConfig;
    private PointAllocator pointAllocator;
    private TreeInfoRepository treeInfoRepository;

    public TreePlantingServiceImpl(OpenAIService openAIService, TreeIdentifierConfig treeIdentifierConfig,
                                   PointAllocator pointAllocator, TreeInfoRepository treeInfoRepository) {
        super();
        this.openAIService = openAIService;
        this.treeIdentifierConfig = treeIdentifierConfig;
        this.pointAllocator = pointAllocator;
        this.treeInfoRepository = treeInfoRepository;
    }

    @Override
    public Mono<String> processTreeSubmission(Tree tree, long tree_id) {
        try {

            byte[] imagePath = tree.getTree_photo();
            String plantingDate = tree.getPlanting_date();
            String location = tree.getGps_location();

            // Step 1: Identify the tree species from the image
            String treeSpecies = treeIdentifierConfig.identifyTreeSpecies(imagePath);
            System.out.println("Identified Tree Species: " + treeSpecies);

            // Step 2: Get tree information and impact assessment
            String treeInfo = openAIService.getTreeInformation(treeSpecies, plantingDate, location);
            System.out.println(treeInfo);

            // Step 3: Extract the impact category from the API response
            String impactCategory = pointAllocator.extractImpactCategory(treeInfo);
            System.out.println("Impact Category: " + impactCategory);

            // Step 4: Assign points based on the impact category
            int points = pointAllocator.determinePoints(impactCategory);
            System.out.println("Points Awarded: " + points);
            int startIndex = treeInfo.indexOf("1.");
            if (startIndex != -1) {
                String filteredTreeInfo = treeInfo.substring(startIndex);
                String[] paragraphs = filteredTreeInfo.split("\\n\\n");
                TreeInfo treeData = new TreeInfo();
                treeData.setTree_species(treeSpecies);
                treeData.setInfo_one(paragraphs[0]);
                treeData.setInfo_two(paragraphs[1]);
                treeData.setInfo_three(paragraphs[2]);
                treeData.setInfo_four(paragraphs[3]);
                treeData.setImpact_category(impactCategory);
                treeData.setPoints(points);
                tree.setPoints_rewarded(points);
                treeData.setTree_id(tree_id);
                treeInfoRepository.save(treeData);
            } else {
                System.out.println("The starting sequence was not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TreeInfo getTreeById(long treeId) {
        return treeInfoRepository.findById(treeId).orElseThrow(
                () -> new ResourceNotFoundException("Tree", "Id", treeId));
    }

    @Override
    public List<TreeInfo> getAllTreeInfo() {
        return treeInfoRepository.findAll();
    }
}
