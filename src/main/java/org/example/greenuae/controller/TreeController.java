package org.example.greenuae.controller;

import org.example.greenuae.model.Tree;
import org.example.greenuae.model.TreeInfo;
import org.example.greenuae.service.TreePlantingService;
import org.example.greenuae.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class TreeController {
    private TreeService treeService;

    private TreePlantingService treePlantingService;

    @Autowired
    public TreeController(TreeService treeService, TreePlantingService treePlantingService) {
        super();
        this.treeService = treeService;
        this.treePlantingService = treePlantingService;
    }

    @PostMapping(value = "/addTree")
    public ResponseEntity<?> addTree(
            @RequestParam("tree_photo") MultipartFile treePhoto,
            @RequestParam("gps_location") String gpsLocation,
            @RequestParam("planting_date") String plantingDate,
            @RequestParam("user_id") long user_id) {

        try {
            byte[] photoBytes = treePhoto.getBytes();
            Tree tree = new Tree();
            tree.setTree_photo(photoBytes);
            tree.setGps_location(gpsLocation);
            tree.setPlanting_date(plantingDate);
            tree.setUser_id(user_id);

            Tree savedTree = treeService.addTree(tree);
            treePlantingService.processTreeSubmission(tree, tree.getTree_id());

            return new ResponseEntity<>(savedTree, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing tree photo", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error adding tree: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getTree/{id}")
    public ResponseEntity<Tree> getTree(@PathVariable("id") long treeId) {
        return new ResponseEntity<Tree>(treeService.getTreeById(treeId), HttpStatus.OK);
    }

    @GetMapping(value = "/trees")
    public List<Tree> getTrees() {
        return treeService.getAllTree();
    }

    @GetMapping(value = "/treeInfo")
    public List<TreeInfo> getTreeInfo() {
        return treePlantingService.getAllTreeInfo();
    }

    @GetMapping(value = "/getTreeInfo/{id}")
    public ResponseEntity<TreeInfo> getTreesInfo(@PathVariable("id") long treeId) {
        return new ResponseEntity<TreeInfo>(treePlantingService.getTreeById(treeId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteTree/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable("id") long treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<String>("Tree deleted!", HttpStatus.OK);
    }
}